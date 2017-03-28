package com.contact.exercise.service.utils;

import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class DocumentHelper {
	private static Logger logger = LoggerFactory.getLogger(DocumentHelper.class);

	@SuppressWarnings("resource")
	public static String readFromDOC(InputStream inputStream) {
		StringBuilder sBuilder = new StringBuilder();
		HWPFDocument document = null;
		try {
			logger.debug("Inside DOC...");
			document = new HWPFDocument(inputStream);
			WordExtractor wordExtractor = new WordExtractor(document);
			String[] wordParagraphText = wordExtractor.getParagraphText();
			for (int i = 0; i < wordParagraphText.length; i++) {
				if (wordParagraphText[i] != null)
					sBuilder.append(wordParagraphText[i]);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception while parsing the Microsoft doucment. " + ex.getLocalizedMessage());
		}finally {
			document = null; 
		}

		return sBuilder.toString();
	}

	@SuppressWarnings("resource")
	public static String readFromDOCX(InputStream inputStream) {
		StringBuilder sBuilder = new StringBuilder();
		XWPFDocument document = null;
		try {
			logger.debug("Inside DOCX...");
			document = new XWPFDocument(inputStream);
			XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(document);
			sBuilder.append(xwpfWordExtractor.getText());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception while parsing the Microsoft doucment. " + ex.getLocalizedMessage());
		} finally {
			document = null;
		}
		return sBuilder.toString();
	}

	public static String readFromPDF(InputStream inputStream) {
		StringBuilder sBuilder = new StringBuilder();
		try {
			logger.debug("Inside PDF...");
			PdfReader reader = new PdfReader(inputStream);
			for (int pg = 1; pg <= reader.getNumberOfPages(); pg++) {
				String text = new String(PdfTextExtractor.getTextFromPage(reader, pg).getBytes("UTF-8"), "UTF-8");
				sBuilder.append(text);
			}
			reader.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception while parsing the Microsoft doucment. " + ex.getLocalizedMessage());
		}
		return sBuilder.toString();
	}
}
