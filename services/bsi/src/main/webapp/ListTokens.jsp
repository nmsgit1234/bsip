<%@ page
	import="com.bsi.client.util.BSIConstants,java.util.List,java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>


<%@ include file="include/SessionCheck.jsp"%>

<html:html xhtml="true" lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><bean:message key="Token.list" /></title>
<html:base />
</head>

<style type="text/css" media="all">
@import url("css/displaytag/maven-base.css");

@import url("css/displaytag/maven-theme.css");

@import url("css/displaytag/site.css");

@import url("css/displaytag/screen.css");
</style>

<body>
	<%
		int maxPageItems = 5;
			String message = (String) request.getAttribute("message");
			if (message != null && message.length() > 0) {
	%>

	<%=request.getAttribute("message")%>
	<%
		}
	%>
	<html:errors />


	<display:table name="sessionScope.TokensList" sort="list" id="token"
		export="true" pagesize="50" defaultsort="1" defaultorder="descending">
		<display:column property="tokenId" paramId="tokenId" paramProperty="tokenId"
				titleKey="Token.name" sortable="true" />
		<display:column property="tokenName" paramId="tokenId" paramProperty="tokenId"
				titleKey="Token.name" sortable="true" />
		<display:column property="tokenDesc" titleKey="Token.description" />
		<display:column property="tokenValues" titleKey="Token.values" />
		  <display:column title="Edit">
			<a href="DisplayTokenValues.do?<%= BSIConstants.ENTITY_NAME %>=com.bsi.common.beans.Token&tokenId=<c:out value="${token.tokenId}"/>">Edit</a>
		  </display:column>
		<display:setProperty name="export.pdf" value="true" />
	</display:table>
</body>
</html:html>
