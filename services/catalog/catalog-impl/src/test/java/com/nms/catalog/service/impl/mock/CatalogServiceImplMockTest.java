package com.nms.catalog.service.impl.mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.bsi.common.beans.Service;
import com.nms.catalog.domain.Tree;
import com.nms.catalog.service.impl.CatalogServiceImpl;
import com.nms.db.DataAccessHibernateImpl;

public class CatalogServiceImplMockTest extends TestCase {
	CatalogServiceImpl csImpl = new CatalogServiceImpl();
	private static final Logger logger = LoggerFactory
			.getLogger(CatalogServiceImplMockTest.class);
	ApplicationContext applicationContext = null;

	@Test
	public void testGetFirstLevelServices() {
		DataAccessHibernateImpl dbAccessor = Mockito
				.mock(DataAccessHibernateImpl.class);
		csImpl.setDbAccessor(dbAccessor);
		when(
				dbAccessor.getObjectsFromQuery(anyString(), anyList(),
						any(Method[].class))).thenReturn(getServicesList());
		logger.debug("In CatalogServiceImpl:getFirstLevelServices()");
		Tree servicesTree = csImpl.getFirstLevelServices();
		assertNotNull("Failed while getting the first level services",
				servicesTree);
	}

	@Test
	public void testChildServices() {
		DataAccessHibernateImpl dbAccessor = Mockito
				.mock(DataAccessHibernateImpl.class);
		csImpl.setDbAccessor(dbAccessor);
		when(
				dbAccessor.getObjectsFromQuery(anyString(), anyList(),
						any(Method[].class))).thenReturn(getServicesList());
		Tree services = csImpl.getChildServices("0");
		assertNotNull("Failed while getting the child services", services);
	}

	private List<Service> getServicesList() {
		List<Service> services = new ArrayList<Service>();
		Service childService = new Service();
		childService.setNodeId(new Long(1));
		childService.setName("Services");
		services.add(childService);
		return services;
	}

}
