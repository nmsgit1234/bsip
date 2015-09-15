package com.bsi.external.ws.client;

/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.utils.Options;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class BSIWebServiceClient
{
    public static void main(String [] args)
    {
        try {
            Options options = new Options(args);

            String endpointURL = options.getURL();
            String textToSend;

            args = options.getRemainingArgs();
            if ((args == null) || (args.length < 1)) {
                textToSend = "<nothing>";
            } else {
                textToSend = args[0];
            }

            Service  service = new Service();
            Call     call    = (Call) service.createCall();

            call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
            call.setOperationName( new QName("http://com.bsi.external.ws.BSIWebServiceIntf", "getCountries") );
            //call.addParameter( "arg1", XMLType.XSD_STRING, ParameterMode.IN);
            //call.setReturnType( org.apache.axis.encoding.XMLType.XSD_STRING );
            call.setOperationStyle("rpc");
            call.setReturnClass(Map.class);

            System.out.println("The getSOAPActionURI is " + call.getSOAPActionURI());

            Map ret = (Map) call.invoke( new Object[] { } );

            System.out.println("The Countries are ****** \n");
            Iterator iter = ret.keySet().iterator();
            while (iter.hasNext())
            {
              String key = (String)iter.next();
              System.out.println("The key is " + key + ", the value is " + ret.get(key));
              if (key != null && key.trim() != "0")
                getStates(endpointURL,key);
            }
            getServiceProperties(endpointURL,"Maths tuition");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getStates(String endpointURL,String countryId) throws Exception
    {

      try
      {
        Service  service = new Service();
         Call     call    = (Call) service.createCall();

         call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
         call.setOperationName( new QName("http://com.bsi.external.ws.BSIWebServiceIntf", "getStates") );
         call.addParameter("countryId", XMLType.XSD_STRING, ParameterMode.IN);
         //call.setReturnType( org.apache.axis.encoding.XMLType.XSD_STRING );
         call.setOperationStyle("rpc");
         call.setReturnClass(Map.class);


        Map ret = (Map) call.invoke(new Object[] {countryId});
        System.out.println("The states are \n");
        Iterator iter = ret.keySet().iterator();
        while (iter.hasNext()) {
          String key = (String) iter.next();
          System.out.println("The key is " + key + ", the value is " +
                             ret.get(key));
          if (key != null && key.trim() != "0")
            getLocations(endpointURL,key);

        }
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
        throw new Exception("Exception in getStates");
      }
    }

    public static void getLocations(String endpointURL,String stateId) throws Exception
    {

      try
      {
        Service  service = new Service();
          Call     call    = (Call) service.createCall();

          call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
          call.setOperationName( new QName("http://com.bsi.external.ws.BSIWebServiceIntf", "getLocations") );
          call.addParameter("countryId", XMLType.XSD_STRING, ParameterMode.IN);
          //call.setReturnType( org.apache.axis.encoding.XMLType.XSD_STRING );
          call.setOperationStyle("rpc");
          call.setReturnClass(Map.class);


        Map ret = (Map) call.invoke(new Object[] {stateId});

        Iterator iter = ret.keySet().iterator();
        System.out.println("The locations are \n");
        while (iter.hasNext()) {
          String key = (String) iter.next();
          System.out.println("The key is " + key + ", the value is " +
                             ret.get(key));
        }
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
        throw new Exception("the exception occured in getLocations() : ");
      }
    }

    public static void getServiceProperties(String endpointURL,String srvsName) throws Exception
     {

       try
       {
         Service  service = new Service();
           Call     call    = (Call) service.createCall();

           call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
           call.setOperationName( new QName("http://com.bsi.external.ws.BSIWebServiceIntf", "getServiceProperties") );
           call.addParameter("srvsName", XMLType.XSD_STRING, ParameterMode.IN);
           //call.setReturnType( org.apache.axis.encoding.XMLType.XSD_STRING );
           call.setOperationStyle("rpc");
           call.setReturnClass(String.class);


         String ret = (String) call.invoke(new Object[] {srvsName});

         System.out.println("The serviceProperty xml is \n" + ret);
       }
       catch(Exception ex)
       {
         ex.printStackTrace();
         throw new Exception("the exception occured in getLocations() : ");
       }
     }


}
