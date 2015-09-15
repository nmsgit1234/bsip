package com.bsi.client.managers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.BuyerSrvsRegion;
import com.bsi.common.beans.Person;
import com.bsi.common.beans.Region;
import com.bsi.common.beans.Service;
import com.nms.db.IDataAccess;
import com.nms.util.db.BSIException;
import com.nms.util.db.HibernateUtil;
import com.nms.util.log.CommonLogger;

public class BSITransactionMgr {

	private static Log log = LogFactory.getLog(PersonManager.class);
	//private HbntDBHandler dbHandler = null;
	private static IDataAccess dbAccessor = null;

	public void setDbAccessor(IDataAccess dbAccessor) {
		this.dbAccessor = dbAccessor;
	}

	public void createBuyer(Person personObj, List serviceIds, Long regionId,
			Map bsrProps) throws BSIException {

		CommonLogger.logDebug(log,
				"In BSITransactionMgr:createBuyer() : bsrProps" + bsrProps);
		Session session = null;
		Transaction tx = null;

		try {
			//dbHandler = new HbntDBHandler();

			// Create the Person
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			personObj = (Person) dbAccessor.createObject(session, personObj);
			Set serviceObjs = dbAccessor.getObjectsRequested(session, serviceIds, Service.class, null);
			personObj.setRequestedSevices(serviceObjs);

			// Create service region.
			// srvsRegn = (ServiceRegion)dbHandler.addObject(session,srvsRegn);

			// Create buyer service region
			BuyerSrvsRegion bsr = new BuyerSrvsRegion();

			bsr.setBuyerId(personObj.getId());
			bsr.setServiceId((Long) serviceIds.get(0));
			bsr.setRegion((Region) dbAccessor.getObjectRequested(regionId,
					Region.class, null));
			// bsr.setServiceRegionId(regionId);

			bsr = (BuyerSrvsRegion) dbAccessor.createObject(session, bsr);
			bsr.setBsrPropValues(bsrProps);

			// Create the service region
			tx.commit();
		} catch (ConstraintViolationException ex1) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"Constraint violation excepton occured in BSITransactionMgr:createBuyer() :",
							ex1);
			throw new BSIException("1005", ex1);

		} catch (HibernateException ex2) {
			//TODO remove references to the hibernate exception in middle layer.
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5026: Hibernate exception occured in BSITransactionMgr:createBuyer():",
							ex2);
			throw new BSIException("1001", "5026");
		} catch (Exception ex3) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5027: Exception occured in BSITransactionMgr:createBuyer():",
							ex3);
			throw new BSIException("1001", "5027");
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void subscribeService(Session session, String personId,
			String serviceId, List ssrList, String personType)
			throws BSIException {
		if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.BUYER)) {
			requestService(session, personId, serviceId, ssrList);
		} else if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
			offerService(session, personId, serviceId, ssrList);
		}
	}

	public void requestService(Session session, String personId,
			String serviceId, List ssrList) throws BSIException {

		CommonLogger.logDebug(log,
				"In BSITransactionMgr:requestService(), serviceId is "
						+ serviceId);
		// Session session = null;
		Transaction tx = null;
		boolean isNewTxn = false;

		try {
			//dbHandler = new HbntDBHandler();

			// Create the Person
			if (session == null) {
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				isNewTxn = true;
			} else {
				tx = session.getTransaction();
			}

			Method[] methods = new Method[1];
			methods[0] = Person.class.getMethod("getRequestedSevices", null);

			Person personObj = (Person) dbAccessor.getObjectRequested(session,
					new Long(personId), Person.class, methods);
			if (log.isDebugEnabled()) {
				Set oldSrvs = personObj.getRequestedSevices();
				Iterator iter = oldSrvs.iterator();
				while (iter.hasNext()) {
					CommonLogger.logDebug(log, "The old service id is "
							+ ((Service) iter.next()).getNodeId().toString());
				}
			}

			// Check if the service id exists.
			Set oldSrvs = personObj.getRequestedSevices();
			Iterator iter = oldSrvs.iterator();
			String exSeviceId = null;
			boolean isNew = true;
			while (iter.hasNext()) {
				exSeviceId = ((Service) iter.next()).getNodeId().toString();
				if (serviceId.equals(exSeviceId)) {
					isNew = false;
					break;
				}
			}

			CommonLogger.logDebug(log, "isNew is " + isNew);

			// Update the person object
			if (isNew) {
				Service serviceObj = (Service) dbAccessor.getObjectRequested(
						session, new Long(serviceId), Service.class, null);
				personObj.getRequestedSevices().add(serviceObj);
				dbAccessor.updateObject(session, personObj);
			}

			// Create SupplierServiceRegions
			dbAccessor.saveOrUpdate(session, ssrList);

			// Create the service region
			if (isNewTxn)
				tx.commit();
		} catch (HibernateException ex1) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5031: Hibernate exception occured in BSITransactionMgr:requestService():",
							ex1);
			throw new BSIException("1001", "5031");
		} catch (Exception ex2) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5032: Exception occured in BSITransactionMgr:requestService():",
							ex2);
			throw new BSIException("1001", "5032");
		} finally {
			if (isNewTxn && session != null)
				session.close();
		}
	}

	public void offerService(Session session, String personId,
			String serviceId, List ssrList) throws BSIException {

		CommonLogger.logDebug(log,
				"In BSITransactionMgr:offerService(), serviceId is "
						+ serviceId);
		Transaction tx = null;
		boolean isNewTxn = false;

		try {
			//dbHandler = new HbntDBHandler();

			// Create the Person
			if (session == null) {
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				isNewTxn = true;
			} else {
				tx = session.getTransaction();
			}

			Method[] methods = new Method[1];
			methods[0] = Person.class.getMethod("getOfferedServices", null);

			Person personObj = (Person) dbAccessor.getObjectRequested(session,
					new Long(personId), Person.class, methods);
			if (log.isDebugEnabled()) {
				Set oldSrvs = personObj.getOfferedServices();
				Iterator iter = oldSrvs.iterator();
				while (iter.hasNext()) {
					CommonLogger.logDebug(log, "The old service id is "
							+ ((Service) iter.next()).getNodeId().toString());
				}
			}

			// Check if the service id exists.
			Set oldSrvs = personObj.getOfferedServices();
			Iterator iter = oldSrvs.iterator();
			String exSeviceId = null;
			boolean isNewService = true;
			while (iter.hasNext()) {
				exSeviceId = ((Service) iter.next()).getNodeId().toString();
				if (serviceId.equals(exSeviceId)) {
					isNewService = false;
					break;
				}
			}

			CommonLogger.logDebug(log, "isNew is " + isNewService);

			// Update the person object
			if (isNewService) {
				Service serviceObj = (Service) dbAccessor.getObjectRequested(
						session, new Long(serviceId), Service.class, null);
				personObj.getOfferedServices().add(serviceObj);
				dbAccessor.updateObject(session, personObj);
			}

			// Create SupplierServiceRegions
			dbAccessor.saveOrUpdate(session, ssrList);

			// Create the service region
			if (isNewTxn)
				tx.commit();
		} catch (HibernateException ex1) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5055: Hibernate exception occured in BSITransactionMgr:offerService():",
							ex1);
			throw new BSIException("1001", "5055");
		} catch (Exception ex2) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5054: Exception occured in BSITransactionMgr:offerService():",
							ex2);
			throw new BSIException("1001", "5054");
		} finally {
			if (isNewTxn && session != null)
				session.close();
		}
	}

	/*
	public void administerService(Session session, String personId,
			String serviceId, List ssrList) throws BSIException {

		CommonLogger.logDebug(log,
				"In BSITransactionMgr:administerService(), serviceId is "
						+ serviceId);
		Transaction tx = null;
		boolean isNewTxn = false;

		try {
			dbHandler = new HbntDBHandler();

			// Create the Person
			if (session == null) {
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				isNewTxn = true;
			} else {
				tx = session.getTransaction();
			}

			Method[] methods = new Method[1];
			methods[0] = Person.class
					.getMethod("getAdministeredServices", null);

			Person personObj = (Person) dbHandler.getObjectRequested(session,
					new Long(personId), Person.class, methods);
			if (log.isDebugEnabled()) {
				Set oldSrvs = personObj.getAdministeredServices();
				Iterator iter = oldSrvs.iterator();
				while (iter.hasNext()) {
					CommonLogger.logDebug(log, "The old service id is "
							+ ((Service) iter.next()).getNodeId().toString());
				}
			}

			// Check if the service id exists.
			Set oldSrvs = personObj.getAdministeredServices();
			Iterator iter = oldSrvs.iterator();
			String exSeviceId = null;
			boolean isNewService = true;
			while (iter.hasNext()) {
				exSeviceId = ((Service) iter.next()).getNodeId().toString();
				if (serviceId.equals(exSeviceId)) {
					isNewService = false;
					break;
				}
			}

			CommonLogger.logDebug(log, "isNew is " + isNewService);

			// Update the person object
			if (isNewService) {
				Service serviceObj = (Service) dbHandler.getObjectRequested(
						session, new Long(serviceId), Service.class, null);
				personObj.getAdministeredServices().add(serviceObj);
				dbHandler.update(session, personObj);
			}

			// Create AdminServiceRegion
			dbHandler.saveOrUpdate(session, ssrList);

			// Create the service region
			if (isNewTxn)
				tx.commit();
		} catch (HibernateException ex1) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5064: Hibernate exception occured in BSITransactionMgr:administerService():",
							ex1);
			throw new BSIException("1001", "5064");
		} catch (Exception ex2) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5065: Exception occured in BSITransactionMgr:administerService():",
							ex2);
			throw new BSIException("1001", "5065");
		} finally {
			if (isNewTxn && session != null)
				session.close();
		}
	}
	
	*/

	/*
	 * public void subscribeService(Transaction tx,String personId,String
	 * serviceId,List ssrList,String personType) throws BSIException{
	 * 
	 * CommonLogger.logDebug(log,
	 * "In BSITransactionMgr:subscribeService(), serviceId is " + serviceId);
	 * Session session = null; //Transaction tx = null; boolean isNewTxn =
	 * false;
	 * 
	 * try { dbHandler = new HbntDBHandler();
	 * 
	 * //Create the Person if (tx == null) { session =
	 * HibernateUtil.getSession(); tx = session.beginTransaction();
	 * isNewTxn=true; }
	 * 
	 * Method[] methods = new Method[1]; methods[0] =
	 * Person.class.getMethod("getServices",null);
	 * 
	 * 
	 * Person personObj = (Person) dbHandler.getObjectRequested(new
	 * Long(personId),Person.class,methods); if (log.isDebugEnabled()) { Set
	 * oldSrvs = personObj.getServices(); Iterator iter = oldSrvs.iterator();
	 * while (iter.hasNext()) {
	 * CommonLogger.logDebug(log,"The old service id is " +
	 * ((Service)iter.next()).getNodeId().toString()); } }
	 * 
	 * //Check if the service id exists. Set oldSrvs = personObj.getServices();
	 * Iterator iter = oldSrvs.iterator(); String exSeviceId = null; boolean
	 * isNew = true; while (iter.hasNext()) { exSeviceId =
	 * ((Service)iter.next()).getNodeId().toString(); if
	 * (serviceId.equals(exSeviceId)) { isNew = false; break; } }
	 * 
	 * CommonLogger.logDebug(log,"isNew is " + isNew);
	 * 
	 * //Update the person object if (isNew) { Service serviceObj = (Service)
	 * dbHandler.getObjectRequested(new Long(serviceId),Service.class,null);
	 * personObj.getServices().add(serviceObj);
	 * dbHandler.update(session,personObj); }
	 * 
	 * //Create SupplierServiceRegions dbHandler.saveOrUpdate(session,ssrList);
	 * 
	 * //Create the service region if (isNewTxn) tx.commit(); }
	 * catch(HibernateException ex1) { if (tx!=null) tx.rollback();
	 * CommonLogger.logError(log,
	 * "5031: Hibernate exception occured in BSITransactionMgr:subscribeService():"
	 * ,ex1); throw new BSIException("1001","5031"); } catch(Exception ex2) { if
	 * (tx!=null) tx.rollback(); CommonLogger.logError(log,
	 * "5032: Exception occured in BSITransactionMgr:subscribeService():",ex2);
	 * throw new BSIException("1001","5032"); } finally { if (isNewTxn &&
	 * session != null) session.close(); } }
	 */

	/*
	public void subscribeService(String personId, String serviceId,
			List ssrList, String personType) throws BSIException {
		if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.BUYER)) {
			requestService(null, personId, serviceId, ssrList);
		} else if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
			offerService(null, personId, serviceId, ssrList);
		} else if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.REGION_ADMIN)) {
			administerService(null, personId, serviceId, ssrList);
		}
	}
*/
	public void unSubscribeService(Session session, String personId,
			String serviceId, String personType) throws BSIException {
		if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.BUYER)) {
			unSubscribeRequestedService(session, personId, serviceId);
		} else if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
			unSubscribeOfferedService(session, personId, serviceId);
		} else if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.REGION_ADMIN)) {
			unSubscribeAdministeredService(session, personId, serviceId);
		}
	}

	/*
	 * public void unSubscribeService(Transaction tx,String personId,String
	 * serviceId,String personType) throws BSIException{
	 * 
	 * CommonLogger.logDebug(log,
	 * "In BSITransactionMgr:unSubscribeService(), serviceId is " + serviceId);
	 * Session session = null; boolean isNewTxn = false; //Transaction tx =
	 * null;
	 * 
	 * try { dbHandler = new HbntDBHandler();
	 * 
	 * //Create the Person if (tx == null) { session =
	 * HibernateUtil.getSession(); tx = session.beginTransaction(); isNewTxn =
	 * true; }
	 * 
	 * Method[] methods = new Method[1]; methods[0] =
	 * Person.class.getMethod("getServices",null);
	 * 
	 * 
	 * Person personObj = (Person) dbHandler.getObjectRequested(session,new
	 * Long(personId),Person.class,methods);
	 * 
	 * 
	 * /* if (log.isDebugEnabled()) { Set oldSrvs = personObj.getServices();
	 * Iterator iter = oldSrvs.iterator(); while (iter.hasNext()) {
	 * CommonLogger.logDebug(log,"The old service id is " +
	 * ((Service)iter.next()).getNodeId().toString()); } }
	 */
	// Check if the service id exists.
	/*
	 * Set oldSrvs = personObj.getServices(); Iterator iter =
	 * oldSrvs.iterator(); String exSeviceId = null; boolean isOld=false; String
	 * hqlQuery = ""; List paramList = null; while (iter.hasNext()) { exSeviceId
	 * = ((Service)iter.next()).getNodeId().toString(); if
	 * (serviceId.equals(exSeviceId)) { Service serviceObj = (Service)
	 * dbHandler.getObjectRequested(session,new
	 * Long(serviceId),Service.class,null);
	 * personObj.getServices().remove(serviceObj);
	 * dbHandler.update(session,personObj); isOld = true; break; } }
	 * 
	 * 
	 * if (personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) { hqlQuery =
	 * "delete from SupplierSrvsRegion as ssreg where ssreg.supplierId=? and ssreg.serviceId=?"
	 * ; } else if (personType.equalsIgnoreCase(BSIConstants.BUYER)) { hqlQuery
	 * =
	 * "delete from BuyerSrvsRegion as bsreg where bsreg.buyerId=? and bsreg.serviceId=?"
	 * ; }
	 * 
	 * paramList = new ArrayList(); paramList.add(new Long(personId));
	 * paramList.add(new Long(serviceId));
	 * 
	 * //Delete from SupplierServiceRegion if (isOld) { int rowsUpdated =
	 * dbHandler.update(session,hqlQuery,paramList); } //Update Person object.
	 * //personObj.setServices(new HashSet());
	 * //dbHandler.update(session,personObj);
	 * 
	 * //Create the service region /* if (isNewTxn) {
	 * System.out.println("Commiting the transaction"); tx.commit(); } }
	 * catch(HibernateException ex1) { if (isNewTxn && tx!=null) tx.rollback();
	 * CommonLogger.logError(log,
	 * "5035: Hibernate exception occured in BSITransactionMgr:unSubscribeService():"
	 * ,ex1); throw new BSIException("1001","5035"); } catch(Exception ex2) { if
	 * (isNewTxn && tx!=null) tx.rollback(); CommonLogger.logError(log,
	 * "5036: Exception occured in BSITransactionMgr:unSubscribeService():"
	 * ,ex2); throw new BSIException("1001","5036"); } finally { if (isNewTxn &&
	 * session != null) session.close(); } }
	 */

	public void unSubscribeRequestedService(Session session, String personId,
			String serviceId) throws BSIException {

		CommonLogger.logDebug(log,
				"In BSITransactionMgr:unSubscribeRequestedService(), serviceId is "
						+ serviceId);
		// Session session = null;
		boolean isNewTxn = false;
		Transaction tx = null;

		try {
			//dbHandler = new HbntDBHandler();

			// Create the Person
			if (session == null) {
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				isNewTxn = true;
			} else {
				tx = session.getTransaction();
			}

			Method[] methods = new Method[1];
			methods[0] = Person.class.getMethod("getRequestedSevices", null);

			Person personObj = (Person) dbAccessor.getObjectRequested(session,
					new Long(personId), Person.class, methods);

			// Check if the service id exists.
			Set oldSrvs = personObj.getRequestedSevices();
			Iterator iter = oldSrvs.iterator();
			String exSeviceId = null;
			boolean isOld = false;
			String hqlQuery = "";
			List paramList = null;
			while (iter.hasNext()) {
				exSeviceId = ((Service) iter.next()).getNodeId().toString();
				if (serviceId.equals(exSeviceId)) {
					Service serviceObj = (Service) dbAccessor
							.getObjectRequested(session, new Long(serviceId),
									Service.class, null);
					personObj.getRequestedSevices().remove(serviceObj);
					dbAccessor.updateObject(session, personObj);
					isOld = true;
					break;
				}
			}

			//Select all service regions and delete
			hqlQuery = "from BuyerSrvsRegion as bsreg where bsreg.buyerId=? and bsreg.serviceId=?";
			paramList = new ArrayList();
			paramList.add(new Long(personId));
			paramList.add(new Long(serviceId));

			List bsrObjs = dbAccessor.getObjectsFromQuery(session,hqlQuery,paramList,null);
			if (bsrObjs != null && bsrObjs.size() > 0){
				dbAccessor.deleteObjects(session, bsrObjs);
			}
			
			//
/*			hqlQuery = "delete from BuyerSrvsRegion as bsreg where bsreg.buyerId=? and bsreg.serviceId=?";

			paramList = new ArrayList();
			paramList.add(new Long(personId));
			paramList.add(new Long(serviceId));

			// Delete from SupplierServiceRegion
			if (isOld) {
				int rowsUpdated = dbAccessor
						.updateObjects(session, hqlQuery, paramList);
			}
			// Update Person object.
			// personObj.setServices(new HashSet());
			// dbHandler.update(session,personObj);

			// Create the service region
*/			if (isNewTxn)
				tx.commit();
		} catch (HibernateException ex1) {
			if (isNewTxn && tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5056: Hibernate exception occured in BSITransactionMgr:unSubscribeRequestedService():",
							ex1);
			throw new BSIException("1001", "5056");
		} catch (Exception ex2) {
			if (isNewTxn && tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5057: Exception occured in BSITransactionMgr:unSubscribeRequestedService():",
							ex2);
			throw new BSIException("1001", "5057");
		} finally {
			if (isNewTxn && session != null)
				session.close();
		}
	}

	public void unSubscribeOfferedService(Session session, String personId,
			String serviceId) throws BSIException {

		CommonLogger.logDebug(log,
				"In BSITransactionMgr:unSubscribeOfferedService(), serviceId is "
						+ serviceId);
		// Session session = null;
		boolean isNewTxn = false;
		Transaction tx = null;

		try {
			//dbHandler = new HbntDBHandler();

			// Create the Person
			if (session == null) {
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				isNewTxn = true;
			} else {
				tx = session.getTransaction();
			}

			Method[] methods = new Method[1];
			methods[0] = Person.class.getMethod("getOfferedServices", null);

			Person personObj = (Person) dbAccessor.getObjectRequested(session,
					new Long(personId), Person.class, methods);

			// Check if the service id exists.
			Set oldSrvs = personObj.getOfferedServices();
			Iterator iter = oldSrvs.iterator();
			String exSeviceId = null;
			boolean isOld = false;
			String hqlQuery = "";
			List paramList = null;
			while (iter.hasNext()) {
				exSeviceId = ((Service) iter.next()).getNodeId().toString();
				if (serviceId.equals(exSeviceId)) {
					Service serviceObj = (Service) dbAccessor
							.getObjectRequested(session, new Long(serviceId),
									Service.class, null);
					personObj.getOfferedServices().remove(serviceObj);
					dbAccessor.updateObject(session, personObj);
					isOld = true;
					break;
				}
			}

			//Select all service regions and delete
			hqlQuery = "from SupplierSrvsRegion as ssreg where ssreg.supplierId=? and ssreg.serviceId=?";
			paramList = new ArrayList();
			paramList.add(new Long(personId));
			paramList.add(new Long(serviceId));

			List ssrObjs = dbAccessor.getObjectsFromQuery(session,hqlQuery,paramList,null);
			if (ssrObjs != null && ssrObjs.size() > 0){
				dbAccessor.deleteObjects(session, ssrObjs);
			}
			session.flush();
/*			hqlQuery = "delete from SupplierSrvsRegion as ssreg where ssreg.supplierId=? and ssreg.serviceId=?";

			paramList = new ArrayList();
			paramList.add(new Long(personId));
			paramList.add(new Long(serviceId));

			// Delete from SupplierServiceRegion
			if (isOld) {
				int rowsUpdated = dbAccessor
						.updateObjects(session, hqlQuery, paramList);
			}
*/			// Update Person object.
			// personObj.setServices(new HashSet());
			// dbHandler.update(session,personObj);

			// Create the service region
			if (isNewTxn)
				tx.commit();
		} catch (HibernateException ex1) {
			if (isNewTxn && tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5058: Hibernate exception occured in BSITransactionMgr:unSubscribeOfferedService():",
							ex1);
			throw new BSIException("1001", "5058");
		} catch (Exception ex2) {
			if (isNewTxn && tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5059: Exception occured in BSITransactionMgr:unSubscribeOfferedService():",
							ex2);
			throw new BSIException("1001", "5059");
		} finally {
			if (isNewTxn && session != null)
				session.close();
		}
	}

	public void unSubscribeAdministeredService(Session session,
			String personId, String serviceId) throws BSIException {

		CommonLogger.logDebug(log,
				"In BSITransactionMgr:unSubscribeAdministeredService(), serviceId is "
						+ serviceId);
		// Session session = null;
		boolean isNewTxn = false;
		Transaction tx = null;

		try {
			//dbHandler = new HbntDBHandler();

			// Create the Person
			if (session == null) {
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				isNewTxn = true;
			} else {
				tx = session.getTransaction();
			}

			Method[] methods = new Method[1];
			methods[0] = Person.class
					.getMethod("getAdministeredServices", null);

			Person personObj = (Person) dbAccessor.getObjectRequested(session,
					new Long(personId), Person.class, methods);

			// Check if the service id exists.
			Set oldSrvs = personObj.getOfferedServices();
			Iterator iter = oldSrvs.iterator();
			String exSeviceId = null;
			boolean isOld = false;
			String hqlQuery = "";
			List paramList = null;
			while (iter.hasNext()) {
				exSeviceId = ((Service) iter.next()).getNodeId().toString();
				if (serviceId.equals(exSeviceId)) {
					Service serviceObj = (Service) dbAccessor
							.getObjectRequested(session, new Long(serviceId),
									Service.class, null);
					personObj.getAdministeredServices().remove(serviceObj);
					dbAccessor.updateObject(session, personObj);
					isOld = true;
					break;
				}
			}

			hqlQuery = "delete from AdminServiceRegion as ssreg where ssreg.adminId=? and ssreg.serviceId=?";

			paramList = new ArrayList();
			paramList.add(new Long(personId));
			paramList.add(new Long(serviceId));

			// Delete from SupplierServiceRegion
			if (isOld) {
				int rowsUpdated = dbAccessor
						.updateObjects(session, hqlQuery, paramList);
			}
			// Update Person object.
			// personObj.setServices(new HashSet());
			// dbHandler.update(session,personObj);

			// Create the service region
			if (isNewTxn)
				tx.commit();
		} catch (HibernateException ex1) {
			if (isNewTxn && tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5066: Hibernate exception occured in BSITransactionMgr:unSubscribeAdministeredService():",
							ex1);
			throw new BSIException("1001", "5066");
		} catch (Exception ex2) {
			if (isNewTxn && tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5067: Exception occured in BSITransactionMgr:unSubscribeAdministeredService():",
							ex2);
			throw new BSIException("1001", "5067");
		} finally {
			if (isNewTxn && session != null)
				session.close();
		}
	}

	public void unSubscribeService(String personId, String serviceId,
			String personType) throws BSIException {
		if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.BUYER)) {
			unSubscribeRequestedService(null, personId, serviceId);
		} else if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
			unSubscribeOfferedService(null, personId, serviceId);
		} else if (personType != null
				&& personType.equalsIgnoreCase(BSIConstants.REGION_ADMIN)) {
			unSubscribeAdministeredService(null, personId, serviceId);
		}

	}

	public void updateSubscribedServices(String personId, String serviceId,
			List ssrList, String personType) throws BSIException {

		CommonLogger.logDebug(log,
				"In BSITransactionMgr:updateSubscribedServices(), serviceId is "
						+ serviceId);
		Session session = null;
		Transaction tx = null;

		try {
			//dbHandler = new HbntDBHandler();

			// Create the Person
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			unSubscribeService(session, personId, serviceId, personType);
			subscribeService(session, personId, serviceId, ssrList, personType);

			// Create the service region
			tx.commit();
		} catch (HibernateException ex1) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5041: Hibernate exception occured in BSITransactionMgr:updateSubscribedServices():",
							ex1);
			throw new BSIException("1001", "5041");
		} catch (Exception ex2) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5041: Exception occured in BSITransactionMgr:updateSubscribedServices():",
							ex2);
			throw new BSIException("1001", "5041");
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void deletePerson(Long personId) throws BSIException {

		CommonLogger.logDebug(log,
				"In BSITransactionMgr:deletePerson(), personId is " + personId);
		Session session = null;
		Transaction tx = null;
		String hqlQuery = null;
		List paramList = null;

		try {
			if (personId == null)
				return;

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			//dbHandler = new HbntDBHandler();
			Person personObj = (Person) dbAccessor.getObjectRequested(session,
					personId, Person.class, null);
			
			//
			//Select all service regions and delete
			if (personObj != null && personObj.getPersonType().equalsIgnoreCase(BSIConstants.SUPPLIER)){
				hqlQuery = "from SupplierSrvsRegion as ssreg where ssreg.supplierId=?";
				paramList = new ArrayList();
				paramList.add(new Long(personId));

				List ssrObjs = dbAccessor.getObjectsFromQuery(session,hqlQuery,paramList,null);
				if (ssrObjs != null && ssrObjs.size() > 0){
					dbAccessor.deleteObjects(session, ssrObjs);
				}
			} else if (personObj != null && personObj.getPersonType().equalsIgnoreCase(BSIConstants.BUYER)){
				//Select all service regions and delete
				hqlQuery = "from BuyerSrvsRegion as bsreg where bsreg.buyerId=?";
				paramList = new ArrayList();
				paramList.add(new Long(personId));

				List bsrObjs = dbAccessor.getObjectsFromQuery(session,hqlQuery,paramList,null);
				if (bsrObjs != null && bsrObjs.size() > 0){
					dbAccessor.deleteObjects(session, bsrObjs);
				}
			}
			//

/*			// Delete the supplier service region.
			hqlQuery = "delete from SupplierSrvsRegion as ssreg where ssreg.supplierId=?";
			paramList = new ArrayList();
			paramList.add(personId);
			int rowsUpdated = dbAccessor.updateObjects(session, hqlQuery, paramList);
*/
			//
/*			hqlQuery = "delete from AdminServiceRegion as asreg where asreg.adminId=?";
			paramList = new ArrayList();
			paramList.add(personId);
			rowsUpdated = dbAccessor.updateObjects(session, hqlQuery, paramList);
*/
			//
/*			hqlQuery = "from BuyerSrvsRegion as bsreg where bsreg.buyerId=?";
			paramList = new ArrayList();
			paramList.add(personId);

			List bsr = dbAccessor.getObjectsFromQuery(session, hqlQuery,
					paramList, null);
			dbAccessor.deleteObjects(session, bsr);
*/
			// Delete the buyer service region.
			/*
			 * hqlQuery =
			 * "delete from BuyerSrvsRegion as bsreg where bsreg.buyerId=?";
			 * paramList = new ArrayList(); paramList.add(personId); rowsUpdated
			 * = dbHandler.update(session, hqlQuery, paramList);
			 */
			// Delete person

			List personList = new ArrayList();
			personList.add(personObj);
			dbAccessor.deleteObjects(session, personList);
			tx.commit();
		} catch (HibernateException ex1) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5041: Hibernate exception occured in BSITransactionMgr:updateSubscribedServices():",
							ex1);
			throw new BSIException("1001", "5041");
		} catch (Exception ex2) {
			if (tx != null)
				tx.rollback();
			CommonLogger
					.logError(
							log,
							"5041: Exception occured in BSITransactionMgr:updateSubscribedServices():",
							ex2);
			throw new BSIException("1001", "5041");
		} finally {
			if (session != null)
				session.close();
		}
	}
}
