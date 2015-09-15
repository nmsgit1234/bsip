package com.nms.util.beans;

public class SortParams{

	private String sortColumn;
	private String sortOrder;
	private int startPage;
	private int goToPage;
	private String actionType;
	private int rowsPerPage;


	public void setSortColumn(String sortColumn){
		this.sortColumn=sortColumn;
	}

	public String getSortColumn(){
		return sortColumn;
	}

	public void setSortOrder(String sortOrder){
		this.sortOrder=sortOrder;
	}

	public String getSortOrder(){
		return sortOrder;
	}

	public void setStartPage(int startPage){
		this.startPage=startPage;
	}

	public int getStartPage(){
		return startPage;
	}

	public void setGoToPage(int goToPage){
		this.goToPage=goToPage;
	}

	public int getGoToPage(){
		return goToPage;
	}

	public void setActionType(String actionType){
		this.actionType=actionType;
	}

	public String getActionType(){
		return actionType;
	}

	public void setRowsPerPage(int rowsPerPage){
		this.rowsPerPage=rowsPerPage;
	}

	public int getRowsPerPage(){
		return rowsPerPage;
	}
}
