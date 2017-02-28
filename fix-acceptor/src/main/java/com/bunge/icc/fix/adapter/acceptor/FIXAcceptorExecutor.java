package com.bunge.icc.fix.adapter.acceptor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import quickfix.Acceptor;
import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FieldConvertError;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.RuntimeError;
import quickfix.ScreenLogFactory;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider.TemplateMapping;

public class FIXAcceptorExecutor {

	private final SocketAcceptor acceptor;
	private final static Map dynamicSessionMappings = new HashMap();

	public FIXAcceptorExecutor(SessionSettings settings) throws ConfigError,
			FieldConvertError {
		Application application = new FIXAcceptorApplication(settings);
		MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
		
		LogFactory logFactory = new ScreenLogFactory( true, true, true);
		
//		LogFactory logFactory = new FileLogFactory(settings);
		MessageFactory messageFactory = new DefaultMessageFactory();

		acceptor = new SocketAcceptor(application, messageStoreFactory,
				settings, logFactory, messageFactory);

	    
		configureDynamicSessions(settings, application, messageStoreFactory,
				logFactory, messageFactory);

	}

	private void configureDynamicSessions(SessionSettings settings,
			Application application, MessageStoreFactory messageStoreFactory,
			LogFactory logFactory, MessageFactory messageFactory)
			throws ConfigError, FieldConvertError {
		Iterator sectionIterator = settings.sectionIterator();
		
		while (sectionIterator.hasNext()) {
			SessionID sessionID = (SessionID) sectionIterator.next();
			if (isSessionTemplate(settings, sessionID)) {
				InetSocketAddress address = getAcceptorSocketAddress(settings,
						sessionID);
				getMappings(address).add(
						new DynamicAcceptorSessionProvider.TemplateMapping(sessionID, sessionID));
			}
		}

		Set<Entry> entrySets = dynamicSessionMappings.entrySet();

		for (Entry entry : entrySets) {
			SocketAddress socketAddress = (SocketAddress) entry.getKey();
			List<TemplateMapping> mappings = (List<TemplateMapping>) entry
					.getValue();
			acceptor.setSessionProvider(socketAddress,
					new DynamicAcceptorSessionProvider(settings,
							mappings, application,
							messageStoreFactory, logFactory, messageFactory));
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

	private InetSocketAddress getAcceptorSocketAddress(
			SessionSettings settings, SessionID sessionID) throws ConfigError,
			FieldConvertError {
		String acceptorHost = "127.0.0.1";
		if (settings.isSetting(sessionID, Acceptor.SETTING_SOCKET_ACCEPT_ADDRESS)) {
			acceptorHost = settings.getString(sessionID, Acceptor.SETTING_SOCKET_ACCEPT_ADDRESS);
			
		}
		int acceptorPort = (int) settings.getLong(sessionID, Acceptor.SETTING_SOCKET_ACCEPT_PORT);
		InetSocketAddress address = new InetSocketAddress(acceptorHost,
				acceptorPort);
		System.out.println(acceptorHost);
		System.out.println(acceptorPort);
		return address;
	}

	private boolean isSessionTemplate(SessionSettings settings,
			SessionID sessionID) throws ConfigError, FieldConvertError {
		System.out.println("isSetting:="+ settings.isSetting(Acceptor.SETTING_ACCEPTOR_TEMPLATE));
		return settings.isSetting(sessionID, Acceptor.SETTING_ACCEPTOR_TEMPLATE)
				&& settings.getBool(sessionID,Acceptor.SETTING_ACCEPTOR_TEMPLATE);
	}

	private void start() throws RuntimeError, ConfigError {
		this.acceptor.start();
	}

	private void stop() {
		acceptor.stop();
	}

	public static void main(String args[]) throws Exception {
		InputStream inputStream = null;
		if (args.length == 0) {
			System.out.println(FIXAcceptorExecutor.class.getClassLoader().getResource("acceptor.cfg").getFile());
			inputStream = FIXAcceptorExecutor.class.getClassLoader()
					.getResourceAsStream("acceptor.cfg");
		} else if (args.length == 1) {
			inputStream = new FileInputStream(args[0]);
		}

		SessionSettings settings = new SessionSettings("acceptor.cfg");
		FIXAcceptorExecutor executor = new FIXAcceptorExecutor(settings);
		executor.start();
		
		System.out.println("press to quit");
		System.in.read();
		executor.stop();
	}

}
