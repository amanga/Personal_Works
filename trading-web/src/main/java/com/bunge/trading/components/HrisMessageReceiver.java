package com.bunge.trading.components;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class HrisMessageReceiver {

	@Qualifier("devQueueDefaultJmsListenerContainerFactory")
	@JmsListener(destination = "${bunge.jms.destination.hris.queue}")
	public void onHrisMessage(final Message<String> message) throws JmsException {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("Message Header {" + message.getHeaders() + "}");
		System.out.println("Message Payload {" + message.getPayload() + "}");

		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

	}

}
