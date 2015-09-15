package com.nms.util.beans;

import java.util.HashMap;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.nms.util.ui.ObjectHandler;


public class ArrayToSetConverterTest {

	@Test
	public void convert() {
		String[] strTokens = { "Token1", "Token2", "Token3" };
		ArrayToSetConverter converter = new ArrayToSetConverter();
		Object convertedSet = converter.convert(Set.class, strTokens);
		Assert.assertTrue("Conversion failed", ((Set) convertedSet).size() > 0);
	}

	@Test
	/**
	Tests for converting the request values to the object specified into Set
	 */
	/*
	 * public void convertSetOfObjects() throws Exception { String[] strTokens =
	 * { "testProperty1", "testProperty2", "testProperty3" }; String
	 * requestParamName = "com.nms.util.beans.Property:name,propertiesSet";
	 * ArrayToSetConverter converter = new ArrayToSetConverter(); Set
	 * convertedSet = converter.convert((Class)Set.class, (Object)strTokens,
	 * requestParamName); Assert.assertTrue("Conversion failed", ((Set)
	 * convertedSet).size() > 0); }
	 */
	public void convertSetOfObjects() throws Exception {
		String[] strTokens = { "testProperty1", "testProperty2",
				"testProperty3" };
		String requestParamName = "com.nms.util.sample.Property:name,propertiesSet";
		HashMap reqParamMap = new HashMap();
		reqParamMap.put(requestParamName, strTokens);
		ObjectHandler objHandler = new ObjectHandler();
		Object convertedObject = objHandler.createObject("com.nms.util.sample.Property", reqParamMap);
		Assert.assertTrue("Conversion failed", convertedObject instanceof com.nms.util.sample.Property);
	}

}
