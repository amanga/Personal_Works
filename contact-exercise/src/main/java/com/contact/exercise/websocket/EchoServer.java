package com.contact.exercise.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ServerEndpoint("/echo")
public class EchoServer {
	private static Logger logger = LoggerFactory.getLogger(EchoServer.class);
	
	@OnOpen
	public void onOpne(Session session){
		logger.info(session.getId() + " has opened a connection.");
		try{
			session.getBasicRemote().sendText("Connection Established.");
		}catch(IOException io){
			logger.error(io.getMessage());
		}
	}
	
	@OnMessage
	public void onMessage(String message, Session session){
		logger.info("Message from " + session.getId() +":" + message);
		
		try{
			session.getBasicRemote().sendText("out>>"+ message);
		}catch(IOException io){
			logger.error(io.getMessage());
		}
	}
	
	@OnClose
	public void onClose(Session session){
		logger.info("Session # "+session.getId() +" has ended.");
	}
}
