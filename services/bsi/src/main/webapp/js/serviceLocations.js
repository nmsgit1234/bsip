		var currntRow = 0;
		var countriesOptionsHtml = null;
		
		function getCountries()
		{
			var options=document.forms[0].modelCountryList;
			countriesOptionsHtml=options.innerHTML;
		}


	
		function validateForm()
		{
			var elems=new Array();

			elems = document.forms[0].elements["regionId1"];
			if (elems.type == 'select-one')
			{
				var selIndex = document.forms[0].regionId1.selectedIndex;
				if (selIndex == 0)
				{
					alert("Please select the country id");
					return false;
				}
				else
				{
					return true;
				}
			}
			for(var x=0;x < elems.length;x++)
			{
				if (elems[x].value == '0')
				{
					alert("Please select the country id");
					return false;
				}
			}
			return true;
		}
		
		

		function selectMutlipleLocns(totalRegns)
		{
		  
		  for (var x=0;x<totalRegns;x++)
		  {
		  	selectMultipleRegions(x+1);
		  }
		  
		  /*
		  var locationIds=document.getElementsByName("locationId");
		  for(var x=0;x<locationIds.length;x++)
		  {
			var type = locationIds[x].type;
			if (type != 'select-multiple') continue;

		  	var selOptionValues = locationIds[x].options;
			var newValue = "";
		  	for (var y=0;y<selOptionValues.length;y++)
		  	{

		  			if ( selOptionValues[y].selected)
		  			{
		  				newValue = newValue + "," + selOptionValues[y].value ;
		  			}
		  	}
			selOptionValues.length=0;
		  	var oOption = document.createElement("OPTION");
			selOptionValues.options.add(oOption);
			oOption.value = newValue;
			oOption.selected = 'true';

		  }
		  */

		  return true;
		  
		 } 
		 
		 
		function selectMultipleRegions(regionNum)
		{

		  var regionIds=document.getElementsByName("regionId" + regionNum);
		  for(var x=0;x<regionIds.length;x++)
		  {
		  	var type = regionIds[x].type;
			if (type != 'select-multiple') continue;

		  	var selOptionValues = regionIds[x].options;
			var newValue = "";
		  	for (var y=0;y<selOptionValues.length;y++)
		  	{

		  			if ( selOptionValues[y].selected)
		  			{
		  				newValue = newValue + "," + selOptionValues[y].value ;
		  			}
		  	}
			selOptionValues.length=0;
		  	var oOption = document.createElement("OPTION");
			selOptionValues.options.add(oOption);
			oOption.value = newValue;
			oOption.selected = 'true';

		  }
		  return true;
		  
		 } 
		 



		function validateSubscribeServiceForm(totalRegns)
		{
			selectMutlipleLocns(totalRegns);
			if (validateForm())
			{
			 	document.forms[0].submit();
			 	//alert("Submitting the form");
			}

			return true;
		}


		function searchResultBufferLoaded()
		{
			
			
			alert("In searchResultBufferLoaded");
			
			/*
			alert("Style is " + document.all.searchResultBuffer.style.visibility);
			alert("The inner text is " + document.all.searchResultBuffer.innerText);
			alert("The outer text is " + document.all.searchResultBuffer.outerText);
			alert("The outerHTML is " + document.all.searchResultBuffer.outerHTML);
			
			var resultsDoc = document.frames('searchResultBuffer').document;
			
			
			alert("innerttext is " + resultsDoc.all('searchResults').innerText);
			
			var resultDiplayDiv = document.getElementById('SearchResult');
			
			alert("resultDiplayDiv is " + resultDiplayDiv);
			
			resultDiplayDiv.innerHTML=resultsDoc.all('searchResults').innerText;
			*/
			
			var resultsDoc = document.frames('searchResultBuffer').document;
			alert("innerttext is " + resultsDoc.all('searchResults').innerText);
			var resultDiplayDiv = document.getElementById('SearchResult');
			alert(resultDiplayDiv);
			resultDiplayDiv.innerHTML=resultsDoc.all('searchResults').innerText;
						
			//document.all.searchResultBuffer.style.visibility = "visible";
			
			//var searchResultBufferDoc = document.frames('searchResultBuffer').document;
			//alert(searchResultBufferDoc);
			//alert(searchResultBufferDoc.all('newhtml').innerText);
			//var resultDiplayDiv = document.getElementById('SearchResult');
			//alert(resultDiplayDiv);
			//resultDiplayDiv.innertHTML=searchResultBufferDoc.all('newhtml').innerText;
			//alert("The frame visibility is " + searchResultBufferDoc.style.visibility );
			//searchResultBufferDoc.style.visibility = "visible";
		}

	
		function openSvsPopup(){
			
			var newwindow;
			newwindow=window.open("TreeView.jsp",'services','height=300,width=300');
			if (window.focus) {newwindow.focus()}

		}
		

		function addRow()
		{
		
		currntRow = document.getElementsByName("countryId").length;
		
		currntRow = currntRow +1;
		
		var datadiv = document.getElementById('serviceregions');
		var html = datadiv.innerHTML;
		
		//var newRow = "<table width=\"50%\" border=\"0\"><tr>" +
		//				"<td width=\"33%\"><input type=\"text\" name=\"countryId\" maxlength=\"50\" value=\"\" style=\"width:90%\" /></td>" +
		//				"<td width=\"33%\"><input type=\"text\" name=\"stateId\" maxlength=\"50\" value=\"\" style=\"width:90%\" /></td>" +
		//				"<td width=\"33%\"><input type=\"text\" name=\"locationId\" maxlength=\"50\" value=\"\" style=\"width:90%\"/></td>" +
		//			"</tr></table>";
		var newRow = "<table  border=\"0\" align=top cellpadding=\"0\" cellspacing=\"0\">" +
				  "<tr>" +
				    "<td valign=\"top\" align=\"left\" width=\"147\" id=\"" + currntRow + "\">" +
				    	"<select name=\"countryId\" onchange=\"getStates(this)\">" + countriesOptionsHtml +
				    "</td>" +
				    "<td valign=\"top\" align=\"left\" style=\"display:none\" id=\"" + currntRow + "\" width=\"147\">" +
				    	"<div id='states_" + currntRow + "'>" +
				    "</td>" +
				    "<td valign=\"top\" align=\"left\" style=\"display:none\" id=\"" + currntRow + "\" width=\"147\">" +
				    	"<div id='locations_" + currntRow + "'></div>" +
				    "</td>" +
				  "</tr>" +
				 "</table>";

		//if (datadiv == null) alert("Null");
		datadiv.innerHTML=html + newRow;
		}


		
		/*
		function getStates(which)
		{

			//countriesOptionsHtml =which.innerHTML;
			var rowNum = which.parentNode.getAttribute('id');
			//Set the states and location div to null
			var stateDiv= document.getElementById('states_' + rowNum);
			if (stateDiv != null)
			{
				stateDiv.innerHTML="";
				stateDiv.parentNode.style.display = 'none';

			}
			var locationsDiv= document.getElementById('locations_' + rowNum);
			if (locationsDiv != null)
			{
				locationsDiv.innerHTML="";
				locationsDiv.parentNode.style.display = 'none';

			}
			var selCountryIndex = which.selectedIndex;
			if (selCountryIndex == 0) return;
			//var selCountry = document.forms[0].countryId[selCountryIndex].value;
			var selCountry = which.value;
			var statusDiv=document.getElementById('progressbar');
			var contentDiv=document.getElementById('states_' + rowNum);
			//sendHttpRequest('GetStates.do','countryId=' + selCountry,statusDiv,contentDiv);
			sendHttpRequest('GetRegionNames.do','Action_type=listChildRegions&regionId=' + selCountry,statusDiv,contentDiv);
			stateDiv.parentNode.style.display = 'block';
			return;
		}
		

		function getLocations(which)
		{
			var rowNum = which.parentNode.parentNode.getAttribute('id');

			var locationsDiv= document.getElementById('locations_' + rowNum);
			if (locationsDiv != null)
			{
				locationsDiv.innerHTML="";
				locationsDiv.parentNode.style.display = 'none';
			}
			var selStateIndex = which.selectedIndex;
			if (selStateIndex == 0) return;
			//var selState = document.forms[0].stateId[selStateIndex].value;

			//var selState = document.forms[0].stateId[selStateIndex].value;
			var selState = which.value;
			var statusDiv=document.getElementById('progressbar');
			var contentDiv=document.getElementById('locations_' + rowNum);
			//sendHttpRequest('GetLocations.do','stateId=' + selState,statusDiv,contentDiv);
			sendHttpRequest('GetRegionNames.do','Action_type=listChildRegions&regionId=' + selState,statusDiv,contentDiv);
			
			locationsDiv.parentNode.style.display = 'block';
			return;
		}
		
		*/
		
		function getChildRegions(regNum,which)
		{
			//Set all other location div's to null
			var rowNum = which.parentNode.parentNode.getAttribute('id');
			var currntReg = "region" + regNum + "_" + rowNum;
			var nextReg = "region" + (regNum+1) + "_" + rowNum;
			

			var contentDiv = document.getElementById(nextReg);
			var statusDiv=document.getElementById('progressbar');
			var regDiv = null;
			for (var x=regNum+1;x<=6;x++)
			{
				var regName = "region" + x + "_" + rowNum;
				regDiv = document.getElementById(regName);
				if (regDiv != null)
				{
					regDiv.innerHTML="";
					regDiv.parentNode.style.display = 'none';
				}
			}
			var selRegIndex = which.selectedIndex;
			if (selRegIndex == 0) return;
			//alert(which.id);
			var regionId = which.value;
			var regionNum = regNum + 1;
			sendHttpRequest('GetRegionNames.do','Action_type=listChildRegions&regionNumber='+ regionNum +'&regionId=' + regionId,statusDiv,contentDiv);
			contentDiv.parentNode.style.display = 'block';
			return;	
		}		
