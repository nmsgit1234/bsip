<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    	 import="com.bsi.client.util.BSIConstants,
    	 	 org.apache.struts.action.ActionErrors,
    	 	 org.apache.struts.action.ActionMessages" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<html>
<style>
	body {font-size:12px}
	.{font-family:arial;font-size:12px}
	h1 {cursor:hand;font-size:16px;margin-left:10px;line-height:10px}
	xmp {color:green;font-size:12px;margin:0px;font-family:courier;background-color:#e6e6fa;padding:2px}
	.hdr{
		background-color:lightgrey;
		margin-bottom:10px;
		padding-left:10px;
	}
</style>
<script language="Javascript" src="js/ajax.js"></script>
<script language="JavaScript">

		function loadProperties(){
			//var index = document.forms["BuyerForm"].<%= BSIConstants.SERVICE_ID %>.selectedIndex;
			var selSrvs = window.opener.document.forms[0].<%= BSIConstants.SERVICE_ID %>.value;
			var statusDiv=window.opener.document.getElementById('progressbar');
			var contentDiv = window.opener.document.getElementById('properties');
			sendHttpRequest('GetProperties.do','serviceId='+ selSrvs,statusDiv,contentDiv);
		}
		
		function loadParentProperties(){
			var selSrvs = window.opener.document.forms[0].<%= BSIConstants.SERVICE_ID %>.value;
			var statusDiv=window.opener.document.getElementById('progressbar');
			var contentDiv = window.opener.document.getElementById('properties');
			sendHttpRequest('GetParentProperties.do','serviceId='+ selSrvs,statusDiv,contentDiv);
		}
		
		
		
		
</script>






<body>
	<link rel="STYLESHEET" type="text/css" href="../css/dhtml/dhtmlXTree.css">
	<script  src="js/dhtmlXCommon.js"></script>
	<script  src="js/dhtmlXTree.js"></script>
	<table>
		<tr>
			<td>
				<div id="treeboxbox_tree" style="width:250; height:218;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;"/>
			</td>
			<td rowspan="2" style="padding-left:25" valign="top">

		</div>	
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	<script>
			tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
			tree.setImagePath("img/dhtml/");
			//tree.loadXML("test2.xml")
			//tree.setXMLAutoLoading("http://localhost:7001/bsi/GetTree.do")
			//tree.loadXML("http://localhost:7001/bsi/GetTree.do?id=0");
			tree.setXMLAutoLoading("GetTree.do")
			tree.loadXML("GetTree.do?id=0");
			tree.setOnDblClickHandler(doOnClick);
			tree.setOnRightClickHandler(doOnMouseOver)
			function doOnMouseOver(nodeId){
				alert("ID is : " + nodeId);
			}

			function doOnClick(nodeId){
				//alert("tree.hasChildren(nodeId) " +  tree.hasChildren(nodeId));
				//alert("id" + nodeId);
				//alert("name" + tree.getItemText(nodeId));
				window.opener.document.forms[0].serviceIdName.value = tree.getItemText(nodeId);
				window.opener.document.forms[0].<%= BSIConstants.SERVICE_ID %>.value = nodeId;
				<% if(request.getParameter("Action_type") != null && request.getParameter("Action_type").equals("parentProperties")) {%>
					window.opener.document.forms[0].prntNodeId.value = nodeId;
					loadParentProperties();
				<%  } else { %>
				loadProperties();
				<% } %>
				//window.opener.location.reload();
			}



	</script>
<br><br>
<p>
</body>
</html>
