package com.nms.util.beans;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.io.*;
import java.text.DecimalFormat;

//For logging
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseSorter implements Serializable
{
	//----------------------------------------------------------------------
	// Data
	//----------------------------------------------------------------------

	protected static Log log = LogFactory.getLog(BaseSorter.class);

	protected int _rowsPerPage = 20;
	protected int _currPage = 0;
	protected int _lastPage = 0;

	protected List _dataHeader = new ArrayList();
	protected List _dataRows = new ArrayList();

	// pointers to actual data rows; will change when sorting
	// (sorting does not move data rows around, only pointers to them)
	protected int[] _rowIndex = new int[0];

	// visibility flag for each row (for filtering);
	// the index into this array is the *direct* index, not sorted -
	// ie, not _rowVisible[guiRow], but _rowVisible[_rowIndex[guiRow]]
	protected boolean[] _rowVisible = new boolean[0];

	//----------------------------------------------------------------
	// Sorting support
	protected String _sortColumn = "n/a";
	protected boolean _sortOrderAsc = true;

	//----------------------------------------------------------------
	// Caching support

	// Write to cache file(s) when number of rows exceeds the threshold;
	// a new file is created for each <threshold> rows to speed up
	// operations when a row near the end of the dataset is requested
	// (because of limitations of file pointer moving, non-sequential row
	// reads have to reopen the file, thus slowing random reads to a crawl).
	// SUGGESTION: when large datasets are expected, use threshold of
	// 5000 rows or less (in ctor) - provides better fetching performance;
	// 10K rows is default since most apps would probably not use more
	// than this and keeping them in memory is best.
	protected int _cacheThreshold = 10000;

	// the control structures (row indices, header, etc.) are not cached,
	// as well as the first rows up to the first <threshold>;
	// cache file is used only for the data rows beyond the first threshold
	protected File[] _cacheFiles = null;
	protected ObjectOutputStream _cacheFileWriter = null;
	protected ObjectInputStream _cacheFileReader = null;
	protected int _cacheFileCurrRowIndex = -1;
	protected ArrayList _cacheFileCurrDataRow = null;

	//----------------------------------------------------------------
	// Statics, inners, etc.
	protected final static String  DT_ALPHA	= "alpha",
								DT_NUMERIC	= "numeric",
								DT_DATE		= "date";

	protected class DBColDef implements Serializable
	{
		String colName = "";
		String dataType = DT_ALPHA;
		DBColDef(String col_name, String data_type)
		{
			if( col_name != null )
				colName = col_name;
			if( data_type != null &&
				(data_type.equals(DT_NUMERIC)) || (data_type.equals(DT_ALPHA)) || (data_type.startsWith(DT_DATE)) )
				dataType = data_type;
		}
	}

	//----------------------------------------------------------------------
	// Constructors/destructors
	//----------------------------------------------------------------------
	public BaseSorter()
	{
		super();
	}

	public BaseSorter(int cacheThreshold)
	{
		super();
		_cacheThreshold = cacheThreshold;
	}

	public void finalize()
	{
		try {
			if(_cacheFileWriter != null) _cacheFileWriter.close();
			if(_cacheFileReader != null) _cacheFileReader.close();
			if(_cacheFiles != null)
				for(int i=0;i<_cacheFiles.length;i++)
					_cacheFiles[i].delete();
		} catch(Throwable e) { }

		// since we call this function directly in addition to it being called
		// by the JVM (hopefully), need to null ptrs so doesn't choke if called again
		_cacheFileWriter = null;
		_cacheFileReader = null;
		_cacheFiles = null;
	}


	public void saveResult(Object result) throws Exception
	{

	}



	//----------------------------------------------------------------------
	// Population
	//----------------------------------------------------------------------


	public boolean replaceHeaderCol(String oldColName, String newColName)
	{
		for(int i=0; i<_dataHeader.size(); i++)
		{
			DBColDef col = (DBColDef)_dataHeader.get(i);
			if( col.colName.equalsIgnoreCase(oldColName) )
			{
				col.colName = newColName;
				return true;
			}
		}
		return false;
	}

	public void setHeader(List columnsArray)
	{
		DBColDef dbColDef = null;
		for(int i=0; i<columnsArray.size(); i++)
		{
			ColumnDefinition columnDefinition = (ColumnDefinition) columnsArray.get(i);
			String dt = columnDefinition.getDataType();
			dt = "number".equals(dt)? DT_NUMERIC : DT_ALPHA;
			dbColDef = new DBColDef( columnDefinition.getColumnName(), dt );
			_dataHeader.add( dbColDef );
		}
	}


	public void saveResultsetRow(ArrayList rsRow) throws Exception
	{
		int row_count = 1;

		// adding either to the list or to file, depending on number of rows
		if( row_count + _rowIndex.length <= _cacheThreshold )
			_dataRows.add(rsRow);
		else
			_writeRowToCacheFile(row_count + _rowIndex.length, rsRow);

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

	//----------------------------------------------------------------------
	// Attributes
	//----------------------------------------------------------------------
	public int getRowCount()
	{
		return _rowIndex.length;
	}

	public int getRowsPerPage()
	{
		return _rowsPerPage;
	}

	public int getCurrPage()
	{
		return _currPage;
	}

	public int getLastPage()
	{
		return _lastPage;
	}

	public String getSortColumn()
	{
		return _sortColumn;
	}

	public int getCacheThreshold()
	{
		return _cacheThreshold;
	}

	// as part of TO-DO, the name has to change to getDisplayRowCount(),
	// and existing getRowCount() has to become getAllRowCount();
	// usages have to be updated accordingly depending on purpose
	private int __getVisibleRowCount()
	{
		int visibleRowCount = 0;
		for(int i=0; i<_rowVisible.length; i++)
			if( _rowVisible[i] )
				visibleRowCount++;
		return visibleRowCount;
	}

	public int getStartRec()
	{
		return ((getCurrPage() - 1) * getRowsPerPage());
	}

	public int getLoopTillRec(int startRec)
	{
		int loopTillRec = startRec + getRowsPerPage();
		if( loopTillRec > getRowCount() )
			loopTillRec = getRowCount();
		return loopTillRec;
	}

	public List getDataRow(int index)
	{
		return _getRowDirect( _rowIndex[index] );
	}

	public List getDataRowByOrigIdx(int origIndex)
	{
		return _getRowDirect( origIndex );
	}

	public String getValue(int row, int col)
	{
		List dataRow = getDataRow(row);
		return (String) dataRow.get(col);
	}

	public String getValue(int row, String colName)
	{
		List dataRow = getDataRow(row);
		int col = getColIndex(colName);
		if( col >= 0 )
			return (String) dataRow.get(col);
		return null;
	}

	public int getColIndex(String colName)
	{
		for(int i=0; i<_dataHeader.size(); i++)
			if( ((DBColDef)_dataHeader.get(i)).colName.equalsIgnoreCase(colName) )
				return i;

		return -1;
	}

	public String getColDataType(int colIndex)
	{
		return ((DBColDef)_dataHeader.get(colIndex)).dataType;
	}

	public String getColDataType(String colName)
	{
		int index = getColIndex(colName);
		if( index >= 0 )
			return ((DBColDef)_dataHeader.get(index)).dataType;
		return null;
	}

	public boolean isColNumeric(int colIndex)
	{
		return DT_NUMERIC.equals(getColDataType(colIndex));
	}

	public boolean isColNumeric(String colName)
	{
		return isColNumeric(getColIndex(colName));
	}

	public boolean isColDate(int colIndex)
	{
		String type = getColDataType(colIndex);
		if( type != null && type.startsWith(DT_DATE) )
			return true;
		return false;
	}

	public boolean isColDate(String colName)
	{
		return isColDate(getColIndex(colName));
	}

	public boolean isRowVisible(int index)
	{
		return _rowVisible[ _rowIndex[index] ];
	}

	public boolean isSortAscending()
	{
		return _sortOrderAsc;
	}


	//----------------------------------------------------------------------
	// Operations
	//----------------------------------------------------------------------

	// Performs the action (if sort) or adjusts current page and
	// returns a 0-based index of the record from which to begin displaying the table
	public int doActionGetStartRec(String action_type, String sort_col, String sort_order, int rows_per_page, int go_to_page)
	{
		int start_rec = 0;

		if( _rowsPerPage != rows_per_page )
		{
			// if rows per page changed, update _rowsPerPage, _lastPage, and _currPage
			_rowsPerPage = (rows_per_page > 0)? rows_per_page : 20;
			_lastPage = getRowCount() / _rowsPerPage;
			if( getRowCount() % _rowsPerPage > 0 )
				_lastPage++;
			if( _currPage > _lastPage )
				_currPage = (_lastPage > 0)? 1 : 0;
		}

		if( getRowCount() <= 0 )
		{
			_currPage = _lastPage = 0;
			start_rec = 0;
			return start_rec;
		}

		if( action_type.startsWith("sort") && getColIndex(sort_col) >= 0 )
		{
			boolean asc = (sort_order != null && sort_order.toLowerCase().startsWith("asc"));
			sort(sort_col, asc);
			start_rec = (_currPage - 1) * _rowsPerPage;
		}
		else if( action_type.startsWith("show") )
		{
			_currPage = 1;
			start_rec = 0;
		}
		else if( action_type.startsWith("next") )
		{
			if( _currPage < _lastPage )
				_currPage++;
			start_rec = (_currPage - 1) * _rowsPerPage;
		}
		else if( action_type.startsWith("prev") )
		{
			if( _currPage > 1 )
				_currPage--;
			start_rec = (_currPage - 1) * _rowsPerPage;
		}
		else if( action_type.startsWith("go_to_page") )// specific page
		{
			if( go_to_page < 0 )
			{
				_currPage = 1;
				start_rec = 0;
			}
			else
			{
				_currPage = (go_to_page > _lastPage)? _lastPage : go_to_page;
				start_rec = (_currPage - 1) * _rowsPerPage;
			}
		}

		return start_rec;
	}

	// NOTE: sorting is disabled for large (cache file-based) datasets,
	// i.e. when getRowCount() > getCacheThreshold(), as it's extremely slow
	public void sort(String colName, boolean ascending)
	{

		if( getRowCount() > getCacheThreshold() )
			return;

		if( getRowCount() < 2 )
			return;

		// see what the previous sort was
		if( _sortColumn.equalsIgnoreCase(colName) )
		{
			// already sorted on this column, see if need to reverse
			if( _sortOrderAsc != ascending )
			{
				reverseRows();
				_sortOrderAsc = ascending;
			}
			return;
		}

		// previous sort is no good, need to sort afresh
		_sortColumn = colName;
		_sortOrderAsc = ascending;

		// reset row pointers to their virgin state
		clearSort();

		// sort
		int col = getColIndex(colName);
		_sort(col, (int[]) _rowIndex.clone(), _rowIndex, 0, _rowIndex.length);
		if( !_sortOrderAsc )
			reverseRows();
	}

	public void reverseRows()
	{

		int row_count = getRowCount();
		if(row_count < 2) return;

		int half = row_count / 2;
		int i = -1;
		int temp_idx;
		while( ++i < half )
		{
			temp_idx = _rowIndex[row_count - i - 1];
			_rowIndex[row_count - i - 1] = _rowIndex[i];
			_rowIndex[i] = temp_idx;
		}
	}

	// Revert to original order of rows
	public void clearSort()
	{
		for(int i=0; i<_rowIndex.length; i++)
			_rowIndex[i] = i;
	}

	// Accepted values for $filterOp: =, != (or <>), <, >
	private void __applyFilter(String colName, String filterOp, String filterVal)
	{
		int col = getColIndex(colName);
		if( col < 0 || filterOp == null || filterOp.length() == 0 ||
		    !(filterOp.equals("=") || filterOp.equals("!=") || filterOp.equals("<>") || filterOp.equals("<") || filterOp.equals(">")) )
			return;

		if( filterOp.equals("<>") )
			filterOp = "!=";

		String col_type = ((DBColDef)_dataHeader.get(col)).dataType;

		List dataRow = null;
		String rowVal = null;
		int cmpResult = 0;
		boolean match = false;

		for(int i=0; i<_rowVisible.length; i++)
		{
			dataRow = _getRowDirect(i);
			rowVal = (String) dataRow.get(col);
			match = false;

			cmpResult = _compareTwoVals(col_type, rowVal, filterVal);
			if( (cmpResult == 0 && filterOp.equals("=")) ||
				(cmpResult != 0 && filterOp.equals("!=")) ||
				(cmpResult < 0 && filterOp.equals("<")) ||
				(cmpResult > 0 && filterOp.equals(">")) )
				match = true;

			_rowVisible[i] = match;
		}
	}

	private void __clearFilter()
	{
		for(int i=0; i<_rowVisible.length; i++)
			_rowVisible[i] = true;
	}

	//----------------------------------------------------------------------
	// Private helper functions
	//----------------------------------------------------------------------

	// Always sorts in ascending order; see wrapper sort() for descending
	private void _sort(int col, int from[], int to[], int first, int count)
	{
	    if( count - first < 2 )
	        return;

	    int middle = (first + count) / 2;
	    _sort(col, to, from, first, middle);
	    _sort(col, to, from, middle, count);

	    int p = first;
	    int q = middle;

	    if( (count - first >= 4) && _compare(col, from[middle - 1], from[middle]) <= 0 )
	    {
	        for (int i = first; i < count; i++)
	            to[i] = from[i];
	        return;
	    }

	    for( int i = first; i < count; i++ )
	    {
	        if( (q >= count) || (p < middle && _compare(col, from[p], from[q]) <= 0) )
	            to[i] = from[p++];
	        else
	            to[i] = from[q++];
	    }
	}

	private int _compare(int col, int row1, int row2)
	{
		String col_type = ((DBColDef)_dataHeader.get(col)).dataType;
		String val1 = _getValueDirect(row1, col);
		String val2 = _getValueDirect(row2, col);

		// compare
		return _compareTwoVals(col_type, val1, val2);
	}

	private int _compareTwoVals(String colType, String val1, String val2)
	{
		if( (val1 == null || val1.length() == 0) && (val2 == null || val2.length() == 0) )
		    return 0;
		else if( val1 == null || val1.length() == 0 )
		    return -1;
		else if( val2 == null || val2.length() == 0 )
		    return 1;

		try
		{
			if( colType.equals(DT_NUMERIC) )
			{
				//double d1 = Double.valueOf(val1).doubleValue();
				//double d2 = Double.valueOf(val2).doubleValue();
				double d1 = Double.valueOf(HTMLUtil.replaceString(val1, ",", "")).doubleValue();
				double d2 = Double.valueOf(HTMLUtil.replaceString(val2, ",", "")).doubleValue();
				if(d1 == d2)
					return 0;
				else if(d1 > d2)
					return 1;
				else
					return -1;
			}
			else
				return val1.compareTo(val2);
		}
		catch(Exception e)
		{
			return val1.compareTo(val2);
		}
	}

	private String _getValueDirect(int directRow, int col)
	{
		List dataRow = _getRowDirect(directRow);
		return (String) dataRow.get(col);
	}

	private synchronized List _getRowDirect(int directRowIndex)
	{
		if( directRowIndex >= getRowCount() )
			return null;

		// first see if this row is in memory
		if( directRowIndex < _cacheThreshold )
			return (List) _dataRows.get(directRowIndex);

		// if not, get from cache file
		try
		{
			// see if this is:
			// a) first access to cache file(s)
			// b) the row requested is before the last read row's position
			// c) the row requested follows the last read but is not within the current cache file
			// if b), since mark()/reset() are not supported (ie, cannot rewind), treat as a)
			if( (_cacheFileCurrRowIndex == -1) ||
				(directRowIndex < _cacheFileCurrRowIndex) ||
				(directRowIndex > _cacheFileCurrRowIndex && (directRowIndex/_cacheThreshold != _cacheFileCurrRowIndex/_cacheThreshold)) )
			{
				try {
					// close the writer first - won't need it again;
					if(_cacheFileWriter != null) _cacheFileWriter.close();
					// close reader if exists (if does, it is on wrong file - see if condition above)
					if(_cacheFileReader != null) _cacheFileReader.close();
				} catch(Exception e) {}
				_cacheFileWriter = null; // so that finalize() doesn't choke

				// figure out correct cache file
				int cacheFileIndex = directRowIndex / _cacheThreshold - 1;
				_cacheFileReader = new ObjectInputStream(new FileInputStream(_cacheFiles[cacheFileIndex]));

				if (log.isDebugEnabled()){
					log.debug( "BaseSorter.getRowDirect(): Opening cache file [" + (cacheFileIndex+1) + "] for access" );
				}

				_cacheFileCurrRowIndex = _cacheThreshold * (cacheFileIndex + 1);
				_cacheFileCurrDataRow = (ArrayList) _cacheFileReader.readObject();
			}

			// read cache file until the requested record is found
			while( _cacheFileCurrRowIndex < directRowIndex )
			{
				_cacheFileCurrDataRow = (ArrayList) _cacheFileReader.readObject();
				_cacheFileCurrRowIndex++;
			}

			return _cacheFileCurrDataRow;
		}
		catch(Exception e)
		{
			log.error( "BaseSorter.getRowDirect(): Exception while reading from cache file: ", e );
		}

		return null;
	}

	protected void _writeRowToCacheFile(int rowNum, ArrayList rsRow) throws IOException
	{
		// create a new cache file as needed according to current rowNum and cacheThreshold
		int len = 0;
		if( _cacheFiles != null )
			len = _cacheFiles.length;

		if( len < (rowNum-1)/_cacheThreshold )
		{
			// copy all existing files+1 to new array
			File[] tmp = new File[len+1];
			if( _cacheFiles != null )
				System.arraycopy(_cacheFiles, 0, tmp, 0, len);
			_cacheFiles = tmp;

			// create new file
			_cacheFiles[len] = File.createTempFile("rsbuf", ".tmp");
			// flag for automatic deletion when this JVM shuts down
			try { _cacheFiles[len].deleteOnExit(); } catch(Exception e) {}


			if (log.isInfoEnabled()){
				log.info("BaseSorter: Current row [" + rowNum + "] exceeds Threshold of [" + _cacheThreshold + "] rows; Created cache file [" + _cacheFiles[len].getPath() + "]");
			}

			// close previous writer if exists
			if( _cacheFileWriter != null )
				_cacheFileWriter.close();
			// open writer on the new file
			_cacheFileWriter = new ObjectOutputStream(new FileOutputStream(_cacheFiles[len]));
		}

		// whew!
		_cacheFileWriter.writeObject(rsRow);
	}


	public List getSortedPage(SortParams sortParams){

			List sortedPageList = new ArrayList();
			List sortedPage = null;

			int startRec = doActionGetStartRec(sortParams.getActionType(),sortParams.getSortColumn(),sortParams.getSortOrder(),sortParams.getRowsPerPage(),sortParams.getGoToPage());

			int loopTillRec = getLoopTillRec(startRec);

			if (log.isDebugEnabled()){
				log.debug("The start rec is " +  startRec + " and loopTillRec is " + loopTillRec + ", getCurrPage " + getCurrPage());
			}

			if( getRowCount() == 0 || startRec >= loopTillRec )
			{
				return sortedPageList;
			}

			for (int i=startRec;i<loopTillRec;i++){
				sortedPage = getDataRow(i);
				sortedPageList.add(sortedPage);
			}

			return sortedPageList;
	}


	public List getSortedPages(SortParams sortParams){

			int startRec = doActionGetStartRec(sortParams.getActionType(),sortParams.getSortColumn(),sortParams.getSortOrder(),sortParams.getRowsPerPage(),sortParams.getGoToPage());

			return _dataRows;
	}

}
