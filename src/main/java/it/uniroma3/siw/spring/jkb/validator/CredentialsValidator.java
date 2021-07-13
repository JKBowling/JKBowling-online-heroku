package it.uniroma3.siw.spring.jkb.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.jkb.model.Credentials;
import it.uniroma3.siw.spring.jkb.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator {
	
	@Autowired
	private CredentialsService credentialService;
	
    final Integer MAX_PASSWORD_LENGTH = 20;
    final Integer MIN_PASSWORD_LENGTH = 6;
    
    private static final Logger logger = LoggerFactory.getLogger(CredentialsValidator.class);
    
	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
		
		Credentials credentials = (Credentials) obj;
		String password = credentials.getPassword().trim();
		
		if(password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
			errors.rejectValue("password", "size");
		
		if(!errors.hasErrors()) {
			logger.debug("valori validi");
			if(this.credentialService.getCredentials(credentials.getUsername()) != null ) {
				logger.debug("utente già registrato");
				errors.reject("duplicato");
			}
		}
	}
}
