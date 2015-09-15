package com.nms.util.ui;

import static org.junit.Assert.fail;

import java.security.Provider.Service;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class ObjectHandlerTest {

	@Test
	public void testCreateObject() throws Exception {
		HashMap reqMap = new HashMap();
		reqMap.put("com.nms.util.sample.Property:name,propertiesSet",new String[]{"Medium","Name2"});
		reqMap.put("com.nms.util.sample.Property:description,propertiesSet",new String[]{"Medium of the tuition","Description2"});
		reqMap.put("com.nms.util.sample.Property:isMandatory,propertiesSet",new String[]{"Y","N"});
		reqMap.put(	"com.nms.util.sample.Property:displayType,propertiesSet",new String[]{"TF","DD"});
		reqMap.put("com.nms.util.sample.Property:valueType,propertiesSet",new String[]{"String","String"});
		reqMap.put("com.nms.util.sample.Property:tokenId,propertiesSet",new String[]{"123","456"});
		reqMap.put("com.nms.util.sample.Property:displaySize,propertiesSet",new String[]{"22","33"});
		reqMap.put("com.nms.util.sample.Property:dataSize,propertiesSet",new String[]{"24","34"});
		reqMap.put("prntNodeId", new String[]{"23"});
		reqMap.put("name",new String[]{"Tuition"});
		reqMap.put("description",new String[]{"Sample service"});
		ObjectHandler objHandler = new ObjectHandler();
		Object obj = objHandler.createObject("com.nms.util.sample.ServiceBean",reqMap);
		Assert.assertTrue("The map not converted properly", obj != null);
	}

	@Test
	public void testProcessParamNames() {
		Map reqMap = new HashMap();
		reqMap.put("com.nms.util.sample.Property:name,propertiesSet",new String[]{"Medium"});
		reqMap.put("com.nms.util.sample.Property:description,propertiesSet",new String[]{"Medium of the tuition"});
		reqMap.put("com.nms.util.sample.Property:isMandatory,propertiesSet",new String[]{"Y"});
		reqMap.put(	"com.nms.util.sample.Property:displayType,propertiesSet",new String[]{"TF"});
		reqMap.put("com.nms.util.sample.Property:valueType,propertiesSet",new String[]{"String"});
		reqMap.put("com.nms.util.sample.Property:tokenId,propertiesSet",new String[]{"123"});
		reqMap.put("com.nms.util.sample.Property:displaySize,propertiesSet",new String[]{"22"});
		reqMap.put("com.nms.util.sample.Property:dataSize,propertiesSet",new String[]{"24"});
		reqMap.put("prntNodeId", new String[]{"23"});
		reqMap.put("name",new String[]{"Tuition"});
		reqMap.put("description",new String[]{"Sample service"});
		ObjectHandler objHandler = new ObjectHandler();
		Map newMap = objHandler.processParamNames(reqMap);
		Assert.assertTrue("The map not converted properly", newMap.keySet().iterator().hasNext());
	}
	
}
