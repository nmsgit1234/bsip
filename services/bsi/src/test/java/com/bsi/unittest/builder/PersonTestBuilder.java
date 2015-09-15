package com.bsi.unittest.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bsi.client.actions.forms.BuyerForm;
import com.bsi.client.actions.forms.BuyerPropertiesForm;
import com.bsi.client.actions.forms.PersonForm;
import com.bsi.client.util.BSIConstants;

/**
 * @author nshaikh
 *
 */
public class PersonTestBuilder {

	public static final String REGION_ID_8 = "8";
	public static final String SERVICE_ID_3 = "3";
	public static final String SERVICE_ID_2 = "2";
	
	public static final String SERVICE_ID_4 = "4";
	
	public static final String PERSON_TYPE_BUYER = "B";
	public static final String PERSON_TYPE_SUPPLIER = "S";
	public static final String UPDATED_EMAIL_BUYER = "nms9999@buyer.com";

	public BuyerForm createTestBuyerForm() {
		BuyerForm buyerForm = new BuyerForm();
		  buyerForm.setName("NmsBuyer9999");
		  buyerForm.setAddress("10th cross,jayanagar");
		  buyerForm.setPhoneNumber("343434");
		  buyerForm.setCountryCode("91");
		  buyerForm.setPersonType("B");
		  buyerForm.setPassword("test1");
		  buyerForm.setServiceId(new String[]{"3"});
		  buyerForm.setEmail("nms9999@buyer.com");


		  //Location related info.
		  buyerForm.setRegionId1("50");
		  buyerForm.setRegionId2("62");
		  buyerForm.setRegionId3("110");
		  buyerForm.setRegionId4("115");
		  buyerForm.setRegionId5("119");
		  buyerForm.setRegionId6("-1");


		  String[] propertyId = new String[4];
		  String[] propertyValues = new String[4];

		  propertyId[0]="1";
		  propertyId[1]="2";
		  propertyId[2]="3";
		  propertyId[3]="4";

		  propertyValues[0]="9th Grade";
		  propertyValues[1]="English";
		  propertyValues[2]="10A.M";
		  propertyValues[3]="In house";

		  buyerForm.setPropertyId(propertyId);
		  buyerForm.setPropertyValue(propertyValues);
		return buyerForm;
	}
	
	/**
	 * Prepares and returns the login info details in hashmap.
	 * @return
	 */
	public Map getBuyerLoginInfo(){
		BuyerForm personForm = createTestBuyerForm();
		String username = personForm.getEmail();
		String password = personForm.getPassword();

		HashMap userInputMap = new HashMap();
		userInputMap.put(BSIConstants.USERID, username);
		userInputMap.put(BSIConstants.PASSWORD, password);
		return userInputMap;
	}
	
	
	public Map getSupplierLoginInfo(){
		PersonForm personForm = createTestSupplierForm();
		String username = personForm.getEmail();
		String password = personForm.getPassword();

		HashMap userInputMap = new HashMap();
		userInputMap.put(BSIConstants.USERID, username);
		userInputMap.put(BSIConstants.PASSWORD, password);
		return userInputMap;
	}

	public BuyerPropertiesForm getBuyerPropertiesForm(){
		//
		String[] propertyId = new String[4];
		String[] propertyValues = new String[4];

		propertyId[0] = "1";
		propertyId[1] = "2";
		propertyId[2] = "3";
		propertyId[3] = "4";

		propertyValues[0] = "9th Grade";
		propertyValues[1] = "English";
		propertyValues[2] = "10A.M";
		propertyValues[3] = "In house";

		BuyerPropertiesForm props = new BuyerPropertiesForm();
		props.setPropertyId(propertyId);
		props.setPropertyValue(propertyValues);
		return props;
	}
	
	public PersonForm createTestSupplierForm(){
		String username = "nms9999@supplier.com";
		String password = "test1";

		PersonForm supplier = new PersonForm();
		supplier.setName("Nasir Supplier");
		supplier.setEmail(username);
		supplier.setAddress("123 Street,Bangalore");
		supplier.setPassword(password);
		supplier.setPhoneNumber("456456565");
		supplier.setCountryCode("91");
		supplier.setPersonType("S");
		return supplier;
	}
}
