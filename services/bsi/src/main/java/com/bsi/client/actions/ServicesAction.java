package com.bsi.client.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.bsi.client.actions.forms.PropertiesForm;
import com.bsi.client.actions.forms.PropertyForm;
import com.bsi.client.managers.PersonManager;
import com.bsi.client.managers.ServiceManager;
import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.SupplierServicePropertyValues;
import com.nms.util.log.CommonLogger;


/**
 * Retrieve and process data from the submitted form
 *
 * @version $Rev: 421486 $ $Date: 2006-07-12 20:37:08 -0700 (Wed, 12 Jul 2006) $
 */
public class ServicesAction extends MappingDispatchAction {

	private static Log log = LogFactory.getLog(ServicesAction.class);

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for ProcessFormAction.
     */
    public ServicesAction() {
        super();
    }

    // ---------------------------------------------------------- Action Methods

    /**
     * Process the request and return an <code>ActionForward</code> instance
     * describing where and how control should be forwarded, or
     * <code>null</code>if the response has already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if the application logic throws an exception
     *
     * @return the ActionForward for the next view
     */


    public ActionForward list(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {


		CommonLogger.logDebug(log,"In ServicesAction:list()");

        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }


		ServiceManager svsMgr = new ServiceManager();

		List svsList = svsMgr.getAllServices();

        request.setAttribute(BSIConstants.SERVICES_LIST,svsList);

        return mapping.findForward("success");

    }

    public ActionForward add(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {


		CommonLogger.logDebug(log,"In ServicesAction:add()");

		PropertiesForm properties = (PropertiesForm)form;
		ArrayList props = new ArrayList();

		PropertyForm propForm = null;


		for (int i=0;i<1;i++){
			propForm = new PropertyForm();
			props.add(propForm);
		}
		properties.populateForm(props);

        return mapping.findForward("success");
    }

    public ActionForward save(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

		CommonLogger.logDebug(log,"In ServicesAction:save()");

		String svsId = request.getParameter(BSIConstants.SERVICE_ID);

		CommonLogger.logDebug(log,"The service id is " + svsId);


		PropertiesForm properties = (PropertiesForm)form;

		List props = properties.getProperties();
		ServiceManager svsMgr = new ServiceManager();
		svsMgr.updateServiceProperties(svsId,props);


		for (int x=0;x<props.size();x++){
			PropertyForm prop = (PropertyForm)props.get(x);
			Long propId = prop.getPropertyId();
			CommonLogger.logDebug(log,"The property id is " + x + " is :" + propId.toString());
			CommonLogger.logDebug(log,"The property name for " + x + " is :" + prop.getName());
		}

        return mapping.findForward("success");
	}


/*
    public ActionForward getProperties(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

		CommonLogger.logDebug(log,"In ServicesAction:getService()");
		String svsId = request.getParameter(BSIConstants.SERVICE_ID);
		CommonLogger.logDebug(log,"The service id is " + svsId);


		ServiceManager svsMgr = new ServiceManager();

		Set props = svsMgr.getServiceProperties();
		//Create the PropertyValueForm beans

		PropertiesValuesForm properties = (PropertiesValuesForm)form;

		List props = properties.getPropertiesValues();

		for (int x=0;x<props.size();x++){
			PropertyForm prop = (PropertyForm)props.get(x);
			Long propId = prop.getPropertyId();
			CommonLogger.logDebug(log,"The property id is " + x + " is :" + propId.toString());
			CommonLogger.logDebug(log,"The property name for " + x + " is :" + prop.getName());
		}


		String proptyXML = svsMgr.getServicePropertiesXML(svsId);

		//response.setContentType("text/xml");

		PrintWriter out = response.getWriter();
		out.println(proptyXML);
		out.flush();

        return null;
	}

*/

    public ActionForward getProperties(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

		CommonLogger.logDebug(log,"In ServicesAction:getService()");
		String svsId = request.getParameter(BSIConstants.SERVICE_ID);
		CommonLogger.logDebug(log,"The service id is " + svsId);
		Set propertySet = null;
		if (svsId != null && !svsId.equalsIgnoreCase("null")){
			ServiceManager svsMgr = new ServiceManager();
			propertySet = svsMgr.getServiceProperties(svsId);
		}			
		request.setAttribute(BSIConstants.SERVICE_PROPERTIES_SET, propertySet);
		return mapping.findForward("success");
	}

    
    public ActionForward getPropertiesXML1(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

    		CommonLogger.logDebug(log,"In ServicesAction:getPropertiesXML1");
    		Set propertySet = null;
    		String propertyXML = "";

    		if (request.getAttribute(BSIConstants.SERVICE_PROPERTIES_SET) != null){
        		ServiceManager svsMgr = new ServiceManager();
    			propertySet = (Set)request.getAttribute(BSIConstants.SERVICE_PROPERTIES_SET);
    			propertyXML = svsMgr.createPropertiesXML(propertySet);
        	}
    		
    		response.setContentType("text/xml");

    		PrintWriter out = response.getWriter();
    		out.println(propertyXML);
    		out.flush();
    		return null;
    	}

    
    public ActionForward getPropertiesXML2(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

    		CommonLogger.logDebug(log,"In ServicesAction:getPropertiesXML2");
    		Set propertySet = null;
    		String propertyXML = "";

    		if (request.getAttribute(BSIConstants.SERVICE_PROPERTIES_SET) != null){
        		ServiceManager svsMgr = new ServiceManager();
    			propertySet = (Set)request.getAttribute(BSIConstants.SERVICE_PROPERTIES_SET);
    			propertyXML = svsMgr.createPropertiesXML2(propertySet);
        	}
    		
    		response.setContentType("text/xml");

    		PrintWriter out = response.getWriter();
    		out.println(propertyXML);
    		out.flush();
    		return null;
    	}
    
    public ActionForward getSupplierPropertiesXML(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

    		CommonLogger.logDebug(log,"In ServicesAction:getSupplierPropertiesXML");
    		Set propertySet = null;
    		String propertyXML = "";
    		String serviceId = request.getParameter(BSIConstants.SERVICE_ID);
    		String supplierId = request.getParameter(BSIConstants.PERSON_ID);
    		if (request.getAttribute(BSIConstants.SERVICE_PROPERTIES_SET) != null){
    			PersonManager personMgr = new PersonManager();
    			Set<SupplierServicePropertyValues> subscribedPropValueSet = personMgr.getSupplierSubscribedSrvPropertyValues(supplierId, serviceId);
        		ServiceManager svsMgr = new ServiceManager();
    			propertySet = (Set)request.getAttribute(BSIConstants.SERVICE_PROPERTIES_SET);
    			propertyXML = svsMgr.createSupplierSubscribedPropertiesXML(propertySet,subscribedPropValueSet);
        	}
    		
    		response.setContentType("text/xml");

    		PrintWriter out = response.getWriter();
    		out.println(propertyXML);
    		out.flush();
    		return null;
    	}
    
    /**
     * Method to return jus the html from the list of properties.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
/*    public ActionForward getPropertiesXML(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

    		CommonLogger.logDebug(log,"In ServicesAction:getService()");
    		String svsId = request.getParameter(BSIConstants.SERVICE_ID);
    		CommonLogger.logDebug(log,"The service id is " + svsId);


    		List<Property> propertyList = (List)request.getAttribute("PROPERTY_LIST");
    		ServiceManager svsMgr = new ServiceManager();
    		String proptyXML = svsMgr.getServicePropertiesXML(propertyList);

    		//response.setContentType("text/xml");

    		PrintWriter out = response.getWriter();
    		out.println(proptyXML);
    		out.flush();

            return null;
    	}
*/

}

