package com.bunge.trading.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.bunge.trading.pojo.StockTicker;

public class MessagePushTask implements Runnable {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public void run() {
		System.out.println(">>>>Inside Run....");
		try {
			while (true) {
				Thread.sleep(1000);
				simpMessagingTemplate.convertAndSend("/topic/greetings", new StockTicker("APPL",144.5));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
