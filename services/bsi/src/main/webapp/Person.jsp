<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" 
    	 import="com.bsi.client.util.BSIConstants,
    	 	 org.apache.struts.action.ActionErrors,
    	 	 org.apache.struts.action.ActionMessages" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>




<html:html xhtml="true" lang="true">
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="0">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><bean:message key=CreateSupplier.title"/></title>
		<html:base/>
	</head>
	<script language="Javascript" src="js/ajax.js"></script>
	
	<script language="JavaScript">
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
			var selCountry = document.forms["PersonForm"].countryId[selCountryIndex].value;
			var statusDiv=document.getElementById('progressbar');
			var contentDiv=document.getElementById('states');
			sendHttpRequest('GetStates.do','countryId=' + selCountry,statusDiv,contentDiv);
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
			var selState = document.forms["PersonForm"].stateId[selStateIndex].value;
			var statusDiv=document.getElementById('progressbar');
			var contentDiv=document.getElementById('locations');
			sendHttpRequest('GetLocations.do','stateId=' + selState,statusDiv,contentDiv);
			locationsDiv.parentNode.parentNode.style.display = 'block';
			return;
		}
		
		function openSvsPopup(){
			
			var newwindow;
			newwindow=window.open("TreeView.jsp",'services','height=300,width=300');
			if (window.focus) {newwindow.focus()}

		}
		
		function loadProperties(){
			var index = document.forms["PersonForm"].<%= BSIConstants.SERVICE_ID %>.selectedIndex;
			var selSrvs = document.forms["PersonForm"].<%= BSIConstants.SERVICE_ID %>[index].value;
			alert("Selected service id is" + selSrvs);
			var statusDiv=document.getElementById('progressbar');
			var contentDiv=document.getElementById('properties');
			sendHttpRequest('GetProperties.do','serviceId='+ selSrvs,statusDiv,contentDiv);
		}
		
		

	</script>
	
	<body>

	<logic:messagesPresent>
		<p>Following errors occured:</p>
		<ul>
		 <html:messages id="error" property="<%=ActionErrors.GLOBAL_MESSAGE%>" bundle="errormsgs"> 
			 <li><bean:write name="error"/></li>
		 </html:messages> 
		</ul> 
	</logic:messagesPresent>
	

	<logic:messagesPresent message="true">
		 <html:messages id="msg" message="true" property="<%=ActionMessages.GLOBAL_MESSAGE%>"> 
			 <bean:write name="msg"/>
		 </html:messages> 
	</logic:messagesPresent>

	
	<!-- --------------------- FOR CREATE PERSON ACTION -------------------------------------------------- -->   

	<logic:equal name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.CREATE %>" scope="request">
	
		<html:form action="/AddSupplier" onsubmit="return validatePersonForm(this);">
			<input type="hidden" name="personType" value="<%= request.getParameter("type") %>"/>
			<table width="50%" border="0" align=top cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="AutoNumber1">
			  <tr>
			    <td width="25%"><bean:message key="CreateSupplier.name"/></td>
			    <td width="75%">
			      <html:text property="name" size="40" maxlength="50"/>
			     </td>
			  </tr>
			  <tr>
			    <td width="25%" valign=top ><bean:message key="CreateSupplier.address"/></td>
			    <td width="75%">
			      <html:text property="address" size="40" maxlength="50"/>
			  </td>
			  </tr>
			  <tr>
			    <td width="25%"><bean:message key="CreateSupplier.phoneNumber"/></td>
			    <td width="75%">
				<html:text property="phoneNumber" size="40" maxlength="50"/>
			    </td>
			  </tr>
			  <tr>
			    <td width="25%"><bean:message key="CreateSupplier.email"/></td>
			    <td width="75%">
				<html:text property="email" size="40" maxlength="50"/>
			    </td>
			  </tr>

			 <logic:present name="<%= BSIConstants.SERVICES_LIST %>">	  
				  <tr>
				    <td width="25%"><bean:message key="CreateSupplier.servicesOffered"/></td>
				    <td width="75%">
					<html:select multiple="true" size="3" property="<%= BSIConstants.SERVICE_ID %>" onchange="javascript:loadProperties()">
					    <html:options collection="ServicesList" 
					     		  property="nodeId"
							  labelProperty="name"/>
					</html:select>
					<input type="button" value="Browse" onclick="javascript:openSvsPopup();">
				    </td>
				  </tr>
		 	 </logic:present>
		
			 <logic:present name="<%= BSIConstants.COUNTRY_LIST %>">
				  <tr>
				    <td width="25%"><bean:message key="CreateSupplier.country"/></td>
				    <td width="75%">
					<html:select property="countryId" onchange="getStates(this)">
					    <html:option value=""><bean:message key="common.dropdown.select"/></html:option>
					    <html:options collection="<%= BSIConstants.COUNTRY_LIST %>" 
					    		  property="id"
					    		  labelProperty="name"/>
					</html:select>
				    </td>
				  </tr>
			 </logic:present>	  

			  <tr style="display:none">
			    <td width="25%"><bean:message key="CreateSupplier.state"/></td>
			    <td width="75%">
			    	<div id='states'></div>
			    </td>
			  </tr>
			  <tr style="display:none">
			    <td width="25%"><bean:message key="CreateSupplier.location"/></td>
			    <td width="75%">
			    	<div id='locations'></div>
			    </td>
			  </tr>
			  
		</table>
		<div id="properties">
		</div>
			
		<table>
		  <tr>
		    <td width="25%">&nbsp;</td>
		    <td width="75%">
		      <html:submit><bean:message key="CreateSupplier.button.submit"/></html:submit>
		      <html:reset/>
		      <html:cancel/>
		    </td>
		  </tr>
		</table>
		</html:form>
	</logic:equal>
	
	<!-- ------------------------------------------------------------------------------------------------- -->    

	<!-- --------------------- FOR EDIT PERSON ACTION ---------------------------------------------------- -->    
	
	<logic:equal name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.EDIT %>" scope="request">

		<html:form action="/AddSupplier" onsubmit="return validatePersonForm(this);">
			<table width=600 border="0" align=top cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="AutoNumber1">
			  <tr>
			    <td width="25%"><bean:message key="CreateSupplier.name"/></td>
			    <td width="75%">
			      <html:text property="name" size="40" maxlength="50"/>
			     </td>
			  </tr>
			  <tr>
			    <td width="25%" valign=top ><bean:message key="CreateSupplier.address"/></td>
			    <td width="75%">
			      <html:text property="address" size="40" maxlength="50"/>
			  </td>
			  </tr>
			  <tr>
			    <td width="25%"><bean:message key="CreateSupplier.phoneNumber"/></td>
			    <td width="75%">
				<html:text property="phoneNumber" size="40" maxlength="50"/>
			    </td>
			  </tr>
			  <tr>
			    <td width="25%"><bean:message key="CreateSupplier.email"/></td>
			    <td width="75%">
				<html:text property="email" size="40" maxlength="50"/>
			    </td>
			  </tr>

			 <logic:present name="<%= BSIConstants.SERVICES_LIST %>">	  
				  <tr>
				    <td width="25%"><bean:message key="CreateSupplier.servicesOffered"/></td>
				    <td width="75%">
					<html:select multiple="true" size="3" property="<%= BSIConstants.SERVICE_ID %>">
					    <html:options collection="ServicesList" 
					     		  property="nodeId"
							  labelProperty="name"/>
					</html:select>
				    </td>
				  </tr>
		 	 </logic:present>
		
			 <logic:present name="<%= BSIConstants.COUNTRY_LIST %>">
				  <tr>
				    <td width="25%"><bean:message key="CreateSupplier.country"/></td>
				    <td width="75%">
					<html:select property="countryId" onchange="getStates(this)">
					    <html:option value=""><bean:message key="common.dropdown.select"/></html:option>
					    <html:options collection="<%= BSIConstants.COUNTRY_LIST %>" 
					    		  property="id"
					    		  labelProperty="name"/>
					</html:select>
				    </td>
				  </tr>
			 </logic:present>	  
			 

			 <logic:present name="<%= BSIConstants.STATE_LIST %>">
				  <tr style="display:block"> 
				    <td width="25%"><bean:message key="CreateSupplier.state"/></td>
				    <td width="75%">
				    	<div id='states'>
						<html:select property="stateId" onchange="getStates(this)">
						    <html:option value=""><bean:message key="common.dropdown.select"/></html:option>
						    <html:options collection="<%= BSIConstants.STATE_LIST %>" 
								  property="id"
								  labelProperty="name"/>
						</html:select>
					</div>	
				    </td>
				  </tr>
			 </logic:present>	  

			 <logic:present name="<%= BSIConstants.REGION_LIST %>">
				  <tr style="display:block">
				    <td width="25%"><bean:message key="CreateSupplier.location"/></td>
				    <td width="75%">
				    	<div id='locations'>
						<html:select property="locationId" onchange="getStates(this)">
						    <html:option value=""><bean:message key="common.dropdown.select"/></html:option>
						    <html:options collection="<%= BSIConstants.REGION_LIST %>" 
								  property="id"
								  labelProperty="name"/>
						</html:select>
					</div>
				    </td>
				  </tr>
			 </logic:present>	  

			  <tr>
			    <td width="25%">&nbsp;</td>
			    <td width="75%">
			      <html:submit><bean:message key="CreateSupplier.button.submit"/></html:submit>
			      <html:reset/>
			      <html:cancel/>
			    </td>
			  </tr>
			</table>
		</html:form>
	</logic:equal>	
	
	<!-- ------------------------------------------------------------------------------------------------- -->    
	
	<table cellspacing=0 cellpadding=0 width="100%" height="5%">
	 <tr>
	   <td align="center">
		<div id="progressbar"></div>
	   </td>
	 </tr>
	</table>	
	
	<html:javascript formName="PersonForm"
			 dynamicJavascript="true"
			 staticJavascript="false"/>

	<script language="Javascript1.1" src="StaticJavascript.jsp"></script>		                 

	</body>
</html:html>
	
