
<%@ page language="java" contentType="text/html; charset=utf-8"
	 import="org.apache.axis.client.Call,
		org.apache.axis.client.Service,
		org.apache.axis.encoding.XMLType,
		org.apache.axis.utils.Options,
		javax.xml.namespace.QName,
		javax.xml.rpc.ParameterMode,
		java.util.HashMap,
		java.util.Map,
		java.util.Iterator" %>


<%
	Map countryMap = null;

	try
	{
            Service  service = new Service();
            Call     call    = (Call) service.createCall();

            call.setTargetEndpointAddress( new java.net.URL("http://localhost:7001/bsi/services/BSIWebService") );
            call.setOperationName( new QName("http://com.bsi.external.ws.BSIWebServiceIntf", "getCountries") );
            //call.addParameter( "arg1", XMLType.XSD_STRING, ParameterMode.IN);
            //call.setReturnType( org.apache.axis.encoding.XMLType.XSD_STRING );
            call.setOperationStyle("rpc");
            call.setReturnClass(Map.class);

            countryMap = (Map) call.invoke( new Object[] { } );

            System.out.println("The Countries are ****** \n");
         }
         catch(Exception ex)
         {
         	//System.setErr(out);
         	ex.printStackTrace();
%>

		out.println("An acco")

<%
         }

%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en-GB" xml:lang="en-GB">
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="-1">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Create Service requestor</title>
		<base href="http://localhost:7001/bsi/Buyer.jsp" />
	</head>

	<style type="text/css" media="all">
	      @import url("css/bsi.css");
	</style>


	<script language="Javascript" src="js/ajax.js"></script>

	<script language="JavaScript">


		function onRememberMe()
		{
            		var isSelected = document.forms[0].remember.checked;
			var displayDiv = document.getElementById("rememberme");
			var passwdDiv = document.getElementById("password");
			if (isSelected)
			{
				displayDiv.style.display = 'block';
			}
			else
			{
				passwdDiv.value="";
				displayDiv.style.display = 'none';
			}
			return;
		}
		
		function getStates(which)
		{
			//Set the states and location div to null
			var stateDiv= document.getElementById('states');
			if (stateDiv != null)
			{
				stateDiv.innerHTML="";
				stateDiv.parentNode.parentNode.style.display = 'none';

			}
			var locationsDiv= document.getElementById('locations');
			if (locationsDiv != null)
			{
				locationsDiv.innerHTML="";
				locationsDiv.parentNode.parentNode.style.display = 'none';

			}
			var selCountryIndex = which.selectedIndex;
			if (selCountryIndex == 0) return;
			var selCountry = document.forms[0].countryId[selCountryIndex].value;

			var statusDiv=document.getElementById('progressbar');
			var contentDiv=document.getElementById('states');
			sendHttpRequest('CallWebService.jsp','action=getStates&countryId=' + selCountry,statusDiv,contentDiv);

			stateDiv.parentNode.parentNode.style.display = 'block';
			return;
		}

		function getLocations(which)
		{
			var locationsDiv= document.getElementById('locations');
			if (locationsDiv != null)
			{
				locationsDiv.innerHTML="";
				locationsDiv.parentNode.parentNode.style.display = 'none';
			}
			var selStateIndex = which.selectedIndex;
			if (selStateIndex == 0) return;
			var selState = document.forms[0].stateId[selStateIndex].value;
			var statusDiv=document.getElementById('progressbar');
			var contentDiv=document.getElementById('locations');
			sendHttpRequest('CallWebService.jsp','action=getLocations&stateId=' + selState,statusDiv,contentDiv);
			locationsDiv.parentNode.parentNode.style.display = 'block';
			return;
		}

		function getServiceProps(srvsName)
		{
			alert("In getServiceProps ");
			var propertyDiv= document.getElementById('properties');
			if (propertyDiv != null)
			{
				propertyDiv.innerHTML="";
			}
			var statusDiv=document.getElementById('progressbar');
			sendHttpRequest('CallWebService.jsp','action=getServiceProperties&srvsName=' + escape(srvsName),statusDiv,propertyDiv);
			return;
		}


	</script>

	<body onLoad="getServiceProps('Maths tuition')">


	<table width="70%" border="0" cellpadding="10" align=top cellpadding="0" cellspacing="0">
	<tr>

	</tr>
</table>


		<form id="BuyerForm" method="post" action="/bsi/CreateBuyer.do" onsubmit="return validateBuyerForm(this);" >
                        <input type="hidden" name="personType" value="B" />

			<table width="70%" border="0" cellpadding="10" align=top  cellspacing="0">
				<tr>
					<td class="screen_title"><u>Service requestor registration</u>
					</td>
				</tr>
				<tr>
					<td>
						<fieldset>
							 <legend>Tell us about yourself</legend>

							<table width="100%" border="0" align=top cellpadding="0" cellspacing="10">
							  <tr>
							    <td width="25%">Name</td>
							    <td width="75%">
							      <input type="text" name="name" maxlength="50" size="40" value="" /><font class="mandatory">*</font>
							     </td>
							  </tr>
							  <tr>
							    <td width="25%" valign=top >Address</td>
							    <td width="75%">
							      <input type="text" name="address" maxlength="100" size="40" value="" /><font class="mandatory">*</font>
							  </td>
							  </tr>
							  <tr>
							    <td width="25%">Phone number</td>
							    <td width="75%">
								<input type="text" name="phoneNumber" maxlength="50" size="40" value="" />
							    </td>
							  </tr>
							  <tr>
							    <td width="25%">Email</td>
							    <td width="75%">
								<input type="text" name="email" maxlength="50" size="40" value="" /><font class="mandatory">*</font>
							    </td>
							  </tr>
							  <tr>
							    <td width="25%"></td>
							    <td width="75%">
								<input type="checkbox" name="remember"  onClick="onRememberMe()"/>Remember me
							    </td>
							  </tr>
							   <tr id='rememberme' style="display:none">
                                                            <td width="25%">Password</td>
                                                            <td width="75%">
                                                            <input type="text" name="password" value="" />
                                                            </td>
							  </tr>
							</table>
						</fieldset>
					</td>
				</tr>

				<tr>
					<td>
						<div id="properties">
						</div>
					</td>
				</tr>
				<tr>

					<td>
						<fieldset>
							 <legend>About service location</legend>
							<table width="100%" border="0" align=top cellpadding="0" cellspacing="10">

	  <tr>
	    <td width="25%">Country</td>
	    <td width="75%">
		<select name="countryId" onchange="getStates(this)">
		
<%

	if (countryMap != null)
	{

%>

		<option value="0" selected="selected">Select All</option>
<%
	    Iterator iter = countryMap.keySet().iterator();
            while (iter.hasNext())
            {
              String key = (String)iter.next();
              System.out.println("The key is " + key + ", the value is " + countryMap.get(key));
              
%>
		<option value="<%= key %>"><%= countryMap.get(key) %></option>
<%
            }	
	}
%>

	    </td>
	  </tr>



	<tr style="display:none">
		<td width="25%">State</td>
		<td width="75%">
			<div id='states'></div>
		</td>
	</tr>
	<tr style="display:none">
		<td width="25%">Location</td>
		<td width="75%">
			<div id='locations'></div>
		</td>
	</tr>

							</table>
						</fieldset>
					</td>
				</tr>

				<tr>
					<td>
						<fieldset>
							<table width="100%" border="0" align=top cellpadding="0" cellspacing="10">
                                                          <tr>
								<td align="right">
								      <input type="submit" value="Submit" />
								</td>
								<td align="center">
								      <input type="reset" value="Reset" />
								</td>
								<td align="left">
								      <input type="submit" name="org.apache.struts.taglib.html.CANCEL" value="Cancel" onclick="bCancel=true;" />
								</td>
                                                          </tr>
							</table>
						</fieldset>
					</td
				</tr>
			</table>
		</form>

	</body>
</html>

