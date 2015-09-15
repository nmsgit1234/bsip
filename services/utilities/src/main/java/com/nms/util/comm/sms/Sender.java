/*
 * 
 * @author : William Alexander
 *
 */

package com.nms.util.comm.sms;

import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nms.util.comm.FileHandler;
import com.nms.util.log.CommonLogger;

public class Sender implements Runnable {

	private static final long STANDARD = 500;
	private static final long LONG = 2000;
	private static final long VERYLONG = 20000;
	private static String portName = null;
	private static int baudRate = 9600;
	private static int flowControl = 0;
	private static int flowControlOut = 0;
	private static int databits = 8;
	private static int stopbits = 1;
	private static int parity = 0;
	private static String csca=null;

	// Read from properties file.

	private static Log log = LogFactory.getLog(Sender.class);

	static {
		try {
			FileHandler fileHandler = new FileHandler();
			Properties properties = fileHandler
					.getProperties("SMSSettings.properties");
			portName = properties.getProperty("portName");
			csca =  properties.getProperty("csca");
			baudRate = new Integer(properties.getProperty("baudRate")).intValue();
			flowControl = new Integer(properties.getProperty("flowControl")).intValue();
			flowControlOut = new Integer(properties.getProperty("flowControlOut")).intValue();
			databits = new Integer(properties.getProperty("databits")).intValue();
			stopbits = new Integer(properties.getProperty("stopbits")).intValue();
			parity = new Integer(properties.getProperty("parity")).intValue();
		} catch (Exception ex) {
			CommonLogger.logError(log,
					"Couldnot intitiazlie, SMSSettings.properties file not found ",
					ex);
		}
	}

	SerialConnection mySerial = null;

	static final private char cntrlZ = (char) 26;
	String in, out;
	Thread aThread = null;
	private long delay = STANDARD;
	String recipient = null;
	String message = null;
	private SerialParameters defaultParameters = new SerialParameters(portName,
			baudRate, flowControl,flowControlOut, databits, stopbits,parity);
	public int step;
	public int status = -1;
	public long messageNo = -1;

	public Sender(String recipient, String message) {

		this.recipient = recipient;
		this.message = message;

	}

	/**
	 * connect to the port and start the dialogue thread
	 */
	public int send() throws Exception {

		SerialParameters params = defaultParameters;

		mySerial = new SerialConnection(params);

		mySerial.openConnection();

		aThread = new Thread(this);

		aThread.start();
		// log("start");

		return 0;
	}

	/**
	 * implement the dialogue thread, message / response via steps, handle time
	 * out
	 */

	public void run() {

		boolean timeOut = false;
		long startTime = (new Date()).getTime();

		while ((step < 7) && (!timeOut)) {
			// log(""+((new Date()).getTime() - startTime);
			// check where we are in specified delay
			timeOut = ((new Date()).getTime() - startTime) > delay;

			// if atz does not work, type to send cntrlZ and retry, in case a
			// message was stuck
			if (timeOut && (step == 1)) {
				step = -1;
				mySerial.send("" + cntrlZ);
			}

			// read incoming string
			String result = mySerial.getIncommingString();

			// log ("<- "+result+"\n--------");
			int expectedResult = -1;

			try {
				// log ("Step:"+step);

				switch (step) {
				case 0:

					mySerial.send("atz");
					delay = LONG;
					startTime = (new Date()).getTime();
					break;

				case 1:
					delay = STANDARD;
					mySerial.send("ath0");
					startTime = (new Date()).getTime();
					break;
				case 2:
					expectedResult = result.indexOf("OK");

					// log ("received ok ="+expectedResult);
					if (expectedResult > -1) {
						mySerial.send("at+cmgf=1");
						startTime = (new Date()).getTime();
					} else {
						step = step - 1;
					}
					break;
				case 3:
					expectedResult = result.indexOf("OK");

					// log ("received ok ="+expectedResult);
					if (expectedResult > -1) {
						mySerial.send("at+csca=\"" + csca + "\"");
						startTime = (new Date()).getTime();
					} else {
						step = step - 1;
					}

					break;
				case 4:
					expectedResult = result.indexOf("OK");

					// log ("received ok ="+expectedResult);
					if (expectedResult > -1) {
						mySerial.send("at+cmgs=\"" + recipient + "\"");
						startTime = (new Date()).getTime();
					} else {
						step = step - 1;
					}

					break;
				case 5:
					expectedResult = result.indexOf(">");

					// log ("received ok ="+expectedResult);
					if (expectedResult > -1) {
						mySerial.send(message + cntrlZ);
						startTime = (new Date()).getTime();
					} else {
						step = step - 1;
					}
					delay = VERYLONG;// waitning for message ack

					break;

				case 6:
					expectedResult = result.indexOf("OK");
					// read message number
					if (expectedResult > -1) {
						int n = result.indexOf("CMGS:");
						result = result.substring(n + 5);
						n = result.indexOf("\n");
						status = 0;
						messageNo = Long.parseLong(result.substring(0, n)
								.trim());

						log("sent message no:" + messageNo);

					} else {
						step = step - 1;
					}

					break;
				}
				step = step + 1;

				aThread.sleep(100);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		mySerial.closeConnection();

		// if timed out set status

		if (timeOut) {
			status = -2;
			log("*** time out at step " + step + "***");
		}
	}

	/**
	 * logging function, includes date and class name
	 */
	private void log(String s) {
		System.out.println(new java.util.Date() + ":"
				+ this.getClass().getName() + ":" + s);
	}
}