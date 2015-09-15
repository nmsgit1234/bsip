package com.nms.util.comm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class do all aspects of file handling.
 * @author Nasiruddin
 *
 */
public class FileHandler {
	
	public Properties getProperties(String fileName) throws Exception{
	  	Properties prop = new Properties();
    	try {
    	  	InputStream in =  getClass().getClassLoader().getResourceAsStream(fileName);  	 

    		//load a properties file
    		prop.load(in);
 
    	} catch (IOException ex) {
    		throw new Exception("Unable to access the file: " + fileName );
        }
    	return prop;
 
    }
}
