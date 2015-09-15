package com.bsi.client.actions.forms;

import org.apache.struts.action.*;

public class PropertyValueForm extends ActionForm{

	private String propertyId;
	private String propertyValue;


	public void setPropertyId(String propertyId){
		this.propertyId=propertyId;
	}

	public String getPropertyId(){
		return propertyId;
	}

	public void setPropertyValue(String propertyValue){
		this.propertyValue=propertyValue;
	}

	public String getPropertyValue(){
		return propertyValue;
	}

}