package com.nms.util.db;


import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.nms.util.log.CommonLogger;
import com.nms.util.xml.XMLHelper;

public class BulkDataUploader
{

	//Tag Names specific to the mapping xml
	private static String XML_DATA_DEF_NODE_NAME = "dataDefn";
	private static String XML_DATA_MAPPING_NODE_NAME = "dataMapping";
	private static String XML_DATA_DEF_CHILD_NAME = "dataName";
	private static String XML_DATA_MAPPING_CHILD_NAME = "table";

	private static String XML_DATA_MAPPING_NODE1="colName";
	private static String XML_DATA_MAPPING_NODE2="dataName";
	private static String XML_DATA_MAPPING_NODE3="type";
	private static String XML_DATA_MAPPING_NODE4="maxlength";
	private static String XML_DATA_MAPPING_ATTRIB1="name";

	//Delimiter
	private static String DATA_FILE_DELIMITER=",";


	//SQL types
	private static final int DATA_TYPE_STRING=1;
	private static final int DATA_TYPE_INTEGER=2;
	private static int rowsCreated = 0;




	//Data lists

	/*DataDefn List contains the DataDefn values as follows
	   ["CountryName"]
		"CountryID"
		"ContryCode"

	*/

	public static ArrayList dataDefnList = new ArrayList();

	/* dataMapList contains following
		[{	"ColName","CountryID"		}	]
		    "DataName","CountryName"
		    "DataType","String"
		    "MaxLength","50"

	*/

	public static LinkedHashMap dataMap = new LinkedHashMap();

	/* dataList contains the data read from the flat file.

		[["aaa"]]
		  "bbb"
		  "ccc"
		  "ddd"
		 ["xxx"]
		  "uuu"
		  "ddd"
		  "eee"
	*/

	public static ArrayList dataList = new ArrayList();
	public static LinkedHashMap queryValueOrderMap = new LinkedHashMap();
	public static LinkedHashMap dataTypeMap = new LinkedHashMap();





	public static void main(String args[])
	{

		//Get the mapping xml and the comma delimitted data file.
		String mappingFile=System.getProperty("mapfile");
		String 	dataFile=System.getProperty("datafile");

		CommonLogger.initFile();

		CommonLogger.logInfo("Mapping file is " + mappingFile + ", Data file name is " + dataFile);

		if (mappingFile == null || mappingFile.length() == 0 ||
			dataFile == null || dataFile.length() == 0
		   )
		{
			System.out.println("Please set the system properties for \"mapfile\" and \"datafile\" ");
			System.exit(-1);
		}

		BulkDataUploader loader = new BulkDataUploader();
		loader.uploadData(mappingFile,dataFile);

	}



	public void uploadData(String mapFile,String dataFile)
	{
		try
		{
			//Parse XML mapping file and store the values.
			parseXML(mapFile);

			//Read the data file and store the values.
			readDataFile(dataFile);


			executeDBUpload();
			CommonLogger.logDebug("Created " + rowsCreated + " records.");

		}
		catch(Exception ex)
		{
			CommonLogger.logError("Couldn't upload the data, the following exception occured : " + ex.getMessage(),ex);
		}
	}


	public void executeDBUpload() throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;

		try
		{
			conn = ConnectionManager.getDBConnection();
			conn.setAutoCommit(false);

			Iterator iter = dataMap.keySet().iterator();

			while (iter.hasNext())
			{
				String tableName = (String)iter.next();
				String query = createInsertString(tableName,(List)dataMap.get(tableName));
				pstmt = ConnectionManager.createPreparedStatment(conn,query);
				List posList = (List)queryValueOrderMap.get(tableName);
				List dataTypeList = (List)dataTypeMap.get(tableName);
				executeQuery(pstmt,posList,dataTypeList);
			}
			conn.commit();
		}
		catch(Exception ex)
		{
			ConnectionManager.rollBack(conn);
			throw new Exception("Failed in executeDBUpload() ",ex);
		}
		finally
		{
			ConnectionManager.closeStatement(pstmt);
			ConnectionManager.closeConnection(conn);
		}
	}



	public static void executeQuery(PreparedStatement pstmt,List posList,List dataTypeList ) throws Exception
	{
		try
		{
			CommonLogger.logDebug("In executeQuery() \n" + posList);

			for (int y=1;y<dataList.size();y++)
			{
				List data = (List)dataList.get(y);
				CommonLogger.logDebug("The data is  \n" + data);

				int loc =0;

				//For the given row of data check if all the position values are null.If true, ignore the record.

				boolean isNewRow = false;


				for (int z=0;z<posList.size();z++)
				{
					int posn = ((Integer)posList.get(z)).intValue();
					String dataValue = (String)data.get(posn);

					if (dataValue != null)
					{
						isNewRow = true;
						break;
					}
				}


				if (!isNewRow) continue;


				String actualData = null;

				for (int x=0;x<posList.size();x++)
				{
					loc++;
					int pos = ((Integer)posList.get(x)).intValue();
					int dataType = ((Integer)dataTypeList.get(x)).intValue();

					actualData = (String)data.get(pos);

					switch (dataType)
					{
						case DATA_TYPE_STRING:
								pstmt.setString(loc,actualData);
								break;
						case DATA_TYPE_INTEGER:
								if (actualData != null && !actualData.equalsIgnoreCase("null"))
								{
									pstmt.setInt(loc,Integer.parseInt((String)data.get(pos)));
								}
								else
								{
									pstmt.setInt(loc,-1);
								}
								break;
						default:
								throw new Exception("Unsupported datatype ");
					}
				}

				int numUpdate = pstmt.executeUpdate();
				if (numUpdate > 0)
					rowsCreated++;
			}

		}
		catch(Exception ex)
		{
			throw new Exception("Failed to execute the query ",ex);
		}
	}


	public void readDataFile(String file) throws Exception
	{
		FileReader input = new FileReader(file);
		BufferedReader bufRead = new BufferedReader(input);
		String line; // String that holds current file line
		int count = 0; // Line number of count

		// Read first line
		line = bufRead.readLine();
		count++;
		StringTokenizer str = null;

		while (line != null) {
		  if (line != null && line.length() > 0) {
			CommonLogger.logDebug("The line read is " +  line);
			String[] tokens = line.split(DATA_FILE_DELIMITER,-1);

			CommonLogger.logDebug("The data size is " + tokens.length);

			extractData(tokens);
		  }
		  line = bufRead.readLine();
		  count++;
		}
		bufRead.close();
	}


	public String createInsertString(String tableName,List mapList) throws Exception
	{

		List postList = new ArrayList();
		List dataTypeList = new ArrayList();


		StringBuffer insertStr = new StringBuffer();
		insertStr.append("INSERT INTO ");
		insertStr.append(tableName).append(" (");

		int count =0;
		int numKeys = mapList.size();

		for (int x=0;x<mapList.size();x++)
		{
			count++;

			HashMap colDef = (HashMap)mapList.get(x);
			insertStr.append((String)colDef.get("ColName"));
			if (count < numKeys)
				insertStr.append(",");

			int pos = getPosition((String)colDef.get("DataName"));
			postList.add(new Integer(pos));

			String dataType = (String)colDef.get("DataType");
			dataTypeList.add(new Integer(getDataType((String)colDef.get("DataType"))));
		}

		insertStr.append(") VALUES (");
		count =0;
		for (int z=0;z<numKeys;z++)
		{
			count++;
			insertStr.append("?");
			if (count < numKeys)
				insertStr.append(",");

		}
		insertStr.append(")");

		queryValueOrderMap.put(tableName,postList);
		dataTypeMap.put(tableName,dataTypeList);

		CommonLogger.logInfo("Insert statment : \n" + insertStr.toString());
		return insertStr.toString();
	}


	public int getDataType(String type) throws Exception
	{
		int dataType = -1;

		if (type == null) throw new Exception("DataType cannot be null");

		if (type.equalsIgnoreCase("String"))
		{
			dataType = DATA_TYPE_STRING;
		}
		else if (type.equalsIgnoreCase("Integer"))
		{
			dataType = DATA_TYPE_INTEGER;
		}

		if (dataType < 0) throw new Exception("Unknown data type ");

		return dataType;

	}



	public static int getPosition(String dataName)
	{
			return dataDefnList.indexOf(dataName);
	}



	public static void extractData(String[] str) throws Exception
	{
		try
		{
			if (str.length > dataDefnList.size())
				throw new Exception("Found too many vlaues, couldn't insert");

			ArrayList tempList = new ArrayList(dataDefnList.size());

			String token = null;
			for(int x=0;x<str.length;x++)
			{
				token= str[x].trim();
				if (token != null && (token.trim().length() == 0 || token.equalsIgnoreCase("null")))
					token = null;
				tempList.add(token);
			}

			dataList.add(tempList);
		}
		catch(Exception ex)
		{
			throw new Exception("The exception occured while extracting data from file",ex);
		}

	}




	public void parseXML(String xmlFile) throws Exception
	{
		try
		{
			Document doc = 	XMLHelper.getDocument(xmlFile);

			//Parse the DataDefn
			Element root = doc.getDocumentElement();

			System.out.println(root.getTagName());
			NodeList dataDefnElements = root.getElementsByTagName(XML_DATA_DEF_NODE_NAME);
			NodeList dataMapElements = root.getElementsByTagName(XML_DATA_MAPPING_NODE_NAME);

			parseDataDefn(dataDefnElements.item(0));
			parseDataMapping(dataMapElements.item(0));

		}
		catch(Exception ex)
		{
			throw new Exception("Exception occured while parsing the XML ",ex);
		}
	}



	public static void parseDataDefn(Node dataDefElement) throws Exception
	{
		try
		{
			NodeList columList = ((Element)dataDefElement).getElementsByTagName(XML_DATA_DEF_CHILD_NAME);
			System.out.println("Lenght : " + columList.getLength());
			Map tempMap = null;

			String colName = null;
			for (int i=0;i<columList.getLength();i++)
			{
				Element column = (Element)columList.item(i);

				colName = column.getFirstChild().getNodeValue();

				dataDefnList.add(colName);
			}
		}
		catch(Exception ex)
		{
			throw new Exception("The exception occured while parsing the data definition in  XML ",ex);
		}
	}


	public static void parseDataMapping(Node dataDefElement) throws Exception
	{
		try
		{

			NodeList tableList = ((Element)dataDefElement).getElementsByTagName(XML_DATA_MAPPING_CHILD_NAME);
			System.out.println("Lenght : " + tableList.getLength());
			HashMap tempMap = null;
			HashMap tempMap1 = null;


			for (int i=0;i<tableList.getLength();i++)
			{
				Element table = (Element)tableList.item(i);
				tempMap = new LinkedHashMap();

				System.out.println("The tag name is " + table.getTagName());
				System.out.println("The attribuet name is " + table.getAttribute("name"));

				NodeList columList = table.getElementsByTagName("column");


				List tempList = new ArrayList();


				for (int x=0;x<columList.getLength();x++)
				{
					tempMap1 = new LinkedHashMap();

					Element column = (Element)columList.item(x);

					tempMap1.put("ColName",XMLHelper.getTextValue(column,XML_DATA_MAPPING_NODE1));
					tempMap1.put("DataName",XMLHelper.getTextValue(column,XML_DATA_MAPPING_NODE2));
					tempMap1.put("DataType",XMLHelper.getTextValue(column,XML_DATA_MAPPING_NODE3));
					tempMap1.put("MaxLength",XMLHelper.getTextValue(column,XML_DATA_MAPPING_NODE4));
					tempList.add(tempMap1);
				}

				dataMap.put(table.getAttribute(XML_DATA_MAPPING_ATTRIB1),tempList);
			}
		}
		catch(Exception ex)
		{
			throw new Exception("The exception occured while parsing the data mapping in  XML ",ex);
		}
	}

}