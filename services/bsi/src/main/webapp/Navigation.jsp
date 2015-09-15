<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    	 import="com.bsi.client.util.BSIConstants" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page import="com.bsi.client.session.User" %>

<%@ include file="include/SessionCheck.jsp" %>

<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>

<html>
	<head>
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="0">

	
    <link rel="stylesheet" type="text/css" media="screen" href="css/struts-menu/global.css" />
    
    <script type="text/javascript" src="js/struts-menu/cookies.js"></script>


	</head>
	<body>
	    <menu:useMenuDisplayer name="ListMenu" bundle="org.apache.struts.action.MESSAGE">
		  <menu:displayMenu name="Home"/>
		  <menu:displayMenu name="Profile"/>
	    </menu:useMenuDisplayer>	 
	    <menu:useMenuDisplayer name="ListMenu" bundle="org.apache.struts.action.MESSAGE" permissions="bsiRoles">
		  <menu:displayMenu name="BuyerServiceMenu"/>
	     </menu:useMenuDisplayer>
	    <menu:useMenuDisplayer name="ListMenu" bundle="org.apache.struts.action.MESSAGE" permissions="bsiRoles">
		  <menu:displayMenu name="SupplierServiceMenu"/>
	     </menu:useMenuDisplayer>
	    <menu:useMenuDisplayer name="ListMenu" bundle="org.apache.struts.action.MESSAGE" permissions="bsiRoles">
		  <menu:displayMenu name="AdminSevicesMenu"/>
	     </menu:useMenuDisplayer>
	    <menu:useMenuDisplayer name="ListMenu" bundle="org.apache.struts.action.MESSAGE">
		  <menu:displayMenu name="Logout"/>
	     </menu:useMenuDisplayer>
		<span class="submenu" id="sub4">
			<%@ include file="include/Locales.jsp" %>
		</span>

	</body>
</html>