package com.bunge.icc.misc;

public class RandomNumber {

	int number;
	boolean isPrime;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isPrime() {
		return isPrime;
	}

	public void setPrime(boolean isPrime) {
		this.isPrime = isPrime;
	}

	@Override
	public String toString() {
		return "RandomNumber [number=" + number + ", isPrime=" + isPrime + "]";
	}

}
