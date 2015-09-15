package com.nms.db;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.nms.util.db.RestrictionConstants;

public class DataAccessHibernateImplTest extends TestCase {
	private static IDataAccess daoClass = null;
	private static Log log = LogFactory.getLog(DataAccessHibernateImplTest.class);

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext context = new FileSystemXmlApplicationContext(
				new String[] {"src/test/resources/Test_bean_config.xml",
				"src/test/resources/Test_Spring_config.xml"});
	}

	public void setDaoClass(IDataAccess daoClass) {
		this.daoClass = daoClass;
	}
	
	@Test
	public void testGetObjectsFromSQlqueryWithAggregateFunctions(){
		String selectValues = "id,count(id) as totalProperties ";
		Set<Map<String,Object>> criteriaSet = new HashSet<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("property_id", "5");
		map.put("property_value", "In house");
		criteriaSet.add(map);
		map = new HashMap<String,Object>();
		map.put("property_id", "5");
		map.put("property_value", "Out house");
		criteriaSet.add(map);

		Map<String,String> groupCriteria = new HashMap<String,String>();
		groupCriteria.put(RestrictionConstants.GROUP_BY, "id");
		
		List objects = daoClass.getObjectsFromSQlqueryWithAggregateFunctions("supplier_service_property_values", selectValues, criteriaSet, groupCriteria);
		assertTrue("The number of objects returned doesn't match", objects!= null && objects.size() > 0);
	}
	
	@Test
	public void testPrepareWhereClauseForSetCriteria(){
		DataAccessHibernateImpl dbImpl = new DataAccessHibernateImpl();
		Set<Map<String,Object>> criteriaSet = new HashSet<Map<String,Object>>();
		for (int i=0;i < 6;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("property_id", "5" + i);
			map.put("property_value", "Value_" + i);
			criteriaSet.add(map);
		}
		String whereClauseStr = dbImpl.prepareWhereClause(criteriaSet);
		assertTrue("The where clause string not correct", whereClauseStr.length() > 0);
	}

}
