<%@ page language="java" contentType="text/html; charset=utf-8"
    	 import="com.bsi.client.util.BSIConstants,java.util.List,com.bsi.common.beans.Token,
    	 	 org.apache.struts.action.ActionErrors,
    	 	 org.apache.struts.action.ActionMessages" %>
    	 	 <%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<html:html xhtml="true" lang="true">
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="0">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><bean:message key="Token.edit"/></title>
		<html:base/>
	</head>

	<style type="text/css" media="all">
	      @import url("css/bsi.css");
	</style>

	<script language="Javascript" src="js/ajax.js"></script>
		<script language="javascript">
			function addRow(tableID) {
				var table = document.getElementById(tableID);
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				var cell1 = row.insertCell(0);
				var element1 = document.createElement("input");
				element1.type = "checkbox";
				cell1.appendChild(element1);
				//var cell2 = row.insertCell(1);
				//cell2.innerHTML = rowCount + 1;
				var cell3 = row.insertCell(1);
				var element2 = document.createElement("input");
				//var element2 = document.createElement("<input type='text' name='tokenValuesSet' maxlength='50' size='40' value='' />");
				//var element2 = document.createCDATASection("<input type='text' name='tokenValuesSet' maxlength='50' size='40' value='' />");
				element2.type = "text";
				element2.setAttribute('name', 'tokenValuesSet');
				element2.setAttribute('maxlength', '50');
				element2.setAttribute('size', '40');
				element2.setAttribute('value', '');
				cell3.appendChild(element2);
				var element3 = document.createElement("font");
				element3.setAttribute('class', 'mandatory');
				var txt = document.createTextNode("*");
				element3.appendChild(txt);
				cell3.appendChild(element3);
			}

			function deleteRow(tableID) {
				try {
					var table = document.getElementById(tableID);
					var rowCount = table.rows.length;
					for ( var i = 0; i < rowCount; i++) {
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

			function validateTokens(form) {
				var tokenValue = document.getElementsByName('tokenValuesSet');
				var size = tokenValue.length;
				for ( var i = 0; i < size; i++) {
					if (tokenValue[i].value == ''
							|| tokenValue[i].value.trim().length == 0) {
						alert("Token value cannot be empty");
						return false;
					}

				}
				return validateTokenForm(form)
			}

			String.prototype.trim = function() {
				return this.replace(/^\s*/, "").replace(/\s*$/, "");
			}
		</script>
	
	<body>

	<%@ include file="include/ServerMessages.jsp" %>
	
	<!-- --------------------- FOR EDIT Token values ACTION ---------------------------------------------------- -->


<%-- <jsp:useBean id="tokens"name="tokenList" scope="session" class="java.util.List"/>
 --%>
<logic:iterate name="ObjectsList" id="token"  type="com.bsi.common.beans.Token">
				<html:form action="/CreateToken.do">
					<html:hidden property="<%= BSIConstants.ENTITY_NAME %>"
						value="com.bsi.common.beans.Token" />
					<table width="80%" border="0" cellpadding="10" align=top
						cellspacing="0">
						<tr>
							<td class="screen_title"><u><bean:message
										key="Token.create.title" /></u></td>

						</tr>
						<tr>
							<td>
								<table border="0" align=top cellpadding="0" cellspacing="10">
									<tr>
										<td width="25%"><bean:message key="Token.name" /></td>
										<td width="75%"><html:text property="tokenName" value="${token.tokenName}" size="40"
												maxlength="50"/>
												<font class="mandatory">*</font></td>
									</tr>
									<tr>
										<td width="25%"><bean:message key="Token.description" /></td>
										<td width="75%"><html:text property="tokenDesc" size="40" value="${token.tokenDesc}"
												maxlength="50" /><font class="mandatory">*</font></td>
									</tr>
									<tr>
										<td><INPUT type="button" value="Add Tokenvalue"
											onclick="addRow('dataTable')" /></td>
										<td><INPUT type="button" value="Delete Token value"
											onclick="deleteRow('dataTable')" /></td>
									</tr>
									<c:forEach var="tokenValue" items="${token.tokenValues}">
									<tr>
										<TABLE id="dataTable" align=top cellpadding="0"
											cellspacing="10">
										<tr>
										<TD><INPUT type="checkbox" name="chk" disabled="disabled" /></TD>
										<td><html:text property="tokenValuesSet" size="40" value="${tokenValue}"
												maxlength="50" /><font class="mandatory">*</font></td>
												</tr>
												</TABLE>
									</tr>
									</c:forEach>

									<tr>
										<TABLE id="dataTable" align=top cellpadding="0"
											cellspacing="10">
											<TR>
												<TD></TD>
												<TD></TD>
											</TR>
										</TABLE>
									</tr>
								</table>

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
</logic:iterate>
	<!-- ------------------------------------------------------------------------------------------------- -->
	
	<%@ include file="include/ProgressBar.jsp" %>

	<script language="Javascript1.1" src="StaticJavascript.jsp"></script>

	</body>
</html:html>

