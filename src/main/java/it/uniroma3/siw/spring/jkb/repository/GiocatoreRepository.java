package it.uniroma3.siw.spring.jkb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.jkb.model.Giocatore;
import it.uniroma3.siw.spring.jkb.model.Squadra;

public interface GiocatoreRepository extends CrudRepository<Giocatore,Long> {
	
	public List<Giocatore> findByCodiceSquadra(String codiceSquadra);
	
	@Modifying
	@Query("update Giocatore g set g.squadra = ?1 where g.id = ?2")
	public void impostaSquadra(Squadra squadra,Long id);

}
