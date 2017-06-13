package com.bunge.icc.text.operations;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TextRegexOperations {

	private String patternNetworkAccountPrefixedTitle = "(?iu).*SVC.*|.*WKS.*|.*SRV.*|.*SPL.*|^X.*|^APP-.*";
	private Pattern PATTERN_NETWORK_ACCOUNT_PREFIXED_TITLE;
	
	private String patternUseraccountCheck = "(?i)\\bH\\b|\\bZ\\b";
	private Pattern PATTERN_USERACCOUNT_IGNORE_MATCH;

	@Test
	public void testRegex() {
		System.out.println("Network account condition check");
		String[] userNames = { "ABSXJDAVIS", "SPLKAYAL", "CAMWKSRD", "XJDAVIS","DAPP-XYX-SIA","amangax","APP-23JAS"};
		for (String userName : userNames) {
			System.out.println(userName + ":=" + isNetworkAccountPrefixed(userName));
		}
	}
	
	@Test
	public void testUseraccountIgnoreMatchRegex() {
		System.out.println("Useraccount condition check");
		String[] useraccountValue= { "A", "H", "B","AHA", "Z","h","z"};
		for (String useraccount: useraccountValue) {
			System.out.println(useraccount + ":=" + isUseraccountIgnoreMatch(useraccount));
		}
	}
	
	@Test
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
}
