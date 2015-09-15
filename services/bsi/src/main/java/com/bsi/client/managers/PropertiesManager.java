package com.bsi.client.managers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nms.db.IDataAccess;

public class PropertiesManager {
	private static Log log = LogFactory.getLog(PersonManager.class);

	private static IDataAccess dbAccessor = null;


	public static IDataAccess getDbAccessor() {
		return dbAccessor;
	}


	public static void setDbAccessor(IDataAccess dbAccessor) {
		PropertiesManager.dbAccessor = dbAccessor;
	}


	public List<Long> getBSRIDsForMatchingPropertyValues() {
		// TODO Auto-generated method stub
		return null;
	}

}

