package com.bunge.app.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bunge.app.consumer.EmployeeJobConsumer;
import com.bunge.app.consumer.impl.EmployeeJobConsumerImpl;

public class TestODataEmployeeJob {
	
	
	EmployeeJobConsumer employeeJobConsumer;
	
	@Before
	public void setup(){
		System.out.println("Init Method");
		employeeJobConsumer = new EmployeeJobConsumerImpl();
	}
	
	@Test
	public void testHttpGetMethod(){
		System.out.println("test Http Get Method.");
		try {
			employeeJobConsumer.synch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@After
	public void cleanup(){
		System.out.println("Destroy Method");
	}

}
