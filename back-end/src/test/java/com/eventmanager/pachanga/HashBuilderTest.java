package com.eventmanager.pachanga;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.eventmanager.pachanga.utils.HashBuilder;

public class HashBuilderTest {
	
	private String senha = "1234";
	
	@Test
	public void criacaoSenhaTest(){
		String senhaHash = HashBuilder.gerarSenha(senha);
		assertEquals(senhaHash.length(), 160);
		assertNotEquals(senhaHash, senha);
	}
	
	@Test
	public void compararCertoSenha(){
		String senhaLogin = HashBuilder.gerarSenha(senha);
		boolean senhaCorreta = HashBuilder.compararSenha(senha, senhaLogin);
		assertEquals(senhaCorreta, true);
	}
	
	@Test
	public void compararErradaSenha(){
		String senhaLogin = HashBuilder.gerarSenha("123");
		boolean senhaCorreta = HashBuilder.compararSenha(senha, senhaLogin);
		assertEquals(senhaCorreta, false);
	}

}
