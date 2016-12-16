package com.bunge.app.testcases;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTextSimilarities {

	@Before
	public void setup(){
		System.out.println("Init Method");
	}
	
	@Test
	public void testTextSimilarty(){
		System.out.println(checkStringSimilarty("SimoneOgawa HashimotoUchiyama", "SimoneOgawaHashimoto"));
		System.out.println(checkStringSimilarty("Jose Gomes DaSilva", "AlexBruno Dos SantosMorais"));
		System.out.println(checkStringSimilarty("Santiago Emanuel Alvarez Matelica", "Santiago Emanuel Alvarez Matel"));
		System.out.println(checkStringSimilarty("Emiliano Palmieri Mayco", "Mayco Emiliano Palmieri"));
	}
	
	private double checkStringSimilarty(String first, String second){
		return StringUtils.getJaroWinklerDistance(first, second);
	}
	
	@After
	public void cleanup(){
		System.out.println("Destroy Method");
	}
}
