package it.uniroma3.siw.spring.jkb.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.jkb.model.Torneo;
import it.uniroma3.siw.spring.jkb.service.TorneoService;

@Component
public class TorneoValidator implements Validator {

	@Autowired
	private TorneoService torneoService;

	private final static Logger logger = LoggerFactory.getLogger(TorneoValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Torneo.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		Torneo o = (Torneo) obj;

		if(this.torneoService.alreadyExists(o)) {
			logger.debug("Torneo già inserito");
			errors.reject("Torneo già inserito");
		}
		logger.debug("valori validi");
	}
}
