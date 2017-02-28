package com.bunge.icc.ejbclients;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ejb.access.SimpleRemoteStatelessSessionProxyFactoryBean;
import org.springframework.jndi.JndiTemplate;

import com.bunge.integration.ejb.IntegrationServiceClient;
import com.bunge.integration.ejb.IntegrationServiceClientHome;

@Configuration
@PropertySource(value="/fixadapter.properties")
public class BISIntegrationConfiguration {
	
	@Value("#{provider.url}")
	private String providerUrl;
	
	@Value("#{jndi.name}")
	private String jndiName;
	
	@Bean
	public SimpleRemoteStatelessSessionProxyFactoryBean simpleRemoteStatelessSessionProxyFactoryBean(){
		System.out.println(jndiName);
		SimpleRemoteStatelessSessionProxyFactoryBean obj = new SimpleRemoteStatelessSessionProxyFactoryBean();
		obj.setRefreshHomeOnConnectFailure(true);
		obj.setCacheHome(true);
		obj.setLookupHomeOnStartup(false);
		obj.setResourceRef(false);
		obj.setHomeInterface(IntegrationServiceClientHome.class);
		obj.setBusinessInterface(IntegrationServiceClient.class);
		obj.setJndiName(jndiName);
		
		Properties jndiProperties = new Properties();
		jndiProperties.put("org.omg.CORBA.ORBClass","com.ibm.CORBA.iiop.ORB");
		jndiProperties.put("java.naming.factory.initial","com.ibm.websphere.naming.WsnInitialContextFactory");
		jndiProperties.put("java.naming.factory.url.pkgs","com.ibm.ws.naming");
		jndiProperties.put("java.naming.provider.url",providerUrl);
		
		JndiTemplate jndiTemplate = new JndiTemplate(jndiProperties);
		
		obj.setJndiTemplate(jndiTemplate);
		
		return obj;
	}
	
	
}
