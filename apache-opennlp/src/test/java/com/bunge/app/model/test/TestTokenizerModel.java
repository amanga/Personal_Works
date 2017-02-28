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

//	@Test
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

	@Test
	public void testNameFinderTrainer(){
		String sourceFileName = "Skills_From_Resume.txt";
		String outputFileName = "en-skills_generated.bin";
		String nameFinder = "Skill";
//		generateTrainerDocument(sourceFileName, outputFileName, nameFinder);
		
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Over 8 years experience in Software Development life Cycle (SDLC) including Requirement Analysis, Design, prototyping, development, Integration, Documentation and testing of applications using Java/J2EE Technologies for various client/server and web applications");
		strBuilder.append("Experienced in Agile, RUP (Rational Unified Process), software development methodologies and knowledge of UML Diagrams for Object Oriented Design");
		strBuilder.append("Hands on experience in solving software design issues by applying design patterns including Singleton Pattern, Business Delegator Pattern, Controller Pattern, MVC Pattern, Factory Pattern, Abstract Factory Pattern, DAO Pattern and Template Pattern.");
		strBuilder.append("Experienced in creative and effective front-end development using JSP, JavaScript, JQuery, HTML 5, Hibernate, Ajax and CSS.");
		strBuilder.append("Aced the persistent service, Hibernate and JPA for object mapping with database. Configured xml files for mapping and hooking it with other frameworks like Spring, Struts.");
		strBuilder.append("Extensive knowledge of database such as Postgresql, Oracle, Microsoft SQL Server and Mysql.");
		strBuilder.append("Strong experience in database design, writing complex SQL Queries and Stored Procedures.");
		strBuilder.append("Experienced in development of logging standards and mechanism based on Log4J.");
		strBuilder.append("Strong working experience in insurance and investment applications.  Excellent communication skills to deal with people at all levels. Self-motivated team player with good Analytical, Logical and Problem Solving ability.");
		String tokens[] = getTokens(strBuilder.toString());
/*		for(String token : tokens){
			System.out.println(token);
		}*/
		testSkillNameFinderModel(strBuilder,outputFileName);
		
	}
	
	public void testSkillNameFinderModel(StringBuilder strBuilder, String tokenNameFinderModelFileName){
		System.out.println("SkillNameFinderModel");
		try {
			
			String tokens[] = getTokens(strBuilder.toString());
			System.out.println("PATH:= "
					+ getClass().getClassLoader().getResource(
							tokenNameFinderModelFileName));
			
			InputStream modelFile = new FileInputStream(
					(getClass().getClassLoader().getResource(
							tokenNameFinderModelFileName).getFile()));

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
	
	@SuppressWarnings("deprecation")
	public void generateTrainerDocument(String sourceFileName, String generatedFileName,String type) {
		System.out.println("GenerateTrainerDocument");
		Charset charset = Charset.forName("UTF-8");
		ObjectStream<NameSample> sampleStream = null;
		BufferedOutputStream modelOut = null;
		try {
			InputStream strm =new FileInputStream(getClass().getClassLoader()
					.getResource(sourceFileName).getFile()); 
			ObjectStream<String> lineStream = new PlainTextByLineStream(strm,
					charset);
			sampleStream = new NameSampleDataStream(lineStream);

			TokenNameFinderModel model = null;
			Map<String, Object> mp = new HashMap<String, Object>();

			model = NameFinderME.train("en", type , sampleStream,
					new HashMap<String, Object>());
			System.out.println(getClass().getClassLoader()
					.getResource("").getPath()+generatedFileName);
			modelOut = new BufferedOutputStream(new FileOutputStream(getClass().getClassLoader()
					.getResource("").getPath()+generatedFileName));
			model.serialize(modelOut);

			sampleStream.close();
			modelOut.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanup() {
		System.out.println("Destroy Method");
	}
}
