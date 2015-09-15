	<logic:present name="<%= BSIConstants.COUNTRY_LIST %>">
	  <tr>
	    <td width="25%"><bean:message key="CreateSupplier.country"/></td>
	    <td width="75%">
	       <div id='region1'>
		<html:select property="regionId1" onchange="getChildRegions(1,this)">
		    <html:option value=""><bean:message key="common.dropdown.select"/></html:option>
		    <html:options collection="<%= BSIConstants.COUNTRY_LIST %>" 
				  property="regionId"
				  labelProperty="name"/>
		</html:select>
		</div>
	    </td>
	  </tr>
	</logic:present>	  


	<tr style="display:none">
		<td width="25%"><bean:message key="CreateSupplier.state"/></td>
		<td width="75%">
			<div id='region2'></div>
		</td>
	</tr>
	<tr style="display:none">
		<td width="25%"><bean:message key="CreateSupplier.location"/></td>
		<td width="75%">
			<div id='region3'></div>
		</td>
	</tr>
	<tr style="display:none">
		<td width="25%"><bean:message key="CreateSupplier.location"/></td>
		<td width="75%">
			<div id='region4'></div>
		</td>
	</tr>
	<tr style="display:none">
		<td width="25%"><bean:message key="CreateSupplier.location"/></td>
		<td width="75%">
			<div id='region5'></div>
		</td>
	</tr>
	<tr style="display:none">
		<td width="25%"><bean:message key="CreateSupplier.location"/></td>
		<td width="75%">
			<div id='region6'></div>
		</td>
	</tr>
