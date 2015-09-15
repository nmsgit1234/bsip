package com.nms.serviceclients.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceClientConfig {
	private static Logger  log  = LoggerFactory.getLogger(ServiceClientConfig.class);
	
	private static Properties serviceClientProperties;

	public Properties getServiceClientProperties() {
		return serviceClientProperties;
	}

	public void setServiceClientProperties(
			Properties serviceClientProperties) {
		ServiceClientConfig.serviceClientProperties = serviceClientProperties;
	}
	
	public static String getPropertyValue(String keyName){
			return (String)serviceClientProperties.get(keyName);
	}
}
