<%@ page import="com.bsi.common.beans.Person,
		 com.bsi.client.util.BSIConstants,
		 java.util.List,
		 java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<%@ include file="include/SessionCheck.jsp" %>

<html:html xhtml="true" lang="true">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><bean:message key="CreateSupplier.title"/></title>
		<html:base/>
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
		String message=(String)request.getAttribute("message");
		if (message != null && message.length() > 0)
		{
	%>

		<%= request.getAttribute("message") %>
	<%
		}
	%>
	<html:errors/>
	<display:table name="sessionScope.ObjectsList" sort="list" id="person" export="true" pagesize="50" defaultsort="1" defaultorder="descending">
	  <logic:equal parameter="<%= BSIConstants.PERSON_TYPE %>" value="<%= BSIConstants.BUYER %>" scope="request">
		  <display:column property="name" href="GetPerson.do" paramId="<%= BSIConstants.PERSON_ID%>" paramProperty="id" titleKey="CreateSupplier.name" sortable="true"/>
	  </logic:equal>
	  <logic:equal parameter="<%= BSIConstants.PERSON_TYPE %>" value="<%= BSIConstants.SUPPLIER %>" scope="request">
		  <display:column property="name" href="GetPerson.do" paramId="<%= BSIConstants.PERSON_ID%>" paramProperty="id" titleKey="CreateSupplier.name" sortable="true"/>
	  </logic:equal>
	  <display:column property="address" titleKey="CreateSupplier.address" />
	  <display:column property="phoneNumber" titleKey="CreateSupplier.phoneNumber" />
	  <display:column property="email" titleKey="CreateSupplier.email"/>
	  <% if (user.hasRole("DeleteBuyer")){ %>
		  <display:column title="Delete">
			<a href="DeletePerson.do?personId=<c:out value="${person.id}"/>">Delete</a>
		  </display:column>
	  <% } %>
	  <% if (user.hasRole("GlobalAdministration")){ %>
		  <display:column title="Offer Service">
		    <a href="SubscribeServiceDisplay.do?Action_type=create&amp;personType=<c:out value="${person.personType}"/>&amp;personId=<c:out value="${person.id}"/>"/>Offer Service</a>
		  </display:column>
	  <% } %>
	  <% if (user.hasRole("GlobalAdministration")){ %>
		  <display:column title="Offered Service">
		    <a href="ListSubscribedService.do?personType=<c:out value="${person.personType}"/>&amp;personId=<c:out value="${person.id}"/>"/>Offered Service</a>
		  </display:column>
	  <% } %>

	  <display:setProperty name="export.pdf" value="true" />
	</display:table>

	</body>
</html:html>
