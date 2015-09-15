package com.bsi.common.beans;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import java.lang.reflect.*;

public class Group{
	private Long groupID;
	private String name;
	private Set roles = new HashSet();
        private Set persons = new HashSet();

	public void setGroupID(Long groupID)
	{
		this.groupID=groupID;
	}

	public Long getGroupID()
	{
		return groupID;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getName()
	{
		return name;
	}

	public void setRoles(Set roles)
	{
		this.roles=roles;
	}

	public Set getRoles()
	{
		return roles;
	}

        public void setPersons(Set persons)
        {
                this.persons=persons;
        }

        public Set getPersons()
        {
                return persons;
        }


}
