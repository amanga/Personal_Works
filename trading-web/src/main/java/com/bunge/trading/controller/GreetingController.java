package com.bunge.trading.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bunge.trading.pojo.HelloMessage;
import com.bunge.trading.task.MessagePushTask;


@Controller
public class GreetingController {

	@Autowired
	@Qualifier("devQueueJmsTemplate")
	JmsTemplate jmsTemplate;
	
	@Autowired
	TaskExecutor taskExecutor;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public void greeting(HelloMessage message) throws Exception{
		Thread.sleep(1000);
		
		jmsTemplate.send(messageCreator(message.getName()));
		
//		return new Greeting( new Date()+" Hello, " + message.getName() +"!" );
	}
	
	@RequestMapping("/task")
	public void startTask(){
		MessagePushTask messagePushTask = applicationContext.getBean(MessagePushTask.class);		
		taskExecutor.execute(messagePushTask);
	}
	
	private MessageCreator messageCreator(final String message){
		return new MessageCreator() {
			@Override
			public Message createMessage(Session arg0) throws JMSException {

				return arg0.createTextMessage(" Message "+message);
			}
		};
	}
}
