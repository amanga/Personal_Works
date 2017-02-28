package com.bunge.icc.doc.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bunge.icc.misc.FetchHttpResponse;

public class FetchHttpResponseTest {

	@Before
	public void init(){
		System.out.println("init");
	}
	
	@Test
	public void testHttpResponseFromURL(){
		String url = "http://localhost:9081/ce/aiccinfo";
		String params = "command=GetParam&version=2.2&session_id=123456789";
		FetchHttpResponse httpResposneObj = new FetchHttpResponse(url, params);
		System.out.println(httpResposneObj.getGetResponse());
	}
	@After
	public void destroy(){
		System.out.println("destory");
	}
}
