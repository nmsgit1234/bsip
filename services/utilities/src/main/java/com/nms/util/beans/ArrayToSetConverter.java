package com.nms.util.beans;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.Converter;

import com.nms.util.ui.RequestParamValue;

public class ArrayToSetConverter implements Converter {
	private String delimiter = ",";

	public Object convert(Class type, Object value) {
		if (value == null || !value.getClass().equals(String[].class)) {
			return null;
		} else {
			return convertStringArrayToSet((String[]) value);
		}
	}

	public Set convert(Class type, Map valueMap) throws Exception {
		if (valueMap == null)
			return null;
		Set converedSet = null;
		Iterator iter = valueMap.keySet().iterator();
		while (iter.hasNext()) {
			// String keyName = (String) iter.next();
			RequestParamValue reqParam = (RequestParamValue) iter.next();
			converedSet = convert(Set.class, valueMap.get(reqParam), reqParam);
			while (iter.hasNext()) {
				// keyName = (String) iter.next();
				reqParam = (RequestParamValue) iter.next();
				updateSet(converedSet, (String[]) valueMap.get(reqParam),
						reqParam);
			}
		}
		return converedSet;
	}

	/**
	 * Updates the given set containing object of specified type with the values
	 * given.
	 * 
	 * @param converedSet
	 * @param object
	 * @param keyName
	 */
	private Set updateSet(Set setToUpdate, String[] valueArray,
			RequestParamValue reqParam) throws Exception {
		Iterator setIter = setToUpdate.iterator();
		List valueList = new LinkedList(Arrays.asList(valueArray));
		Iterator valueIter = valueList.iterator();

		// Identify the property in the object to update.
		// String propertyName = getChildObjectPropertyName(propertyNameToken);
		String propertyName = reqParam.getNameofChildClassVarible();
		while (setIter.hasNext()) {
			Object objToUpdate = setIter.next();
			updateObject(objToUpdate, propertyName, valueIter.next());
		}
		return setToUpdate;
	}

	public Set convert(Class type, Object value, RequestParamValue reqParam)
			throws Exception {
		if (value == null || !value.getClass().equals(String[].class))
			return null;
		// if (value == null) return null;
		// First instantiate the object of the type as mentioned in the parame
		Set convertedSet = new LinkedHashSet();
		// if (paramName != null && paramName.contains(":")){
		if (reqParam != null
				//&& !reqParam.getTypeOfParentClassVarible().equals("java.lang.String")) {
				&& !reqParam.getClassOfParentClassVariable().equals("java.lang.String")) {
			// Create tokens to know the parent class.
			// String propertyName = getChildObjectPropertyName(paramName);
			String propertyName = reqParam.getNameofChildClassVarible();
			Object parentObj = null;
			// First token represent the parent class and the second token has
			// the property name within
			// String className = getClassName(paramName);
			String className = reqParam.getClassOfParentClassVariable();
			Class classToLoad = Class.forName(className);
			parentObj = classToLoad.newInstance();
			convertedSet = convertObjectToSet((String[]) value, classToLoad,
					propertyName);
		} else {
			convertedSet = convertStringArrayToSet((String[]) value);
		}

		return convertedSet;

	}

	public Set convertStringArrayToSet(String[] valueStrArray) {
		Set<String> convertedSet = new HashSet<String>();
		if (valueStrArray.length == 0)
			return null;
		for (String arrayValue : valueStrArray) {
			convertedSet.add(arrayValue);
		}
		return convertedSet;
	}

	/**
	 * Based on the property Name For eg: Property.name.propertiesSet Creates
	 * the object of type Property, sets the name value in that and then creates
	 * a set of Property objects.
	 * 
	 * @param valueStrArray
	 * @return
	 */
	// public Set convertObjectToSet(String[] valueStrArray) {
	// Set<String> convertedSet = new HashSet<String>();
	// if (valueStrArray.length == 0)
	// return null;
	// for (String arrayValue : valueStrArray) {
	// convertedSet.add(arrayValue);
	// }
	// return convertedSet;
	// }

	/**
	 * This method creates the set of the specified object type after setting
	 * the give property value on the newly created objects.
	 * 
	 * @param valueStrArray
	 * @param classOfObj
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public Set<Object> convertObjectToSet(String[] valueStrArray,
			Class classOfObj, String paramName) throws Exception {
		Set<Object> convertedSet = new HashSet<Object>();
		Object parentObj = null;
		for (String arrayValue : valueStrArray) {
			parentObj = classOfObj.newInstance();
			parentObj = updateObject(parentObj, paramName, arrayValue);
			convertedSet.add(parentObj);
		}
		return convertedSet;
	}

	/**
	 * Updates the property value for the given object.
	 * 
	 * @param objToUpdate
	 * @param paramName
	 * @param paramValue
	 * @return
	 * @throws Exception
	 */
	private Object updateObject(Object objToUpdate, String paramName,
			Object paramValue) throws Exception {
		Map valueMap = new HashMap();
		valueMap.put(paramName, paramValue);
		BeanUtils.populate(objToUpdate, valueMap);
		return objToUpdate;
	}

	private String getChildObjectPropertyName(String paramName) {
		String propertyName = null;
		StringTokenizer strTokens = new StringTokenizer(paramName, ":");
		while (strTokens.hasMoreTokens()) {
			String propertyNameToken = strTokens.nextToken();
			if (propertyNameToken.contains(",")) {
				StringTokenizer nameTokens = new StringTokenizer(
						propertyNameToken, ",");
				if (nameTokens.countTokens() == 2) {
					propertyName = nameTokens.nextToken();
				}
			}

		}
		return propertyName;
	}

	private String getClassName(String paramName) {
		String className = null;
		StringTokenizer strTokens = new StringTokenizer(paramName, ":");
		int tokenSize = strTokens.countTokens();
		if (tokenSize == 2)
			// First token represent the parent class and the second token has
			// the property name within
			className = strTokens.nextToken();
		return className;

	}

	// public String getParentObjectPropertyName(String paramName) {
	// String propertyName = null;
	// StringTokenizer strTokens = new StringTokenizer(paramName, ":");
	// int tokenSize = strTokens.countTokens();
	// if (tokenSize == 2) {
	// // First token represent the parent class and the second token has
	// // the property name within
	// String propertyNameToken = strTokens.nextToken();
	// StringTokenizer propertyTokens = new StringTokenizer(
	// propertyNameToken, ",");
	// if (propertyTokens.countTokens() == 2) {
	// propertyName = propertyTokens.nextToken();
	// }
	// }
	// return propertyName;
	// }

}
