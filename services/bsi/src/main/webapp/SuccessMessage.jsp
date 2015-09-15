<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" 
    	 import="org.apache.struts.action.ActionMessages,
    	 	org.apache.struts.action.ActionErrors" %>
    	 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

    	 
<html:html xhtml="true" lang="true">
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="0">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><bean:message key="success.message.title"/></title>
		<html:base/>
	</head>
	<body>

		<%@ include file="include/ServerMessages.jsp" %>


		<%-- logic:messagesPresent message="true">
			 <html:messages id="msg" message="true" property="<%=ActionMessages.GLOBAL_MESSAGE%>"> 
				 <bean:write name="msg"/>
			 </html:messages> 
		</logic:messagesPresent>
		 --%>
	</body>	
</html:html>	