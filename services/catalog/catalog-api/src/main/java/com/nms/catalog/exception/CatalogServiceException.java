package com.nms.catalog.exception;
import com.nms.util.exception.ServiceException;

public class CatalogServiceException extends ServiceException{

	private static final long serialVersionUID = 1L;

	public CatalogServiceException(String errorCode, String errorDesc,
			String errorDetail, Throwable cause) {
		super(errorCode, errorDesc, errorDetail, cause);
	}

	public CatalogServiceException(String errorCode, String errorDesc,
			String errorDetail) {
		super(errorCode, errorDesc, errorDetail);
	}
}