package com.nms.util.db;



public class BSIException extends Exception{

	public String errorCode = null;

	public BSIException(Throwable root){
		super(root);
	}

	public BSIException(String message){
		super(message);
		this.errorCode=message;
	}

	public BSIException(String message,Throwable root){
		super(message,root);
		this.errorCode=message;
	}

	public BSIException(String errorCode,String message){
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode(){
		return errorCode;
	}

}