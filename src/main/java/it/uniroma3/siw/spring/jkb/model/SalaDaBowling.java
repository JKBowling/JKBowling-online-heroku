package it.uniroma3.siw.spring.jkb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class SalaDaBowling {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String descrizione;
	
	@Column//(unique=true)
	private String link;
	
	@Column
	private String yes;
	
	@OneToMany(mappedBy="sala",cascade=CascadeType.ALL)
	private List<Torneo> torneiOspitati;
	
	private String indirizzo;
	
	public SalaDaBowling() {
		this.torneiOspitati=new ArrayList<>();
		this.yes="yes";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaDaBowling other = (SalaDaBowling) obj;
		if (indirizzo == null) {
			if (other.indirizzo != null)
				return false;
		} else if (!indirizzo.equals(other.indirizzo))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indirizzo == null) ? 0 : indirizzo.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "SalaDaBowling [nome=" + nome + ", descrizione=" + descrizione + ", link=" + link + ", torneiOspitati="
				+ torneiOspitati + ", indirizzo=" + indirizzo + "]";
	}
	
	
	
}
