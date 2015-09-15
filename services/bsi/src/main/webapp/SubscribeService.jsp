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
	
	<script language="Javascript">
	function loadProperties(){
		var selSrvs = document.forms[0].<%= BSIConstants.SERVICE_ID %>.value;
		var personId = document.forms[0].<%= BSIConstants.PERSON_ID %>.value;
		//alert("personId:" + personId);
		if (selSrvs != null && selSrvs.length > 0){
			var statusDiv=document.getElementById('progressbar');
			var contentDiv = document.getElementById('properties');
			sendHttpRequest('GetSupplierProperties.do','serviceId='+ selSrvs + "&personId=" + personId,statusDiv,contentDiv);
		}
	}
	</script>

	<body onLoad="getCountries();loadProperties()">
	
	<%@ include file="include/ServerMessages.jsp" %>

	<logic:notEqual name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.SEARCH %>" scope="request">

		<html:form action="/SubscribeService">
			<input type="hidden" name="personType" value="<%= request.getParameter(BSIConstants.PERSON_TYPE) %>"/>
			<%@ include file="include/ServiceLocations.jsp" %>
		</html:form>
	
	</logic:notEqual>

	<c:if test="${param.Action_type == 'search'}">
		<html:form action="/ListPersonsForService" target="searchResultBuffer" style="display:inline" onsubmit="validateSubscribeServiceForm()">
			<input type="hidden" name="personType" value="<c:out value="${param.personType}"/>"/>
			<%@ include file="include/ServiceLocations.jsp" %>
		</html:form>
	</c:if>	
	
	<div id="SearchResult">
	</div>
	

	
	<iframe name="searchResultBuffer" id="searchResultBuffer" src="empty.html" style="position: absolute; height: 300; width: 900; visibility: invisible;"></iframe>

	


	<%@ include file="include/ProgressBar.jsp" %>

	</body>
</html:html>