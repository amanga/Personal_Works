package com.contact.exercise.rest.controller;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.contact.exercise.domain.CandidateResume;
import com.contact.exercise.service.CandidateInformationService;

@RestController
@RequestMapping("/uploadfile")
public class UploadController {
	private static Logger logger = LoggerFactory
			.getLogger(UploadController.class);
	
	@Autowired
	private CandidateInformationService candidateInformationService;

	@RequestMapping(method = RequestMethod.GET)
	public void getContacts() throws Exception {
		logger.debug("get all list documents uploaded by client");
	}

	@RequestMapping(method = RequestMethod.POST)
	public void uploadFile(@RequestParam("file") MultipartFile filePart,
			@RequestParam("firstname") String firstName,
			@RequestParam("lastname") String lastName,
			@RequestParam("email") String email,
			@RequestParam("clientid") String clientId)throws Exception {
		
		logger.info("Upload File Controller.");
		String originalFileName = filePart.getOriginalFilename();
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
		logger.info("File Name :="+ originalFileName);
		logger.info("UPloaded File Extension :=" + fileExtension);
		logger.info("File Size :="+filePart.getSize());
		logger.info("File Content Type :="+filePart.getContentType());
		
		logger.info("First Name :="+ firstName);
		logger.info("Last Name :="+ lastName);
		logger.info("email :="+ email);
		logger.info("Client Id :="+clientId);
		
		InputStream inputStream = filePart.getInputStream();
		
		CandidateResume candidateResume = new CandidateResume();
		candidateResume.setFirstName(firstName);
		candidateResume.setLastName(lastName);
		candidateResume.setEmail(email);
		candidateResume.setInputStream(inputStream);
		candidateResume.setFileExtension(fileExtension);
		
		candidateInformationService.save(candidateResume);
	}
	
	
	

}
