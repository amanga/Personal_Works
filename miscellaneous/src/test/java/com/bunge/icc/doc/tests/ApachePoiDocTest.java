package com.bunge.icc.doc.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ApachePoiDocTest {

	String filePath = "";
	String[] listOfFiles = { "Jitendra_java.docx",
			"Leroy_Liu_Java_Developer.doc", "LaiWang_Java_Developer_Resume.doc" };

	@Before
	public void init() {
		System.out.println("Init..");
	}

	@SuppressWarnings("resource")
	@Test
	public void testLoadingDoc() {
		System.out.println("Loading Document.");
		for (String fileName : listOfFiles) {
			System.out.println("----------------------------------");
			System.out.println("File Name: "+fileName);
			
			filePath = getClass().getClassLoader().getResource(fileName)
					.getPath();
			try {
				File file = new File(filePath);
				FileInputStream fInputStrm = new FileInputStream(
						file.getAbsolutePath());

				if (filePath.toLowerCase().endsWith(".doc")) {
					System.out.println("Inside DOC...");
					HWPFDocument document = new HWPFDocument(fInputStrm);
					WordExtractor wordExtractor = new WordExtractor(document);
//					System.out.println(wordExtractor.getText());
					String[] fileData = wordExtractor.getParagraphText();
					for (int i = 0; i < fileData.length; i++) {
						if (fileData[i] != null)
							System.out.println(fileData[i]);
					}
				} else if (filePath.toLowerCase().endsWith(".docx")) {
					System.out.println("Inside DOCX...");
					XWPFDocument document = new XWPFDocument(fInputStrm);
					XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(
							document);
					System.out.println(xwpfWordExtractor.getText());
				}else {
					System.out.println("Inside no support format...");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("----------------------------------");
		} // FOR END: files loop...
	}

	@After
	public void destroy() {
		System.out.println("Destroy..");
	}
}
