package com.bsi.bulk.testdata;


import java.util.*;
import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.Person;
import com.bsi.client.actions.forms.BuyerForm;
import com.bsi.client.managers.PersonManager;


public class BulkBuyerCreate
{

	private static PersonManager personMgr = null;
	private static Person newPerson = null;


	public void createBuyers(int totalBuyers)
	{

		//PersonForm personForm = new PersonForm();

		try
		{

			BuyerForm buyerForm = null;

			System.out.println("Creating " + totalBuyers + " buyers.");
			personMgr = new PersonManager();

			for (int x=1;x<=totalBuyers;x++ )
			{

				buyerForm = new BuyerForm();


				buyerForm.setName("NmsBuyer" + x);
				buyerForm.setAddress("10" + x +"th cross,jayanagar");
				buyerForm.setPhoneNumber("343434" + x);
				buyerForm.setPersonType("B");
				buyerForm.setPassword("test1");
				buyerForm.setServiceId(new String[]{"3"});
				buyerForm.setEmail("nms"+ x +"@buyer.com");


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

				propertyValues[0]="9th Grade" + x;
				propertyValues[1]="English"+ x;
				propertyValues[2]="10A.M"+ x;
				propertyValues[3]="In house"+ x;

				buyerForm.setPropertyId(propertyId);
				buyerForm.setPropertyValue(propertyValues);

				personMgr.createBuyer(buyerForm);

			}
		}
		catch(Exception ex)
		{
            ex.printStackTrace();
		}

	}



	public void deleteBuyer()
	{

		//PersonForm personForm = new PersonForm();

		try
		{
			String username = "nms3@buyer.com";
			String password = "test1";

			HashMap userInputMap = new HashMap();
			userInputMap.put(BSIConstants.USERID,username);
			userInputMap.put(BSIConstants.PASSWORD,password);

			List result = personMgr.login(userInputMap);

			newPerson = (Person)result.get(0);


			//Delete person;
			personMgr.deletePerson(newPerson.getId());


		}
		catch(Exception ex)
		{
			ex.printStackTrace();
            //assertTrue("An excpetion has occured",false);

		}

	}


	public static void main(String args[])
	{
		BulkBuyerCreate buyer = new BulkBuyerCreate();
		buyer.createBuyers(500);
	}



}
