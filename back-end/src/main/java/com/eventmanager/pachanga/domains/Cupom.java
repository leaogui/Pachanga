package com.eventmanager.pachanga.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cupom")
public class Cupom {
	
	@Id
	@Column(name = "cod_cupom")
    private int codCupom;
	
	@Column(name = "nome_cupom")
	private String nomeCupom;
	
	@ManyToOne
	@JoinColumn(name = "cod_festa")
	private Festa festa;
	
	@Column(name = "preco_desconto")
	private float precoDesconto;

	public int getCodCupom() {
		return codCupom;
	}

	public void setCodCupom(int codCupom) {
		this.codCupom = codCupom;
	}

	public Festa getFesta() {
		return festa;
	}

	public void setFesta(Festa festa) {
		this.festa = festa;
	}

	public float getPrecoDesconto() {
		return precoDesconto;
	}

	public void setPrecoDesconto(float precoDesconto) {
		this.precoDesconto = precoDesconto;
	}

	public String getNomeCupom() {
		return nomeCupom;
	}

	public void setNomeCupom(String nomeCupom) {
		this.nomeCupom = nomeCupom;
	}
	
	
}