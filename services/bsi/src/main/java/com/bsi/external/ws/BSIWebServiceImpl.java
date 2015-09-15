package com.bsi.external.ws;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bsi.client.managers.RegionManager;
import com.bsi.client.managers.ServiceManager;
import com.bsi.common.beans.Region;
import com.bsi.common.beans.Service;
import com.nms.util.log.CommonLogger;


public class BSIWebServiceImpl implements BSIWebServiceIntf{

 private static Log log = LogFactory.getLog(BSIWebServiceImpl.class);
  private RegionManager regMgr = null;

  public BSIWebServiceImpl() {
  }

 public String getServiceProperties(String srvsName)
 {
   //Map serviceMap = new HashMap();
   StringBuffer sbr = new StringBuffer();

   try
   {
     ServiceManager srvsMgr = new ServiceManager();
     Service srvs = srvsMgr.getServiceByServiceName(srvsName);
     String propsXML = srvsMgr.getServicePropertiesXML(srvs.getNodeId().toString());
     CommonLogger.logDebug(log,"The ServiceID is : " + srvs.getNodeId().toString());
     CommonLogger.logDebug(log,"The propsxml is : " + propsXML);

     sbr.append("<fieldset><legend>About service</legend>");
     sbr.append("<table width=\"100%\" border=\"0\" align=top cellpadding=\"0\" cellspacing=\"10\">");
     sbr.append("<tr><td width=\"25%\">Service requested</td>");
     sbr.append("<td width=\"75%\"><input type=\"hidden\" name=\"serviceIds\" value=\"").append(srvs.getNodeId().toString()).append("\"/>");
     sbr.append("<input type=\"text\" name=\"serviceIdName\" maxlength=\"50\" size=\"40\" value=\"").append(srvs.getName()).append("\" readonly=\"readonly\" />");
     sbr.append("</td></tr></table>");
     sbr.append(propsXML);
     sbr.append("</fieldset>");

     //serviceMap.put("serviceID", srvs.getNodeId());
     //serviceMap.put("propertiesXML", propsXML);
   }
   catch(Exception ex)
   {
     CommonLogger.logError(log,"Exception occured in BSIWebServiceImpl.getServiceProperties",ex);
   }
   return sbr.toString();
 }




  public Map getCountries()
  {
    Map countryMap = new HashMap();
   try
    {
      regMgr = new RegionManager();
      List countries = regMgr.getRootRegions();
      for (int x=0;x<countries.size();x++)
      {
        Region country = (Region)countries.get(x);
        //Key is the countryID and value is the country
        countryMap.put(country.getRegionId().toString(),country.getName());
      }
    }
    catch(Exception ex)
    {
       ex.printStackTrace();
    }
    return countryMap;
  }

  public Map getStates(String countryId)
  {
    Map stateMap = new HashMap();
   try
    {
      regMgr = new RegionManager();
      List childRegs = regMgr.getChildRegions(countryId);
      Iterator iter = childRegs.iterator();

      while (iter.hasNext())
      {
        Region reg = (Region)iter.next();
        //Key is the countryID and value is the country
        stateMap.put(reg.getRegionId().toString(),reg.getName());
      }
    }
    catch(Exception ex)
    {
       ex.printStackTrace();
    }
    return stateMap;

  }

  public Map getLocations(String stateId)
  {
    Map locnMap = new HashMap();
   try
    {
      regMgr = new RegionManager();
       List childRegs = regMgr.getChildRegions(stateId);
      Iterator iter = childRegs.iterator();

      while (iter.hasNext())
      {
        Region location = (Region)iter.next();
        //Key is the countryID and value is the country
        locnMap.put(location.getRegionId().toString(),location.getName());
      }

    }
    catch(Exception ex)
    {
       ex.printStackTrace();
    }
    return locnMap;
  }
/*

*/

}
