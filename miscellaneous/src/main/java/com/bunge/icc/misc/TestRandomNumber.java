package com.bunge.icc.misc;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRandomNumber {

	Randomizer randomizer = null;
	
	@Before
	public void init(){
		System.out.println("init");
		randomizer = new Randomizer();
	}
	
	@Test
	public void testRandomizerPrime(){
		int numberOfRandomValues = 5;
		List<RandomNumber> rndNums = randomizer.getRandomNumbers(numberOfRandomValues);
		randomizer.processQueue(rndNums);
	}
	
	@After
	public void destory(){
		System.out.println("destory");
	}
	
	
}
