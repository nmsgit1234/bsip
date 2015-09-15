package com.nms.catalog.resources.integration;

import java.net.MalformedURLException;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.bsi.common.beans.Property;
import com.bsi.common.beans.Service;
import com.bsi.common.beans.Token;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;


public class CatalogResourcesIntegrationTest extends JerseyTest {
	private static final Logger logger = LoggerFactory.getLogger(CatalogResourcesIntegrationTest.class);
	private static final String CONTEXT_INFO = "/catalog/";
	
	protected AppDescriptor configure() {

		logger.info("\n*********Inside configure()******");
		
		return new WebAppDescriptor.Builder("com.nms.catalog.resources")				
				.contextPath("catalogservices").contextParam("contextConfigLocation", "classpath:spring-config/catalog-beans.xml")				
				.servletClass(ServletContainer.class).initParam("com.sun.jersey.api.json.POJOMappingFeature", "true")
				.contextListenerClass(ContextLoaderListener.class).build();
	}	
	
	@Test
	public void getFirstLevelServices(){
		String expectedResponse="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><tree id=\"0\"><item text=\"/\" child=\"1\" id=\"999999\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"><item text=\"Services\" child=\"1\" id=\"1\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"/></item></tree>";
		try {
			logger.info("************* Inside getFirstLevelServices()**************************");
			String path = CONTEXT_INFO + "firstLevelServices";
			String xmlResponse = sendGetRequest(path,MediaType.APPLICATION_XML);
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
	public void getServiceProperties(){
		try {
			logger.info("************* Inside getServiceProperties()**************************");
			String path = CONTEXT_INFO + "serviceproperties/3";
			String xmlResponse = sendGetRequest(path,MediaType.APPLICATION_JSON);
			ObjectMapper mapper = new ObjectMapper();
			Property[] properties = mapper.readValue(xmlResponse, Property[].class);
			Assert.assertTrue("Correct number of properties for the service not returned", properties.length == 4);
		} catch (Exception e) {
			logger.info(" Exception :" + e.getMessage());
			Assert.fail(e.getLocalizedMessage());
		}		
		
	}

	
	@Test
	public void getServiceDetailsWithProperties(){
		try {
			logger.info("************* Inside getServiceDetailsWithProperties()**************************");
			String path = CONTEXT_INFO + "servicedetails/3/true";
			String xmlResponse = sendGetRequest(path,MediaType.APPLICATION_JSON);
			ObjectMapper mapper = new ObjectMapper();
			Service service = mapper.readValue(xmlResponse, Service.class);
			Assert.assertTrue("The properties for the given service are not retrieved correctly", service.getProperties().size() > 0);
		} catch (Exception e) {
			logger.info(" Exception :" + e.getMessage());
			Assert.fail(e.getLocalizedMessage());
		}		
	}

	@Test
	public void getServiceDetailsWithOutProperties(){
		try {
			logger.info("************* Inside getServiceDetailsWithOutProperties()**************************");
			String path = CONTEXT_INFO + "servicedetails/3/false";
			String xmlResponse = sendGetRequest(path,MediaType.APPLICATION_JSON);
			ObjectMapper mapper = new ObjectMapper();
			Service service = mapper.readValue(xmlResponse, Service.class);
			Assert.assertTrue("The properties for the given service are not retrieved correctly", service.getProperties().size() == 0);
		} catch (Exception e) {
			logger.info(" Exception :" + e.getMessage());
			Assert.fail(e.getLocalizedMessage());
		}		
		
	}
	
	@Test
	public void updateServiceProperties(){
		String originalPropertyResponse="[{\"propertyId\":3,\"name\":\"Preferred time\",\"description\":\"Tuition timing\",\"isMandatory\":\"N\",\"displayType\":\"TF\",\"valueType\":\"String\",\"tokenId\":0,\"displaySize\":\"50\",\"dataSize\":\"50\"},{\"propertyId\":4,\"name\":\"Tuition type\",\"description\":\"Tuition type\",\"isMandatory\":\"N\",\"displayType\":\"DD\",\"valueType\":\"String\",\"tokenId\":1,\"displaySize\":\"50\",\"dataSize\":\"50\"},{\"propertyId\":1,\"name\":\"Which Grade?\",\"description\":\"For which grade you need tuition?\",\"isMandatory\":\"Y\",\"displayType\":\"TF\",\"valueType\":\"String\",\"tokenId\":0,\"displaySize\":\"50\",\"dataSize\":\"50\"},{\"propertyId\":2,\"name\":\"Instruction Medium\",\"description\":\"Instruction medium\",\"isMandatory\":\"Y\",\"displayType\":\"TF\",\"valueType\":\"String\",\"tokenId\":0,\"displaySize\":\"50\",\"dataSize\":\"50\"}]";
		String updatedProperty="[{\"propertyId\":3,\"name\":\"Preferred time\",\"description\":\"Tuition timing\",\"isMandatory\":\"N\",\"displayType\":\"TF\",\"valueType\":\"String\",\"tokenId\":0,\"displaySize\":\"50\",\"dataSize\":\"50\"},{\"propertyId\":4,\"name\":\"Tuition type\",\"description\":\"Tuition type\",\"isMandatory\":\"N\",\"displayType\":\"DD\",\"valueType\":\"String\",\"tokenId\":1,\"displaySize\":\"50\",\"dataSize\":\"50\"},{\"propertyId\":1,\"name\":\"Which Grade?\",\"description\":\"For which grade you need tuition?\",\"isMandatory\":\"Y\",\"displayType\":\"TF\",\"valueType\":\"String\",\"tokenId\":0,\"displaySize\":\"50\",\"dataSize\":\"50\"},{\"propertyId\":2,\"name\":\"Instruction Medium\",\"description\":\"Instruction medium\",\"isMandatory\":\"Y\",\"displayType\":\"TF\",\"valueType\":\"String\",\"tokenId\":0,\"displaySize\":\"50\",\"dataSize\":\"80\"}]";

		try {
			logger.info("************* Inside updateServiceProperties()**************************");
			String path = CONTEXT_INFO + "updateServiceProperties/3";
			//String xmlResponse = sendGetRequest(path,MediaType.APPLICATION_JSON);
			ObjectMapper mapper = new ObjectMapper();
			//Property[] service = mapper.readValue(expectedResponse, Property[].class);
			WebResource webResource = resource();
			String response = webResource.path(path).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).put(String.class,updatedProperty);
			Assert.assertTrue(response != null);
			//Revert back the change
			response = webResource.path(path).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).put(String.class,originalPropertyResponse);
		} catch (Exception e) {
			logger.info(" Exception :" + e.getMessage());
			Assert.fail(e.getLocalizedMessage());
		}		
	}

	@Test
	public void testGetAllServices() throws Exception{
		logger.info("************* Inside testGetAllServices()**************************");
		String path = CONTEXT_INFO + "allservices";
		String xmlResponse = sendGetRequest(path,MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		Service[] services = mapper.readValue(xmlResponse, Service[].class);
		Assert.assertTrue("Unable to get all the services",services.length > 0);
	}

	@Test
	public void testGetToken() throws Exception{
		logger.info("************* Inside testGetToken()**************************");
		String path = CONTEXT_INFO + "gettoken/1";
		String xmlResponse = sendGetRequest(path,MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		Token token = mapper.readValue(xmlResponse, Token.class);
		Assert.assertTrue("Unable to get all the services",token != null && token.getTokenName() != null);
	}

	@Test
	public void testGetServiceByName() throws Exception{
		logger.info("************* Inside testGetToken()**************************");
		String path = CONTEXT_INFO + "getservicebyname/Maths tuition";
		String xmlResponse = sendGetRequest(path,MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		Service service = mapper.readValue(xmlResponse, Service.class);
		Assert.assertTrue("Unable to get all the services",service != null && service.getName() != null);
	}
	
	private String sendGetRequest(String path,String acceptType) throws MalformedURLException {
		WebResource webResource = resource();
		String xmlResponse = webResource	.path(path).accept(acceptType).get(String.class);		
		return xmlResponse;
	}		
}