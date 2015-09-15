package com.bsi.common.beans;

import java.util.HashSet;
import java.util.Set;
import com.bsi.common.beans.Person;
import com.bsi.common.beans.Service;

public class SupplierSrvsRegion {

	private Long ssrId;
	private Long supplierId;
	private Long serviceId;
	// private Long serviceRegionId;
	private Region region;
	//private Map ssrPropValues;
    private Set<SupplierServicePropertyValues> ssrPropValues = new HashSet<SupplierServicePropertyValues>(0);


	public Set<SupplierServicePropertyValues> getSsrPropValues() {
        return this.ssrPropValues;
    }
    
    public void setSsrPropValues(Set<SupplierServicePropertyValues> ssrPropValues) {
        this.ssrPropValues = ssrPropValues;
    }
	
	public void setSsrId(Long ssrId) {
		this.ssrId = ssrId;
	}

	public Long getSsrId() {
		return ssrId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Region getRegion() {
		return region;
	}

	/*
	 * public void setServiceRegionId(Long serviceRegionId){
	 * this.serviceRegionId=serviceRegionId; }
	 * 
	 * public Long getServiceRegionId(){ return serviceRegionId;
	 * 
	 * }
	 */
}
