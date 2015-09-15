package com.bsi.client.managers;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bsi.common.beans.Person;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

public class PersonSrvsRegionManagerTest extends TestCase {
	private static Log log = LogFactory.getLog(ServiceManager.class);

	PersonSrvsRegionManager psrMgr = null;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext context = new FileSystemXmlApplicationContext(
				new String[]{
						"src/main/webapp/WEB-INF/BSI_bean_config.xml",
						"src/main/webapp/WEB-INF/BSI_Spring_config.xml"});
		psrMgr=new PersonSrvsRegionManager();
	}
	
	@Test
	public void testGetLocationMatchingSuppliers() {
		List regionIds = new ArrayList();
		//TODO ensure that supplier is already subscribed and is availabe in DB.
		regionIds.add(new Long(52));
		
		try {
			List<Person> person = psrMgr.getLocationMatchingPersons(regionIds, "S");
			assertTrue("Failed to get correct number of suppliers",person != null && person.size()==1);
		} catch (BSIException ex) {
			CommonLogger.logError(log, "Failed in PersonSrvsRegionManagerTest:testGetLocationMatchingSuppliers", ex);
			fail("Exception occured while testing PersonSrvsRegionManagerTest:testGetLocationMatchingSuppliers");
		}
	}

	@Test
	public void testGetLocationMatchingBuyers() {
		List regionIds = new ArrayList();
		//TODO ensure that supplier is already subscribed and is availabe in DB.
		regionIds.add(new Long(52));
		
		try {
			List<Person> person = psrMgr.getLocationMatchingPersons(regionIds, "B");
			assertTrue("Failed to get correct number of Buyers",person.size()==1);
		} catch (BSIException ex) {
			CommonLogger.logError(log, "Failed in PersonSrvsRegionManagerTest:testGetLocationMatchingBuyers", ex);
			fail("Exception occured while testing PersonSrvsRegionManagerTest:testGetLocationMatchingBuyers");
		}
	}
	
	public void testGetLocationMatchingSupplierrForAllCountries(){
		List<Long> regionIds = new ArrayList<Long>();
		//TODO ensure that supplier is already subscribed and is availabe in DB.
		regionIds.add(new Long("52"));
		
		try {
			List<Person> person = psrMgr.getLocationMatchingPersons(regionIds, "S");
			assertTrue("Failed to get correct number of Suppliers",person.size()==1);
		} catch (BSIException ex) {
			CommonLogger.logError(log, "Failed in PersonSrvsRegionManagerTest:testGetLocationMatchingSupplierrForAllCountries", ex);
			fail("Exception occured while testing PersonSrvsRegionManagerTest:testGetLocationMatchingSupplierrForAllCountries");
		}
	}
		
	}

