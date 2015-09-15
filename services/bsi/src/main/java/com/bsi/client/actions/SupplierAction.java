package com.bsi.client.actions;

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

import com.bsi.client.managers.PersonManager;
import com.bsi.client.managers.RegionManager;
import com.bsi.client.managers.ServiceManager;
import com.bsi.client.util.BSIConstants;
import com.nms.util.log.CommonLogger;



/**
 * Retrieve and process data from the submitted form
 *
 * @version $Rev: 421486 $ $Date: 2006-07-12 20:37:08 -0700 (Wed, 12 Jul 2006) $
 */
public class SupplierAction extends MappingDispatchAction {

	private static Log log = LogFactory.getLog(SupplierAction.class);

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for ProcessFormAction.
     */
    public SupplierAction() {
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

/*
    public ActionForward subSrvsDisplay(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

		CommonLogger.logDebug(log,"In SupplierAction:create() ");

        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }

		try
		{
			//Get the list of all services available.
			ServiceManager svsMgr = new ServiceManager();
			List svsList = svsMgr.getAllServices();
			request.getSession().setAttribute(BSIConstants.SERVICES_LIST,svsList);
			//Get the list of all countries.
			List locnList = getCountriesList();
			request.getSession().setAttribute(BSIConstants.COUNTRY_LIST,locnList);


			SubscribeServiceForm ssform = (SubscribeServiceForm)form;
			ArrayList locns = new ArrayList();

			LocationForm locform = null;


			for (int i=0;i<1;i++){
				locform = new LocationForm();
				locns.add(locform);
			}

			ssform.populateForm(locns);


		}
		catch(BSIException ex1)
		{
        	ActionErrors errors = new ActionErrors();
        	CommonLogger.logDebug(log,"In SupplierAction:add() \n exception occured. Exception message is " + ex1.getMessage());
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

*/

/*
    public ActionForward subscribeSrvs(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception
    {

		CommonLogger.logDebug(log,"In SupplierAction:subscribeSrvs() ");


		try
		{
			String personId = request.getParameter(BSIConstants.PERSON_ID);
			String serviceId = request.getParameter("serviceIds");
                        String personType = request.getParameter(BSIConstants.PERSON_TYPE);


			CommonLogger.logDebug(log,"The personId is " + personId + ", the service id is " + serviceId);

			SubscribeServiceForm ssform = (SubscribeServiceForm)form;
			ArrayList locns = ssform.getLocns();

			List locnList = new ArrayList();
			HashMap locnMap = null;
			LocationForm locn = null;

			for (int x=0;x<locns.size();x++)
			{
				locn = (LocationForm)locns.get(x);

				locnMap = new HashMap();
				locnMap.put("CountryId",locn.getCountryId());
				locnMap.put("StateId",locn.getStateId());
				locnMap.put("LocationId",locn.getLocationId());
				locnList.add(locnMap);
			}

			if (locnList.size() == 0) throw new Exception("No regions selected");


			PersonManager clientHandler = new PersonManager();
			clientHandler.unSubscribeServiceLocation(personId,serviceId,personType);
			clientHandler.subscribeService(personId,serviceId,locnList,personType);
        	ActionMessages msgs = new ActionMessages();
	        msgs.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("creation.action.succesfull"));
	        saveMessages(request, msgs);


		}
		catch(BSIException ex1)
		{
        	ActionErrors errors = new ActionErrors();
        	CommonLogger.logDebug(log,"In SupplierAction:subscribeSrvs() \n exception occured. Exception message is " + ex1.getMessage());
	        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage(ex1.getErrorCode(),ex1.getMessage()));
	        saveErrors(request, errors);
		}
		catch(Exception ex2){
			throw ex2;
		}

		return mapping.findForward("success");
	}

*/


/*
    public ActionForward createSupplier(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

		CommonLogger.logDebug(log,"In SupplierAction:createSupplier() ");

        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }

        //DynaValidatorForm dynaForm = (DynaValidatorForm) form;

		try
		{
			PersonManager clientHandler = new PersonManager();
			clientHandler.createPerson(form);
        	ActionMessages msgs = new ActionMessages();
	        msgs.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("creation.action.succesfull"));
	        saveMessages(request, msgs);
			//dynaForm.getMap().clear();

		}
		catch(BSIException ex1)
		{
        	ActionErrors errors = new ActionErrors();
        	CommonLogger.logDebug(log,"In SupplierAction:createSupplier() \n exception occured. Exception message is " + ex1.getMessage());
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

*/

/*
   public ActionForward editSubServiceLocation(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        // If user pressed 'Cancel' button,
        // return to home page

		CommonLogger.logDebug(log,"In SupplierAction:edit() ");

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }

		try
		{

			String personId = request.getParameter("personId");
			String serviceId = request.getParameter("serviceId");
			String serviceName = request.getParameter("serviceName");

			CommonLogger.logInfo(log,"Executing the editSubServiceLocation action for the person id:" + personId + ",serviceId = " + serviceId + ",serviceName = " + serviceName);

			PersonSrvsRegionManager ssrMgr = new PersonSrvsRegionManager();

			List serviceRegnList = ssrMgr.getSubscribedSrvsRegions(personId,serviceId,BSIConstants.SUPPLIER);


			SubscribeServiceForm subForm = 	(SubscribeServiceForm)form;

			LocationForm locForm = null;
			ServiceRegion svsRegn = null;
			String countryId = null;
			String countryName = null;
			String stateId = null;
			String stateName = null;
			String locationId = null;
			String locationName = null;
			List locnsList = new ArrayList();

			for (int z=0;z<serviceRegnList.size();z++)
			{
				locForm = new LocationForm();

				svsRegn = (ServiceRegion)serviceRegnList.get(z);
				//Get the list of all countries.
				countryId = svsRegn.getCountry().getId().toString();
				countryName = svsRegn.getCountry().getName();

				stateId = svsRegn.getState().getId().toString();
				stateName =  svsRegn.getState().getName();
				locationId = svsRegn.getLocation().getId().toString();
				locationName =  svsRegn.getLocation().getName();

				locForm.setCountryId(countryId);
				locForm.setCountryName(countryName);
				locForm.setStateId(stateId);
				locForm.setStateName(stateName);
				locForm.setLocationId(locationId);
				locForm.setLocationName(locationName);
				locnsList.add(locForm);
			}

			subForm.populateForm(locnsList);

			List countryList = getCountriesList();
			request.setAttribute(BSIConstants.COUNTRY_LIST,countryList);


		}
		catch(BSIException ex1)
		{
        	ActionErrors errors = new ActionErrors();
        	CommonLogger.logDebug(log,"In SupplierAction:edit() \n exception occured. Exception message is " + ex1.getMessage());
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


		CommonLogger.logDebug(log,"In SupplierAction:list() ");

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
        	CommonLogger.logDebug(log,"In SupplierAction:list() \n exception occured. Exception message is " + ex1.getMessage());
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

/*

   public ActionForward listSubscribedServices(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {


		CommonLogger.logDebug(log,"In SupplierAction:subscribeService() ");

       	ActionMessages msgs = new ActionMessages();
		Set srvs = null;

		try
		{

			String personId = request.getParameter(BSIConstants.PERSON_ID);

			CommonLogger.logDebug(log,"The person id is " + personId);

			PersonManager clientHandler = new PersonManager();
			Person personObj = clientHandler.getPerson(personId);

			//Get the service region for the person
			//TODO should support multiple service region, each for a service.
			Long serviceId = null;
			srvs = personObj.getServices();
			if (log.isDebugEnabled())
			{
				if (srvs != null){
					Object[] services = srvs.toArray();
					if (services.length > 0)
						serviceId = ((Service)services[0]).getNodeId();
						CommonLogger.logDebug(log,"The service id is " + serviceId);
				}
			}

		}
		catch(BSIException ex1)
		{
        	ActionErrors errors = new ActionErrors();
        	CommonLogger.logDebug(log,"In SupplierAction:subscribeService()  \n exception occured. Exception message is " + ex1.getMessage());
	        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage(ex1.getErrorCode(),ex1.getMessage()));
	        saveErrors(request, errors);
		}
		finally
		{
			request.setAttribute(BSIConstants.SUBSCRIBED_SERVICES_LIST,srvs);
		}
		return mapping.findForward("success");
	}

*/
   public ActionForward listBuyerProperties(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

		CommonLogger.logDebug(log,"In SupplierAction.listBuyerProperties()");


		String serviceId = request.getParameter(BSIConstants.SERVICE_ID);
		String supplierId= request.getParameter(BSIConstants.PERSON_ID);
		String ssrId = request.getParameter(BSIConstants.SUPPLIER_SERVICE_REGION_ID);

		CommonLogger.logDebug(log,"The request parameters are, serviceId=" + serviceId + ",supplierId=" + supplierId + ",ssrId=" + ssrId);

		//Get the service properties.

		ServiceManager srvsMgr = new ServiceManager();
		Set srvsProperties = srvsMgr.getServiceProperties(serviceId);

                CommonLogger.logDebug(log,"The srvsProperties are : " + srvsProperties);

		//Get the list of buyer and the property values.
		PersonManager personMgr = new PersonManager();
		List bsrList = personMgr.getBuyersForSupplier(supplierId,serviceId,ssrId);

                CommonLogger.logDebug(log,"The bsrList is : " + bsrList);

		request.setAttribute("BuyerProperties",bsrList);


		return mapping.findForward("success");

	}

/*
        public ActionForward listSuppliersForService(
              ActionMapping mapping,
              ActionForm form,
              HttpServletRequest request,
              HttpServletResponse response)
              throws Exception {


            CommonLogger.logDebug(log,"In SupplierAction.listSuppliersForService()");


            String serviceId = request.getParameter(BSIConstants.SERVICE_ID);
            String ssrId = request.getParameter(BSIConstants.SUPPLIER_SERVICE_REGION_ID);
            Set suppSet = null;
            List newSuppList = new ArrayList();

            CommonLogger.logDebug(log,"The request parameters are, serviceId=" + serviceId + ",ssrId=" + ssrId);
            try {

              //Get the service region
              SubscribeServiceForm ssform = (SubscribeServiceForm)form;
              ArrayList locns = ssform.getLocns();

              List regionIds = new ArrayList();
              HashMap locnMap = null;
              LocationForm locn = null;

              for (int x=0;x<locns.size();x++)
              {
                      locn = (LocationForm)locns.get(x);
                      regionIds.add(locn.getSubscribedRegionId());
                      //regionIds.add(locnMap);
/*
                      locnMap = new HashMap();
                      locnMap.put("CountryId",locn.getCountryId());
                      locnMap.put("StateId",locn.getStateId());
                      locnMap.put("LocationId",locn.getLocationId());
 */

/*

              if (regionIds.size() == 0) throw new Exception("No regions selected");


              //LocationManager locMgr = new LocationManager();
              //List srvRegnIds = locMgr.getServiceRegions(locnList);


              PersonManager personMgr = new PersonManager();
              suppSet = personMgr.getSuppliersForService(serviceId, regionIds);

              //Convert this supplier set to the list
              Iterator iter = suppSet.iterator();

              while (iter.hasNext())
              {
                Person person = (Person)iter.next();
                CommonLogger.logDebug(log, "The person name is " + person.getName());
               newSuppList.add(person);
              }


              CommonLogger.logDebug(log, "The supplier set contains : " + newSuppList);
              //request.getSession().setAttribute(BSIConstants.PERSONS_LIST, suppSet);
            }
            catch (BSIException ex1) {
              ActionErrors errors = new ActionErrors();
              CommonLogger.logDebug(log,
                                    "In SupplierAction:listSuppliersForService()  \n exception occured. Exception message is " +
                                    ex1.getMessage());
              errors.add(ActionErrors.GLOBAL_MESSAGE,
                         new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
              saveErrors(request, errors);
            }
            finally {
              request.getSession().setAttribute(BSIConstants.PERSONS_LIST, newSuppList);
            }
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
              public List getChildRegions(String regionId) throws Exception{
                      CommonLogger.logDebug(log,"In PersonAction:getChildRegions() ");

                      RegionManager regMgr = new RegionManager();
                      List childRegns = regMgr.getChildRegions(regionId);
                      return childRegns;
              }
*/

/*
	public List getCountriesList() throws Exception{
		CommonLogger.logDebug(log,"In SupplierAction:getCountriesList() ");

		LocationManager locnMgr = new LocationManager();
		List locnList = locnMgr.getCountries();
		return locnList;
	}

	public Set getStates(String countryId) throws Exception{
		CommonLogger.logDebug(log,"In SupplierAction:getStates() ");

		LocationManager locnMgr = new LocationManager();
		Set states = locnMgr.getStates(countryId);
		return states;
	}

	public Set getLocations(String stateId) throws Exception{
		CommonLogger.logDebug(log,"In SupplierAction:getLocations() ");

		LocationManager locnMgr = new LocationManager();
		Set locns = locnMgr.getLocations(stateId);
		return locns;
	}
*/

}
