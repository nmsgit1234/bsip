<%@ page language="java" contentType="text/html; charset=utf-8"
    	 import="com.bsi.client.util.BSIConstants,
    	 	 org.apache.struts.action.ActionErrors,
    	 	 org.apache.struts.action.ActionMessages" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<%@ include file="include/SessionCheck.jsp" %>


<html:html xhtml="true" lang="true">
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="0">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><bean:message key="Supplier.title"/></title>
		<html:base/>
	</head>

	<script language="Javascript" src="js/ajax.js"></script>
	<script language="Javascript" src="js/serviceLocations.js"></script>

	<body>
	
	<%@ include file="include/ServerMessages.jsp" %>

	<c:if test="${param.Action_type == 'search'}">
		<html:form action="/SearchPersonByName" target="searchResultBuffer" style="display:inline">
			<input type="hidden" name="personType" value="<c:out value="${param.personType}"/>"/>
			<html:hidden property="<%= BSIConstants.ENTITY_NAME %>" value="com.bsi.common.beans.Person" />
			<html:text property="name"/>
		<table width="450" border="0">

			<tr>
				<td  width="100%" colspan="4" align="center">
					<input type="submit" value="<bean:message key="button.search"/>" />
				<td>
			</tr>
		</table>
			
		</html:form>
	</c:if>	
	
	<div id="SearchResult">
	</div>
	<iframe name="searchResultBuffer" id="searchResultBuffer" src="empty.html" style="position: absolute; top: 80; left: 10; height: 300; width: 600; visibility: visible;"></iframe>
	<%@ include file="include/ProgressBar.jsp" %>

	</body>
</html:html>