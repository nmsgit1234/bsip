		<html:select property="modelCountryList" style="display:none">
			    <html:option value="0"><bean:message key="common.dropdown.select"/></html:option>
			    <html:options collection="<%= BSIConstants.COUNTRY_LIST %>"
					  property="regionId"
					  labelProperty="name"/>
		</html:select>

<% if (request.getParameter("personId") == null) { %>
		<html:hidden property="personId" value="<%= user.getPerson().getId().toString() %>"/>
<% } else { %>		
		<html:hidden property="personId" value="<%= request.getParameter("personId") %>"/>
<% } %>		

		<input type="hidden" name="<%= BSIConstants.SERVICE_ID %>" value="<%= request.getParameter("serviceId") %>"/>
		<!-- input type="hidden" name="personId" value="<%-- = user.getPerson().getId() --%>"/ -->
		<!-- input type="hidden" name="personType" value="<%-- = user.getPerson().getPersonType() --%>"/ -->

		  <html:hidden property="page" value="1"/>


		<table width="50%" border="0" align=top cellpadding="0" cellspacing="0">
			 <!-- logic:present name="<%= BSIConstants.SERVICES_LIST %>" -->
				  <tr>
				    <td width="25%"><bean:message key="Supplier.servicesOffered"/></td>
				    <td width="75%">
					<logic:equal name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.CREATE %>" scope="request">
						<html:text property="serviceIdName" size="40" readonly="true" maxlength="50"/>
						<input type="button" value="Browse" onclick="javascript:openSvsPopup();">
					</logic:equal>
					<logic:equal name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.SEARCH %>" scope="request">
						<html:text property="serviceIdName" size="40" readonly="true" maxlength="50"/>
						<input type="button" value="Browse" onclick="javascript:openSvsPopup();">
					</logic:equal>
					<logic:equal name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.EDIT %>" scope="request">
						<input type="text" name="serviceIdName" size="40" readonly value="<%= request.getParameter("serviceName") %>"/>
					</logic:equal>
				    </td>
				  </tr>
			 <!-- /logic:present -->
		</table>

		<br>
		
		<c:if test="${(param.personType == 'B' || param.personType == 'S' )}">
			<div id="properties">
			</div>
		</c:if>
		
		<br>

		<table width="450" border="1" align=top cellpadding="0" cellspacing="0">
		  <tr>
		    <th width="150"><bean:message key="CreateSupplier.location"/></th>
		  </tr>
		 </table>


	<logic:notEqual name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.SEARCH %>" scope="request">
		
		<logic:equal name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.CREATE %>" scope="request">
			<%
				int totalRegns = 0;
			
			%>
			


			<div id="serviceregions">
				<logic:iterate name="SubscribeServiceForm" property="locns" id="row" indexId="rowNum" type="com.bsi.client.actions.forms.LocationForm">
					<table border="0" align=top cellpadding="0" cellspacing="0">
					  <tr>
					    <td valign="top" align="left" width="147" id="<bean:write name="rowNum"/>">
						<div id='region1_<bean:write name="rowNum" />'>
							<html:select property="regionId1" onchange="getChildRegions(1,this)">
							<html:option value="0"><bean:message key="common.dropdown.select"/></html:option>
							<html:options collection="<%= BSIConstants.COUNTRY_LIST %>"
								  property="regionId"
								  labelProperty="name"/>
							</html:select>
						</div>	
						<html:hidden property="regionName1"/>
					    	
					    </td>
					    
					    
					    <td valign="top" align="left" style="display:none" width="147" id="<bean:write name="rowNum"/>" >
						<html:hidden property="regionName2"/>
						<div id='region2_<bean:write name="rowNum" />'></div>
					    </td>
					    <td valign="top" align="left" style="display:none" width="147" id="<bean:write name="rowNum"/>" >
						<html:hidden property="regionName3"/>
						<div id='region3_<bean:write name="rowNum" />'></div>
					    </td>
					    <td valign="top" align="left" style="display:none" width="147" id="<bean:write name="rowNum"/>" >
						<html:hidden property="regionName4"/>
						<div id='region4_<bean:write name="rowNum" />'></div>
					    </td>
					    <td valign="top" align="left" style="display:none" width="147" id="<bean:write name="rowNum"/>" >
						<html:hidden property="regionName5"/>
						<div id='region5_<bean:write name="rowNum" />'></div>
					    </td>
					    <td valign="top" align="left" style="display:none" width="147" id="<bean:write name="rowNum"/>" >
						<html:hidden property="regionName6"/>
						<div id='region6_<bean:write name="rowNum" />'></div>
					    </td>
					  </tr>
					 </table>
					 
					 <%
					 	++totalRegns;	
					 %>
					 
				</logic:iterate>
			</div>
			<table width="450" border="0">
				<tr>
					<td  width="50%" colspan="4" align="right">
						<input type="button" onclick="validateSubscribeServiceForm(<%= totalRegns %>)" value="<bean:message key="button.submit"/>" />
					<td>
					<td  width="50%" colspan="4" align="left">
						<input type="button" onclick="addRow()" value="<bean:message key="Location.add.row"/>" />
					<td>
				</tr>
			</table>

		</logic:equal>




		<logic:equal name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.EDIT %>" scope="request">
			<div id="serviceregions">
				<logic:iterate name="SubscribeServiceForm" property="locns" id="row" indexId="rowNum" type="com.bsi.client.actions.forms.LocationForm">
					<table border="0" align=top cellpadding="0" cellspacing="0">
					  <tr>
					    <td valign="top" align="left" width="147" id="<bean:write name="rowNum"/>">
						<html:text name="row" property="regionName1"  readonly="true"/>
						<html:hidden name="row" property="regionId1"/>
					    </td>
					<c:if test="${not empty row.regionName2}">					    
					    
					    <td valign="top" align="left" width="147" id="<bean:write name="rowNum"/>" >
						<html:text name="row" property="regionName2" readonly="true"/>
						<html:hidden name="row" property="regionId2"/>
					    </td>
					</c:if>
					<c:if test="${not empty row.regionName3}">					    
					
					    <td valign="top" align="left" width="147" id="<bean:write name="rowNum"/>">
						<html:text name="row" property="regionName3" readonly="true"/>
						<html:hidden name="row" property="regionId3"/>
					    </td>
					</c:if>

					<c:if test="${not empty row.regionName4}">					    
					    <td valign="top" align="left" width="147" id="<bean:write name="rowNum"/>">
						<html:text name="row" property="regionName4" readonly="true"/>
						<html:hidden name="row" property="regionId4"/>
					    </td>
					</c:if>

					<c:if test="${not empty row.regionName5}">					    
					    <td valign="top" align="left" width="147" id="<bean:write name="rowNum"/>">
						<html:text name="row" property="regionName5" readonly="true"/>
						<html:hidden name="row" property="regionId5"/>
					    </td>
					</c:if>

					<c:if test="${not empty row.regionName6}">					    
					    <td valign="top" align="left" width="147" id="<bean:write name="rowNum"/>">
						<html:text name="row" property="regionName6" readonly="true"/>
						<html:hidden name="row" property="regionId6"/>
					    </td>
					</c:if>

					  </tr>
					 </table>
				</logic:iterate>
			</div>
			<table width="450" border="0">

				<tr>
					<td  width="50%" colspan="4" align="right">
						<input type="button" onclick="validateSubscribeServiceForm()" value="<bean:message key="button.submit"/>" />
					<td>
					<td  width="50%" colspan="4" align="left">
						<input type="button" onclick="addRow()" value="<bean:message key="Location.add.row"/>" />
					<td>
				</tr>
			</table>
		</logic:equal>

	</logic:notEqual>


	<logic:equal name="<%= BSIConstants.ACTION_TYPE %>" value="<%= BSIConstants.SEARCH %>" scope="request">
			<div id="serviceregions">
				<logic:iterate name="SubscribeServiceForm" property="locns" id="row" indexId="rowNum" type="com.bsi.client.actions.forms.LocationForm">
					<table border="0" align=top cellpadding="0" cellspacing="0">
					  <tr>
					    <td valign="top" align="left" width="147" id="<bean:write name="rowNum"/>">
					    	<div id='region1_<bean:write name="rowNum" />'>
							<html:select property="regionId1" onchange="getChildRegions(1,this)">
							    <html:option value="0"><bean:message key="common.dropdown.select"/></html:option>
							    <html:options collection="<%= BSIConstants.COUNTRY_LIST %>"
									  property="regionId"
									  labelProperty="name"/>
							</html:select>
						</div>
							<html:hidden property="regionName1"/>
					    </td>
					    <td valign="top" align="left" style="display:none" width="147" id="<bean:write name="rowNum"/>" >
						<html:hidden property="regionName2"/>
						<div id='region2_<bean:write name="rowNum" />'></div>
					    </td>
					    <td valign="top" align="left" style="display:none" width="147" id="<bean:write name="rowNum"/>" >
						<html:hidden property="regionName3"/>
						<div id='region3_<bean:write name="rowNum" />'></div>
					    </td>
					    <td valign="top" align="left" style="display:none" width="147" id="<bean:write name="rowNum"/>" >
						<html:hidden property="regionName4"/>
						<div id='region4_<bean:write name="rowNum" />'></div>
					    </td>
					    <td valign="top" align="left" style="display:none" width="147" id="<bean:write name="rowNum"/>" >
						<html:hidden property="regionName5"/>
						<div id='region5_<bean:write name="rowNum" />'></div>
					    </td>
					    <td valign="top" align="left" style="display:none" width="147" id="<bean:write name="rowNum"/>" >
						<html:hidden property="regionName6"/>
						<div id='region6_<bean:write name="rowNum" />'></div>
					    </td>
					  </tr>
					 </table>
				</logic:iterate>
			</div>
		<table width="450" border="0">

			<tr>
				<td  width="100%" colspan="4" align="center">
					<input type="button" onclick="validateSubscribeServiceForm()" value="<bean:message key="button.search"/>" />
				<td>
			</tr>
		</table>
	</logic:equal>
	
