package com.nms.catalog.service.impl.integration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.bsi.common.beans.Property;
import com.bsi.common.beans.Service;
import com.bsi.common.beans.Token;
import com.nms.catalog.domain.Tree;
import com.nms.catalog.service.CatalogService;

public class CatalogServiceImplIntegrationTest extends TestCase{
	CatalogService csImpl = null;
	private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImplIntegrationTest.class);
	ApplicationContext applicationContext = null;
	public static final String SERVICE_ID_1 = "3";
	public static final String SERVICE_ID_3 = "3";
	

	
	public CatalogServiceImplIntegrationTest(){
		applicationContext = new ClassPathXmlApplicationContext("/spring-config/catalog-beans.xml");
		csImpl=(CatalogService)applicationContext.getBean("catalogServiceImpl");
	}
	@Test
	public void testGetFirstLevelServices() {
		logger.debug("In CCatalogServiceImplIntegrationTest:getFirstLevelServices()");
		Tree servicesTree = csImpl.getFirstLevelServices();
		Assert.notNull(servicesTree, "Failed while getting the first level services");
	}
	
	@Test
	public void testGetServiceWithOutProperties(){
		logger.debug("In CatalogServiceImplIntegrationTest:testGetServiceWithProperties()");
		csImpl=(CatalogService)applicationContext.getBean("catalogServiceImpl");
		Service service = csImpl.getService("3",false);
		Assert.notNull(service, "Failed while getting the first level services");
		Assert.isTrue(service.getProperties().size() == 0, "The service details retrieved with properties");
	}
	
	@Test
	public void testGetServiceWithProperties(){
		logger.debug("In CatalogServiceImplIntegrationTest:testGetServiceWithProperties()");
		csImpl=(CatalogService)applicationContext.getBean("catalogServiceImpl");
		Service service = csImpl.getService("3",true);
		Assert.notNull(service, "Failed while getting the first level services");
		Assert.isTrue(service.getProperties().size() == 4, "The service properties count doesn't match");
	}
	
	/**
	 * Tests for updating the service properties.
	 */
	@Test
	public void testUpdateServiceProperties() {
		logger.debug("In CatalogServiceImplIntegrationTest:testUpdateServiceProperties()");
		Random r = new Random();
		int Low = 10;
		int High = 100;
		int R = r.nextInt(High-Low) + Low;
		String originalDisplaySize="";

		try {
			//Set properties = csImpl.getServiceProperties(SERVICE_ID_3);
			Set properties = csImpl.getServiceSpecificProperties(SERVICE_ID_3);
			List updatedPropertyList = new ArrayList();
			Iterator iter = properties.iterator();
			Property property = null;
			while (iter.hasNext()){
				property = (Property)iter.next();
				if (property.getPropertyId() == 1l){
					originalDisplaySize = property.getDisplaySize();
					property.setDisplaySize("" + R);
				}
				updatedPropertyList.add(property);
			}
			csImpl.updateServiceProperties(SERVICE_ID_3, new HashSet(updatedPropertyList));
			
			Set newProperties = csImpl.getServiceProperties(SERVICE_ID_3);
			Iterator newIter = newProperties.iterator();

			while (newIter.hasNext()){
				property = (Property)newIter.next();
				if (property.getPropertyId() == new Long(1)){
					Assert.isTrue(property.getDisplaySize() == "" + R, "The properties not updated correctly.");
				}
			}
			
			//Update the property value to original one
			//Set newProperties2 = csImpl.getServiceProperties(SERVICE_ID_3);
			Set newProperties2 = csImpl.getServiceSpecificProperties(SERVICE_ID_3);
			Iterator newIter2 = newProperties.iterator();

			while (newIter2.hasNext()){
				property = (Property)newIter2.next();
				if (property.getPropertyId() == new Long(1)){
					property.setDisplaySize(originalDisplaySize);
				}
			}
			csImpl.updateServiceProperties(SERVICE_ID_3, newProperties2);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}	
	
	@Test
	public void testGetAllServices() throws Exception{
		logger.debug("In CatalogServiceImplIntegrationTest:testGetAllServices()");
		csImpl=(CatalogService)applicationContext.getBean("catalogServiceImpl");
		List<Service> services = csImpl.getAllServices();
		Assert.isTrue(services.size() > 0, "Unable to get all the services");
	}

	@Test
	public void testGetToken() throws Exception{
		logger.debug("In CatalogServiceImplIntegrationTest:testGetToken()");
		csImpl=(CatalogService)applicationContext.getBean("catalogServiceImpl");
		Token token = csImpl.getToken(1l);
		Assert.isTrue(token !=null && token.getTokenName() != null, "Unable to get token");
	}
	
	@Test
	public void testGetServiceByName() throws Exception{
		logger.debug("In CatalogServiceImplIntegrationTest:testGetServiceByName()");
		csImpl=(CatalogService)applicationContext.getBean("catalogServiceImpl");
		Service service = csImpl.getServiceByServiceName("Maths tuition");
		Assert.isTrue(service !=null && service.getName() != null, "Unable to get token");
	}
}
