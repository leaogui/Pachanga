package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.NotificacaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.NotificacaoFactory;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoGrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoUsuarioRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value=NotificacaoService.class)
public class NotificacaoServiceTest {

	@Autowired
	private NotificacaoService notificacaoService;

	@MockBean
	private NotificacaoRepository notificacaoRepository;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private ConvidadoRepository convidadoRepository;

	@MockBean
	private GrupoRepository grupoRepository;
	
	@MockBean
	private NotificacaoUsuarioRepository notificacaoUsuarioRepository;
	
	@MockBean
	private NotificacaoGrupoRepository notificacaoGrupoRepository;
	
	@MockBean
	private NotificacaoFactory notificacaoFactory;

	@SuppressWarnings("deprecation")
	private Usuario usuarioTest(){
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setSexo("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}
	
	private Notificacao notificacaoTest() {
		Notificacao notificacao = new Notificacao();
		notificacao.setCodNotificacao(1);
		notificacao.setDescNotificacao("teste");
		return notificacao;
	}

	@Test
	public void procurarNoticacaoUsuario() {

		List<Notificacao> notificacoesRetorno = new ArrayList<Notificacao>(); 

		Mockito.when(notificacaoRepository.findNotificacaoGrupoByUserId(Mockito.anyInt())).thenReturn(notificacoesRetorno);
		
		Mockito.when(notificacaoRepository.findConvidadoNotificacaoByUserId(Mockito.anyInt())).thenReturn(notificacoesRetorno);

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		NotificacaoTO notificacaoTo = notificacaoService.procurarNotificacaoUsuario(100);

//		assertEquals(true, notificacoes.isEmpty());

	}

	@Test
	public void procurarNoticacaoUsuarioUsuarioNaoEncontrado() {

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(null);

		boolean erro = false;
		String mensagemErro = null;
		try {
			notificacaoService.procurarNotificacaoUsuario(100);			
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}

		assertEquals(true, erro);

		assertEquals("USERNFOU", mensagemErro);

	}

	@Test
	public void deletarNotificacaoConvidado() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(new Notificacao());

		doNothing().when(notificacaoRepository).deleteConvidadoNotificacao(Mockito.anyInt(), Mockito.anyInt());

		Mockito.when(convidadoRepository.findByEmail(Mockito.anyString())).thenReturn(new Convidado());

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTest());

		notificacaoService.deletarNotificacaoConvidado("teste@teste.com",100);

	}

	@Test
	public void deletarNotificacaoConvidadoNotificacaoNaoEncontrado() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(null);

		doNothing().when(notificacaoRepository).deleteConvidadoNotificacao(Mockito.anyInt(), Mockito.anyInt());

		Mockito.when(convidadoRepository.findByEmail(Mockito.anyString())).thenReturn(new Convidado());

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTest());

		boolean erro = false;
		String mensagemErro = null;
		try {
			notificacaoService.deletarNotificacaoConvidado("teste@teste.com",100);
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}

		assertEquals(true, erro);

		assertEquals("NOTINFOU", mensagemErro);
	}

	@Test
	public void deletarNotificacaoConvidadoConvidadoNaoEncontrado() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(new Notificacao());

		doNothing().when(notificacaoRepository).deleteConvidadoNotificacao(Mockito.anyInt(), Mockito.anyInt());

		Mockito.when(convidadoRepository.findByEmail(Mockito.anyString())).thenReturn(null);

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTest());

		boolean erro = false;
		String mensagemErro = null;
		try {
			notificacaoService.deletarNotificacaoConvidado("teste@teste.com",100);
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}

		assertEquals(true, erro);

		assertEquals("CONVNFOU", mensagemErro);
	}

	@Test
	public void deletarNotificacaoGrupo() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(notificacaoTest());

		doNothing().when(notificacaoRepository).deleteNotificacaoGrupo(Mockito.anyInt(), Mockito.anyInt());

		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(new Grupo());

		notificacaoService.deletarNotificacaoGrupo(1,100);

	}

	@Test
	public void deletarNotificacaoGrupoNotFound() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(notificacaoTest());

		doNothing().when(notificacaoRepository).deleteNotificacaoGrupo(Mockito.anyInt(), Mockito.anyInt());

		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(null);

		boolean erro = false;
		String mensagemErro = null;
		try {
			notificacaoService.deletarNotificacaoGrupo(1,100);
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}
		assertEquals(true, erro);

		assertEquals("GRUPNFOU", mensagemErro);

	}
	
	@Test
	public void inserirNotificacaoGrupo() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(notificacaoTest());

		doNothing().when(notificacaoRepository).insertNotificacaoGrupo(Mockito.anyInt(), Mockito.anyInt());

		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(new Grupo());

		notificacaoService.inserirNotificacaoGrupo(1,100);

	}
	
	@Test
	public void inserirNotificacaoConvidado() {
		
		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(new Notificacao());

		Mockito.when(convidadoRepository.findByEmail(Mockito.anyString())).thenReturn(new Convidado());

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTest());

		doNothing().when(notificacaoRepository).insertConvidadoNotificacao(Mockito.anyInt(), Mockito.anyInt());

		notificacaoService.inserirNotificacaoConvidado("teste@teste.com",100);

	}


}
