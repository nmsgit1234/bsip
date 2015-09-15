package com.bsi.unittest;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.bsi.unittest.admin.RegionalAdminTest;
import com.bsi.unittest.buyer.BuyerTest;
import com.bsi.unittest.supplier.SupplierTest;

public class BSITestSuite extends TestSuite {

   public static Test suite( ) {
      TestSuite suite = new TestSuite( );
      suite.addTest(new BuyerTest("testCreateBuyer"));
      suite.addTest(new BuyerTest("testBuyerSubscribeService"));
      suite.addTest(new SupplierTest("testCreateSupplier"));
      suite.addTest(new SupplierTest("testSupplierSubscribeService"));
      suite.addTest(new RegionalAdminTest("testCreateRegionalAdmin"));
      suite.addTest(new RegionalAdminTest("testLogin"));
      suite.addTest(new BuyerTest("testDeleteBuyer"));
      suite.addTest(new SupplierTest("testDeleteSupplier"));
      suite.addTest(new RegionalAdminTest("testDeleteRegionalAdmin"));
      return suite;
   }
}
