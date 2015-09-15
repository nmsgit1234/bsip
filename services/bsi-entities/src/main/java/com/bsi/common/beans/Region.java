package com.bsi.common.beans;


public class Region{

	private Long regionId;
	private String name;
	private Long areaCode;
	private Region parentRegion;
	private char isRoot;
	private char hasChild;


	public void setRegionId(Long regionId){
		this.regionId=regionId;
	}

	public Long getRegionId(){
		return regionId;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setAreaCode(Long areaCode){
		this.areaCode=areaCode;
	}

	public Long getAreaCode(){
		return areaCode;
	}

	public void setParentRegion(Region parentRegion){
		this.parentRegion=parentRegion;
	}

	public Region getParentRegion(){
		return parentRegion;
	}

	public void setIsRoot(char isRoot)
	{
		this.isRoot=isRoot;
	}

	public char getIsRoot()
	{
		return isRoot;
	}

	public void setHasChild(char hasChild)
	{
		this.hasChild=hasChild;
	}

	public char getHasChild()
	{
		return hasChild;
	}


	//Utility methods

	public boolean isRoot()
	{
		boolean isroot = false;

		if (isRoot == 'y')
			isroot = true;
		return isroot;
	}

	public boolean hasChildren()
	{
		boolean haschildren = false;

		if (hasChild == 'y')
			haschildren = true;
		return haschildren;
	}


}
