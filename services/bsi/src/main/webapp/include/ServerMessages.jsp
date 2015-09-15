<table width="70%" border="0" cellpadding="10" align=top cellpadding="0" cellspacing="0">
	<tr>
		<logic:messagesPresent>
			<td class="errormsg">
				<font color="red">
					<h><bean:message key="common.following.error.occured"/></h>
					<ul>
					 <html:messages id="error" property="<%=ActionErrors.GLOBAL_MESSAGE%>" bundle="errormsgs">
						 <strong><li><bean:write name="error"/></li></strong>
					 </html:messages>
					</ul>
				</font>
			</td>
		</logic:messagesPresent>

<% request.getAttribute(ActionMessages.GLOBAL_MESSAGE); %>



		<logic:messagesPresent message="true">
			<td class="successmsg">
				 <html:messages id="msg" message="true" property="<%=ActionMessages.GLOBAL_MESSAGE%>">
					 <bean:write name="msg"/>
				 </html:messages>
			</td>
		</logic:messagesPresent>

	</tr>	
</table>	
