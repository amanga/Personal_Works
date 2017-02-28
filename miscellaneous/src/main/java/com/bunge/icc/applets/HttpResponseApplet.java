package com.bunge.icc.applets;

import java.applet.Applet;
import java.util.logging.Logger;

public class HttpResponseApplet extends Applet{

	Logger logger = Logger.getLogger(HttpResponseApplet.class.getName());
	private static final long serialVersionUID = -1849427606217556381L;
	
	public void init(){
		logger.info("Inside Init...");
		this.getParameter("aiccURL");
		this.getParameter("queryString");
	}
	
	public void start(){
		logger.info("Inside Start...");
	}

	public void stop(){
		logger.info("Inside Stop...");
	}
	
	
}
