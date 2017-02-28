package com.bunge.icc.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FetchHttpResponse {

	private String url;
	private String requestParamStr;

	public FetchHttpResponse(String url, String params) {
		this.url = url;
		this.requestParamStr = params;
	}

	public String getGetResponse() {
		String response = null;
		String finalURL = constructURL(this.getUrl(), this.getRequestParamStr());
		try {
			URL url = new URL(finalURL);
			URLConnection urlConnection = url.openConnection();
			urlConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestParamStr() {
		return requestParamStr;
	}

	public void setRequestParamStr(String requestParamStr) {
		this.requestParamStr = requestParamStr;
	}

}
