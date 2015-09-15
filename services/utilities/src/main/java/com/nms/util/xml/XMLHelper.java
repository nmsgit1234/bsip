package com.nms.util.xml;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.nms.util.log.CommonLogger;


public class XMLHelper
{

		public static Document getDocument(String xmlFile)
		{
			Document doc = null;

			try
			{
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				doc = db.parse(new File(xmlFile));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return doc;
		}

	public static String getTextValue(Element ele, String tagName) throws Exception
	{
		String textVal = null;

		try
		{
			NodeList nl = ele.getElementsByTagName(tagName);
			if(nl != null && nl.getLength() > 0) {
				Element el = (Element)nl.item(0);
				if (el != null)
				{
					if (el.getFirstChild() != null)
						textVal = el.getFirstChild().getNodeValue();
				}
			}
		}
		catch(Exception ex)
		{
			CommonLogger.logError(" Exception occrued while getting the element value : ",ex);
			throw new Exception("Exception occrued while getting the element value");
		}

		return textVal;
	}



}
