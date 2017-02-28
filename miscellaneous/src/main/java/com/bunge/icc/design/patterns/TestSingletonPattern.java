package com.bunge.icc.design.patterns;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSingletonPattern {
	
	SingleObject obj = null;
	
	@Before
	public void init(){
		System.out.println("init");
	}
	
	@Test
	public void testSingletonInit(){
		obj = SingleObject.getInstance();
		obj.init();
	}
	
	@Test
	public void testSingletonDestroy(){
		obj = SingleObject.getInstance();
		obj.destroy();
	}
	
	@After
	public void destroy(){
		System.out.println("destroy");
	}
}


