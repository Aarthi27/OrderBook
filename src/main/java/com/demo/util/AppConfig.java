package com.demo.util;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.demo.APIInfo;
import com.demo.MyClient;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.demo.controllers")
public class AppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public InternalResourceViewResolver getIRVR() {
		System.out.println("Inside Config Class view resolver");
		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
		irvr.setViewClass(JstlView.class);
		irvr.setPrefix("WEB-INF/jsp/");
		irvr.setSuffix(".jsp");
		return irvr;
	}

	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public MyClient myClient() {
		Map<BigDecimal, BigDecimal> buyMap = new TreeMap<>((a,b)-> b.compareTo(a));// Ascending order
		Map<BigDecimal, BigDecimal> sellMap = new TreeMap<>();//Descending order
		return new MyClient(APIInfo.getInstance().getWebsocketURL(), new MyMessageHandler(buyMap, sellMap));
		
	}


}
