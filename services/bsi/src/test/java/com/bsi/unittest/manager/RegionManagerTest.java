package com.bsi.unittest.manager;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bsi.client.actions.forms.LocationForm;
import com.bsi.client.managers.RegionManager;
import com.bsi.common.beans.Region;
import com.bsi.unittest.builder.RegionTestBuilder;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

/**
 * Junit test class for testing all the methods in RegionManager class.
 * 
 * @author nshaikh
 * 
 */
public class RegionManagerTest extends TestCase {

	private static Log log = LogFactory.getLog(RegionManagerTest.class);

	private RegionManager regionMgr = new RegionManager();
	private RegionTestBuilder regionBuilder = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// ApplicationContext context = new ClassPathXmlApplicationContext(
		// new String[]
		// {"/WEB-INF/BSI_bean_config.xml","/WEB-INF/BSI_Spring_config.xml"});
		ApplicationContext context = new FileSystemXmlApplicationContext(
				new String[]{
						//"E:/NasirRepository/Personal/Project/NMS/apps/BSI/web/WEB-INF/BSI_bean_config.xml",
						//"E:/NasirRepository/Personal/Project/NMS/apps/BSI/web/WEB-INF/BSI_Spring_config.xml"});
						"src/main/webapp/WEB-INF/BSI_bean_config.xml",
						"src/main/webapp/WEB-INF/BSI_Spring_config.xml"});
	}
	
	public void testSubscribedRegionIds() {
		LocationForm locn = new LocationForm();
		locn.setRegionId1("52");
		locn.setRegionId2("63");
		locn.setRegionId3("0");
		locn.setRegionId4("-1");
		locn.setRegionId5("-1");
		locn.setRegionId6("-1");

		try {
			List<Long> regionIds=locn.getSubscribedRegionIds();
			assertTrue("Problem occured while getting the recursive child regions ",regionIds.size() == 35);
		} catch (BSIException e) {
			CommonLogger.logError(log,"Error occured while getting the recursive child regions",e);
			fail("Error occured while getting the recursive child regions");
		}
	}

	public void testSubscribedRegionIds2() {
		LocationForm locn = new LocationForm();
		locn.setRegionId1("52");
		locn.setRegionId2("63");
		locn.setRegionId3("116");
		locn.setRegionId4("-1");
		locn.setRegionId5("-1");
		locn.setRegionId6("-1");

		try {
			List<Long> regionIds=locn.getSubscribedRegionIds();
			assertTrue("Problem occured while getting the recursive child regions ",regionIds.size() == 1);
		} catch (BSIException e) {
			CommonLogger.logError(log,"Error occured while getting the recursive child regions",e);
			fail("Error occured while getting the recursive child regions");
		}
	}

	/**
	 * Tests for getting the countries list.
	 */
	public void testGetRootRegions() {
		regionBuilder = getRegionTestBuilder();
		try {
			List countryList = regionMgr.getRootRegions();
			assertTrue("Unable to get country list", countryList.size() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Tests for getting the list of all states within India
	 */
	public void testGetRegion() {
		regionBuilder = getRegionTestBuilder();
		try {
			Region region = regionMgr.getRegion(RegionTestBuilder.REGION_ID_INDIA);
			assertTrue("Unable to get country list", region.getName()
					.equalsIgnoreCase("India"));
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Test for getting the parent region of a particular region.
	 */
	public void testGetParentRegion() {
		regionBuilder = getRegionTestBuilder();
		try {
			Region region = regionMgr
					.getParentRegion(RegionTestBuilder.REGION_ID_KARNATAKA);
			// assertTrue("Unable to get country list", region.getRegionId() ==
			// regionBuilder.REGION_ID_INDIA);
			assertTrue("Unable to get country list", region.getRegionId()
					.toString()
					.equals(RegionTestBuilder.REGION_ID_INDIA.toString()));
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	/**
	 * Test for getting the parent region of a particular region.
	 */
	public void testGetRecursiveChildRegions() {
		try {
			List<Long> regions = regionMgr.getRecursiveChildRegions(RegionTestBuilder.REGION_ID_KARNATAKA);
			assertTrue("Unable to get all cities for the state", regions.size() == 35);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	
	private RegionTestBuilder getRegionTestBuilder() {
		regionBuilder = new RegionTestBuilder();
		return regionBuilder;
	}

}
