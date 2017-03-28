package com.bunge.icc.doc.tests;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.ibm.icu.text.SimpleDateFormat;

public class RetriveDataFromEC {

	private AtomicLong lastAccess;
	SimpleDateFormat simpleDateFrmt ;
	private final String dateFormat = "MM/dd/yy hh:mm a";
	
	public static void main(String args[]){
		RetriveDataFromEC obj = new RetriveDataFromEC();
		obj.clearSession();
		
	}
	
	public void clearSession(){
		simpleDateFrmt = new SimpleDateFormat(dateFormat);
		lastAccess = new AtomicLong(System.currentTimeMillis());
		Executors.defaultThreadFactory().newThread(new KeepAlive()).start();
	}
	
	public void debugMssg(String mssg){
		System.out.println(simpleDateFrmt.format(new Date(System.currentTimeMillis()))+ ": " +mssg);
	}
	
	private class KeepAlive implements Runnable {
        @Override
        public void run() {
            long waitFor = TimeUnit.MINUTES.toMillis(5);
            debugMssg("Inside Run...");
            while (true) {            	
                try {
                    Thread.sleep(waitFor);
                    debugMssg("After wait...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (System.currentTimeMillis() > lastAccess.get() + waitFor) {
                	debugMssg("Stoping Session here...");
                    return;
                }
            }
        }
        
    }
}
