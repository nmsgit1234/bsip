package com.bsi.common.beans;

import com.bsi.common.beans.Person;
import com.bsi.common.beans.Service;

public class AdminServiceRegion{

	private Long ssrId;
	private Long adminId;
	private Long serviceId;
	private String status;

	//private Long serviceRegionId;
        private Region region;


	public void setSsrId(Long ssrId){
		this.ssrId=ssrId;
	}

	public Long getSsrId(){
		return ssrId;
	}

	public void setAdminId(Long adminId){
		this.adminId=adminId;
	}

	public Long getAdminId(){
		return adminId;
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

	public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status=status;
	}

/*
	public void setServiceRegionId(Long serviceRegionId){
		this.serviceRegionId=serviceRegionId;
	}

	public Long getServiceRegionId(){
		return serviceRegionId;

	}
*/
      }
