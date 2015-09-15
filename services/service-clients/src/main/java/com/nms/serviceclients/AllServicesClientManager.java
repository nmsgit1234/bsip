package com.nms.serviceclients;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nms.serviceclients.wsclients.catalog.CatalogWebserviceClient;

public class AllServicesClientManager {
	
	private static ApplicationContext applicationContext = null;
	static{
		applicationContext = new ClassPathXmlApplicationContext("/conf/services-client-conf.xml");
	}
	
	public static CatalogServiceClient getCatalogServiceClient(){
		return (CatalogWebserviceClient)applicationContext.getBean("catalogWebserviceClient");
	}

}
