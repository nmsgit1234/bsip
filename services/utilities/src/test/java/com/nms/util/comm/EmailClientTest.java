package com.nms.util.comm;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

public class EmailClientTest {
	
	//TODO uncomment this method
	@Ignore
	@Test
	/**
	 * Test method for testing sending email.
	 */
	public void testSendEmail(){
		EmailClient email = new EmailClient();
		try{
			email.sendEmail(null, "bsi.admiin@gmail.com", "Junit test for email", "Email message body");
		} catch (Exception ex){
			fail("Coudn't send email");
		}
	}

}
