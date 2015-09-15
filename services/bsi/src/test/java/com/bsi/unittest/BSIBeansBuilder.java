package com.bsi.unittest;

import java.util.HashSet;
import java.util.Set;
import com.bsi.common.beans.Role;

public class BSIBeansBuilder {
	
	public static Object getRoleObject(){
		
		Role role = new Role();
		role.setName("Administrator");
		role.setName("Administrator role");
		return role;
	}
	
	public static Role getInvalidRoleObject(){
		Role role = new Role();
		role.setRoleID(1l);
		role.setName("Administrator");
		role.setName("Administrator role");
		return role;
	}

}
