/*
 * 
 * @author : William Alexander
 *
 */

package com.nms.util.comm.sms;

import com.nms.util.comm.ISMSManager;

public class SerialPortSMSHandler implements Runnable,ISMSManager {

	/**
	 * PLEASE NOTE
	 * For the SMS sending to work you need to do the following.
	 * 1. Copy the lib\win32comm.dll to the jre's lib folder For eg: jdk1.6.0_30\jre\bin
	 * 2. Copy the lib\comm.jar to jre's lib folder For eg: jdk1.6.0_30\jre\lib
	 */
	
	public final static int SYNCHRONOUS = 0;
	public final static int ASYNCHRONOUS = 1;
	private Thread myThread = null;

	private int mode = -1;
	private String recipient = null;
	private String message = null;

	public int status = -1;
	public long messageNo = -1;

	public SerialPortSMSHandler() {
		this.mode = SYNCHRONOUS;
	}
	public SerialPortSMSHandler(int mode) {
		this.mode = mode;
	}

	public String sendSMS(boolean isSynchronus,String recipient, String message) {
		if (isSynchronus) {
			this.mode=0;
		} else {
			this.mode=1;
		}
		
		this.recipient = recipient;
		this.message = message;
		// System.out.println("recipient: " + recipient + " message: " +
		// message);
		myThread = new Thread(this);
		myThread.start();
		// run();
		String satusMsg = null;
		//Return appropriate message of the error.
		if (status == 0) satusMsg="Success";
		if (status == -2) satusMsg="Time out";
		if (status == -1) satusMsg="Error occured";
		
		
		return satusMsg;
	}

	public void run() {
		Sender aSender = new Sender(recipient, message);
		try {
			// send message
			aSender.send();
			// System.out.println("sending ... ");
			// in SYNCHRONOUS mode wait for return : 0 for OK, -2 for timeout,
			// -1 for other errors
			if (mode == SYNCHRONOUS) {
				while (aSender.status == -1) {
					myThread.sleep(1000);
				}
			}
			if (aSender.status == 0)
				messageNo = aSender.messageNo;

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.status = aSender.status;
		aSender = null;
	}
	
	public static void main(String args[]){
		SerialPortSMSHandler sender = new SerialPortSMSHandler();
		sender.sendSMS(true, "9945415503", "Test SMS from BSI");
		
	}
}