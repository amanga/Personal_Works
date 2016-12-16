package com.bunge.icc.text.operations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextOperations {
	
	public String replaceCommaInQuotes(String inputStr, String regexPattern, char substitute){
		String rtnStr = inputStr;
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher= pattern.matcher(inputStr);
		int index = 0;
		
		StringBuilder strBuilder = new StringBuilder(inputStr);
		
		if(matcher.find()){
			index = matcher.start();
			if(index>0){
				strBuilder.setCharAt(index, substitute);
				rtnStr = replaceCommaInQuotes(strBuilder.toString(),regexPattern,substitute);
			}
		}
		return rtnStr;
	}
	
	public String replaceAValue(String inputStr, String regex, String substitue){
		return inputStr.replaceAll(regex,substitue);
	}
	
	public static void main(String args[]){
		
		/*String inputStr = "==\"20161013-14:02:56.034627\",=\"\",\"TO CME\",=\"Alex.Rodionov\",=\"\",=\"BUNGE0001\",=\"W37\",=\"216\",\"Y\",\"D\",4,1,\"\",=\"00000021388UZCH1\",\"00000002479UOCH1\",\"\",\"ZCZ6\",\"\",=\"UO000003TR001CH\",=\"\",1,1,\"\",\"\",1,0,\"\",\"\",\"\",\"US,IL\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"";
		String strPattern = ",\\w{1,}\"";		
		int index = 0;
		
		Pattern pattern = Pattern.compile(strPattern);
		Matcher matcher= pattern.matcher(inputStr);
		
		System.out.println(strPattern);
		StringBuilder strBuilder = new StringBuilder(inputStr);
		
		if(matcher.find()){
			index = matcher.start();
			if(index>0){
				strBuilder.append(inputStr.substring(0, index-1)).append(" ");
				strBuilder.append(inputStr.substring(index+1, inputStr.length()));
				strBuilder.setCharAt(index, ' ');
			}
		}
		
		System.out.println(strBuilder.toString());*/
		
		String strPattern = ",\\w{1,}\"";
		char substitute = ' ';
		
		String inputStrs[] = {
				"==\"20161013-14:02:56.034627\",=\"\",\"TO CME\",=\"Alex.Rodionov\",=\"\",=\"BUNGE0001,HELLO\",=\"W37\",=\"216\",\"Y\",\"D\",4,1,\"\",=\"00000021388UZCH1\",\"00000002479UOCH1\",\"\",\"ZCZ6\",\"\",=\"UO000003TR001CH\",=\"\",1,1,\"\",\"\",1,0,\"\",\"\",\"\",\"US,IL\",\"US,1124\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\""
				,"38,\"2016-10-04\",=\"17:52:07.675681\",=\"\",\"\",\"\",\"IFUS\",\"From EMMA To CLIENT\",\"R\",\"GS_marketIsClosed\",=\"-11051\",\"EMMA\",=\"UNALLOC\",=\"\",=\"\",\"00000000562ORCH1\",\"\",=\"\",=\"\",\"NEW ORDER\",\"B\",2.0,0.0,5029567,24,20170228,\"FXXXXX\",\"\",\"\",0.0,25.0,0.000000,0.0,0.0,\"LIM\",\"\",\"\",4,\"\",=\"\",=\"\",\"\",=\"\",\"\",=\"\",\"\",\"\",\"\""
				
		};
		
		
		TextOperations testInstance = new TextOperations();
		String outputStr = null;
		for(String inputStr :inputStrs ){
			outputStr = testInstance.replaceCommaInQuotes(inputStr,strPattern, substitute);
			outputStr = testInstance.replaceAValue(outputStr, "\"", "");
			outputStr = testInstance.replaceAValue(outputStr, "=", "");
			System.out.println(outputStr);
			outputStr = null;
		}
	}
	
	
	
}
