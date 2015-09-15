package com.bsi.client.managers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsi.client.actions.forms.PropertyForm;
import com.bsi.common.beans.Property;
import com.bsi.common.beans.Service;
import com.bsi.common.beans.Token;
import com.nms.db.IDataAccess;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

//TODO this calls needs be moved under service-client for accessing the service info
//directly from DB.
public class CatalogDatabaseClient {

	private static Log log = LogFactory.getLog(CatalogDatabaseClient.class);

	private static IDataAccess dbAccessor = null;

	public void setDbAccessor(IDataAccess dbAccessor) {
		this.dbAccessor = dbAccessor;
	}

	// private static ResourceLocatorIntf resLoc = null;

	// private ServiceIntf serviceHandler = null;
	
	// static {
	// resLoc = ResourceFactory.getResourceLocator("local");
	// }

	public void updateServiceProperties(String srvsId, List props)
			throws BSIException {

		CommonLogger.logDebug(log,
				"In ServiceManager:updateServiceProperties()");

		try {
			//dbHandler = new HbntDBHandler();
			Set propsSet = createPropertyBeans(props);
			Class[] clss = new Class[1];
			clss[0] = Set.class;

			Method[] methods = new Method[1];
			methods[0] = Service.class.getMethod("setProperties", clss);

			// Method arguments.
			Object[] args = new Object[1];
			args[0] = propsSet;

			List argsList = new ArrayList();
			argsList.add(args);

			Object object = dbAccessor.updateObjectRequested(new Long(srvsId), Service.class,
					methods, argsList);

		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5021: Exception occured in ServiceHandler:updateServiceProperties() :",
							ex);
			throw new BSIException("1001", "5021");
		}

	}

	public Service getService(String serviceId) throws BSIException {
		return getService(serviceId, true);

	}

	public Service getService(String serviceId, boolean getProps)
			throws BSIException {

		CommonLogger.logDebug(log,
				"In ServiceManager:getService() \n The service id is "
						+ serviceId);

		Service srvs = null;

		if (serviceId == null || serviceId.trim().length() == 0)
			throw new BSIException("1004");

		try {
			// dbHandler = new HbntDBHandler();
			Method[] methods = null;
			if (getProps) {
				methods = new Method[1];
				methods[0] = Service.class.getMethod("getProperties", null);
			}
			srvs = (Service) dbAccessor.getObjectRequested(new Long(serviceId),
					Service.class, methods);
			CommonLogger.logDebug(log, "The service returned is " + srvs);

		} catch (Exception ex) {
			CommonLogger.logError(log,
					"5021: Exception occured in ServiceHandler:getService() :",
					ex);
			throw new BSIException("1001", "5021");
		}
		return srvs;

	}

	public Set getServiceProperties(String serviceId) throws BSIException {

		CommonLogger.logDebug(log,
				"In ServiceManager:getServiceProperties () \n The service id is "
						+ serviceId);

		Set props = null;
		Service srvs = null;

		srvs = getService(serviceId);
		props = srvs.getProperties();

		// Get the properties of all parents.
		while (true) {
			serviceId = srvs.getPrntNodeId().toString();
			CommonLogger.logDebug(log, "The parent service id is " + serviceId);
			if (serviceId != null && serviceId.equalsIgnoreCase("0"))
				break;
			srvs = getService(serviceId);
			props.addAll(srvs.getProperties());
		}

		return props;
	}

	public String getServicePropertiesXML(String serviceId) throws BSIException {
		// Service srvs = getService(serviceId);
		// Set props = srvs.getProperties();
		Set props = getServiceProperties(serviceId);
		return createPropertiesXML(props);
	}

	public List getAllServices() throws BSIException {

		CommonLogger.logDebug(log, "In ServiceManager:getAllServices()");
		List srvss = null;
		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from Service";
			srvss = dbAccessor.getObjectsFromQuery(hqlQuery, null, null);
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5017: Exception occured in ServiceHandler:getAllServices() :",
							ex);
			throw new BSIException("1001", "5017");
		}

		Service serviceObj = null;

		if (log.isDebugEnabled()) {
			log.debug("The service list contains :");

			for (int x = 0; x < srvss.size(); x++) {
				serviceObj = (Service) srvss.get(x);
				CommonLogger.logDebug(
						log,
						"getNodeId:" + serviceObj.getNodeId()
								+ "\n getPrntNodeId:"
								+ serviceObj.getPrntNodeId() + "\n getName:"
								+ serviceObj.getName() + "\n getDescription:"
								+ serviceObj.getDescription());
			}
		}

		return srvss;

	}

	public List getFirstLevelServices() throws BSIException {

		CommonLogger.logDebug(log, "In ServiceManager:getFirstLevelServices()");
		List srvss = null;

		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from Service as svs where svs.prntNodeId = ?";
			List params = new ArrayList();
			params.add(new Long("0"));
			srvss = dbAccessor.getObjectsFromQuery(hqlQuery, params, null);
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5018: Exception occured in ServiceHandler:getFirstLevelServices() :",
							ex);
			throw new BSIException("1001", "5018");
		}

		Service serviceObj = null;

		if (log.isDebugEnabled()) {
			log.debug("The service list contains :");

			for (int x = 0; x < srvss.size(); x++) {
				serviceObj = (Service) srvss.get(x);
				CommonLogger.logDebug(
						log,
						"getNodeId:" + serviceObj.getNodeId()
								+ "\n getPrntNodeId:"
								+ serviceObj.getPrntNodeId() + "\n getName:"
								+ serviceObj.getName() + "\n getDescription:"
								+ serviceObj.getDescription());
			}
		}
		return srvss;

	}

	public String getFirstLevelServicesAsXML() throws BSIException {

		CommonLogger.logDebug(log,
				"In ServiceManager:getFirstLevelServicesAsXML()");

		List srvs = getFirstLevelServices();

		return createServiceXML(srvs, "0");

	}

	public List getChildServices(String parentId) throws BSIException {

		Long prntId = new Long(parentId);
		List srvss = null;

		CommonLogger.logDebug(log, "In ServiceManager:getChildServices()");

		try {
			//dbHandler = new HbntDBHandler();
			String hqlQuery = "from Service as svs where svs.prntNodeId = ?";
			List params = new ArrayList();
			params.add(prntId);
			srvss = dbAccessor.getObjectsFromQuery(hqlQuery, params,null);
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5019: Exception occured in LocalServiceHandler:getChildServices() :",
							ex);
			throw new BSIException("1001", "5019");
		}

		Service serviceObj = null;

		if (log.isDebugEnabled()) {
			log.debug("The service list contains :");

			for (int x = 0; x < srvss.size(); x++) {
				serviceObj = (Service) srvss.get(x);
				CommonLogger.logDebug(
						log,
						"getNodeId:" + serviceObj.getNodeId()
								+ "\n getPrntNodeId:"
								+ serviceObj.getPrntNodeId() + "\n getName:"
								+ serviceObj.getName() + "\n getDescription:"
								+ serviceObj.getDescription());
			}
		}
		return srvss;

	}

	public String getChildServicesAsXML(String parentId) throws BSIException {

		CommonLogger.logDebug(log, "In ServiceManager:getChildServicesAsXML()");

		List srvs = getChildServices(parentId);

		return createServiceXML(srvs, parentId);

	}

	public String createServiceXML(List svsList, String id) throws BSIException {

		CommonLogger.logDebug(log,
				"In ServiceManager:createServiceXML() \n the requested service id is "
						+ id);

		String name = "";
		String nodeId = "";

		StringBuffer svsXML = new StringBuffer();
		svsXML.append("<?xml version='1.0' encoding='iso-8859-1'?>");

		svsXML.append("<tree id=\"" + id + "\">");

		if (id != null && id.equalsIgnoreCase("0")) {
			svsXML.append("<item text=\"/\" child=\"1\" id=\"999999\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\">");
		}

		for (int i = 0; i < svsList.size(); i++) {
			Service svs = (Service) svsList.get(i);
			name = svs.getName();
			nodeId = svs.getNodeId().toString();
			svsXML.append("<item text=\""
					+ name
					+ "\" child=\"1\" id=\""
					+ nodeId
					+ "\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" call=\"1\" select=\"1\"></item>");
		}

		if (id != null && id.equalsIgnoreCase("0")) {
			svsXML.append("</item>");
		}
		svsXML.append("</tree>");

		CommonLogger.logDebug(log,
				"The service xml generated is \n" + svsXML.toString());

		return svsXML.toString();

	}

	public String createPropertiesXML(Set props) throws BSIException {

		CommonLogger.logDebug(log, "In ServiceManager:createPropertiesXML() ");

		StringBuffer propsXML = new StringBuffer();
		String propType = null;

		propsXML.append("<table width=\"100%\" border=\"0\" align=\"top\" cellpadding=\"0\" cellspacing=\"10\">");

		Iterator itr = props.iterator();
		while (itr.hasNext()) {
			Property prop = (Property) itr.next();
			propType = prop.getDisplayType();

			CommonLogger
					.logDebug(log, "The Property name is " + prop.getName());

			propsXML.append("<tr>");
			propsXML.append("<td width=\"25%\" valign=\"top\">");
			propsXML.append(prop.getName());

			propsXML.append("</td>");
			propsXML.append("<td width=\"75%\" valign=\"top\">");

			propsXML.append("<input type=\"hidden\" name=\"propertyId\" value=\""
					+ prop.getPropertyId() + "\"/>");

			// IF type is Text Field
			if (propType != null && propType.equalsIgnoreCase("TF")) {
				propsXML.append("<input type=\"text\" name=\"propertyValue\" value=\"\">");
			}
			// If type is Drop down box
			if (propType != null && propType.equalsIgnoreCase("DD")) {

				propsXML.append("<select name=\"propertyValue\">");

				Token token = getToken(prop.getTokenId());
				Set tokenValues = token.getTokenValues();
				List<String> sortedList = new ArrayList<String>(tokenValues);
				Collections.sort(sortedList);
				Iterator iter = sortedList.iterator();
				String tokenVal = null;
				while (iter.hasNext()) {
					tokenVal = (String) iter.next();
					propsXML.append("<option value=\"" + tokenVal + "\">"
							+ tokenVal + "</option>");
				}
				propsXML.append("</select>");
			}
			propsXML.append("</td>");
			propsXML.append("</tr>");
		}

		propsXML.append("</table>");
		CommonLogger.logDebug(log,
				"The properties xml is \n" + propsXML.toString());

		return propsXML.toString();

	}


	/**
	 * This returns the html to be displayed while creating the service properties through UI. This prepares html for
	 * only parent service properties
	 * @param props
	 * @return
	 * @throws BSIException
	 */
	public String createPropertiesXML2(Set props) throws BSIException {

		CommonLogger.logDebug(log, "In ServiceManager:createPropertiesXML() ");
		StringBuffer propsXML = new StringBuffer();
		String propType = null;
		Iterator itr = props.iterator();
		int i=0;

		propsXML.append("<table width=\"100%\" border=\"1\">");
		propsXML.append("<tr>");
		propsXML.append("<th width=\"12%\">No</th>");	
		propsXML.append("<th width=\"12%\">Name</th>");	
		propsXML.append("<th width=\"12%\">Description</th>");	
		propsXML.append("<th width=\"12%\">Is Mandatory</th>");	
		propsXML.append("<th width=\"12%\">DisplayType</th>");	
		propsXML.append("<th width=\"12%\">Value Type</th>");	
		propsXML.append("<th width=\"12%\">Tokens</th>");	
		propsXML.append("<th width=\"12%\">Display Size</th>");	
		propsXML.append("<th width=\"12%\">Data Size</th>");	
		propsXML.append("</tr>");
		
		while (itr.hasNext()) {
			Property prop = (Property) itr.next();
			propType = prop.getDisplayType();

			CommonLogger
					.logDebug(log, "The Property name is " + prop.getName());
		
			propsXML.append("<tr>");
			propsXML.append("<td width=\"12%\">");
			propsXML.append( ++i);
			propsXML.append("</td>");	
			propsXML.append("<td width=\"12%\">").append(prop.getName()).append("</td>");	
			propsXML.append("<td width=\"12%\">").append(prop.getDescription()).append("</td>");	
			propsXML.append("<td width=\"12%\">").append(prop.getIsMandatory()).append("</td>");	
			propsXML.append("<td width=\"12%\">").append(prop.getDisplayType()).append("</td>");	
			propsXML.append("<td width=\"12%\">").append(prop.getValueType()).append("</td>");	
			propsXML.append("<td width=\"12%\">");
		
			// If type is Drop down box
			if (propType != null && propType.equalsIgnoreCase("DD")) {

				propsXML.append("<select name=\"propertyValue\">");

				Token token = getToken(prop.getTokenId());
				Set tokenValues = token.getTokenValues();
				List<String> sortedList = new ArrayList<String>(tokenValues);
				Collections.sort(sortedList);
				
				Iterator iter = sortedList.iterator();
				String tokenVal = null;
				while (iter.hasNext()) {
					tokenVal = (String) iter.next();
					propsXML.append("<option value=\"" + tokenVal + "\">"
							+ tokenVal + "</option>");
				}
				propsXML.append("</select>");
			}
			propsXML.append("</td>");	
			propsXML.append("<td width=\"12%\">").append(prop.getDisplaySize()).append("</td>");	
			propsXML.append("<td width=\"12%\">").append(prop.getDataSize()).append("</td>");	
			propsXML.append("</tr>");
		}
		propsXML.append("</table>");
		
		CommonLogger.logDebug(log,
				"The properties xml is \n" + propsXML.toString());
		return propsXML.toString();
	}

	
	
	
	public Token getToken(Long tokenId) throws BSIException {
		CommonLogger.logDebug(log,
				"In ServiceManager:getTokenValues() \n the token id is :"
						+ tokenId);
		Token token = null;

		try {
			Method[] methods = new Method[1];
			methods[0] = Token.class.getMethod("getTokenValues", null);

			//dbHandler = new HbntDBHandler();
			token = (Token) dbAccessor.getObjectRequested(tokenId, Token.class,
					methods);

			if (log.isDebugEnabled()) {
				Set tokenValues = token.getTokenValues();
				Iterator iter = tokenValues.iterator();
				String tokenVal = null;
				while (iter.hasNext()) {
					tokenVal = (String) iter.next();
					CommonLogger
							.logDebug(log, "The token value is " + tokenVal);
				}
			}

		} catch (Exception ex) {
			CommonLogger.logError(log,
					"5023: Exception occured in ServiceHandler:getTokens() :",
					ex);
			throw new BSIException("1001", "5023");
		}

		if (log.isDebugEnabled()) {
			CommonLogger.logDebug(log,
					"The token Name is " + token.getTokenName());
			CommonLogger.logDebug(log,
					"The token Value is " + token.getTokenDesc());
		}
		return token;
	}

	public Set createPropertyBeans(List props) {

		Set propSet = new HashSet();
		Property property = null;
		if (props.get(0) instanceof Property) {
			for (int i=0;i<props.size();i++){
				propSet.add(props.get(i));
			}
			return propSet;
		}

		for (int x = 0; x < props.size(); x++) {
			PropertyForm propForm = (PropertyForm) props.get(x);
			property = new Property();
			property.setName(propForm.getName());
			property.setDescription(propForm.getDescription());
			property.setIsMandatory(propForm.getIsMandatory());
			property.setDisplayType(propForm.getDisplayType());
			property.setValueType(propForm.getValueType());
			property.setTokenId(propForm.getTokenId());
			property.setDisplaySize(propForm.getDisplaySize());
			property.setDataSize(propForm.getDataSize());
			propSet.add(property);
		}

		return propSet;

	}

	public Service getServiceByServiceName(String srvsName) throws BSIException {
		Service srvs = null;

		try {
			//dbHandler = new HbntDBHandler();
			String hqlQuery = "from Service as svs where svs.name = ?";
			List params = new ArrayList();
			params.add(srvsName);
			List srvss = dbAccessor.getObjectsFromQuery(hqlQuery, params,null);

			if (srvss == null || srvss.size() == 0)
				throw new Exception(
						"No service found for the given service Name");

			if (srvss.size() > 1)
				throw new Exception(
						"More than one service found for the given service name");

			srvs = (Service) srvss.get(0);
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5048: Exception occured in LocalServiceHandler:getService() :",
							ex);
			throw new BSIException("1001", "5048");
		}
		return srvs;
	}

	public String getServicePropertiesXML(List<Property> props) throws BSIException{
		CommonLogger.logDebug(log, "In ServiceManager:getServicePropertiesXML() ");

		StringBuffer propsXML = new StringBuffer();
		String propType = null;

		propsXML.append("<table width=\"100%\" border=\"0\" align=\"top\" cellpadding=\"0\" cellspacing=\"10\">");

		Iterator itr = props.iterator();
		while (itr.hasNext()) {
			Property prop = (Property) itr.next();
			propType = prop.getDisplayType();

			CommonLogger
					.logDebug(log, "The Property name is " + prop.getName());

			propsXML.append("<tr>");
			propsXML.append("<td width=\"25%\" valign=\"top\">");
			propsXML.append(prop.getName());

			propsXML.append("</td>");
			propsXML.append("<td width=\"75%\" valign=\"top\">");

			propsXML.append("<input type=\"hidden\" name=\"propertyId\" value=\""
					+ prop.getPropertyId() + "\"/>");

			// IF type is Text Field
			if (propType != null && propType.equalsIgnoreCase("TF")) {
				propsXML.append("<input type=\"text\" name=\"propertyValue\" value=\"\" maxlength=\"250\">");
			} else if (propType != null && propType.equalsIgnoreCase("TA")) {
				propsXML.append("<textarea name=\"propertyValue\" cols=\"31\" rows=\"5\"></textarea>");
			}
			// If type is Drop down box
			else if (propType != null && propType.equalsIgnoreCase("DD")) {

				propsXML.append("<select name=\"propertyValue\">");

				Token token = getToken(prop.getTokenId());
				Set tokenValues = token.getTokenValues();
				List<String> sortedList = new ArrayList<String>(tokenValues);
				Collections.sort(sortedList);
				
				Iterator iter = sortedList.iterator();
				String tokenVal = null;
				while (iter.hasNext()) {
					tokenVal = (String) iter.next();
					propsXML.append("<option value=\"" + tokenVal + "\">"
							+ tokenVal + "</option>");
				}
				propsXML.append("</select>");
			}
			propsXML.append("</td>");
			propsXML.append("</tr>");
		}

		propsXML.append("</table>");
		CommonLogger.logDebug(log,
				"The properties xml is \n" + propsXML.toString());

		return propsXML.toString();
	}
	
	public void getPropertyValues(String serviceID, String personType){
		
	}
}
