package it.uniroma3.siw.spring.jkb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.jkb.model.Torneo;
import it.uniroma3.siw.spring.jkb.service.SalaDaBowlingService;
import it.uniroma3.siw.spring.jkb.service.TorneoService;

@Controller
public class NavigationController {

	@Autowired
	private SalaDaBowlingService salaDaBowlingService;

	@Autowired
	private TorneoService torneoService;


	//HOME

	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String index(Model model) {
		if(this.torneoService.torneiProssimiAllaScadenza().size()!=0)
			model.addAttribute("tornei",this.torneoService.torneiProssimiAllaScadenza());
		return "index.html";
	}


	//TORNEI

	@RequestMapping(value = "/tornei", method = RequestMethod.GET)
	public String tornei(Model model) {
		model.addAttribute("tornei",this.torneoService.tutti());
		return "tornei.html";
	}

	//SALE PARTNER

	@RequestMapping(value = "/salePartner", method = RequestMethod.GET)
	public String salePartner(Model model) {
		
		model.addAttribute("saleDaBowling", this.salaDaBowlingService.tutti());
		return "salePartner.html";

	}
	
	
	//INFORMAZIONI
	
	@RequestMapping(value = "/informazioni", method = RequestMethod.GET)
	public String informazioni(Model model) {
		return "informazioni.html";
	}


	//ADMIN HOME

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Model model) {
		return "admin/adminHome.html";
	}

}
