package it.uniroma3.siw.spring.jkb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.jkb.model.Credentials;
import it.uniroma3.siw.spring.jkb.service.CredentialsService;
import it.uniroma3.siw.spring.jkb.service.GiocatoreService;

@Controller
public class GiocatoreController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private GiocatoreService giocatoreService;

	@RequestMapping(value="/giocatoreHome", method = RequestMethod.GET)
	public String giocatoreHome(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("giocatore", this.giocatoreService.getGiocatore(credentials.getGiocatore().getId()));
		return "giocatore/home.html";
	}

}
