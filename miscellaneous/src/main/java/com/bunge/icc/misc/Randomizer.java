package com.bunge.icc.misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Randomizer {

	private NaturalNumber naturalNumber;
	
	public Randomizer(){
	}
	
	public RandomNumber getRandomNumber(){
		Random rndObj = new Random();
		RandomNumber rndNumberObj = new RandomNumber();
		rndNumberObj.setNumber(rndObj.nextInt(100));
		rndNumberObj.setPrime(false);
		return rndNumberObj;
	}
	
	public List<RandomNumber> getRandomNumbers(int size){
		if(size==0)
			return null;
		
		List<RandomNumber> queue  = new ArrayList<RandomNumber>();
		for(int i=0; i<size; i++){
			RandomNumber rndNum = getRandomNumber();
			queue.add(rndNum);
		}
		return queue;
	}
	
	public void processQueue(List<RandomNumber> rndNums){
		naturalNumber = new NaturalNumber();
		List<RandomNumber> finalRslt = naturalNumber.validatePrimeNumber(rndNums);
		printValue(finalRslt);
	}
	
	public void printValue(List<RandomNumber> rndNumbers){
		Iterator<RandomNumber> rndNumberItr = rndNumbers.iterator();
		while(rndNumberItr.hasNext()){
			RandomNumber rndNum = rndNumberItr.next();
			System.out.println(rndNum);
		}
	}
	
}
