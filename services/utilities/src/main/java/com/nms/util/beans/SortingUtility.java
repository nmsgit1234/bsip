package com.nms.util.beans;

import java.util.List;

import com.nms.util.client.Constants;

public class SortingUtility{


	public static List getSortedPage(Object result,SortParams sortParam,String resultType) throws Exception{

		BaseSorter sorter = null;
		List resultPage = null;

		if (resultType.equalsIgnoreCase(Constants.SORT_LIST_TYPE_ENTITYLIST))
		{
			sorter = new EntitySorter();
		}
		else if (resultType.equalsIgnoreCase(Constants.SORT_LIST_TYPE_RESULTSET))
		{
			sorter = new ResultSetSorter();
		}

		sorter.saveResult(result);
		resultPage = sorter.getSortedPage(sortParam);
		return resultPage;

	}

	public static List getSortedPages(Object result,SortParams sortParam,String resultType) throws Exception{


		BaseSorter sorter = null;
		List resultPages = null;

		if (resultType.equalsIgnoreCase(Constants.SORT_LIST_TYPE_ENTITYLIST))
		{
			sorter = new EntitySorter();
		}
		else if (resultType.equalsIgnoreCase(Constants.SORT_LIST_TYPE_RESULTSET))
		{
			sorter = new ResultSetSorter();
		}

		sorter.saveResult(result);
		resultPages = sorter.getSortedPages(sortParam);
		return resultPages;


	}

}
