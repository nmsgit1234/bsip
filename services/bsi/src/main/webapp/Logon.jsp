<%@ page contentType="text/html;charset=UTF-8" language="java"
    	 import="org.apache.struts.action.ActionErrors,
    	 	 org.apache.struts.action.ActionMessages" %>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<html:xhtml/>
<html>
<head>
    <title><bean:message key="Logon.title"/></title>
</head>

<body>

<%@ include file="include/ServerMessages.jsp" %>

<html:errors/>

<html:form action="/SubmitLogon" focus="email"
           onsubmit="return validateLogonForm(this);" target="_top">

<table width="40%" border="0" cellpadding="10" align=top  cellspacing="0">
	<tr>
		<td>
		<fieldset>
			 <legend><bean:message key="Legend.login"/></legend>

			<table width="100%" border="0" align=top cellpadding="0" cellspacing="10">

				<tr>
					<th align="right">
					<bean:message key="Logon.username"/>:
					</th>
					<td align="left">
					<html:text property="email" size="16" maxlength="50"/>
					</td>
				</tr>

				<tr>
					<th align="right">
					<bean:message key="Logon.password"/>:
					</th>
					<td align="left">
					<html:password property="password" size="16" maxlength="18"
					redisplay="false"/>
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
						<html:submit property="Submit">
						<bean:message key="Logon.submit"/>
						</html:submit>
					</td>
					<td align="left">
						<html:reset/>
					</td>
				</tr>
			</table>
		</fieldset>
		</td>
	</tr>
</table>

</html:form>

<html:javascript formName="LogonForm" dynamicJavascript="true"
	staticJavascript="false" />

<script language="Javascript1.1" src="StaticJavascript.jsp"></script>




<!-- html:javascript formName="LogonForm"
                 dynamicJavascript="true"
                 staticJavascript="false"/>
<script language="Javascript1.1" src="StaticJavascript.jsp"></script -->
</body>
</html>
