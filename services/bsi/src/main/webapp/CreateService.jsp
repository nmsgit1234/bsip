<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
	import="com.bsi.client.util.BSIConstants, com.bsi.common.beans.Region,
    	 	 org.apache.struts.action.ActionErrors,
    	 	 org.apache.struts.action.ActionMessages"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<jsp:include flush="true" page="include/SessionCheck.jsp" />

<%
	String srvsId = request.getParameter(BSIConstants.SERVICE_ID);
%>


<html:html xhtml="true" lang="true">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="-1">
		<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><bean:message key="Buyer.title" /></title>
<html:base />
		</head>

		<style type="text/css" media="all">
@import url("css/bsi.css");
</style>
		<script language="Javascript" src="js/ajax.js"></script>
		<script language="JavaScript">

		function openSvsPopup(){

			var newwindow;
			newwindow=window.open("TreeView.jsp?Action_type=parentProperties",'services','height=300,width=300');
			if (window.focus) {newwindow.focus()}
		}

		function loadProperties(){
			var selSrvs = <%= srvsId %>;
			if (selSrvs == null || selSrvs == '') return;
			var statusDiv=document.getElementById('progressbar');
			var contentDiv=document.getElementById('properties');
			sendHttpRequest('GeParentProperties.do','serviceId='+ selSrvs,statusDiv,contentDiv);
		}

		function deleteRow(tableID) {
			try {
				var table = document.getElementById(tableID);
				var rowCount = table.rows.length;
				for (var i = 0; i < rowCount; i++) {
					var row = table.rows[i];
					var chkbox = row.cells[0].childNodes[0];
					if (null != chkbox && true == chkbox.checked) {
						table.deleteRow(i);
						rowCount--;
						i--;
					}
				}
			} catch (e) {
				alert(e);
			}
		}
		
		function addRowInnerHTML()
		{
		  var tblBody = document.getElementById('dataTable').tBodies[0];
		  var newRow = tblBody.insertRow(-1);
		  var newCell0 = newRow.insertCell(0);
		  newCell0.innerHTML = "<input type=\"checkbox\" nam=\"selected\" maxlength=\"50\" value=\"\" style=\"width:90%\" />";
		  var newCell1 = newRow.insertCell(1);
		  newCell1.innerHTML = "<input type=\"text\" name=\"com.bsi.common.beans.Property:name,propertiesSet\" maxlength=\"50\" value=\"\" style=\"width:90%\" />";
		  var newCell2 = newRow.insertCell(2);
		  newCell2.innerHTML = "<input type=\"text\" name=\"com.bsi.common.beans.Property:description,propertiesSet\" maxlength=\"50\" value=\"\" style=\"width:90%\"/>";
		  var newCell3 = newRow.insertCell(3);
		  //newCell3.innerHTML = "<input type=\"text\" name=\"com.bsi.common.beans.Property:isMandatory,propertiesSet\" maxlength=\"50\" value=\"\" style=\"width:90%\" />";

		newCell3.innerHTML = "<td width=\"12%\" style=\"width:90%\">"+
			"<select name=\"com.bsi.common.beans.Property:isMandatory,propertiesSet\">"+
				"<option value=\"Y\" selected>Yes</option>"+
				"<option value=\"N\">No</option></select></td>";
		  
		  
		  
		  var newCell4 = newRow.insertCell(4);
		  //newCell4.innerHTML = "<input type=\"text\" name=\"com.bsi.common.beans.Property:displayType,propertiesSet\" maxlength=\"50\" value=\"\" style=\"width:90%\" />";

		  
		  newCell4.innerHTML = "<td width=\"12%\" style=\"width:90%\"><select name=\"com.bsi.common.beans.Property:displayType,propertiesSet\">" +
		  "<option value=\"DD\" selected>Drop Down</option>"+
		  //"<option value=\"TF\">Text Field</option>"+
		  "<option value=\"MS\" >Multi Select</option></select></td>";
		  
		  
		  
		  var newCell5 = newRow.insertCell(5);
		  //newCell5.innerHTML = "<input type=\"text\" name=\"com.bsi.common.beans.Property:valueType,propertiesSet\" maxlength=\"50\" value=\"\" style=\"width:90%\" />";
		  
		  
		newCell5.innerHTML = "<td width=\"12%\" style=\"width:90%\">"+
			"<select name=\"com.bsi.common.beans.Property:valueType,propertiesSet\">" +
				"<option value=\"String\" selected>String</option>"+
				"<option value=\"Number\">Number</option></select></td>";
		  
		  var newCell6 = newRow.insertCell(6);
		  newCell6.innerHTML = "<input type=\"text\" name=\"com.bsi.common.beans.Property:tokenId,propertiesSet\" maxlength=\"50\" value=\"\" style=\"width:90%\" />";
		  var newCell7 = newRow.insertCell(7);
		  newCell7.innerHTML = "<input type=\"text\" name=\"com.bsi.common.beans.Property:displaySize,propertiesSet\" maxlength=\"50\" value=\"\" style=\"width:90%\" />";
		  var newCell8 = newRow.insertCell(8);
		  newCell8.innerHTML = "<input type=\"text\" name=\"com.bsi.common.beans.Property:dataSize,propertiesSet\" maxlength=\"50\" value=\"\" style=\"width:90%\" />";
		}
		

		function validateRequiredInputs(form){
			if (!validateServiceForm(form)){
				return false;
			}
			
			var tokenValue = document.getElementsByName('com.bsi.common.beans.Property:name,propertiesSet');
			var size = tokenValue.length;
 			for ( var i = 0; i < size; i++) {
				if (tokenValue[i].value == ''
						|| tokenValue[i].value.trim().length == 0) {
					alert("Please enter property details.");
					return false;
				}
			}
 			//Alert if the DD is selected for drop down and token id is not set.
			var tokenId =  document.getElementsByName("com.bsi.common.beans.Property:tokenId,propertiesSet")[0].value;
 			var displayTypeElements = document.getElementsByName("com.bsi.common.beans.Property:displayType,propertiesSet");
			var displayType = displayTypeElements[0].value;
 			if ((displayType == "DD" || displayType == "MS") && tokenId.length == 0){
 				alert("Please select the token id.");
 				return false;
 			} 
 			return true;
		}
	</script>

		<body onload="loadProperties()">


			<%@ include file="include/ServerMessages.jsp"%>

			<!-- --------------------- FOR CREATE SERVICE ACTION -------------------------------------------------- -->

			<logic:equal name="<%= BSIConstants.ACTION_TYPE %>"
				value="<%= BSIConstants.CREATE %>" scope="request">

				<html:form action="/CreateService"
					onsubmit="return validateRequiredInputs(this);">
					<html:hidden property="personType" value="B" />
					<html:hidden property="<%= BSIConstants.ENTITY_NAME %>" value="com.bsi.common.beans.Service" />
					<!-- html:hidden property="id" / -->
					<table width="90%" border="0" cellpadding="10" align=top
						cellspacing="0">
						<tr>
							<td class="screen_title"><u><bean:message
										key="service.create.title" /></u></td>

						</tr>
						<tr>
							<td>
								<fieldset>
									<legend>
										<bean:message key="service.create.legend" />
									</legend>
									<table border="0" align=top cellpadding="0" cellspacing="10">
										<!-- logic:present name="<%= BSIConstants.SERVICES_LIST %>" -->
										<html:hidden property="prntNodeId" value="" />
										<tr>
											<td width="25%"><bean:message
													key="service.parent.name" /></td>
											<td width="75%"><html:hidden
													property="<%= BSIConstants.SERVICE_ID %>" /> <!-- input type="hidden" name="serviceIds" value=""/ -->
												<html:text property="serviceIdName" size="40"
													readonly="true" maxlength="50" /> <input type="button"
												value="Browse" onclick="javascript:openSvsPopup();"><font
													class="mandatory">*</font></td>
										</tr>
										<tr>
											<td width="25%"><bean:message
													key="service.child.name" /></td>
											<td width="75%">
												<html:text property="name" size="40"
													maxlength="50" /><font
													class="mandatory">*</font></td>
										</tr>
										<tr>
											<td width="25%"><bean:message
													key="service.child.description" /></td>
											<td width="75%">
												<html:text property="description" size="40"
													maxlength="50" /> <font
													class="mandatory">*</font></td>
										</tr>
								</table>
							</fieldset>
							</td>
							<tr>
						<tr>
							<td>
								<fieldset>
									<legend>
										<bean:message key="service.parent.properties" />
									</legend>
															</fieldset>
									<div id="properties"></div>
							</td>
						</tr>										

						<tr>
							<td>
								<fieldset>
									<legend>
										<bean:message key="service.child.properties" />
									</legend>
									<input type="button" onclick="addRowInnerHTML()"value="Add" />
									<INPUT type="button" value="Delete" onclick="deleteRow('dataTable')" />
									<div id="childProperties">
									<table id="dataTable" width="100%" border="1">
									
										<tr>
											<th></th>	
											<th>Name</th>	
											<th>Description</th>	
											<th>Is Mandatory</th>	
											<th>DisplayType</th>	
											<th>Value Type</th>	
											<th><a href="ListObjects.do?entityName=Token" target="_blank">Tokens</a></th>	
											<th>Display Size</th>	
											<th>Data Size</th>	
										</tr>
 										<tr>
											<td width="2%"><input type="checkbox" name="selected" value=""  /></td>
											<td width="20%"><input type="text" name="com.bsi.common.beans.Property:name,propertiesSet" maxlength="50" value="" style="width:90%" /></td>
											<td width="20%"><input type="text" name="com.bsi.common.beans.Property:description,propertiesSet" maxlength="50" value="" style="width:90%"/></td>
											<td width="8%">
												<select name="com.bsi.common.beans.Property:isMandatory,propertiesSet">
													<option value="Y" selected><bean:message key="common.dropdown.yes"/></option>
													<option value="N"><bean:message key="common.dropdown.no"/></option>
												</select>											
											</td>
											<td width="12%">
												<select name="com.bsi.common.beans.Property:displayType,propertiesSet">
													<option value="DD" selected><bean:message key="common.dropdown.displaytype.dropdown"/></option>
													<!-- option value="TF" selected><bean:message key="common.dropdown.displaytype.textfield"/></option -->
													<option value="MS"><bean:message key="common.dropdown.displaytype.multiselect"/></option>
												</select>											
											</td>
											
											
											<td width="12%">
												<select name="com.bsi.common.beans.Property:valueType,propertiesSet">
													<option value="String" selected><bean:message key="common.dropdown.valuetype.string"/></option>
													<option value="Number"><bean:message key="common.dropdown.valuetype.number"/></option>
												</select>											
											</td>
											<td width="12%"><input type="text" name="com.bsi.common.beans.Property:tokenId,propertiesSet" maxlength="50" value="" style="width:90%" /></td>
											<td width="12%"><input type="text" name="com.bsi.common.beans.Property:displaySize,propertiesSet" maxlength="50" value="" style="width:90%" /></td>
											<td width="12%"><input type="text" name="com.bsi.common.beans.Property:dataSize,propertiesSet" maxlength="50" value="" style="width:90%" /></td>
										</tr>
									</table>
									</div>
									</fieldset>
							</td>
						</tr>										

										
										<!-- /logic:present -->
						<tr>
							<td>
								<fieldset>
									<table width="50%" border="0" align=top cellpadding="0"
										cellspacing="10">
										<tr>
											<td align="right"><html:submit>
													<bean:message key="button.submit" />
												</html:submit></td>
											<td align="center"><html:reset /></td>
											<td align="left"><html:cancel /></td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</html:form>

			</logic:equal>

			<%@ include file="include/ProgressBar.jsp"%>
			<html:javascript formName="ServiceForm" dynamicJavascript="true" staticJavascript="false" />
			

			<script language="Javascript1.1" src="StaticJavascript.jsp"></script>

		</body>
</html:html>

