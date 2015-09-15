/**
 * 
 */
package com.bsi.unittest.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bsi.client.managers.ServiceManager;
import com.bsi.common.beans.Property;
import com.bsi.common.beans.Service;
import com.bsi.unittest.builder.ServiceTestBuilder;
/**
 * Test class for testing the ServiceManager class.
 * 
 * @author nshaikh
 * 
 */
public class ServiceManagerTest extends TestCase {

	private ServiceTestBuilder serviceBuilder = null;
	private ServiceManager srvsMgr = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext context = new FileSystemXmlApplicationContext(
				new String[]{
						"src/main/webapp/WEB-INF/BSI_bean_config.xml",
						"src/main/webapp/WEB-INF/BSI_Spring_config.xml"});
		srvsMgr = new ServiceManager();
	}

	/**
	 * Tests for getting the specific service with ID and compares the service
	 * name
	 */
	public void testGetService() {
		serviceBuilder = getServiceBuilder();
		try {
			srvsMgr = new ServiceManager();
			Service srvs = srvsMgr.getService(serviceBuilder.SERVICE_ID_2);
			assertTrue("Service id didn't match", srvs.getName()
					.equalsIgnoreCase(serviceBuilder.SERVICE_ID_2_MATHS_TUTION));
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Tests for getting the properties of the service.
	 * 
	 */
	public void testGetServiceProperties() {
		serviceBuilder = getServiceBuilder();
		try {
			srvsMgr = new ServiceManager();
			Set properties = srvsMgr
					.getServiceProperties(serviceBuilder.SERVICE_ID_3);
			assertTrue("Returned properties doesn't match",
					properties.size() == 4);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Tests for getting the properties in a html table.
	 */
	public void testGetServicePropertiesXML() {
		serviceBuilder = getServiceBuilder();
		try {
			srvsMgr = new ServiceManager();
			String xml = srvsMgr
					.getServicePropertiesXML("3");
			assertTrue("Returned properties doesn't match",
					xml.contains("propertyId"));
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Tests for getting the list of all services
	 */
	public void testGetAllServices() {
		serviceBuilder = getServiceBuilder();
		try {
			srvsMgr = new ServiceManager();
			List services = srvsMgr.getAllServices();
			System.out.println("The services list is " + services);
			assertTrue("Couldnot get the service list", services.size() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Tests for getting the root level services.
	 */
	public void testGetFirstLevelServices() {
		serviceBuilder = getServiceBuilder();
		try {
			srvsMgr = new ServiceManager();
			String services = srvsMgr.getFirstLevelServicesAsXML();
			System.out.println("The services list is " + services);
			assertTrue("Couldnot get the service list", services.length() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Tests for getting the child services as xml.
	 * 
	 * @return
	 */
	public void testGetChildServicesAsXML() {
		serviceBuilder = getServiceBuilder();
		try {
			srvsMgr = new ServiceManager();
			//String xml = srvsMgr.getChildServicesAsXML(serviceBuilder.SERVICE_ID_1);
			String xml = srvsMgr.getChildServicesAsXML("0");
			System.out.println("The services list is " + xml);
			assertTrue("Couldnot get the service list", xml.length() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Tests for updating the service properties.
	 */
	public void testUpdateServiceProperties() {
		serviceBuilder = getServiceBuilder();
		try {
			srvsMgr = new ServiceManager();
			Set properties = srvsMgr.getServiceProperties(serviceBuilder.SERVICE_ID_1);
			List updatedPropertyList = new ArrayList();
			Iterator iter = properties.iterator();
			Property property = null;
			while (iter.hasNext()){
				property = (Property)iter.next();
				if (property.getPropertyId() == 4l){
					property.setDisplaySize("60");
				}
				updatedPropertyList.add(property);
			}
			srvsMgr.updateServiceProperties(serviceBuilder.SERVICE_ID_1, updatedPropertyList);
			//srvsMgr.updateServiceProperties(serviceBuilder.SERVICE_ID_2, properties);
			//assertTrue("Couldnot get the service list", xml.startsWith("<?xml"));
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	private ServiceTestBuilder getServiceBuilder() {
		serviceBuilder = new ServiceTestBuilder();
		return serviceBuilder;
	}
}