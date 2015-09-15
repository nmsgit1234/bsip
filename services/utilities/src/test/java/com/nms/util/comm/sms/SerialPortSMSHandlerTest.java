package com.nms.util.comm.sms;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class SerialPortSMSHandlerTest {

	//TODO Uncomment this method
	@Ignore
	@Test
	public void testSendSMS() {
		SerialPortSMSHandler smsManager = new SerialPortSMSHandler();
		String satusMsg = smsManager.sendSMS(true, "9945415503", "Testing for SMS from programme");
		assertEquals("Success", satusMsg);
	}

}
