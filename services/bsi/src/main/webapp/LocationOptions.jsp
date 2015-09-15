<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    	 import="com.bsi.client.util.BSIConstants,java.util.*,com.bsi.common.beans.Region,
    	 	 org.apache.struts.action.ActionErrors,
    	 	 org.apache.struts.action.ActionMessages" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>




<logic:equal name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.LIST_CHILD_REGIONS %>" scope="request">
<%
	Collection regions = (Collection)request.getAttribute(BSIConstants.OPTION_LIST);
	
	if (regions != null && regions.size() > 0)
	{
%>
		<select name="regionId<c:out value="${param.regionNumber}"/>" 
		<c:if test="${hasNext eq true}">
			onchange="getChildRegions(<c:out value="${param.regionNumber}"/>,this)"
		</c:if>
		
		>
			<option value="0"><bean:message key="common.dropdown.select"/></option>
<%
          	Region region = null;
		Iterator iter = regions.iterator();
		while (iter.hasNext())
		{
                  region = (Region)iter.next();
%>
	                <option value="<%= region.getRegionId()%>"><%= region.getName() %></option>
<%
		}

%>		
		</select>
<%	
	}
%>
</logic:equal>