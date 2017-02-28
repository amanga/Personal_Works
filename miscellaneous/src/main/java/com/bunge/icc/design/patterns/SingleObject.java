package com.bunge.icc.design.patterns;

public class SingleObject {

	private static SingleObject instance ;
	
	private SingleObject(){
		
	}
	
	public static SingleObject getInstance(){
		
		if( instance == null){
			System.out.println("SingleObject is null and creating instance.");
			instance = new SingleObject();
		}
		return instance;
	}
	
	public void init(){
		System.out.println("Init Method Singleton.");
	}
	
	public void destroy(){
		System.out.println("Destroy Method Singleton.");
	}
	
}
