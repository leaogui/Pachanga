package com.eventmanager.pachanga.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusCompra;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusIngresso;

@RunWith(SpringRunner.class)
@WebMvcTest(value=PdfConviteManager.class)
public class PdfConviteManagerTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	public Festa festaTest() {
		Festa festaTest = new Festa();
		festaTest.setCodFesta(2);
		festaTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTest.setDescOrganizador("sou demente");
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.DECEMBER, 7, 7, 7));
		festaTest.setNomeFesta("festao");
		festaTest.setOrganizador("Joao Neves");
		festaTest.setStatusFesta(TipoStatusFesta.PREPARACAO.getValor());
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}
	
	private Lote loteTest() {
		//LocalDateTime.of
		Lote lote = new Lote();
		lote.setFesta(festaTest());
		lote.setCodLote(1);
		lote.setDescLote("lote vip krl");
		lote.setNomeLote("Teste");
		lote.setHorarioInicio(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));
		lote.setHorarioFim(LocalDateTime.of(2020, Month.SEPTEMBER, 24, 7, 7));
		lote.setNumeroLote(1);
		lote.setPreco(17.2f);
		lote.setQuantidade(100);
		return lote;
	}
	
	private Ingresso ingressoTest(String codIngresso) {
		Lote lote = loteTest();
		Ingresso ingresso = new Ingresso();
		ingresso.setCodBoleto("ABC");
		ingresso.setCodIngresso(codIngresso);
		ingresso.setDataCheckin(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		ingresso.setDataCompra(LocalDateTime.of(2014, Month.JUNE, 22, 19, 10));
		ingresso.setEmailTitular("teste@email.com.invalid");
		ingresso.setFesta(lote.getFesta());
		ingresso.setLote(lote);
		ingresso.setNomeTitular("Fulano");
		ingresso.setPreco((float) 2.30);
		ingresso.setStatusCompra(TipoStatusCompra.PAGO.getDescricao());
		ingresso.setStatusIngresso(TipoStatusIngresso.CHECKED.getDescricao());
		ingresso.setUrlBoleto("https://teste.com");
		return ingresso;
	}
	
	private Ingresso ingressoTest2(String codIngresso) {
		Lote lote = loteTest();
		Ingresso ingresso = new Ingresso();
		ingresso.setCodBoleto("ABC");
		ingresso.setCodIngresso(codIngresso);
		ingresso.setDataCheckin(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		ingresso.setDataCompra(null);
		ingresso.setEmailTitular("teste@email.com.invalid");
		ingresso.setFesta(lote.getFesta());
		ingresso.setLote(lote);
		ingresso.setNomeTitular("Fulano");
		ingresso.setPreco((float) 2.30);
		ingresso.setStatusCompra(TipoStatusCompra.PAGO.getDescricao());
		ingresso.setStatusIngresso(TipoStatusIngresso.CHECKED.getDescricao());
		ingresso.setUrlBoleto("https://teste.com");
		return ingresso;
	}
	
	private List<Ingresso> listaIngressoTest() {
		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest("1236543"));
		ingressos.add(ingressoTest("1236546"));
		ingressos.add(ingressoTest2("1236548"));
		return ingressos;
	}
	
	@Test
	void gerarPDF() {
		List<Ingresso> ingressos = listaIngressoTest();
		//File expected = new File("src/main/resources/arquivos/pdf/convite/exemplos/1236543.pdf");
//		File file = PdfConviteManager.gerarPDF(ingressos);
//		File expected = PdfConviteManager.gerarPDF(ingressos);
//		
//		try {
//			boolean result = FileUtils.contentEquals(file, expected);
//			//boolean result = verificaDuplicidade(file, expected);
//			assertEquals(true, result);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally{
//			file.delete();
//			//n deletar o expected	
//		}
	}
	/*
	public static boolean verificaDuplicidade(File f1, File f2) {
        //File f1 = new File(file1);
        //File f2 = new File(file2);
        int byte_f1;
        int byte_f2;
        if (f1.length() == f2.length()) {
            try { 
                InputStream isf1 = new FileInputStream(f1);
                InputStream isf2 = new FileInputStream(f2);
                for (long i = 0; i <= f1.length(); i++) {
                    try {
                        byte_f1 = isf1.read();
                        byte_f2 = isf2.read();
                        if (byte_f1 != byte_f2) {
                            isf1.close();
                            isf2.close();
                            return false; // tamanhos iguais e conteudos diferentes

                        }
                    } catch (IOException ex) {
                    }
                }
            } catch (FileNotFoundException ex) {
            }
        } else {
            return false; // tamanho e conteudo diferente
        }
        return true; // arquivos iguais
    }*/
}
