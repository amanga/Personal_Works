package com.bunge.trading.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	@Value("${bunge.welcome.message}")
	private String message = "Spring Boot!";
	
	@RequestMapping("/")
	public String welcome(Map<String, Object> model){
		System.out.println("Helooooo");
		model.put("message", this.message);
		return "welcome";
	}
}