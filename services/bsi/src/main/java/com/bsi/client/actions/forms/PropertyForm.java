package com.bsi.client.actions.forms;

import org.apache.struts.action.*;

public class PropertyForm extends ActionForm{

	private Long propertyId;
	private String name;
	private String description;
	private String isMandatory;
	private String displayType;
	private String valueType;
	private Long tokenId;
	private String displaySize;
	private String dataSize;


	public void setPropertyId(Long propertyId){
		this.propertyId=propertyId;
	}

	public Long getPropertyId(){
		return propertyId;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}
	public void setDescription(String description){
		this.description=description;
	}

	public String getDescription(){
		return description;
	}
	public void setIsMandatory(String isMandatory){
		this.isMandatory=isMandatory;
	}

	public String getIsMandatory(){
		return isMandatory;
	}
	public void setDisplayType(String displayType){
		this.displayType=displayType;
	}

	public String getDisplayType(){
		return displayType;
	}
	public void setValueType(String valueType){
		this.valueType=valueType;
	}

	public String getValueType(){
		return valueType;
	}
	public void setTokenId(Long tokenId){
		this.tokenId=tokenId;
	}

	public Long getTokenId(){
		return tokenId;
	}
	public void setDisplaySize(String displaySize){
		this.displaySize=displaySize;
	}

	public String getDisplaySize(){
		return displaySize;
	}

	public void setDataSize(String dataSize){
		this.dataSize=dataSize;
	}

	public String getDataSize(){
		return dataSize;
	}

}