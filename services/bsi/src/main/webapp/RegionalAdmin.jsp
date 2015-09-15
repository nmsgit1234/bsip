<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    	 import="com.bsi.client.util.BSIConstants,
    	 	 org.apache.struts.action.ActionErrors,
    	 	 org.apache.struts.action.ActionMessages" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>




<html:html xhtml="true" lang="true">
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="0">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><bean:message key="RegAdmin.title"/></title>
		<html:base/>
	</head>

	<style type="text/css" media="all">
	      @import url("css/bsi.css");
	</style>

	<script language="Javascript" src="js/ajax.js"></script>
	<script language="Javascript" src="js/locations.js"></script>


	<body>

	<%@ include file="include/ServerMessages.jsp" %>


	<!-- --------------------- FOR CREATE Regional Admin ACTION -------------------------------------------------- -->

	<c:if test="${param.Action_type == 'create'}">

	<html:form action="/CreatePerson" onsubmit="return validatePersonForm(this);">
		<input type="hidden" name="personType" value="RA"/>
		<table width="70%" border="0" cellpadding="10" align=top  cellspacing="0">
			<tr>
				<td class="screen_title"><u><bean:message key="RegAdmin.register.title"/></u>
				</td>
			</tr>

			<tr>
				<td>
					<fieldset>
						 <legend><bean:message key="Legend.user.info"/></legend>
						<table width="100%" border="0" align=top cellpadding="0" cellspacing="10">
						  <tr>
						    <td width="25%"><bean:message key="RegAdmin.name"/></td>
						    <td width="75%">
						      <html:text property="name" size="40" maxlength="50"/>
						     </td>
						  </tr>
						  <tr>
						    <td width="25%" valign=top ><bean:message key="RegAdmin.address"/></td>
						    <td width="75%">
						      <html:text property="address" size="40" maxlength="50"/>
						  </td>
						  </tr>
						  <tr>
						    <td width="25%"><bean:message key="RegAdmin.phoneNumber"/></td>
						    <td width="75%">
							<html:text property="phoneNumber" size="40" maxlength="50"/>
						    </td>
						  </tr>
						  <tr>
						    <td width="25%"><bean:message key="RegAdmin.email"/></td>
						    <td width="75%">
							<html:text property="email" size="40" maxlength="50"/>
						    </td>
						  </tr>
						  <tr>
						    <td width="25%"><bean:message key="RegAdmin.password"/></td>
						    <td width="75%">
							<html:password property="password" size="20" maxlength="10"/>
						    </td>
						  </tr>
						</table>
						</legend>
					</fieldset>
				</td>
			</tr>
			
			<tr>
				<td>
					<fieldset>
						<table width="100%" border="0" align=top cellpadding="0" cellspacing="10">
							<tr>
								<td align="right">
								<html:submit><bean:message key="button.submit"/></html:submit>
								</td>
								<td align="center">
									<html:reset/>
								</td>

								<td align="left">
									<html:cancel/>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</html:form>
	</c:if>

	<!-- ------------------------------------------------------------------------------------------------- -->

	<!-- --------------------- FOR EDIT Regional Admin ACTION ---------------------------------------------------- -->

	<c:if test="${param.Action_type == 'edit'}">
	<html:form action="/UpdatePersonInfoRegionalAdmin" onsubmit="return validatePersonForm(this);">
            <html:hidden property="id"/>
            <html:hidden property="personType"/>
            <html:hidden property="password"/>
            <table width="70%" border="0" cellpadding="10" align=top  cellspacing="0">
			<tr>
				<td>
					<fieldset>
 						 <legend><bean:message key="Legend.user.info"/></legend>
  						 <table width="100%" border="0" align=top cellpadding="0" cellspacing="10">
							  <tr>
							    <td width="25%"><bean:message key="RegAdmin.name"/></td>
							    <td width="75%">
							      <html:text property="name" size="40" maxlength="50"/>
							     </td>
							  </tr>
							  <tr>
							    <td width="25%" valign=top ><bean:message key="RegAdmin.address"/></td>
							    <td width="75%">
							      <html:text property="address" size="40" maxlength="50"/>
							  </td>
							  </tr>
							  <tr>
							    <td width="25%"><bean:message key="RegAdmin.phoneNumber"/></td>
							    <td width="75%">
								<html:text property="phoneNumber" size="40" maxlength="50"/>
							    </td>
							  </tr>
							  <tr>
							    <td width="25%"><bean:message key="RegAdmin.email"/></td>
							    <td width="75%">
								<html:text property="email" size="40" maxlength="50"/>
							    </td>
							  </tr>
						</table>
						</legend>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td>
					<fieldset>
						<table width="100%" border="0" align=top cellpadding="0" cellspacing="10">
							<tr>
								<td align="right">
									<html:submit><bean:message key="button.submit"/></html:submit>
								</td>

								<td align="left">
									<html:cancel/>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</html:form>

	</c:if>

	<!-- ------------------------------------------------------------------------------------------------- -->
	
	<%@ include file="include/ProgressBar.jsp" %>

	<html:javascript formName="PersonForm"
		 dynamicJavascript="true"
		 staticJavascript="false"/>

	<script language="Javascript1.1" src="StaticJavascript.jsp"></script>

	</body>
</html:html>

