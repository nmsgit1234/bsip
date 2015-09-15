package com.bsi.unittest.buyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bsi.client.actions.forms.BuyerForm;
import com.bsi.client.actions.forms.BuyerPropertiesForm;
import com.bsi.client.managers.PersonManager;
import com.bsi.client.managers.PersonSrvsRegionManager;
import com.bsi.common.beans.Person;
import com.bsi.common.beans.Service;
import com.bsi.unittest.builder.PersonTestBuilder;
import com.nms.util.db.BSIException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BuyerTest extends TestCase {

	private static PersonManager personMgr = new PersonManager();
	private static PersonSrvsRegionManager personSrvsMgr = new PersonSrvsRegionManager();
	
	private static Person newPerson = null;
	private PersonTestBuilder personBuilder = null;

	public BuyerTest(String name) {
		super(name);
	}

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

						// daoClass = (IDataAccess)context.getBean("hibernateImpl");
	}

	public void testACreateBuyer() {
		personBuilder = getPersonTestBuilder();
		try {
			BuyerForm buyerForm = personBuilder.createTestBuyerForm();
			personMgr.createBuyer(buyerForm);
			assertEquals(true, true);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	public void testBCreatePerson() {
		personBuilder = getPersonTestBuilder();
		try {
			deleteBuyer();
			BuyerForm buyerForm = personBuilder.createTestBuyerForm();
			personMgr.createPerson(buyerForm);
			assertEquals(true, true);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	public void testCBuyerLogin() {
		// Check if the buyer created properly.
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getBuyerLoginInfo();
		String personName = personBuilder.createTestBuyerForm().getName();

		try {
			newPerson = getPersonObject(userInputMap);
			assertEquals("Person not created", personName, newPerson.getName());
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	public void testDUpdateSubscribedServices() {
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getBuyerLoginInfo();
		// Region ids
		List regionIds = new ArrayList();
		regionIds.add(new Long(personBuilder.REGION_ID_8));

		try {
			newPerson = getPersonObject(userInputMap);
			BuyerPropertiesForm props = personBuilder.getBuyerPropertiesForm();
			List buyerPropList = props.getPropertiesValues();
			personMgr.updateSubscribedServices(newPerson.getId().toString(),
					personBuilder.SERVICE_ID_4, regionIds,
					personBuilder.PERSON_TYPE_BUYER, buyerPropList);
			assertTrue("Bueyr subscription succesfull", true);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Test for the getting the list of the buyers for specific service and region.
	 */
	public void testEGetBuyersForService(){
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getBuyerLoginInfo();
		try {
			newPerson = getPersonObject(userInputMap);
			List ssrList = new ArrayList();
			ssrList.add(personBuilder.REGION_ID_8);
			List buyersList = personMgr.getBuyersForService(personBuilder.SERVICE_ID_4, ssrList);
			assertTrue("No buyers found", buyersList.size() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	public void testFGetPersonsForService(){
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getBuyerLoginInfo();
		try {
			newPerson = getPersonObject(userInputMap);
			List ssrList = new ArrayList();
			ssrList.add(personBuilder.REGION_ID_8);
			Set buyersList = personMgr.getPersonsForService(personBuilder.SERVICE_ID_4, ssrList,personBuilder.PERSON_TYPE_BUYER);
			assertTrue("No buyers found", buyersList.size() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	/**
	 * Tests for the update of the person object
	 * 
	 */
	public void testGUpdatePerson() {
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getBuyerLoginInfo();
		try {
			newPerson = getPersonObject(userInputMap);
			newPerson.setEmail(personBuilder.UPDATED_EMAIL_BUYER);
			personMgr.updatePersonInfo(newPerson);
			newPerson = getPersonObject(userInputMap);
			newPerson = getPersonObject(userInputMap);
			assertEquals("Couldn't update person details",
					personBuilder.UPDATED_EMAIL_BUYER, newPerson.getEmail());

		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Tests for the unsubscribing of the buyer services.
	 */
	public void testHUnSubscribeService() {
		personBuilder = getPersonTestBuilder();
		try {
			HashMap userInputMap = (HashMap) personBuilder.getBuyerLoginInfo();
			newPerson = getPersonObject(userInputMap);
			Set services = newPerson.getServices();
			Iterator iter = services.iterator();
			int subscribedServicesBefore = services.size();
			while (iter.hasNext()) {
				Service service = (Service) iter.next();

				personMgr.unSubscribeService(newPerson.getId().toString(),
						service.getNodeId().toString(),
						newPerson.getPersonType());
			}
			newPerson = getPersonObject(userInputMap);
			services = newPerson.getServices();
			int subscribedServicesAfter = services.size();
			assertTrue("Couldn't unsubscribe", subscribedServicesAfter == 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}

	}
	
	/**
	 * Tests for the retrieving the person details based on the person id.
	 */
	public void testIGetPerson() {
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getBuyerLoginInfo();
		try {
			newPerson = getPersonObject(userInputMap);
			newPerson = personMgr.getPerson(newPerson.getId().toString());
			assertTrue("Unable to retrieve the person details",
					newPerson.getRequestedSevices().size() > 0);

		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	/**
	 * Tests for getting all the buyers from the system.
	 */
	public void testJListPersons() {
		personBuilder = getPersonTestBuilder();
		try {
			List persons = personMgr.listPersons(personBuilder.PERSON_TYPE_BUYER);
			assertTrue("Unable to retrieve the person details",
					persons.size() > 0);

		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	//TOD This method may be duplicate of 
	public void testKGetServiceMatchingBuyers() {
		personBuilder = getPersonTestBuilder();
		try {
			
			List ssrList = new ArrayList();
			ssrList.add(personBuilder.REGION_ID_8);

			List buyersList = personSrvsMgr.getServiceMatchingBuyers(personBuilder.SERVICE_ID_4, ssrList);
			assertTrue("Unable to retrieve the person details",
					buyersList.size() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	/**
	 * Tests for fetching all buyer subscribed region for a particular service
	 */
	/*
	public void testGetBuyerSrvsRegion() {
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getBuyerLoginInfo();
		try {
			newPerson = getPersonObject(userInputMap);
			BuyerSrvsRegion bsr = personSrvsMgr.getBuyerSrvsRegion(newPerson.getId(),new Long(personBuilder.SERVICE_ID_4));
			assertTrue("Unable to retrieve the service region details",
					bsr.getBsrId() != null);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	*/
	
	/**
	 * Tests for fetching all buyer subscribed region for a particular service
	 */
	public void testLGetBuyerSubSrvsRegions() {
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getBuyerLoginInfo();
		try {
			newPerson = getPersonObject(userInputMap);
			List bsrList = personSrvsMgr.getBuyerSubSrvsRegions(newPerson.getId(),new Long(personBuilder.SERVICE_ID_4));
			assertTrue("Unable to retrieve the service region details",
					bsrList.size() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	/**
	 * Gets the list of all buyer service regions
	 */
	public void testMGetAllBuyerSrvsRegion() {
		try {
			List bsrList = personSrvsMgr.getAllBuyerSrvsRegion();
			assertTrue("Unable to retrieve the person details",
					bsrList.size() > 0);

		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	/**
	 * Tests for deleting the buyer
	 */
	public void testNDeleteBuyer() {

		try {
			deleteBuyer();
			// Delete person;
			// personMgr.deletePerson(newPerson.getId());
			assertTrue("Unable to delete the buyer", true);
		} catch (Exception ex) {
			ex.printStackTrace();
			assertTrue("An excpetion has occured", false);
		}
	}

	public void deleteBuyer() throws Exception {
		try{
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getBuyerLoginInfo();
		newPerson = getPersonObject(userInputMap);
		// Delete person;
		personMgr.deletePerson(newPerson.getId());
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Based on the login input provided gets the person object retrieved from
	 * db.
	 * 
	 * @param userInputMap
	 *            login credentials
	 * @return Person person object
	 * @throws BSIException
	 */
	private Person getPersonObject(HashMap userInputMap) throws Exception {
		List result = personMgr.login(userInputMap);
		if (result == null || result.size() > 1)
			throw new Exception();
		newPerson = (Person) result.get(0);
		return newPerson;
	}

	/**
	 * Gets the new instance of the test builder.
	 * 
	 * @return PersonTestBuilder test builder
	 */
	private PersonTestBuilder getPersonTestBuilder() {
		personBuilder = new PersonTestBuilder();
		return personBuilder;
	}

}
