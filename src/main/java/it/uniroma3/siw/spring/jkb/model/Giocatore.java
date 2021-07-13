package it.uniroma3.siw.spring.jkb.model;

import java.time.LocalDate;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class Giocatore {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String cognome;
	
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascita;
	
	@Column(nullable=true,unique=true)
	private String soprannome;
	
	@Column(nullable=false)
	private String email;
	
	@Column
	private String cellulare;
	
	@Column(nullable=false,unique=true)
	private String codiceSquadra;
	
	@ManyToOne
	@JoinColumn(name = "giocatori")
	private Squadra squadra;
	
	private Boolean isCapitano;
	
	private String messaggi;
	
	public Giocatore() {
		
		this.isCapitano = false;
		
	}

	public void generaCodice() {
		
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		
		this.codiceSquadra = String.format("%06d", number);
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		if (codiceSquadra == null) {
			if (other.codiceSquadra != null)
				return false;
		} else if (!codiceSquadra.equals(other.codiceSquadra))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceSquadra == null) ? 0 : codiceSquadra.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Giocatore [nome=" + nome + ", cognome=" + cognome + ", dataNascita=" + dataNascita + ", soprannome="
				+ soprannome + ", email=" + email + ", cellulare=" + cellulare + ", codiceSquadra=" + codiceSquadra
				+ "]";
	}
	
	
	
}
