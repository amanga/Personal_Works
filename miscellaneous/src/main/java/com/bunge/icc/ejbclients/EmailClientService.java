package com.bunge.icc.ejbclients;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bunge.mailer.client.IntegrationMailServiceClient;
import com.bunge.mailer.client.MailerClientException;
import com.bunge.mailer.client.request.MailRequest;


public class EmailClientService {

	
//	private String FROM_EMAIL = "Anil Kumar Mangali <anilkumar.mangali@bunge.com>";
	private String FROM_EMAIL = "TIBCO-DEV@bunge.com";
//	private String SMTP_HOST = "dmz1-nt-smtp2.na.dir.bunge.com";
	private String SMTP_HOST = "bga-ux-smtp.na.dir.bunge.com";
	public void sendEmailToClient(String[] toAddresses, String subject, String message) {
		try {
			Properties properties = System.getProperties();
			properties.put("mail.transport.protocol", "smtp");
			properties.put("mail.host", SMTP_HOST);
//			properties.put("mail.port", SMTP_HOST);
			Session session = Session.getInstance(properties,null);
			
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(FROM_EMAIL));
			for(String toAddress : toAddresses){
				mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			}
			mimeMessage.setSubject(subject);
			mimeMessage.setContent(message, "text/html; charset=utf-8");
			Transport transport = session.getTransport("smtp");
			transport.send(mimeMessage);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void sendEmailToClient() {
		try {
			
			ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BISIntegrationConfiguration.class);
			
			
			IntegrationMailServiceClient obj = new IntegrationMailServiceClient();
			obj.setApplicationContext(applicationContext);
			obj.setBisJndi("ejb/IntegrationServiceClientHome");
			obj.setInitialized(true);
			obj.setProviderUrl("iiop://bga-ux-wsd1.na.dir.bunge.com:2809");
			obj.setBisMailServiceName("MTMSendMail");
			
			MailRequest mail = new MailRequest();
			mail.setFrom(FROM_EMAIL);
			mail.setTo("Anil Kumar Mangali <anilkumar.mangali@bunge.com>");
			mail.setSubject("Testing Fidessa email service.");
			mail.setBody("Test Mail for Java Fidessa");
			mail.setContentType("text/html");

			obj.send(mail);
		} catch (MailerClientException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		String[] toAddress = {"Anil Kumar Mangali <anilkumar.mangali@bunge.com>"};
		EmailClientService emailClientService = new EmailClientService();
//		emailClientService.sendEmailToClient(toAddress, "Test Subject", "Test Message");
		emailClientService.sendEmailToClient();
	}

}
