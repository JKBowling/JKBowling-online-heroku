package it.uniroma3.siw.spring.jkb.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class Torneo {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false,length=20000)
	private String descrizione;
	
	@Column(nullable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataInizio;
	
	@Column(nullable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataFine;
	
	@Column(nullable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataInizioIscrizioni;
	
	@Column(nullable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataFineIscrizioni;
	
	@Column
	private String quotaIscrizione;
	
//	@Column//(nullable=false,unique=true)
//	private Integer code;
//	
	@Column//attributo che serve per la presentazione dei torne nella pagina
	private String yes;
	
	@Column//attributo che serve per verificare che le date di iscrizioni siano accessibili o meno 
	private Boolean scadenza;
	
	@Column(length=20000)//mi dice se la form verrà mostrata o meno a seconda che il valore sia true o null
	private String form;
	
	@OneToMany(mappedBy="torneo")
	private List<Squadra> squadreIscritte;
	
	@ManyToOne
	private SalaDaBowling sala;
	
	public Torneo() {
		this.squadreIscritte = new ArrayList<>();
		this.yes="y";
	}
	
	@Override
	public String toString() {
		return "Torneo [nome=" + nome + ", descrizione=" + descrizione + ", dataInizio=" + dataInizio + ", dataFine="
				+ dataFine + ", dataInizioIscrizioni=" + dataInizioIscrizioni + ", dataFineIscrizioni="
				+ dataFineIscrizioni + ", quotaIscrizione=" + quotaIscrizione + ", sala=" + sala
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Torneo other = (Torneo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	
	
	
	
}
