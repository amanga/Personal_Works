package com.bunge.icc.tibco.adapter;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bunge.icc.tibco.adapter.util.AdapterConstants;
import com.bunge.icc.tibco.adapter.util.AdapterLogHandler;
import com.bunge.icc.tibco.adapter.util.AdapterPropertyUtil;
import com.tibco.sdk.MAdapterServiceInfo;
import com.tibco.sdk.MApp;
import com.tibco.sdk.MAppProperties;
import com.tibco.sdk.MComponent;
import com.tibco.sdk.MComponentRegistry;
import com.tibco.sdk.MException;
import com.tibco.sdk.MHostInfo;
import com.tibco.sdk.MProperties;
import com.tibco.sdk.MUserApplicationState;
import com.tibco.sdk.events.MPublisher;

public class TibcoMApp extends MApp {

	private Logger logger = Logger.getLogger(TibcoMApp.class.getName());
	
	public TibcoMApp(MAppProperties mAppProperties) {
		super(mAppProperties);
	}

	@Override
	protected void onInitialization() throws MException {
		//set the log handler.
		Logger.getLogger("").addHandler(new AdapterLogHandler(this.getTrace()));
		logger.log(Level.INFO,"onInitialization .....");
		// Register Publisher
		
		MProperties appProperties = this.getConfigProperties();
		MAdapterServiceInfo serviceAppInfo = new MAdapterServiceInfo();
		MComponentRegistry componentReg = this.getComponentRegistry();
		
		MHostInfo hostInfo = this.getHostInfo();
		hostInfo.setAppState(MUserApplicationState.RUNNING);
		this.setHostInfo(hostInfo);
		
		String socketConnectionUrl = AdapterPropertyUtil.getString(appProperties, AdapterConstants.APP_NAME, AdapterConstants.SESSION_CONNECTION_URL_HOST_PROPERTY);
		String socketConnectionPort = AdapterPropertyUtil.getString(appProperties, AdapterConstants.APP_NAME, AdapterConstants.SESSION_CONNECTION_URL_PORT_PROPERTY); 
		logger.log(Level.INFO, "Socket Connection URL := "+ socketConnectionUrl);
		logger.log(Level.INFO, "Socket Connection Port :="+ socketConnectionPort);
		
		
		@SuppressWarnings("unchecked")
		Enumeration<MComponent> components = componentReg.getComponents();
		while(components.hasMoreElements()){
			MComponent component = components.nextElement();
			if(component instanceof MPublisher){
				MPublisher mPublisher = (MPublisher) component;
				mPublisher.activate();
				@SuppressWarnings("unchecked")
				Enumeration<String> classNames = mPublisher.getClassNames();
				while(classNames.hasMoreElements()){
					serviceAppInfo.set("Publish Service", mPublisher.getName(), classNames.nextElement());
				}
			}
		}
		setAdapterServiceInfo(serviceAppInfo);
	}

	@Override
	protected void onTermination() throws MException {
		logger.log(Level.INFO,"onTermination .....");
		
		MHostInfo hostInfo = this.getHostInfo();
		hostInfo.setAppState(MUserApplicationState.STOPPED);
		this.setHostInfo(hostInfo);
		logger.info("End of onTermination....");
	}

}
