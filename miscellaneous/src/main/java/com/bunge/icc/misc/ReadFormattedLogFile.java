package com.bunge.icc.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFormattedLogFile {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) {
		ReadFormattedLogFile rFile = new ReadFormattedLogFile();
		try {
			List<String> fixMessages = rFile.readFile("TT_Test.txt");
			System.out.println(fixMessages.size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> readFile(String filePath) throws FileNotFoundException, IOException{
		
		List<String> listOfFixMessages = new ArrayList<String>();
		File file = new File(getClass().getClassLoader().getResource(filePath).getFile());
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
		    for(String line; (line = br.readLine()) != null; ) {

		    	if((line!=null)&&(line.length()>1)){
		    		listOfFixMessages.add(line);
		    	}

		    }
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return listOfFixMessages;
	}

}
