package com.bsi.unittest.actions.forms;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import com.bsi.client.actions.forms.BuyerPropertiesForm;
import com.bsi.client.actions.forms.PropertyValueForm;

public class BuyerPropertiesFormTest  extends TestCase {

	@Test
	public void testGetPropertiesValues() {
		BuyerPropertiesForm buyerForm = new BuyerPropertiesForm();
		buyerForm.setPropertyId(new String[]{"1","2"});
		buyerForm.setPropertyValue(new String[]{"1_red","2_india","2_pakistan","2_bangladesh","2_srilanka"});
		ArrayList<PropertyValueForm> propertiesList = buyerForm.getPropertiesValues();
		assertTrue("The number of properties returned not correct", propertiesList.size() == 5);
	}

}
