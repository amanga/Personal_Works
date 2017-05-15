package com.bunge.log4j.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class BungeLog4jAppender extends AppenderSkeleton{

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void append(LoggingEvent event) {
		if(event.getMessage().toString().contains("Exception")){
			System.out.println(">>>>>>>"+event.getMessage().toString());
		}
	}

}
