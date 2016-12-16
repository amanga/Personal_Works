package com.bunge.icc.tibco.adapter.util;

import com.tibco.sdk.MProperties;

public class AdapterPropertyUtil {

	public static String getString(MProperties properties, String appName, String propertyName) {
		return properties.getString(getPath(appName, propertyName));
	}
	
	public static String getString(MProperties properties, String appName, String componentName, String propertyName) {
		return properties.getString(getPath(appName, componentName, propertyName));
	}
	
	private static String getPath(String appName, String propertyName) {
		return getPath(appName, null, propertyName);
	}
	
	private static String getPath(String appName, String componentName, String propertyName) {
		
		return componentName != null 
					? String.format("/%s/extendedProperties/%s/%s", appName, componentName, propertyName)
						: String.format("/%s/extendedProperties/%s", appName, propertyName);
	}
}
