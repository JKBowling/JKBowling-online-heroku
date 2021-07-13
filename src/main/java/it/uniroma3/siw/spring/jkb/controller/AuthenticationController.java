package it.uniroma3.siw.spring.jkb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.jkb.model.Credentials;
import it.uniroma3.siw.spring.jkb.model.Giocatore;
import it.uniroma3.siw.spring.jkb.service.CredentialsService;
import it.uniroma3.siw.spring.jkb.service.GiocatoreService;
import it.uniroma3.siw.spring.jkb.service.TorneoService;
import it.uniroma3.siw.spring.jkb.validator.CredentialsValidator;
import it.uniroma3.siw.spring.jkb.validator.GiocatoreValidator;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private TorneoService torneoService;
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@Autowired
	private GiocatoreValidator giocatoreValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterForm(Model model) {
		
		model.addAttribute("giocatore", new Giocatore());
		model.addAttribute("credentials", new Credentials());
		return "registerUser.html";
		
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm(Model model) {
		return "login.html";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		return "index.html";
	}
	
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String defaultAfterLogin(Model model) {
	
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		
		if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "admin/adminHome.html";
		}
		
		model.addAttribute("giocatore", this.giocatoreService.getGiocatore(credentials.getGiocatore().getId()));
		return "giocatore/home.html";
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("giocatore") Giocatore giocatore, BindingResult giocatoreBindingResult,
								@ModelAttribute("credentials") Credentials credentials,
								BindingResult credentialsBindingResult,
								Model model) {
		
		this.giocatoreValidator.validate(giocatore, giocatoreBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);
		
		if(!giocatoreBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			
			giocatore.generaCodice();
			credentials.setGiocatore(giocatore);
			credentialsService.saveCredentials(credentials);
			if(this.torneoService.torneiProssimiAllaScadenza().size()!=0)
				model.addAttribute("tornei",this.torneoService.torneiProssimiAllaScadenza());
			return "index.html";
			
		}
		
		return "registerUser.html";
		
	}
				
}
