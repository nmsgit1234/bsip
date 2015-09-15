package com.nms.util.log;

import org.apache.commons.logging.Log;
import java.io.*;
import java.util.*;
import java.text.*;

public class CommonLogger{

	private static String fileName = "bsi";
	private static String REPORT_FILENAME_DATE_FORMAT = "yyyyMMdd";
	private static PrintWriter logFile;
	private static SimpleDateFormat _dateFormat = new SimpleDateFormat( "yyyy.MM.dd.hh.mm" );

	/*static
	{
		initFile();
	}
      }
      */


	public static void logDebug(Log log,String msg){
		if (log.isDebugEnabled()){
			log.debug(msg);
		}
	}

	public static void logDebug(String msg){
		writeToFile(msg);
	}


	public static void logInfo(String msg){
		writeToFile(msg);
	}

	public static void logInfo(Log log,String msg){

		if (log.isInfoEnabled()){
			log.info(msg);
		}
	}

	public static void logError(Log log,String msg,Throwable ex){
		if (log.isErrorEnabled()){
			log.error(msg,ex);
		}
	}

	public static void logError(String msg,Throwable ex){
		writeToFile(msg);
		writeToFileException(ex);
	}

	public static void writeToFile(String msg)
	{
		String timeStamp = _dateFormat.format( new Date() );
		logFile.println( timeStamp + ":" + msg );
		logFile.flush();
	}

	public static void writeToFileException (Throwable ex)
	{
		String timeStamp = _dateFormat.format( new Date() );
		logFile.println( timeStamp + ":");
		ex.printStackTrace(logFile);
		logFile.flush();
	}


	public void finalize()
	{
		logFile.flush();
		logFile.close();
	}

	public static void initFile()
	{
		int fileNo =1;

		try
		{
			SimpleDateFormat reportFilenameDateFormat = new SimpleDateFormat(REPORT_FILENAME_DATE_FORMAT);
			String filenameTimeStamp = reportFilenameDateFormat.format( new Date() );
			File file = new File(fileName + "." + fileNo + "."+ filenameTimeStamp + ".log");
			while (file.exists())
			{
				fileNo++;
				file = new File(fileName + "." + fileNo + "."+ filenameTimeStamp + ".log");
			}

			logFile = new PrintWriter( new BufferedWriter( new FileWriter(file)));
		}
		catch(Exception ex)
		{
			logError("Unable to create file for logging",ex);
		}
	}

}
