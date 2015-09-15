package com.bsi.unittest.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bsi.client.actions.forms.LocationForm;
import com.bsi.client.actions.forms.PersonForm;
import com.bsi.client.managers.PersonManager;
import com.bsi.client.managers.PersonSrvsRegionManager;
import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.Person;
import com.bsi.common.beans.Service;
import com.bsi.unittest.builder.PersonTestBuilder;
import com.bsi.unittest.buyer.BuyerTest;
import com.nms.util.db.BSIException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SupplierTest extends TestCase {

	private static PersonManager personMgr = null;
	private static Person newPerson = null;
	private PersonTestBuilder personBuilder = null;
	private static PersonSrvsRegionManager personSrvsMgr = new PersonSrvsRegionManager();


	public SupplierTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		personMgr = new PersonManager();
		ApplicationContext context = new FileSystemXmlApplicationContext(
				new String[]{
						"src/main/webapp/WEB-INF/BSI_bean_config.xml",
						"src/main/webapp/WEB-INF/BSI_Spring_config.xml"});
	}

	public void testACreateSupplier() {
		personBuilder = getPersonTestBuilder();
		try {
			PersonForm supplierForm = personBuilder.createTestSupplierForm();
			personMgr.createPerson(supplierForm);
			assertEquals(true, true);
		} catch (Exception ex) {
			ex.printStackTrace();
			assertTrue("An excpetion has occured while creating supplier",
					false);
		}
	}

	public void testBSupplierSubscribeService() {
		personBuilder = getPersonTestBuilder();
		try {

			HashMap userInputMap = (HashMap) personBuilder
					.getSupplierLoginInfo();
			newPerson = getPersonObject(userInputMap);

			LocationForm locForm = new LocationForm();
			List regionIds = new ArrayList();
			regionIds.add(new Long("119"));
			personMgr.updateSubscribedServices(newPerson.getId().toString(),
					"3", regionIds, "S", null);

			System.out.println("Getting the person ************ ");

			Person testPerson = personMgr.getPerson(newPerson.getId()
					.toString());
			Set srvs = testPerson.getOfferedServices();
			if (srvs != null) {
				Object[] services = srvs.toArray();
				if (services.length > 0) {
					Long serviceId = ((Service) services[0]).getNodeId();
					System.out.println("The service id is " + serviceId);
					assertTrue("Success", true);
				}
			} else {
				System.out.println("The services returned is " + srvs);
				fail("Coudlnot find the subscribed services by the supplier");
			}
			System.out.println("Done processing ...............");

		} catch (Exception ex) {
			ex.printStackTrace();
			assertTrue("An excpetion has occured", false);
		}
	}

	public void testCGetBuyersForSupplier() {
		personBuilder = getPersonTestBuilder();
		try {
			BuyerTest buyerTest = new BuyerTest("testACreateBuyer");
			buyerTest.run();
			HashMap userInputMap = (HashMap) personBuilder
					.getSupplierLoginInfo();
			newPerson = getPersonObject(userInputMap);
			List buyerList = personMgr.getBuyersForSupplier(newPerson.getId()
					.toString(), "3", "119");
			assertTrue("Couldn't get the buyers for the subscribed service",
					buyerList.size() > 0);
			buyerTest.deleteBuyer();
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	public void testDGetPersonsForService(){
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getSupplierLoginInfo();
		try {
			//newPerson = getPersonObject(userInputMap);
			List ssrList = new ArrayList();
			ssrList.add("52");
			Set supplierList = personMgr.getPersonsForService(personBuilder.SERVICE_ID_2, ssrList,personBuilder.PERSON_TYPE_SUPPLIER);
			assertTrue("No buyers found", supplierList.size() > 0);
		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	/**
	 * Tests for the retrieving the person details based on the person id.
	 */
	public void testEGetPerson() {
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getSupplierLoginInfo();
		try {
			newPerson = getPersonObject(userInputMap);
			newPerson = personMgr.getPerson(newPerson.getId().toString());
			assertTrue("Unable to retrieve the person details",
					newPerson.getOfferedServices().size() > 0);

		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	/**
	 * Tests for getting all the suppliers from the system.
	 */
	public void testFListPersons() {
		personBuilder = getPersonTestBuilder();
		try {
			List persons = personMgr.listPersons(personBuilder.PERSON_TYPE_SUPPLIER);
			assertTrue("Unable to retrieve the person details",
					persons.size() > 0);

		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Tests for fetching all supplier subscribed region for a particular service
	 */
	public void testGGetSupplierSubSrvsRegions() {
		personBuilder = getPersonTestBuilder();
		HashMap userInputMap = (HashMap) personBuilder.getSupplierLoginInfo();
		try {
			newPerson = getPersonObject(userInputMap);
			List persons = personSrvsMgr.getSupplierSubSrvsRegions(newPerson.getId(),new Long(personBuilder.SERVICE_ID_3));
			assertTrue("Unable to retrieve the person details",
					persons.size() > 0);

		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}

	/**
	 * Gets the list of all suppliers service regions
	 */
	public void testHGetAllSupplierSrvsRegion() {
		try {
			List srsList = personSrvsMgr.getAllSupplierSrvsRegion();
			assertTrue("Unable to retrieve the person details",
					srsList.size() > 0);

		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	
	/**
	 * Gets the list of all suppliers for a particular service regions
	 */
	public void testIGetAllSupplierForLocation() {
		try {
			List srvsRegions = new ArrayList();
			srvsRegions.add(new Long("119"));
			List personList = personSrvsMgr.getLocationMatchingPersons(srvsRegions, "S");
			assertTrue("Unable to retrieve the person details",
					personList.size() > 0);

		} catch (Exception ex) {
			assertTrue("An excpetion has occured", false);
		}
	}
	public void testJDeleteSupplier() {

		// PersonForm personForm = new PersonForm();

		try {
			String username = "nms9999@supplier.com";
			String password = "test1";

			HashMap userInputMap = new HashMap();
			userInputMap.put(BSIConstants.USERID, username);
			userInputMap.put(BSIConstants.PASSWORD, password);

			List result = personMgr.login(userInputMap);

			newPerson = (Person) result.get(0);

			// Delete person;
			personMgr.deletePerson(newPerson.getId());
/*			BuyerTest buyerTest = new BuyerTest("testNDeleteBuyer");
			buyerTest.run();
*/			
			assertTrue("Person delted.", true);

		} catch (Exception ex) {
			ex.printStackTrace();
			assertTrue("An excpetion has occured", false);

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
