package it.uniroma3.siw.spring.jkb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//import it.uniroma3.siw.spring.jkb.model.Indirizzo;
import it.uniroma3.siw.spring.jkb.model.SalaDaBowling;

public interface SalaDaBowlingRepository  extends CrudRepository<SalaDaBowling,Long> {
	
	@Query("SELECT max(id) FROM SalaDaBowling")
	public Long findSalaUltima();

	public List<SalaDaBowling> findByNomeAndIndirizzo(String nome, String indirizzo);

	public List<SalaDaBowling> findByNomeLike(String nome);

	public List<SalaDaBowling> findByLink(String link);
	
	@Modifying
	@Query("update SalaDaBowling s set s.nome = ?1 , s.descrizione = ?2 , s.link = ?3 , s.indirizzo = ?4 where s.id = ?5")
	public void modificaSala(String nome,String descrizione,String link, String indirizzo, Long id);
	
}
