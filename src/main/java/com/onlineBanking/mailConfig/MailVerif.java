package com.onlineBanking.mailConfig;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class MailVerif{

   public static void sendMail(String recepient,String string) {  
     
      String username = "shabina261191@gmail.com";
      String password="ppgvcrexrklusqem";
      // Get system properties
      Properties props = new Properties();
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
     
		Session session = Session.getInstance(props, new Authenticator() {		 
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
			}
		  });
      

     Message message =prepareMessage(session,username,recepient,string);
     try {
		Transport.send(message);
		System.out.println("Message sent success");
	} catch (MessagingException e) {
		System.out.println(e);
		e.printStackTrace();
		
	}
    
    }

   private static Message prepareMessage(Session session,String username,String recepient,String string) {
      try {
         // Create a default MimeMessage object.
        Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(username));

         // Set To: header field of the header.
         message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));

         // Set Subject: header field
         message.setSubject("This is the Subject Line!");
        
         message.setText(string);
        
        
         // Send message
        return message;
      } catch (MessagingException mex) {
    	 
         mex.printStackTrace();
         
      }
	return null;
   }
   }
