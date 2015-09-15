<%@ page import="org.apache.struts.Globals,java.util.Locale" %>



	<%
	
		Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
		String lang = locale.getLanguage();
		String country = locale.getCountry();

	%>

	<script language="JavaScript">

		function setSelectedLocale()
		{

		  var selLocale = "<%= lang %>_<%= country %>";
		  var locales = document.forms["LocaleForm"].locales.options;
		  for (var x=0;x<locales.length;x++)
		  {
			var value = locales[x].value;
			if (value == selLocale)
			{
			   document.forms[0].locales.selectedIndex = x;
			   return;
			}
		  }

		}


		function changeLocale(which)
		{
		   var locale = which.options[which.selectedIndex].value;
		   parent.window.location.href = "ChangeLocale.do?<%= BSIConstants.LANG_COUNTRY %>=" + locale;

		}

	</script>

<form name="LocaleForm">

	<select name="locales" onChange="changeLocale(this)">
		<option value="en_US"><bean:message key="Locale.english"/></option>
		<option value="ja_JP"><bean:message key="Locale.japanese"/></option>
		<option value="zh_CN"><bean:message key="Locale.chinese"/></option>
	</select>
</form>
