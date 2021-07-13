package it.uniroma3.siw.spring.jkb.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.jkb.model.Giocatore;
import it.uniroma3.siw.spring.jkb.model.Squadra;
import it.uniroma3.siw.spring.jkb.repository.GiocatoreRepository;

@Service
public class GiocatoreService {
	
	@Autowired
	private GiocatoreRepository giocatoreRepository;
	
	@Transactional
	public Giocatore getGiocatore(Long id) {
		
		Optional<Giocatore> result = this.giocatoreRepository.findById(id);
		return result.orElse(null);
		
	}
	
	@Transactional
	public Giocatore saveGiocatore(Giocatore giocatore) {
		return this.giocatoreRepository.save(giocatore);
	}
	
	@Transactional
	public List<Giocatore> tutti(){
		return (List<Giocatore>) this.giocatoreRepository.findAll();
	}
	
	@Transactional
	public List<Giocatore> reclutaGiocatore(String codiceSquadra){
		return this.giocatoreRepository.findByCodiceSquadra(codiceSquadra);
	}
	
	@Transactional
	public void impostaSquadra(Squadra squadra, Long id) {
		this.giocatoreRepository.impostaSquadra(squadra, id);
	}

}
