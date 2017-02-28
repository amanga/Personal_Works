package com.contact.exercise.service.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MicrosoftDocumentHelper {
	private static Logger logger = LoggerFactory
			.getLogger(MicrosoftDocumentHelper.class);

	@SuppressWarnings("resource")
	public static String readDocumentIntoString(InputStream inputStream,
			String fileExtension) {
		StringBuilder sBuilder = new StringBuilder();
		try {
			if (fileExtension.toLowerCase().endsWith(".doc")) {
				logger.debug("Inside DOC...");
				HWPFDocument document = new HWPFDocument(inputStream);
				WordExtractor wordExtractor = new WordExtractor(document);
				String[] wordParagraphText = wordExtractor.getParagraphText();
				for (int i = 0; i < wordParagraphText.length; i++) {
					if (wordParagraphText[i] != null)
						sBuilder.append(wordParagraphText[i]);
				}
			} else if (fileExtension.toLowerCase().endsWith(".docx")) {
				logger.debug("Inside DOCX...");
				XWPFDocument document = new XWPFDocument(inputStream);
				XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(
						document);
				sBuilder.append(xwpfWordExtractor.getText());
			} else {
				System.out.println("Inside no support format...");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception while parsing the Microsoft doucment. "+ ex.getLocalizedMessage());
		}

		return sBuilder.toString();
	}
}
