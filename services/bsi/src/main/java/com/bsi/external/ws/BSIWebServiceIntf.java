package com.bsi.external.ws;

import java.util.List;
import java.util.Set;
import java.util.Map;

public interface BSIWebServiceIntf
{
  public String getServiceProperties(String srvsName);
  public Map getCountries();
//  public String getCountries();
  public Map getStates(String countryId);
  public Map getLocations(String stateId);
}
