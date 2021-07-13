package it.uniroma3.siw.spring.jkb.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.jkb.model.SalaDaBowling;
import it.uniroma3.siw.spring.jkb.model.Torneo;

public interface TorneoRepository  extends CrudRepository<Torneo,Long> {
	
	public List<Torneo> findByNome(String nome);
	
	@Query("SELECT max(id) FROM Torneo")
	public Long findTorneoUltimo();

	@Modifying
	@Query("update Torneo t set t.nome = ?1 , t.descrizione = ?2 , t.dataInizio = ?3 , t.dataFine = ?4 , t.dataInizioIscrizioni = ?5 , t.dataFineIscrizioni = ?6 , t.quotaIscrizione = ?7 , t.sala =?8 where t.id = ?9")
	public void modificaTorneo(String nome,String descrizione,LocalDate dataInizio,
			LocalDate dataFine,LocalDate dataInizioIscrizioni,LocalDate dataFineIscrizioni,String quota,SalaDaBowling sala,Long id);
	
	@Modifying
	@Query("update Torneo t set t.scadenza= ?1, t.form = ?2  where t.id =?3")
	public void mostraForm(Boolean bool,String form,Long id);
	
	@Query("SELECT t FROM Torneo t WHERE t.scadenza='true'")
	public List<Torneo> torneiScadenti();
	
}
