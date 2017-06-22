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

import com.bunge.icc.http.webextract.ReadHttpResponse;

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
		
		Set<String> backLinks = new HashSet<String>();
		
		List<String> externalLinks = new ArrayList<String>();
		List<String> subLinks = new ArrayList<String>();
		
		ReadHttpResponse httpResposneObj = new ReadHttpResponse();
		URLConnection urlConnection = httpResposneObj.establishConnection(url, params);
		
		System.out.println(" URL host:="+urlConnection.getURL().getHost());
		
		try {
			if(httpResposneObj.getHTTPStatus(urlConnection)== 200){
				String responseInStr = httpResposneObj.getGetResponse(urlConnection);
				 httpResposneObj.getAllBacklinks(responseInStr);
				Iterator<String> backLinksItr = backLinks.iterator();
				while(backLinksItr.hasNext()){
					String href = backLinksItr.next();
//					uniqueUrls.add(backLinksItr.next());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void destroy(){
		System.out.println("destory");
	}
}
