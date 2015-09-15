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

		<script language="Javascript" src="js/locations.js"></script>

		<script language="JavaScript">

		function openSvsPopup(){

			var newwindow;
			newwindow=window.open("TreeView.jsp",'services','height=300,width=300');
			if (window.focus) {newwindow.focus()}
		}

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



		function loadProperties(){

			var selSrvs = <%= srvsId %>;
			if (selSrvs == null || selSrvs == '') return;
			var statusDiv=document.getElementById('progressbar');
			var contentDiv=document.getElementById('properties');
			sendHttpRequest('GetProperties.do','serviceId='+ selSrvs,statusDiv,contentDiv);
		}

		function verifyPassword(form){
			if (form.password.value != form.confirmPassword.value){
				alert("Password don't matach. Please check the password");
				form.password.value='';
				form.confirmPassword.value='';
			}
		}
		
		function validateRequiredInputs(form){
			if (!validateBuyerForm(form)){
				return false;
			}
			if (form.password.value == ''){
				alert("Please ener password");
				return false;
			}
			if (form.serviceIdName.value == ''){
				alert("Please select valid service");
				return false;
			}
			if (form.regionId1.value == ''){
				alert("Please select the region.");
				return false;
			}
			
			return;
		}


	</script>

		<body onload="loadProperties()">


			<%@ include file="include/ServerMessages.jsp"%>

			<!-- --------------------- FOR CREATE PERSON ACTION -------------------------------------------------- -->

			<logic:equal name="<%= BSIConstants.ACTION_TYPE %>"
				value="<%= BSIConstants.CREATE %>" scope="request">

				<html:form action="/CreateBuyer"
					onsubmit="return validateRequiredInputs(this);">
					<html:hidden property="personType" value="B" />
					<html:hidden property="id" />
					<table width="45%" border="0" cellpadding="10" align=top
						cellspacing="0">
						<tr>
							<td class="screen_title"><u><bean:message
										key="Buyer.register.title" /></u></td>

						</tr>
						<tr>
							<td>
								<fieldset>
									<legend>
										<bean:message key="Legend.user.info" />
									</legend>

									<table border="0" align=top cellpadding="0" cellspacing="10">
										<tr>
											<td width="25%"><bean:message key="Buyer.name" /></td>
											<td width="75%"><html:text property="name" size="40"
													maxlength="50" /><font class="mandatory">*</font></td>
										</tr>
										<tr>
											<td width="25%" valign=top><bean:message
													key="Buyer.address" /></td>
											<td width="75%"><html:text property="address" size="40"
													maxlength="100" /><font class="mandatory">*</font></td>
										</tr>
										<tr>
											<td width="25%"><bean:message key="Buyer.phoneNumber" /></td>
											<td width="75%"><html:text property="phoneNumber"
													size="40" maxlength="50" /><font class="mandatory">*</font>
											</td>
										</tr>
										<tr>
											<td width="25%"><bean:message key="Buyer.email" /></td>
											<td width="75%"><html:text property="email" size="40"
													maxlength="50" /><font class="mandatory">*</font></td>
										</tr>
										<!-- tr>
											<td width="25%"></td>
											<td width="75%"><input type="checkbox" name="remember"
												onClick="onRememberMe()" />
											<bean:message key="Buyer.rememberme" /></td>
										</tr -->
										<tr>
											<td colspan="2">
											<table width="100%" id='rememberme' style="display: block">
												<tr>
													<td width="25%"><bean:message key="Buyer.password" /></td>
													<td width="75%">
														<!-- html:text property="password" value="" redisplay="false"/>  -->
														<html:password property="password" value="" />
													</td>
												</tr>
												<tr>
													<td width="25%"><bean:message
															key="Buyer.confirmPassword" /></td>
													<td width="75%"><html:password
															property="confirmPassword" value=""
															onchange="verifyPassword(this.form)" /></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>

						<tr>
							<td>
								<fieldset>
									<legend>
										<bean:message key="Legend.service.required" />
									</legend>
									<table border="0" align=top cellpadding="0" cellspacing="10">
										<!-- logic:present name="<%= BSIConstants.SERVICES_LIST %>" -->
										<tr>
											<td width="25%"><bean:message
													key="Buyer.serviceRequested" /></td>
											<td width="75%"><html:hidden
													property="<%= BSIConstants.SERVICE_ID %>" /> <!-- input type="hidden" name="serviceIds" value=""/ -->
												<html:text property="serviceIdName" size="40"
													readonly="true" maxlength="50" /> <input type="button"
												value="Browse" onclick="javascript:openSvsPopup();"><font
													class="mandatory">*</font></td>
										</tr>
										<!-- /logic:present -->
									</table>
						<div id="properties"></div>
						</fieldset>
						</td>
						</tr>
						<tr>
							<td>
								<fieldset>
									<legend>
										<bean:message key="Legend.service.required.locaton" />
									</legend>
									<table width="50%" border="0" align=top cellpadding="0"
										cellspacing="10">
										<%@ include file="include/Location.jsp"%>
									</table>
								</fieldset>
							</td>
						</tr>

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


			<!-- ------------------------------------------------------------------------------------------------- -->

			<!-- --------------------- FOR EDIT PERSON ACTION ---------------------------------------------------- -->

			<logic:equal name="<%= BSIConstants.ACTION_TYPE %>"
				value="<%= BSIConstants.EDIT %>" scope="request">

				<html:form action="/UpdatePersonInfoBuyer"
					onsubmit="return validatePersonForm(this);">
					<html:hidden property="id" />
					<html:hidden property="personType" />
					<html:hidden property="password" />

					<table width="70%" border="0" cellpadding="10" align=top
						cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<fieldset>
									<table width="100%" border="0" cellpadding="10" align=top
										cellpadding="0" cellspacing="0">

										<tr>
											<td width="25%"><bean:message key="Buyer.name" /></td>
											<td width="75%"><html:text property="name"
													readonly="true" size="40" maxlength="50" /></td>
										</tr>
										<tr>
											<td width="25%" valign=top><bean:message
													key="Buyer.address" /></td>
											<td width="75%"><html:text property="address" size="40"
													maxlength="50" /></td>
										</tr>
										<tr>
											<td width="25%"><bean:message key="Buyer.phoneNumber" /></td>
											<td width="75%"><html:text property="phoneNumber"
													size="40" maxlength="50" /></td>
										</tr>
										<tr>
											<td width="25%"><bean:message key="Buyer.email" /></td>
											<td width="75%"><html:text property="email" size="40"
													maxlength="50" /></td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
						<tr>
							<td>

								<fieldset>
									<table width="100%" border="0" cellpadding="10" align=top
										cellpadding="0" cellspacing="0">
										<tr>
											<td align="right"><html:submit>
													<bean:message key="button.submit" />
												</html:submit></td>
											<td align="left"><html:cancel /></td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</html:form>
			</logic:equal>

			<!-- ------------------------------------------------------------------------------------------------- -->

			<!-- --------------------- FOR VIEW PERSON ACTION ---------------------------------------------------- -->

			<logic:equal name="<%= BSIConstants.ACTION_TYPE %>"
				value="<%= BSIConstants.VIEW %>" scope="request">

				<html:form action="/CreateBuyer"
					onsubmit="return validateBuyerForm(this);">
					<table width=600 border="0" align=top cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="25%"><bean:message key="Buyer.name" /></td>
							<td width="75%"><html:text property="name" readonly="true"
									size="40" maxlength="50" /></td>
						</tr>
						<tr>
							<td width="25%" valign=top><bean:message
									key="Buyer.address" /></td>
							<td width="75%"><html:text property="address"
									readonly="true" size="40" maxlength="50" /></td>
						</tr>
						<tr>
							<td width="25%"><bean:message key="Buyer.phoneNumber" /></td>
							<td width="75%"><html:text property="phoneNumber"
									readonly="true" size="40" maxlength="50" /></td>
						</tr>
						<tr>
							<td width="25%"><bean:message key="Buyer.email" /></td>
							<td width="75%"><html:text property="email" readonly="true"
									size="40" maxlength="50" /></td>
						</tr>

					</table>
				</html:form>
			</logic:equal>

			<!-- ------------------------------------------------------------------------------------------------- -->


			<%@ include file="include/ProgressBar.jsp"%>

			<html:javascript formName="BuyerForm" dynamicJavascript="true"
				staticJavascript="false" />

			<html:javascript formName="PersonForm" dynamicJavascript="true"
				staticJavascript="false" />

			<script language="Javascript1.1" src="StaticJavascript.jsp"></script>

		</body>
</html:html>

