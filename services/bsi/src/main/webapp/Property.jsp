<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.bsi.common.beans.Person,
		 java.util.List,
		 java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<html:html xhtml="true" lang="true">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><bean:message key="property.add.title"/></title>
		<html:base/>
	</head>
	
	
	<script language="JavaScript">

	function addRow()
	{
	var datadiv = document.getElementById('propdata');
	var html = datadiv.innerHTML;
	var newRow = "<table width=\"90%\" border=\"1\"><tr>" +
					"<td width=\"12%\"><input type=\"text\" name=\"propertyId\" maxlength=\"50\" value=\"\" style=\"width:90%\" /></td>" +
					"<td width=\"12%\"><input type=\"text\" name=\"name\" maxlength=\"50\" value=\"\" style=\"width:90%\" /></td>" +
					"<td width=\"12%\"><input type=\"text\" name=\"description\" maxlength=\"50\" value=\"\" style=\"width:90%\"/></td>" +
					"<td width=\"12%\"><input type=\"text\" name=\"isMandatory\" maxlength=\"50\" value=\"\" style=\"width:90%\" /></td>" +
					"<td width=\"12%\"><input type=\"text\" name=\"displayType\" maxlength=\"50\" value=\"\" style=\"width:90%\" /></td>" +
					"<td width=\"12%\"><input type=\"text\" name=\"valueType\" maxlength=\"50\" value=\"\" style=\"width:90%\" /></td>" +
					"<td width=\"12%\"><input type=\"text\" name=\"tokenId\" maxlength=\"50\" value=\"\" style=\"width:90%\" /></td>" +
					"<td width=\"12%\"><input type=\"text\" name=\"displaySize\" maxlength=\"50\" value=\"\" style=\"width:90%\" /></td>" +
					"<td width=\"12%\"><input type=\"text\" name=\"dataSize\" maxlength=\"50\" value=\"\" style=\"width:90%\" /></td>" +
				"</tr></table>";

	if (datadiv == null) alert("Null");
	datadiv.innerHTML=html + newRow;
	}

	</script>
	<body>
		<html:form action="/AddProperty">
			<input type="hidden" name="serviceId" value="<%= request.getParameter("serviceId") %>"/>
	
			<table width="90%" border="1">
			
				<tr>
					<th width="12%">PropertyId</th>	
					<th width="12%">Name</th>	
					<th width="12%">Description</th>	
					<th width="12%">Is Mandatory</th>	
					<th width="12%">DisplayType</th>	
					<th width="12%">Value Type</th>	
					<th width="12%">Token Id</th>	
					<th width="12%">Display Size</th>	
					<th width="12%">Data Size</th>	
				</tr>
			</table>

		<logic:iterate name="PropertiesForm" property="properties" id="row" type="com.bsi.client.actions.forms.PropertyForm">

		<div id="propdata">
		
			<table width="90%" border="1">
				<tr> 
					<td width="12%"><html:text name="row"  property="propertyId" style="width:90%" maxlength="50"/></td>	
					<td width="12%"><html:text name="row"  property="name" style="width:90%" maxlength="50"/></td>	
					<td width="12%"><html:text name="row" property="description" style="width:90%" maxlength="50"/></td>	
					<td width="12%"><html:text name="row" property="isMandatory" style="width:90%" maxlength="50"/></td>	
					<td width="12%"><html:text name="row" property="displayType" style="width:90%" maxlength="50"/></td>	
					<td width="12%"><html:text name="row" property="valueType" style="width:90%" maxlength="50"/></td>	
					<td width="12%"><html:text name="row" property="tokenId"  style="width:90%" maxlength="50"/></td>	
					<td width="12%"><html:text name="row" property="displaySize" style="width:90%" maxlength="50"/></td>	
					<td width="12%"><html:text name="row" property="dataSize" style="width:90%" maxlength="50"/></td>	
				</tr>
			</table>
		</div>
		</logic:iterate>	

			<table width="90%" border="1">

				<tr>
					<td  colspan="4" align="right"><html:submit>Submit</html:submit><td>
					<td  colspan="4" align="right"><input type="button" onclick="addRow()"value="Add row" /><td>
				</tr>
			</table>
			
		</html:form>	
	</body>
</html:html>!