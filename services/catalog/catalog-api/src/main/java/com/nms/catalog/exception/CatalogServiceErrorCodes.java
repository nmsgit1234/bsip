package com.nms.catalog.exception;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CatalogServiceErrorCodes {

	DB_ERROR_FIRST_LEVEL_SERVICES ("catalog-10000", "Exception occured while querying the database to get first level services"),
	DB_ERROR_CHILD_SERVICES ("catalog-10001", "Exception occured while querying the database to get child services"),
	DB_ERROR_UPDATE_PROPERTIES ("catalog-10002", "Exception occured while querying the database to update the service properties"), 
	SERVICE_ID_NULL("catalog-10003", "Service id cannot be null"),
	DB_ERROR_GET_SERVICE("catalog-10004", "Execption occured while getting the service details from the database"), 
	DB_ERROR_GET_TOKEN("catalog-10005", "Execption occured while getting the token details from the database"),
	DB_ERROR_GET_SERVICE_BY_NAME("catalog-10006", "Execption occured while getting the service by name from the database");
	
	
	private static final Map<String, CatalogServiceErrorCodes> ServiceErrorCodeMap = new HashMap<String, CatalogServiceErrorCodes>();

	static {
		for (CatalogServiceErrorCodes serviceErrorCode : EnumSet
				.allOf(CatalogServiceErrorCodes.class))
			ServiceErrorCodeMap.put(serviceErrorCode
					.getErrorCode(), serviceErrorCode);
	}

	private String errorCode;
	private String errorDesc;

	private CatalogServiceErrorCodes(String errorCode, String errorDesc) {
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;

	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public static CatalogServiceErrorCodes get(String errorCode) {
		return ServiceErrorCodeMap.get(errorCode);
	}
}

