package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.ConvidadoTO;

public class ConvidadoTOBuilder {

	private int codConvidado;
	private String email;
	
	public static ConvidadoTOBuilder getInstance() {
		return new ConvidadoTOBuilder();
	}
	
	public ConvidadoTOBuilder codConvidado(int codConvidado) {
		this.codConvidado = codConvidado;
		return this;
	}
	public ConvidadoTOBuilder email(String email) {
		this.email = email;
		return this;
	}
	
	public ConvidadoTO build() {
		ConvidadoTO convidadoTo = new ConvidadoTO();
		convidadoTo.setCodConvidado(codConvidado);
		convidadoTo.setEmail(email);
		return convidadoTo;
	}
	
	
}
