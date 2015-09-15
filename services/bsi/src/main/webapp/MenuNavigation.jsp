<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    	 import="com.bsi.client.util.BSIConstants" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page import="com.bsi.client.session.User" %>

<%@ include file="include/SessionCheck.jsp" %>

<html>
	<head>
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="0">

	<style type="text/css">
		@import url("css/bsi.css");
	</style>

	<script type="text/javascript" src="js/NavMenu.js">
	</script>
	
	</head>
	<body onLoad="setSelectedLocale()">
	

		<!-- Keep all menus within masterdiv-->
		<div id="masterdiv">

			<div class="menutitle" onclick="parent.location.href='Home.jsp';"><bean:message key="Menu.home"/></div>
			<div class="menutitle" onclick="SwitchMenu('sub1')"><bean:message key="Menu.profile"/></div>
			<span class="submenu" id="sub1">
				- <a href="GetPerson.do?personId=<%= user.getPerson().getId() %>" target="display"><bean:message key="Menu.profile.edit"/></a><br>
				- <a href="" target="display"><bean:message key="Menu.profile.change.passwd"/></a><br>
			</span>

			<div class="menutitle" onclick="SwitchMenu('sub2')"><bean:message key="Menu.person.list"/></div>
			<span class="submenu" id="sub2">
				- <a href="ListPersons.do?personType=B" target="display"><bean:message key="Menu.list.buyer"/></a><br>
				- <a href="ListPersons.do?personType=S" target="display"><bean:message key="Menu.list.supplier"/></a><br>
			</span>

			<div class="menutitle" onclick="SwitchMenu('sub3')"><bean:message key="Menu.services"/></div>
			<span class="submenu" id="sub3">
				- <a href="TreeView.html" target="display"><bean:message key="Menu.browse.services"/></a><br>
				- <a href="SubscribeServiceDisplay.do?<%= BSIConstants.ACTION_TYPE %>=<%= BSIConstants.CREATE %>&personId=<%= user.getPerson().getId() %>&personType=<%= user.getPerson().getPersonType() %>" target="display"><bean:message key="Menu.subscribe.service"/></a><br>
				- <a href="ListSubscribedService.do?personId=<%= user.getPerson().getId()%>" target="display"><bean:message key="Menu.subscribe.service.edit"/></a><br>
				- <a href="ListMatchingBuyers.do?personId=<%= user.getPerson().getId()%>&serviceId=2" target="display"><bean:message key="Menu.matching.buyers"/></a><br>
				- <a href="ServicePersonSearch.do?<%= BSIConstants.ACTION_TYPE %>=<%= BSIConstants.SEARCH %>" target="display"><bean:message key="Menu.matching.suppliers"/></a><br>
			</span>
			

			<div class="menutitle" onclick="SwitchMenu('sub4')"><bean:message key="Menu.select.locale"/></div>
			<span class="submenu" id="sub4">
				<%@ include file="include/Locales.jsp" %>
			</span>

			<div class="menutitle" onclick="parent.location.href='Logout.do';"><bean:message key="Menu.logout"/></div>
		</div>	
	
	</body>
</html>