package com.nms.util.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nms.util.beans.ArrayToSetConverter;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

/**
 * This class is a utility class for creating the object of the specified type
 * from the request paramters. It is expected that the request provides
 * information about the object type and all the valuesfollowing parameter
 * values
 * 
 * Syntax : <Class of the class variable other than wrapper and primitive
 * classes and string>:<property name of the class variable's class>, <name of
 * the class variable><Type of the variable ex:Set, Map etc if other than
 * wrapper classes and String>
 * 
 * For example to create the object of the following type package
 * com.bsi.common.beans;
 * 
 * public class Service{
 * 
 * private Long nodeId; private Long prntNodeId; private String name; private
 * String description; private Set properties = new HashSet();
 * 
 * We need to pass following request parameter values.
 * 
 * entityName=com.nms.util.beans.Service
 * 
 * com.bsi.common.beans.Property:name,propertiesSet=Medium
 * com.bsi.common.beans.Property:description,propertiesSet=Medium of the tuition
 * com.bsi.common.beans.Property:isMandatory,propertiesSet=Y
 * com.bsi.common.beans.Property:displayType,propertiesSet=TF
 * com.bsi.common.beans.Property:valueType,propertiesSet=String
 * com.bsi.common.beans.Property:tokenId,propertiesSet=123
 * com.bsi.common.beans.Property:displaySize,propertiesSet=22
 * com.bsi.common.beans.Property:dataSize,propertiesSet=33 nodeId=null
 * prntNodeId=23 name=Tutition description=Sample service
 * 
 * *
 * 
 * @author Nasiruddin
 * 
 */
public class ObjectHandler {

	private static Log log = LogFactory.getLog(ObjectHandler.class);

	/**
	 * Creates an object of specified type from the request values.
	 * 
	 * 
	 * @param objClassName
	 *            name of the class of the main object to be created.
	 * @param reqParamMap
	 *            values of the object in hash map
	 * @return
	 * @throws Exception
	 */

	public Object createObject(String objClassName, HashMap reqParamMap)
			throws Exception {
		Object convertedObject = null;

		CommonLogger.logDebug(log, "In ObjectHandler.createObject()");

		try {
			Class classToLoad = Class.forName(objClassName);
			convertedObject = classToLoad.newInstance();

			// The name of the html field tha contains the set values should end
			// with "Set"
			// Map newMap = (HashMap) reqParamMap.clone();
			Map newMap = processParamNames(reqParamMap);
			Set mapKeysSet = newMap.keySet();
			Iterator keys = mapKeysSet.iterator();
			// To contain the parameter name values for the Set of specified
			// object type
			Map childPropertiesMap = new HashMap();
			while (keys.hasNext()) {
				// String keyName = (String) keys.next();
				RequestParamValue reqParam = (RequestParamValue) keys.next();
				// if (keyName.endsWith("Set")) {
				if (reqParam.getTypeOfParentClassVarible().equals("Set")) {
					// String setPropertyName =
					// keyName.substring(keyName.lastIndexOf(',') + 1);
					String setPropertyName = reqParam
							.getNameOfParentClassVariable() + "Set";
					// Check if the values needs to be set in different object
					// than the parent.
					if (setPropertyName != null
							&& !childPropertiesMap.containsKey(setPropertyName)) {
						Map valueMap = new HashMap();
						valueMap.put(reqParam, newMap.get(reqParam));
						childPropertiesMap.put(setPropertyName, valueMap);
					} else {
						Map valueMap = (Map) childPropertiesMap
								.get(setPropertyName);
						valueMap.put(reqParam, newMap.get(reqParam));
						childPropertiesMap.put(setPropertyName, valueMap);
					}
				}
			}

			// Now further process the childPropertiesMap to generate the Set
			// object with specified object types.
			Iterator childIterator = childPropertiesMap.keySet().iterator();
			while (childIterator.hasNext()) {
				String keyName = (String) childIterator.next();
				// RequestParamValue reqParam = (RequestParamValue)
				// childIterator.next();

				if (keyName.endsWith("Set")) {
					// if (reqParam.getTypeOfParentClassVarible().equals("Set"))
					// {
					// String parentPropertyName = getPropertyName(keyName);

					Map valueMap = (Map) childPropertiesMap.get(keyName);
					ArrayToSetConverter converter = new ArrayToSetConverter();
					Set convertedSets = converter.convert(Set.class, valueMap);
					String parentClassPropertyName = null;
					Iterator iter = valueMap.keySet().iterator();
					while (iter.hasNext()) {
						parentClassPropertyName = ((RequestParamValue) iter
								.next()).getNameOfParentClassVariable();
						break;
					}
					// reqParamMap.put(reqParam.getNameOfParentClassVariable(),
					// convertedSets);
					reqParamMap.put(parentClassPropertyName, convertedSets);
				}
			}

			BeanUtils.populate(convertedObject, reqParamMap);
		} catch (BSIException ex1) {
			CommonLogger.logDebug(log,
					"In CreateObject \n exception occured. Exception message is "
							+ ex1.getMessage());
		} catch (Exception ex2) {
			ex2.printStackTrace();
			throw ex2;
		}
		return convertedObject;
	}

	public Map processParamNames(Map reqParamValuesMap) {

		Map modifiedMap = new HashMap();
		Iterator iter = reqParamValuesMap.keySet().iterator();
		RequestParamValue reqParamObj = null;

		while (iter.hasNext()) {
			reqParamObj = new RequestParamValue();
			String keyName = (String) iter.next();
			if (keyName.endsWith("Set") && keyName.contains(",")) {
				// nameOfParentClassVariable
				if (keyName.endsWith("Set")) {
					reqParamObj.setTypeOfParentClassVarible("Set");
				} else {
					reqParamObj.setTypeOfParentClassVarible("NULL");
				}
				StringTokenizer strTokens = new StringTokenizer(keyName, ":");
				while (strTokens.hasMoreTokens()) {
					String token = strTokens.nextToken();
					if (token.contains(",")) {
						StringTokenizer nameTokens = new StringTokenizer(token,
								",");
						if (nameTokens.countTokens() == 2) {
							reqParamObj.setNameofChildClassVarible(nameTokens
									.nextToken());
							// name of the parent property
							reqParamObj
									.setNameOfParentClassVariable(getPropertyName(nameTokens
											.nextToken()));
						}
					} else {
						reqParamObj.setClassOfParentClassVariable(token);
					}
				}

			} else {
				
				if (keyName.endsWith("Set")){
					reqParamObj.setNameOfParentClassVariable(getPropertyName(keyName));
					reqParamObj.setTypeOfParentClassVarible("Set");
					reqParamObj.setClassOfParentClassVariable("java.lang.String");
				}
				else {
				// It is of type String ex: name
					reqParamObj.setNameOfParentClassVariable(keyName);
					reqParamObj.setTypeOfParentClassVarible("NULL");
				}
			}
				modifiedMap.put(reqParamObj,
					(String[]) reqParamValuesMap.get(keyName));

		}

		return modifiedMap;
	}

	/**
	 * Gets the name of the property of the parent object from the request
	 * parameter.
	 * 
	 * @param reqParamName
	 * @return
	 */
	private String getPropertyName(String reqParamName) {
		// Assuming that the request param is of type
		// [com.bsi.common.beans.Property:name,propertiesSet]
		String propertyName = null;
		if (reqParamName.endsWith("Set")) {
			int index = reqParamName.lastIndexOf(',');
			propertyName = reqParamName.substring(index + 1,
					reqParamName.indexOf("Set"));
		}
		return propertyName;
	}
	
	public static HashMap<String, Object> getSearchPropertiesMap(Object convertedObject) throws Exception{
		HashMap<String, Object> criteria = new HashMap<String, Object>();
		Map objectProperties = PropertyUtils.describe(convertedObject);
		Iterator keys = objectProperties.keySet().iterator();
		
		while (keys.hasNext()) {
			try { 
				String keyName = (String) keys.next();
				Object propertyValue = PropertyUtils.getProperty(convertedObject, keyName);
				if (propertyValue == null) continue;
				// Uncheck "class" variable
				if (propertyValue instanceof List){
					if (((List) propertyValue).size() == 0) continue;
				}
				if (propertyValue instanceof Set){
					if (((Set) propertyValue).size() == 0) continue;
				}
				if (keyName.equalsIgnoreCase("class")){
					continue;
				} 
				criteria.put(keyName, propertyValue);
			} catch (Exception ex) {
				ex.printStackTrace();	
			}
		}
		return criteria;
	}
}
