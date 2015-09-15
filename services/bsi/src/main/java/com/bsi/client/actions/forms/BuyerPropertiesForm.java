package com.bsi.client.actions.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.struts.action.ActionForm;

public class BuyerPropertiesForm extends ActionForm
{

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

	public ArrayList<PropertyValueForm> getPropertiesValues(){
		ArrayList<PropertyValueForm> props = new ArrayList<PropertyValueForm>();
		for (int x=0;x<propertyId.length;x++){
			for (int y=0;y<propertyValue.length;y++){
				StringTokenizer str = new StringTokenizer(propertyValue[y],"_");
				if (str.countTokens() == 2){
					String pId = str.nextToken();
					if (propertyId[x].equals(pId) ){
						PropertyValueForm prop = new PropertyValueForm();
						prop.setPropertyId(propertyId[x]);
						prop.setPropertyValue(str.nextToken());
						props.add(prop);
					}
				} 
			}
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
