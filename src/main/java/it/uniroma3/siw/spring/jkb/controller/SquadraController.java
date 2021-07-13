package it.uniroma3.siw.spring.jkb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.jkb.model.Credentials;
import it.uniroma3.siw.spring.jkb.model.Giocatore;
import it.uniroma3.siw.spring.jkb.model.Squadra;
import it.uniroma3.siw.spring.jkb.model.Torneo;
import it.uniroma3.siw.spring.jkb.service.CredentialsService;
import it.uniroma3.siw.spring.jkb.service.GiocatoreService;
import it.uniroma3.siw.spring.jkb.service.SquadraService;
import it.uniroma3.siw.spring.jkb.service.TorneoService;

@Controller
public class SquadraController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private SquadraService squadraService;
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@Autowired
	private TorneoService torneoService;
	
	@RequestMapping(value="/creaSquadra", method=RequestMethod.GET)
	public String creaSquadra(Model model){
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		
		Giocatore capitano = credentials.getGiocatore();

		if(capitano.getSquadra() != null)
			return "error.html";
		
		model.addAttribute("squadra", new Squadra());
		model.addAttribute("capitano", capitano);
		
		return "/giocatore/creaSquadra.html";
	}
	
	@RequestMapping(value="/verificaSquadra", method=RequestMethod.POST)
	public String verificaSquadra(@ModelAttribute("squadra") Squadra squadra,@RequestParam("capitano") String capitano,
									@RequestParam("giocatore2") String giocatore2, @RequestParam("giocatore3") String giocatore3,
									@RequestParam("giocatore4") String giocatore4, Model model) {
		
		Giocatore c = this.giocatoreService.reclutaGiocatore(capitano).get(0);
		
		Giocatore g2 = this.giocatoreService.reclutaGiocatore(giocatore2).get(0);
		model.addAttribute("giocatore2", g2);
				
		Giocatore g3 = this.giocatoreService.reclutaGiocatore(giocatore3).get(0);
		model.addAttribute("giocatore3", g3);
				
		Giocatore g4 = this.giocatoreService.reclutaGiocatore(giocatore4).get(0);
		model.addAttribute("giocatore4", g4);
				
		model.addAttribute("squadra", squadra);
		model.addAttribute("capitano", c);
		
		return "/giocatore/verificaSquadra.html";
		
	}
	
	 @RequestMapping(value="/controlloSquadra", method=RequestMethod.POST)
	 public String indietroSquadra(@ModelAttribute("squadra") Squadra squadra,@RequestParam("capitano") String capitano,
									@RequestParam("giocatore2") String giocatore2, @RequestParam("giocatore3") String giocatore3,
									@RequestParam("giocatore4") String giocatore4, 
									@RequestParam(value = "avanti", required = false) String avanti,
									@RequestParam(value = "indietro", required = false) String indietro,
									Model model) {
		
		if(avanti != null && indietro == null) {
			
			List<Giocatore> team = new ArrayList<>();
			
			Giocatore cpt = this.giocatoreService.reclutaGiocatore(capitano).get(0);
			team.add(cpt);
			

			Giocatore g2 = this.giocatoreService.reclutaGiocatore(giocatore2).get(0);
			team.add(g2);
			Giocatore g3 = this.giocatoreService.reclutaGiocatore(giocatore3).get(0);
			team.add(g3);
			Giocatore g4 = this.giocatoreService.reclutaGiocatore(giocatore4).get(0);
			team.add(g4);
			
			squadra.setGiocatori(team);
			
			for(Giocatore g : squadra.getGiocatori())
				g.setSquadra(squadra);
			
			squadra.getGiocatori().get(0).setIsCapitano(true);
			
			this.squadraService.inserisci(squadra);
			
			return "/giocatore/home.html";
			
		}

		Giocatore c = this.giocatoreService.reclutaGiocatore(capitano).get(0);
		
		Giocatore g2 = this.giocatoreService.reclutaGiocatore(giocatore2).get(0);
		model.addAttribute("giocatore2", g2);
				
		Giocatore g3 = this.giocatoreService.reclutaGiocatore(giocatore3).get(0);
		model.addAttribute("giocatore3", g3);
		
		Giocatore g4 = this.giocatoreService.reclutaGiocatore(giocatore4).get(0);
		model.addAttribute("giocatore4", g4);
		
		model.addAttribute("squadra", squadra);
		model.addAttribute("capitano", c);
		
		return "/giocatore/creaSquadra.html";
		 
	 }
	 
	 @RequestMapping(value = "/giocatore/eliminaSquadra", method = RequestMethod.POST)
	 public String elimiminaSquadra(Model model) {

		 UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		 Giocatore capitano = credentials.getGiocatore();

		 Squadra squadra = capitano.getSquadra();

		 for(Giocatore g : squadra.getGiocatori())
			 g.setSquadra(null);

		 squadra.getTorneo().getSquadreIscritte().remove(squadra);

		 this.squadraService.eliminaSquadra(squadra);

		 model.addAttribute("giocatore", capitano);
		 
		 return "giocatore/home.html";

	 }
	 
	 @RequestMapping(value="/giocatore/modificaSquadra", method = RequestMethod.GET)
	 public String modificaSquadra(Model model) {
		 
		 UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		 Giocatore capitano = credentials.getGiocatore();

		 Squadra squadra = capitano.getSquadra();
		 
		 if(squadra != null) {		 
			 model.addAttribute("squadra", squadra);
			 model.addAttribute("capitano", capitano);
			 model.addAttribute("giocatore2", squadra.getGiocatori().get(1));
			 model.addAttribute("giocatore3", squadra.getGiocatori().get(2));
			 model.addAttribute("giocatore4", squadra.getGiocatori().get(3));
			 return "/giocatore/modificaSquadra.html";
		 }
		 
		 return "error.html";
		 
	 }
	 
	 @RequestMapping(value="/giocatore/confermaModifica", method = RequestMethod.POST)
	 public String modificaSquadra(@ModelAttribute("id") Long id, @RequestParam("nome") String nome,
			 						@RequestParam("capitano") String capitano, @RequestParam("giocatore2") String giocatore2, 
			 						@RequestParam("giocatore3") String giocatore3,
									@RequestParam("giocatore4") String giocatore4,
									Model model) {
		 
		 Squadra s = this.squadraService.getSquadra(id);
		 
		 for(Giocatore g : s.getGiocatori())
			 g.setSquadra(null);

		 List<Giocatore> team = new ArrayList<>();

		 Giocatore cpt = this.giocatoreService.reclutaGiocatore(capitano).get(0);
		 team.add(cpt);

		 Giocatore g2 = this.giocatoreService.reclutaGiocatore(giocatore2).get(0);
		 team.add(g2);

		 Giocatore g3 = this.giocatoreService.reclutaGiocatore(giocatore3).get(0);
		 team.add(g3);

		 Giocatore g4 = this.giocatoreService.reclutaGiocatore(giocatore4).get(0);
		 team.add(g4);


		 s.setGiocatori(team);

		 for(Giocatore g : s.getGiocatori())
			 g.setSquadra(s);

		 s.getGiocatori().get(0).setIsCapitano(true);

		 this.squadraService.modificaSquadra(nome, id);
		 
		 UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		 Giocatore user = credentials.getGiocatore();
		 
		 model.addAttribute("giocatore", user);
		 
		 return "/giocatore/home.html";
		 
	 }
	 
	 @RequestMapping(value="/iscrizione", method = RequestMethod.POST)
	 public String  iscriviti(@RequestParam("nome") String torneo, Model model) {

		 UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		 Giocatore giocatore = credentials.getGiocatore();

		 Squadra s = this.squadraService.getSquadra(giocatore.getSquadra().getId());
		 Torneo t = this.torneoService.torneoPerNome(torneo).get(0);

		 this.squadraService.impostaTorneo(t, s.getId());

		 t.getSquadreIscritte().add(s);

		 model.addAttribute("giocatore", giocatore);

		 return "/giocatore/home.html";

	 }

}
