package com.bsi.unittest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllBSITests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllBSITests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(com.bsi.unittest.buyer.BuyerTest.class);
		suite.addTestSuite(com.bsi.unittest.supplier.SupplierTest.class);
		suite.addTestSuite(com.bsi.unittest.admin.RegionalAdminTest.class);
		suite.addTestSuite(com.bsi.unittest.manager.RegionManagerTest.class);
		suite.addTestSuite(com.bsi.unittest.manager.ServiceManagerTest.class);
		//$JUnit-END$
		return suite;
	}

}
