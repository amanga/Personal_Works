package com.bunge.icc.fix.adapter.initiator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.DefaultMessageFactory;
import quickfix.DoNotSend;
import quickfix.Field;
import quickfix.FieldNotFound;
import quickfix.FileStoreFactory;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.Message;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.RejectLogon;
import quickfix.ScreenLogFactory;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.UnsupportedMessageType;
import quickfix.fix44.MessageCracker;

public class FIXInitiatorApplication implements Application {

	private static volatile SessionID sessionID;

	@Override
	public void onCreate(SessionID sessionID) {
	    System.out.println("OnCreate");
	}

	@Override
	public void onLogon(SessionID sessionID) {
	    System.out.println("OnLogon");
	    FIXInitiatorApplication.sessionID = sessionID;
	}

	@Override
	public void onLogout(SessionID sessionID) {
	    System.out.println("OnLogout");
//	    FIXInitiatorApplication.sessionID = null;
	}

	@Override
	public void toAdmin(Message message, SessionID sessionID) {
	    System.out.println("ToAdmin");
	}

	@Override
	public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
	    System.out.println("FromAdmin");
//	    printHeaders(message);
	}

	@Override
	public void toApp(Message message, SessionID sessionID) throws DoNotSend {
	    System.out.println("ToApp: " + message);
		
	}

	@Override
	public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
	    System.out.println("FromApp");
//	    crack(message, sessionID);
	}
	
	private void printHeaders(Message message) throws ConfigError{
		DataDictionary dd = new DataDictionary("FIX44.xml");
		Iterator<Field<?>> fieldItr = message.getHeader().iterator();
	    while(fieldItr.hasNext()){
	    	Field<?> field = fieldItr.next();
	    	try {
				System.out.println(field.getTag() +":="+ message.getHeader().getString(field.getField()));
			} catch (FieldNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	public static void main(String[] args) throws ConfigError, InterruptedException, SessionNotFound, IOException {
	    SessionSettings settings = new SessionSettings("initiator.cfg");

	    Application application = new FIXInitiatorApplication();
	    MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
	    LogFactory logFactory = new ScreenLogFactory( true, true, true);
	    MessageFactory messageFactory = new DefaultMessageFactory();

	    Initiator initiator = new SocketInitiator(application, messageStoreFactory, settings, logFactory, messageFactory);
	    initiator.start();
	    
	    System.in.read();
	    //stop the initiator...
	    initiator.stop();
	    
	    System.out.println(new SimpleDateFormat("yyyyMMdd-HH:mm:ss").format(new Date(System.currentTimeMillis())));
	}
}