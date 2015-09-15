package com.nms.util.exception;

public class ServiceException extends RuntimeException implements ExceptionInfo {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorDetail;

	public ServiceException(String errorCode, String errorDesc,
			String errorDetail) {
		super(errorDesc);
		this.errorCode = errorCode;
		this.errorDetail = errorDetail;
	}

	public ServiceException(String errorCode, String errorDesc,
			String errorDetail, Throwable cause) {
		super(errorDesc, cause);
		this.errorCode = errorCode;
		this.errorDetail = errorDetail;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorDesc() {
		return super.getMessage();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("errorCode:");
		sb.append(getErrorCode());
		sb.append("|");
		sb.append("errorDescription:");
		sb.append(getErrorDesc());
		sb.append("|");
		sb.append("errorDetail:");
		sb.append(getErrorDetail());
		return sb.toString();
	}

}