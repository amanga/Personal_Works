package com.bunge.email.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.mail.Folder;
import javax.mail.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bunge.email.service.MailReader;

public class EmailReadTest {

	private String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
	@Before
	public void setup(){
		System.out.println("Init Method");
	}
	
//	@Test
	public void readPop3EmailFromFolder(){
		MailReader reader = new MailReader();
		try {
			Folder inbox = reader.getPop3Inbox();
			Message msg = inbox.getMessage(1);
			System.out.println("Subject: "+msg.getSubject());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
//	@Test
	public void readIMapsEmailFromFolder(){
		MailReader reader = new MailReader();
		try {
			Folder inbox = reader.getImapsInbox();
			Message msg = inbox.getMessage(1);
			System.out.println("Subject: "+msg.getSubject());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void printDifferentTimeZone() throws ParseException{
		
		long milliseconds = 1475818155000l;
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(milliseconds);
		
//		Date currentDate = c.getTime();
		SimpleDateFormat sdf =  new SimpleDateFormat(DATE_FORMAT);
		System.out.println("1.) "+sdf.format(c.getTimeInMillis()));
		
		Calendar cet = getEuropeanTimeZone(c);
		
		
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		
		System.out.println("2.) "+sdf.format(cet.getTimeInMillis()));
		
		System.out.println("3.) "+sdf.format(c.getTimeInMillis()));
		
		String cet_format = sdf.format(c.getTimeInMillis());
		Calendar c2 = Calendar.getInstance();
		c2.setTimeZone(TimeZone.getTimeZone("CET"));
		c2.setTime(c.getTime());
		
//		System.out.println(c2);
	}
	
	private Calendar getEuropeanTimeZone(Calendar calendar) throws ParseException{
		if(calendar == null)
			return calendar;
		
		SimpleDateFormat sdf =  new SimpleDateFormat(DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		String cet_format = sdf.format(calendar.getTime());
		
		;
		Calendar rtnCal = Calendar.getInstance();
		rtnCal.setTimeZone(TimeZone.getTimeZone("CET"));
		rtnCal.setTime(sdf.parse(cet_format));
		
		return rtnCal;
		
	}
	
	@After
	public void cleanup(){
		System.out.println("Destroy Method");
	}
}
