package com.bunge.icc.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchHttpResponse {

	private String rootUrl;
	private String requestParamStr;
	
	private String HREF_URL_PATTERN ="(?iu)href=\"(.*?)\"";


	public URLConnection establishConnection(String urlStr, String reqParams) {
		URLConnection urlConnection = null;
		this.setUrl(urlStr);
		this.setRequestParamStr(reqParams);
		String finalURL = constructURL(urlStr, reqParams);
		try {
			System.out.println(finalURL);
			URL url = new URL(finalURL);
			urlConnection = url.openConnection();
			urlConnection.connect();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return urlConnection;
	}
	
	public int getHTTPStatus(URLConnection urlConnection) throws IOException{
		HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
		return httpUrlConnection.getResponseCode();
	}

	public String getGetResponse(URLConnection urlConnection) {
		StringBuilder response = new StringBuilder();

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response.toString();
	}

	public String constructURL(String urlStr, String requestParamStr) {
		String rtnURL = null;
		if (urlStr.contains("?")) {
			if (urlStr.substring(urlStr.length()).equalsIgnoreCase("&")) {
				rtnURL = urlStr + requestParamStr;
			} else {
				rtnURL = urlStr + "&" + requestParamStr;
			}
		} else {
			rtnURL = getUrl() + "?" + requestParamStr;
		}
		return rtnURL;
	}
	
	public List<String> getAllBacklinks(String input){
		List<String> rtnObj = new ArrayList<String>();
		Pattern pattern = Pattern.compile(HREF_URL_PATTERN);
		Matcher matcher =pattern.matcher(input);
		while(matcher.find()){
			rtnObj.add(matcher.group());
		}
		return rtnObj;
	}

	public String getUrl() {
		return rootUrl;
	}

	public void setUrl(String url) {
		this.rootUrl = url;
	}

	public String getRequestParamStr() {
		return requestParamStr;
	}

	public void setRequestParamStr(String requestParamStr) {
		this.requestParamStr = requestParamStr;
	}

}
