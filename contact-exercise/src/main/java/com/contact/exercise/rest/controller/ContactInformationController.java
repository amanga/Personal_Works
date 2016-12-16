package com.contact.exercise.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.contact.exercise.domain.ContactInformation;
import com.contact.exercise.service.ContactInformationService;

@RestController
@RequestMapping("/contacts")
public class ContactInformationController {

	private static Logger logger = LoggerFactory.getLogger(ContactInformationController.class);
	
	@Autowired
	private ContactInformationService contactInformationService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<ContactInformation> getContacts() throws Exception{
		logger.debug("get all Contact information");
		return contactInformationService.get();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void save(@RequestBody ContactInformation contactInfo)throws Exception{
		logger.debug("createId  is null :="+ StringUtils.isEmpty(contactInfo.getId()));
		logger.debug(contactInfo.toString());
		if(StringUtils.isEmpty(contactInfo.getId())){
			logger.debug("Save New Contact information");
			contactInformationService.save(contactInfo);
		}else{
			logger.debug("Update Existing Contact information");
			contactInformationService.update(contactInfo);
		}
	}
	
}
