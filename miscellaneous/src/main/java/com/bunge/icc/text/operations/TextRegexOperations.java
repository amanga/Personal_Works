package com.bunge.icc.text.operations;

import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.util.StringUtils;

public class TextRegexOperations {
	
	private String patternNetworkAccountPrefixedTitle = "(?iu).*SVC.*|.*WKS.*|.*SRV.*|.*SPL.*|^x.*";
	private Pattern PATTERN_NETWORK_ACCOUNT_PREFIXED_TITLE;
	
	
	@Test
	public void testRegex(){
		String[] userNames = {"ABSXJDAVIS","SPLKAYAL","CAMWKSRD"};
		for(String userName : userNames){
			System.out.println(userName +":="+ isNetworkAccountPrefixed(userName));
		}
		
	}
	private boolean isNetworkAccountPrefixed(String title) {		
		if(StringUtils.isEmpty(title))
			return false;
		
		if(PATTERN_NETWORK_ACCOUNT_PREFIXED_TITLE == null) {
			
			PATTERN_NETWORK_ACCOUNT_PREFIXED_TITLE = Pattern.compile(patternNetworkAccountPrefixedTitle);
		}
		
		return PATTERN_NETWORK_ACCOUNT_PREFIXED_TITLE.matcher(title).matches();
	}

}
