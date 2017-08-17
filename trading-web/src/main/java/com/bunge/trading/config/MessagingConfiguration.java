package com.bunge.trading.config;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
import javax.naming.Context;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiTemplate;

@Configuration
public class MessagingConfiguration {

	@Value("${bunge.tibco.jms.contextfactory.class}")
	public String contextFactoryTibcoClass;

	// @Value("${bunge.jms.destination.hris.queue}")
	// public String hrisDestinationQueueName ;
	//
	// @Value("${bunge.jms.destination.error.queue}")
	// public String errorDestinationQueueName;
	//
	// @Value("${bunge.jms.destination.organization.topic}")
	// public String organizationDestinationTopicName;

	@Value("${bunge.tibco.ems.dev.url}")
	String devUrl; // Dev ems link

	@Value("${bunge.tibco.ems.model.url}")
	String modelUrl; // Model ems link

	@Bean(name = "devJndiTemplate")
	public JndiTemplate devJndiTemplate() {
		JndiTemplate jndiTempalte = new JndiTemplate();
		try {
			Properties environment = new Properties();
			environment.setProperty(Context.INITIAL_CONTEXT_FACTORY, contextFactoryTibcoClass);
			environment.setProperty(Context.PROVIDER_URL, devUrl);
			// environment.setProperty("SM_USER", "camanga");
			jndiTempalte.setEnvironment(environment);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jndiTempalte;
	}

	@Bean(name = "devQueueConnectionFactory")
	@Primary
	public ConnectionFactory devQueueConnectionFactory() {
		ConnectionFactory connectionFactory = null;
		try {
			connectionFactory = (QueueConnectionFactory) devJndiTemplate().lookup("XAQueueConnectionFactory");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connectionFactory;
	}

	@Bean(name = "devTopicConnectionFactory")
	public ConnectionFactory devTopicConnectionFactory() {
		ConnectionFactory connectionFactory = null;
		try {
			connectionFactory = (TopicConnectionFactory) devJndiTemplate().lookup("XATopicConnectionFactory");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connectionFactory;
	}

	@Bean(name = "modelJndiTemplate")
	public JndiTemplate modelJndiTemplate() {
		JndiTemplate jndiTempalte = new JndiTemplate();
		try {
			Properties environment = new Properties();
			environment.setProperty(Context.INITIAL_CONTEXT_FACTORY, contextFactoryTibcoClass);
			environment.setProperty(Context.PROVIDER_URL, modelUrl);
			// environment.setProperty("SM_USER", "camanga");
			jndiTempalte.setEnvironment(environment);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jndiTempalte;
	}

	@Bean(name = "modelQueueConnectionFactory")
	public ConnectionFactory modelQueueConnectionFactory() {
		ConnectionFactory connectionFactory = null;
		try {
			connectionFactory = (QueueConnectionFactory) modelJndiTemplate().lookup("XAQueueConnectionFactory");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connectionFactory;
	}

	@Bean(name = "modelTopicConnectionFactory")
	public ConnectionFactory modelTopicConnectionFactory() {
		ConnectionFactory connectionFactory = null;
		try {
			connectionFactory = (TopicConnectionFactory) modelJndiTemplate().lookup("XATopicConnectionFactory");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return connectionFactory;
	}
	
	//Default JMS Listener Container factory's

	@Bean(name = "devQueueDefaultJmsListenerContainerFactory")
	public DefaultJmsListenerContainerFactory devJmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
		jmsListenerContainerFactory.setConnectionFactory(devQueueConnectionFactory());
		return jmsListenerContainerFactory;
	}

	@Bean(name = "devTopicDefaultJmsListenerContainerFactory")
	public DefaultJmsListenerContainerFactory devTopicJmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
		jmsListenerContainerFactory.setConnectionFactory(devTopicConnectionFactory());
		return jmsListenerContainerFactory;
	}

	@Bean(name = "modelQueueDefaultJmsListenerContainerFactory")
	public DefaultJmsListenerContainerFactory modelQueueJmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
		jmsListenerContainerFactory.setConnectionFactory(modelQueueConnectionFactory());
		return jmsListenerContainerFactory;
	}

	@Bean(name = "modelTopicDefaultJmsListenerContainerFactory")
	public DefaultJmsListenerContainerFactory modelTopicJmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
		jmsListenerContainerFactory.setConnectionFactory(modelTopicConnectionFactory());
		return jmsListenerContainerFactory;
	}
	
	//JSM Template's

	@Bean(name = "devQueueJmsTemplate")
	public JmsTemplate devQueueJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(devQueueConnectionFactory());
		// jmsTemplate.setDefaultDestinationName(hrisDestinationQueueName);
		return jmsTemplate;
	}

	@Bean(name = "devTopicJmsTempalte")
	public JmsTemplate devTopicJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(devTopicConnectionFactory());
		// jmsTemplate.setDefaultDestinationName(organizationDestinationTopicName);
		return jmsTemplate;
	}

	@Bean(name = "modelQueueJmsTemplate")
	public JmsTemplate modelQueueJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(modelQueueConnectionFactory());
		// jmsTemplate.setDefaultDestinationName(errorDestinationQueueName);
		return jmsTemplate;
	}

	@Bean(name = "modelQueueJmsTemplate")
	public JmsTemplate modelTopicJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(modelTopicConnectionFactory());
		// jmsTemplate.setDefaultDestinationName(errorDestinationQueueName);
		return jmsTemplate;
	}
}
