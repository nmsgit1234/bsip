package com.nms.db;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

/**
 * Interface for the all data access operation.
 * 
 * @author nshaikh
 * 
 */
public interface IDataAccess {

	/**
	 * ######################################################## 
	 * Methods related to CREATING OBJECTS
	 * ########################################################
	 */

	/**
	 * Creates an object as specified by the POJO object
	 * 
	 * @param object
	 *            POJO
	 * @return 
	 */
	Object createObject(Object object) throws DataAccessException;

	/**
	 * Creates the object using the same session.
	 * @param session
	 * @param object
	 * @throws DataAccessException
	 */
	Object createObject(Session session,Object object) throws DataAccessException;
	
	/**
	 * Creates objects as specified in the list
	 * 
	 * @param objs
	 *            list of the objects to be created.
	 */
	void createObjects(List<Object> objs) throws DataAccessException;
	/**
	 * Creates objects only if not exists earlier.
	 * 
	 * @param objs
	 */
	void createObjectsIfNotExist(List<Object> objs) throws DataAccessException;

	/**
	 * ######################################################## 
	 * Methods related to GETTING OBJECTS
	 * ########################################################
	 */

	/**
	 * Get list of the objects where the given criteria is evaluated as like parameter.
	 * @param objName
	 * @param criteria
	 * @return
	 */
	List<Object> getObjectsLike(String objName, HashMap<String, Object> criteria);

	/**
	 * Gets list of all object of the specified type.
	 * 
	 * @param objectType
	 *            class name of the object to be retrieved
	 * @return
	 */
	List<Object> getObjects(String objectType) throws DataAccessException;
	/**
	 * Gets all the objects of the specific type based on some criteria
	 * 
	 * @param objectType
	 *            Type of the objects to
	 * @param criteria
	 *            criteria
	 * @return
	 */
	List<Object> getObjects(String objectType, HashMap<String, Object> criteria) throws DataAccessException;
	/**
	 * Gets all the objects of the specific type based on some criteria in a set
	 * 
	 * @param objectType
	 *            Type of the objects to
	 * @param criteria
	 *            criteria
	 * @return
	 */
	List<Object[]> getObjectsFromSQlqueryWithAggregateFunctions(String objType, String selectValues,Set<Map<String,Object>> criteria,Map<String,String> groupCriteria) throws DataAccessException;

	/**
	 * Gets the requested object of the specified type and childrens.
	 * 
	 * @param objId
	 *            id of the object to be retrieved
	 * @param objClass
	 *            Type of the object
	 * @param methods
	 *            childrens needed.
	 * @return Object
	 */
	Object getObjectRequested(Object objId, Class objClass,
			Method[] methods) throws DataAccessException;
	/**
	 * Gets the requested object from the same sessin.
	 * @param session
	 * @param objId
	 * @param objClass
	 * @param methods
	 * @return
	 * @throws DataAccessException
	 */
	Object getObjectRequested(Session session,Object objId, Class objClass,
			Method[] methods) throws DataAccessException;
	/**
	 * Gets the Set of the specified objects.
	 * 
	 * @param objIds
	 *            id's of the objects to be retrieved in list
	 * @param objClass
	 *            type of the object
	 * @param methods
	 *            childrens to retrieve
	 * @return Set
	 */
	Set<Object> getObjectsRequested(List objIds,
			Class objClass, Method[] methods) throws DataAccessException;

	/**
	 * Gets the requested objects from the same session.
	 * @param session
	 * @param objIds
	 * @param objClass
	 * @param methods
	 * @return
	 * @throws DataAccessException
	 */
	//TODO need to use the spring transaction management.
	Set<Object> getObjectsRequested(Session session,List objIds,
			Class objClass, Method[] methods) throws DataAccessException;
	/**
	 * Fetches all the objects of the specific class with defined criteria.
	 * 
	 * @param objClass
	 * @param criteria
	 * @return
	 */
	List<Object> getObjectsRequested(Class<Object> objClass,
			HashMap<String, String> criteria) throws DataAccessException;
	
	
	List getObjectsFromQuery(String hqlQuery, List paramValues, Method[] methods)
			throws DataAccessException;
	
	/**
	 * Fetches the object by exceuting the hql query with parameters. Also invokes the specified method
	 * on the objects.
	 * @param session
	 * @param hqlQuery
	 * @param paramValues
	 * @param methods
	 * @return
	 * @throws DataAccessException
	 */
	List getObjectsFromQuery(Session session, String hqlQuery, List paramValues, Method[] methods)
	throws DataAccessException;

	/**
	 * ######################################################## 
	 * Methods related to DELETING OBJECTS
	 * ########################################################
	 */

	/**
	 * Deletes objects as specified in the list.
	 * 
	 * @param objects
	 * @param objClass
	 * @return
	 */
	void deleteObjects(List<Object> objList) throws DataAccessException;

	/**
	 * Delets the list of objects from same session
	 * @param objList
	 * @throws DataAccessException
	 */
	void deleteObjects(Session session, List<Object> objList) throws DataAccessException;

	/**
	 * Deletes single object.
	 * 
	 * @param obj
	 */
	void deleteObject(Object obj) throws DataAccessException;
	/**
	 * Deletes object based on some condition.
	 * 
	 * @param objType
	 * @param criteria
	 * @return
	 */
	boolean deleteObjectByCondition(String objType,
			HashMap<String, String> criteria) throws DataAccessException;

	/**
	 * ######################################################## 
	 * Methods related to UPDATING OBJECTS
	 * ########################################################
	 */

	/**
	 * Updates the specfied object.
	 * 
	 * @param obj
	 */
	void updateObject(Object obj) throws DataAccessException;

	/**
	 * Updates the object in same session.
	 * @param session
	 * @param objects
	 * @throws DataAccessException
	 */
	void updateObject(Session session,Object object) throws DataAccessException;

	/**
	 * This method is used for storing the childrens to the parents.
	 * 
	 * @param objId
	 *            id of parent object
	 * @param objClass
	 *            type of the object
	 * @param methods
	 *            methods to call
	 * @param methodArgs
	 *            values to set for the child object.
	 */
	Object updateObjectRequested(Object objId, Class objClass,
			Method[] methods, List methodArgs) throws DataAccessException;
	/**
	 * Updates the list of the requestd objects
	 * 
	 * @param objects
	 */
	void updateObjects(List<Object> objects) throws DataAccessException;
	
	/**
	 * Updates the object in same session by executing the hql query 
	 * with parameters specified in the list.
	 * @param objects
	 * @throws DataAccessException
	 */
	int updateObjects(Session session, String hqlQuery,List<Object> objects) throws DataAccessException;
	
	/**
	 * Saves or updates the list of the objects.
	 * 
	 * @param objs
	 */
	void saveOrUpdate(List<Object> objs) throws DataAccessException;
	
	/**
	 * Saves or updagtes the objects in same session
	 * @param session
	 * @param objs
	 * @throws DataAccessException
	 */
	void saveOrUpdate(Session session, List<Object> objs) throws DataAccessException;
}
