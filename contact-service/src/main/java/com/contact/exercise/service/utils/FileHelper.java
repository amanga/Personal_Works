package com.contact.exercise.service.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileHelper {

	@SuppressWarnings("resource")
	public static String readFileIntoString(String filePath){
		String rtnString = null;
		try{
			StringBuilder sBuilder = new StringBuilder();
			
			File f = new File(filePath);
			if(f.exists()){
				BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				String line = buf.readLine();
				while(line != null){
					sBuilder.append(line);
					line = buf.readLine();
				}
				rtnString = sBuilder.toString();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return rtnString;
	}
	
}
