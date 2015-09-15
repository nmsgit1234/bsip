package com.bsi.client.managers;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.BuyerSrvsRegion;
import com.bsi.common.beans.Person;
import com.bsi.common.beans.SupplierServicePropertyValues;
import com.bsi.common.beans.SupplierSrvsRegion;
import com.nms.db.IDataAccess;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

public class PersonSrvsRegionManager {

	private static Log log = LogFactory.getLog(PersonSrvsRegionManager.class);

	// private static ResourceLocatorIntf resLoc = null;

	// private PersonSrvsRegionIntf ssrHandler = null;

	// private HbntDBHandler dbHandler = null;
	private static IDataAccess dbAccessor = null;

	public void setDbAccessor(IDataAccess dbAccessor) {
		this.dbAccessor = dbAccessor;
	}

	// static {
	// resLoc = ResourceFactory.getResourceLocator("local");
	// }

	/*
	 * 
	 * public Long createPersonSrvsRegion(Long personId,String personType,List
	 * serviceIds,Long svsRegionId) throws BSIException{
	 * 
	 * Long srvsRegnId = null;
	 * 
	 * CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:createPersonSrvsRegion()\n");
	 * CommonLogger.logDebug(log," personId=" + personId + "serviceIds are : " +
	 * serviceIds + ", personType=" + personType + ",svsRegionId=" +
	 * svsRegionId);
	 * 
	 * if (personType != null && personType.equalsIgnoreCase("S")){
	 * createSupplierSrvsRegion(personId,serviceIds,svsRegionId); } else if
	 * (personType != null && personType.equalsIgnoreCase("B")){ srvsRegnId =
	 * createBuyerSrvsRegion(personId,serviceIds,svsRegionId); }
	 * 
	 * return srvsRegnId; }
	 * 
	 * 
	 * 
	 * public void createSupplierSrvsRegion(Long supplierId,List serviceIds,Long
	 * svsRegionId) throws BSIException { CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:createSupplierSrvsRegion()\n");
	 * 
	 * try { dbHandler = new HbntDBHandler(); SupplierSrvsRegion ssr = null;
	 * List srvsRegions = new ArrayList();
	 * 
	 * for(int x=0;x<serviceIds.size();x++){
	 * 
	 * CommonLogger.logDebug(log,"IFor serviced id :" + serviceIds.get(x)); ssr
	 * = new SupplierSrvsRegion(); ssr.setSupplierId(supplierId); //Service
	 * service = (Service)session.load(Service.class,new
	 * Long((String)serviceIds.get(x)));
	 * ssr.setServiceId((Long)serviceIds.get(x));
	 * ssr.setRegion((Region)dbHandler
	 * .getObjectRequested(svsRegionId,Region.class,null));
	 * //ssr.setServiceRegionId(svsRegionId); srvsRegions.add(ssr); }
	 * 
	 * dbHandler.addObjects(srvsRegions); } catch(Exception ex){
	 * CommonLogger.logError(log,
	 * "5011: Exception occured in PersonSrvsRegionManager:createSupplierSrvsRegion() :"
	 * ,ex); throw new BSIException("1001","5011"); }
	 * 
	 * }
	 */

	/*
	 * public Long createBuyerSrvsRegion(Long buyerId,List serviceIds,Long
	 * svsRegionId) throws BSIException { CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:createBuyerSrvsRegion()\n"); Long bsrId =
	 * null;
	 * 
	 * try { dbHandler = new HbntDBHandler(); BuyerSrvsRegion bsr = null; List
	 * srvsRegions = new ArrayList();
	 * 
	 * //We assume that buyer subscribes to one service at a time.
	 * 
	 * /* for(int x=0;x<serviceIds.size();x++){
	 * 
	 * CommonLogger.logDebug(log,"IFor serviced id :" + serviceIds.get(x)); bsr
	 * = new BuyerSrvsRegion(); bsr.setBuyerId(buyerId); //Service service =
	 * (Service)session.load(Service.class,new Long((String)serviceIds.get(x)));
	 * bsr.setServiceId((Long)serviceIds.get(x));
	 * bsr.setServiceRegionId(svsRegionId); srvsRegions.add(bsr); }
	 */

	/*
	 * bsr = new BuyerSrvsRegion(); bsr.setBuyerId(buyerId);
	 * bsr.setServiceId((Long)serviceIds.get(0));
	 * bsr.setServiceRegionId(svsRegionId);
	 * 
	 * bsr = (BuyerSrvsRegion)dbHandler.addObject(bsr); bsrId = bsr.getBsrId();
	 * } catch(Exception ex){ CommonLogger.logError(log,
	 * "5011: Exception occured in PersonSrvsRegionManager:createBuyerSrvsRegion() :"
	 * ,ex); throw new BSIException("1001","5011"); } return bsrId;
	 * 
	 * }
	 */

	/*
	 * public SupplierSrvsRegion getSupplierSrvsRegion(Long supplierId,Long
	 * serviceId) throws BSIException{
	 * 
	 * CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:getSupplierSrvsRegion() \n supplierId=" +
	 * supplierId + " \n serviceId=" + serviceId); SupplierSrvsRegion ssr =
	 * null;
	 * 
	 * try { dbHandler = new HbntDBHandler(); String hqlQuery =
	 * "from SupplierSrvsRegion as ssr where ssr.supplierId = ? and ssr.serviceId=?"
	 * ; List params = new ArrayList(); params.add(supplierId);
	 * params.add(serviceId);
	 * 
	 * 
	 * List ssrs = dbHandler.getObjectsFromQuery(hqlQuery,params); if (ssrs !=
	 * null && ssrs.size() > 0){ ssr = (SupplierSrvsRegion)ssrs.get(0); } }
	 * catch(Exception ex) { CommonLogger.logError(log,
	 * "5013: Exception occured in PersonSrvsRegionHandler:getSupplierSrvsRegion() :"
	 * ,ex); throw new BSIException("1001","5013"); }
	 * 
	 * return ssr;
	 * 
	 * }
	 */

	public List getServiceMatchingBuyers(String serviceId, List srvsRegnList)
			throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:BuyerSrvsRegion() \n serviceId="
						+ serviceId);

		List bsrList = null;

		try {
			StringBuffer hqlQuery = new StringBuffer();
			hqlQuery.append("from BuyerSrvsRegion as bsr where bsr.serviceId=? and bsr.region.regionId in (");

			// SupplierSrvsRegion ssr = null;
			List paramsList = new ArrayList();

			for (int x = 0; x < srvsRegnList.size(); x++) {
				hqlQuery.append("?");
				if ((x + 1) < srvsRegnList.size())
					hqlQuery.append(",");
			}

			hqlQuery.append(")");

			CommonLogger.logDebug(log,
					"The hql query is " + hqlQuery.toString());

			srvsRegnList.add(0, new Long(serviceId));

			Method[] methods = new Method[1];
			methods[0] = BuyerSrvsRegion.class.getMethod("getBsrPropValues",
					null);

			// dbHandler = new HbntDBHandler();
			bsrList = dbAccessor.getObjectsFromQuery(hqlQuery.toString(),
					srvsRegnList, methods);
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5033: Exception occured in PersonSrvsRegionHandler:getServiceMatchingBuyers() :",
							ex);
			throw new BSIException("1001", "5033");
		}

		return bsrList;

	}

	/*
	 * public void deletePersonSrvsRegion(Long personId,String personType)
	 * throws BSIException {
	 * 
	 * String objectType = null; try { HashMap param = new HashMap();
	 * param.put("buyerId", personId);
	 * 
	 * if (personType.equals(BSIConstants.BUYER)) { objectType =
	 * "BuyerSrvsRegion"; } else if (personType.equals(BSIConstants.SUPPLIER)) {
	 * objectType = "SupplierSrvsRegion"; } dbHandler = new HbntDBHandler();
	 * dbHandler.deleteObjectByCondition(objectType, param);
	 * 
	 * } catch (BSIException ex1) { CommonLogger.logError(log,
	 * "Exception occured in PersonManager:deletePersonSrvsRegion();", ex1);
	 * throw ex1; }
	 * 
	 * }
	 */
	/*
	 * public List getServiceMatchingBuyers(String serviceId,List srvsRegnList)
	 * throws BSIException { CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:BuyerSrvsRegion() \n serviceId=" + serviceId
	 * );
	 * 
	 * List bsrList = null;
	 * 
	 * try { StringBuffer hqlQuery = new StringBuffer(); hqlQuery.append(
	 * "from BuyerSrvsRegion as bsr where bsr.serviceId=? and bsr.serviceRegionId in ("
	 * );
	 * 
	 * SupplierSrvsRegion ssr = null; List paramsList = new ArrayList();
	 * 
	 * for (int x=0;x<srvsRegnList.size();x++) { hqlQuery.append("?"); if ((x+1)
	 * < srvsRegnList.size()) hqlQuery.append(","); }
	 * 
	 * hqlQuery.append(")");
	 * 
	 * CommonLogger.logDebug(log,"The hql query is " + hqlQuery.toString());
	 * 
	 * srvsRegnList.add(0,new Long(serviceId));
	 * 
	 * Method[] methods = new Method[1]; methods[0] =
	 * BuyerSrvsRegion.class.getMethod("getBsrPropValues",null);
	 * 
	 * dbHandler = new HbntDBHandler(); bsrList =
	 * dbHandler.getObjectsFromQuery(hqlQuery.toString(),srvsRegnList,methods);
	 * } catch(Exception ex) { CommonLogger.logError(log,
	 * "5033: Exception occured in PersonSrvsRegionHandler:getServiceMatchingBuyers() :"
	 * ,ex); throw new BSIException("1001","5033"); }
	 * 
	 * return bsrList;
	 * 
	 * }
	 */

	/*
	 * public Set getServiceMatchingSuppliers(String serviceId,List
	 * srvsRegnList) throws BSIException { CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:getServiceMatchingSuppliers() \n serviceId="
	 * + serviceId + ", srvsRegnList :" + srvsRegnList); List ssrList = null;
	 * Set suppSett = new HashSet();
	 * 
	 * try {
	 * 
	 * StringBuffer hqlQuery = new StringBuffer(); hqlQuery.append(
	 * "from SupplierSrvsRegion as ssr where ssr.serviceId=? and ssr.region.regionId in ("
	 * );
	 * 
	 * List paramsList = new ArrayList();
	 * 
	 * for (int x=0;x<srvsRegnList.size();x++) { hqlQuery.append("?"); if ((x+1)
	 * < srvsRegnList.size()) hqlQuery.append(","); }
	 * 
	 * hqlQuery.append(")");
	 * 
	 * CommonLogger.logDebug(log,"The hql query is " + hqlQuery.toString());
	 * 
	 * srvsRegnList.add(0,new Long(serviceId));
	 * 
	 * //Method[] methods = new Method[1]; //methods[0] =
	 * BuyerSrvsRegion.class.getMethod("getBsrPropValues",null);
	 * 
	 * dbHandler = new HbntDBHandler(); ssrList =
	 * dbHandler.getObjectsFromQuery(hqlQuery.toString(),srvsRegnList,null);
	 * SupplierSrvsRegion ssr = null; List supplierIDs = new ArrayList();
	 * 
	 * for (int x=0;x<ssrList.size();x++) { ssr =
	 * (SupplierSrvsRegion)ssrList.get(x); supplierIDs.add(ssr.getSupplierId());
	 * }
	 * 
	 * if (supplierIDs.size() > 0) { suppSett =
	 * dbHandler.getObjectsRequested(null,supplierIDs,Person.class,null); }
	 * 
	 * } catch(Exception ex) { CommonLogger.logError(log,
	 * "5045: Exception occured in PersonSrvsRegionHandler:getServiceMatchingSuppliers() :"
	 * ,ex); throw new BSIException("1001","5045"); }
	 * 
	 * return suppSett;
	 * 
	 * }
	 */

	public Set getServiceMatchingPersons(String serviceId, List srvsRegnList,
			String personType) throws BSIException {
		return getServicePropertyMatchingPersons(serviceId,null,srvsRegnList,personType);
	}


	/**
	 * Gets the list of the person matching specific service region for the particular service. If the specific ssr id's are passed return the persons 
	 * only for those matching ssrIds.
	 * @param serviceId
	 * @param ssrIds
	 * @param srvsRegnList
	 * @param personType
	 * @return
	 * @throws BSIException
	 */
	public Set getServicePropertyMatchingPersons(String serviceId, List<BigInteger> ssrIds, List srvsRegnList,
			String personType) throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:getServicePropertyMatchingPersons() \n serviceId="
						+ serviceId + ", ssrIds : " + ssrIds + ",srvsRegnList :" + srvsRegnList
						+ ",personType=" + personType);
		List ssrList = null;
		Set personsSet = new HashSet();

		try {
			if (personType == null)
				return null;

			StringBuffer hqlQuery = new StringBuffer();

			if (personType.equalsIgnoreCase(BSIConstants.BUYER)) {
				hqlQuery.append("from BuyerSrvsRegion as bsr where bsr.serviceId=? and bsr.region.regionId in (");
			} else if (personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
				hqlQuery.append("from SupplierSrvsRegion as ssr where ssr.serviceId=? and ssr.region.regionId in (");
			}

			List paramsList = new ArrayList();

			for (int x = 0; x < srvsRegnList.size(); x++) {
				hqlQuery.append("?");
				if ((x + 1) < srvsRegnList.size())
					hqlQuery.append(",");
			}

			hqlQuery.append(")");
			
			//Add the ssrIds if present
			if (ssrIds != null && ssrIds.size() > 0){
			hqlQuery.append(" and ssr.id in (");
				for (int x = 0; x < ssrIds.size(); x++) {
					hqlQuery.append(ssrIds.get(x));
					if ((x + 1) < ssrIds.size()) {
						hqlQuery.append(",");
					} else {
						hqlQuery.append(")");
					}
				}

			}

			CommonLogger.logDebug(log,
					"The hql query is " + hqlQuery.toString());

			srvsRegnList.add(0, new Long(serviceId));

			// Method[] methods = new Method[1];
			// methods[0] =
			// BuyerSrvsRegion.class.getMethod("getBsrPropValues",null);

			// dbHandler = new HbntDBHandler();
			ssrList = dbAccessor.getObjectsFromQuery(hqlQuery.toString(),
					srvsRegnList, null);
			SupplierSrvsRegion ssr = null;
			BuyerSrvsRegion bsr = null;
			List personIds = new ArrayList();

			if (personType.equalsIgnoreCase(BSIConstants.BUYER)) {
				for (int x = 0; x < ssrList.size(); x++) {
					bsr = (BuyerSrvsRegion) ssrList.get(x);
					personIds.add(bsr.getBuyerId());
				}
			} else if (personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
				for (int x = 0; x < ssrList.size(); x++) {
					ssr = (SupplierSrvsRegion) ssrList.get(x);
					personIds.add(ssr.getSupplierId());
				}
			}

			if (personIds.size() > 0) {
				personsSet = dbAccessor.getObjectsRequested(null, personIds,
						Person.class, null);
			}

		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5045: Exception occured in PersonSrvsRegionHandler:getServicePropertyMatchingPersons() :",
							ex);
			throw new BSIException("1001", "5045");
		}

		return personsSet;

	}
	

	/*
	 * public List getSupplierSubSrvsRegions(Long supplierId,Long serviceId)
	 * throws BSIException{
	 * 
	 * CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:getSubscribedSupplierSrvsRegions() \n supplierId="
	 * + supplierId + " \n serviceId=" + serviceId); List ssrs = null;
	 * 
	 * try { dbHandler = new HbntDBHandler(); String hqlQuery =
	 * "from SupplierSrvsRegion as ssr where ssr.supplierId = ? and ssr.serviceId=?"
	 * ; List params = new ArrayList(); params.add(supplierId);
	 * params.add(serviceId); ssrs =
	 * dbHandler.getObjectsFromQuery(hqlQuery,params); } catch(Exception ex) {
	 * CommonLogger.logError(log,
	 * "5013: Exception occured in PersonSrvsRegionHandler:getSubscribedSupplierSrvsRegions() :"
	 * ,ex); throw new BSIException("1001","5013"); }
	 * 
	 * return ssrs; }
	 */

	public List getSupplierSubSrvsRegions(Long supplierId, Long serviceId)
			throws BSIException {

		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:getSubscribedSupplierSrvsRegions() \n supplierId="
						+ supplierId + " \n serviceId=" + serviceId);
		List ssrs = null;
		List regions = null;

		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from SupplierSrvsRegion as ssr where ssr.supplierId = ? and ssr.serviceId=?";
			List params = new ArrayList();
			params.add(supplierId);
			params.add(serviceId);
			Method[] methods = new Method[2];
			methods[0] = SupplierSrvsRegion.class.getMethod("getRegion", null);
			methods[1] = SupplierSrvsRegion.class.getMethod("getSsrPropValues",
					null);

			ssrs = dbAccessor.getObjectsFromQuery(hqlQuery, params, methods);
			if (ssrs == null || ssrs.size() == 0)
				return null;
			regions = new ArrayList();

			for (int x = 0; x < ssrs.size(); x++) {
				regions.add(((SupplierSrvsRegion) ssrs.get(x)).getRegion());
			}
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5013: Exception occured in PersonSrvsRegionHandler:getSubscribedSupplierSrvsRegions() :",
							ex);
			throw new BSIException("1001", "5013");
		}

		return regions;
	}

	
	public Set<SupplierServicePropertyValues> getSupplierSubSrvsRegionsPropertyValues(Long supplierId, Long serviceId)
			throws BSIException {

		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:getSubscribedSupplierSrvsRegions() \n supplierId="
						+ supplierId + " \n serviceId=" + serviceId);
		List ssrs = null;
		//List regions = null;
		Set<SupplierServicePropertyValues> suppPropValues = null;
		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from SupplierSrvsRegion as ssr where ssr.supplierId = ? and ssr.serviceId=?";
			List params = new ArrayList();
			params.add(supplierId);
			params.add(serviceId);
			Method[] methods = new Method[1];
			//methods[0] = SupplierSrvsRegion.class.getMethod("getRegion", null);
			methods[0] = SupplierSrvsRegion.class.getMethod("getSsrPropValues",
					null);

			ssrs = dbAccessor.getObjectsFromQuery(hqlQuery, params, methods);
			if (ssrs == null || ssrs.size() == 0)
				return null;
			for (int x = 0; x < ssrs.size(); x++) {
				suppPropValues = (((SupplierSrvsRegion) ssrs.get(x)).getSsrPropValues());
			}
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5013: Exception occured in PersonSrvsRegionHandler:getSubscribedSupplierSrvsRegions() :",
							ex);
			throw new BSIException("1001", "5013");
		}

		return suppPropValues;
	}
	
	
	/*
	 * public BuyerSrvsRegion getBuyerSrvsRegion(Long buyerId,Long serviceId)
	 * throws BSIException{
	 * 
	 * CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:getBuyerSrvsRegion() \n buyerId=" + buyerId +
	 * " \n serviceId=" + serviceId); CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:getPersonSrvsRegion()\n");
	 * CommonLogger.logDebug(log,"buyerId: " + buyerId + ",serviceId:" +
	 * serviceId);
	 * 
	 * BuyerSrvsRegion bsr = null;
	 * 
	 * try { //dbHandler = new HbntDBHandler(); String hqlQuery =
	 * "from BuyerSrvsRegion as bsr where bsr.buyerId = ? and bsr.serviceId=?";
	 * List params = new ArrayList(); params.add(buyerId);
	 * params.add(serviceId);
	 * 
	 * List bsrs = dbAccessor.getObjectsFromQuery(hqlQuery,params,null); if
	 * (bsrs != null && bsrs.size() > 0){ bsr = (BuyerSrvsRegion)bsrs.get(0); }
	 * } catch(Exception ex) { CommonLogger.logError(log,
	 * "5014: Exception occured in PersonSrvsRegionHandler:getBuyerSrvsRegion() :"
	 * ,ex); throw new BSIException("1001","5014"); } return bsr; }
	 */
	/*
	 * public List getBuyerSubSrvsRegions(Long buyerId,Long serviceId) throws
	 * BSIException{
	 * 
	 * CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:getBuyerSrvsRegion() \n buyerId=" + buyerId +
	 * " \n serviceId=" + serviceId); CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:getPersonSrvsRegion()\n");
	 * CommonLogger.logDebug(log,"buyerId: " + buyerId + ",serviceId:" +
	 * serviceId);
	 * 
	 * List bsrs = null;
	 * 
	 * try { dbHandler = new HbntDBHandler(); String hqlQuery =
	 * "from BuyerSrvsRegion as bsr where bsr.buyerId = ? and bsr.serviceId=?";
	 * List params = new ArrayList(); params.add(buyerId);
	 * params.add(serviceId);
	 * 
	 * bsrs = dbHandler.getObjectsFromQuery(hqlQuery,params); } catch(Exception
	 * ex) { CommonLogger.logError(log,
	 * "5014: Exception occured in PersonSrvsRegionHandler:getBuyerSrvsRegion() :"
	 * ,ex); throw new BSIException("1001","5014"); }
	 * 
	 * return bsrs; }
	 */

	public List getBuyerSubSrvsRegions(Long buyerId, Long serviceId)
			throws BSIException {

		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:getBuyerSrvsRegion() \n buyerId="
						+ buyerId + " \n serviceId=" + serviceId);
		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:getPersonSrvsRegion()\n");
		CommonLogger.logDebug(log, "buyerId: " + buyerId + ",serviceId:"
				+ serviceId);

		List bsrs = null;
		List regions = null;

		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from BuyerSrvsRegion as bsr where bsr.buyerId = ? and bsr.serviceId=?";
			List params = new ArrayList();
			params.add(buyerId);
			params.add(serviceId);

			Method[] methods = new Method[1];
			methods[0] = BuyerSrvsRegion.class.getMethod("getRegion", null);

			bsrs = dbAccessor.getObjectsFromQuery(hqlQuery, params, methods);
			if (bsrs == null || bsrs.size() == 0)
				return null;
			regions = new ArrayList();
			for (int x = 0; x < bsrs.size(); x++) {
				regions.add(((BuyerSrvsRegion) bsrs.get(x)).getRegion());
			}
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5014: Exception occured in PersonSrvsRegionHandler:getBuyerSrvsRegion() :",
							ex);
			throw new BSIException("1001", "5014");
		}

		return regions;
	}

	public List getAllSupplierSrvsRegion() throws BSIException {

		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:getAllSupplierSrvsRegion() ");

		List ssrs = null;
		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from SupplierSrvsRegion";
			ssrs = dbAccessor.getObjectsFromQuery(hqlQuery, null, null);

		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5015: Exception occured in PersonSrvsRegionHandler:getAllSupplierSrvsRegion() :",
							ex);
			throw new BSIException("1001", "5015");
		}
		return ssrs;

	}

	public List getAllBuyerSrvsRegion() throws BSIException {

		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:getAllBuyerSrvsRegion() ");

		List bsrs = null;
		try {
			// dbHandler = new HbntDBHandler();
			String hqlQuery = "from BuyerSrvsRegion";
			bsrs = dbAccessor.getObjectsFromQuery(hqlQuery, null, null);

		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5016: Exception occured in PersonSrvsRegionManager:getAllBuyerSrvsRegion() :",
							ex);
			throw new BSIException("1001", "5016");
		}
		return bsrs;

	}

	/*
	 * This returns a list containing the ServiceRegion objects.
	 */

	public List getSubscribedSrvsRegions(String personId, String srvsId,
			String personType) throws BSIException {

		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:getSubscribedSrvsRegions(): personId = "
						+ personId + ", srvsId= " + srvsId + ", personType="
						+ personType);

		List regnList = null;

		try {
			if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
				regnList = getSupplierSubSrvsRegions(new Long(personId),
						new Long(srvsId));
			} else if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.BUYER)) {
				regnList = getBuyerSubSrvsRegions(new Long(personId), new Long(
						srvsId));
			}

		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5031: Exception occured in PersonSrvsRegionManager:getSubscribedSrvsRegions() :",
							ex);
			throw new BSIException("1001", "5016");
		}
		return regnList;
	}


	public List getSubscribedSrvsPropertyValues(String personId, String srvsId,
			String personType) throws BSIException {

		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:getSubscribedSrvsPropertyValues(): personId = "
						+ personId + ", srvsId= " + srvsId + ", personType="
						+ personType);

		List regnList = null;

		try {
			if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
				regnList = getSupplierSubSrvsRegions(new Long(personId),
						new Long(srvsId));
			} else if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.BUYER)) {
				regnList = getBuyerSubSrvsRegions(new Long(personId), new Long(
						srvsId));
			}

		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5031: Exception occured in PersonSrvsRegionManager:getSubscribedSrvsRegions() :",
							ex);
			throw new BSIException("1001", "5016");
		}
		return regnList;
	}
	
	
	
	/**
	 * Get the list of all persons given a location.
	 * 
	 * @return
	 * @throws BSIException
	 */
	public List<Person> getLocationMatchingPersons(List srvsRegnList,
			String personType) throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonSrvsRegionManager:getLocationMatchingPersons()  srvsRegnList :"
						+ srvsRegnList + ",personType=" + personType);
		List<Person> personList = null;

		try {
			if (personType == null)
				return null;

			// If "All countries" selected then return all persons of the
			// specified type
			if (srvsRegnList != null && srvsRegnList.size() == 1) {
				Long regId = (Long) srvsRegnList.get(0);
				if (regId == 1) {
					PersonManager personMgr = new PersonManager();
					personList = personMgr.listPersons(personType);
					return personList;
				}
			}

			StringBuffer personIds = new StringBuffer();
			StringBuffer hqlQuery = new StringBuffer();

			if (personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
				List<SupplierSrvsRegion> ssrList = null;
				// Get the persons specific to the service region.
				hqlQuery.append("from SupplierSrvsRegion as ssr where ssr.region.regionId in (");
				for (int x = 0; x < srvsRegnList.size(); x++) {
					hqlQuery.append(srvsRegnList.get(x));
					if ((x + 1) < srvsRegnList.size()) {
						hqlQuery.append(",");
					} else {
						hqlQuery.append(")");
					}
				}
				ssrList = dbAccessor.getObjectsFromQuery(hqlQuery.toString(),
						null, null);
				for (SupplierSrvsRegion ssr : ssrList) {
					personIds.append(ssr.getSupplierId());
					personIds.append(",");
				}
				// Remove the last comma from personIds.
				if (personIds != null
						&& personIds.toString().trim().length() > 0) {
					personIds.deleteCharAt(personIds.lastIndexOf(","));
				}
			} else if (personType.equalsIgnoreCase(BSIConstants.BUYER)) {
				List<BuyerSrvsRegion> bsrList = null;
				hqlQuery.append("from BuyerSrvsRegion as bsr where bsr.region.regionId in (");
				for (int x = 0; x < srvsRegnList.size(); x++) {
					hqlQuery.append(srvsRegnList.get(x));
					if ((x + 1) < srvsRegnList.size()) {
						hqlQuery.append(",");
					} else {
						hqlQuery.append(")");
					}
				}
				bsrList = dbAccessor.getObjectsFromQuery(hqlQuery.toString(),
						null, null);
				for (BuyerSrvsRegion bsr : bsrList) {
					personIds.append(bsr.getBuyerId());
					personIds.append(",");
				}
				// Remove the last comma from personIds.
				if (personIds != null
						&& personIds.toString().trim().length() > 0) {
					personIds.deleteCharAt(personIds.lastIndexOf(","));
				}
			}

			hqlQuery = new StringBuffer();
			if (personIds != null && personIds.length() > 0) {
				hqlQuery.append(
						"from Person as person where person.personType='")
						.append(personType).append("'")
						.append(" and person.id in (");
				hqlQuery.append(personIds);
				hqlQuery.append(")");
				CommonLogger.logDebug(log,
						"The hql query is " + hqlQuery.toString());

				personList = dbAccessor.getObjectsFromQuery(hqlQuery.toString(),
						null, null);

			}
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5045: Exception occured in PersonSrvsRegionHandler:getLocationMatchingPersons() :",
							ex);
			throw new BSIException("1001", "5074");
		}

		return personList;
	}

	/*
	 * public List getSubscribedSrvsRegions(String personId,String srvsId,String
	 * personType) throws BSIException {
	 * 
	 * CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:getSubscribedSrvsRegions(): personId = " +
	 * personId + ", srvsId= " + srvsId + ", personType=" + personType);
	 * 
	 * List personSrvsRegnList = null;
	 * 
	 * List subSrvsRegionList = new ArrayList();
	 * 
	 * try { if (personType != null &&
	 * personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) { personSrvsRegnList
	 * = getSupplierSubSrvsRegions(new Long(personId),new Long(srvsId)); } else
	 * if (personType != null &&
	 * personType.equalsIgnoreCase(BSIConstants.BUYER)) { personSrvsRegnList =
	 * getBuyerSubSrvsRegions(new Long(personId),new Long(srvsId)); }
	 * 
	 * ServiceRegion svsRegn = null; Long svsRegnId = null; LocationManager
	 * locMgr = new LocationManager();
	 * 
	 * for (int x=0;x<personSrvsRegnList.size();x++) { if (personType != null &&
	 * personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) svsRegnId =
	 * ((SupplierSrvsRegion)personSrvsRegnList.get(x)).getServiceRegionId();
	 * else if (personType != null &&
	 * personType.equalsIgnoreCase(BSIConstants.BUYER)) svsRegnId =
	 * ((BuyerSrvsRegion)personSrvsRegnList.get(x)).getServiceRegionId();
	 * 
	 * CommonLogger.logDebug(log,"The service region id is " + svsRegnId); if
	 * (svsRegnId != null) svsRegn = locMgr.getServiceRegion(svsRegnId);
	 * subSrvsRegionList.add(svsRegn); } } catch(Exception ex) {
	 * CommonLogger.logError(log,
	 * "5031: Exception occured in PersonSrvsRegionManager:getSubscribedSrvsRegions() :"
	 * ,ex); throw new BSIException("1001","5016"); } return subSrvsRegionList;
	 * }
	 */

	/*
	 * public void createBuyerSrvsProperties(List bsrProps) throws BSIException{
	 * 
	 * CommonLogger.logDebug(log,
	 * "In PersonSrvsRegionManager:createBuyerSrvsProperty() ");
	 * 
	 * try { dbHandler = new HbntDBHandler(); dbHandler.addObjects(bsrProps);
	 * 
	 * } catch(Exception ex) { CommonLogger.logError(log,
	 * "5024: Exception occured in PersonSrvsRegionManager:createBuyerSrvsProperty() :"
	 * ,ex); throw new BSIException("1001","5024"); }
	 * 
	 * }
	 */

}
