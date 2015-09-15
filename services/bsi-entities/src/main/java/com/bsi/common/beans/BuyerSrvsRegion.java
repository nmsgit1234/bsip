package com.bsi.common.beans;

import java.util.Map;
public class BuyerSrvsRegion{

	private Long bsrId;
	private Long buyerId;
	private Long serviceId;
	//private Long serviceRegionId;
	private Map  bsrPropValues;
        private Region region;


	public void setBsrId(Long bsrId){
		this.bsrId=bsrId;
	}

	public Long getBsrId(){
		return bsrId;
	}

	public void setBuyerId(Long buyerId){
		this.buyerId=buyerId;
	}

	public Long getBuyerId(){
		return buyerId;
	}

	public void setServiceId(Long serviceId){
		this.serviceId=serviceId;
	}

	public Long getServiceId(){
		return serviceId;
	}

        public void setRegion(Region region)
        {
          this.region=region;
        }

        public Region getRegion()
        {
          return region;
        }


/*
	public void setServiceRegionId(Long serviceRegionId){
		this.serviceRegionId=serviceRegionId;
	}

	public Long getServiceRegionId(){
		return serviceRegionId;
	}
*/

	public void setBsrPropValues(Map bsrPropValues){
		this.bsrPropValues=bsrPropValues;
	}

	public Map getBsrPropValues(){
		return bsrPropValues;
	}
}
