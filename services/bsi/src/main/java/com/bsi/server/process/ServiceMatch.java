package com.bsi.server.process;


//For logging
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsi.client.managers.PersonSrvsRegionManager;
import com.bsi.common.beans.BuyerSrvsRegion;
import com.bsi.common.beans.SupplierSrvsRegion;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

public class ServiceMatch{


	public static Log log = LogFactory.getLog(ServiceMatch.class);
	public HashMap bsrMap = new HashMap();
	public HashMap ssrMap = new HashMap();


	private void poulateBuyerSrvsRegnMap() throws BSIException{
		CommonLogger.logDebug(log,"In ServiceMatch:poulateBuyerSrvsRegnMap()");

		//Get the list of buyers
		PersonSrvsRegionManager psrHanlder = new PersonSrvsRegionManager();
		List bsrList = psrHanlder.getAllBuyerSrvsRegion();

		List valueList = null;
		Long keyLong = null;
		String keyValue = null;

		for (int x=0;x<bsrList.size();x++){
			BuyerSrvsRegion bsr = (BuyerSrvsRegion)bsrList.get(x);
			//Hashmap contains key - buyer id
			//				   value - List of [service id + service regn id]
			keyLong =bsr.getBuyerId();
			keyValue = bsr.getServiceId().toString() + "_" + bsr.getRegion().getRegionId().toString();
			if (bsrMap.containsKey(keyLong))
			{
				valueList = (List)bsrMap.get(keyLong);
				valueList.add(keyValue);
				bsrMap.put(keyLong, valueList);

			}
			else
			{
				valueList = new ArrayList();
				valueList.add(keyValue);
				bsrMap.put(keyLong,valueList);
			}
		}

		CommonLogger.logDebug(log,"The buyer Map contains \n" + bsrMap);

	}

	private void poulateSupplierSrvsRegnMap() throws BSIException{

		CommonLogger.logDebug(log,"In ServiceMatch:poulateSupplierSrvsRegnMap()");


		List valueList = null;
		String keyStr = null;
		Long keyValue = null;


		//Get the list of suppliers
		PersonSrvsRegionManager psrHanlder = new PersonSrvsRegionManager();
		List ssrList = psrHanlder.getAllSupplierSrvsRegion();

		for (int x=0;x<ssrList.size();x++){
			SupplierSrvsRegion ssr = (SupplierSrvsRegion)ssrList.get(x);
			//Hashmap contains key -  service id _ service regn id
			//				   value - List of [supplier id]
			keyStr = ssr.getServiceId().toString() + "_" + ssr.getRegion().getRegionId().toString();
			keyValue = ssr.getSupplierId();

			if (ssrMap.containsKey(keyStr))
			{

				valueList = (List)ssrMap.get(keyStr);
				valueList.add(keyValue);
				ssrMap.put(keyStr,valueList);

			}
			else
			{
				valueList = new ArrayList();
				valueList.add(keyValue);
				ssrMap.put(keyStr,valueList);
			}
		}
		CommonLogger.logDebug(log,"The supplier Map contains \n" + ssrMap);
	}



	public void startServiceMatch() {

		CommonLogger.logDebug(log,"In ServiceMatch:startServiceMatch()");


		try{
		//CommonLogger.logDebug(log,"In ServiceMatch:startServiceMatch()");
		//System.out.println("In ServiceMatch:startServiceMatch()");
		//System.out.println("bsrMap is \n" + bsrMap);

		Iterator bsrIterator = bsrMap.keySet().iterator();


		while(bsrIterator.hasNext()){
			Long buyerId = (Long)bsrIterator.next();
			List valueList = (List)bsrMap.get(buyerId);
			//For each value find the supplier service for that particular service and the region.
			for(int x=0;x<valueList.size();x++){
				String valueStr = (String)valueList.get(x);
				//Separate the service id and region id from the value.
				StringTokenizer str = new StringTokenizer(valueStr,"_");
				String serviceId = null;
				String serviceRegnId = null;

				while (str.hasMoreTokens()){
						serviceId = str.nextToken();
						serviceRegnId = str.nextToken();
				}

				//System.out.println("serviceId = " + serviceId + ",serviceRegnId=" + serviceRegnId);

				List supplierList = (List)ssrMap.get(valueStr);
				if (supplierList != null){
					for (int y=0;y<supplierList.size();y++){
						informSupplier(buyerId,(Long)supplierList.get(y),new Long(serviceId),new Long(serviceRegnId));
					}
				}
			}
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}


	}

	public void informSupplier(Long buyerId,Long supplierId,Long serviceId,Long srvsRegnId){
		CommonLogger.logDebug(log,"In ServiceMatch:informSupplier() \n");
		CommonLogger.logDebug(log,"buyerId = " + buyerId + ",supplierId = " + supplierId + ", serviceId=" + serviceId + ",srvsRegnId " +  srvsRegnId);
	}


	public static void main(String args[]){
		ServiceMatch sMatch = new ServiceMatch();

		//Create buyer map
		HashMap buyerMap = new HashMap();
		ArrayList srvs = null;
		srvs = new ArrayList();
		srvs.add("1_33");
		srvs.add("4_32");
		srvs.add("5_54");
		srvs.add("21_65");
		buyerMap.put(new Long(1),srvs);

		srvs = new ArrayList();
		srvs.add("8_33");
		srvs.add("7_32");
		srvs.add("4_54");
		srvs.add("2_65");
		buyerMap.put(new Long(2),srvs);

		srvs = new ArrayList();
		srvs.add("32_33");
		srvs.add("54_32");
		srvs.add("2_54");
		srvs.add("2_65");
		buyerMap.put(new Long(3),srvs);

		//Create supplier map
		HashMap suppMap = new HashMap();
		ArrayList suppList = null;
		suppList = new ArrayList();
		suppList.add(new Long("43223"));
		suppList.add(new Long("343"));
		suppList.add(new Long("6456"));
		suppList.add(new Long("2342"));
		suppMap.put("21_65",suppList);

		suppList = new ArrayList();
		suppList.add(new Long("43223"));
		suppList.add(new Long("343"));
		suppList.add(new Long("6456"));
		suppList.add(new Long("2342"));
		suppMap.put("7_32",suppList);


		suppList = new ArrayList();
		suppList.add(new Long("43223"));
		suppList.add(new Long("343"));
		suppList.add(new Long("6456"));
		suppList.add(new Long("2342"));
		suppMap.put("54_32",suppList);

		sMatch.bsrMap = buyerMap;
		sMatch.ssrMap = suppMap;
		sMatch.startServiceMatch();

	}

}
