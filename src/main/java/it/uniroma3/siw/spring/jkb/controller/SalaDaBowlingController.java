package it.uniroma3.siw.spring.jkb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//import it.uniroma3.siw.spring.jkb.model.Indirizzo;
import it.uniroma3.siw.spring.jkb.model.SalaDaBowling;
import it.uniroma3.siw.spring.jkb.model.Squadra;
import it.uniroma3.siw.spring.jkb.model.Torneo;
//import it.uniroma3.siw.spring.jkb.service.IndirizzoService;
import it.uniroma3.siw.spring.jkb.service.SalaDaBowlingService;
import it.uniroma3.siw.spring.jkb.validator.SalaDaBowlingValidator;

@Controller
public class SalaDaBowlingController {
	
	@Autowired
	private SalaDaBowlingService salaDaBowlingService;
	/*
	@Autowired
	private IndirizzoService indirizzoService;
	*/
	@Autowired
	private SalaDaBowlingValidator salaDaBowlingValidator;

	@RequestMapping(value="/admin/addSala",method= RequestMethod.GET)
	public String sala(Model model) {
		
		model.addAttribute("sala",new SalaDaBowling());
		//model.addAttribute("indirizzo",new Indirizzo());
		return "admin/sala/salaDaBowlingForm.html";
		
	}
	
	@RequestMapping(value = "/admin/deleteSala", method = RequestMethod.GET)
	public String eliminaSala(@RequestParam("sala") String nome, Model model) {
		
		SalaDaBowling salaDelete = this.salaDaBowlingService.salaPerNome(nome).get(0);
		this.salaDaBowlingService.eliminaSala(salaDelete);
		model.addAttribute("sale",this.salaDaBowlingService.tutti());
		return "admin/sala/sale.html";
		
	}
	
	@RequestMapping(value="/admin/salePartner",method= RequestMethod.GET)
	public String saleAdmin(Model model) {
		model.addAttribute("sale",this.salaDaBowlingService.tutti());
		return "admin/sala/sale.html";
	}
	
	@RequestMapping(value="/controlloSala", method=RequestMethod.POST)
	public String controlloSala(@RequestParam(value = "modifica", required = false) String modifica,
			@RequestParam(value = "elimina", required = false) String elimina,
			@RequestParam("link") String link,
			Model model) {

		if(modifica != null && elimina == null) {
			model.addAttribute("sala", this.salaDaBowlingService.getSala(this.salaDaBowlingService.salaPerLink(link).get(0).getId()));
			model.addAttribute("indirizzo", this.salaDaBowlingService.salaPerLink(link).get(0).getIndirizzo());
			return "admin/sala/salaDaBowlingModifica.html";
		}

		SalaDaBowling salaDelete = this.salaDaBowlingService.salaPerLink(link).get(0);
		
		for(Torneo torneo : salaDelete.getTorneiOspitati()) {
			for(Squadra squadra : torneo.getSquadreIscritte())
				squadra.setTorneo(null);
		}
		
		this.salaDaBowlingService.eliminaSala(salaDelete);
		
		model.addAttribute("sale",this.salaDaBowlingService.tutti());
		return "admin/sala/sale.html";

	}
	
	@RequestMapping(value="/admin/modificaSala", method= {RequestMethod.POST, RequestMethod.PUT})
	public String modificaSala(@RequestParam("id") Long id, @RequestParam("nome") String nome, 
								@RequestParam("descrizione") String descrizione, @RequestParam("link") String link,
								@RequestParam("indirizzo") String indirizzo, Model model) {
	
			this.salaDaBowlingService.modifica(nome, descrizione, link, indirizzo, id);
			model.addAttribute("sale",this.salaDaBowlingService.tutti());
			return "admin/sala/sale.html";
		
		
	}
	
	@RequestMapping(value = "/admin/nuovaSala", method = RequestMethod.POST)
	public String newSala(@ModelAttribute("sala") SalaDaBowling sala,/*@ModelAttribute("indirizzo") Indirizzo indirizzo,*/
			Model model, BindingResult bindingResult) {
		
		this.salaDaBowlingValidator.validate(sala, bindingResult);

		if(!bindingResult.hasErrors()) {
			//sala.setIndirizzo(indirizzo);
			this.salaDaBowlingService.inserisci(sala);
			model.addAttribute("sale", this.salaDaBowlingService.tutti());
			return "admin/sala/sale.html";
		}
		return "error.html";
	} 

}
