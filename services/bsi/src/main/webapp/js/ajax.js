  var xmlobj=getXMLHttpRequestObject();
  var contentDiv = null;
  var statusDiv = null;

function getXMLHttpRequestObject(){
    var xmlobj;
    if(xmlobj!=null){
        return xmlobj;
    }
    try{
        // instantiate object for Mozilla, Nestcape, etc.
        xmlobj=new XMLHttpRequest();
    }
    catch(e){
        try{
            // instantiate object for Internet Explorer
            xmlobj=new ActiveXObject('Microsoft.XMLHTTP');
        }
        catch(e){
            // Ajax is not supported by the browser
            xmlobj=null;
            return false;
        }
    }
    return xmlobj;
}

  function sendHttpRequest(action,params,statusDiv,contentDiv){
   	this.contentDiv=contentDiv;
	this.statusDiv=statusDiv;
	//div.innerHTML="<img src=\"../img/waiting_dots.gif\"></img>";
	if (statusDiv != null)
		statusDiv.innerHTML="<br><br><font face=\"Arial\" size=\"3\" color=\"#000000\"><b>Performing Task, Please Wait..........</b></font><img border=\"0\" width=\"20\" src=\"img/loading.gif\"></img>";
    // open socket connection
    var url = action + "?" + params
    xmlobj.open('GET',url,true);
    // set http header
    xmlobj.setRequestHeader('Content-Type','text/html; charset=UTF-8');
    xmlobj.onreadystatechange=xmlobjStatusChecker;
    // send request
    xmlobj.send(null);
}

// check status of requester object
 function xmlobjStatusChecker(){
    // if http request is completed
    if(xmlobj.readyState==4){
        if(xmlobj.status==200){
            // if status == 200 display database records
            if (contentDiv != null)
            displayData(contentDiv);
            contentDiv = null;
   	    if (statusDiv != null)
		statusDiv.innerHTML="";
        }
        else{
            alert("Couldnot get the response, please contact support");
            //var div=document.getElementById('progressbar');
            if (statusDiv != null)
            	statusDiv.innerHTML="";
            return;
        }
    }
}

function displayData(contentDiv){
    //var div=document.getElementById(containerId);
    if(!contentDiv){return};
    var html = xmlobj.responseText;
    //if (html.replace(/^\s*|\s*$/g,'') == "SESSIONINVALID")
    //{
    //  redirectToLogin();
    //  return;
    //}
    contentDiv.innerHTML=html;
	//var div=document.getElementById('progressbar');
	if (statusDiv != null)
		statusDiv.innerHTML="";
}
