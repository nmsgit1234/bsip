package com.bsi.unittest;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


import com.bsi.unittest.BSIBeansBuilder;
import com.nms.db.IDataAccess;

/**
 * Test class for testing Data access generaic dao's.
 * @author nshaikh
 *
 */
public class DataAccessTest extends TestCase{
	private static IDataAccess daoClass = null;
	private static Log log = LogFactory.getLog(DataAccessTest.class);

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext context = new FileSystemXmlApplicationContext(
		        //new String[] {"com/nms/test/Spring_config.xml","com/nms/test/BSIbeanconfig.xml"});
		//		new String[] {"com/bsi/common/beans/test/TestSpring_config.xml","com/bsi/common/beans/test/TestBSIbeanconfig.xml"});
				new String[] {"src/test/resources/com/bsi/common/beans/test/TestBSIbeanconfig.xml",
				"src/main/webapp/WEB-INF/BSI_Spring_config.xml"});
		
		
		//daoClass = (IDataAccess)context.getBean("hibernateImpl");
	}

	public void setDaoClass(IDataAccess daoClass) {
		this.daoClass = daoClass;
	}
	
	/**
	 * Tests for the creation of valid object.
	 */
	public void testValidCreateObject(){
		daoClass.createObject(BSIBeansBuilder.getRoleObject());
		assertTrue(true);
	}

	/**
	 * Tests for the creation of invalid object. Expects exception from dao.
	 */
	public void testInValidCreateObject(){
		try{
			daoClass.createObject(new Object());
		}
		catch(Exception ex){
			log.error(ex);
			assertFalse(false);
		}
		assertTrue(true);
	}
	
	/**
	 * Tests for the object creation in List.
	 */
	public void testValidCreateObjectUsingList(){
		List<Object> objs = new ArrayList<Object>();
		objs.add(BSIBeansBuilder.getRoleObject());
		daoClass.createObjects(objs);
	}
	
	/**
	 * Tests for the invalid object creation in list.
	 */
	public void testInValidCreateObjectUsingList(){
		List<Object> objs = new ArrayList<Object>();
		objs.add(new Object());
		try {
			daoClass.createObjects(objs);
		}
		catch(Exception ex){
			log.error("Exception occured creating object in list",ex);
			assertFalse(false);
		}
		
	}

}
