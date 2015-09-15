

package com.nms.util.beans;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EntitySorter extends BaseSorter
{
	public EntitySorter()
	{
		super();
	}

	public EntitySorter(int cacheThreshold)
	{
		super();
		_cacheThreshold = cacheThreshold;
	}



	public void setHeader(List result)
	{
		// save the row headers (col name and datatype), if not yet saved
		if( _dataHeader.size() == 0 && result.size() > 0)
		{

			Object entity = result.get(0);

			HashMap headerMap = getHeaders(entity);

			Set keys = headerMap.keySet();
			Iterator iter = keys.iterator();
			String colType = null;
			String colName = null;
			while (iter.hasNext()){
				colName = (String)iter.next();
				if (colName.equalsIgnoreCase("Class")) continue;
				colType = (String)headerMap.get(colName);
				if (colType.equalsIgnoreCase("java.util.Date"))
				{
					colType=DT_DATE;
				}
				else if (colType.equalsIgnoreCase("java.lang.Long")){
					colType=DT_NUMERIC;
				}
				else{
					colType=DT_ALPHA;
				}
				DBColDef dbColDef = new DBColDef( colName, colType);
				_dataHeader.add( dbColDef );

			}
		}
	}

	// Saves a new resultset or appends a resultset to existing (if any) rows.
	// Assumes that all resultsets saved in this buffer will have the same columns as the first one.


	public void saveResult(List result) throws Exception
	{
		setHeader(result);

		// now add the data;
		// each new resultset may have its columns in a different order
		// from the first one, so have to use *this* resultset's column indices
		// rather than the first one's
		int col_count = _dataHeader.size();
		int row_count = 0;
		int col_index = 0;

		Method method = null;
		for (int x=0;x<result.size();x++){

			ArrayList rsRow = new ArrayList(col_count);

			Object entity = result.get(x);
			String methodName=null;

			for(int i=0; i<col_count; i++)
			{
				methodName = ((DBColDef) _dataHeader.get(i)).colName;
				if (methodName.equalsIgnoreCase("Class")) continue;

				methodName = "get" + methodName;
				Class clas = entity.getClass();
				method = clas.getMethod(methodName,null);
				Object rslt = method.invoke(entity,null);

				System.out.println("The result is : " + rslt);
				rsRow.add(rslt.toString());
			}

			row_count++;

			// adding either to the list or to file, depending on number of rows
			if( row_count + _rowIndex.length <= _cacheThreshold )
				_dataRows.add(rsRow);
			else
				_writeRowToCacheFile(row_count + _rowIndex.length, rsRow);

			// run garbage collector once in a while to get rid of all the
			// temp variables we keep creating
			if( row_count > _cacheThreshold && (row_count % _cacheThreshold == 1) )
			{

				if (log.isInfoEnabled()){
					log.info("EntitySorter: Current rowcount: " + row_count + "; running gc()" );
				}

				System.gc();
			}

		}

		// create/expand the array with row indices
		_rowIndex = new int[row_count + _rowIndex.length];
		// create/expand the array with row visibility
		_rowVisible = new boolean[_rowIndex.length];
		// populate both arrays
		for(int i=0; i<_rowIndex.length; i++)
		{
			_rowIndex[i] = i;
			_rowVisible[i] = true;
		}

		// calculate current and last pages
		_currPage = (_rowIndex.length > 0)? 1 : 0;

		_lastPage = _rowIndex.length / _rowsPerPage;
		if( _rowIndex.length % _rowsPerPage > 0 )
			_lastPage++;


	}


	// saves only the current row from the resultset

	/*
	public void saveResultsetRow(ResultSet rs) throws Exception
	{
		int col_count = _dataHeader.size();
		ArrayList rsRow = new ArrayList(col_count);
		for(int i=0; i<col_count; i++)
		{
			int col_index = rs.findColumn( ((DBColDef) _dataHeader.get(i)).colName );
			rsRow.add( getStringValue(rs, col_index) );
		}

		saveResultsetRow(rsRow);
	}
	*/

	public HashMap getHeaders(Object entity){

		Class c = entity.getClass();
		HashMap headerMap = new HashMap();

		Method[] theMethods = c.getMethods();
		for (int i = 0; i < theMethods.length; i++) {
			String methodString = theMethods[i].getName();


			System.out.println("Name: " + methodString);
			String returnString =
			 theMethods[i].getReturnType().getName();

			if (methodString.indexOf("get",0) > -1)
			{
				headerMap.put(methodString.substring(3),returnString);
			}
			System.out.println("   Return Type: " + returnString);
			Class[] parameterTypes = theMethods[i].getParameterTypes();
			System.out.print("   Parameter Types:");
			for (int k = 0; k < parameterTypes.length; k ++) {
			  String parameterString = parameterTypes[k].getName();
			  System.out.print(" " + parameterString);
			}

		}
		return headerMap;
	}

	//TODO move this to the test class for testing this.

	/*public static void main(String args[]){

	  List result = new ArrayList();

	  Person supp = new Person();
	  supp.setId(new Long("64"));
	  supp.setName("Nasir1");
	  supp.setEmail("asdf1@asdf.com");
	  supp.setAddress("dfd dfasdf dfasd1");
	  supp.setPhoneNumber("2323");

	  result.add(supp);

	  supp = new Person();
	  supp.setId(new Long("33"));
	  supp.setName("Nasir2");
	  supp.setEmail("asdf2@asdf.com");
	  supp.setAddress("dfd dfasdf dfasd2");
	  supp.setPhoneNumber("434344");

	  result.add(supp);

	  supp = new Person();
	  supp.setId(new Long("56"));
	  supp.setName("Nasir3");
	  supp.setEmail("asdf3@asdf.com");
	  supp.setAddress("dfd dfasdf dfasd3");
	  supp.setPhoneNumber("74564565");

	  result.add(supp);

	  supp = new Person();
	  supp.setId(new Long("32"));
	  supp.setName("Nasir4");
	  supp.setEmail("asdf4@asdf.com");
	  supp.setAddress("dfd dfasdf dfasd4");
	  supp.setPhoneNumber("2312313");

	  result.add(supp);

	  supp = new Person();
	  supp.setId(new Long("86"));
	  supp.setName("Nasir5");
	  supp.setEmail("asdf5@asdf.com");
	  supp.setAddress("dfd dfasdf dfasd5");
	  supp.setPhoneNumber("878787");

	  result.add(supp);

	  supp = new Person();
	  supp.setId(new Long("93"));
	  supp.setName("Nasir6");
	  supp.setEmail("asdf6@asdf.com");
	  supp.setAddress("dfd dfasdf dfasd6");
	  supp.setPhoneNumber("23424233");

	  result.add(supp);


	  supp = new Person();
	  supp.setId(new Long("67"));
	  supp.setName("Nasir7");
	  supp.setEmail("asdf7@asdf.com");
	  supp.setAddress("dfd dfasdf dfasd7");
	  supp.setPhoneNumber("3242343");

	  result.add(supp);

	  supp = new Person();
	  supp.setId(new Long("34"));
	  supp.setName("Nasir8");
	  supp.setEmail("asdf8@asdf.com");
	  supp.setAddress("dfd dfasdf dfasd8");
	  supp.setPhoneNumber("767676");

	  result.add(supp);

	  supp = new Person();
	  supp.setId(new Long("63"));
	  supp.setName("Nasir9");
	  supp.setEmail("asdf9@asdf.com");
	  supp.setAddress("dfd dfasdf dfasd9");
	  supp.setPhoneNumber("7878778");

	  result.add(supp);

	  EntitySorter sorter = new EntitySorter();
	  try{
      	sorter.saveResult(result);

      	int x = sorter.doActionGetStartRec("sort","Id","asc",3,2);
      	System.out.println("The total rows are " + sorter.getRowCount());
      	for(int z=0;z<sorter.getRowCount();z++)
      	{
      		List datarows = sorter.getDataRow(z);
      		System.out.println("The sorted data: \n" + datarows);
		}

		SortParams sortParams = new SortParams();
		sortParams.setActionType("goto");
		sortParams.setSortColumn("Id");
		sortParams.setSortOrder("asc");
		sortParams.setRowsPerPage(3);
		sortParams.setGoToPage(2);


		System.out.println("The sorted page is \n" + sorter.getSortedPage(sortParams));

		System.out.println("Test 2\n");

		System.out.println("The sorted page is \n" + sorter.getSortedPages(sortParams));
  	  }
  	  catch(Exception ex)
  	  {
		  ex.printStackTrace();
	  }

	}
*/
}
