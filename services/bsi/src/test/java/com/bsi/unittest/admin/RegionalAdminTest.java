package com.bsi.unittest.admin;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bsi.client.actions.forms.PersonForm;
import com.bsi.client.managers.ObjectManager;
import com.bsi.client.managers.PersonManager;
import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.Person;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegionalAdminTest extends TestCase {

	private static PersonManager personMgr = null;
	private static Person newPerson = null;

	public RegionalAdminTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		personMgr = new PersonManager();
		super.setUp();
		// ApplicationContext context = new ClassPathXmlApplicationContext(
		// new String[]
		// {"/WEB-INF/BSI_bean_config.xml","/WEB-INF/BSI_Spring_config.xml"});
		ApplicationContext context = new FileSystemXmlApplicationContext(
				new String[]{
		//				"E:/NasirRepository/Personal/Project/NMS/apps/BSI/web/WEB-INF/BSI_bean_config.xml",
		//				"E:/NasirRepository/Personal/Project/NMS/apps/BSI/web/WEB-INF/BSI_Spring_config.xml"});
						"src/main/webapp/WEB-INF/BSI_bean_config.xml",
						"src/main/webapp/WEB-INF/BSI_Spring_config.xml"});

		
		// daoClass = (IDataAccess)context.getBean("hibernateImpl");

	}

	public void testACreateRegionalAdmin() {
		System.out.println("Testing for :testCreateRegionalAdmin ");
		String username = "nms9999@regionalAdmin.com";
		String password = "test1";

		PersonForm regionAdmin = new PersonForm();
		try {
			regionAdmin.setName("Nasir regional admin");
			regionAdmin.setEmail(username);
			regionAdmin.setAddress("123 Street,France");
			regionAdmin.setPassword(password);
			regionAdmin.setPhoneNumber("45444445");
			regionAdmin.setPersonType("RA");
			regionAdmin.setCountryCode("91");
			Person person = (Person)personMgr.createPerson(regionAdmin);
			activatePerson(person.getId());
		} catch (Exception ex) {
			ex.printStackTrace();
			assertTrue("An excpetion has occured while creating supplier",
					false);
		}

	}

	public void testBLogin() {
		try {
			String username = "nms9999@regionalAdmin.com";
			String password = "test1";

			HashMap userInputMap = new HashMap();
			userInputMap.put(BSIConstants.USERID, username);
			userInputMap.put(BSIConstants.PASSWORD, password);
			userInputMap.put(BSIConstants.IS_ACTIVE, "Y");
			List result = personMgr.login(userInputMap);
			System.out.println("The number of users got is " + result.size());
			assertEquals("Couldnot find the person", 1, result.size());

		} catch (Exception ex) {
			ex.printStackTrace();
			assertTrue("An excpetion has occured while creating supplier",
					false);
		}

	}

	public void testCDeleteRegionalAdmin() {

		// PersonForm personForm = new PersonForm();

		try {
			String username = "nms9999@regionalAdmin.com";
			String password = "test1";

			HashMap userInputMap = new HashMap();
			userInputMap.put(BSIConstants.USERID, username);
			userInputMap.put(BSIConstants.PASSWORD, password);

			List result = personMgr.login(userInputMap);

			newPerson = (Person) result.get(0);

			// Delete person;
			personMgr.deletePerson(newPerson.getId());
			assertTrue("Person delted.", true);

		} catch (Exception ex) {
			ex.printStackTrace();
			assertTrue("An excpetion has occured", false);

		}

	}

	private void activatePerson(Long objectID) throws Exception {
		List<Method> methods = new ArrayList<Method>();
		List<Object> argsList = new LinkedList<Object>();
		

		PropertyDescriptor pd = new PropertyDescriptor("isActive",
				Person.class);
		Method method = PropertyUtils.getWriteMethod(pd);
		methods.add(method);
		argsList.add("Y");

		pd = new PropertyDescriptor("isOACAuthenticated",
				Person.class);
		method = PropertyUtils.getWriteMethod(pd);
		methods.add(method);
		argsList.add("Y");
		
		//Activate the person.
		ObjectManager objMgr = new ObjectManager();
		Object object = objMgr.updatetObject(objectID, Person.class,
				methods.toArray(new Method[methods.size()]), argsList);
		
	}

}
