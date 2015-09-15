package com.nms.util.comm;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nms.util.log.CommonLogger;

/**
 * This is an email utility class for sending emails.
 * 
 * @author Nasiruddin
 * 
 */

public class EmailClient implements IEmailManager{
	private static Log log = LogFactory.getLog(EmailClient.class);

	private static String smtpHost;
	private static String smtpPort;
	private static String fromEmail;
	private static String smtpUserName;
	private static String smtpPassword;
	private Session session = null;

	static {
		try {
			FileHandler fileHandler = new FileHandler();
			Properties properties = fileHandler
					.getProperties("AppEmailSettings.properties");
			smtpHost = properties.getProperty("SMTP.HOST");
			smtpPort = properties.getProperty("SMTP.PORT");
			fromEmail = properties.getProperty("FROM.EMAIL.ADDRESS");
			smtpUserName = properties.getProperty("SMTP.USERNAME");
			smtpPassword = properties.getProperty("SMTP.PASSWORD");

		} catch (Exception ex) {
			CommonLogger.logError(log,
					"Couldnot intitiazlie, email properties file not found ",
					ex);
		}
	}

	public void sendEmail(String fromEmail, String toEmail, String subject,
			String bodyMessage) throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		if (fromEmail != null) {
			props.put("mail.from", fromEmail);
		} else {
			props.put("mail.from", this.fromEmail);
		}
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		try {
			session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(smtpUserName,
							smtpPassword);
				}
			});

			MimeMessage msg = prepareMessage(fromEmail, toEmail, subject,
					bodyMessage);

			// MimeMessage msg = new MimeMessage(session);
			msg.setFrom();
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(bodyMessage);
			msg.setRecipients(Message.RecipientType.TO, toEmail);

			Transport.send(msg);

			Transport.send(msg);
		} catch (MessagingException mex) {
			CommonLogger.logError(log, "send failed, exception: ", mex);
			throw new Exception("Unable to send email");
		}
	}

	private MimeMessage prepareMessage(String fromEmail, String toEmail,
			String subject, String bodyMessage) throws MessagingException {
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom();
		msg.setRecipients(Message.RecipientType.TO, toEmail);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setText(bodyMessage);
		return msg;
	}

}
