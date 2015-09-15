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

import com.bsi.client.managers.PersonManager;
import com.bsi.client.managers.ServiceManager;
import com.bsi.common.beans.Property;
import com.bsi.common.beans.Service;
import com.bsi.unittest.builder.ServiceTestBuilder;
/**
 * Test class for testing the PersonManager class.
 * 
 * @author nshaikh
 * 
 */
public class PersonManagerTest extends TestCase {

	private ServiceTestBuilder serviceBuilder = null;
	private PersonManager personMgr = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext context = new FileSystemXmlApplicationContext(
				new String[]{
						"src/main/webapp/WEB-INF/BSI_bean_config.xml",
						"src/main/webapp/WEB-INF/BSI_Spring_config.xml"});
		personMgr = (PersonManager)context.getBean("personMgr");
	}

	public void testGetPersonsMatchingServiceProperties(){
//		personMgr.getPersonsForServiceProperties(serviceId, srvsRegnIds, personType, propertyValuesList)
	}
}