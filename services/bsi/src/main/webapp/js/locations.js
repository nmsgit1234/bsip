function getChildRegions(regNum,which)
{
	//Set all other location div's to null
	var currntReg = "region" + regNum;
	var nextReg = "region" + (regNum+1);
	
	var contentDiv = document.getElementById(nextReg);
	var statusDiv=document.getElementById('progressbar');
	var regDiv = null;
	for (var x=regNum+1;x<=regNum;x++)
	{
		var regName = "region" + x;
		regDiv = document.getElementById(regName);
		if (regDiv != null)
		{
			regDiv.innerHTML="";
			regDiv.parentNode.parentNode.style.display = 'none';
		}
	}
	var selRegIndex = which.selectedIndex;
	if (selRegIndex == 0) return;
	//alert(which.id);
	var regionId = which.value;
	var regionNum = regNum + 1;
	
	sendHttpRequest('GetRegionNames.do','Action_type=listChildRegions&regionNumber='+ regionNum +'&regionId=' + regionId,statusDiv,contentDiv);
	contentDiv.parentNode.parentNode.style.display = 'block';
	return;	
}


/*
function getStates(which)
{
	//Set the states and location div to null
	var stateDiv= document.getElementById('states');
	if (stateDiv != null)
	{
		stateDiv.innerHTML="";
		stateDiv.parentNode.parentNode.style.display = 'none';

	}
	var locationsDiv= document.getElementById('locations');
	if (locationsDiv != null)
	{
		locationsDiv.innerHTML="";
		locationsDiv.parentNode.parentNode.style.display = 'none';

	}
	var selCountryIndex = which.selectedIndex;
	if (selCountryIndex == 0) return;
	var selCountry = document.forms[0].countryId[selCountryIndex].value;

	var statusDiv=document.getElementById('progressbar');
	var contentDiv=document.getElementById('states');
	//sendHttpRequest('GetStates.do','countryId=' + selCountry,statusDiv,contentDiv);

	sendHttpRequest('GetRegionNames.do','Action_type=listChildRegions&regionId=' + selCountry,statusDiv,contentDiv);

	stateDiv.parentNode.parentNode.style.display = 'block';
	return;
}

function getLocations(which)
{
	var locationsDiv= document.getElementById('locations');
	if (locationsDiv != null)
	{
		locationsDiv.innerHTML="";
		locationsDiv.parentNode.parentNode.style.display = 'none';
	}
	var selStateIndex = which.selectedIndex;
	if (selStateIndex == 0) return;
	var selState = document.forms[0].stateId[selStateIndex].value;
	var statusDiv=document.getElementById('progressbar');
	var contentDiv=document.getElementById('locations');
	//sendHttpRequest('GetLocations.do','stateId=' + selState,statusDiv,contentDiv);
	sendHttpRequest('GetRegionNames.do','Action_type=listChildRegions&regionId=' + selState,statusDiv,contentDiv);
	locationsDiv.parentNode.parentNode.style.display = 'block';
	return;
}
*/


