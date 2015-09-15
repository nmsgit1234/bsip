package com.bsi.client.actions.forms;

import org.apache.struts.action.*;
import java.util.List;
import java.util.ArrayList;
import com.bsi.client.actions.forms.PropertyForm;

public class BuyerForm extends PersonForm{

	private String[] propertyId = null;
	private String[] propertyValue = null;


	public void setPropertyId(String[] propertyId){
		this.propertyId=propertyId;
	}

	public String[] getPropertyId(){
		return propertyId;
	}

	public void setPropertyValue(String[] propertyValue){
		System.out.println("Calling setPropertyValue :" + propertyValue.length );
		this.propertyValue=propertyValue;
	}

	public String[] getPropertyValue(){
		return propertyValue;
	}

	public ArrayList getPropertiesValues(){
		ArrayList props = new ArrayList();

		for (int x=0;x<propertyId.length;x++){
			PropertyValueForm prop = new PropertyValueForm();
			//prop.setPropertyId(propertyId[x]);
			prop.setPropertyId(propertyId[x]);
			prop.setPropertyValue(propertyValue[x]);
			props.add(prop);
		}
		return props;
	}


   public void populateForm(List properties) {
      final int size = properties.size();
		propertyId = new String[size];
		propertyValue = new String[size];

		for (int x=0;x<properties.size();x++){
			PropertyValueForm prop = (PropertyValueForm)properties.get(x);
			//propertyId[x] =  String.valueOf(prop.getPropertyId());
			propertyId[x] =  String.valueOf(prop.getPropertyId());
			propertyValue[x] =  String.valueOf(prop.getPropertyValue());
		}

   }
}
