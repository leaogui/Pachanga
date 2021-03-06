package com.eventmanager.pachanga.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.NotificacaoConvidado;

public interface NotificacaoConvidadoRepository extends JpaRepository<NotificacaoConvidado, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_convidado_notificacao');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Query(value = "SELECT nc FROM NotificacaoConvidado nc JOIN nc.convidado c WHERE c.email = :email")
	public List<NotificacaoConvidado> findConvidadoNotificacaoByEmail(String email);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO convidado_x_notificacao (cod_convidado_notificacao, cod_convidado, cod_notificacao, mensagem, data_emissao) VALUES(:codConvidadoNotificacao, :codConvidado, :codNotificacao, :mensagem, :dataEmissao)", nativeQuery = true)
	public void insertConvidadoNotificacao(int codConvidadoNotificacao, int codConvidado, int codNotificacao, String mensagem, LocalDateTime dataEmissao);

	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM convidado_x_notificacao WHERE cod_convidado = :codConvidado", nativeQuery = true)
	public void deleteNotificacoesConvidado(Integer codConvidado);

}
