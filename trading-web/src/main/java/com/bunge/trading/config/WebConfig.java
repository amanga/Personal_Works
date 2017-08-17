package com.bunge.trading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.bunge.trading.task.MessagePushTask;

@Configuration
@ComponentScan({"com.bunge.trading"})
//@EnableWebMvc
@Import({WebSocketConfig.class, MessagingConfiguration.class})
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		registry.viewResolver(resolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/jsp/**").addResourceLocations("/WEB-INF/jsp/");
	}

	@Bean
	@Scope(value="prototype")
	public MessagePushTask messagePushTask(){
		MessagePushTask message = new MessagePushTask();
		return message;
	}
	
	@Bean
	public TaskExecutor taskExecutor(){
		TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		return taskExecutor;
	}
}
