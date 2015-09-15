package com.bsi.client.managers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsi.common.beans.Region;
import com.nms.db.IDataAccess;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

/*
 import java.util.List;
 import java.util.ArrayList;
 import java.util.Set;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.HashMap;
 import java.util.Map;

 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;

 import com.bsi.common.beans.*;
 import com.bsi.client.util.RestrictionConstants;
 //import com.bsi.client.intf.ResourceLocatorIntf;
 //import com.bsi.client.intf.LocatorIntf;
 import com.bsi.reuse.log.CommonLogger;
 import com.bsi.reuse.db.BSIException;
 import com.bsi.reuse.db.HbntDBHandler;
 import java.lang.reflect.Method;
 import java.util.TreeSet;
 import com.bsi.common.beans.Region;
 import java.lang.reflect.Method;
 */

public class RegionManager {

	private static Log log = LogFactory.getLog(RegionManager.class);

	//private HbntDBHandler dbHandler = null;

	private static IDataAccess dbAccessor = null;

	public void setDbAccessor(IDataAccess dbAccessor) {
		this.dbAccessor = dbAccessor;
	}


	public List getRootRegions() throws BSIException {
		return getChildRegions("0");
	}

	public List getChildRegions(String regionId) throws BSIException {
		CommonLogger.logDebug(log, "In RegionManager:getChildRegions()");
		List childRegions = null;
		String hqlQuery = null;
		List paramList = null;

		try {
			//dbHandler = new HbntDBHandler();

			hqlQuery = " from Region as region where region.parentRegion.regionId=?";
			paramList = new ArrayList();
			paramList.add(new Long(regionId));
			childRegions = dbAccessor.getObjectsFromQuery(hqlQuery, paramList,null);
			if (log.isDebugEnabled()) {
				for (int x = 0; x < childRegions.size(); x++) {
					CommonLogger.logDebug(log, "The country name is "
							+ ((Region) childRegions.get(x)).getName());
				}
			}
		} catch (Exception ex) {
			CommonLogger
					.logError(
							log,
							"5052 : Exception occured in RegionManager:getRootRegions():",
							ex);
			throw new BSIException("1001", "5052");
		}
		return childRegions;
	}

	public Region getRegion(Long regionId) throws BSIException {
		Region region = null;

		try {
			//dbHandler = new HbntDBHandler();
			region = (Region) dbAccessor.getObjectRequested(regionId,
					Region.class, null);
		} catch (Exception ex) {
			CommonLogger.logError(log,
					"5053 : Exception occured in RegionManager:getRegion():",
					ex);
			throw new BSIException("1001", "5053");
		}
		return region;

	}

	public Region getParentRegion(Long regionId) throws BSIException {
		Region region = null;

		try {
			//dbHandler = new HbntDBHandler();
			Method[] methods = new Method[1];
			methods[0] = Region.class.getMethod("getParentRegion", null);

			region = (Region) dbAccessor.getObjectRequested(regionId,
					Region.class, methods);
			region = region.getParentRegion();
		} catch (Exception ex) {
			CommonLogger.logError(log,
					"5053 : Exception occured in RegionManager:getRegion():",
					ex);
			throw new BSIException("1001", "5053");
		}
		return region;

	}
	
	//TODO performance intensive query need to improve
	public List<Long> getRecursiveChildRegions(Long regionId) throws BSIException{
		//Region region = null;
		List<Region> parentRegions = new CopyOnWriteArrayList<Region>();
		List<Long> childRegionIds = new ArrayList<Long>();
		
		try{
			parentRegions = getChildRegions(regionId.toString());
			childRegionIds.add(regionId);
			for (int x=0;x<parentRegions.size();x++){
				Region region = parentRegions.get(x);
				childRegionIds.add(region.getRegionId());
				if (parentRegions.get(x).hasChildren()){
					parentRegions.addAll(getChildRegions(region.getRegionId().toString()));
				} 
			}
		} catch (Exception ex) {
			CommonLogger.logError(log,
					"5053 : Exception occured in RegionManager:getRecursiveChildRegions():",
					ex);
			throw new BSIException("1001", "5053");
		}
		return childRegionIds;
	}

	public static void main(String args[]) {

		try {
			RegionManager svsMgr = new RegionManager();
			svsMgr.getChildRegions("1");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
