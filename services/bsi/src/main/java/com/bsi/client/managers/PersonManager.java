package com.bsi.client.managers;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import com.bsi.client.actions.forms.BuyerForm;
import com.bsi.client.actions.forms.LocationForm;
import com.bsi.client.actions.forms.PersonForm;
import com.bsi.client.actions.forms.PropertyValueForm;
import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.AdminServiceRegion;
import com.bsi.common.beans.BuyerSrvsRegion;
import com.bsi.common.beans.Group;
import com.bsi.common.beans.Person;
import com.bsi.common.beans.Property;
import com.bsi.common.beans.Region;
import com.bsi.common.beans.Role;
import com.bsi.common.beans.SupplierServicePropertyValues;
import com.bsi.common.beans.SupplierSrvsRegion;
import com.nms.db.IDataAccess;
import com.nms.util.db.BSIException;
import com.nms.util.db.RestrictionConstants;
import com.nms.util.log.CommonLogger;

public class PersonManager {

	private static Log log = LogFactory.getLog(PersonManager.class);

	private static IDataAccess dbAccessor = null;

	// TODO remove all the commented code.
	/*
	 * public void createPerson(ActionForm form) throws BSIException{
	 * 
	 * CommonLogger.logDebug(log,"In PersonManager:createPerson()");
	 * 
	 * Person personObj = extractPersonObjFromRequestForm(form);
	 * 
	 * 
	 * List serviceIds= getServiceIdFromRequest(form);
	 * 
	 * dbHandler = new HbntDBHandler();
	 * 
	 * Set serviceObjs =
	 * dbHandler.getObjectsRequested(serviceIds,Service.class,null);
	 * 
	 * personObj.setServices(serviceObjs);
	 * 
	 * personObj = (Person)dbHandler.addObject(personObj);
	 * 
	 * Long personId = personObj.getId();
	 * 
	 * //Create the service region //Fetch the countryId,stateId,locationId from
	 * the request.
	 * 
	 * 
	 * LocationManager locMgr = new LocationManager(); Long svsRegionId =
	 * locMgr.
	 * createServiceRegion(getFromRequest(form,"countryId"),getFromRequest
	 * (form,"stateId"),getFromRequest(form,"locationId"));
	 * 
	 * CommonLogger.logDebug(log,"The person details are :");
	 * CommonLogger.logDebug(log,"Name:" + personObj.getName() + "\n Address:" +
	 * personObj.getAddress() + "\n Eamil:" + personObj.getEmail() +
	 * "\n Phone Number:" + personObj.getPhoneNumber());
	 * 
	 * //Associate the service,serviceRegion with the person
	 * //personHandler.createSupplier(personObj,getServiceIdFromRequest(form));
	 * //String personType = personObj.getType(); String personType =
	 * personObj.getPersonType();
	 * 
	 * PersonSrvsRegionManager ssrMgr = new PersonSrvsRegionManager();
	 * //ssrMgr.createPersonSrvsRegion
	 * (personId,personType,serviceIds,svsRegionId);
	 * ssrMgr.createSupplierSrvsRegion(personId,serviceIds,svsRegionId);
	 * 
	 * }
	 */

	/*
	 * public void createBuyer(ActionForm form) throws BSIException{
	 * 
	 * CommonLogger.logDebug(log,"In PersonManager:createBuyer()");
	 * 
	 * Person personObj = extractPersonObjFromRequestForm(form);
	 * 
	 * List serviceIds= getServiceIdFromRequestForm(form);
	 * 
	 * dbHandler = new HbntDBHandler();
	 * 
	 * Set serviceObjs =
	 * dbHandler.getObjectsRequested(serviceIds,Service.class);
	 * 
	 * personObj.setServices(serviceObjs);
	 * 
	 * personObj = (Person)dbHandler.addObject(personObj);
	 * 
	 * Long personId = personObj.getId();
	 * 
	 * //Long personId = personHandler.createPerson(personObj,serviceIds);
	 * 
	 * //Create the service region //Fetch the countryId,stateId,locationId from
	 * the request.
	 * 
	 * LocationForm locForm = (LocationForm)form;
	 * 
	 * 
	 * LocationManager locMgr = new LocationManager(); Long svsRegionId =
	 * locMgr.
	 * createServiceRegion(locForm.getCountryId(),locForm.getStateId(),locForm
	 * .getLocationId());
	 * 
	 * CommonLogger.logDebug(log,"The person details are :");
	 * CommonLogger.logDebug(log,"Name:" + personObj.getName() + "\n Address:" +
	 * personObj.getAddress() + "\n Eamil:" + personObj.getEmail() +
	 * "\n Phone Number:" + personObj.getPhoneNumber());
	 * 
	 * //Associate the service,serviceRegion with the person
	 * //personHandler.createSupplier(personObj,getServiceIdFromRequest(form));
	 * String personType = personObj.getType();
	 * 
	 * BuyerSrvsRegion bsr = new BuyerSrvsRegion();
	 * 
	 * bsr.setBuyerId(personId); bsr.setServiceId((Long)serviceIds.get(0));
	 * bsr.setServiceRegionId(svsRegionId);
	 * 
	 * //Create buyer property values map Map bsrProps = new HashMap();
	 * BuyerForm buyerForm = (BuyerForm)form;
	 * 
	 * List props = buyerForm.getPropertiesValues();
	 * 
	 * for (int x=0;x<props.size();x++){ PropertyValueForm prop =
	 * (PropertyValueForm)props.get(x);
	 * CommonLogger.logDebug(log,"The property id for " + x + " is :" +
	 * prop.getPropertyId());
	 * CommonLogger.logDebug(log,"The property value for " + x + " is :" +
	 * prop.getPropertyValue());
	 * bsrProps.put(prop.getPropertyId(),prop.getPropertyValue()); }
	 * 
	 * bsr.setBsrPropValues(bsrProps);
	 * 
	 * bsr = (BuyerSrvsRegion)dbHandler.addObject(bsr); }
	 */

	public void setDbAccessor(IDataAccess dbAccessor) {
		this.dbAccessor = dbAccessor;
	}

	public void createBuyer(ActionForm form) throws BSIException {

		CommonLogger.logDebug(log, "In PersonManager:createBuyer()");

		try {
			Person personObj = extractPersonObjFromRequestForm(form);
			List serviceIds = getServiceIdFromRequestForm(form);
			// dbHandler = new HbntDBHandler();

			LocationForm locForm = (LocationForm) form;

			CommonLogger.logDebug(log,
					"The regionId1:" + locForm.getRegionId1());
			CommonLogger.logDebug(log,
					"The regionId2:" + locForm.getRegionId2());
			CommonLogger.logDebug(log,
					"The regionId3:" + locForm.getRegionId3());
			CommonLogger.logDebug(log,
					"The regionId4:" + locForm.getRegionId4());
			CommonLogger.logDebug(log,
					"The regionId5:" + locForm.getRegionId5());
			CommonLogger.logDebug(log,
					"The regionId16" + locForm.getRegionId6());

			// ServiceRegion srvsRegn =
			// locMgr.createServiceRegionObj(locForm.getCountryId(),locForm.getStateId(),locForm.getLocationId());

			// Long regionId =
			// locMgr.createServiceRegion(locForm.getCountryId(),locForm.getStateId(),locForm.getLocationId());
			Long regionId = locForm.getSubscribedRegionId();

			Map bsrProps = new HashMap();
			BuyerForm buyerForm = (BuyerForm) form;

			bsrProps = getPropertiesMap(buyerForm.getPropertiesValues());

			CommonLogger.logDebug(log, "The person details are :");
			CommonLogger.logDebug(
					log,
					"Name:" + personObj.getName() + "\n Address:"
							+ personObj.getAddress() + "\n Eamil:"
							+ personObj.getEmail() + "\n Phone Number:"
							+ personObj.getPhoneNumber());

			BSITransactionMgr txnMgr = new BSITransactionMgr();

			// Associate buyer with specific group.
			String personType = personObj.getPersonType();
			if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.BUYER)) {
				personObj.getGroups().add(getGroup(BSIConstants.GROUP_BUYER));
				personObj.getGroups()
						.add(getGroup(BSIConstants.GROUP_SUPPLIER));
			}
			personObj.setIsActive("Y");
			txnMgr.createBuyer(personObj, serviceIds, regionId, bsrProps);
		} catch (BSIException ex1) {
			throw ex1;
		} catch (Exception ex1) {
			CommonLogger.logError(log,
					"5028=Exception occured in PersonManager:createBuyer();",
					ex1);
			throw new BSIException("1001", "5028");
		}
	}

	public Map getPropertiesMap(List propertyValueForms) {
		Map propertyValueMap = new HashMap();

		if (propertyValueForms == null || propertyValueForms.size() == 0)
			return propertyValueMap;

		List props = propertyValueForms;

		for (int x = 0; x < props.size(); x++) {
			PropertyValueForm prop = (PropertyValueForm) props.get(x);
			CommonLogger.logDebug(log, "The property id for " + x + " is :"
					+ prop.getPropertyId());
			CommonLogger.logDebug(log, "The property value for " + x + " is :"
					+ prop.getPropertyValue());
			propertyValueMap.put(prop.getPropertyId(), prop.getPropertyValue());
		}
		return propertyValueMap;
	}

	public Set<SupplierServicePropertyValues> prepareSupplierProopertiesValueSet(List propertyValueForms) {
		Set<SupplierServicePropertyValues> propertyValueSet = new HashSet<SupplierServicePropertyValues>();

		if (propertyValueForms == null || propertyValueForms.size() == 0)
			return propertyValueSet;

		for (int x = 0; x < propertyValueForms.size(); x++) {
			PropertyValueForm prop = (PropertyValueForm) propertyValueForms.get(x);
			CommonLogger.logDebug(log, "The property id for " + x + " is :"
					+ prop.getPropertyId());
			CommonLogger.logDebug(log, "The property value for " + x + " is :"
					+ prop.getPropertyValue());
			SupplierServicePropertyValues ssrPropValue = new SupplierServicePropertyValues();
			ssrPropValue.setProperty_id(prop.getPropertyId());
			ssrPropValue.setProperty_value(prop.getPropertyValue());
			propertyValueSet.add(ssrPropValue);
		}
		return propertyValueSet;
	}	
	
	public void deletePerson(Long personId) throws BSIException {
		CommonLogger.logDebug(
				log,
				"In PersonManager.deletePerson(). personId= "
						+ personId.toString());

		try {
			BSITransactionMgr txnMgr = new BSITransactionMgr();
			txnMgr.deletePerson(personId);

			/*
			 * Person person = getPerson(personId.toString());
			 * deletePerson(person); //Delete the person service region.
			 * PersonSrvsRegionManager prMgr = new PersonSrvsRegionManager();
			 * prMgr
			 * .deletePersonSrvsRegion(person.getId(),person.getPersonType());
			 */

		} catch (BSIException ex1) {
			CommonLogger.logError(log,
					"Exception occured in PersonManager:deletePerson();", ex1);
			throw ex1;
		}

	}

	public void updatePersonInfo(Person newPersonObj) throws BSIException {

		CommonLogger.logDebug(log, "In PersonManager:updatePersonInfo()");
		try {
			dbAccessor.updateObject(newPersonObj);
		} catch (Exception ex2) {
			CommonLogger
					.logError(
							log,
							"5038=Exception occured in PersonManager:updatePersonInfo();",
							ex2);
			throw new BSIException("1001", "5038");
		}
	}

	public Object createPerson(ActionForm form) throws BSIException {
		CommonLogger.logDebug(log, "In PersonManager:createPerson()");
		Object obj = null;

		try {
			// dbHandler = new HbntDBHandler();
			Person personObj = extractPersonObjFromRequestForm(form);
			// Associate user with specific group
			String personType = personObj.getPersonType();
			Set groups = new HashSet();
			if (personType != null
					&& (personType.equalsIgnoreCase(BSIConstants.BUYER))) {
				// groups.add(getGroup(BSIConstants.GROUP_BUYER));
				personObj.getGroups().add(getGroup(BSIConstants.GROUP_BUYER));
				personObj.getGroups()
						.add(getGroup(BSIConstants.GROUP_SUPPLIER));
				personObj.setIsActive(BSIConstants.YES);
			} else if (personType != null
					|| personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
				// groups.add(getGroup(BSIConstants.GROUP_BUYER));
				personObj.getGroups().add(getGroup(BSIConstants.GROUP_BUYER));
				personObj.getGroups()
						.add(getGroup(BSIConstants.GROUP_SUPPLIER));
				personObj.setIsActive(BSIConstants.NO);
			} else if (personType != null
					&& (personType.equalsIgnoreCase(BSIConstants.REGION_ADMIN))) {
				// groups.add(getGroup(BSIConstants.GROUP_BUYER));
				// personObj.getGroups().add(getGroup(BSIConstants.GROUP_BUYER));
				// personObj.getGroups().add(getGroup(BSIConstants.GROUP_SUPPLIER));
				personObj.getGroups().add(
						getGroup(BSIConstants.GROUP_REGION_ADMIN));
				personObj.setIsActive(BSIConstants.YES);
			} else if (personType != null
					&& personType
							.equalsIgnoreCase(BSIConstants.GROUP_GLOBAL_ADMIN)) {
				// groups.add(getGroup(BSIConstants.GROUP_ADMIN));
				personObj.getGroups().add(
						getGroup(BSIConstants.GROUP_GLOBAL_ADMIN));
				personObj.setIsActive(BSIConstants.YES);
			}
			obj = dbAccessor.createObject(personObj);
		} catch (BSIException ex1) {
			String errorCode = ex1.getErrorCode();

			if (errorCode != null
					&& errorCode
							.equalsIgnoreCase(BSIConstants.UNIQUE_CONSTRAINT_VIOLATION)) {
				throw new BSIException("1006", ex1);
			} else {
				throw ex1;
			}
		} catch (Exception ex1) {
			CommonLogger.logError(log,
					"5028=Exception occured in PersonManager:createBuyer();",
					ex1);
			throw new BSIException("1001", "5028");
		}
		return obj;
	}

	/*
	 * public void subscribeService(String personId,String serviceId,List
	 * locnDetailList,String personType) throws BSIException {
	 * 
	 * CommonLogger.logDebug(log,"In PersonManager:subscribeService(),personId="
	 * + personId + ",serviceId=" + serviceId);
	 * 
	 * try {
	 * 
	 * List ssrList =
	 * getServiceRegionObjList(personId,serviceId,locnDetailList,personType);
	 * //Update the person object and SupplierServiceRegion object in a
	 * transaction.
	 * 
	 * BSITransactionMgr txnMgr = new BSITransactionMgr();
	 * txnMgr.subscribeService(personId,serviceId,ssrList,personType);
	 * 
	 * } catch(Exception ex1) { CommonLogger.logError(log,
	 * "5030=Exception occured in PersonManager:subscribeService();",ex1); throw
	 * new BSIException("1001","5030"); }
	 * 
	 * }
	 */

	public List getPersonServiceRegionObjList(String personId,
			String serviceId, List regionIds, String personType)
			throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonManager:getPersonServiceRegionObjList(),locnDetailList="
						+ regionIds + ",personType=" + personType);
		List ssrList = new ArrayList();

		try {
			// Create service regions for the buyer/supplier
			// LocationManager locMgr = new LocationManager();
			// Set srvsRegnIdList = locMgr.createServiceRegion(locnDetailList);

			// CommonLogger.logDebug(log,"The srvsRegnIdList is : " +
			// srvsRegnIdList);
			// Update person object with the service region ids.

			RegionManager regMgr = new RegionManager();
			Region region = null;

			SupplierSrvsRegion ssr = null;
			BuyerSrvsRegion bsr = null;
			AdminServiceRegion asr = null;

			Iterator iter = regionIds.iterator();

			if (personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
				while (iter.hasNext()) {
					ssr = new SupplierSrvsRegion();
					ssr.setSupplierId(new Long(personId));
					ssr.setServiceId(new Long(serviceId));
					region = regMgr.getRegion((Long) iter.next());
					ssr.setRegion(region);
					// ssr.setServiceRegionId( (Long) iter.next());
					ssrList.add(ssr);
				}
			} else if (personType.equalsIgnoreCase(BSIConstants.BUYER)) {
				while (iter.hasNext()) {
					bsr = new BuyerSrvsRegion();
					bsr.setBuyerId(new Long(personId));
					bsr.setServiceId(new Long(serviceId));
					region = regMgr.getRegion((Long) iter.next());
					bsr.setRegion(region);
					// bsr.setServiceRegionId( (Long) iter.next());
					ssrList.add(bsr);
				}
			} else if (personType.equalsIgnoreCase(BSIConstants.REGION_ADMIN)) {
				while (iter.hasNext()) {
					asr = new AdminServiceRegion();
					asr.setAdminId(new Long(personId));
					asr.setServiceId(new Long(serviceId));
					region = regMgr.getRegion((Long) iter.next());
					asr.setRegion(region);
					// bsr.setServiceRegionId( (Long) iter.next());
					ssrList.add(asr);
				}
			}

			CommonLogger.logDebug(log,
					"The service region list after processing is " + ssrList);
			// Update the person object and SupplierServiceRegion object in a
			// transaction.

			// BSITransactionMgr txnMgr = new BSITransactionMgr();
			// txnMgr.subscribeService(personId,serviceId,ssrList,personType);

		} catch (Exception ex1) {
			CommonLogger
					.logError(
							log,
							"5042=Exception occured in PersonManager:getServiceRegionObjList();",
							ex1);
			throw new BSIException("1001", "5042");
		}
		return ssrList;
	}

	/*
	 * public List getServiceRegionObjList(String personId,String serviceId,List
	 * locnDetailList,String personType) throws BSIException {
	 * CommonLogger.logDebug
	 * (log,"In PersonManager:getServiceRegionObjList(),locnDetailList=" +
	 * locnDetailList + ",personType=" + personType); List ssrList = new
	 * ArrayList();
	 * 
	 * try { //Create service regions for the buyer/supplier LocationManager
	 * locMgr = new LocationManager(); Set srvsRegnIdList =
	 * locMgr.createServiceRegion(locnDetailList);
	 * 
	 * CommonLogger.logDebug(log,"The srvsRegnIdList is : " + srvsRegnIdList);
	 * //Update person object with the service region ids.
	 * 
	 * SupplierSrvsRegion ssr = null; BuyerSrvsRegion bsr = null;
	 * 
	 * 
	 * Iterator iter = srvsRegnIdList.iterator();
	 * 
	 * if (personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) { while
	 * (iter.hasNext()) { ssr = new SupplierSrvsRegion(); ssr.setSupplierId(new
	 * Long(personId)); ssr.setServiceId(new Long(serviceId));
	 * ssr.setServiceRegionId( (Long) iter.next()); ssrList.add(ssr); } } else
	 * if (personType.equalsIgnoreCase(BSIConstants.BUYER)) { while
	 * (iter.hasNext()) { bsr = new BuyerSrvsRegion(); bsr.setBuyerId(new
	 * Long(personId)); bsr.setServiceId(new Long(serviceId));
	 * bsr.setServiceRegionId( (Long) iter.next()); ssrList.add(bsr); } }
	 * 
	 * CommonLogger.logDebug(log,"The service region list after processing is "
	 * + ssrList); //Update the person object and SupplierServiceRegion object
	 * in a transaction.
	 * 
	 * //BSITransactionMgr txnMgr = new BSITransactionMgr();
	 * //txnMgr.subscribeService(personId,serviceId,ssrList,personType);
	 * 
	 * } catch(Exception ex1) { CommonLogger.logError(log,
	 * "5042=Exception occured in PersonManager:getServiceRegionObjList();"
	 * ,ex1); throw new BSIException("1001","5042"); } return ssrList; }
	 */

	public void updateSubscribedServices(String personId, String serviceId,
			List locnDetailList, String personType, List propertiesList)
			throws BSIException {
		try {

			Map propsMap = null;
			Set<SupplierServicePropertyValues> supplierPropertiesSet = null;
			List updatedSrvsRegnList = null;
			if (propertiesList != null) {
				if (personType.equalsIgnoreCase(BSIConstants.SUPPLIER)){
					supplierPropertiesSet=prepareSupplierProopertiesValueSet(propertiesList);
				} else {
				propsMap = getPropertiesMap(propertiesList);
				}
			}

			List ssrList = getPersonServiceRegionObjList(personId, serviceId,
					locnDetailList, personType);

			// Update the service region with buyer properties value.
			if (personType != null
					&& (personType.equalsIgnoreCase(BSIConstants.BUYER) || personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) ) {
				updatedSrvsRegnList = new ArrayList();
				for (int x = 0; x < ssrList.size(); x++) {
					if (personType.equalsIgnoreCase(BSIConstants.BUYER)){
						BuyerSrvsRegion bsr = (BuyerSrvsRegion) (ssrList.get(x));
						bsr.setBsrPropValues(propsMap);
						updatedSrvsRegnList.add(bsr);
					} else if (personType.equalsIgnoreCase(BSIConstants.SUPPLIER)){
						SupplierSrvsRegion ssr = (SupplierSrvsRegion) (ssrList.get(x));
						ssr.setSsrPropValues(supplierPropertiesSet);
						updatedSrvsRegnList.add(ssr);
					}
				}
			} else {
				updatedSrvsRegnList = ssrList;
			}

			// Test
			if (log.isDebugEnabled()) {
				for (int y = 0; y < updatedSrvsRegnList.size(); y++) {
					Object obj = updatedSrvsRegnList.get(y);
					if (obj instanceof BuyerSrvsRegion) {
						Map properties = ((BuyerSrvsRegion) obj).getBsrPropValues();
						Iterator iter = properties.keySet().iterator();
						while (iter.hasNext()) {
							CommonLogger.logDebug(log, "The key is " + iter.next());
						}
					}
				}
			}

			BSITransactionMgr txnMgr = new BSITransactionMgr();
			txnMgr.updateSubscribedServices(personId, serviceId,
					updatedSrvsRegnList, personType);
		} catch (Exception ex1) {
			CommonLogger
					.logError(
							log,
							"5043=Exception occured in PersonManager:updateSubscribedServices();",
							ex1);
			throw new BSIException("1001", "5043");
		}

	}

	/*
	 * public void unSubscribeServiceLocation(String personId,String
	 * serviceId,String personType) throws BSIException {
	 * CommonLogger.logDebug(log
	 * ,"In PersonManager:unSubscribeServiceLocation(),personId=" + personId +
	 * ",serviceId=" + serviceId);
	 * 
	 * BSITransactionMgr txnMgr = new BSITransactionMgr();
	 * txnMgr.unSubscribeService(personId,serviceId,personType); }
	 */

	public void unSubscribeService(String personId, String serviceId,
			String personType) throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonManager:unSubscribeService(),personId=" + personId
						+ ",serviceId=" + serviceId);

		BSITransactionMgr txnMgr = new BSITransactionMgr();
		txnMgr.unSubscribeService(personId, serviceId, personType);
	}
	
	public Set<SupplierServicePropertyValues> getSupplierSubscribedSrvPropertyValues(String supplierId, String serviceId) throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonManager:getSupplierSubscribedSrvPropertyValues(),supplierId="
						+ supplierId + ",serviceId=" + serviceId);
		Set<SupplierServicePropertyValues> propValueSet = null;
		PersonSrvsRegionManager psrMgr = new PersonSrvsRegionManager();

		try{
			propValueSet = psrMgr.getSupplierSubSrvsRegionsPropertyValues(new Long(supplierId), new Long(serviceId));
		} catch (BSIException ex1) {
			throw ex1;
		} catch (Exception ex2) {
			CommonLogger
					.logError(
							log,
							"5034=Exception occured in PersonManager:getSupplierSubscribedSrvPropertyValues()",
							ex2);
			throw new BSIException("1001", "5076");
		}
		return propValueSet;
	}

	
	public List getBuyersForSupplier(String supplierId, String serviceId,
			String ssrId) throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonManager:getBuyersForSupplier(),supplierId="
						+ supplierId + ",serviceId=" + serviceId + ",ssrId="
						+ ssrId);

		List ssrList = new ArrayList();
		List newList = new ArrayList();

		PersonSrvsRegionManager psrMgr = new PersonSrvsRegionManager();

		try {

			if (ssrId == null || ssrId.trim().length() == 0) {
				List ssr = psrMgr.getSupplierSubSrvsRegions(
						new Long(supplierId), new Long(serviceId));
				for (int x = 0; x < ssr.size(); x++) {
					ssrList.add(((Region) ssr.get(x)).getRegionId());
				}
			} else {
				ssrList.add(new Long(ssrId));
			}

			CommonLogger.logDebug(log,
					"The ssrList PersonManager.getBuyersForSupplier () is "
							+ ssrList);

			List bsrList = psrMgr.getServiceMatchingBuyers(serviceId, ssrList);

			CommonLogger.logDebug(log,
					"The bsrList PersonManager.getBuyersForSupplier () is "
							+ bsrList);

			// Get the service properties.
			ServiceManager srvsMgr = new ServiceManager();
			Set srvsProperties = srvsMgr.getServiceProperties(serviceId);

			CommonLogger.logDebug(log, "The srvsProperties are "
					+ srvsProperties);

			HashMap propMap = new HashMap();
			Iterator iter = srvsProperties.iterator();
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				propMap.put(prop.getPropertyId().toString(), prop.getName());
			}
			//
			CommonLogger.logDebug(log, "The propMap is " + propMap);
			/*
			 * Get the location names Map countryMap = new HashMap(); Map
			 * stateMap = new HashMap(); Map locationMap = new HashMap();
			 */

			List propList = new ArrayList();
			List buyerIdList = new ArrayList();

			// Extract buyer property values map from BuyerServiceRegion list.
			for (int z = 0; z < bsrList.size(); z++) {
				propList.add(((BuyerSrvsRegion) bsrList.get(z))
						.getBsrPropValues());
				buyerIdList
						.add(((BuyerSrvsRegion) bsrList.get(z)).getBuyerId());
			}
			//

			// dbHandler = new HbntDBHandler();
			Set buyerSet = dbAccessor.getObjectsRequested(null, buyerIdList,
					Person.class, null);

			Object[] buyers = buyerSet.toArray();

			// Update the property id with the name.
			Map newMap = null;

			CommonLogger.logDebug(log, "The propList : " + propList);

			for (int y = 0; y < propList.size(); y++) {
				newMap = new HashMap();
				Person buyer = (Person) buyers[y];
				newMap.put("Buyer Id", buyer.getId());

				// Create a link for viewing the buyer details
				String buyerLink = "<a href=\"ViewBuyer.do?"
						+ BSIConstants.PERSON_ID + "=" + buyer.getId() + "&"
						+ BSIConstants.ACTION_TYPE + "=" + BSIConstants.VIEW
						+ "\">" + buyer.getName() + "</a>";

				CommonLogger.logDebug(log, "The buyer link is " + buyerLink);
				newMap.put("Buyer Name", buyerLink);

				Map bsrPropMap = (Map) propList.get(y);
				Iterator iter2 = bsrPropMap.keySet().iterator();

				while (iter2.hasNext()) {
					String idKey = (String) iter2.next();
					String idValue = (String) bsrPropMap.get(idKey);

					String nameKey = (String) propMap.get(idKey);
					newMap.put(nameKey, idValue);
				}
				newList.add(newMap);
			}

		} catch (BSIException ex1) {
			throw ex1;
		} catch (Exception ex2) {
			CommonLogger
					.logError(
							log,
							"5034=Exception occured in PersonManager:getBuyersForSupplier()",
							ex2);
			throw new BSIException("1001", "5034");
		}
		return newList;
	}

	/*
	 * public Set getSuppliersForService(String serviceId, List srvsRegnIds)
	 * throws BSIException { CommonLogger.logDebug(log,
	 * "In PersonManager:getSuppliersForService(),serviceId=" + serviceId +
	 * ",ssrId=" + srvsRegnIds);
	 * 
	 * List ssrList = new ArrayList(); Set suppSet = null;
	 * 
	 * PersonSrvsRegionManager psrMgr = new PersonSrvsRegionManager();
	 * 
	 * try {
	 * 
	 * CommonLogger.logDebug(log, "The ssrList  is " + ssrList); suppSet =
	 * psrMgr.getServiceMatchingPersons(serviceId, srvsRegnIds,
	 * BSIConstants.SUPPLIER); CommonLogger.logDebug(log,
	 * "The suppList PersonManager.getSuppliersForService () is " + suppSet);
	 * 
	 * } catch (BSIException ex1) { throw ex1; } catch (Exception ex2) {
	 * CommonLogger .logError( log,
	 * "5044=Exception occured in PersonManager:getSuppliersForService()", ex2);
	 * throw new BSIException("1001", "5044"); } return suppSet; }
	 */

	public List getBuyersForService(String serviceId, List srvsRegnIds)
			throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonManager:getBuyersForService(),serviceId=" + serviceId
						+ ",ssrId=" + srvsRegnIds);

		// List bsrList = new ArrayList();
		// Set buyerSet = new HashSet();
		List buyerList = null;

		PersonSrvsRegionManager psrMgr = new PersonSrvsRegionManager();

		try {
			buyerList = psrMgr.getServiceMatchingBuyers(serviceId, srvsRegnIds);
			// buyerSet.addAll(buyerList);
			CommonLogger.logDebug(log,
					"The suppList PersonManager.getBuyersForService () is "
							+ buyerList);

		} catch (BSIException ex1) {
			throw ex1;
		} catch (Exception ex2) {
			CommonLogger
					.logError(
							log,
							"5050=Exception occured in PersonManager:getBuyersForService()",
							ex2);
			throw new BSIException("1001", "5055");
		}
		return buyerList;
	}

	/*
	 * public Set getPersonsForService(String serviceId, List srvsRegnIds,String
	 * personType) throws BSIException { CommonLogger.logDebug(log,
	 * "In PersonManager:getPersonsForService(),serviceId=" + serviceId +
	 * ",ssrId=" + srvsRegnIds);
	 * 
	 * List ssrList = new ArrayList(); Set suppSet = null;
	 * 
	 * PersonSrvsRegionManager psrMgr = new PersonSrvsRegionManager();
	 * 
	 * try {
	 * 
	 * CommonLogger.logDebug(log, "The ssrList  is " + ssrList);
	 * 
	 * suppSet = psrMgr.getServiceMatchingSuppliers(serviceId, srvsRegnIds);
	 * 
	 * CommonLogger.logDebug(log,
	 * "The suppList PersonManager.getSuppliersForService () is " + suppSet);
	 * 
	 * } catch (BSIException ex1) { throw ex1; } catch (Exception ex2) {
	 * CommonLogger.logError(log,
	 * "5044=Exception occured in PersonManager:getSuppliersForService()", ex2);
	 * throw new BSIException("1001", "5044"); } return suppSet; }
	 */

	public Set getPersonsForService(String serviceId, List srvsRegnIds,
			String personType) throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonManager:getPersonsForService(),serviceId="
						+ serviceId + ",ssrId=" + srvsRegnIds);

		List ssrList = new ArrayList();
		Set personSet = null;

		PersonSrvsRegionManager psrMgr = new PersonSrvsRegionManager();

		try {

			CommonLogger.logDebug(log, "The ssrList  is " + ssrList);

			if (serviceId == null || serviceId.equalsIgnoreCase("null") || serviceId.trim().length() == 0) {
				List<Person> pesons = psrMgr.getLocationMatchingPersons(srvsRegnIds,
						personType);
				
				personSet = new HashSet<Person>(pesons);
				
			} else {
				personSet = psrMgr.getServiceMatchingPersons(serviceId,
						srvsRegnIds, personType);
			}
			CommonLogger.logDebug(log,
					"The suppList PersonManager.getPersonsForService () is "
							+ personSet);

		} catch (BSIException ex1) {
			throw ex1;
		} catch (Exception ex2) {
			CommonLogger
					.logError(
							log,
							"5044=Exception occured in PersonManager:getPersonsForService()",
							ex2);
			throw new BSIException("1001", "5044");
		}
		return personSet;
	}


	public Set getPersonsMatchingServiceProperties(String serviceId, List srvsRegnIds,
			String personType,List<PropertyValueForm> propertyValuesList) throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonManager:getPersonsMatchingServiceProperties(),serviceId="
						+ serviceId + ",ssrId=" + srvsRegnIds);

		if (propertyValuesList == null || propertyValuesList.size() == 0) 
			return getPersonsForService(serviceId,srvsRegnIds,personType);
		
		List ssrList = new ArrayList();
		Set personSet = null;

		PersonSrvsRegionManager psrMgr = new PersonSrvsRegionManager();
		//TODO need to revisit the implementation.
		try {
			
			//Get the SSR id's for the given property values.
			String selectValues = "id,count(id) as totalProperties ";
			Set<Map<String,Object>> criteriaSet = new HashSet<Map<String,Object>>();
			for (PropertyValueForm propertyForm:propertyValuesList ){
				Map<String, Object> criteriaMap = new HashMap<String,Object>();
				criteriaMap.put("property_id", propertyForm.getPropertyId());
				criteriaMap.put("property_value", propertyForm.getPropertyValue());
				criteriaSet.add(criteriaMap);
			}
			int matchingPropertyCount = findMatchingPropertyCount(propertyValuesList);
			Map<String,String> groupCriteria = new HashMap<String,String>();
			groupCriteria.put(RestrictionConstants.GROUP_BY, "id");
			
			List<Object[]> objects = dbAccessor.getObjectsFromSQlqueryWithAggregateFunctions("supplier_service_property_values", selectValues, criteriaSet, groupCriteria);
			List<BigInteger> matchingSSRIds = new ArrayList<BigInteger>();
			if (objects != null && objects.size() > 0){
				for (Object[] obj:objects){
					if (obj.length == 2) {
						int countOfPropertiesMatch = ((BigInteger)obj[1]).intValue();
						if (countOfPropertiesMatch == matchingPropertyCount)
							matchingSSRIds.add((BigInteger)obj[0]);
					}
				}
			}
			if (matchingSSRIds.size() > 0 && serviceId != null && serviceId.trim().length() > 0)  {
			    personSet = psrMgr.getServicePropertyMatchingPersons(serviceId,matchingSSRIds, srvsRegnIds, personType);
			} 
/*			else {
				List<Person> pesons = psrMgr.getLocationMatchingPersons(srvsRegnIds,
						personType);
				personSet = new HashSet<Person>(pesons);
			}
*/			CommonLogger.logDebug(log,
					"The suppList PersonManager.getPersonsMatchingServiceProperties () is "
							+ personSet);
			

		} catch (BSIException ex1) {
			throw ex1;
		} catch (Exception ex2) {
			CommonLogger
					.logError(
							log,
							"5044=Exception occured in PersonManager:getPersonsForService()",
							ex2);
			throw new BSIException("1001", "5044");
		}
		return personSet;
	}

	
	
	private int findMatchingPropertyCount(List<PropertyValueForm> propertyValuesList) {
		//TODO need to revisit this logic.
/*		Set<String> propertyIdSet = new HashSet<String>();
		for (PropertyValueForm property:propertyValuesList){
			propertyIdSet.add(property.getPropertyId());
		}
*/		return propertyValuesList.size();
	}

	/**
	 * Gets the list of persons matching the property values.
	 * @param serviceId
	 * @param srvsRegnIds
	 * @param personType
	 * @param propertyValuesList
	 * @return
	 * @throws BSIException
	 */
	public Set getPersonsForServiceProperties(String serviceId, List srvsRegnIds,
			String personType, List propertyValuesList) throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonManager:getPersonsForServiceProperties(),serviceId="
						+ serviceId + ",ssrId=" + srvsRegnIds);
		
		if (propertyValuesList == null || propertyValuesList.size() == 0){
			return getPersonsForService(serviceId,srvsRegnIds,personType);
		}

		//Get the service region id's matching the property values.
		List<Long> srIds = new ArrayList<Long>();
		PropertiesManager propsMgr = new PropertiesManager();
		if (personType.equalsIgnoreCase(BSIConstants.BUYER)){
			srIds=propsMgr.getBSRIDsForMatchingPropertyValues();
		} else if (personType.equalsIgnoreCase(BSIConstants.SUPPLIER)){
			srIds=propsMgr.getBSRIDsForMatchingPropertyValues();
		}
		
		List ssrList = new ArrayList();
		Set personSet = null;

		PersonSrvsRegionManager psrMgr = new PersonSrvsRegionManager();

		try {

			CommonLogger.logDebug(log, "The ssrList  is " + ssrList);

			if (serviceId == null || serviceId.equalsIgnoreCase("null") || serviceId.trim().length() == 0) {
				List<Person> pesons = psrMgr.getLocationMatchingPersons(srvsRegnIds,
						personType);
				
				personSet = new HashSet<Person>(pesons);
				
			} else {
				personSet = psrMgr.getServiceMatchingPersons(serviceId,
						srvsRegnIds, personType);
			}
			CommonLogger.logDebug(log,
					"The suppList PersonManager.getPersonsForService () is "
							+ personSet);

		} catch (BSIException ex1) {
			throw ex1;
		} catch (Exception ex2) {
			CommonLogger
					.logError(
							log,
							"5044=Exception occured in PersonManager:getPersonsForService()",
							ex2);
			throw new BSIException("1001", "5044");
		}
		return personSet;
	}
	
	
	
	public Person extractPersonObjFromRequestForm(ActionForm form)
			throws BSIException {
		PersonForm personForm = (PersonForm) form;
		CommonLogger.logDebug(log,
				"The person type before is " + personForm.getPersonType());

		Person personObj = new Person();

		try {
			PropertyUtils.copyProperties(personObj, personForm);
			CommonLogger.logDebug(log,
					"The person type after is " + personObj.getPersonType());
			/*
			 * personObj.setAddress(personForm.getAddress());
			 * personObj.setEmail(personForm.getEmail());
			 * personObj.setName(personForm.getName());
			 * personObj.setPhoneNumber(personForm.getPhoneNumber());
			 * personObj.setPassword(personForm.getPassword());
			 * 
			 * 
			 * 
			 * String personType = personForm.getPersonType();
			 * CommonLogger.logDebug(log,"The person type is " + personType );
			 * if (personType != null &&
			 * personType.equalsIgnoreCase("Supplier")){ personObj.setType("S");
			 * } else if (personType != null &&
			 * personType.equalsIgnoreCase("Buyer")){ personObj.setType("B"); }
			 */

		} catch (Exception ex1) {
			CommonLogger
					.logError(
							log,
							"5037=Exception occured in PersonManager:extractPersonObjFromRequestForm()",
							ex1);
			throw new BSIException("1001", "5037");
		}

		return personObj;
	}

	/*
	 * public List getBuyerPropertyValuesFromRequestForm(ActionForm form) throws
	 * BSIException { PersonForm personForm = (PersonForm) form;
	 * 
	 * CommonLogger.logDebug(log, "The person details are :");
	 * CommonLogger.logDebug(log, "The person type is " +
	 * personForm.getPersonType());
	 * 
	 * BuyerForm buyerForm = (BuyerForm) form;
	 * 
	 * List props = buyerForm.getPropertiesValues();
	 * 
	 * for (int x = 0; x < props.size(); x++) { PropertyValueForm prop =
	 * (PropertyValueForm) props.get(x); CommonLogger.logDebug(log,
	 * "The property id for " + x + " is :" + prop.getPropertyId());
	 * CommonLogger.logDebug(log, "The property value for " + x + " is :" +
	 * prop.getPropertyValue()); } return props;
	 * 
	 * }
	 */

	public Person getPerson(String id) throws BSIException {

		CommonLogger.logDebug(log,
				"In PersonManager:getPerson() \n supplier id =" + id);
		Long personId = null;
		Person personObj = null;

		try {
			if (id != null) {
				personId = Long.valueOf(id);

			}
			// dbHandler = new HbntDBHandler();

			// Person personObj = personHandler.getPerson(personId);
			Method[] methods = new Method[3];
			methods[0] = Person.class.getMethod("getOfferedServices", null);
			methods[1] = Person.class.getMethod("getRequestedSevices", null);
			methods[2] = Person.class.getMethod("getGroups", null);
			personObj = (Person) dbAccessor.getObjectRequested(personId,
					Person.class, methods);

			CommonLogger.logDebug(log, "The person details are :");
			CommonLogger.logDebug(
					log,
					"Name:" + personObj.getName() + "\n Address:"
							+ personObj.getAddress() + "\n Email:"
							+ personObj.getEmail() + "\n Phone Number:"
							+ personObj.getPhoneNumber());
		} catch (Exception ex) {
			CommonLogger.logError(log,
					"5004: Exception occured in PersonManager:getPerson()", ex);
			throw new BSIException("1001", "5004");
		}

		return personObj;

	}

	/*
	 * public Set getRoles(Long id) throws BSIException {
	 * CommonLogger.logDebug(log,"In PersonManager:getRoles() \n groupID id =" +
	 * id); Long groupID = null; Person personObj = null; Set roles = null;
	 * Group group = null;
	 * 
	 * try { //if(id != null) // groupID = Long.valueOf(id);
	 * 
	 * dbHandler = new HbntDBHandler();
	 * 
	 * //Person personObj = personHandler.getPerson(personId); Method[] methods
	 * = new Method[1]; methods[0] = Group.class.getMethod("getRoles",null);
	 * List objIds = new ArrayList(); objIds.add(id); group =
	 * (Group)dbHandler.getObjectRequested(id,Group.class,methods); roles =
	 * group.getRoles();
	 * 
	 * Iterator iter = roles.iterator(); while(iter.hasNext()) { Role role =
	 * (Role)iter.next(); System.out.println("The role name is" +
	 * role.getName()); CommonLogger.logDebug(log, "The role details are :");
	 * CommonLogger.logDebug(log, "Name:" + role.getName()); } } catch(Exception
	 * ex){ CommonLogger.logError(log,
	 * "5004: Exception occured in PersonManager:getPerson()",ex); throw new
	 * BSIException("1001","5004"); } return roles; }
	 */

	public List listPersons(String personType) throws BSIException {
		if (personType == null || personType.length() == 0) {
			throw new BSIException("1003",
					"Exception occured in PersonManager.listPersons() :");
		}

		List persons = null;

		if (personType.equalsIgnoreCase(BSIConstants.BUYER)) {
			persons = listBuyers();
		} else if (personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
			persons = listSuppliers();

		}
		return persons;

	}

	//TODO select only active suppliers
	public List listSuppliers() throws BSIException {

		CommonLogger.logDebug(log, "In PersonManager:listSuppliers()");
		List supplierList = null;
		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = " from Person as person where person.personType=?";
			// param values
			List paramList = new ArrayList();
			paramList.add("S");
			supplierList = dbAccessor.getObjectsFromQuery(hqlQuery, paramList,
					null);

			Person suppObj = null;

			if (log.isDebugEnabled()) {
				CommonLogger.logDebug(log, "The supplier list contains :");

				for (int x = 0; x < supplierList.size(); x++) {
					suppObj = (Person) supplierList.get(x);
					CommonLogger.logDebug(log, "ame:" + suppObj.getName()
							+ "\n Address:" + suppObj.getAddress()
							+ "\n Eamil:" + suppObj.getEmail()
							+ "\n Phone Number:" + suppObj.getPhoneNumber());
				}
			}
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5002: Exception occured in PersonManager:listSuppliers() :",
							ex);
			throw new BSIException("1001", "5002");
		}

		return supplierList;
	}

	public List listBuyers() throws BSIException {

		CommonLogger.logDebug(log, "In PersonManager:listBuyers()");
		List buyerList = null;

		try {
			// dbHandler = new HbntDBHandler();

			String hqlQuery = " from Person as person where person.personType=?";
			// param values
			List paramList = new ArrayList();
			paramList.add("B");
			buyerList = dbAccessor.getObjectsFromQuery(hqlQuery, paramList,
					null);
			Person buyerObj = null;

			if (log.isDebugEnabled()) {
				CommonLogger.logDebug(log, "The buyer list contains :");
				for (int x = 0; x < buyerList.size(); x++) {
					buyerObj = (Person) buyerList.get(x);
					CommonLogger.logDebug(log, "name:" + buyerObj.getName()
							+ "\n Address:" + buyerObj.getAddress()
							+ "\n Email:" + buyerObj.getEmail()
							+ "\n Phone Number:" + buyerObj.getPhoneNumber());
				}
			}
		} catch (Exception ex) {
			CommonLogger.logError(log,
					"5003: Exception occured in PersonManager:listBuyers() :",
					ex);
			throw new BSIException("1001", "5003");
		}
		return buyerList;
	}

	/*
	 * public Person extractPersonObj(ActionForm form) {
	 * 
	 * CommonLogger.logDebug(log,"In PersonManager:extractPersonObj()");
	 * 
	 * Person personObj = new Person();
	 * 
	 * try {
	 * 
	 * PropertyUtils.copyProperties(personObj,form);
	 * 
	 * /* DynaValidatorForm personForm = (DynaValidatorForm)form;
	 * 
	 * personObj.setAddress((String)personForm.get("address"));
	 * personObj.setEmail((String)personForm.get("email"));
	 * personObj.setName((String)personForm.get("name"));
	 * personObj.setPhoneNumber((String)personForm.get("phoneNumber"));
	 * 
	 * 
	 * String personType = (String)personForm.get("personType");
	 * CommonLogger.logDebug(log,"The person type is " + personType ); if
	 * (personType != null && personType.equalsIgnoreCase("Supplier")){
	 * personObj.setType("S"); } else if (personType != null &&
	 * personType.equalsIgnoreCase("Buyer")){ personObj.setType("B"); }
	 * 
	 * } catch(Exception ex) { ex.printStackTrace(); }
	 * 
	 * return personObj; }
	 */

	public List getServiceIdFromRequestForm(ActionForm form) {

		CommonLogger.logDebug(log,
				"In PersonManager:getServiceIdFromRequestForm()");
		PersonForm personForm = (PersonForm) form;

		Person personObj = new Person();
		List svsList = new ArrayList();
		String[] servicesIds = personForm.getServiceId();

		for (int x = 0; x < servicesIds.length; x++) {
			svsList.add(new Long(servicesIds[x]));
		}

		if (log.isDebugEnabled()) {
			CommonLogger.logDebug(log,
					"The service id selected by the person is " + servicesIds);
		}

		return svsList;
	}

	/**
	 * public List getServiceIdFromRequest(ActionForm form) {
	 * 
	 * CommonLogger .logDebug(log,
	 * "In PersonManager:getServiceIdFromRequest()");
	 * 
	 * Person personObj = new Person(); DynaValidatorForm personForm =
	 * (DynaValidatorForm) form;
	 * 
	 * List svsList = new ArrayList(); String[] servicesIds = (String[])
	 * personForm.get("serviceIds");
	 * 
	 * for (int x = 0; x < servicesIds.length; x++) { svsList.add(new
	 * Long(servicesIds[x])); }
	 * 
	 * if (log.isDebugEnabled()) {
	 * log.debug("The service id selected by the person is " + servicesIds); }
	 * 
	 * return svsList; }
	 * 
	 * public String getFromRequest(ActionForm form, String param) {
	 * CommonLogger.logDebug(log, "In PersonManager:getFromRequest()");
	 * DynaValidatorForm personForm = (DynaValidatorForm) form; return (String)
	 * personForm.get(param); }
	 */

	public List login(HashMap userInfoMap) throws BSIException {
		List persons = null;
		try {
			// dbHandler = new HbntDBHandler();
			persons = dbAccessor.getObjects("Person", userInfoMap);
			if (persons != null && persons.size() > 1) {
				throw new BSIException("1001", "5039");
			}
		} catch (Exception ex2) {
			CommonLogger.logError(log,
					"5040: Exception occured in PersonManager:login() :", ex2);
			throw new BSIException("1001", "5040");
		}
		return persons;
	}

	public Group getGroup(String groupName) throws BSIException {
		CommonLogger.logDebug(log, "In PersonManager:getGroup()");
		System.out.println("Trying to get the group..............");
		Group group = null;

		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from Group as group where group.name=?";
			List paramList = new ArrayList();
			paramList.add(groupName);

			List groups = dbAccessor.getObjectsFromQuery(hqlQuery, paramList,
					null);
			if (groups.size() > 0) {
				group = (Group) groups.get(0);
			} else {
				throw new BSIException("1001", "5049");
			}

			System.out.println("The groupd id retreived is "
					+ group.getGroupID());
			CommonLogger.logDebug(log,
					"The groupd id retreived is " + group.getGroupID());
		} catch (Exception ex2) {
			CommonLogger.logError(log,
					"5049: Exception occured in PersonManager:getGroup() :",
					ex2);
			throw new BSIException("1001", "5049");
		}

		return group;
	}

	public Set getRoles(String personID) throws BSIException {
		Set userRoles = new HashSet();
		Person personObj = getPerson(personID);
		Set groups = personObj.getGroups();

		// For each Group
		Iterator iter = groups.iterator();
		while (iter.hasNext()) {
			Group group = (Group) iter.next();
			// Long groupId = group.getGroupID();
			// System.out.println("The id is " + groupId.toString());

			Set roles = group.getRoles();
			// Set roles = getRoles(groupId);
			// For each role
			Iterator iter2 = roles.iterator();
			Role role = null;
			while (iter2.hasNext()) {
				role = (Role) iter2.next();
				System.out.println("The role name is " + role.getName());
				CommonLogger
						.logDebug(log, "The role name is " + role.getName());
				userRoles.add(role.getName());
			}
		}
		return userRoles;
	}
}
