package com.bunge.icc.fix.adapter.acceptor;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.Message.Header;
import quickfix.RejectLogon;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SystemTime;
import quickfix.UnsupportedMessageType;
import quickfix.field.BeginString;
import quickfix.field.MsgType;
import quickfix.field.SenderCompID;
import quickfix.field.SendingTime;
import quickfix.field.TargetCompID;

public class FIXAcceptorApplication implements Application {

	public FIXAcceptorApplication(SessionSettings sessionSettings){
		
	}
	@Override
	public void onCreate(SessionID sessionID) {
	    System.out.println("OnCreate");
	}

	@Override
	public void onLogon(SessionID sessionID) {
	    System.out.println("OnLogon");
	}

	@Override
	public void onLogout(SessionID sessionID) {
	    System.out.println("OnLogout");
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
//	    System.out.println("ToApp: " + message);
	}

	@Override
	public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
	    System.out.println("FromApp");
	    /*if(message.getHeader().isSetField(MsgType.FIELD)&& (message.getHeader().getString(MsgType.FIELD).equalsIgnoreCase("A"))){
	    	Message logonMssage = new Message();
	    	Header header = logonMssage.getHeader();
	    	Session session = Session.lookupSession(sessionID);
	    	String msgType = session.getDataDictionary().getMsgType("logon");
	    	try {
				populateHeader(session, header, msgType);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	session.send(message);
	    	
	    }*/
//	    crack(message, sessionID);
	}
	
	private void populateHeader(Session session,Header header, String msgType) throws Exception {
		header.setString(BeginString.FIELD, session.getSessionID().getBeginString());
		header.setString(SenderCompID.FIELD, session.getSessionID().getSenderCompID());
		header.setString(TargetCompID.FIELD, session.getSessionID().getTargetCompID());
		header.setUtcTimeStamp(SendingTime.FIELD, SystemTime.getDate());
		header.setString(MsgType.FIELD,msgType);
	}

}