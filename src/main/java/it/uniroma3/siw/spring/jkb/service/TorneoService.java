package it.uniroma3.siw.spring.jkb.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.jkb.model.SalaDaBowling;
import it.uniroma3.siw.spring.jkb.model.Torneo;
import it.uniroma3.siw.spring.jkb.repository.TorneoRepository;

@Service
public class TorneoService {

	@Autowired
	private TorneoRepository torneoRepository;
	
	@Transactional
	public Torneo getTorneo(Long id) {
		
		Optional<Torneo> result = this.torneoRepository.findById(id);
		return result.orElse(null);
		
	}
	
	@Transactional
	public void modificaTorneo(String nome,String descrizione,LocalDate dataInizio,LocalDate dataFine,
			LocalDate dataInizioIscrizioni,LocalDate dataFineIscrzioni,String quota,SalaDaBowling sala,Long id) {
		this.torneoRepository.modificaTorneo(nome, descrizione, dataInizio, dataFine, dataInizioIscrizioni, dataFineIscrzioni, quota, sala, id);
		
	}
	
	@Transactional
	public Boolean controlloDate(Torneo torneo,LocalDate dateInizio,LocalDate dateFine,LocalDate dateInizioIscr,LocalDate dateFineIscr) {
		String messaggio=null;
		if(LocalDate.now().isBefore(dateInizioIscr)) {
			messaggio="Questo torneo non è ancora aperto alle iscrizioni.";
			this.torneoRepository.mostraForm(null,messaggio,torneo.getId());
			return null;
		}
		if(LocalDate.now().isAfter(dateInizioIscr) && LocalDate.now().isBefore(dateFineIscr)) {
			messaggio="Iscrizioni Aperte!";
			this.torneoRepository.mostraForm(true,messaggio,torneo.getId());
			return true;
		}
		if(LocalDate.now().isAfter(dateFineIscr) && LocalDate.now().isBefore(dateInizio)) {
			messaggio="Le iscrizioni a questo torneo sono chiuse.";
			this.torneoRepository.mostraForm(null,messaggio,torneo.getId());
			return null;
		}
		if(LocalDate.now().isAfter(dateInizio) && LocalDate.now().isBefore(dateFine)) {
			this.torneoRepository.mostraForm(null,messaggio,torneo.getId());
			return null;
		}
		if(LocalDate.now().isAfter(dateFine)) {
			messaggio="Torneo Concluso";
			this.torneoRepository.mostraForm(null,messaggio,torneo.getId());
			return null;
		}
		return null;
	}
	
	@Transactional
	public List<Torneo> torneiProssimiAllaScadenza(){
		return this.torneoRepository.torneiScadenti();
	}
	
	@Transactional
	public Torneo saveTorneo(Torneo torneo) {
		List<Torneo> tornei = (List<Torneo>) this.torneoRepository.findAll();
		if(tornei.size()!=0) {
			if(this.getTorneo(this.torneoRepository.findTorneoUltimo()).getYes()!=null) {
				torneo.setYes(null);
			}
		}
		return this.torneoRepository.save(torneo);
	}
	
	@Transactional
	public List<Torneo> tutti(){
		return (List<Torneo>) this.torneoRepository.findAll();
	}
	
	
	@Transactional
	public List<Torneo> torneoPerNome(String nome) {
		return this.torneoRepository.findByNome(nome);
	}
	
	@Transactional
	public void eliminaTorneo(Torneo torneo) {
		this.torneoRepository.deleteById(torneo.getId());
	}
	
	@Transactional
	public Long torneoUltimo() {
		return this.torneoRepository.findTorneoUltimo();
	}
	
	@Transactional
	public boolean alreadyExists(Torneo torneo) {
		List<Torneo> tornei = this.torneoRepository.findByNome(torneo.getNome());
		if (tornei.size() > 0)
			return true;
		else 
			return false;
	} 
}
