package com.bunge.icc.misc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ProcessHugeFile {

	public static void main(String args[]) throws IOException{
		FileInputStream fInputStrm = null;
		Scanner scanner = null;
		try{
			fInputStrm = new FileInputStream("C:/temp/hris-data-aggregation_2.log");
			scanner = new Scanner(fInputStrm);
			while(scanner.hasNextLine()){
				System.out.println(scanner.nextLine());
			}
		}catch(Exception ex){
			
		}finally{
			if(fInputStrm != null)
				fInputStrm.close();
			
			if(scanner != null)
			scanner.close();
		}
	}
}
