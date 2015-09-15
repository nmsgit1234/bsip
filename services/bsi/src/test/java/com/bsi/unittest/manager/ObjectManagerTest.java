package com.bsi.unittest.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bsi.client.managers.ObjectManager;

public class ObjectManagerTest extends TestCase {

	private ObjectManager objMgr = new ObjectManager();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext context = new FileSystemXmlApplicationContext(
				new String[]{
						"src/main/webapp/WEB-INF/BSI_bean_config.xml",
						"src/main/webapp/WEB-INF/BSI_Spring_config.xml"});
	}
	
	@SuppressWarnings("unchecked")
	public void testGetObjects(){
		try {
			
			HashMap criteria = new HashMap();
			criteria.put("tokenId", new Long("1"));
			List<Object> objs = objMgr.getObjects("Token",criteria);
			assertTrue("Unable to get token list", objs.size() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	public void testGetObjectsLike(){
		try {
			HashMap criteria = new HashMap();
			criteria.put("name", "NmsBuyer1");
			List<Object> objs = objMgr.getObjectsLike("com.bsi.common.beans.Person",criteria);
			assertTrue("Unable to get token list", objs.size() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
}
