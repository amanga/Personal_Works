package com.bunge.itext.tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;

public class ParsingPdfTest {
	
	@Before
	public void setup(){
		System.out.println("Init Method");
	}
	
	@Test
	public void extractPDFText(){
		String fName; 
//		= this.getClass().getResource("").getPath();
		fName = "C:/Users/camanga/Downloads/jta-1_2-spec_v2.pdf";
		try {
			PdfReader reader = new PdfReader(fName);
			PdfDictionary pdfDic =  reader.getPageN(1);
			 PdfObject pdfOjbect =  pdfDic.get(PdfName.MEDIABOX);
			for(PdfName pdfName : pdfDic.getKeys()){
					System.out.println(pdfName.toString());
			}
			/*for(int pg =1; pg<=reader.getNumberOfPages(); pg++){
				
				String text = new String(PdfTextExtractor.getTextFromPage(reader, pg).getBytes("UTF-8"),"UTF-8");
				System.out.println(text);
				System.out.println(">>>> page -"+pg);
			}*/
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@After
	public void cleanup(){
		System.out.println("Destroy Method");
	}

}
