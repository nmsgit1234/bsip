package com.nms.util.comm;

public interface IEmailManager {

	void sendEmail(String fromEmail, String toEmail, String subject,
			String bodyMessage) throws Exception;
}
