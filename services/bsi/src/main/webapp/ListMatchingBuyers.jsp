<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.bsi.common.beans.BuyerSrvsRegion,
		 com.bsi.client.util.BSIConstants,	
		 java.util.List,
		 java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>



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
	
	<display:table name="BuyerProperties">
	</display:table>

	</body>
</html:html>