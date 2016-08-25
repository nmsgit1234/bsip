package com.nms.util.db;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nms.db.IDataAccess;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

/**
 * This class does all operations on the object.
 * @author Nasiruddin
 *
 */
public class ObjectManager {

	public static IDataAccess getDbAccessor() {
		return dbAccessor;
	}


	public static void setDbAccessor(IDataAccess dbAccessor) {
		ObjectManager.dbAccessor = dbAccessor;
	}


	private static Log log = LogFactory.getLog(ObjectManager.class);

	private static IDataAccess dbAccessor = null;

	
	public Object createObject(Object obj) throws BSIException{
		
		Object object = null;
		try{
			object = dbAccessor.createObject(obj);
		}
		catch(Exception ex){
			CommonLogger
			.logError(
					log,
					"5068=Exception occured in ObjectManager.createObject();",
					ex);
			throw new BSIException("1008", "5068");
		}
		return object;
	}
	
	public List<Object> getObjects(String objName,HashMap<String,Object> criteria) throws BSIException{
		
		List<Object> objs = null;
		try{
			objs = dbAccessor.getObjects(objName,criteria);
		}
		catch(Exception ex){
			CommonLogger
			.logError(
					log,
					"5071=Exception occured while getting the object in ObjectManager.getObjects();",
					ex);
			throw new BSIException("1001", "5071");
		}
		return objs;
	}

	
	/**
	 * This method is for searching for the objects with parameters of like type. This is used for search operations.
	 * 
	 * @param objName Type of the object to be searched
	 * @param criteria 
	 * @return
	 * @throws BSIException
	 */
	public List<Object> getObjectsLike(String objName,HashMap<String,Object> criteria) throws BSIException{
		
		List<Object> objs = null;
		try{
			objs = dbAccessor.getObjectsLike(objName,criteria);
		}
		catch(Exception ex){
			CommonLogger
			.logError(
					log,
					"5071=Exception occured while getting the object in ObjectManager.getObjects();",
					ex);
			throw new BSIException("1001", "5071");
		}
		return objs;
	}

	public Object updatetObject(Object objID, Class classToLoad, Method[] methods, List<Object> methodArgs) throws BSIException {
		Object object = null;
		try{
			object = dbAccessor.updateObjectRequested(objID, classToLoad, methods, methodArgs);
		}
		catch(Exception ex){
			CommonLogger
			.logError(
					log,
					"5072=Exception occured while updating the object in ObjectManager.updatetObject();",
					ex);
			throw new BSIException("1001", "5072");
		}
		return object;
	}
	

	public Object updatetObject(Object objToUpdate) throws BSIException {
		Object object = null;
		try{
			dbAccessor.updateObject(objToUpdate);
		}
		catch(Exception ex){
			CommonLogger
			.logError(
					log,
					"5072=Exception occured while updating the object in ObjectManager.updatetObject();",
					ex);
			throw new BSIException("1001", "5072");
		}
		return object;
	}
	
	public Object deletetObject(Object objID) throws BSIException {
		Object object = null;
		try{
			dbAccessor.deleteObject(objID);
		}
		catch(Exception ex){
			CommonLogger
			.logError(
					log,
					"5073=Exception occured while updating the object in ObjectManager.deletetObject();",
					ex);
			throw new BSIException("1001", "5073");
		}
		return object;
	}
	
	
	
}
