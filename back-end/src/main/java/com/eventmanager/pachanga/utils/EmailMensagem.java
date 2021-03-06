package com.eventmanager.pachanga.utils;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.errors.ValidacaoException;

@Component(value = "emailMensagem")
public class EmailMensagem {

	public static void enviarEmail(String email, String nomeGrupo, Festa festa) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("eventmanager72@gmail.com", "11443322");
			}
		});

		/** Ativa Debug para sessão */
		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("eventmanager72@gmail.com"));
			// Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(email);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Novo convite para festa");// Assunto

			StringBuilder bodyEmail = new StringBuilder();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			bodyEmail.append("<h1><strong>Pachanga tem uma festa para voc&ecirc;!</strong></h1>\r\n" + "\r\n"
					+ "<p>Voc&ecirc; foi convidado para participar de uma festa como membro coladorador. Segue detalhes da festa:</p>\r\n"
					+ "\r\n" + "<ul>\r\n" + "    <li>Festa: " + festa.getNomeFesta() + "; </li>\r\n"
					+ "    <li>Fun&ccedil;&atilde;o: " + nomeGrupo + "; </li>\r\n" + "    <li>Data: "
					+ festa.getHorarioInicioFesta().format(formatter) + ";</li>\r\n" + "    <li>Local: "
					+ festa.getCodEnderecoFesta() + ".</li>\r\n" + "</ul>\r\n" + "\r\n"
					+ "<p>Para aceitar ou recusar, basta se logar/cadastrar na aplica&ccedil;&atilde;o (clicando <a href=\"https://pachanga.herokuapp.com/\" target=\"_blank\">aqui</a>),&nbsp;acessar as notifica&ccedil;&otilde;es, e fazer a sua escolha.</p>\r\n"
					+ "\r\n" + "<p>Esperamos&nbsp;que aproveite a festa!</p>\r\n" + "\r\n"
					+ "<p><strong>Equipe Pachanga</strong></p>");

			message.setContent(bodyEmail.toString(), "text/html");
			Transport transport = session.getTransport("smtp");
			transport.send(message);

		} catch (MessagingException e) {
			throw new ValidacaoException(e.getMessage());
		}
	}

	public static void enviarEmailQRCode(String email, Festa festa, List<Ingresso> ingressos) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("eventmanager72@gmail.com", "11443322");
			}
		});

		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("eventmanager72@gmail.com"));
			// Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(email);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Pachanga - Os ingressos da festa\"" + festa.getNomeFesta() + "\" chegaram!");// Assunto

			// carrega html
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			MimeBodyPart attachmentPart = new MimeBodyPart();
			MimeMultipart multipart = new MimeMultipart("related");

			File file = PdfConviteManager.gerarPDF(ingressos);
			try {
				attachmentPart = new MimeBodyPart();
				attachmentPart.attachFile(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String htmlMessage = "<h1>Pachanga - " + festa.getNomeFesta()
					+ "</h1>\r\n" + "\r\n" + "<p>Seus ingressos para a " + festa.getNomeFesta()
					+ " foram confirmados. Segue em anexo seu(s) ingresso(s).</p>\r\n";

			messageBodyPart.setContent(htmlMessage, "text/html");
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentPart);

			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.send(message);
			file.delete();

		} catch (MessagingException e) {
			throw new ValidacaoException(e.getMessage());
		}

	}

	public void enviarPDFRelatorio(List<String> emails, File pdf, Usuario usuario, Festa festa) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		// props.put("mail.debug", "false");
		// props.put("mail.smtp.ssl.enable", "true");
		// props.put("mail.smtp.socketFactory.port", "588");
		// props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("eventmanager72@gmail.com", "11443322");
			}
		});

		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("eventmanager72@gmail.com"));
			// Remetente

			for (String email : emails) {
				message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(email));
			}

			// message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Pachanga - Relatórios da Festa " + festa.getNomeFesta() + " Chegaram");// Assunto

			// carrega html
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			MimeBodyPart attachmentPart = new MimeBodyPart();
			MimeMultipart multipart = new MimeMultipart("related");

			try {
				attachmentPart = new MimeBodyPart();
				attachmentPart.attachFile(pdf);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String htmlMessage = "<h1><strong><p style=\"color:#663399\";>Segue o PDF do seu relatório</p></strong></h1>\r\n"
					+ "<div>\r\n" + "<p>Olá,</p><br/>\r\n" + "<p>Segue relatório da festa " + festa.getNomeFesta()
					+ ", gerados pela Pachanga.<br/>\r\n"
					+ "A ausência de um relatório pode significar que a festa não gerou dados suficientes para o mesmo.</p><br/>\r\n"
					+ "<p>Atenciosamente,</p><br/>\r\n" + "<p style=\"color:#FF0000\";>Equipe Pachanga</p> \r\n"
					+ "</div>";

			// messageBodyPart.setContent(htmlMessage, "text/html");
			messageBodyPart.setContent(htmlMessage, "text/html; charset=iso-8859-1");
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentPart);

			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.send(message);
			// pdf.delete();

		} catch (MessagingException e) {
			throw new ValidacaoException(e.getMessage());
		}

	}

}
