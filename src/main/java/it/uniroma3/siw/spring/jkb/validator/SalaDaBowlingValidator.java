package it.uniroma3.siw.spring.jkb.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.jkb.model.SalaDaBowling;
import it.uniroma3.siw.spring.jkb.service.SalaDaBowlingService;

@Component
public class SalaDaBowlingValidator implements Validator{
	
	@Autowired
	private SalaDaBowlingService salaDaBowlingService;
	
	private final static Logger logger = LoggerFactory.getLogger(TorneoValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return SalaDaBowling.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		SalaDaBowling o = (SalaDaBowling) obj;
		
		if(this.salaDaBowlingService.alreadyExists(o, o.getIndirizzo())) {
			logger.debug("Sala già esistente");
			errors.reject("Sala già inserita nel database");
			errors.addAllErrors(errors);
		}
	}
	
}
