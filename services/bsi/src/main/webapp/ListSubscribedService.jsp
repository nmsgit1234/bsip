<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    	 import="com.bsi.client.util.BSIConstants,
    	 	 com.bsi.common.beans.Service,
    	 	 java.util.List,
    	 	 java.util.ArrayList,
    	 	 org.apache.struts.action.ActionErrors,
    	 	 org.apache.struts.action.ActionMessages" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>




<%@ include file="include/SessionCheck.jsp" %>

<html:html xhtml="true" lang="true">
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="0">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><bean:message key="Supplier.services.subscribed.list"/></title>
		<html:base/>
	</head>

	<style type="text/css" media="all">
	      @import url("css/displaytag/maven-base.css");
	      @import url("css/displaytag/maven-theme.css");
	      @import url("css/displaytag/site.css");
	      @import url("css/displaytag/screen.css");
	  </style>
	

	<script language="JavaScript">

		function editService(personId,personType,serviceName,serviceId,action)
		{
			var serviceName=escape(serviceName);
			var url = "EditSrvsLocationDisplay.do?Action_type="+ action +"&personId=" + personId + "&personType="+ personType +"&serviceName=" + serviceName + "&serviceId=" + serviceId;
			window.location.href= url;
		}

	</script>


	<body>

	<logic:messagesPresent>
		<font color="red">
			<p>Following errors occured:</p>
			<ul>
			 <html:messages id="error" property="<%=ActionErrors.GLOBAL_MESSAGE%>" bundle="errormsgs">
				 <strong><li><bean:write name="error"/></li></strong>
			 </html:messages>
			</ul>
		</font>
	</logic:messagesPresent>


	<logic:messagesPresent message="true">
		 <html:messages id="msg" message="true" property="<%=ActionMessages.GLOBAL_MESSAGE%>">
			 <bean:write name="msg"/>
		 </html:messages>
	</logic:messagesPresent>
	<% String personId = null; 
		if (request.getParameter("personId") != null){
			personId = request.getParameter("personId");
		} else {
			personId = user.getPerson().getId().toString();
		}
	
	%>
	
		<display:table name="requestScope.SubscribedServicesList" id="service" sort="list" export="false" pagesize="4" defaultsort="1" defaultorder="descending">
		  <display:column title="Name">
		  	<a href="javascript:editService('<%= user.getPerson().getId().toString() %>','<%= request.getParameter("personType") %>','<c:out value="${service.name}"/>',<c:out value="${service.nodeId}"/>,'edit')"><c:out value="${service.name}"/></a>
		  </display:column>
		  
		  <display:column title="Edit">
				<c:if test="${param.personType == 'B'}">	  
				  	<a href="javascript:editService('<%= personId %>','B','<c:out value="${service.name}"/>',<c:out value="${service.nodeId}"/>)">Edit</a>
				</c:if>	  
				<c:if test="${param.personType == 'S'}">	  
				  	<a href="javascript:editService('<%= personId %>','S','<c:out value="${service.name}"/>',<c:out value="${service.nodeId}"/>)">Edit</a>
				</c:if>	  
		  </display:column>
		  <display:column title="Activate">Activate</display:column>
		  <display:column title="De-activate">Deactivate</display:column>
		  
		  <c:if test="${param.personType == 'S'}">
			  <display:column title="View Buyers">
				<a href="ListMatchingBuyers.do?personId=<%= personId%>&serviceId=<c:out value="${service.nodeId}"/>">View Buyers</a>
			  </display:column>
		  </c:if>
		  <display:column title="UnSubscribe">
		  	<c:if test="${param.personType == 'B'}">	  
				<a href="UnSubscribeService.do?personId=<%= personId%>&serviceId=<c:out value="${service.nodeId}"/>&personType=B">UnSubscribe</a>
			</c:if>
		  	<c:if test="${param.personType == 'S'}">	  
				<a href="UnSubscribeService.do?personId=<%= personId%>&serviceId=<c:out value="${service.nodeId}"/>&personType=S">UnSubscribe</a>
			</c:if>
		  </display:column>
		</display:table>



	<!-- table>
		<tr>
			<th><bean:message key="Supplier.services.subscribed.list"/></th>
		</tr>

	 	<logic:iterate id="service" name="<%= BSIConstants.SUBSCRIBED_SERVICES_LIST %>" type="com.bsi.common.beans.Service" scope="request">
		  <tr>
		    <td><a href="javascript:editService('<%= user.getPerson().getId().toString() %>','<%= user.getPerson().getPersonType() %>','<bean:write name="service" property="name"/>',<bean:write name="service" property="nodeId"/>)"><bean:write name="service" property="name"/></a></td>
		  </tr>
	 	</logic:iterate>
	</table -->
 	
	</body>
</html:html>








