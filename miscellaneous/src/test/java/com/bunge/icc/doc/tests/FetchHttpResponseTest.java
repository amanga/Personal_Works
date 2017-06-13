package com.bunge.icc.doc.tests;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
		String url = "http://www.atcgtek.com";
		String params = "command=GetParam&version=2.2&session_id=123456789";
		params = "";
		
//		url = "https://www.tutorialspoint.com/";
		
		Set<String> uniqueUrls = new HashSet<String>();
		
		List<String> externalLinks = new ArrayList<String>();
		List<String> subLinks = new ArrayList<String>();
		
		FetchHttpResponse httpResposneObj = new FetchHttpResponse();
		URLConnection urlConnection = httpResposneObj.establishConnection(url, params);
		try {
			if(httpResposneObj.getHTTPStatus(urlConnection)== 200){
				String responseInStr = httpResposneObj.getGetResponse(urlConnection);
				List<String> backLinks = httpResposneObj.getAllBacklinks(responseInStr);
				System.out.println(backLinks.size());
				Iterator<String> backLinksItr = backLinks.iterator();
				while(backLinksItr.hasNext()){
					System.out.println(backLinksItr.next());
//					uniqueUrls.add(backLinksItr.next());
				}
			}
			System.out.println("Size of Linsk := "+uniqueUrls.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void destroy(){
		System.out.println("destory");
	}
}
