package com.bsi.client.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.MappingDispatchAction;

import com.bsi.client.actions.forms.BuyerForm;
import com.bsi.client.actions.forms.PersonForm;
import com.bsi.client.managers.PersonManager;
import com.bsi.client.managers.RegionManager;
import com.bsi.client.managers.ServiceManager;
import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.Person;
import com.bsi.common.beans.Service;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;




/**
 * Retrieve and process data from the submitted form
 *
 * @version $Rev: 421486 $ $Date: 2006-07-12 20:37:08 -0700 (Wed, 12 Jul 2006) $
 */
public class BuyerAction extends MappingDispatchAction {

	private static Log log = LogFactory.getLog(BuyerAction.class);

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for ProcessFormAction.
     */
    public BuyerAction() {
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




/*
 * This method retrieves services, countries information for displaying the create person form
 *
 */


    public ActionForward create(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

		CommonLogger.logDebug(log,"In BuyerAction:create() ");
      	ActionErrors errors = new ActionErrors();
        
        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }

		try
		{
                  //Locale test.
                       String srvsId = request.getParameter(BSIConstants.SERVICE_ID);

                       if (srvsId != null && srvsId.trim().length() > 0)
                       {
                         ServiceManager svsMgr = new ServiceManager();
                         Service srvs = svsMgr.getService(srvsId,true);

                         CommonLogger.logDebug(log,"The service id found in request is " + srvsId);
                         if (srvs != null)
                         {
                           CommonLogger.logDebug(log,"The service retrieved is " + srvs);

                           PersonForm personForm = (PersonForm) form;
                           personForm.setServiceIdName(srvs.getName());
                           String[] serviceIds = new String[1];
                           serviceIds[0] = srvs.getNodeId().toString();
                           personForm.setServiceId(serviceIds);
                           CommonLogger.logDebug(log,"The service id is " + personForm.getServiceId()[0]);
                           CommonLogger.logDebug(log,"The service name is " + personForm.getServiceIdName());
                         }

                       }

			//Get the list of all services available.
			//ServiceManager svsMgr = new ServiceManager();
			//List svsList = svsMgr.getAllServices();
			//request.getSession().setAttribute(BSIConstants.SERVICES_LIST,svsList);
			//Get the list of all countries.
			List locnList = getCountriesList();
			request.getSession().setAttribute(BSIConstants.COUNTRY_LIST,locnList);
		}
		catch(BSIException ex1)
		{
        	CommonLogger.logDebug(log,"In BuyerAction:add() \n exception occured. Exception message is " + ex1.getMessage());
	        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage(ex1.getErrorCode(),ex1.getMessage()));
	        saveErrors(request, errors);
		}
		catch(Exception ex2){
			throw ex2;
		}

		finally{
			request.setAttribute(BSIConstants.ACTION_TYPE,BSIConstants.CREATE);
		}
		return mapping.findForward("success");
    }


    public ActionForward add(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

		CommonLogger.logDebug(log,"In BuyerAction:add() ");

        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }

        //DynaValidatorForm dynaForm = (DynaValidatorForm) form;

		try
		{
			PersonManager clientHandler = new PersonManager();
			clientHandler.createBuyer(form);
        	ActionMessages msgs = new ActionMessages();
	        msgs.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("creation.action.succesfull"));
	        saveMessages(request, msgs);
			//dynaForm.getMap().clear();

		}
		catch(BSIException ex1)
		{
        	ActionErrors errors = new ActionErrors();
        	CommonLogger.logDebug(log,"In BuyerAction:add() \n exception occured. Exception message is " + ex1.getMessage());
	        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage(ex1.getErrorCode(),ex1.getMessage()));
	        if (ex1.getErrorCode() == "1005"){
		        return mapping.findForward("alreadyRegistered");
	        	
	        }
	        saveErrors(request, errors);
	        return mapping.findForward("failure");
		}
		catch(Exception ex2){
			throw ex2;
		}

		finally{
			request.setAttribute(BSIConstants.ACTION_TYPE,BSIConstants.CREATE);
		}


        return mapping.findForward("success");
    }



   public ActionForward personInfo(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        // If user pressed 'Cancel' button,
        // return to home page

		CommonLogger.logDebug(log,"In BuyerAction:getPersonInfo() ");

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }


        String personId = request.getParameter(BSIConstants.PERSON_ID);

		CommonLogger.logDebug(log,"Executing the BuyerAction.getPersonInfo(), the person id is :" + personId);

		try
		{
			PersonManager clientHandler = new PersonManager();
			Person personObj = clientHandler.getPerson(personId);

			BuyerForm buyerForm = (BuyerForm)form;
			buyerForm.setName(personObj.getName());
			buyerForm.setEmail(personObj.getEmail());
			buyerForm.setPhoneNumber(personObj.getPhoneNumber());
			buyerForm.setAddress(personObj.getAddress());
		
		}
		catch(BSIException ex1)
		{
        	ActionErrors errors = new ActionErrors();
        	CommonLogger.logDebug(log,"In BuyerAction:edit() \n exception occured. Exception message is " + ex1.getMessage());
	        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage(ex1.getErrorCode(),ex1.getMessage()));
	        saveErrors(request, errors);
		}
		catch(Exception ex2){
			throw ex2;
		}

		finally{
			request.setAttribute(BSIConstants.ACTION_TYPE,BSIConstants.VIEW);
		}
        return mapping.findForward("success");
    }




/*
   public ActionForward edit(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        // If user pressed 'Cancel' button,
        // return to home page

        CommonLogger.logDebug(log,"In BuyerAction:edit() ");

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }


        String personId = request.getParameter("id");

		CommonLogger.logInfo(log,"Executing the view action for the person id:");

		try
		{
			PersonManager clientHandler = new PersonManager();
			Person personObj = clientHandler.getPerson(personId);

			//Get the service region for the person
			//TODO should support multiple service region, each for a service.
			Long serviceId = null;
			Set srvs = personObj.getServices();
			if (srvs != null){
				Object[] services = srvs.toArray();
				if (services.length > 0)
					serviceId = ((Service)services[0]).getNodeId();
			}

			//Get the PersonServiceRegion for the service
			Long svsRegnId = null;

			PersonSrvsRegionManager ssrMgr = new PersonSrvsRegionManager();
			if (personObj.getPersonType().equalsIgnoreCase("S"))
			{
				SupplierSrvsRegion ssr = ssrMgr.getSupplierSrvsRegion(personObj.getId(),serviceId);
				svsRegnId = ssr.getServiceRegionId();
			}
			else if (personObj.getPersonType().equalsIgnoreCase("B"))
			{
				BuyerSrvsRegion bsr = ssrMgr.getBuyerSrvsRegion(personObj.getId(),serviceId);
				svsRegnId = bsr.getServiceRegionId();
			}

			ServiceRegion svsRegn = null;
			LocationManager locMgr = new LocationManager();
			if (svsRegnId != null)
				svsRegn = locMgr.getServiceRegion(svsRegnId);

			//Get the list of all countries.
			String countryId = svsRegn.getCountry().getId().toString();
			String stateId = svsRegn.getState().getId().toString();
			String locationId = svsRegn.getLocation().getId().toString();

			CommonLogger.logInfo(log,"the country id =" + countryId + ",state id = " + stateId);

			List locnList = getCountriesList();
			request.getSession().setAttribute(BSIConstants.COUNTRY_LIST,locnList);


			//Get the list of States for the selected country
			Set states = getStates(countryId);
			request.setAttribute(BSIConstants.STATE_LIST,states);


			//Get the list of locations for the selected state
			Set locns = getLocations(stateId);
			request.setAttribute(BSIConstants.REGION_LIST,locns);


			//Set the values in the form
/*
			DynaValidatorForm dynaForm = (DynaValidatorForm) form;
			dynaForm.set("name",personObj.getName());
			dynaForm.set("email",personObj.getEmail());
			dynaForm.set("phoneNumber",personObj.getPhoneNumber());
			dynaForm.set("address",personObj.getAddress());
			//Get the string array of the serviceIds
			Set svs = personObj.getServices();
			Iterator iter = svs.iterator();
			int size = svs.size();
			String[] nodeIds = new String[size];
			int x=0;
			while(iter.hasNext())
			{
				nodeIds[x]=((Service)iter.next()).getNodeId().toString();
				x++;
			}

			dynaForm.set("serviceIds",nodeIds);

			//
			dynaForm.set("countryId",countryId);
			dynaForm.set("stateId",stateId);
			dynaForm.set("locationId",locationId);
*/
//
/*


			if (personObj.getPersonType().equalsIgnoreCase("B"))
			{
				CommonLogger.logDebug(log,"Populating the buyer form");
				BuyerForm buyerForm = (BuyerForm)form;
				buyerForm.setName(personObj.getName());
				buyerForm.setEmail(personObj.getEmail());
				buyerForm.setPhoneNumber(personObj.getPhoneNumber());
				buyerForm.setAddress(personObj.getAddress());
				buyerForm.setCountryId(countryId);
				buyerForm.setStateId(stateId);
				buyerForm.setLocationId(locationId);

				/*
				DynaValidatorForm dynaForm = (DynaValidatorForm) form;
				dynaForm.set("name",personObj.getName());
				dynaForm.set("email",personObj.getEmail());
				dynaForm.set("phoneNumber",personObj.getPhoneNumber());
				dynaForm.set("address",personObj.getAddress());
				*/

/*
				//Get the string array of the serviceIds
				Set svs = personObj.getServices();
				Iterator iter = svs.iterator();
				int size = svs.size();
				String[] nodeIds = new String[size];
				int x=0;
				while(iter.hasNext())
				{
					nodeIds[x]=((Service)iter.next()).getNodeId().toString();
					x++;
				}

				buyerForm.setServiceIds(nodeIds);
				/*
				dynaForm.set("serviceIds",nodeIds);
				//
				dynaForm.set("countryId",countryId);
				dynaForm.set("stateId",stateId);
				dynaForm.set("locationId",locationId);
			}
			else
			{

			}


			//
			request.setAttribute(BSIConstants.SERVICE_REGION_SELECTED,svsRegn);
			request.setAttribute(BSIConstants.SERVICES_LIST,personObj.getServices());
		}
		catch(BSIException ex1)
		{
        	ActionErrors errors = new ActionErrors();
        	CommonLogger.logDebug(log,"In BuyerAction:edit() \n exception occured. Exception message is " + ex1.getMessage());
	        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage(ex1.getErrorCode(),ex1.getMessage()));
	        saveErrors(request, errors);
		}
		catch(Exception ex2){
			throw ex2;
		}

		finally{
			request.setAttribute(BSIConstants.ACTION_TYPE,BSIConstants.EDIT);
		}



        return mapping.findForward("success");
    }


*/

/*

    public ActionForward editPersonalInfo(
         ActionMapping mapping,
         ActionForm form,
         HttpServletRequest request,
         HttpServletResponse response)
         throws Exception {

         // If user pressed 'Cancel' button,
         // return to home page

         CommonLogger.logDebug(log,"In BuyerAction:editPersonalInfo() ");

         if (isCancelled(request)) {
             return mapping.findForward("home");
         }


         String personId = request.getParameter("id");

                 CommonLogger.logInfo(log,"Executing the view action for the person id:" + personId);

                 try
                 {
                         PersonManager clientHandler = new PersonManager();
                         Person personObj = clientHandler.getPerson(personId);

                         if (personObj.getPersonType().equalsIgnoreCase("B"))
                         {
                                 CommonLogger.logDebug(log,"Populating the buyer form");
                                 PersonForm personrForm = (PersonForm)form;
                                 PropertyUtils.copyProperties(personrForm,personObj);
                         }
                 }
                 catch(BSIException ex1)
                 {
                 ActionErrors errors = new ActionErrors();
                 CommonLogger.logDebug(log,"In BuyerAction:editPersonalInfo() \n exception occured. Exception message is " + ex1.getMessage());
                 errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage(ex1.getErrorCode(),ex1.getMessage()));
                 saveErrors(request, errors);
                 }
                 catch(Exception ex2){
                         throw ex2;
                 }

                 finally{
                         request.setAttribute(BSIConstants.ACTION_TYPE,BSIConstants.EDIT);
                 }
         return mapping.findForward("success");
     }


*/

/*
   public ActionForward list(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {


		CommonLogger.logDebug(log,"In BuyerAction:list() ");

        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }

		try
		{
			PersonManager clientHandler = new PersonManager();

			CommonLogger.logDebug(log,"The person type is " + request.getParameter(BSIConstants.PERSON_TYPE));



			List personsList = clientHandler.listPersons(request.getParameter(BSIConstants.PERSON_TYPE));

			request.getSession().setAttribute(BSIConstants.PERSONS_LIST,personsList);


/*

			List supplierList = clientHandler.listSuppliers();

			request.getSession().setAttribute(BSIConstants.SUPPLIER_LIST,supplierList);
			request.getSession().setAttribute(BSIConstants.SORT_LIST_NAME,BSIConstants.SUPPLIER_LIST);


			SortParams sortParams = new SortParams();
			sortParams.setActionType("goto");
			sortParams.setSortColumn("Id");
			sortParams.setSortOrder("asc");
			sortParams.setRowsPerPage(3);
			sortParams.setGoToPage(2);

			request.getSession().setAttribute(BSIConstants.SORT_PARAMS,sortParams);


		}
		catch(BSIException ex1)
		{
        	ActionErrors errors = new ActionErrors();
        	CommonLogger.logDebug(log,"In BuyerAction:list() \n exception occured. Exception message is " + ex1.getMessage());
	        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage(ex1.getErrorCode(),ex1.getMessage()));
	        saveErrors(request, errors);
		}
		catch(Exception ex2){
			throw ex2;
		}

        // Forward to result page
        return mapping.findForward("success");
    }

*/

	public List getCountriesList() throws Exception{
		CommonLogger.logDebug(log,"In PersonAction:getCountriesList() ");

                RegionManager regMgr = new RegionManager();
                List locnList = regMgr.getRootRegions();

		return locnList;
	}


/*
	public List getCountriesList() throws Exception{
		CommonLogger.logDebug(log,"In BuyerAction:getCountriesList() ");

		LocationManager locnMgr = new LocationManager();
		List locnList = locnMgr.getCountries();
		return locnList;
	}


	public Set getStates(String countryId) throws Exception{
		CommonLogger.logDebug(log,"In BuyerAction:getStates() ");

		LocationManager locnMgr = new LocationManager();
		Set states = locnMgr.getStates(countryId);
		return states;
	}

	public Set getLocations(String stateId) throws Exception{
		CommonLogger.logDebug(log,"In BuyerAction:getLocations() ");

		LocationManager locnMgr = new LocationManager();
		Set locns = locnMgr.getLocations(stateId);
		return locns;
	}
*/


}
