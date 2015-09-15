package com.nms.catalog.service;

import java.util.List;
import java.util.Set;

import com.bsi.common.beans.Property;
import com.bsi.common.beans.Service;
import com.bsi.common.beans.Token;
import com.nms.catalog.domain.Tree;
import com.nms.catalog.exception.CatalogServiceException;


/**
 * This service is for working with the catalogs.
 * 
 * @author Nasiruddin
 *
 */
public interface CatalogService {
	
	public String getAllCategories();
	public Tree getFirstLevelServices() throws CatalogServiceException;
	public Tree getChildServices(String parentId) throws CatalogServiceException;
	public void updateServiceProperties(String srvsId, Set<Property> props);
	public Set<Property> getServiceProperties(String serviceId) throws CatalogServiceException;
	public Service getService(String serviceId, boolean getProps);
	public List<Service> getAllServices() throws Exception;
	public Token getToken(Long tokenId) throws CatalogServiceException;
	public Service getServiceByServiceName(String srvsName) throws CatalogServiceException;
	public Set<Property> getServiceSpecificProperties(String serviceId);
}
