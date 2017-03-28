package com.bunge.icc.doc.tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HashMapTests {

	private Map<String,String> stateCapitals = new HashMap<String, String>();
	
	@Before
	public void init(){
		System.out.println("init method....");
		/*stateCapitals.put("NY", "Albany");
		stateCapitals.put("NJ", "Trenton");
		stateCapitals.put("NC", "Raleigh");*/
		stateCapitals.put("TX", "Austin");
		stateCapitals.put("IL", "Springfield");
	}
	
	@Test
	public void iteratorMap(){
		System.out.println("iterator Map..");
		System.out.println("Print before");
		printStateCapitals();
		setTexasNIllinoisCaptial();
		System.out.println();
		System.out.println("Print after");
		printStateCapitals();
	}
	
	private void printStateCapitals(){
		Iterator<Entry<String, String>> itr= stateCapitals.entrySet().iterator();  
		while(itr.hasNext()){
			Entry<String,String> entrySet = itr.next();
			String key = entrySet.getKey();
			System.out.println(key + ":="+ stateCapitals.get(key));
		}
	}
	
	private void setTexasNIllinoisCaptial(){
		System.out.println("setTexasNIllinois Capital..");
		Iterator<Entry<String, String>> itr= stateCapitals.entrySet().iterator();  
		while(itr.hasNext()){
			Entry<String,String> entrySet = itr.next();
			String key = entrySet.getKey();
			if(key.equals("TX")){
				stateCapitals.put("TX", null);
			}
			if(key.equals("IL")){
				stateCapitals.put("IL", null);
			}
		}
	}
	
	@After
	public void destroy(){
		stateCapitals = null;
	}
	
}
