package it.uniroma3.siw.spring.jkb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.jkb.model.Squadra;
import it.uniroma3.siw.spring.jkb.model.Torneo;

public interface SquadraRepository  extends CrudRepository<Squadra,Long>{
	
	public List<Squadra> findByNomeLike(String nome);
	
	@Modifying
	@Query("update Squadra s set s.nome = ?1 where s.id = ?2")
	public void modificaSquadra(String nome,Long id);
	
	@Modifying
	@Query("update Squadra s set s.torneo = ?1 where s.id = ?2")
	public void impostaTorneo(Torneo torneo,Long idSquadra);

}
