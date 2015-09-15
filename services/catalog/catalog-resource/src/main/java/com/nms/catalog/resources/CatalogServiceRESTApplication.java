package com.nms.catalog.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class CatalogServiceRESTApplication extends Application{
	public static final Set<Class<?>> result = new HashSet<Class<?>>();
	{
		result.add(CatalogResource.class);
		
	}
	@Override
    public Set<Class<?>> getClasses() {
        return result;
    }	
}
