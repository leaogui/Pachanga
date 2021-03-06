package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.utils.EmailMensagem;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ConvidadoService.class)
class ConvidadoServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private ConvidadoRepository convidadoRepository;

	@MockBean
	private FestaRepository festaRepository;

	@Autowired
	private ConvidadoService convidadoService;
	
	@MockBean
	private NotificacaoService notificacaoService;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	public Grupo criacaoGrupo() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		grupo.setOrganizador(false);
		grupo.setFesta(criacaoFesta());
		return grupo;
	}

	private Usuario criacaoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setCodUsuario(1);
		usuario.setEmail("guga.72@hotmail.com");
		return usuario;
	}

	private Festa criacaoFesta() {
		Festa festaTest = new Festa();

		festaTest.setCodFesta(2);
		festaTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTest.setDescOrganizador("sou demente");
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTest.setNomeFesta("festao");
		festaTest.setOrganizador("Joao Neves");
		festaTest.setStatusFesta("P");
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}
	
	@SuppressWarnings("deprecation")
	public Usuario usuarioTest(){
		Usuario usuarioTest = new Usuario();
		
		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");
		
		return usuarioTest;
	}
	
	private List<Grupo> criacaoGrupos(){
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(criacaoGrupo());
		return grupos;
	}
	
	private Convidado criacaoConvidado() {
		Set<Grupo> grupos = new HashSet<>();
		grupos.add(criacaoGrupo());
		Convidado convidado = new Convidado();
		convidado.setCodConvidado(1);
		convidado.setGrupos(grupos);
		convidado.setEmail("teste@teste");
		return convidado;
	}
	

	@Test
	void addUserFestaTest() {
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");
		try (MockedStatic<EmailMensagem> mockEnvioConvite = Mockito
				.mockStatic(EmailMensagem.class)) {
			
			Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
			Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
			Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
			Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
			Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(criacaoUsuario());
			
			StringBuilder retorno = convidadoService.addUsuariosFesta(emails, 14, 1, 13);
			
			assertEquals(20, retorno.length());
			
		}
	}

	@Test
	void addUserFestaEmailTest() {
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");
		
		try (MockedStatic<EmailMensagem> mockEnvioConvite = Mockito
				.mockStatic(EmailMensagem.class)) {
			
			Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
			Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
			Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
			Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
			Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);
			
			StringBuilder retorno = convidadoService.addUsuariosFesta(emails, 14, 1, 13);
			
			assertEquals("guga.72@hotmail.com ", retorno.toString());
		}

	}

	@Test
	void addUserFestaEmailGrupoOrganizadorTest() {
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");

		Grupo grupo = criacaoGrupo();
		grupo.setOrganizador(true);
		
		try (MockedStatic<EmailMensagem> mockEnvioConvite = Mockito
				.mockStatic(EmailMensagem.class)) {
			
			Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
			Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
			Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
			Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
			Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);
			
			boolean erro = false;
			String msgErro = "";
			try {	
				convidadoService.addUsuariosFesta(emails, 14, 1, 13);
			} catch (ValidacaoException e) {
				erro = true;
				msgErro = e.getMessage();
				
			}
			
			assertEquals(true, erro);
			assertEquals("GRUPORGN", msgErro);
		}

	}
	
	@Test
	void addUserFestaEmailFestaNaoEmPreparacaoTest() {
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");

		Festa festa = criacaoFesta();
		festa.setStatusFesta(TipoStatusFesta.INICIADO.getValor());
		
		try (MockedStatic<EmailMensagem> mockEnvioConvite = Mockito
				.mockStatic(EmailMensagem.class)) {
			
			Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
			Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
			Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
			Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
			Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);
			
			boolean erro = false;
			String msgErro = "";
			try {	
				convidadoService.addUsuariosFesta(emails, 14, 1, 13);
			} catch (ValidacaoException e) {
				erro = true;
				msgErro = e.getMessage();
				
			}
			
			assertEquals(true, erro);
			assertEquals("FESTNPRE", msgErro);
		}

	}

	@Test
	void addUserFestaEmailGrupoNulloTest() { // para quando o id do grupo passado retorna null
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");
		
		try (MockedStatic<EmailMensagem> mockEnvioConvite = Mockito
				.mockStatic(EmailMensagem.class)) {
			
			Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
			Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
			Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(null);
			Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
			Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);
			
			boolean erro = false;
			String msgErro = "";
			try {			
				convidadoService.addUsuariosFesta(emails, 14, 1, 13);
			} catch (ValidacaoException e) {
				erro = true;
				msgErro = e.getMessage();
				
			}
			
			assertEquals(true, erro);
			assertEquals("GRUPNFOU", msgErro);
			
		}

	}

	@Test
	void addUserFestaEmailGrupoPermissaoNulloTest() { // para quando o cara nn tiver permissão para fazer isso
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");
		
		List<Grupo> grupos = new ArrayList<>();
		
		try (MockedStatic<EmailMensagem> mockEnvioConvite = Mockito
				.mockStatic(EmailMensagem.class)) {
			
			Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
			Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
			Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
			Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
			Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);
			
			boolean erro = false;
			String msgErro = "";
			try {			
				convidadoService.addUsuariosFesta(emails, 14, 1, 13);
			} catch (ValidacaoException e) {
				erro = true;
				msgErro = e.getMessage();
			}
			
			assertEquals(true, erro);
			assertEquals("USESPERM", msgErro);
			
		}

	}

	@Test
	void addUserFestaEmailFestaNulloTest() { // para quando a festa não existir
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");
		
		try (MockedStatic<EmailMensagem> mockEnvioConvite = Mockito
				.mockStatic(EmailMensagem.class)) {
			
			Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
			Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(null);
			Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
			Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
			Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);
			
			boolean erro = false;
			String msgErro = "";
			try {			
				convidadoService.addUsuariosFesta(emails, 14, 1, 13);
			} catch (ValidacaoException e) {
				erro = true;
				msgErro = e.getMessage();
			}
			
			assertEquals(true, erro);
			assertEquals("FESTNFOU", msgErro);
			
		}

	}

	@Test
	void addUserFestaEmailUsuarioNulloTest() { // para quando o usuário não existir no banco de dados
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");
		
		try (MockedStatic<EmailMensagem> mockEnvioConvite = Mockito
				.mockStatic(EmailMensagem.class)) {
			
			Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(null);
			Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
			Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
			Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
			Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);
			
			boolean erro = false;
			String msgErro = "";
			try {			
				convidadoService.addUsuariosFesta(emails, 14, 1, 13);
			} catch (ValidacaoException e) {
				erro = true;
				msgErro = e.getMessage();
			}
			
			assertEquals(true, erro);
			assertEquals("USERNFOU", msgErro);
			
		}

	}
	
	@Test
	void aceitarConviteTest() {

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuarioTest());
		
		Mockito.when(convidadoRepository.findConvidadoGrupo(Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoConvidado());
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(criacaoUsuario());
		Mockito.doNothing().when(convidadoRepository).deleteConvidadoGrupo(Mockito.anyInt(), Mockito.anyInt());
		Mockito.doNothing().when(notificacaoService).deletarNotificacaoConvidado(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.doNothing().when(convidadoRepository).deleteConvidado(Mockito.anyInt());
		Mockito.doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(usuarioRepository.findUsuarioComPermissao(Mockito.any(), Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.any(), Mockito.anyInt(),Mockito.anyString());

		convidadoService.aceitarConvite(1, 13);

	}
	
	@Test
	void aceitarConviteConvidadoNaoRelacionadoGrupoTest() {

		Mockito.when(convidadoRepository.findConvidadoGrupo(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(criacaoUsuario());
		Mockito.doNothing().when(convidadoRepository).deleteConvidadoGrupo(Mockito.anyInt(), Mockito.anyInt());
		Mockito.doNothing().when(notificacaoService).deletarNotificacaoConvidado(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.doNothing().when(convidadoRepository).deleteConvidado(Mockito.anyInt());
		Mockito.doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());

		boolean erro = false;
		String msgErro = "";
		try {			
		convidadoService.aceitarConvite(1, 13);
		} catch (ValidacaoException e) {
			erro = true;
			msgErro = e.getMessage();
		}

		assertEquals(true, erro);
		assertEquals("CONVNGRU", msgErro);

	}
	
	@Test
	void recusarConviteTest() {

		Mockito.when(convidadoRepository.findConvidadoGrupo(Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoConvidado());
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(criacaoUsuario());
		Mockito.doNothing().when(convidadoRepository).deleteConvidadoGrupo(Mockito.anyInt(), Mockito.anyInt());
		Mockito.doNothing().when(notificacaoService).deletarNotificacaoConvidado(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.doNothing().when(convidadoRepository).deleteConvidado(Mockito.anyInt());
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());

		convidadoService.recusarConvite(1, 13);

	}
	
	@Test
	void pegarConvidadosFestaTest() {
		
		List<Convidado> convidados = new ArrayList<>();
		convidados.add(criacaoConvidado());

		Mockito.when(convidadoRepository.findConvidadosByCodFesta(Mockito.anyInt())).thenReturn(convidados);

		List<Convidado> convidadosRetorno = convidadoService.pegarConvidadosFesta(1);
		
		assertEquals(convidados.size(), convidadosRetorno.size());

	}
	
	@Test
	void pegarConvidadosGrupo() {
		
		List<Convidado> convidados = new ArrayList<>();
		convidados.add(criacaoConvidado());

		Mockito.when(convidadoRepository.findConvidadosNoGrupo(Mockito.anyInt())).thenReturn(convidados);

		List<Convidado> convidadosRetorno = convidadoService.pegarConvidadosGrupo(1);
		
		assertEquals(convidados.size(), convidadosRetorno.size());

	}
	

}