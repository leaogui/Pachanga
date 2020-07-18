package com.eventmanager.pachanga.utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.errors.ValidacaoException;

@Component(value = "emailMensagem")
public class EmailMensagem {
	
	public void enviarEmail(String email) {
		Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465");
	    props.put("mail.smtp.ssl.checkserveridentity", "true");
	 
	    Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	    	@Override
	           protected PasswordAuthentication getPasswordAuthentication() 
	           {
	                 return new PasswordAuthentication("eventmanager72@gmail.com","11443322");
	           }
	      });
	 
	    /** Ativa Debug para sessão */
	    session.setDebug(true);
	 
	    try {
	 
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress("eventmanager72@gmail.com")); 
	      //Remetente
	 
	      Address[] toUser = InternetAddress //Destinatário(s)
	                 .parse(email);  
	 
	      message.setRecipients(Message.RecipientType.TO, toUser);
	      message.setSubject("Enviando email com JavaMail");//Assunto
	      message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
	      Transport.send(message);
	 	 
	     } catch (MessagingException e) {
	        throw new ValidacaoException(e.getMessage());
	    }
	}

}