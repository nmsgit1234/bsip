

package com.nms.util.beans;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.io.*;
import java.text.DecimalFormat;

//For logging
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResultSetSorter extends BaseSorter
{

	private static DecimalFormat df = new DecimalFormat("#####################.########");

	public ResultSetSorter()
	{
		super();
	}

	public ResultSetSorter(int cacheThreshold)
	{
		super();
		_cacheThreshold = cacheThreshold;
	}


	//----------------------------------------------------------------------
	// Population
	//----------------------------------------------------------------------

	public void setHeader(ResultSet rs) throws Exception
	{
		// save the row headers (col name and datatype), if not yet saved
		if( _dataHeader.size() == 0 )
		{
			ResultSetMetaData meta = rs.getMetaData();
			int num_cols = meta.getColumnCount();
			for(int i=1; i<=num_cols; i++)
			{
				String col_name = meta.getColumnName(i);
				String col_type;
				switch( meta.getColumnType(i) )
				{
				case Types.DATE:
				case Types.TIMESTAMP:
					col_type = DT_DATE;
					break;
				case Types.DOUBLE:
				case Types.FLOAT:
				case Types.REAL:
				case Types.NUMERIC:
				case Types.DECIMAL:
				case Types.INTEGER:
				case Types.SMALLINT:
				case Types.TINYINT:
				case Types.BIGINT:
					col_type = DT_NUMERIC;
					break;
				default:
					col_type = DT_ALPHA;
				}

				DBColDef dbColDef = new DBColDef( col_name, col_type );
				_dataHeader.add( dbColDef );
			}
		}
	}

	// Saves a new resultset or appends a resultset to existing (if any) rows.
	// Assumes that all resultsets saved in this buffer will have the same columns as the first one.
	public void saveResult(ResultSet rs) throws Exception
	{
		setHeader(rs);

		// now add the data;
		// each new resultset may have its columns in a different order
		// from the first one, so have to use *this* resultset's column indices
		// rather than the first one's
		int col_count = _dataHeader.size();
		int row_count = 0;
		int col_index = 0;
		while( rs.next() )
		{
			ArrayList rsRow = new ArrayList(col_count);
			for(int i=0; i<col_count; i++)
			{
				col_index = rs.findColumn( ((DBColDef) _dataHeader.get(i)).colName );
				rsRow.add( getStringValue(rs, col_index) );
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
					log.info("ResultSetSorter: Current rowcount: " + row_count + "; running gc()" );
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


		public static String getStringValue(ResultSet rs, int rs_col_index)
		{
			try
			{
				if( rs.getObject( rs_col_index ) == null )
				{
					return "";
				}

				ResultSetMetaData meta = rs.getMetaData();

				switch( meta.getColumnType( rs_col_index ) )
				{
				case Types.DATE:
				case Types.TIMESTAMP:
					return rs.getString( rs_col_index ).substring( 0, 19 );
				case Types.DOUBLE:
				case Types.FLOAT:
				case Types.REAL:
				case Types.NUMERIC:
				case Types.DECIMAL:
					return df.format( rs.getDouble( rs_col_index ) );
				case Types.BINARY:
				case Types.VARBINARY:
				case Types.LONGVARBINARY:
					return "[Byte Array]"; // rs.getBytes(index+1);
				default:
					return rs.getString( rs_col_index ).trim();
				}
			}
			catch( SQLException e )
			{
				log.error( " getStringValue(): " + e.getMessage(), e );
			}

			return "";
		}

}
