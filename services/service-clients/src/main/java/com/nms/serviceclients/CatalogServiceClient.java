package com.nms.serviceclients;

import java.util.List;

import com.bsi.common.beans.Service;
import com.bsi.common.beans.Token;




public interface CatalogServiceClient {
	
	public String getFirstLevelServices();
	public String getChildServices(String parentId);
	public String getServicePropertiesJson(String serviceId);
	public String getServiceDetails(String serviceId, boolean getProps);	
	public String updateServiceProperties(String serviceId, String propertiesJson);
	public List<Service> getAllServices() throws Exception;
	public Token getToken(String tokenId) throws Exception;
	public Service getServiceByName(String serviceName) throws Exception;
}
