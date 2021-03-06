package com.contact.exercise.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.contact.exercise.domain.CandidateResume;
import com.contact.exercise.exceptions.ServiceException;
import com.contact.exercise.service.CandidateInformationService;
import com.contact.exercise.service.utils.FileExtension;
import com.contact.exercise.service.utils.DocumentHelper;

@Service
public class CandidateInformationServiceImpl implements CandidateInformationService {

	private static Logger logger = LoggerFactory.getLogger(CandidateInformationServiceImpl.class);

	@Value("${apache.nlp.tokenized.model.skills.filename}")
	private String nlpSkillsModelFileName;

	@Value("${apache.nlp.tokenized.model.token.filename}")
	private String nlpTokenModelFileName;

	@Value("${contact.exercise.resources.path}")
	private String resourcesPath;

	public boolean save(CandidateResume candidateResume) throws ServiceException {
		boolean rtnFlag = false;
		logger.info("Inside save method.");
		logger.info("Resource Path:= " + (resourcesPath + nlpSkillsModelFileName));
		try {
			String mFileContent = null; 
			switch (FileExtension.fromString(candidateResume.getFileExtension())) {
			case DOC:
				mFileContent  = DocumentHelper.readFromDOC(candidateResume.getInputStream());
				break;
			case DOCX:
				mFileContent  = DocumentHelper.readFromDOCX(candidateResume.getInputStream());
				break;
			case PDF:
				mFileContent  = DocumentHelper.readFromPDF(candidateResume.getInputStream());
				break;
			default:
				
				break;
			}
			
			logger.info(ArrayUtils.toString(getCandidateSkillTokens(mFileContent)));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rtnFlag;
	}

	private String[] getCandidateSkillTokens(String candidateResumeData) throws InvalidFormatException, IOException {

		String[] candidateSkills = null;

		String tokens[] = getTokens(candidateResumeData);

		String skillsTokenizedModelFilePath = resourcesPath + nlpSkillsModelFileName;
		File skillsTokenizedModelFile = new File(skillsTokenizedModelFilePath);
		InputStream modelFile = new FileInputStream(skillsTokenizedModelFile);
		TokenNameFinderModel model = new TokenNameFinderModel(modelFile);
		NameFinderME nameFinder = new NameFinderME(model);
		Span nameSpans[] = nameFinder.find(tokens);

		candidateSkills = Span.spansToStrings(nameSpans, tokens);

		
		return candidateSkills;
	}

	private String[] getTokens(String phrase) {
		String tokens[] = null;
		try {

			InputStream modelFile = new FileInputStream(resourcesPath + nlpTokenModelFileName);

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
}
