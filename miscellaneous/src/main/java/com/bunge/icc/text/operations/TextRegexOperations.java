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
		
		String[] arrayStrs = {
				"721,\"2017-06-21\",=\"07:48:50.010640\",=\"\",\"\",\" \",\"IFEU\",\"From IFEU To BNGE_CHG_GTP_EMMA_PROD\",\"A\",\"\",=\"\",\"\",=\"8170FID\",=\"\",=\"\",\"00000073344ORCH1\",\"00000614509POCH1\",=\"\",=\"\",\"O_UPDATED\",\"B\",5.0,0.0,5278357,205,20170800,\"FXXXXX\",\"FU\",\" \",0.0,407.1,0.0,\"\",\"\",\"LIM\",\"GFD\",\"\",\"\",\"\",=\"636\",=\"636\",\"D\",=\"\",\"\",=\"\",\"\",\"\",\"\""
				,"722,\"2017-06-21\",=\"07:48:50.010846\",=\"\",\"\",\"\",\"IFEU\",\"From BNGE_CHG_GTP_EMMA_PROD To CLIENT\",\"A\",\"\",=\"\",\"\",=\"8170FID\",=\"\",=\"\",\"00000073344ORCH1\",\"00000614509POCH1\",=\"\",=\"\",\"O_EXEC\",\"B\",100.0,5.0,5278357,205,20170800,\"FXXXXX\",\"FU\",\" \",0.0,407.1,0.0,406.7,2.0,\"ICE\",\"GFD\",\"SIM\",\"\",\"\",=\"636\",=\"636\",\"D\",=\"\",\"\",=\"\",\"\",\"\",\"\""
				,"723,\"2017-06-21\",=\"07:48:50.011061\",=\"\",\"\",\" \",\"IFEU\",\"From IFEU To BNGE_CHG_GTP_EMMA_PROD\",\"A\",\"\",=\"\",\"\",=\"8170FID\",=\"\",=\"\",\"00000073344ORCH1\",\"00000614509POCH1\",=\"\",=\"\",\"O_UPDATED\",\"B\",5.0,0.0,5278357,205,20170800,\"FXXXXX\",\"FU\",\" \",0.0,407.1,0.0,\"\",\"\",\"LIM\",\"GFD\",\"\",\"\",\"\",=\"636\",=\"636\",\"D\",=\"\",\"\",=\"\",\"\",\"\",\"\""
				,"724,\"2017-06-21\",=\"07:48:50.011256\",=\"\",\"\",\"\",\"IFEU\",\"From BNGE_CHG_GTP_EMMA_PROD To CLIENT\",\"A\",\"\",=\"\",\"\",=\"8170FID\",=\"\",=\"\",\"00000073344ORCH1\",\"00000614509POCH1\",=\"\",=\"\",\"O_EXEC\",\"B\",100.0,5.0,5278357,205,20170800,\"FXXXXX\",\"FU\",\" \",0.0,407.1,0.0,406.8,1.0,\"ICE\",\"GFD\",\"SIM\",\"\",\"\",=\"636\",=\"636\",\"D\",=\"\",\"\",=\"\",\"\",\"\",\"\""
				,"725,\"2017-06-21\",=\"07:48:50.011318\",=\"\",\"\",\"\",\"IFEU\",\"From EMMA To CLIENT\",\"A\",\"Iceberg+ : Display Qty: 5, Max Display Qty: 0, Start: Not set, End: 20170621 18:30:00 +0100s\",=\"-11051\",\"EMMA\",=\"8170FID\",=\"\",=\"\",\"00000073344ORCH1\",\"\",=\"\",=\"\",\"NEW ORDER\",\"B\",100.0,5.0,5278357,205,20170714,\"FXXXXX\",\"\",\"\",0.0,407.1,0.000000,0.0,0.0,\"ICE\",\"\",\"SIM\",\"\",\"\",=\"\",=\"\",\"\",=\"\",\"\",=\"\",\"\",\"\",\"\" "
				,"5920,\"2017-06-16\",=\"13:09:24.750857\",=\",\",\",\"IFCA\",\"From EMMA To CLIENT\",\"A\",\"Iceberg+ : Display Qty: 1, Max Display Qty: 1, Start: Not set, End: 20170616 14:50:00 -0400s\",=\"-11051\",\"EMMA\",=\"MCWSPD\",=\",=\",\"00000070216ORCH1\",\",=\",=\",\"NEW ORDER\",\"B\",25.0,1.0,5278422,36,20170714,\"FMXXXX\",\",\",0.0,25.1,0.000000,0.0,0.0,\"ICE\",\",\"SIM\",4,\",=\",=\",\",=\",\",=\",\",\",\""
		};
		
		for(String data : arrayStrs){
			System.out.println(normalizeString(data).replaceAll("\"", "").replaceAll(REPLACEABLE_INBETWEEN_COMMA, ""));
		}
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
