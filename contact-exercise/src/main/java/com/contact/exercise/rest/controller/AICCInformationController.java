package com.contact.exercise.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aiccinfo")
public class AICCInformationController {

	@RequestMapping(method = RequestMethod.GET)
	public String getUserInformation(@RequestParam(value="session_id", required=false) String session_id) throws Exception{
		System.out.println(" session_id:= "+ session_id);
		String rtnStr = "Error=0" +
				System.getProperty("line.separator")+"Aicc_data=[core]" +
				System.getProperty("line.separator")+"Student_ID=1" +
				System.getProperty("line.separator")+"Student_Name=student_name" +
				System.getProperty("line.separator")+"Score=0" +
				System.getProperty("line.separator")+"Time=00:00:00" +
				System.getProperty("line.separator")+"" ;
		return rtnStr;
	}
}
