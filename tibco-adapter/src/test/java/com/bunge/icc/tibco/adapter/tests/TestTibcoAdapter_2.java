package com.bunge.icc.tibco.adapter.tests;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.bunge.icc.tibco.adapter.TibcoAdapterAdvisoryListener;
import com.bunge.icc.tibco.adapter.TibcoMApp;
import com.bunge.icc.tibco.adapter.util.AdapterConstants;
import com.tibco.sdk.MAppProperties;
import com.tibco.sdk.MException;
import com.tibco.sdk.MHostInfo;
import com.tibco.sdk.MMessageBundle;
import com.tibco.sdk.MUserApplicationState;

public class TestTibcoAdapter_2 {

	private static Logger logger = Logger.getLogger(TestTibcoAdapter_2.class.getName()); 
	
	private static TibcoMApp appManager;
	
	private static final String APP_VERSION = "1.0";
	private static final String APP_INFO = "TIB/SimpleAdapter";
	private static final String CONFIG_URL = "SimpleAdapter/SimpleAdapterConfiguration";
	private static final String REPO_URL = "adapter.dat";
	
	
	public static void start(String args[]){
		if(appManager != null)
			logger.log(Level.WARNING,"Adapter is already started.");
		
		try{
			MAppProperties properties = new MAppProperties(AdapterConstants.APP_NAME, APP_VERSION, APP_INFO, CONFIG_URL, REPO_URL,args);
			logger.log(Level.INFO, "Start of FIX Adapter.");			
			appManager = new TibcoMApp(properties);
			
			MHostInfo hostInfo = new MHostInfo(appManager);
			hostInfo.setAppState(MUserApplicationState.INITIALIZING);			
			appManager.setHostInfo(hostInfo);
			
			TibcoAdapterAdvisoryListener advisoryListener = new TibcoAdapterAdvisoryListener(appManager);			
			appManager.setAdvisoryListener(advisoryListener);
			
			MMessageBundle.addResourceBundle("adapter", "simpleadapter");
			
			appManager.start();
			
			logger.log(Level.INFO,"isMHostInfo null "+(appManager.getHostInfo()==null));
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void stop(){
		try {
			
			MHostInfo hostInfo = appManager.getHostInfo();
			hostInfo.setAppState(MUserApplicationState.STOPPING);
			appManager.setHostInfo(hostInfo);
			appManager.stop();
			
		} catch (MException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws InterruptedException{
		TestTibcoAdapter_2.start(args);
		//sleep for 10 seconds....
		Thread.sleep(10000);
		TestTibcoAdapter_2.stop();
	}
	
	
	
}
