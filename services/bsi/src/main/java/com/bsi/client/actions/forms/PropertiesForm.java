package com.bsi.client.actions.forms;

import org.apache.struts.action.*;
import java.util.List;
import java.util.ArrayList;
import com.bsi.client.actions.forms.PropertyForm;

public class PropertiesForm extends ActionForm{

	private Long[] propertyId = null;
	private String[] name = null;
	private String[] description = null;
	private String[] isMandatory = null;
	private String[] displayType = null;
	private String[] valueType = null;
	private Long[] tokenId = null;
	private String[] displaySize = null;
	private String[] dataSize = null;


	public void setPropertyId(Long[] propertyId){
		this.propertyId=propertyId;
	}

	public Long[] getPropertyId(){
		return propertyId;
	}

	public void setName(String[] name){
		System.out.println("Calling setName :" + name.length );
		this.name=name;
	}

	public String[] getName(){
		return name;
	}
	public void setDescription(String[] description){
		this.description=description;
	}

	public String[] getDescription(){
		return description;
	}
	public void setIsMandatory(String[] isMandatory){
		this.isMandatory=isMandatory;
	}

	public String[] getIsMandatory(){
		return isMandatory;
	}
	public void setDisplayType(String[] displayType){
		this.displayType=displayType;
	}

	public String[] getDisplayType(){
		return displayType;
	}
	public void setValueType(String[] valueType){
		this.valueType=valueType;
	}

	public String[] getValueType(){
		return valueType;
	}
	public void setTokenId(Long[] tokenId){
		this.tokenId=tokenId;
	}

	public Long[] getTokenId(){
		return tokenId;
	}
	public void setDisplaySize(String[] displaySize){
		this.displaySize=displaySize;
	}

	public String[] getDisplaySize(){
		return displaySize;
	}

	public void setDataSize(String[] dataSize){
		this.dataSize=dataSize;
	}

	public String[] getDataSize(){
		return dataSize;
	}


	public ArrayList getProperties(){
		ArrayList props = new ArrayList();

		for (int x=0;x<name.length;x++){
			PropertyForm prop = new PropertyForm();
			//prop.setPropertyId(propertyId[x]);
			prop.setPropertyId(propertyId[x]);
			prop.setName(name[x]);
			prop.setDescription(description[x]);
			prop.setIsMandatory(isMandatory[x]);
			prop.setDisplayType(displayType[x]);
			prop.setValueType(valueType[x]);
			prop.setTokenId(tokenId[x]);
			prop.setDisplaySize(displaySize[x]);
			prop.setDataSize(dataSize[x]);
			props.add(prop);
		}
		return props;
	}


   public void populateForm(List properties) {
      final int size = properties.size();
		propertyId = new Long[size];
		name = new String[size];
		description = new String[size];
		isMandatory = new String[size];
		displayType = new String[size];
		valueType = new String[size];
		tokenId = new Long[size];
		displaySize = new String[size];
		dataSize = new String[size];

		for (int x=0;x<properties.size();x++){
			PropertyForm prop = (PropertyForm)properties.get(x);
			//propertyId[x] =  String.valueOf(prop.getPropertyId());
			propertyId[x] = (Long)prop.getPropertyId();
			name[x] 	  =  String.valueOf(prop.getName());
			description[x] = String.valueOf(prop.getDescription());
			isMandatory[x] = String.valueOf(prop.getIsMandatory());
			displayType[x] = String.valueOf(prop.getDisplayType());
			valueType[x] = String.valueOf(prop.getValueType());
			tokenId[x] = (Long)prop.getTokenId();
			displaySize[x] = String.valueOf(prop.getDisplaySize());
			dataSize[x] = String.valueOf(prop.getDataSize());
		}

   }
}