package com.bunge.app.utils;

public class EmployeeJobConstants {

	public static String SERVICE_URL = "https://api012.successfactors.eu/odata/v2";
	
	public static String ENTITY_SET_NAME = "EmpJob";
	
	public static String SELECT_QUERY_STR = "endDate,startDate,eventReason,eventNav,emplStatus,eventReasonNav,company,event,emplStatusNav,userId";

	public static String FILTER_QUERY_STR = "eventNav/externalCode eq '26'";

	public static final String TOPRECORDS_QUERY_STR = "10";
	
	public static String EXPAND_QUERY_STR = "$expand=emplStatusNav,eventReasonNav,eventNav";
	
	public static String METADATA_QUERY_STR = "$metadata";

	public static String FORMAT_QUERY_STR = "$format=json";

	public static String USERNAME = "SFAPI-Bunge";

	public static String PASSWORD = "Succ3$$F@ct0r$!!";

	public static String COMPANY_ID = "BungeTest";

	public static final String APPLICATION_JSON = "application/json";

	public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";

	public static final String HTTP_HEADER_ACCEPT = "Accept";

	public static final String SEPARATOR = "/";

	public static final String HTTP_METHOD_PUT = "PUT";

	public static final String HTTP_METHOD_POST = "POST";

	public static final String HTTP_METHOD_GET = "GET";

	private static final String HTTP_METHOD_DELETE = "DELETE";

	public static final String APPLICATION_XML = "application/xml";

	
}
