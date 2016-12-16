package com.bunge.app.model.test;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTokenizerModel {

	@Before
	public void setup() {
		System.out.println("Init Method");
	}

	private String[] getTokens(String phrase) {
		String tokens[] = null;
		try {
			InputStream modelFile = new FileInputStream((getClass()
					.getClassLoader().getResource("en-token.bin").getFile()));
			TokenizerModel model = new TokenizerModel(modelFile);

			Tokenizer tokenizer = new TokenizerME(model);
			tokens = tokenizer.tokenize(phrase);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tokens;
	}

//	@Test
	public void testTokenizerModel() {
		System.out.println("TokenizerModel");
		String tokens[] = getTokens("The tokenizer offers two tokenize methods both expect an input String object which contains the untokenized text");
		System.out.println(ArrayUtils.toString(tokens));
	}

//	@Test
	public void testTokenNameFinderModel() {
		System.out.println("NameFinderModel");
		try {
			String tokens[] = getTokens("Pierre Vinken , 61 years old , will join the board as a nonexecutive director Nov. 29 . Mr . Vinken is chairman of Elsevier N.V. , the Dutch publishing group . Rudolph Agnew , 55 years old and former chairman of Consolidated Gold Fields PLC , was named a director of this British industrial conglomerate .");

			InputStream modelFile = new FileInputStream(
					(getClass().getClassLoader().getResource(
							"en-ner-person.bin").getFile()));

			TokenNameFinderModel model = new TokenNameFinderModel(modelFile);

			NameFinderME nameFinder = new NameFinderME(model);

			Span nameSpans[] = nameFinder.find(tokens);

			System.out.println(ArrayUtils.toString(Span.spansToStrings(
					nameSpans, tokens)));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testTokenNameFinderModel_Java() {
		System.out.println("NameFinderModel");
		try {
			String tokens[] = getTokens("Used to define a block of statements for a block defined previously by the try keyword. The finally block is executed after execution exits the try block and any associated catch clauses regardless of whether an exception was thrown or caught, or execution left method in the middle of the try or catch blocks using the return keyword.");
			

			InputStream modelFile = new FileInputStream(
					(getClass().getClassLoader().getResource(
							"en-ner-java_generated.bin").getFile()));

			TokenNameFinderModel model = new TokenNameFinderModel(modelFile);

			NameFinderME nameFinder = new NameFinderME(model);

			Span nameSpans[] = nameFinder.find(tokens);

			System.out.println(ArrayUtils.toString(Span.spansToStrings(
					nameSpans, tokens)));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testTokenNameFinderTrainer() {
		System.out.println("TokenNameFinderTrainer");
		Charset charset = Charset.forName("UTF-8");
		ObjectStream<NameSample> sampleStream = null;
		BufferedOutputStream modelOut = null;
		try {
			ObjectStream<String> lineStream = new PlainTextByLineStream(
					new FileInputStream(getClass().getClassLoader()
							.getResource("JavaKeyWordDetails.txt").getFile()),
					charset);
			sampleStream = new NameSampleDataStream(lineStream);

			TokenNameFinderModel model = null;
			Map<String, Object> mp = new HashMap<String, Object>();

			model = NameFinderME.train("en", "java", sampleStream,
					new HashMap<String, Object>());
			
			modelOut = new BufferedOutputStream(new FileOutputStream(getClass().getClassLoader()
					.getResource("").getPath()+"en-ner-java_generated.bin"));
			model.serialize(modelOut);

			sampleStream.close();
			modelOut.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@After
	public void cleanup() {
		System.out.println("Destroy Method");
	}
}
