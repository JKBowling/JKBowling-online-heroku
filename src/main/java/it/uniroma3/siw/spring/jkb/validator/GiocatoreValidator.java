package it.uniroma3.siw.spring.jkb.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.jkb.model.Giocatore;

@Component
public class GiocatoreValidator implements Validator {
	
	final Integer MAX_NAME_LENGHT = 100;
	final Integer MIN_NAME_LENGHT = 2;
	
	private static final Logger logger = LoggerFactory.getLogger(GiocatoreValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Giocatore.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		Giocatore giocatore = (Giocatore) obj;
		String nome = giocatore.getNome().trim();
		String cognome = giocatore.getCognome().trim();

		if(nome.length() < MIN_NAME_LENGHT || nome.length() > MAX_NAME_LENGHT ) {
			errors.rejectValue("nome", "size");
			logger.debug("lunghezza del nome non valida");
		}
		
		if(cognome.length() < MIN_NAME_LENGHT || nome.length() > MAX_NAME_LENGHT ) {
			errors.rejectValue("cognome", "size");
			logger.debug("lunghezza del cognome non valida");
		}
		
		if(!errors.hasErrors())
			logger.debug("valori validi");
		
	}

}
