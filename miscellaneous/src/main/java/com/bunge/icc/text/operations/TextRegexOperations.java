package com.bunge.icc.text.operations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TextRegexOperations {

	private String patternNetworkAccountPrefixedTitle = "(?iu).*SVC.*|.*WKS.*|.*SRV.*|.*SPL.*|^X.*|^APP-.*";
	private Pattern PATTERN_NETWORK_ACCOUNT_PREFIXED_TITLE;
	
	private String patternUseraccountCheck = "(?i)\\bH\\b|\\bZ\\b";
	private Pattern PATTERN_USERACCOUNT_IGNORE_MATCH;

//	@Test
	public void testRegex() {
		System.out.println("Network account condition check");
		String[] userNames = { "ABSXJDAVIS", "SPLKAYAL", "CAMWKSRD", "XJDAVIS","DAPP-XYX-SIA","amangax","APP-23JAS"};
		for (String userName : userNames) {
			System.out.println(userName + ":=" + isNetworkAccountPrefixed(userName));
		}
	}
	
//	@Test
	public void testUseraccountIgnoreMatchRegex() {
		System.out.println("Useraccount condition check");
		String[] useraccountValue= { "A", "H", "B","AHA", "Z","h","z"};
		for (String useraccount: useraccountValue) {
			System.out.println(useraccount + ":=" + isUseraccountIgnoreMatch(useraccount));
		}
	}
	
//	@Test
	public void testJaroWinklerDistance(){
		System.out.println(StringUtils.trim("Matthew E Hall"));
		System.out.println("Matched Percentage: "+ getJaroWinklerDistance("MatthewEHall","MatthewHall"));
	}

	private boolean isNetworkAccountPrefixed(String title) {
		if (StringUtils.isEmpty(title))
			return false;

		if (PATTERN_NETWORK_ACCOUNT_PREFIXED_TITLE == null) {

			PATTERN_NETWORK_ACCOUNT_PREFIXED_TITLE = Pattern.compile(patternNetworkAccountPrefixedTitle);
		}

		return PATTERN_NETWORK_ACCOUNT_PREFIXED_TITLE.matcher(title).matches();
	}

	private double getJaroWinklerDistance(String firstStr, String secondStr) {

		if (!StringUtils.isBlank(firstStr) && !StringUtils.isBlank(secondStr)) {
			return StringUtils.getJaroWinklerDistance(StringUtils.trim(firstStr), StringUtils.trim(secondStr));
		}

		return 0.0;
	}
	
	private boolean isUseraccountIgnoreMatch(String value){
		if (StringUtils.isEmpty(value))
			return false;

		if (PATTERN_USERACCOUNT_IGNORE_MATCH == null) {

			PATTERN_USERACCOUNT_IGNORE_MATCH = Pattern.compile(patternUseraccountCheck);
		}

		return PATTERN_USERACCOUNT_IGNORE_MATCH.matcher(value).matches();
	}
	
	String REGEX_PATTERN ="\",|\"[^\"]*\",|(\\d+[.\\d]+)|\\d";
	String REGEX_ATLEAST_TWO_COMMAS = ",{2,}";
	
	String REPLACEABLE_INBETWEEN_COMMA ="_##_";
	String COMMA = ",";
	
	@Test
	public void containNumbersWithinQuotes(){
		String originalData = "5920,\"2017-06-16\",=\"13:09:24.750857\",=\",\",\",\"IFCA\",\"From EMMA To CLIENT\",\"A\",\"Iceberg+ : Display Qty: 1, Max Display Qty: 1, Start: Not set, End: 20170616 14:50:00 -0400s\",=\"-11051\",\"EMMA\",=\"MCWSPD\",=\",=\",\"00000070216ORCH1\",\",=\",=\",\"NEW ORDER\",\"B\",25.0,1.0,5278422,36,20170714,\"FMXXXX\",\",\",0.0,25.1,0.000000,0.0,0.0,\"ICE\",\",\"SIM\",4,\",=\",=\",\",=\",\",=\",\",\",\"";
		System.out.println(normalizeString(originalData).replaceAll("\"", "").replaceAll(REPLACEABLE_INBETWEEN_COMMA, ""));
	}
	
	private String normalizeString(String inputStr){
		StringBuilder strBuilder = new StringBuilder();
		
//		String data = inputStr.replace("=", "");
		String data = inputStr;
		Pattern pattern = Pattern.compile(REGEX_PATTERN);
		Matcher matcher = pattern.matcher(data);
		
		Pattern commaPattern = Pattern.compile(REGEX_ATLEAST_TWO_COMMAS);
		
		while(matcher.find()){
			String tmpVal = matcher.group();
			
			//check if tmpVal lastindex value contains comma, if not add
			if((tmpVal!=null)){
				
				Matcher commaMatcher = commaPattern.matcher(tmpVal);
				if(!commaMatcher.matches()){
					if((tmpVal.substring(0, tmpVal.length()-1).contains(COMMA))){
						 tmpVal = tmpVal.substring(0, tmpVal.length()-1).replaceAll(COMMA, REPLACEABLE_INBETWEEN_COMMA);
					}
				}
				//if tmpval lastIndexed String is not comma
				if(!tmpVal.substring(tmpVal.length()-1).contains(COMMA)){
					tmpVal = tmpVal +",";
				}
			}
			
			strBuilder.append(tmpVal);
		}
		
		return strBuilder.toString();
	}
}
