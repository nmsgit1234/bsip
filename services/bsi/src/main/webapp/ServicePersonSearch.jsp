<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    	 import="com.bsi.client.util.BSIConstants" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<%@ include file="include/SessionCheck.jsp" %>

<html>
  	<head>
            <meta http-equiv="Window-Target" content="_top">
        </head>
	<frameset rows="*,*" frameborder="0" scrolling="no" framespacing="0">
		<frame name="search"  src="SubscribeServiceDisplay.do?<%= BSIConstants.ACTION_TYPE %>=<%= BSIConstants.SEARCH %>&<%= BSIConstants.PERSON_TYPE %>=<c:out value="${param.personType}"/>">
		<frame name="result"  src="">
	</frameset>
</html>
