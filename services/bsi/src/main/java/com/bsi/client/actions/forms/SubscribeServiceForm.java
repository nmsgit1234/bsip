package com.bsi.client.actions.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;

import com.nms.util.log.CommonLogger;



public class SubscribeServiceForm extends BuyerPropertiesForm{

	private static Log log = LogFactory.getLog(SubscribeServiceForm.class);
	private String personId;
	private String serviceIds;
	private String serviceIdName;

        private String[] regionId1;
        private String[] regionName1;

        private String[] regionId2;
        private String[] regionName2;

        private String[] regionId3;
        private String[] regionName3;

        private String[] regionId4;
        private String[] regionName4;

        private String[] regionId5;
        private String[] regionName5;

        private String[] regionId6;
        private String[] regionName6;

	private String modelCountryList;


	public void setPersonId(String personId){
		this.personId=personId;
	}

	public String getPersonId(){
		return personId;
	}

	public void setServiceIds(String serviceIds){
		this.serviceIds=serviceIds;
	}

	public String getServiceIds(){
		return serviceIds;
	}


	public void setServiceIdName(String serviceIdName){
		this.serviceIdName=serviceIdName;
	}

	public String getServiceIdName(){
		return serviceIdName;
	}

        public void setRegionId1(String[] regionId1)
        {
                this.regionId1=regionId1;
        }

        public String[] getRegionId1()
        {
                return regionId1;
        }

        public void setRegionId2(String[] regionId2)
        {
                this.regionId2=regionId2;
        }

        public String[] getRegionId2()
        {
                return regionId2;
        }

        public void setRegionId3(String[] regionId3)
        {
                this.regionId3=regionId3;
        }

        public String[] getRegionId3()
        {
                return regionId3;
        }

        public void setRegionId4(String[] regionId4)
        {
                this.regionId4=regionId4;
        }

        public String[] getRegionId4()
        {
                return regionId4;
        }

        public void setRegionId5(String[] regionId5)
        {
                this.regionId5=regionId5;
        }

        public String[] getRegionId5()
        {
                return regionId5;
        }

        public void setRegionId6(String[] regionId6)
        {
                this.regionId6=regionId6;
        }

        public String[] getRegionId6()
        {
                return regionId6;
        }


        public void setRegionName1(String[] regionName1)
        {
                this.regionName1=regionName1;
        }

        public String[] getRegionName1()
        {
                return regionName1;
        }


        public void setRegionName2(String[] regionName2)
        {
                this.regionName2=regionName2;
        }

        public String[] getRegionName2()
        {
                return regionName2;
        }

        public void setRegionName3(String[] regionName3)
        {
                this.regionName3=regionName3;
        }

        public String[] getRegionName3()
        {
                return regionName3;
        }

        public void setRegionName4(String[] regionName4)
        {
                this.regionName4=regionName4;
        }

        public String[] getRegionName4()
        {
                return regionName4;
        }

        public void setRegionName5(String[] regionName5)
        {
                this.regionName5=regionName5;
        }

        public String[] getRegionName5()
        {
                return regionName5;
        }

        public void setRegionName6(String[] regionName1)
        {
                this.regionName6=regionName6;
        }

        public String[] getRegionName6()
        {
                return regionName6;
        }


	public void setModelCountryList(String modelCountryList){
		this.modelCountryList=modelCountryList;
	}

	public String getModelCountryList(){
		return modelCountryList;
	}



        public ArrayList getLocns(){
                ArrayList locns = new ArrayList();

                for (int x=0;x<regionId1.length;x++)
                {

                        LocationForm locn = new LocationForm();


                        //For region 1
                        try
                        {
                          //if (regionId1[x] != null)
                          // locn.setRegionId1(regionId1[x]);


                              if (regionId1[x] != null)
                              {
                                              CommonLogger.logDebug(log,"The locationId cotains " + regionId1[x]);
                                              //LocationId can have multiple selected values.

                                              StringTokenizer str = new StringTokenizer(regionId1[x],",");
                                              int tokenCount = str.countTokens();

                                              if (tokenCount > 0 )
                                              {
                                                              String newToken = str.nextToken();
                                                              if (newToken != null && newToken.trim().length() > 0)
                                                              locn.setRegionId1(newToken);
                                              }

                                              while (str.hasMoreTokens())
                                              {
                                                              String token = str.nextToken();
                                                              CommonLogger.logDebug(log,"token  =" + token);

                                                              if (token != null && token.trim().length() > 0)
                                                              {
                                                                              //Create other location form objects with specified country and stateId.
                                                                              LocationForm newLocn = new LocationForm();
                                                                              newLocn.setRegionId1(token);
                                                                              locns.add(newLocn);
                                                              }
                                              }

                                              if (tokenCount > 1)
                                              {
                                                locns.add(locn);
                                                continue;
                                              }

                              }


                        }
                        catch(Exception ex)
                        {
                          locn.setRegionId1("-1");
                        }

                        try
                        {
                                if (regionName1[x] != null)
                                        locn.setRegionName1(regionName1[x]);
                        }
                        catch(Exception ex)
                        {
                                locn.setRegionName1("");
                        }

                        //For region 2
                       try
                       {
                        if (regionId2[x] != null)
                        {
                                        CommonLogger.logDebug(log,"The locationId cotains " + regionId2[x]);
                                        //LocationId can have multiple selected values.

                                        StringTokenizer str = new StringTokenizer(regionId2[x],",");
                                        int tokenCount = str.countTokens();

                                        if (tokenCount > 0 )
                                        {
                                                        String newToken = str.nextToken();
                                                        if (newToken != null && newToken.trim().length() > 0)
                                                                        locn.setRegionId2(newToken);
                                        }

                                        while (str.hasMoreTokens())
                                        {
                                                        String token = str.nextToken();
                                                        CommonLogger.logDebug(log,"token  =" + token);

                                                        if (token != null && token.trim().length() > 0)
                                                        {
                                                                        //Create other location form objects with specified country and stateId.
                                                                        LocationForm newLocn = new LocationForm();
                                                                        newLocn.setRegionId1(regionId1[x]);
                                                                        newLocn.setRegionId2(token);
                                                                        locns.add(newLocn);
                                                        }
                                        }
									  if (tokenCount > 1)
									  {
										locns.add(locn);
										continue;
									  }
                        }


                  }
                  catch(Exception ex)
                  {
                    locn.setRegionId2("-1");
                  }

                  try
                  {
                          if (regionName2[x] != null)
                                  locn.setRegionName2(regionName2[x]);
                  }
                  catch(Exception ex)
                  {
                          locn.setRegionName2("");
                  }

                        //For region 3
                        try
                        {
                        if (regionId3[x] != null)
                        {
                                        CommonLogger.logDebug(log,"The locationId cotains " + regionId3[x]);
                                        //LocationId can have multiple selected values.

                                        StringTokenizer str = new StringTokenizer(regionId3[x],",");
                                        int tokenCount = str.countTokens();

                                        if (tokenCount > 0 )
                                        {
                                                        String newToken = str.nextToken();
                                                        if (newToken != null && newToken.trim().length() > 0)
                                                                        locn.setRegionId3(newToken);
                                        }

                                        while (str.hasMoreTokens())
                                        {
                                                        String token = str.nextToken();
                                                        CommonLogger.logDebug(log,"token  =" + token);

                                                        if (token != null && token.trim().length() > 0)
                                                        {
                                                                        //Create other location form objects with specified country and stateId.
                                                                        LocationForm newLocn = new LocationForm();
                                                                        newLocn.setRegionId1(regionId1[x]);
                                                                        newLocn.setRegionId2(regionId2[x]);
                                                                        newLocn.setRegionId3(token);
                                                                        locns.add(newLocn);
                                                        }
                                        }
									  if (tokenCount > 1)
									  {
										locns.add(locn);
										continue;
									  }
                        }


                  }
                  catch(Exception ex)
                  {
                    locn.setRegionId3("-1");
                  }

                  try
                  {
                          if (regionName3[x] != null)
                                  locn.setRegionName3(regionName3[x]);
                  }
                  catch(Exception ex)
                  {
                          locn.setRegionName3("");
                  }

                        //For region 4
                        try
                        {
                        if (regionId4[x] != null)
                        {
                                        CommonLogger.logDebug(log,"The locationId cotains " + regionId4[x]);
                                        //LocationId can have multiple selected values.

                                        StringTokenizer str = new StringTokenizer(regionId4[x],",");
                                        int tokenCount = str.countTokens();

                                        if (tokenCount > 0 )
                                        {
                                                        String newToken = str.nextToken();
                                                        if (newToken != null && newToken.trim().length() > 0)
                                                                        locn.setRegionId4(newToken);
                                        }

                                        while (str.hasMoreTokens())
                                        {
                                                        String token = str.nextToken();
                                                        CommonLogger.logDebug(log,"token  =" + token);

                                                        if (token != null && token.trim().length() > 0)
                                                        {
                                                                        //Create other location form objects with specified country and stateId.
                                                                        LocationForm newLocn = new LocationForm();
                                                                        newLocn.setRegionId1(regionId1[x]);
                                                                        newLocn.setRegionId2(regionId2[x]);
                                                                        newLocn.setRegionId3(regionId3[x]);
                                                                        newLocn.setRegionId4(token);
                                                                        locns.add(newLocn);
                                                        }
                                        }
									  if (tokenCount > 1)
									  {
										locns.add(locn);
										continue;
									  }
                        }


                  }
                  catch(Exception ex)
                  {
                    locn.setRegionId4("-1");
                  }

                  try
                  {
                          if (regionName4[x] != null)
                                  locn.setRegionName4(regionName4[x]);
                  }
                  catch(Exception ex)
                  {
                          locn.setRegionName4("");
                  }

                        //For region 5
                        try
                        {
                        if (regionId5[x] != null)
                        {
                                        CommonLogger.logDebug(log,"The locationId cotains " + regionId5[x]);
                                        //LocationId can have multiple selected values.

                                        StringTokenizer str = new StringTokenizer(regionId5[x],",");
                                        int tokenCount = str.countTokens();

                                        if (tokenCount > 0 )
                                        {
                                                        String newToken = str.nextToken();
                                                        if (newToken != null && newToken.trim().length() > 0)
                                                                        locn.setRegionId5(newToken);
                                        }

                                        while (str.hasMoreTokens())
                                        {
                                                        String token = str.nextToken();
                                                        CommonLogger.logDebug(log,"token  =" + token);

                                                        if (token != null && token.trim().length() > 0)
                                                        {
                                                                        //Create other location form objects with specified country and stateId.
                                                                        LocationForm newLocn = new LocationForm();
                                                                        newLocn.setRegionId1(regionId1[x]);
                                                                        newLocn.setRegionId2(regionId2[x]);
                                                                        newLocn.setRegionId3(regionId3[x]);
                                                                        newLocn.setRegionId4(regionId4[x]);
                                                                        newLocn.setRegionId5(regionId5[x]);
                                                                        locns.add(newLocn);
                                                        }
                                        }
									  if (tokenCount > 1)
									  {
										locns.add(locn);
										continue;
									  }

                        }


                  }
                  catch(Exception ex)
                  {
                    locn.setRegionId5("-1");
                  }

                  try
                  {
                          if (regionName5[x] != null)
                                  locn.setRegionName5(regionName5[x]);
                  }
                  catch(Exception ex)
                  {
                          locn.setRegionName5("");
                  }

                        //For region 6
                        try
                        {
                          if (regionId6[x] != null) {
                            CommonLogger.logDebug(log, "The locationId cotains " + regionId6[x]);
                            //LocationId can have multiple selected values.

                            StringTokenizer str = new StringTokenizer(regionId6[x], ",");
                            int tokenCount = str.countTokens();

                            if (tokenCount > 0) {
                              String newToken = str.nextToken();
                              if (newToken != null && newToken.trim().length() > 0)
                                locn.setRegionId6(newToken);
                            }

                            while (str.hasMoreTokens()) {
                              String token = str.nextToken();
                              CommonLogger.logDebug(log, "token  =" + token);

                              if (token != null && token.trim().length() > 0) {
                                //Create other location form objects with specified country and stateId.
                                LocationForm newLocn = new LocationForm();
                                newLocn.setRegionId1(regionId1[x]);
                                newLocn.setRegionId2(regionId2[x]);
                                newLocn.setRegionId3(regionId3[x]);
                                newLocn.setRegionId4(regionId4[x]);
                                newLocn.setRegionId5(regionId5[x]);
                                newLocn.setRegionId6(token);
                                locns.add(newLocn);
                              }
                              if (tokenCount > 1) {
                                locns.add(locn);
                                continue;
                              }

                            }

                          }
                        }

                  catch(Exception ex)
                  {
                    locn.setRegionId6("-1");
                  }

                  try
                  {
                          if (regionName6[x] != null)
                                  locn.setRegionName6(regionName6[x]);
                  }
                  catch(Exception ex)
                  {
                          locn.setRegionName6("");
                  }
                  locns.add(locn);

        }
                return locns;
        }

        public void populateForm(List locns) {
           final int size = locns.size();
                     regionId1 = new String[size];
                     regionName1 = new String[size];

                     regionId2 = new String[size];
                     regionName2 = new String[size];

                     regionId3 = new String[size];
                     regionName3 = new String[size];

                     regionId4 = new String[size];
                     regionName4 = new String[size];

                     regionId5 = new String[size];
                     regionName5 = new String[size];

                     regionId6 = new String[size];
                     regionName6 = new String[size];

                     for (int x=0;x<locns.size();x++){
                             LocationForm locn = (LocationForm)locns.get(x);
                             regionId1[x] = String.valueOf(locn.getRegionId1());
                             regionId2[x] = String.valueOf(locn.getRegionId2());
                             regionId3[x] = String.valueOf(locn.getRegionId3());
                             regionId4[x] = String.valueOf(locn.getRegionId4());
                             regionId5[x] = String.valueOf(locn.getRegionId5());
                             regionId6[x] = String.valueOf(locn.getRegionId6());

                             regionName1[x] = String.valueOf(locn.getRegionName1());
                             regionName2[x] = String.valueOf(locn.getRegionName2());
                             regionName3[x] = String.valueOf(locn.getRegionName3());
                             regionName4[x] = String.valueOf(locn.getRegionName4());
                             regionName5[x] = String.valueOf(locn.getRegionName5());
                             regionName6[x] = String.valueOf(locn.getRegionName6());
                     }

        }

        public void reset(ActionMapping mapping, HttpServletRequest request) {

          CommonLogger.logDebug(log, "Resettng the SubscribeServiceForm " );
          this.personId = null;
          this.serviceIds = null;
          this.serviceIdName = null;
          this.regionId1 = null;
          this.regionName1 = null;
          this.regionId2 = null;
          this.regionName2 = null;
          this.regionId3 = null;
          this.regionName3 = null;
          this.regionId4 = null;
          this.regionName4 = null;
          this.regionId5 = null;
          this.regionName5 = null;
          this.regionId6 = null;
          this.regionName6 = null;
          this.modelCountryList = null;
        }
        }
