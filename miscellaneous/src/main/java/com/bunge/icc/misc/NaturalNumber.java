package com.bunge.icc.misc;

import java.util.Iterator;
import java.util.List;

public class NaturalNumber {
	
	public NaturalNumber(){
	}

	public boolean isPrime(int val){
		for(int i=2;i<val;i++) {
	        if(val%i==0)
	            return false;
	    }
	    return true;
	}
	
	public List<RandomNumber> validatePrimeNumber(List<RandomNumber> rndNumCol){
		Iterator<RandomNumber> rndNumbItr = rndNumCol.iterator();
		while(rndNumbItr.hasNext()){
			RandomNumber rndNumObj = rndNumbItr.next();
			rndNumObj.setPrime(isPrime(rndNumObj.getNumber()));
		}
		return rndNumCol;
	}
}
