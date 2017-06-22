package com.bunge.icc.http.webextract;

public class UrlHelper {

	public static String constructURL(String urlStr, String requestParamStr) {
		String rtnURL = null;
		if (urlStr.contains("?")) {
			if (urlStr.substring(urlStr.length()).equalsIgnoreCase("&")) {
				rtnURL = urlStr + requestParamStr;
			} else {
				rtnURL = urlStr + "&" + requestParamStr;
			}
		} else {
			rtnURL = urlStr;
			if((requestParamStr!=null)&&(!requestParamStr.isEmpty())){			
				rtnURL = rtnURL + "?" + requestParamStr;
			}
		}
		return rtnURL;
	}
	
}
