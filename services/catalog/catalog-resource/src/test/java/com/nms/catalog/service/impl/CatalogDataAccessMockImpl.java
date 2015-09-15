package com.nms.catalog.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.bsi.common.beans.Service;
import com.nms.db.IDataAccess;

public class CatalogDataAccessMockImpl implements IDataAccess{

	public Object createObject(Object object) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object createObject(Session session, Object object)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public void createObjects(List<Object> objs) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	public void createObjectsIfNotExist(List<Object> objs)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	public List<Object> getObjectsLike(String objName,
			HashMap<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getObjects(String objectType)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getObjects(String objectType,
			HashMap<String, Object> criteria) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObjectRequested(Object objId, Class objClass,
			Method[] methods) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObjectRequested(Session session, Object objId,
			Class objClass, Method[] methods) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Object> getObjectsRequested(List objIds, Class objClass,
			Method[] methods) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Object> getObjectsRequested(Session session, List objIds,
			Class objClass, Method[] methods) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getObjectsRequested(Class<Object> objClass,
			HashMap<String, String> criteria) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getObjectsFromQuery(String hqlQuery, List paramValues,
			Method[] methods) throws DataAccessException {
		List<Service> services = new ArrayList<Service>();
		Service childService = new Service();
		childService.setNodeId(new Long(1));
		childService.setName("Services");
		services.add(childService);
		return services;
	}

	public List getObjectsFromQuery(Session session, String hqlQuery,
			List paramValues, Method[] methods) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteObjects(List<Object> objList) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	public void deleteObjects(Session session, List<Object> objList)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	public void deleteObject(Object obj) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	public boolean deleteObjectByCondition(String objType,
			HashMap<String, String> criteria) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateObject(Object obj) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	public void updateObject(Session session, Object object)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	public Object updateObjectRequested(Object objId, Class objClass,
			Method[] methods, List methodArgs) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateObjects(List<Object> objects) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	public int updateObjects(Session session, String hqlQuery,
			List<Object> objects) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void saveOrUpdate(List<Object> objs) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	public void saveOrUpdate(Session session, List<Object> objs)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	public List<Object[]> getObjectsFromSQlqueryWithAggregateFunctions(
			String objType, String selectValues,
			Set<Map<String, Object>> criteria, Map<String, String> groupCriteria)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
