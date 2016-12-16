package com.bunge.app.testcases;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDateValues {
	@Before
	public void setup(){
		System.out.println("Init Method");
	}
	
//	@Test
	public void testDate(){
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    simpDate = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");
	    System.out.println(simpDate.format(date));
	}

	@Test
	public void testAlphaUser(){
		String[] values = {"test","USER111sdf","aaaaBPL",""};
		for(String val : values){
			System.out.println(val+":="+(isAlphaUser(val)));
		}
		
		
	}
	@After
	public void cleanup(){
		System.out.println("Destroy Method");
	}
	
	private static final Pattern PATTERN_ALPHA_USER = Pattern.compile("^([a-z])\\1{3}.*$");
	private boolean isAlphaUser(String username) {
		return username != null && PATTERN_ALPHA_USER.matcher(username).matches();
		
	}
}
