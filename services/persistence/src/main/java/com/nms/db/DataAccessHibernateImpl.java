package com.nms.db;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.nms.util.beans.ToString;
import com.nms.util.db.BSIException;
import com.nms.util.db.RestrictionConstants;
import com.nms.util.log.CommonLogger;

/**
 * Implementation for the data access using Hibernate
 * 
 * @author nshaikh
 * 
 */
public class DataAccessHibernateImpl extends HibernateDaoSupport implements
		IDataAccess {

	private static Log log = LogFactory.getLog(DataAccessHibernateImpl.class);

	public Object createObject(Object object) throws DataAccessException {
		CommonLogger.logDebug(log, "In DataAccessHibernateImpl.createObject()"
				+ ToString.toString(object));
		return createObject(null, object);
	}

	public void createObjects(List<Object> objs) throws DataAccessException {
		CommonLogger.logDebug(
				log,
				"In DataAccessHibernateImpl:createObjects(List)"
						+ ToString.toString(objs));
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession();
			tx = session.beginTransaction();
			for (int x = 0; x < objs.size(); x++) {
				session.save(objs.get(x));
			}
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"In DataAccessHibernateImpl:createObjects(List)) :", ex);
			throw convertHibernateAccessException(ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public Object createObject(Session session, Object obj)
			throws DataAccessException {
		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl.createObject(Session session, Object obj) "
						+ ToString.toString(obj));
		// Session session = null;
		Transaction tx = null;
		boolean isNewTxn = false;

		try {
			if (session == null) {
				session = getSession();
				isNewTxn = true;
				tx = session.beginTransaction();
			}

			tx = session.getTransaction();
			session.save(obj);

			if (isNewTxn) {
				tx.commit();
			}
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while creating object", ex);
			throw convertHibernateAccessException(ex);
		} finally {
			if (isNewTxn && session != null) {
				session.close();
			}
		}
		return obj;
	}

	public void createObjectsIfNotExist(List<Object> objs)
			throws DataAccessException {
		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl.:createObjectsIfNotExist()"
						+ ToString.toString(objs));
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession();
			tx = session.beginTransaction();
			for (int x = 0; x < objs.size(); x++) {

				try {
					// Check if exists.
					session.save(objs.get(x));
				} catch (ConstraintViolationException ex1) {
					CommonLogger.logDebug(log, "The object already exists ");
				}
			}
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while creating object", ex);
			throw convertHibernateAccessException(ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	public List<Object> getObjects(String objectType)
			throws DataAccessException {
		List objects = null;
		if (objectType != null && !"".equalsIgnoreCase(objectType)) {
			objects = getObjects(objectType, null);
		}
		return objects;
	}

	public List<Object> getObjectsLike(String objectType,
			HashMap<String, Object> criteria) {
		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl.:getObjectsLike()" + objectType
						+ ":" + criteria);
		Session session = null;
		Transaction tx = null;
		List results = null;
		StringBuffer sbr = new StringBuffer();

		try {
			session = getSession();
			tx = session.beginTransaction();

			// Create the query
			sbr.append("from ").append(objectType).append(" as obj ");

			if (criteria != null && criteria.keySet().size() > 0) {
				sbr.append("where obj.");

				Iterator iter = criteria.keySet().iterator();

				for (int z = 0; iter.hasNext(); z++) {
					String key = (String) iter.next();
					Object value = criteria.get(key);
					CommonLogger.logDebug(log,
							"The key value pairs are : key :" + key
									+ ", value :" + value);
					if (z > 0) {
						sbr.append(" and obj.");
					}
					if (value.getClass() == String.class) {
						sbr.append(key).append(" like '%").append(value)
								.append("%'");
					} else {
						sbr.append(key).append("=").append(value);
					}
				}
			}
			CommonLogger.logDebug(log, "The query is " + sbr.toString());
			results = session.createQuery(sbr.toString()).list();
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while getting object", ex);
			throw convertHibernateAccessException(ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}

	public List<Object> getObjects(String objectType,
			HashMap<String, Object> criteria) throws DataAccessException {
		CommonLogger.logDebug(log, "In DataAccessHibernateImpl.:getObjects()"
				+ objectType + ":" + criteria);
		Session session = null;
		Transaction tx = null;
		List results = null;
		StringBuffer sbr = new StringBuffer();

		try {
			session = getSession();
			tx = session.beginTransaction();

			// Create the query
			sbr.append("from ").append(objectType).append(" as obj ");
			sbr.append(prepareWhereClause(criteria));
			CommonLogger.logDebug(log, "The query is " + sbr.toString());
			results = session.createQuery(sbr.toString()).list();
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while getting object", ex);
			throw convertHibernateAccessException(ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}

	public String prepareWhereClause(HashMap<String, Object> criteria) {
		StringBuffer sbr = new StringBuffer();
		if (criteria != null && criteria.keySet().size() > 0) {
			sbr.append("where obj.");

			Iterator iter = criteria.keySet().iterator();

			for (int z = 0; iter.hasNext(); z++) {
				String key = (String) iter.next();
				Object value = criteria.get(key);
				CommonLogger.logDebug(log, "The key value pairs are : key :"
						+ key + ", value :" + value);
				if (z > 0) {
					sbr.append(" and obj.");
				}
				if (value.getClass() == String.class) {
					sbr.append(key).append("='").append(value).append("'");
				} else {
					sbr.append(key).append("=").append(value);
				}
			}
		}
		return sbr.toString();
	}

	/**
	 * This query is used when we need to get aggregate functions like count, sum etc.
	 * @param objectType the table name
	 * @param selectValues the select values to update in the select clause
	 * @param criteria this will go in the where clause
	 * @param groupCriteria group by/order by criterias.
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object[]> getObjectsFromSQlqueryWithAggregateFunctions(String objectType,
			String selectValues, Set<Map<String, Object>> criteria,
			Map<String, String> groupCriteria) throws DataAccessException {
		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl.:getObjectsFromWhereClause()"
						+ objectType + ":" + criteria);
		Session session = null;
		Transaction tx = null;
		List<Object[]> results = null;
		StringBuffer sbr = new StringBuffer();

		try {
			session = getSession();
			tx = session.beginTransaction();

			// Create the query
			if (selectValues != null && selectValues.trim().length() > 0) {
				sbr.append("select ").append(selectValues).append(" from ").append(objectType).append(" as obj ");
			} else {
				sbr.append("select * from ").append(objectType).append(" as obj ");
			}
			sbr.append(prepareWhereClause(criteria));
			// Append the group clause
			if (groupCriteria != null) {
				Iterator keySetIter = groupCriteria.keySet().iterator();
				while (keySetIter.hasNext()) {
					String key = (String) keySetIter.next();
					Object value = groupCriteria.get(key);
					if (key != null
							&& key.equalsIgnoreCase(RestrictionConstants.GROUP_BY)) {
						sbr.append("group by ").append(value).append(" ");
					}
				}
			}
			CommonLogger.logDebug(log, "The query is " + sbr.toString());
			results = session.createSQLQuery(sbr.toString()).list();
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while getting object", ex);
			throw convertHibernateAccessException(ex);
		} 
		catch(Exception e){
			CommonLogger.logError(log,
					"Exception occured while getting object", e);
			
		}
		finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}

	public String prepareWhereClause(Set<Map<String, Object>> criteria) {
		StringBuffer sbr = new StringBuffer();
		Iterator<Map<String, Object>> iter = criteria.iterator();
		if (criteria != null && criteria.size() > 0) {
			sbr.append("where ");
			while (iter.hasNext()) {
				sbr.append("(obj. ");
				Map<String, Object> valueMap = iter.next();
				Iterator keySetIter = valueMap.keySet().iterator();
				while (keySetIter.hasNext()) {
					String key = (String) keySetIter.next();
					Object value = valueMap.get(key);
					CommonLogger.logDebug(log,
							"The key value pairs are : key :" + key
									+ ", value :" + value);
					if (value.getClass() == String.class) {
						sbr.append(key).append("='").append(value).append("'");
					} else {
						sbr.append(key).append("=").append(value);
					}
					if (keySetIter.hasNext()) {
						sbr.append(" and obj.");
					}
				}
				sbr.append(") ");
				if (iter.hasNext()) {
					sbr.append(" or ");
				}
			}
		}
		return sbr.toString();
	}

	public Object getObjectRequested(Object objId, Class objClass,
			Method[] methods) throws DataAccessException {
		return getObjectRequested(null, objId, objClass, methods);
	}

	public Set getObjectsRequested(List objIds, Class objClass, Method[] methods)
			throws DataAccessException {
		Set objects = getObjectsRequested(null, objIds, objClass, methods);
		return objects;
	}

	public Object getObjectRequested(Session session, Object objId,
			Class objClass, Method[] methods) throws DataAccessException {
		List objList = new ArrayList();
		Object result = null;
		objList.add(objId);
		Set results = getObjectsRequested(session, objList, objClass, methods);
		Object[] objs = results.toArray();
		if (objs.length > 0) {
			result = objs[0];
		}
		return result;

	}

	public Set getObjectsRequested(Session session, List objIds,
			Class objClass, Method[] methods) throws DataAccessException {
		CommonLogger
				.logDebug(log,
						"In DataAccessHibernateImpl:getObjectsRequested(), objIDs are "
								+ ToString.toString(objIds) + " methods are "
								+ methods);
		// Session session = null;
		Transaction tx = null;
		Object obj = null;
		Set objs = new HashSet();
		boolean isNewTxn = false;
		try {

			if (session == null) {
				session = getSession();
				isNewTxn = true;
				tx = session.beginTransaction();
			}
			tx = session.getTransaction();

			for (int z = 0; z < objIds.size(); z++) {
				obj = session.load(objClass, (Serializable) objIds.get(z));

				if (methods != null && methods.length > 0) {
					for (int x = 0; x < methods.length; x++) {
						Object[] args = null;

						/*
						 * try { if (methodArgs != null && methodArgs.size() >
						 * 0) { args = (Object[])methodArgs.get(x); } }
						 * catch(Exception ex) {
						 * 
						 * }
						 */
						Hibernate.initialize(methods[x].invoke(obj, args));
					}
				}

				Hibernate.initialize(obj);
				objs.add(obj);
			}
			// session.save();
			if (isNewTxn) {
				tx.commit();
			}
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while getting object", ex);
			throw convertHibernateAccessException(ex);
		} catch (Exception ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while getting object", ex);
			throw convertHibernateAccessException(new HibernateException(ex));

		} finally {
			if (isNewTxn && session != null) {
				session.close();
			}
		}
		return objs;
	}

	public List<Object> getObjectsRequested(Class<Object> objClass,
			HashMap<String, String> criteria) {
		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl:getObjectsRequested(): "
						+ "objClass is " + objClass + " criteria is "
						+ criteria);
		Session session = null;
		Transaction tx = null;
		List results = null;
		StringBuffer sbr = new StringBuffer();
		try {
			session = getSession();
			tx = session.beginTransaction();
			Criteria rootCriteria = session.createCriteria(objClass);
			if (criteria != null) {
				Iterator iter = criteria.keySet().iterator();
				for (int z = 0; iter.hasNext(); z++) {
					String key = (String) iter.next();
					String value = (String) criteria.get(key);
					CommonLogger.logDebug(log,
							"The key value pairs are : key :" + key
									+ ", value :" + value);
					if (key == null) {
						continue;
					}
					if (key.equalsIgnoreCase(RestrictionConstants.ORDER_BY_ASCENDING)) {
						rootCriteria.addOrder(Order.asc(value));
					} else if (key
							.equalsIgnoreCase(RestrictionConstants.ORDER_BY_DESCENDING)) {
						rootCriteria.addOrder(Order.desc(value));
					} else {
						rootCriteria.add(RestrictionConstants.getCriterion(key,
								value));
					}
				}
			}
			results = rootCriteria.list();
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while getting object", ex);
			throw convertHibernateAccessException(ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}

	public void deleteObjects(List<Object> objList) {
		deleteObjects(null, objList);
	}

	public void deleteObject(Object obj) throws DataAccessException {
		List objList = new ArrayList();
		objList.add(obj);
		deleteObjects(null, objList);
	}

	public boolean deleteObjectByCondition(String objType,
			HashMap<String, String> criteria) throws DataAccessException {
		return deleteObjectByCondition(null, objType, criteria);
	}

	public void deleteObjects(Session session, List objList)
			throws DataAccessException {
		CommonLogger.logDebug(log, "In DataAccesssHibernateImpl:deleteObject()"
				+ ToString.toString(objList));
		Transaction tx = null;
		boolean isNewTxn = false;

		try {
			if (session == null) {
				session = getSession();
				isNewTxn = true;
				tx = session.beginTransaction();
			}

			tx = session.getTransaction();
			for (int x = 0; x < objList.size(); x++) {
				session.delete(objList.get(x));
			}

			if (isNewTxn) {
				tx.commit();
			}
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while deleting object", ex);
			throw convertHibernateAccessException(ex);
		} finally {
			if (isNewTxn && session != null) {
				session.close();
			}
		}
	}

	public boolean deleteObjectByCondition(Session session, String objType,
			HashMap criteria) throws DataAccessException {
		CommonLogger.logDebug(log, "In DataAccessHibernateImpl:deleteObject()"
				+ ". objType is " + objType + ", criteria is " + criteria);
		Transaction tx = null;
		boolean isNewTxn = false;

		try {
			if (session == null) {
				session = getSession();
				isNewTxn = true;
				tx = session.beginTransaction();
			}
			tx = session.getTransaction();
			// Create the query
			StringBuffer sbr = new StringBuffer();
			sbr.append("delete from ").append(objType).append(" as obj ");
			if (criteria != null) {
				sbr.append("where obj.");
				Iterator iter = criteria.keySet().iterator();
				for (int z = 0; iter.hasNext(); z++) {
					String key = (String) iter.next();
					Object value = criteria.get(key);
					CommonLogger.logDebug(log,
							"The key value pairs are : key :" + key
									+ ", value :" + value);
					if (z > 0) {
						sbr.append(" and ");
					}
					if (!(value instanceof String)) {
						sbr.append(key).append("=").append(value);
					} else {
						sbr.append(key).append("='").append(value.toString())
								.append("'");
					}
				}
			}
			CommonLogger.logDebug(log, "The query is " + sbr.toString());
			Query query = session.createQuery(sbr.toString());
			int rowsDeleted = query.executeUpdate();
			if (rowsDeleted == 1) {
				return true;
			} else {
				return false;
			}
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while deleting object", ex);
			throw convertHibernateAccessException(ex);
		} finally {
			if (isNewTxn && session != null) {
				session.close();
			}
		}
	}

	public void updateObject(Object obj) throws DataAccessException {
		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl:updateObject(obj)");
		List objects = new ArrayList();
		objects.add(obj);
		updateObjects(null, objects);
	}

	/**
	 * Adds the given object to a list and updates the object in the list with
	 * same session.
	 * 
	 * @param session
	 * @param obj
	 * @throws BSIException
	 */
	public void updateObject(Session session, Object obj) {
		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl:update(session,obj)");
		List objects = new ArrayList();
		objects.add(obj);
		updateObjects(session, objects);
	}

	public Object updateObjectRequested(Object objId, Class objClass,
			Method[] methods, List methodArgs) throws DataAccessException {
		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl:updateObjectRequested()"
						+ ",objId = " + ToString.toString(objId) + ",class="
						+ objClass + ",methods=" + methods + ",methodArgs="
						+ methodArgs);
		Session session = null;
		Transaction tx = null;
		Object obj = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			obj = session.load(objClass, (Serializable) objId);

			if (methods != null && methods.length > 0) {
				for (int x = 0; x < methods.length; x++) {
					Object[] args = null;

					try {
						if (methodArgs != null && methodArgs.size() > 0) {
							if (methodArgs.get(x) instanceof Object[]) {
								args = (Object[]) methodArgs.get(x);
								for (int z = 0; z < args.length; z++) {
									if (args[z] instanceof Collection) {
										Iterator iter = ((Collection) args[z])
												.iterator();
										while (iter.hasNext()) {
											// session.save(iter.next());
											session.update(iter.next());
										}
									}
								}
							} else {
								args = new Object[1];
								args[0] = methodArgs.get(x);
							}
						}
					} catch (Exception ex) {
						CommonLogger
								.logDebug(log,
										"Exception occured in  HbntDBHandler.updateObjectRequested():");
					}

					Hibernate.initialize(methods[x].invoke(obj, args));
				}
			}
			// session.save(obj);
			session.update(obj);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while updating object", ex);
			throw convertHibernateAccessException(ex);
		} catch (Exception ex) {
			if (tx != null) {
				tx.rollback();
			}
			throw convertHibernateAccessException(new HibernateException(ex));
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return obj;
	}

	public void updateObjects(List<Object> objects) throws DataAccessException {
		updateObjects(null, objects);
	}

	public void updateObjects(Session session, List objects)
			throws DataAccessException {
		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl:update(session,objs)"
						+ ",objects: " + objects);
		// Session session = null;
		Transaction tx = null;
		boolean isNewTxn = false;

		try {
			if (session == null) {
				session = getSession();
				isNewTxn = true;
				tx = session.beginTransaction();
			}

			tx = session.getTransaction();
			for (int x = 0; x < objects.size(); x++) {
				session.update(objects.get(x));
			}

			if (isNewTxn) {
				tx.commit();
			}
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			throw convertHibernateAccessException(new HibernateException(ex));
		} finally {
			if (isNewTxn && session != null) {
				session.close();
			}
		}
	}

	public void saveOrUpdate(List<Object> objs) throws DataAccessException {
		saveOrUpdate(null, objs);
	}

	public void saveOrUpdate(Session session, List objs)
			throws DataAccessException {
		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl:updateObjectRequested(): objs"
						+ objs);
		Transaction tx = null;
		boolean isNewTxn = false;
		try {
			if (session == null) {
				session = getSession();
				isNewTxn = true;
				tx = session.beginTransaction();
			}
			tx = session.getTransaction();
			for (int x = 0; x < objs.size(); x++) {
				session.saveOrUpdate(objs.get(x));
			}
			if (isNewTxn) {
				tx.commit();
			}
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger.logError(log,
					"Exception occured while saving/updating object", ex);
			throw convertHibernateAccessException(ex);
		} finally {
			if (isNewTxn && session != null) {
				session.close();
			}
		}
	}

	// //////////////////////////////////////////////////////////////////
	// Extra methods
	// //////////////////////////////////////////////////////////////////

	public int updateObjects(Session session, String hqlQuery, List paramValues)
			throws DataAccessException {

		CommonLogger.logDebug(log,
				"In HbntDBHandler:getObjects(hqlquery,paramValues)");
		// Session session = null;
		Transaction tx = null;
		int rowsUpdated = 0;
		StringBuffer sbr = new StringBuffer();

		boolean isNewTxn = false;

		try {
			if (session == null) {
				session = getSession();
				isNewTxn = true;
				tx = session.beginTransaction();
			}
			tx = session.getTransaction();

			Query query = session.createQuery(hqlQuery);
			if (paramValues != null && paramValues.size() > 0) {
				for (int i = 0; i < paramValues.size(); i++) {
					Object obj = paramValues.get(i);
					if (obj instanceof Long) {
						query.setLong(i, ((Long) obj).longValue());
					} else if (obj instanceof String) {
						query.setString(i, (String) obj);
					} else if (obj instanceof Integer) {
						query.setInteger(i, ((Integer) obj).intValue());
					} else if (obj instanceof Boolean) {
						query.setBoolean(i, ((Boolean) obj).booleanValue());
					} else if (obj instanceof Byte) {
						query.setByte(i, ((Byte) obj).byteValue());
					} else if (obj instanceof Short) {
						query.setShort(i, ((Short) obj).shortValue());
					} else if (obj instanceof Character) {
						query.setCharacter(i, ((Character) obj).charValue());
					} else if (obj instanceof java.util.Date) {
						query.setDate(i, (java.util.Date) obj);
					} else if (obj instanceof Double) {
						query.setDouble(i, ((Double) obj).doubleValue());
					} else if (obj instanceof Float) {
						query.setFloat(i, ((Float) obj).floatValue());
					}
				}
			}

			rowsUpdated = query.executeUpdate();

			if (isNewTxn) {
				tx.commit();
			}
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger
					.logDebug(log,
							" Exception occured in HbntDBHandler:update(hqlquery,paramValues) :");
			throw convertHibernateAccessException(ex);

		} finally {
			if (isNewTxn && session != null) {
				session.close();
			}
		}
		return rowsUpdated;
	}

	public List getObjectsFromQuery(Session session, String hqlQuery,
			List paramValues, Method[] methods) throws DataAccessException {

		CommonLogger.logDebug(log,
				"In HbntDBHandler:getObjects(hqlquery,paramValues)");
		// Session session = null;
		Transaction tx = null;
		List results = null;
		List updatedResults = new ArrayList();
		StringBuffer sbr = new StringBuffer();
		boolean isNewTxn = false;

		try {

			if (session == null) {
				session = getSession();
				isNewTxn = true;
				tx = session.beginTransaction();
			}
			tx = session.getTransaction();

			Query query = session.createQuery(hqlQuery);
			if (paramValues != null && paramValues.size() > 0) {
				for (int i = 0; i < paramValues.size(); i++) {
					Object obj = paramValues.get(i);
					if (obj instanceof Long) {
						query.setLong(i, ((Long) obj).longValue());
					} else if (obj instanceof String) {
						query.setString(i, (String) obj);
					} else if (obj instanceof Integer) {
						query.setInteger(i, ((Integer) obj).intValue());
					} else if (obj instanceof Boolean) {
						query.setBoolean(i, ((Boolean) obj).booleanValue());
					} else if (obj instanceof Byte) {
						query.setByte(i, ((Byte) obj).byteValue());
					} else if (obj instanceof Short) {
						query.setShort(i, ((Short) obj).shortValue());
					} else if (obj instanceof Character) {
						query.setCharacter(i, ((Character) obj).charValue());
					} else if (obj instanceof java.util.Date) {
						query.setDate(i, (java.util.Date) obj);
					} else if (obj instanceof Double) {
						query.setDouble(i, ((Double) obj).doubleValue());
					} else if (obj instanceof Float) {
						query.setFloat(i, ((Float) obj).floatValue());
					}
				}
			}

			results = query.list();

			for (int x = 0; x < results.size(); x++) {
				Object result = results.get(x);
				Object[] args = null;
				if (methods != null && methods.length > 0) {
					for (int y = 0; y < methods.length; y++) {
						Hibernate.initialize(methods[y].invoke(result, args));
						// updatedResults.add(result);
					}
				}
			}
			if (isNewTxn)
				tx.commit();
		} catch (Exception ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger
					.logDebug(log,
							" Exception occured in HbntDBHandler:getObjects(hqlquery,paramValues) :");
			throw convertHibernateAccessException(new HibernateException(ex));

		} finally {
			if (isNewTxn && session != null) {
				session.close();
			}
		}
		return results;
	}

	public List getObjectsFromQuery(String hqlQuery, List paramValues,
			Method[] methods) throws DataAccessException {
		return getObjectsFromQuery(null, hqlQuery, paramValues, methods);
	}

	public List getObjectsFromQuery(String hqlQuery, List paramValues)
			throws DataAccessException {

		CommonLogger.logDebug(log,
				"In DataAccessHibernateImpl::getObjects(hqlquery,paramValues)"
						+ ",hqlQuery=" + hqlQuery + ",paramValues="
						+ paramValues);
		Session session = null;
		Transaction tx = null;
		List results = null;
		StringBuffer sbr = new StringBuffer();

		try {
			session = getSession();
			tx = session.beginTransaction();

			Query query = session.createQuery(hqlQuery);
			if (paramValues != null && paramValues.size() > 0) {
				for (int i = 0; i < paramValues.size(); i++) {
					Object obj = paramValues.get(i);
					if (obj instanceof Long) {
						query.setLong(i, ((Long) obj).longValue());
					} else if (obj instanceof String) {
						query.setString(i, (String) obj);
					} else if (obj instanceof Integer) {
						query.setInteger(i, ((Integer) obj).intValue());
					} else if (obj instanceof Boolean) {
						query.setBoolean(i, ((Boolean) obj).booleanValue());
					} else if (obj instanceof Byte) {
						query.setByte(i, ((Byte) obj).byteValue());
					} else if (obj instanceof Short) {
						query.setShort(i, ((Short) obj).shortValue());
					} else if (obj instanceof Character) {
						query.setCharacter(i, ((Character) obj).charValue());
					} else if (obj instanceof java.util.Date) {
						query.setDate(i, (java.util.Date) obj);
					} else if (obj instanceof Double) {
						query.setDouble(i, ((Double) obj).doubleValue());
					} else if (obj instanceof Float) {
						query.setFloat(i, ((Float) obj).floatValue());
					}
				}
			}
			results = query.list();
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			CommonLogger
					.logDebug(log,
							" Exception occured in HbntDBHandler:getObjects(hqlquery,paramValues) :");
			throw convertHibernateAccessException(ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
}
