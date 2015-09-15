package com.nms.serviceclients.wsclients.catalog;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;

import com.bsi.common.beans.Service;
import com.bsi.common.beans.Token;
import com.nms.serviceclients.CatalogServiceClient;
import com.nms.serviceclients.config.ServiceClientConfig;

public class CatalogWebserviceClient implements CatalogServiceClient {
	private static String catalogServiceHostURL = null;
	private static String catalogServiceUserName = null;
	private static String catalogServicePassword = null;
	
	RestTemplate catalogService = null;
	static {
		catalogServiceHostURL=ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_SERVICE_HOST_URL);
		catalogServiceUserName=ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_SERVICE_USERNAME);
		catalogServicePassword=ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_SERVICE_PASSWORD);
	}
	public RestTemplate getCatalogService() {
		return catalogService;
	}


	public void setCatalogService(RestTemplate catalogService) {
		this.catalogService = catalogService;
	}

/*
	static{
		catalogServiceHostURL=ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_SERVICE_HOST_URL);
	}
*/	
	public String getFirstLevelServices(){
		String getFirstLevelServiceURL = catalogServiceHostURL + ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_SERVICE_FIRSTLEVEL_SERVICES_URL);
		HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/xml");
        headers.set("Content-Type", "application/xml");
        addAuthorization(headers);
        ResponseEntity<String> response =  catalogService.exchange(getFirstLevelServiceURL, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        return response.getBody();
	}


	public String getChildServices(String parentId) {
		String getChildlServiceURL = MessageFormat.format(catalogServiceHostURL + ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_SERVICE_CHILD_SERVICES_URL), parentId);
		HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/xml");
        headers.set("Content-Type", "application/xml");
        addAuthorization(headers);
        ResponseEntity<String> response =  catalogService.exchange(getChildlServiceURL, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        return response.getBody();
	}


	public String getServicePropertiesJson(String serviceId) {
		String getServicePropertiesURL = MessageFormat.format(catalogServiceHostURL + ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_SERVICE_PROPERTIES_URL), serviceId);
		HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        addAuthorization(headers);
        ResponseEntity<String> response =  catalogService.exchange(getServicePropertiesURL, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        return response.getBody();
	}


	public String getServiceDetails(String serviceId, boolean getProps) {
		String getServiceDetailsURL = MessageFormat.format(catalogServiceHostURL + ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_SERVICE_DETAILS_URL), serviceId,getProps);
		HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        addAuthorization(headers);
        ResponseEntity<String> response =  catalogService.exchange(getServiceDetailsURL, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
       
        return response.getBody();
	}


	private void addAuthorization(HttpHeaders headers) {
		String auth=catalogServiceUserName + ":" + catalogServicePassword;
        byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
        headers.add("Authorization", "Basic " + new String(encodedAuthorisation));
	}


	public String updateServiceProperties(String serviceId,
			String propertiesJson) {
		String updatePropertiesURL = MessageFormat.format(catalogServiceHostURL + ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_SERVICE_UPDATE_PROPERTIES_URL), serviceId);
		HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-type","application/json");
        addAuthorization(headers);
        HttpEntity<String> entity = new HttpEntity<String>(propertiesJson,headers);
        ResponseEntity<String>  response= catalogService.exchange(updatePropertiesURL,HttpMethod.PUT,entity,String.class);
        //catalogService.put(updatePropertiesURL, entity);
        return response.getBody();
	}
	
	public List<Service> getAllServices() throws Exception{
		String getAllServicesURL = catalogServiceHostURL + ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_GET_ALL_SERVICES_URL);
		HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-type","application/json");
        addAuthorization(headers);
        ResponseEntity<String> response =  catalogService.exchange(getAllServicesURL, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
		ObjectMapper mapper = new ObjectMapper();
		Service[] 	services = mapper.readValue(response.getBody(), Service[].class);
        return Arrays.asList(services);
		
	}
	
	public Token getToken(String tokenId) throws Exception{
		String getTokenURL = MessageFormat.format(catalogServiceHostURL + ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_GET_TOKEN_URL),tokenId);
		HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        addAuthorization(headers);
        HttpEntity<String> entity = new HttpEntity<String>(getTokenURL,headers);
        ResponseEntity<String> response =  catalogService.exchange(getTokenURL, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
		ObjectMapper mapper = new ObjectMapper();
		Token token = mapper.readValue(response.getBody(), Token.class);
        return token;
	}
	
	public Service getServiceByName(String serviceName) throws Exception{
		String getTokenURL = MessageFormat.format(catalogServiceHostURL + ServiceClientConfig.getPropertyValue(CatalogServiceConstants.CATALOG_GET_SERVICE_BY_NAME_URL),serviceName);
		HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        addAuthorization(headers);
        HttpEntity<String> entity = new HttpEntity<String>(getTokenURL,headers);
        ResponseEntity<String> response =  catalogService.exchange(getTokenURL, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
		ObjectMapper mapper = new ObjectMapper();
		Service service = mapper.readValue(response.getBody(), Service.class);
        return service;
	}

}
