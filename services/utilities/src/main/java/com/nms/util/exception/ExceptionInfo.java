package com.nms.util.exception;
/**
 * ExceptionInfo enforces that all the exceptions in the application should implement the
 * the methods defined below..

 */
public interface ExceptionInfo {
	
	public String getErrorCode();
	public String getErrorDetail();
	public String getErrorDesc();

}
