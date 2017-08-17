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
import quickfix.field.Account;
import quickfix.field.AvgPx;
import quickfix.field.BeginString;
import quickfix.field.CFICode;
import quickfix.field.ClOrdID;
import quickfix.field.CumQty;
import quickfix.field.Currency;
import quickfix.field.ExecID;
import quickfix.field.ExecType;
import quickfix.field.LastCapacity;
import quickfix.field.LastMkt;
import quickfix.field.LastPx;
import quickfix.field.LastShares;
import quickfix.field.LeavesQty;
import quickfix.field.MsgSeqNum;
import quickfix.field.MsgType;
import quickfix.field.MultiLegReportingType;
import quickfix.field.NoUnderlyings;
import quickfix.field.OrdStatus;
import quickfix.field.OrdType;
import quickfix.field.OrderCapacity;
import quickfix.field.OrderID;
import quickfix.field.OrderQty;
import quickfix.field.PossDupFlag;
import quickfix.field.Price;
import quickfix.field.SecondaryExecID;
import quickfix.field.SecondaryOrderID;
import quickfix.field.SecurityDesc;
import quickfix.field.SecurityExchange;
import quickfix.field.SecurityID;
import quickfix.field.SecurityIDSource;
import quickfix.field.SecurityType;
import quickfix.field.SenderCompID;
import quickfix.field.SendingTime;
import quickfix.field.SettlCurrAmt;
import quickfix.field.SettlCurrency;
import quickfix.field.SettlType;
import quickfix.field.Side;
import quickfix.field.StopPx;
import quickfix.field.Symbol;
import quickfix.field.TargetCompID;
import quickfix.field.TimeInForce;
import quickfix.field.UnderlyingSecurityID;
import quickfix.field.UnderlyingSecurityIDSource;
import quickfix.fix44.ExecutionReport;

public class FIXAcceptorApplication implements Application {
	
	private SessionID sessionId;
	
	public FIXAcceptorApplication(SessionSettings sessionSettings){
		
	}
	@Override
	public void onCreate(SessionID sessionID) {
	    System.out.println("OnCreate");
	    this.sessionId = sessionID;
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
	    /*try {
//	    	printHeaders(message);
	    	System.out.println("Session ID: "+this.sessionId);
	    	Session session = Session.lookupSession(this.sessionId);
	    	if(session!=null){
	    		System.out.println("Session is not null");
	    		
	    		System.out.println("sender Comp ID "+session.getSessionID().getSenderCompID());
	    		System.out.println("sender Target ID "+session.getSessionID().getTargetCompID());
	    		
	    		
	    		Message executionReportMessage = constructExecMessage(session);
	    		Header header = executionReportMessage.getHeader();
				populateHeader(session,header,"8");
				if(session.sendToTarget(executionReportMessage)){
					System.out.println("Message sent...");
				}else{
					System.out.println("Message not sent...");
				}
	    	}
	    } catch (SessionNotFound e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
	    System.out.println("FromAdmin");
	}

	@Override
	public void toApp(Message message, SessionID sessionID) throws DoNotSend {
//	    System.out.println("ToApp: " + message);
		System.out.println("ToApp");
		
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
	
	
	private Message constructExecMessage(Session session){
		Message message = new Message();
		
		message.setString(BeginString.FIELD, "FIX.4.4");
//		message.setString(MsgType.FIELD, "8"); //35=8		
		message.setString(PossDupFlag.FIELD, "N");
//		message.setString(SendingTime.FIELD, "20170724-13:58:05.251");
		message.setString(Account.FIELD, "ROM88773");
		message.setString(AvgPx.FIELD, "1017.000000000000");
//		message.setString(ClOrdID.FIELD, "00000055683ORCH1.3");
		message.setString(CumQty.FIELD, "1");  //14=1
		message.setString(Currency.FIELD, "USD");  //15=USD
		message.setString(ExecID.FIELD, "00000110588TRCH1.1.1");  //17=00000110588TRCH1.1.1
		message.setString(20, "0");  //20=0
		message.setString(SecurityIDSource.FIELD, "8");  //22=8
		message.setString(LastCapacity.FIELD, "1");  //29=1
		message.setString(LastMkt.FIELD, "IM");  //30=IM
		message.setString(LastPx.FIELD, "1017.000000000000");  //31=1017.000000000000
		message.setString(LastShares.FIELD, "1");  //32=1
		message.setString(OrderID.FIELD, "00000055683ORCH1");  //37=00000055683ORCH1
		message.setString(OrderQty.FIELD, "1");  //38=1
		message.setString(OrdStatus.FIELD, "2");  //39=2
		message.setString(OrdType.FIELD, "2");  //40=2
		message.setString(Price.FIELD, "1017.000000000000");  //44=1017.000000000000
		message.setString(SecurityID.FIELD, "ZS");  //48=ZS
		message.setString(BeginString.FIELD, session.getSessionID().getBeginString());		
		message.setString(Side.FIELD, "1");  //54=1
		message.setString(Symbol.FIELD, "ZS");  //55=ZS
		message.setString(SenderCompID.FIELD, session.getSessionID().getSenderCompID());
		message.setString(TimeInForce.FIELD, "0");  //59=0
//		message.setString(TransactTime.FIELD, "20170724-13:58:05.146");  //60=20170724-13:58:05.146
		message.setString(SettlType.FIELD, "6");  //63=6
//		message.setString(SettlDate.FIELD, "20170727");  //64=20170727
//		message.setString(TradeDate.FIELD, "20170724");  //75=20170724
		message.setString(76, "BUNGE");  //76=BUNGE
		message.setString(StopPx.FIELD, "0.000000");  //99=0.000000
		message.setString(SecurityDesc.FIELD, "Soybean Sep17");  //107=Soybean Sep17
		message.setString(109, "OTHER");  //109=OTHER
		message.setString(SettlCurrAmt.FIELD, "0.000000");  //119=0.000000
		message.setString(SettlCurrency.FIELD, "USD");  //120=USD
//		message.setString(ExpireTime.FIELD, "20170724-18:45:00.000");  //126=20170724-18:45:00.000
		message.setString(ExecType.FIELD, "F");  //150=F
		message.setString(LeavesQty.FIELD, "0.000000");  //151=0.000000
		message.setString(SecurityType.FIELD, "FUT");  //167=FUT
		message.setString(SecondaryOrderID.FIELD, "00000007207OACH1");  //198=00000007207OACH1
//		message.setString(MaturityMonthYear.FIELD, "201709");  //200=201709
		message.setString(SecurityExchange.FIELD, "XCBT");  //207=XCBT
		message.setString(UnderlyingSecurityIDSource.FIELD, "K");  //305=K
		message.setString(UnderlyingSecurityID.FIELD, "OILSEED.NEWS");  //309=OILSEED.NEWS
		message.setString(MultiLegReportingType.FIELD, "1");  //442=1
		message.setString(CFICode.FIELD, "1");  //461=FCAXSX
		message.setString(SecondaryExecID.FIELD, "00000003060TRCH1");  //527=00000003060TRCH1
		message.setString(OrderCapacity.FIELD, "A");  //528=A
//		message.setString(MaturityDate.FIELD, "20170914");  //541=20170914
		message.setString(NoUnderlyings.FIELD, "1");  //711=1
		message.setString(6442, "0.000000");  //6442=0.000000
		message.setString(7562, "00000076635UECH1");  //7562=00000076635UECH1
		message.setString(8001, "1");  //8001=EXEC_NEW
		message.setString(8002, "20170724 08:58:05.237668 -0500s");  //8002=20170724 08:58:05.237668 -0500s
		message.setString(8004, "3");  //8004=3
		message.setString(8022, "AT PE");  //8022=AT PE  
		message.setString(8031, "1");  //8031=CBT-GFU
		message.setString(8033, "N");  //8033=N
		message.setString(8040, "0");  //8040=0
		message.setString(8055, "BUNGE");  //8055=BUNGE
		message.setString(8056, "OMAR");  //8056=OMAR
		message.setString(8059, "N");  //8059=N
		message.setString(8061, "20170724 08:58:05.146384 -0500s");  //8061=20170724 08:58:05.146384 -0500s
		message.setString(8062, "EMMA");  //8062=EMMA
		message.setString(8065, "1009.000000000000");  //8065=1009.000000000000
		message.setString(8066, "1018.250000000000");  //8066=1018.250000000000
		message.setString(8072, "F");  //8072=F
		message.setString(8075, "C");  //8075=C
		message.setString(8076, "00000055683ORCH1");  //8076=00000055683ORCH1
		message.setString(8077, "OMAR");  //8077=OMAR
		message.setString(8086, "SMARTES");  //8086=SMARTES
		message.setString(8094, "C");  //8094=C
		message.setString(8095, "N");  //8095=N
		message.setString(8100, "CME002091528");  //8100=CME002091528
		message.setString(8102,"4");  //8102=4
		message.setString(8142, "OMAR");  //8142=OMAR
		message.setString(8143,"OMAR");  //8143=OMAR
		message.setString(8144,"CE");  //8144=CE
		message.setString(8154,"5000.000000");  //8154=5000.000000
		message.setString(8161,"Shashi.Rathee");  //8161=Shashi.Rathee
		message.setString(9510,"2");  //9510=2
		message.setString(9511,"2");  //9511=2
		message.setString(30001,"XOFF");  //30001=XOFF
		message.setString(30003,"X851");  //30003=X851
		message.setString(453,"5");  //453=5
		message.setString(448,"Shashi.Rathee");  //448=Shashi.Rathee
		message.setString(447,"D");  //447=D
		message.setString(452,"11");  //452=11
		message.setString(448,"BUNGE");  //448=BUNGE
		message.setString(447,"D");  //447=D
		message.setString(452,"3");  //452=3
		message.setString(448,"Shashi.Rathee");  //448=Shashi.Rathee
		message.setString(447,"D");  //447=D
		message.setString(452,"12");  //452=12
		message.setString(452,"12");  //448=ROM88773
		message.setString(447,"D");  //447=D
		message.setString(452,"24");  //452=24
		message.setString(448,"BNGE");  //448=BNGE
		message.setString(447,"D");  //447=D
		message.setString(452,"7");  //452=7
		message.setString(454,"3");  //454=3		
		message.setString(455,"CME002091528");  //455=CME002091528
		message.setString(455,"ZS_FU7.CB");  //455=ZS_FU7.CB
		message.setString(456,"FIM");  //456=FIM
		message.setString(455,"ZS_FU7");  //455=ZS_FU7
		message.setString(456,"8");  //456=8
		message.setString(10,"055");  //10=055
		
		return message;
	}
	
	public SessionID getSessionId() {
		return sessionId;
	}
	public void setSessionId(SessionID sessionId) {
		this.sessionId = sessionId;
	}

	
}