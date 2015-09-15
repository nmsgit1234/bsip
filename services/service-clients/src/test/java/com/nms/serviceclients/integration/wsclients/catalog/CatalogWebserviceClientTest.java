package com.nms.serviceclients.integration.wsclients.catalog;

import java.util.List;

import junit.framework.TestCase;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bsi.common.beans.Property;
import com.bsi.common.beans.Service;
import com.bsi.common.beans.Token;
import com.nms.serviceclients.wsclients.catalog.CatalogWebserviceClient;

public class CatalogWebserviceClientTest extends TestCase {
	CatalogWebserviceClient csClient = null;
	ApplicationContext applicationContext = null;
	public CatalogWebserviceClientTest(){
		applicationContext = new ClassPathXmlApplicationContext("/conf/services-client-conf.xml");
		csClient = (CatalogWebserviceClient)applicationContext.getBean("catalogWebserviceClient");
	}
	
	@Test
	public void testGetFirstLevelServices(){
		String expectedResponse="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><tree id=\"0\"><item text=\"/\" child=\"1\" id=\"999999\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"><item text=\"Services\" child=\"1\" id=\"1\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"/></item></tree>";
		String actualResponse = csClient.getFirstLevelServices();
		Assert.assertEquals(expectedResponse,actualResponse);
	}
	
	@Test
	public void testChildServices(){
		String expectedResponse="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><tree id=\"0\"><item text=\"/\" child=\"1\" id=\"999999\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"><item text=\"Services\" child=\"1\" id=\"1\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"/></item></tree>";
		String actualResponse = csClient.getChildServices("0");
		Assert.assertEquals(expectedResponse,actualResponse);
	}

	@Test
	public void testGetServicePropertiesJson(){
		String actualResponse = csClient.getServicePropertiesJson("3");
		ObjectMapper mapper = new ObjectMapper();
		Property[] properties;
		try {
			properties = mapper.readValue(actualResponse, Property[].class);
			Assert.assertTrue("The properties for the given service are not retrieved correctly", properties.length > 0);
		} catch (Exception ex) {
			Assert.fail("Exception occured in testGetServiceDetailsWithProperty() ");
		}
	}

	@Test
	public void testGetServiceDetailsWithProperty(){
		String actualResponse = csClient.getServiceDetails("3", true);
		ObjectMapper mapper = new ObjectMapper();
		Service service;
		try {
			service = mapper.readValue(actualResponse, Service.class);
			Assert.assertTrue("The properties for the given service are not retrieved correctly", service.getProperties().size() > 0);
		} catch (Exception ex) {
			Assert.fail("Exception occured in testGetServiceDetailsWithProperty() ");
		}
	}

	@Test
	public void testGetServiceDetailsWithOutProperty(){
		String actualResponse = csClient.getServiceDetails("3", false);
		ObjectMapper mapper = new ObjectMapper();
		Service service;
		try {
			service = mapper.readValue(actualResponse, Service.class);
			Assert.assertTrue("The properties for the given service are not retrieved correctly", service.getProperties().size() == 0);
		} catch (Exception ex) {
			Assert.fail("Exception occured in testGetServiceDetailsWithProperty() ");
		}
	}
	
	@Test
	public void testUpdateServiceProperties(){
		String propertyJson="[{\"propertyId\":3,\"name\":\"Preferred time\",\"description\":\"Tuition timing\",\"isMandatory\":\"N\",\"displayType\":\"TF\",\"valueType\":\"String\",\"tokenId\":0,\"displaySize\":\"50\",\"dataSize\":\"50\"},{\"propertyId\":4,\"name\":\"Tuition type\",\"description\":\"Tuition type\",\"isMandatory\":\"N\",\"displayType\":\"DD\",\"valueType\":\"String\",\"tokenId\":1,\"displaySize\":\"50\",\"dataSize\":\"50\"},{\"propertyId\":1,\"name\":\"Which Grade?\",\"description\":\"For which grade you need tuition?\",\"isMandatory\":\"Y\",\"displayType\":\"TF\",\"valueType\":\"String\",\"tokenId\":0,\"displaySize\":\"50\",\"dataSize\":\"50\"},{\"propertyId\":2,\"name\":\"Instruction Medium\",\"description\":\"Instruction medium\",\"isMandatory\":\"Y\",\"displayType\":\"TF\",\"valueType\":\"String\",\"tokenId\":0,\"displaySize\":\"50\",\"dataSize\":\"80\"}]";
		String actualResponse = csClient.updateServiceProperties("3", propertyJson);
		Assert.assertTrue(actualResponse.equalsIgnoreCase("Success"));
		//Assert.assertEquals(expectedResponse,actualResponse);
	}
	
	@Test
	public void testAllServices(){
		List<Service> services;
		try {
			services = csClient.getAllServices();
			Assert.assertTrue("All services not retrieved correctly", services.size() > 0);
		} catch (Exception ex) {
			Assert.fail("Exception occured in testAllServices() ");
		}
	}
	
	@Test
	public void testGetToken(){
		Token token;
		try {
			token = csClient.getToken("1");
			Assert.assertTrue("The Token value not retrieved correctly", token.getTokenName() != null );
		} catch (Exception ex) {
			Assert.fail("Exception occured in testGetServiceDetailsWithProperty() ");
		}
	}

	@Test
	public void testGetServiceByName(){
		Service service;
		try {
			service = csClient.getServiceByName("Maths tuition");
			Assert.assertTrue("The Service object not retrieved correctly", service.getName() != null );
		} catch (Exception ex) {
			Assert.fail("Exception occured in testGetServiceByName() ");
		}
	}
	
}
