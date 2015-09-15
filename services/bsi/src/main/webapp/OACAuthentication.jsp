<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
	import="com.bsi.client.util.BSIConstants,com.bsi.common.beans.Person,org.apache.struts.action.ActionErrors,org.apache.struts.action.ActionMessages"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>




<html:html xhtml="true" lang="true">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="0">
		<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><bean:message key="Supplier.title" /></title>
<html:base />
		</head>

		<style type="text/css" media="all">
@import url("css/bsi.css");
</style>

		<script language="Javascript" src="js/ajax.js"></script>
		<script language="Javascript" src="js/locations.js"></script>

		<script language="Javascript">
			function reSendOAC() {
				alert("Resending OAC");
				var form = document.forms[0];
				form.action = 'SendOAC.do';
				form.submit();
			}
		</script>
		<body>

			<%@ include file="include/ServerMessages.jsp"%>


			<html:form action="/ConfirmOAC" onsubmit="return validateOACValuesForm(this);">
				<html:hidden property="<%=BSIConstants.ENTITY_NAME%>"
					value="com.bsi.common.beans.Person" />
				<html:hidden property="id"
					value="<%=((Person) request.getSession().getAttribute(
							BSIConstants.CREATED_ENTITIY)).getId().toString()%>" />

				</html>


				<table width="50%" border="0" cellpadding="10" align=top
					cellspacing="0">
					<tr>
						<td class="screen_title"><u><bean:message
									key="OAC.confirm.title" /></u></td>
					</tr>

					<tr>
						<td>
							<table width="100%" border="0" align=top cellpadding="0"
								cellspacing="10">
								<tr>
									<td width="100%"><bean:message key="OAC.enter.message" />
									</td>
								</tr>
								<tr>
									<td align="center" width="100%"><html:text
											property="oacNumber" size="10" maxlength="10" /></td>
								</tr>

							</table>
						</td>
					</tr>

					<tr>
						<td>
							<fieldset>
								<table width="100%" border="0" align=top cellpadding="0"
									cellspacing="10">
									<tr>
										<td align="right"><html:submit>
												<bean:message key="button.submit" />
											</html:submit></td>
										<td align="left"><html:button property="resendoac"
												onclick="javascript:reSendOAC();">
												<bean:message key="button.resend.oac" />
											</html:button></td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
				</table>
			</html:form>

			<html:javascript formName="OACValuesForm" dynamicJavascript="true"
				staticJavascript="false" />

			<script language="Javascript1.1" src="StaticJavascript.jsp"></script>

		</body>
</html:html>

