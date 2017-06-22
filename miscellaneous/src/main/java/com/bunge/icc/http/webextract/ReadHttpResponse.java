package com.bunge.icc.http.webextract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadHttpResponse {
	private String rootUrl;
	private String requestParamStr;

	private String HREF_URL_PATTERN = "(?iu)href=\"(.*?)\"";
	private String STRING_IN_QUOTES = "\"([^\"]*)\"";

	public URLConnection establishConnection(String urlStr, String reqParams) {
		URLConnection urlConnection = null;
		this.setUrl(urlStr);
		this.setRequestParamStr(reqParams);
		String finalURL = UrlHelper.constructURL(urlStr, reqParams);
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

	public int getHTTPStatus(URLConnection urlConnection) throws IOException {
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

	public Set<String> getAllBacklinks(String input) {
		Set<String> rtnObj = new HashSet<String>();
		
		Pattern hrefPattern = Pattern.compile(HREF_URL_PATTERN);
		Matcher hrefMatcher = hrefPattern.matcher(input);
		while (hrefMatcher.find()) {
			String hrefStr = hrefMatcher.group();
//			System.out.println(hrefStr);
			Pattern quoteStrpattern = Pattern.compile(STRING_IN_QUOTES);
			Matcher quoteStrMatcher = quoteStrpattern.matcher(hrefStr);
			if(quoteStrMatcher.find()){
				rtnObj.add(quoteStrMatcher.group(1));
			}
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
	
	public static boolean isExternalUrl(URLConnection urlConnection, String href){
		boolean rtnFlag = false;
		String tmpHref = href.replace("www.", "").replace("WWW.", "");
		String urlHost = urlConnection.getURL().getHost().replace("www.", "").replace("WWW.", "");
		if((href.toLowerCase().contains(".com")) && (!tmpHref.contains(urlHost))){
			rtnFlag = true;
		}
		return rtnFlag;
	}
	
	public static boolean isInternalUrl(URLConnection urlConnection, String href){
		boolean rtnFlag = false;
		String tmpHref = href.replace("www.", "").replace("WWW.", "");
		String urlHost = urlConnection.getURL().getHost().replace("www.", "").replace("WWW.", "");
		if(tmpHref.contains(urlHost)){
			rtnFlag = true;
		}
		return rtnFlag;
	}
}
