package com.nms.util.comm;

public interface ISMSManager {

	String sendSMS(boolean isSynchornus,String mobileNumber,String message);
}
