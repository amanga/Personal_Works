package com.bunge.email.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.codec.binary.Base64;

import com.bunge.email.utils.EmailConstants;

public class MailReader {

	public Folder getPop3Inbox() throws UnsupportedEncodingException, MessagingException{
		Properties properties = new Properties();
		properties.put("mail.pop3.host", EmailConstants.MAIL_SERVER_POP_HOST);
		properties.put("mail.pop3.port", EmailConstants.MAIL_SERVERT_POP_PORT);
		properties.put("mail.pop3.starttls.enable", "true");
		
		Session session = Session.getDefaultInstance(properties);
		Store store = session.getStore(EmailConstants.MAIL_SERVER_POP_STORETYPE);
		
		store.connect(EmailConstants.MAIL_SERVER_POP_HOST, EmailConstants.MAIL_USERNAME, new String(Base64.decodeBase64(EmailConstants.MAIL_PASSWORD),"UTF-8"));
		System.out.println("123");
		Folder inbox = store.getFolder("Inbox");

		inbox.open(Folder.READ_ONLY);
	
		return inbox;
	}
	public Folder getImapsInbox() throws UnsupportedEncodingException, MessagingException{
		Properties properties = new Properties();
		properties.put("mail.store.protocol", EmailConstants.MAIL_SERVER_IMAPS_PROTOCOL);
		properties.put("mail.store.host", EmailConstants.MAIL_SERVER_IMAPS_HOST);		
		properties.put("mail.store.port", EmailConstants.MAIL_SERVER_IMAPS_PORT);
//		properties.put("mail.pop3.starttls.enable", "true");
		
		Session session = Session.getDefaultInstance(properties);
		Store store = session.getStore(EmailConstants.MAIL_SERVER_IMAPS_PROTOCOL);
		
		store.connect(EmailConstants.MAIL_SERVER_IMAPS_HOST, EmailConstants.MAIL_USERNAME, new String(Base64.decodeBase64(EmailConstants.MAIL_PASSWORD),"UTF-8"));
		System.out.println("123");
		Folder inbox = store.getFolder("Inbox");

		inbox.open(Folder.READ_ONLY);
	
		return inbox;
	}
}
