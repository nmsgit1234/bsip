package com.bsi.common.beans;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import java.lang.reflect.*;

public class Role{
	private Long roleID;
	private String name;
	private String description;

	public void setRoleID(Long roleID)
	{
		this.roleID=roleID;
	}

	public Long getRoleID()
	{
		return roleID;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getName()
	{
		return name;
	}

	public void setDescription(String description)
	{
		this.description=description;
	}

	public String getDescription()
	{
		return description;
	}

}