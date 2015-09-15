package com.nms.catalog.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsi.common.beans.Property;
import com.bsi.common.beans.Service;
import com.bsi.common.beans.Token;
import com.nms.catalog.domain.Item;
import com.nms.catalog.domain.Tree;
import com.nms.catalog.exception.CatalogServiceErrorCodes;
import com.nms.catalog.exception.CatalogServiceException;
import com.nms.catalog.service.CatalogService;
import com.nms.db.IDataAccess;

public class CatalogServiceImpl implements CatalogService {
	private static final Logger logger = LoggerFactory
			.getLogger(CatalogServiceImpl.class);
	private static IDataAccess dbAccessor = null;

	public void setDbAccessor(IDataAccess dbAccessor) {
		this.dbAccessor = dbAccessor;
	}

	public String getAllCategories() {
		System.out.println("Getting All categories");
		return "success";
	}

	public Tree getFirstLevelServices() throws CatalogServiceException {
		logger.debug("In CatalogServiceImpl:getFirstLevelServices()");
		List srvss = null;

		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from Service as svs where svs.prntNodeId = ?";
			List params = new ArrayList();
			params.add(new Long("0"));
			srvss = dbAccessor.getObjectsFromQuery(hqlQuery, params, null);
		} catch (Exception ex) {
			logger.error(
					"catalog-10000: Exception occured in CatalogServiceImpl:getFirstLevelServices() :",
					ex);
			throw new CatalogServiceException(
					CatalogServiceErrorCodes.DB_ERROR_FIRST_LEVEL_SERVICES
							.getErrorCode(),
					CatalogServiceErrorCodes.DB_ERROR_FIRST_LEVEL_SERVICES
							.getErrorDesc(),
					"Execption occured while getting the first level service");
		}

		Service serviceObj = null;

		if (logger.isDebugEnabled()) {
			logger.debug("The service list contains :");

			for (int x = 0; x < srvss.size(); x++) {
				serviceObj = (Service) srvss.get(x);
				logger.debug("getNodeId:" + serviceObj.getNodeId()
						+ "\n getPrntNodeId:" + serviceObj.getPrntNodeId()
						+ "\n getName:" + serviceObj.getName()
						+ "\n getDescription:" + serviceObj.getDescription());
			}
		}
		return getServicesAsTree(srvss, "0");
	}

	public Tree getChildServices(String parentId)
			throws CatalogServiceException {

		Long prntId = new Long(parentId);
		List<Service> srvss = null;

		logger.debug("In CatalogServiceImpl:getChildServices()");

		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from Service as svs where svs.prntNodeId = ?";
			List<Long> params = new ArrayList<Long>();
			params.add(prntId);
			srvss = dbAccessor.getObjectsFromQuery(hqlQuery, params, null);
		} catch (Exception ex) {
			logger.error(
					"catalog-10001: Exception occured in CatalogServiceImpl:getChildServices() :",
					ex);
			throw new CatalogServiceException(
					CatalogServiceErrorCodes.DB_ERROR_CHILD_SERVICES
							.getErrorCode(),
					CatalogServiceErrorCodes.DB_ERROR_CHILD_SERVICES
							.getErrorDesc(),
					"Execption occured while getting the ChildServices");
		}

		Service serviceObj = null;

		if (logger.isDebugEnabled()) {
			logger.debug("The service list contains :");

			for (int x = 0; x < srvss.size(); x++) {
				serviceObj = (Service) srvss.get(x);
				logger.debug("getNodeId:" + serviceObj.getNodeId()
						+ "\n getPrntNodeId:" + serviceObj.getPrntNodeId()
						+ "\n getName:" + serviceObj.getName()
						+ "\n getDescription:" + serviceObj.getDescription());
			}
		}
		return getServicesAsTree(srvss, parentId);
	}

	public Set getServiceProperties(String serviceId)
			throws CatalogServiceException {

		logger.debug("In CatalogServiceImpl:getServiceProperties () \n The service id is "
				+ serviceId);

		Set<Property> props = new HashSet<Property>();
		Service srvs = null;

		srvs = getService(serviceId);
		// props = srvs.getProperties();
		props.addAll(srvs.getProperties());

		// Get the properties of all parents.
		while (true) {
			serviceId = srvs.getPrntNodeId().toString();
			logger.debug("The parent service id is " + serviceId);
			if (serviceId != null && serviceId.equalsIgnoreCase("0"))
				break;
			srvs = getService(serviceId);
			props.addAll(srvs.getProperties());
		}

		return props;
	}

	/**
	 * This returns only the properties specific to the service without considering parent properties.
	 * @param serviceId
	 * @return
	 * @throws CatalogServiceException
	 */
	public Set<Property> getServiceSpecificProperties(String serviceId)
			throws CatalogServiceException {

		logger.debug("In CatalogServiceImpl:getServiceProperties () \n The service id is "
				+ serviceId);

		Set<Property> props = new HashSet<Property>();
		Service srvs = null;

		srvs = getService(serviceId);
		// props = srvs.getProperties();
		props.addAll(srvs.getProperties());

		// Get the properties of all parents.
		return props;
	}
	
	private Service getService(String serviceId) throws CatalogServiceException {
		return getService(serviceId, true);

	}

	public Service getService(String serviceId, boolean getProps)
			throws CatalogServiceException {

		logger.debug("In CatalogServiceImpl:getService() \n The service id is "
				+ serviceId);

		Service srvs = null;
		Service updatedService = new Service();

		/*
		 * if (serviceId == null || serviceId.trim().length() == 0) throw new
		 * BSIException("1004");
		 */
		if (serviceId == null || serviceId.trim().length() == 0)
			throw new CatalogServiceException(
					CatalogServiceErrorCodes.SERVICE_ID_NULL.getErrorCode(),
					CatalogServiceErrorCodes.SERVICE_ID_NULL.getErrorDesc(),
					"Service id cannot be null");
		try {
			// dbHandler = new HbntDBHandler();
			Method[] methods = null;
			if (getProps) {
				methods = new Method[1];
				methods[0] = Service.class.getMethod("getProperties", null);
			}
			srvs = (Service) dbAccessor.getObjectRequested(new Long(serviceId),
					Service.class, methods);
			if (logger.isDebugEnabled())
				logger.debug("The service returned is " + srvs);

			BeanUtils.copyProperties(updatedService, srvs);
			// Avoid setting the persistentset from hibernate being loaded into
			// the pojo.

			// TODO this needs to be set based on the values available in
			// persistentset
			updatedService.setPersons(new HashSet());
			if (getProps && updatedService.getProperties() != null
					&& updatedService.getProperties().size() > 0) {
				Object[] properties = updatedService.getProperties().toArray();
				updatedService.setProperties(new HashSet(Arrays
						.asList(properties)));
			} else {
				updatedService.setProperties(new HashSet());
			}
		} catch (Exception ex) {
			logger.error(
					"catalog-10004: Exception occured in CatalogServiceImpl:getService() :",
					ex);
			throw new CatalogServiceException(
					CatalogServiceErrorCodes.DB_ERROR_GET_SERVICE
							.getErrorCode(),
					CatalogServiceErrorCodes.DB_ERROR_GET_SERVICE
							.getErrorDesc(),
					"Execption occured while getting the service details");
		}
		return updatedService;

	}

	public void updateServiceProperties(String srvsId, Set propsSet)
			throws CatalogServiceException {

		if (logger.isDebugEnabled())
			logger.debug("In CatalogServiceImpl:updateServiceProperties()");

		try {
			Class[] clss = new Class[1];
			clss[0] = Set.class;

			Method[] methods = new Method[1];
			methods[0] = Service.class.getMethod("setProperties", clss);

			// Method arguments.
			Object[] args = new Object[1];
			args[0] = propsSet;

			List argsList = new ArrayList();
			argsList.add(args);

			Object object = dbAccessor.updateObjectRequested(new Long(srvsId),
					Service.class, methods, argsList);

		} catch (Exception ex) {
			logger.error(
					"catalog-10002: Exception occured in CatalogServiceImpl:updateServiceProperties() :",
					ex);
			throw new CatalogServiceException(
					CatalogServiceErrorCodes.DB_ERROR_UPDATE_PROPERTIES
							.getErrorCode(),
					CatalogServiceErrorCodes.DB_ERROR_UPDATE_PROPERTIES
							.getErrorDesc(),
					"Execption occured while getting the ChildServices");
		}
	}

	public List<Service> getAllServices() throws CatalogServiceException,
			Exception {

		if (logger.isDebugEnabled())
			logger.debug("In CatalogServiceImpl:getAllServices()");
		List srvss = null;
		try {
			String hqlQuery = "from Service";
			srvss = dbAccessor.getObjectsFromQuery(hqlQuery, null, null);
		} catch (Exception ex) {
			logger.error(
					"catalog-10004: Exception occured in CatalogServiceImpl:getAllServices() :",
					ex);
			throw new CatalogServiceException(
					CatalogServiceErrorCodes.DB_ERROR_GET_SERVICE
							.getErrorCode(),
					CatalogServiceErrorCodes.DB_ERROR_GET_SERVICE
							.getErrorDesc(),
					"Execption occured while getting all the services");
		}

		Service serviceObj = null;

		if (logger.isDebugEnabled()) {
			logger.debug("The service list contains :");

			for (int x = 0; x < srvss.size(); x++) {
				serviceObj = (Service) srvss.get(x);
				logger.debug("getNodeId:" + serviceObj.getNodeId()
						+ "\n getPrntNodeId:" + serviceObj.getPrntNodeId()
						+ "\n getName:" + serviceObj.getName()
						+ "\n getDescription:" + serviceObj.getDescription());
			}
		}
		// Copy the Service object to a new service object due to issue with
		// marshalling the persistentset in response
		List<Service> newServiceList = new ArrayList<Service>();
		for (int x = 0; x < srvss.size(); x++) {
			serviceObj = (Service) srvss.get(x);
			Service newService = new Service();
			BeanUtils.copyProperties(newService, serviceObj);
			newService.setPersons(new HashSet());
			newService.setProperties(new HashSet());
			newServiceList.add(newService);
		}
		return newServiceList;
	}

	public Token getToken(Long tokenId) throws CatalogServiceException {
		logger.debug("In CatalogServiceImpl:getTokenValues() \n the token id is :"
				+ tokenId);
		Token token = new Token();
		Set<String> tokenValueSet = new HashSet<String>();

		try {
			Method[] methods = new Method[1];
			methods[0] = Token.class.getMethod("getTokenValues", null);

			// dbHandler = new HbntDBHandler();
			Token dbToken = (Token) dbAccessor.getObjectRequested(tokenId,
					Token.class, methods);
			BeanUtils.copyProperties(token, dbToken);

			Set tokenValues = token.getTokenValues();
			Iterator iter = tokenValues.iterator();
			String tokenVal = null;
			while (iter.hasNext()) {
				tokenVal = (String) iter.next();
				tokenValueSet.add(tokenVal);
				if (logger.isDebugEnabled())
					logger.debug("The token value is " + tokenVal);
			}
			token.setTokenValues(tokenValueSet);
		} catch (Exception ex) {
			logger.error(
					"catalog-10005: Exception occured in ServiceHandler:getTokens() :",
					ex);
			throw new CatalogServiceException(
					CatalogServiceErrorCodes.DB_ERROR_GET_TOKEN.getErrorCode(),
					CatalogServiceErrorCodes.DB_ERROR_GET_TOKEN.getErrorDesc(),
					"Execption occured while getting the token details from db");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("The token Name is " + token.getTokenName());
			logger.debug("The token Value is " + token.getTokenDesc());
		}
		return token;
	}

	public Service getServiceByServiceName(String srvsName) throws CatalogServiceException {
		Service srvs = null;
		Service updatedService = new Service();

		logger.debug("In CatalogServiceImpl:getServiceByServiceName() \n the service name is :"
				+ srvsName);

		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from Service as svs where svs.name = ?";
			List params = new ArrayList();
			params.add(srvsName);
			List srvss = dbAccessor.getObjectsFromQuery(hqlQuery, params, null);

			if (srvss == null || srvss.size() == 0)
				throw new Exception(
						"No service found for the given service Name");

			if (srvss.size() > 1)
				throw new Exception(
						"More than one service found for the given service name");

			srvs = (Service) srvss.get(0);
			BeanUtils.copyProperties(updatedService, srvs);
			//updatedService.setPersons(new HashSet(Arrays.asList(srvs.getPersons().toArray())));
			updatedService.setPersons(new HashSet());
			updatedService.setProperties(new HashSet());
		} catch (Exception ex) {
			logger.error("5048: Exception occured in LocalServiceHandler:getService() :",ex);
			throw new CatalogServiceException(
					CatalogServiceErrorCodes.DB_ERROR_GET_SERVICE_BY_NAME.getErrorCode(),
					CatalogServiceErrorCodes.DB_ERROR_GET_SERVICE_BY_NAME.getErrorDesc(),
					"Execption occured while getting the service by name from db");
		}
		return updatedService;
	}
	
	
	private Tree getServicesAsTree(List servicesList, String id) {
		logger.debug("In CatalogServiceImpl:createServiceXML() \n the requested service id is "
				+ id);
		Tree newTree = new Tree();
		newTree.setId(new Byte(id));

		Item rootItem = null;

		if (id != null && id.equalsIgnoreCase("0")) {
			rootItem = new Item();
			rootItem.setText("/");
			rootItem.setChild(new Byte("1"));
			rootItem.setId(new Integer("999999"));
			rootItem.setIm0("leaf.gif");
			rootItem.setIm1("folderOpen.gif");
			rootItem.setIm2("folderClosed.gif");
			rootItem.setCall(new Byte("1"));
			rootItem.setSelect(new Byte("1"));
		}

		Item childItem = null;

		for (int i = 0; i < servicesList.size(); i++) {
			childItem = new Item();
			Service svs = (Service) servicesList.get(i);
			childItem.setText(svs.getName());
			childItem.setChild(new Byte("1"));
			childItem.setId(svs.getNodeId().intValue());
			childItem.setIm0("leaf.gif");
			childItem.setIm1("folderOpen.gif");
			childItem.setIm2("folderClosed.gif");
			childItem.setCall(new Byte("1"));
			childItem.setSelect(new Byte("1"));

			if (rootItem != null) {
				rootItem.getItem().add(childItem);
			} else {
				newTree.getItem().add(childItem);
			}
		}
		if (rootItem != null) {
			newTree.getItem().add(rootItem);
		}
		return newTree;
	}
}
