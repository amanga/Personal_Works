package com.bunge.icc.fix.adapter.acceptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import quickfix.Acceptor;
import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.DefaultMessageFactory;
import quickfix.FieldConvertError;
import quickfix.FieldNotFound;
import quickfix.FileStoreFactory;
import quickfix.Group;
import quickfix.LogFactory;
import quickfix.Message;
import quickfix.Message.Header;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.MessageUtils;
import quickfix.RuntimeError;
import quickfix.ScreenLogFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.SystemTime;
import quickfix.field.Account;
import quickfix.field.AvgPx;
import quickfix.field.BeginString;
import quickfix.field.CFICode;
import quickfix.field.ClOrdID;
import quickfix.field.ClientID;
import quickfix.field.CumQty;
import quickfix.field.Currency;
import quickfix.field.ExecBroker;
import quickfix.field.ExecID;
import quickfix.field.ExecType;
import quickfix.field.ExpireTime;
import quickfix.field.LastCapacity;
import quickfix.field.LastMkt;
import quickfix.field.LastPx;
import quickfix.field.LastShares;
import quickfix.field.LeavesQty;
import quickfix.field.MaturityDate;
import quickfix.field.MaturityMonthYear;
import quickfix.field.MsgType;
import quickfix.field.MultiLegReportingType;
import quickfix.field.NoUnderlyings;
import quickfix.field.OrdStatus;
import quickfix.field.OrdType;
import quickfix.field.OrderCapacity;
import quickfix.field.OrderID;
import quickfix.field.OrderQty;
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
import quickfix.field.SettlDate;
import quickfix.field.SettlType;
import quickfix.field.Side;
import quickfix.field.StopPx;
import quickfix.field.Symbol;
import quickfix.field.TargetCompID;
import quickfix.field.TestReqID;
import quickfix.field.TimeInForce;
import quickfix.field.TradeDate;
import quickfix.field.TransactTime;
import quickfix.field.UnderlyingSecurityID;
import quickfix.field.UnderlyingSecurityIDSource;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.TestRequest;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider.TemplateMapping;

public class FIXAcceptorExecutor {

	private final SocketAcceptor acceptor;
	private final static Map dynamicSessionMappings = new HashMap();

	public FIXAcceptorExecutor(SessionSettings settings) throws ConfigError, FieldConvertError {
		Application application = new FIXAcceptorApplication(settings);
		MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);

		LogFactory logFactory = new ScreenLogFactory(true, true, true);

		// LogFactory logFactory = new FileLogFactory(settings);
		MessageFactory messageFactory = new DefaultMessageFactory();

		acceptor = new SocketAcceptor(application, messageStoreFactory, settings, logFactory, messageFactory);

		configureDynamicSessions(settings, application, messageStoreFactory, logFactory, messageFactory);

	}

	private void configureDynamicSessions(SessionSettings settings, Application application,
			MessageStoreFactory messageStoreFactory, LogFactory logFactory, MessageFactory messageFactory)
			throws ConfigError, FieldConvertError {
		Iterator sectionIterator = settings.sectionIterator();

		while (sectionIterator.hasNext()) {
			SessionID sessionID = (SessionID) sectionIterator.next();
			if (isSessionTemplate(settings, sessionID)) {
				InetSocketAddress address = getAcceptorSocketAddress(settings, sessionID);
				getMappings(address).add(new DynamicAcceptorSessionProvider.TemplateMapping(sessionID, sessionID));
			}
		}

		Set<Entry> entrySets = dynamicSessionMappings.entrySet();

		for (Entry entry : entrySets) {
			SocketAddress socketAddress = (SocketAddress) entry.getKey();
			List<TemplateMapping> mappings = (List<TemplateMapping>) entry.getValue();
			acceptor.setSessionProvider(socketAddress, new DynamicAcceptorSessionProvider(settings, mappings,
					application, messageStoreFactory, logFactory, messageFactory));
		}
	}

	private List getMappings(InetSocketAddress address) {
		System.out.println("GetMappings...");
		List<TemplateMapping> mappings = (List<TemplateMapping>) dynamicSessionMappings.get(address);
		if (mappings == null) {
			mappings = new ArrayList();
			this.dynamicSessionMappings.put(address, mappings);
		}
		return mappings;
	}

	private InetSocketAddress getAcceptorSocketAddress(SessionSettings settings, SessionID sessionID)
			throws ConfigError, FieldConvertError {
		String acceptorHost = "127.0.0.1";
		if (settings.isSetting(sessionID, Acceptor.SETTING_SOCKET_ACCEPT_ADDRESS)) {
			acceptorHost = settings.getString(sessionID, Acceptor.SETTING_SOCKET_ACCEPT_ADDRESS);

		}
		int acceptorPort = (int) settings.getLong(sessionID, Acceptor.SETTING_SOCKET_ACCEPT_PORT);
		InetSocketAddress address = new InetSocketAddress(acceptorHost, acceptorPort);
		System.out.println(acceptorHost);
		System.out.println(acceptorPort);
		return address;
	}

	private boolean isSessionTemplate(SessionSettings settings, SessionID sessionID)
			throws ConfigError, FieldConvertError {
		System.out.println("isSetting:=" + settings.isSetting(Acceptor.SETTING_ACCEPTOR_TEMPLATE));
		return settings.isSetting(sessionID, Acceptor.SETTING_ACCEPTOR_TEMPLATE)
				&& settings.getBool(sessionID, Acceptor.SETTING_ACCEPTOR_TEMPLATE);
	}

	private void start() throws RuntimeError, ConfigError {
		this.acceptor.start();
	}

	private void stop() {
		acceptor.stop();
		System.exit(0);
	}

	public static void main(String args[]) throws Exception {
		InputStream inputStream = null;
		if (args.length == 0) {
			System.out.println(FIXAcceptorExecutor.class.getClassLoader().getResource("acceptor.cfg").getFile());
			inputStream = FIXAcceptorExecutor.class.getClassLoader().getResourceAsStream("acceptor.cfg");
		} else if (args.length == 1) {
			inputStream = new FileInputStream(args[0]);
		}

		SessionSettings settings = new SessionSettings(inputStream);
		FIXAcceptorExecutor executor = new FIXAcceptorExecutor(settings);
		executor.start();
		
		
		
		executor.sendMessage();

		System.out.println("press to quit");
		System.in.read();
		executor.stop();
	}

	private List<Message> readFile4FixMessages(){
		List<Message> message = new ArrayList<Message>();
		//Read File from local with FIX messages;
		String filePath = FIXAcceptorExecutor.class.getClassLoader().getResource("Fidessa_fix_messages.txt").getFile();
		try {
			FileReader fileInput = new FileReader(new File(filePath));
			BufferedReader buffReader = new BufferedReader(fileInput);
			String line =null;
			while((line=buffReader.readLine())!=null){
				System.out.println(line);
				System.out.println("");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return message;
		
	}
	private void sendMessage() {
		
		
		class SendMessage extends Thread {
			private boolean sentMessages = false; 
			@Override
			public void run() {
				while (!sentMessages) {
					ArrayList<SessionID> sessionIds = acceptor.getSessions();
					for (SessionID sessionID : sessionIds) {
						try {
							// printHeaders(message);
							System.out.println("Session ID: " + sessionID);
							Session session = Session.lookupSession(sessionID);
							if (session != null) {
								System.out.println("Session is not null");

								System.out.println("sender Comp ID " + session.getSessionID().getSenderCompID());
								System.out.println("sender Target ID " + session.getSessionID().getTargetCompID());

								List<Message> executionReportMessages = getMessageFromFile(session);
//								List<Message> executionReportMessages = constructTestMessage(session);
								Iterator<Message> messageItr = executionReportMessages.iterator();
								while(messageItr.hasNext()){
									Message executionReportMessage = messageItr.next();
//									System.out.println( executionReportMessage.toXML());
									if (session.sendToTarget(executionReportMessage,
											session.getSessionID().getSenderCompID(),
											session.getSessionID().getTargetCompID())) {
										System.out.println("Message sent...");
										sentMessages = true;
									} else {
										System.out.println("Message not sent...");
										sentMessages = false;
									}
								}
							}
							Thread.sleep(30000);
						} catch (SessionNotFound e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		Thread th = new Thread(new SendMessage());
		th.start();
	}

	private List<Message> constructTestMessage(Session session) throws Exception{
		List<Message> messages = new ArrayList<Message>();
		
		TestReqID testReqId = new TestReqID("ACCEPTOR TEST.");
		TestRequest testRequest = new TestRequest(testReqId);
		populateHeader(session, testRequest.getHeader(), "1");
		messages.add(testRequest);
		return messages;
	}
	
	private List<Message> getMessageFromFile(Session session){
		String FIX_PATTERN = "\\[FROMAPP]8=FIX\\..*#$";
		Pattern pattern = Pattern.compile(FIX_PATTERN);
		
		List<Message> messages = new ArrayList<Message>();
		//Read File from local with FIX messages;
		
		if(FIXAcceptorExecutor.class.getClassLoader().getResource("testlog.txt")!=null){
			String filePath = FIXAcceptorExecutor.class.getClassLoader().getResource("testlog.txt").getFile();
			try {
				DataDictionary dd = new DataDictionary(FIXAcceptorExecutor.class.getClassLoader().getResource("FIX44.xml").getFile());
				FileReader fileInput = new FileReader(new File(filePath));
				BufferedReader buffReader = new BufferedReader(fileInput);
				
				String line =null;
				while((line=buffReader.readLine())!=null){
					Matcher fixMatcher = pattern.matcher(line);
					if(fixMatcher.find()){					
						String fixMessage = normalizeFIXMessage(fixMatcher.group(0));
						/*Message message = null;
						message = new Message(fixMessage);*/
					
						Message message = constructMessage(session,MessageUtils.parse(new DefaultMessageFactory(), dd, fixMessage));
						
						messages.add(message);
					}
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) { 
				e.printStackTrace();
			}
		}
		
		return messages;
	}
	
	private String normalizeFIXMessage(String fixMessage){
		return fixMessage.substring(9,fixMessage.indexOf("\""));
	}
	
	
	private Message constructMessage(Session session,Message logMessage) throws Exception{
		OrderID orderID = new OrderID(logMessage.getString(OrderID.FIELD));
		ExecID execID = new ExecID(logMessage.getString(ExecID.FIELD));
		ExecType execType = new ExecType(logMessage.getChar(ExecType.FIELD));
		OrdStatus ordStatus = new OrdStatus(logMessage.getChar(OrdStatus.FIELD));
		Side side = new Side(logMessage.getChar(Side.FIELD));
		LeavesQty leavesQty = new LeavesQty(logMessage.getDouble(LeavesQty.FIELD));
		CumQty cumQty = new CumQty(logMessage.getDouble(CumQty.FIELD));
		AvgPx avgPx = new AvgPx(logMessage.getDouble(AvgPx.FIELD));
		
		ExecutionReport execReport = new ExecutionReport(orderID, execID, execType, ordStatus, side, leavesQty, cumQty, avgPx);
		
		execReport.set(new Account(logMessage.getString(Account.FIELD)));
		execReport.set(new ClOrdID(logMessage.getString(ClOrdID.FIELD)));
		execReport.set(new Currency(logMessage.getString(Currency.FIELD)));
		if(logMessage.isSetField(20))
			execReport.setString(20, logMessage.getString(20));
		
		execReport.set(new Symbol(logMessage.getString(Symbol.FIELD)));
		execReport.set(new SecurityIDSource(logMessage.getString(SecurityIDSource.FIELD)));
		execReport.set(new SecurityID(logMessage.getString(SecurityID.FIELD)));
		
		if(logMessage.isSetField(LastCapacity.FIELD)) //29
			execReport.set(new LastCapacity(logMessage.getChar(LastCapacity.FIELD)));
		
		execReport.set(new LastMkt(logMessage.getString(LastMkt.FIELD)));
		execReport.set(new LastPx(logMessage.getDouble(LastPx.FIELD)));
		execReport.setString(LastShares.FIELD, logMessage.getString(LastShares.FIELD));
		execReport.set(new OrderQty(logMessage.getInt(OrderQty.FIELD)));
		execReport.set(new OrdStatus(logMessage.getChar(OrdStatus.FIELD)));
		execReport.set(new OrdType(logMessage.getChar(OrdType.FIELD)));
		execReport.setDouble(Price.FIELD,logMessage.getDouble(Price.FIELD));		
		execReport.set(new TimeInForce(logMessage.getChar(TimeInForce.FIELD)));
		execReport.setString(TransactTime.FIELD, logMessage.getString(TransactTime.FIELD));
		execReport.set(new SettlType(logMessage.getString(SettlType.FIELD)));
		execReport.set(new SettlDate(logMessage.getString(SettlDate.FIELD)));
		
		if(logMessage.isSetField(TradeDate.FIELD)) //75
			execReport.set(new TradeDate(logMessage.getString(TradeDate.FIELD)));
		
		if(logMessage.isSetField(ExecBroker.FIELD)) //76
			execReport.setString(ExecBroker.FIELD, logMessage.getString(ExecBroker.FIELD));
		
		if(logMessage.isSetField(StopPx.FIELD)) //198
		execReport.set(new StopPx(logMessage.getDouble(StopPx.FIELD)));
		
		if(logMessage.isSetField(SecurityDesc.FIELD)) //198
		execReport.set(new SecurityDesc(logMessage.getString(SecurityDesc.FIELD)));
		
		if(logMessage.isSetField(ClientID.FIELD)) //198
		execReport.setString(ClientID.FIELD, logMessage.getString(ClientID.FIELD));
		
		if(logMessage.isSetField(SettlCurrAmt.FIELD)) //198
		execReport.set(new SettlCurrAmt(logMessage.getDouble(SettlCurrAmt.FIELD)));
		
		if(logMessage.isSetField(SettlCurrency.FIELD)) //198
		execReport.set(new SettlCurrency(logMessage.getString(SettlCurrency.FIELD)));
		
		if(logMessage.isSetField(ExpireTime.FIELD)) //198
		execReport.setString(ExpireTime.FIELD, logMessage.getString(ExpireTime.FIELD));
		
		if(logMessage.isSetField(LeavesQty.FIELD)) //198
		execReport.set(new LeavesQty(logMessage.getDouble(LeavesQty.FIELD)));
		
		if(logMessage.isSetField(SecurityType.FIELD)) //198
		execReport.set(new SecurityType(logMessage.getString(SecurityType.FIELD)));
		
		if(logMessage.isSetField(SecondaryOrderID.FIELD)) //198
			execReport.set(new SecondaryOrderID(logMessage.getString(SecondaryOrderID.FIELD)));
		
		if(logMessage.isSetField(MaturityMonthYear.FIELD)) //527
			execReport.set(new MaturityMonthYear(logMessage.getString(MaturityMonthYear.FIELD)));
		
		if(logMessage.isSetField(SecurityExchange.FIELD)) //527
			execReport.set(new SecurityExchange(logMessage.getString(SecurityExchange.FIELD)));
		
		if(logMessage.isSetField(UnderlyingSecurityIDSource.FIELD)) //527
			execReport.setString(UnderlyingSecurityIDSource.FIELD, logMessage.getString(UnderlyingSecurityIDSource.FIELD));
		
		if(logMessage.isSetField(UnderlyingSecurityID.FIELD)) //527
			execReport.setString(UnderlyingSecurityID.FIELD, logMessage.getString(UnderlyingSecurityID.FIELD));
		
		if(logMessage.isSetField(MultiLegReportingType.FIELD)) //527
			execReport.set(new MultiLegReportingType(logMessage.getChar(MultiLegReportingType.FIELD)));
		
		if(logMessage.isSetField(CFICode.FIELD)) //527
			execReport.set(new CFICode(logMessage.getString(CFICode.FIELD)));
		
		if(logMessage.isSetField(SecondaryExecID.FIELD)) //527
			execReport.set(new SecondaryExecID(logMessage.getString(SecondaryExecID.FIELD)));
		
		if(logMessage.isSetField(OrderCapacity.FIELD)) //528
		execReport.set(new OrderCapacity(logMessage.getChar(OrderCapacity.FIELD)));
		
		if(logMessage.isSetField(MaturityDate.FIELD)) //6442
		execReport.set(new MaturityDate(logMessage.getString(MaturityDate.FIELD)));
		
		if(logMessage.isSetField(NoUnderlyings.FIELD)) //6442
		execReport.set(new NoUnderlyings(logMessage.getInt(NoUnderlyings.FIELD)));
		
		if(logMessage.isSetField(6442)) //6442
			execReport.setString(6442, logMessage.getString(6442));
		
		if(logMessage.isSetField(7562)) //7562
			execReport.setString(7562, logMessage.getString(7562));
		
		if(logMessage.isSetField(8001)) //7562
			execReport.setString(8001, logMessage.getString(8001)); // 8001=EXEC_NEW
		
		if(logMessage.isSetField(8002)) //7562
			execReport.setString(8002, logMessage.getString(8002)); // 8002=2017072408:58:05.237668-0500s
		
		if(logMessage.isSetField(8004)) //7562
			execReport.setString(8004, logMessage.getString(8004)); // 8004=3
		
		if(logMessage.isSetField(8022)) //7562
			execReport.setString(8022, logMessage.getString(8022)); // 8022=AT PE
		
		if(logMessage.isSetField(8031)) //8031
		execReport.setString(8031, logMessage.getString(8031)); // 8031=CBT-GFU
		
		if(logMessage.isSetField(8033)) //8033
		execReport.setString(8033, logMessage.getString(8033)); // 8033=N
		
		if(logMessage.isSetField(8040)) //8040
		execReport.setString(8040, logMessage.getString(8040)); // 8040=0
		
		if(logMessage.isSetField(8055)) //8055
		execReport.setString(8055, logMessage.getString(8055)); // 8055=BUNGE
		
		if(logMessage.isSetField(8056)) //8056
		execReport.setString(8056, logMessage.getString(8056)); // 8056=OMAR
		
		if(logMessage.isSetField(8059)) //8059
		execReport.setString(8059, logMessage.getString(8059)); // 8059=N
		
		if(logMessage.isSetField(8061)) //8061
		execReport.setString(8061, logMessage.getString(8061)); // 8061=2017072408:58:05.146384-0500s
		
		if(logMessage.isSetField(8062)) //8062
		execReport.setString(8062, logMessage.getString(8062)); // 8062=EMMA
		
		if(logMessage.isSetField(8065)) //8065
		execReport.setString(8065, logMessage.getString(8065)); // 8065=1009.000000000000
		
		if(logMessage.isSetField(8066)) //8066
		execReport.setString(8066, logMessage.getString(8066)); // 8066=1018.250000000000
		
		if(logMessage.isSetField(8072)) //8072
		execReport.setString(8072, logMessage.getString(8072)); // 8072=F
		
		if(logMessage.isSetField(8075)) //8075
		execReport.setString(8075, logMessage.getString(8075)); // 8075=C
		
		if(logMessage.isSetField(8076)) //7562
		execReport.setString(8076, logMessage.getString(8076)); // 8076=00000055683ORCH1
		
		if(logMessage.isSetField(8077)) //8077
		execReport.setString(8077, logMessage.getString(8077)); // 8077=OMAR
		
		if(logMessage.isSetField(8086)) //8086
		execReport.setString(8086, logMessage.getString(8086)); // 8086=SMARTES
		
		if(logMessage.isSetField(8094)) //8094
		execReport.setString(8094, logMessage.getString(8094)); // 8094=C
		
		if(logMessage.isSetField(8095)) //8095
		execReport.setString(8095, logMessage.getString(8095)); // 8095=N
		
		if(logMessage.isSetField(8100)) //8100
		execReport.setString(8100, logMessage.getString(8100)); // 8100=CME002091528
		
		if(logMessage.isSetField(8102)) //8102
		execReport.setString(8102, logMessage.getString(8102)); // 8102=4
		
		if(logMessage.isSetField(8142)) //7562
		execReport.setString(8142, logMessage.getString(8142)); // 8142=OMAR
		
		if(logMessage.isSetField(8143)) //7562
		execReport.setString(8143, logMessage.getString(8143)); // 8143=OMAR
		
		if(logMessage.isSetField(8144)) //7562
		execReport.setString(8144, logMessage.getString(8144)); // 8144=CE
		
		if(logMessage.isSetField(8154)) //7562
		execReport.setString(8154, logMessage.getString(8154)); // 8154=5000.000000
		
		if(logMessage.isSetField(8161)) //7562
		execReport.setString(8161, logMessage.getString(8161)); // 8161=Shashi.Rathee
		
		if(logMessage.isSetField(9510)) //7562
		execReport.setString(9510, logMessage.getString(9510)); // 9510=2
		
		if(logMessage.isSetField(9511)) //7562
		execReport.setString(9511, logMessage.getString(9511)); // 9511=2
				
		if(logMessage.isSetField(30001))
			execReport.setString(30001, logMessage.getString(30001)); // 30001=XOFF
		
		if(logMessage.isSetField(30003))
			execReport.setString(30003, logMessage.getString(30003)); // 30003=X851

		//GROUP FOR 453
		List<Group> group453 = logMessage.getGroups(453);
		Iterator<Group> group453Itr = group453.iterator();
		while(group453Itr.hasNext()){
			execReport.addGroup(group453Itr.next());
		}
		
		//GROUP FOR 454
		List<Group> group454 = logMessage.getGroups(453);
		Iterator<Group> group454Itr = group454.iterator();
		while(group454Itr.hasNext()){
			execReport.addGroup(group454Itr.next());
		}
		
		populateHeader(session, execReport.getHeader(), "8");
		
		return execReport;
	}

	private void populateHeader(Session session, Header header, String msgType) throws Exception {

		header.setString(BeginString.FIELD, session.getSessionID().getBeginString());
		header.setString(SenderCompID.FIELD, session.getSessionID().getSenderCompID());
		header.setString(TargetCompID.FIELD, session.getSessionID().getTargetCompID());
		header.setUtcTimeStamp(SendingTime.FIELD, SystemTime.getDate());
		header.setString(MsgType.FIELD, msgType);
	}
}
