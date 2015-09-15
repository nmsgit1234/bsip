<%@ page language="java" contentType="text/html; charset=utf-8"
	 import="org.apache.axis.client.Call,
		org.apache.axis.client.Service,
		org.apache.axis.encoding.XMLType,
		javax.xml.namespace.QName,
		javax.xml.rpc.ParameterMode,
		java.util.HashMap,
		java.util.Map,
		java.util.Iterator" %>


<%
	String action = request.getParameter("action");
	
	System.out.println("The action is " + action);
	
	if(action != null && action.equalsIgnoreCase("getStates"))
	{
		String countryId = request.getParameter("countryId");
		System.out.println("The country id is " + countryId);
		Map stateMap = getStates(countryId);
		out.println("<select name=\"stateId\" onchange=\"getLocations(this)\">");
		
		out.println(getOptionsHTML(stateMap));
		out.println("</select>");
		out.flush();
		return;
	}
	else if (action != null && action.equalsIgnoreCase("getLocations"))
	{
		String stateId = request.getParameter("stateId");
		System.out.println("The stateId id is " + stateId);
		Map stateMap = getLocations(stateId);
		
		out.println("<select name=\"locationId\">");
		out.println(getOptionsHTML(stateMap));
		out.println("</select>");
		out.flush();
		return ;
	}
	else if (action != null && action.equalsIgnoreCase("getServiceProperties"))
	{
		String srvsName = request.getParameter("srvsName");
		out.println(getServiceProps(srvsName));
		out.flush();
		return;
	}
%>


<%!

   public static String endpointURL = "http://localhost:7001/bsi/services/BSIWebService";	


    public Map getStates(String countryId) throws Exception
    {

	Map stateMap = null;
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


        stateMap = (Map) call.invoke(new Object[] {countryId});
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
        throw new Exception("Exception in getStates");
      }
      
      return stateMap;
    }
    
    

    public Map getLocations(String stateId) throws Exception
    {

	Map locnMap = null;
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


       locnMap = (Map) call.invoke(new Object[] {stateId});

      }
      catch(Exception ex)
      {
        ex.printStackTrace();
        throw new Exception("the exception occured in getLocations() : ");
      }
      return locnMap;
    }


    public String getServiceProps(String srvsName) throws Exception
    {

	String srvsProps = "";
      try
      {
        Service  service = new Service();
          Call     call    = (Call) service.createCall();

          call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
          call.setOperationName( new QName("http://com.bsi.external.ws.BSIWebServiceIntf", "getServiceProperties") );
          call.addParameter("countryId", XMLType.XSD_STRING, ParameterMode.IN);
          //call.setReturnType( org.apache.axis.encoding.XMLType.XSD_STRING );
          call.setOperationStyle("rpc");
          call.setReturnClass(String.class);

       srvsProps = (String) call.invoke(new Object[] {srvsName});

      }
      catch(Exception ex)
      {
        ex.printStackTrace();
        throw new Exception("the exception occured in getServiceProps() : ");
      }
      return srvsProps;
    }


    public String getOptionsHTML(Map resultMap)
    {
	StringBuffer options = new StringBuffer();
	
	Iterator iter = resultMap.keySet().iterator();
	
	options.append("<option value=\"0\">Select All</option>");
	while (iter.hasNext())
	{
		String key = (String)iter.next();
		options.append("<option value=\"").append(key).append("\">").append(resultMap.get(key)).append("</option>");
	}
	
	System.out.println("The options html is \n" +  options.toString());
	
	return options.toString();
    }
    
    
    
    
    
%>