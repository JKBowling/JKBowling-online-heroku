package it.uniroma3.siw.spring.jkb.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.jkb.model.Credentials;
import it.uniroma3.siw.spring.jkb.model.Giocatore;
import it.uniroma3.siw.spring.jkb.model.SalaDaBowling;
import it.uniroma3.siw.spring.jkb.model.Squadra;
import it.uniroma3.siw.spring.jkb.model.Torneo;
import it.uniroma3.siw.spring.jkb.service.CredentialsService;
import it.uniroma3.siw.spring.jkb.service.SalaDaBowlingService;
import it.uniroma3.siw.spring.jkb.service.TorneoService;
import it.uniroma3.siw.spring.jkb.validator.TorneoValidator;

@Controller
public class TorneoController {

	Integer cont=0;

	@Autowired
	private TorneoService torneoService;
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private SalaDaBowlingService salaDaBowlingService;

	@Autowired
	private TorneoValidator torneoValidator;

	@RequestMapping(value ="/admin/addTorneo",method=RequestMethod.GET)
	public String addTorneo(Model model) {
		model.addAttribute("torneo",new Torneo());
		model.addAttribute("sale",this.salaDaBowlingService.tutti());
		return "admin/torneo/torneoForm.html";
	}

	@RequestMapping(value = "/torneo/{id}", method = RequestMethod.GET)
	public String getTorneo(@PathVariable("id") Long id, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if(credentials != null) {
			Giocatore user = credentials.getGiocatore();
			model.addAttribute("giocatore", user);
		}
		Torneo t=this.torneoService.getTorneo(id);
		this.torneoService.controlloDate(t, t.getDataInizio(),t.getDataFine(),t.getDataInizioIscrizioni(),t.getDataFineIscrizioni());
		model.addAttribute("torneo",  t);
		return "torneo.html";

	}

	@RequestMapping(value = "/admin/tornei", method = RequestMethod.GET)
	public String torneiAdmin(Model model) {

		List<Torneo> tornei = this.torneoService.tutti();
		model.addAttribute("tornei",tornei);

		return "admin/torneo/tornei.html";

	}

	@RequestMapping(value = "/admin/nuovoTorneo", method = { RequestMethod.GET,RequestMethod.PUT, RequestMethod.POST })
	public String newTorneo(@ModelAttribute("torneo") Torneo torneo,@RequestParam("link")String link,
			Model model, BindingResult bindingResult) {

		this.torneoValidator.validate(torneo, bindingResult);

		if(!bindingResult.hasErrors()) {
			torneo.setSala(this.salaDaBowlingService.salaPerLink(link).get(0));
			this.torneoService.saveTorneo(torneo);
			model.addAttribute("tornei", this.torneoService.tutti());
			return "admin/torneo/tornei.html";
		}
		return "error.html";
	}

	@RequestMapping(value="/admin/controlloTorneo", method =RequestMethod.POST)
	public String dmTorneo(@RequestParam(value = "modifica", required = false) String modifica,
			@RequestParam(value = "elimina", required = false) String elimina,
			@RequestParam("torn") String torneo,
			Model model) {

		if(modifica != null && elimina == null) {
			model.addAttribute("torneo", this.torneoService.getTorneo(this.torneoService.torneoPerNome(torneo).get(0).getId()));
			model.addAttribute("sale",this.salaDaBowlingService.tutti());
			return "admin/torneo/torneoModifica.html";
		}

		Torneo torneoDelete = this.torneoService.torneoPerNome(torneo).get(0);

		for(Squadra squadra : torneoDelete.getSquadreIscritte())
			squadra.setTorneo(null);

		torneoDelete.getSala().getTorneiOspitati().remove(torneoDelete);

		this.torneoService.eliminaTorneo(torneoDelete);

		model.addAttribute("tornei",this.torneoService.tutti());
		return "admin/torneo/tornei.html";

	}

	@RequestMapping(value="/admin/modificaTorneo",method= {RequestMethod.POST})
	public String controlloModificheTorneo(@RequestParam("id") Long id,
			@RequestParam("nome")String nome,
			@RequestParam("descrizione")String descrizione,
			@RequestParam("dataInizioIscrizioni")String dataInizioIscrizioni,
			@RequestParam("dataFineIscrizioni")String dataFineIscrizioni,
			@RequestParam("dataInizio")String dataInizio,
			@RequestParam("dataFine")String dataFine,
			@RequestParam("quotaIscrizione")String quota,
			@RequestParam("link")String link,
			Model model) {

		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDateInizio = LocalDate.parse(dataInizio, formatter);
		LocalDate localDateInizioIscrizioni = LocalDate.parse(dataInizioIscrizioni,formatter);
		LocalDate localDateFineIscrizioni = LocalDate.parse(dataFineIscrizioni,formatter);
		LocalDate localDateFine = LocalDate.parse(dataFine,formatter);

		SalaDaBowling s = this.salaDaBowlingService.salaPerLink(link).get(0);
		this.torneoService.modificaTorneo(nome, descrizione, localDateInizio, localDateFine, localDateInizioIscrizioni, localDateFineIscrizioni, quota, s, id);

		model.addAttribute("tornei",this.torneoService.tutti());
		return "admin/torneo/tornei.html";
	}

}

