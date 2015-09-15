package com.nms.catalog.resources;

import java.net.MalformedURLException;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;


public class CatalogResourcesMockTest extends JerseyTest {
	private static final Logger logger = LoggerFactory.getLogger(CatalogResourcesMockTest.class);
	private static final String CONTEXT_INFO = "/catalog/";
	
	protected AppDescriptor configure() {

		logger.info("\n*********Inside configure()******");
		
		return new WebAppDescriptor.Builder("com.nms.catalog.resources")				
				.contextPath("catalogservices").contextParam("contextConfigLocation", "classpath:spring-config/catalog-beans-mock.xml")				
				.servletClass(ServletContainer.class)
				.contextListenerClass(ContextLoaderListener.class).build();
	}	
	
	@Test
	public void getFirstLevelServices(){
		String expectedResponse="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><tree id=\"0\"><item text=\"/\" child=\"1\" id=\"999999\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"><item text=\"Services\" child=\"1\" id=\"1\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"/></item></tree>";
		try {
			logger.info("************* Inside getFirstLevelServices()**************************");
			String path = CONTEXT_INFO + "firstLevelServices";
			String xmlResponse = sendGetRequest(path);
			String actualResponse= xmlResponse;			
			logger.info("expectedResponse:" + expectedResponse);
			logger.info("actualResponse:" + actualResponse);
			Assert.assertEquals(expectedResponse,actualResponse);
		} catch (Exception e) {
			logger.info(" Exception :" + e.getMessage());
			Assert.fail(e.getLocalizedMessage());
		}		
		
	}

	
	@Test
	public void getChildServices(){
		String expectedResponse="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><tree id=\"0\"><item text=\"/\" child=\"1\" id=\"999999\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"><item text=\"Services\" child=\"1\" id=\"1\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"/></item></tree>";
		try {
			logger.info("************* Inside getFirstLevelServices()**************************");
			String path = CONTEXT_INFO + "childServices/0";
			String xmlResponse = sendGetRequest(path);
			String actualResponse= xmlResponse;			
			logger.info("expectedResponse:" + expectedResponse);
			logger.info("actualResponse:" + actualResponse);
			Assert.assertEquals(expectedResponse,actualResponse);
		} catch (Exception e) {
			logger.info(" Exception :" + e.getMessage());
			Assert.fail(e.getLocalizedMessage());
		}		
	}

	private String sendGetRequest(String path) throws MalformedURLException {
		WebResource webResource = resource();

		String xmlResponse = webResource	.path(path).accept(MediaType.APPLICATION_XML).get(String.class);		
		return xmlResponse;
	}		

}
