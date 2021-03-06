package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.domains.Usuario;

public class IngressoBuilder {
	
	private String codIngresso;
	private Lote lote;
	private Festa festa;
	private Usuario usuario;
	private String statusIngresso;
	private float preco;
	private String statusCompra;
	private LocalDateTime dataCompra;
	private LocalDateTime dataCheckin;
	private String nomeTitular;
	private String emailTitular;
	private String urlBoleto;
	
	public static IngressoBuilder getInstance() {
		return new IngressoBuilder();
	}
	
	public IngressoBuilder codIngresso(String codIngresso) {
		this.codIngresso = codIngresso;
		return this;
	}
	
	public IngressoBuilder lote(Lote lote) {
		this.lote = lote;
		return this;
	}
	
	public IngressoBuilder festa(Festa festa) {
		this.festa = festa;
		return this;
	}
	
	public IngressoBuilder usuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}
	
	public IngressoBuilder statusIngresso(String statusIngresso) {
		this.statusIngresso = statusIngresso;
		return this;
	}
	
	public IngressoBuilder preco(float preco) {
		this.preco = preco;
		return this;
	}
	
	public IngressoBuilder statusCompra(String statusCompra) {
		this.statusCompra = statusCompra;
		return this;
	}
	
	public IngressoBuilder dataCompra(LocalDateTime dataCompra) {
		this.dataCompra = dataCompra;
		return this;
	}
	
	public IngressoBuilder dataCheckin(LocalDateTime dataCheckin) {
		this.dataCheckin = dataCheckin;
		return this;
	}
	
	public IngressoBuilder nomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
		return this;
	}
	
	public IngressoBuilder emailTitular(String emailTitular) {
		this.emailTitular = emailTitular;
		return this;
	}
	
	public IngressoBuilder urlBoleto(String urlBoleto) {
		this.urlBoleto = urlBoleto;
		return this;
	}
	
	public Ingresso build() {
		Ingresso ingresso = new Ingresso();
		ingresso.setCodIngresso(codIngresso);
		ingresso.setDataCheckin(dataCheckin);
		ingresso.setDataCompra(dataCompra);
		ingresso.setFesta(festa);
		ingresso.setLote(lote);
		ingresso.setPreco(preco);
		ingresso.setStatusCompra(statusCompra);
		ingresso.setStatusIngresso(statusIngresso);
		ingresso.setUsuario(usuario);
		ingresso.setNomeTitular(nomeTitular);
		ingresso.setEmailTitular(emailTitular);
		ingresso.setUrlBoleto(urlBoleto);
		return ingresso;
		
	}

}
