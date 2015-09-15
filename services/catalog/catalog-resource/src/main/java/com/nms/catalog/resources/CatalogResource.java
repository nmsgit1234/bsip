package com.nms.catalog.resources;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsi.common.beans.Property;
import com.bsi.common.beans.Service;
import com.bsi.common.beans.Token;
import com.nms.catalog.domain.Tree;
import com.nms.catalog.exception.CatalogServiceException;
import com.nms.catalog.service.CatalogService;
import com.nms.catalog.utils.GenericUtil;

@Path("/catalog")
public class CatalogResource {
	private static Logger log = LoggerFactory.getLogger(CatalogResource.class);
	private static CatalogService catalogService;

	public CatalogService getCatalogService() {
		return catalogService;
	}

	public static void setCatalogService(CatalogService catalogService) {
		CatalogResource.catalogService = catalogService;
	}

	/**
	 * This lists all the first lever services
	 * 
	 * @return
	 */
	@GET
	@Path("firstLevelServices")
	@Produces({ MediaType.APPLICATION_XML })
	public Response getFirstLevelServices(@Context HttpHeaders httpHeaders) {
		MediaType mediaType = GenericUtil.getMediaType(httpHeaders);
		Tree tree = catalogService.getFirstLevelServices();
		return Response.ok(tree, MediaType.APPLICATION_XML_TYPE).build();
		// return Response.ok().build();
	}

	/**
	 * This lists all the child services for the given parent.
	 * 
	 * @return
	 */
	@GET
	@Path("childServices/{parentId}")
	@Produces({ MediaType.APPLICATION_XML })
	public Response getChildServices(@Context HttpHeaders httpHeaders,
			@PathParam("parentId") String parentId) {
		MediaType mediaType = GenericUtil.getMediaType(httpHeaders);
		Tree services = catalogService.getChildServices(parentId);
		return Response.ok(services, MediaType.APPLICATION_XML_TYPE).build();
		// return Response.ok().build();
	}

	/**
	 * Retrieves all the properties of the given service.
	 * 
	 * @param serviceId
	 */
	@GET
	@Path("serviceproperties/{serviceId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getServiceProperties(
			@PathParam("serviceId") String serviceId) {
		@SuppressWarnings("unchecked")
		Set<Property> properties = catalogService
				.getServiceProperties(serviceId);
		return Response.ok(properties, MediaType.APPLICATION_JSON).build();
		// return Response.ok().build();
	}

	/**
	 * Gets the service details with or without the property info based on the
	 * value of query param
	 */
	@GET
	@Path("servicedetails/{serviceId}/{getProps}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getServiceDetails(@PathParam("serviceId") String serviceId,
			@PathParam("getProps") boolean getProps) {
		@SuppressWarnings("unchecked")
		Service service = catalogService.getService(serviceId, getProps);
		return Response.ok(service, MediaType.APPLICATION_JSON).build();
		// return Response.ok().build();
	}
	
	/**
	 * Updates the properties of a particular service
	 * @param srvsId
	 * @param propsSet
	 * @throws CatalogServiceException
	 */
	@PUT
	@Path("updateServiceProperties/{serviceId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
//	public Response updateServiceProperties(@PathParam("serviceId") String serviceId, Set<Property> propertiesSet){
	public Response updateServiceProperties(@PathParam("serviceId") String serviceId, Set<Property> propertiesSet){
		//catalogService.updateServiceProperties(serviceId, new HashSet(Arrays.asList(propertiesSet)));
		catalogService.updateServiceProperties(serviceId, propertiesSet);
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("allservices")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllServices() throws Exception {
		@SuppressWarnings("unchecked")
		List<Service> services = catalogService.getAllServices();
		return Response.ok(services, MediaType.APPLICATION_JSON).build();
		// return Response.ok().build();
	}

	@GET
	@Path("gettoken/{tokenid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getToken(@PathParam("tokenid") long tokenid) {
		@SuppressWarnings("unchecked")
		Token token = catalogService.getToken(tokenid);
		return Response.ok(token, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("getservicebyname/{servceName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getServiceByServiceName(@PathParam("servceName") String servceName) {
		@SuppressWarnings("unchecked")
		Service service = catalogService.getServiceByServiceName(servceName);
		return Response.ok(service, MediaType.APPLICATION_JSON).build();
	}

}
