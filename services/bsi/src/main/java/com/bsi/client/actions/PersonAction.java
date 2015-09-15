package com.bsi.client.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.MappingDispatchAction;

import com.bsi.client.actions.forms.BuyerPropertiesForm;
import com.bsi.client.actions.forms.LocationForm;
import com.bsi.client.actions.forms.PersonForm;
import com.bsi.client.actions.forms.SubscribeServiceForm;
import com.bsi.client.managers.PersonManager;
import com.bsi.client.managers.PersonSrvsRegionManager;
import com.bsi.client.managers.RegionManager;
import com.bsi.client.managers.ServiceManager;
import com.bsi.client.session.User;
import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.Person;
import com.bsi.common.beans.Region;
import com.bsi.common.beans.Service;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

/**
 * Retrieve and process data from the submitted form
 * 
 * @version $Rev: 421486 $ $Date: 2006-07-12 20:37:08 -0700 (Wed, 12 Jul 2006) $
 */
public class PersonAction extends MappingDispatchAction {

	private static Log log = LogFactory.getLog(PersonAction.class);

	// ------------------------------------------------------------ Constructors

	/**
	 * Constructor for ProcessFormAction.
	 */
	public PersonAction() {
		super();
	}

	// ---------------------------------------------------------- Action Methods

	/**
	 * Process the request and return an <code>ActionForward</code> instance
	 * describing where and how control should be forwarded, or
	 * <code>null</code>if the response has already been completed.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * 
	 * @exception Exception
	 *                if the application logic throws an exception
	 * 
	 * @return the ActionForward for the next view
	 */

	public ActionForward subSrvsDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In SupplierAction:create() ");

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		try {
			// Get the list of all services available.
			ServiceManager svsMgr = new ServiceManager();
			List svsList = svsMgr.getAllServices();
			request.getSession().setAttribute(BSIConstants.SERVICES_LIST,
					svsList);
			// Get the list of all countries.
			List locnList = getCountriesList();
			request.setAttribute(BSIConstants.COUNTRY_LIST, locnList);

			SubscribeServiceForm ssform = (SubscribeServiceForm) form;
			ssform.reset(mapping, request);
			ArrayList locns = new ArrayList();

			LocationForm locform = null;

			for (int i = 0; i < 1; i++) {
				locform = new LocationForm();
				locns.add(locform);
			}

			ssform.populateForm(locns);

		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In SupplierAction:add() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} catch (Exception ex2) {
			throw ex2;
		}

		finally {
			request.setAttribute(BSIConstants.ACTION_TYPE,
					request.getParameter(BSIConstants.ACTION_TYPE));
		}
		return mapping.findForward("success");
	}

	public ActionForward subscribeSrvs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In PersonAction:subscribeSrvs() ");

		try {
			String personId = request.getParameter(BSIConstants.PERSON_ID);
			String serviceId = request.getParameter(BSIConstants.SERVICE_ID);
			String personType = request.getParameter(BSIConstants.PERSON_TYPE);

			CommonLogger.logDebug(log, "The personId is " + personId
					+ ", the service id is " + serviceId
					+ ",the personType is " + personType);

			SubscribeServiceForm ssform = (SubscribeServiceForm) form;
			ArrayList locns = ssform.getLocns();

			List regionIds = new ArrayList();
			HashMap locnMap = null;
			LocationForm locn = null;

			for (int x = 0; x < locns.size(); x++) {
				locn = (LocationForm) locns.get(x);
				CommonLogger.logDebug(log,
						"The regionId1:" + locn.getRegionId1());
				CommonLogger.logDebug(log,
						"The regionId2:" + locn.getRegionId2());
				CommonLogger.logDebug(log,
						"The regionId3:" + locn.getRegionId3());
				CommonLogger.logDebug(log,
						"The regionId4:" + locn.getRegionId4());
				CommonLogger.logDebug(log,
						"The regionId5:" + locn.getRegionId5());
				CommonLogger.logDebug(log,
						"The regionId16" + locn.getRegionId6());

				regionIds.add(locn.getSubscribedRegionId());
				/*
				 * locnMap = new HashMap();
				 * locnMap.put("CountryId",locn.getCountryId());
				 * locnMap.put("StateId",locn.getStateId());
				 * locnMap.put("LocationId",locn.getLocationId());
				 * locnList.add(locnMap);
				 */

			}

			if (regionIds.size() == 0)
				throw new Exception("No regions selected");

			// Get the buyer properties form, in case buyer.
			PersonManager clientHandler = new PersonManager();
			List buyerPropList = null;
/*			if (personType.equalsIgnoreCase(BSIConstants.BUYER)) {
*/				buyerPropList = ((BuyerPropertiesForm) ssform)
						.getPropertiesValues();
//			}
			clientHandler.updateSubscribedServices(personId, serviceId,
					regionIds, personType, buyerPropList);
			// clientHandler.unSubscribeServiceLocation(personId,serviceId,personType);
			// clientHandler.subscribeService(personId,serviceId,locnList,personType);
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"creation.action.succesfull"));
			saveMessages(request, msgs);

		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In SupplierAction:subscribeSrvs() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} catch (Exception ex2) {
			throw ex2;
		}
		return mapping.findForward("success");
	}

	public ActionForward unSubscribeSrvs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CommonLogger.logDebug(log, "In PersonAction:unSubscribeSrvs() ");

		try {
			String personId = request.getParameter(BSIConstants.PERSON_ID);
			String serviceId = request.getParameter(BSIConstants.SERVICE_ID);
			String personType = request.getParameter(BSIConstants.PERSON_TYPE);

			CommonLogger.logDebug(log, "The personId is " + personId
					+ ", the service id is " + serviceId + ", person type is "
					+ personType);

			PersonManager clientHandler = new PersonManager();
			clientHandler.unSubscribeService(personId, serviceId, personType);
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"creation.action.succesfull"));
			saveMessages(request, msgs);
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In SupplierAction:subscribeSrvs() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} catch (Exception ex2) {
			throw ex2;
		}

		return mapping.findForward("success");
	}

	/*
	 * This method retrieves services, countries information for displaying the
	 * create person form
	 */

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In PersonAction:create() ");

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		try {
			// Get the list of all services available.
			ServiceManager svsMgr = new ServiceManager();
			List svsList = svsMgr.getAllServices();
			request.getSession().setAttribute(BSIConstants.SERVICES_LIST,
					svsList);
			// Get the list of all countries.
			List locnList = getCountriesList();
			request.getSession().setAttribute(BSIConstants.COUNTRY_LIST,
					locnList);
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In PersonAction:add() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} catch (Exception ex2) {
			throw ex2;
		}

		finally {
			request.setAttribute(BSIConstants.ACTION_TYPE, BSIConstants.CREATE);
		}
		return mapping.findForward("success");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In PersonAction:delete() ");

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		try {
			// Get the list of all services available.
			String personId = request.getParameter(BSIConstants.PERSON_ID);
			PersonManager personMgr = new PersonManager();
			personMgr.deletePerson(new Long(personId));
        	ActionMessages msgs = new ActionMessages();
	        msgs.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("creation.action.succesfull"));
	        saveMessages(request, msgs);
			
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In PersonAction:delete() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} catch (Exception ex2) {
			throw ex2;
		}
		return mapping.findForward("success");
	}

	public ActionForward listBuyersForService(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CommonLogger.logDebug(log, "In PersonAction.listBuyersForService()");

		String serviceId = request.getParameter(BSIConstants.SERVICE_ID);
		String ssrId = request.getParameter(BSIConstants.SERVICE_REGION_ID);
		List buyerList = null;

		CommonLogger.logDebug(log, "The request parameters are, serviceId="
				+ serviceId + ",ssrId=" + ssrId);
		try {

			// Get the service region
			SubscribeServiceForm ssform = (SubscribeServiceForm) form;
			ArrayList locns = ssform.getLocns();

			List regionIds = new ArrayList();
			HashMap locnMap = null;
			LocationForm locn = null;

			for (int x = 0; x < locns.size(); x++) {
				locn = (LocationForm) locns.get(x);
				regionIds.add(locn.getSubscribedRegionId());
				/*
				 * locnMap = new HashMap();
				 * locnMap.put("CountryId",locn.getCountryId());
				 * locnMap.put("StateId",locn.getStateId());
				 * locnMap.put("LocationId",locn.getLocationId());
				 * locnList.add(locnMap);
				 */
			}

			if (regionIds.size() == 0)
				throw new Exception("No regions selected");

			// LocationManager locMgr = new LocationManager();
			// List srvRegnIds = locMgr.getServiceRegions(locnList);

			PersonManager personMgr = new PersonManager();
			buyerList = personMgr.getBuyersForService(serviceId, regionIds);

			if (log.isDebugEnabled()) {
				Iterator iter = buyerList.iterator();
				while (iter.hasNext()) {
					Person person = (Person) iter.next();
					CommonLogger.logDebug(log,
							"The person name is " + person.getName());
				}
			}

			// request.getSession().setAttribute(BSIConstants.PERSONS_LIST,
			// buyerSet);
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger
					.logDebug(
							log,
							"In PersonAction:listBuyersForService()  \n exception occured. Exception message is "
									+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} finally {
			request.setAttribute(BSIConstants.PERSONS_LIST, buyerList);
		}
		return mapping.findForward("success");

	}

	/*
	 * public ActionForward add( ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * Exception {
	 * 
	 * CommonLogger.logDebug(log,"In PersonAction:add() ");
	 * 
	 * // If user pressed 'Cancel' button, // return to home page
	 * 
	 * if (isCancelled(request)) { return mapping.findForward("home"); }
	 * 
	 * DynaValidatorForm dynaForm = (DynaValidatorForm) form;
	 * 
	 * try { PersonManager clientHandler = new PersonManager();
	 * clientHandler.createPerson(form); ActionMessages msgs = new
	 * ActionMessages(); msgs.add(ActionMessages.GLOBAL_MESSAGE,new
	 * ActionMessage("creation.action.succesfull")); saveMessages(request,
	 * msgs); dynaForm.getMap().clear();
	 * 
	 * } catch(BSIException ex1) { ActionErrors errors = new ActionErrors();
	 * CommonLogger.logDebug(log,
	 * "In PersonAction:add() \n exception occured. Exception message is " +
	 * ex1.getMessage()); errors.add(ActionErrors.GLOBAL_MESSAGE,new
	 * ActionMessage(ex1.getErrorCode(),ex1.getMessage())); saveErrors(request,
	 * errors); } catch(Exception ex2){ throw ex2; }
	 * 
	 * finally{
	 * request.setAttribute(BSIConstants.ACTION_TYPE,BSIConstants.CREATE); }
	 * 
	 * 
	 * return mapping.findForward("success"); }
	 */

	/**
	 * createPerson
	 * 
	 * This method creates only the Person.
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 * @return ActionForward
	 */

	public ActionForward createPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In PersonAction:createPerson() ");

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}
		Object obj = null;
		//ActionRedirect redirect = null;
		try {
			PersonManager clientHandler = new PersonManager();
			obj = clientHandler.createPerson(form);
			String personType = ((PersonForm) form).getPersonType();
			ActionMessages msgs = new ActionMessages();
			if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Supplier.creation.succesfull"));
			} else if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.REGION_ADMIN)) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"RegAdmin.create.succesfully"));
			}
			saveMessages(request, msgs);
/*			HttpSession session = request.getSession(true);
			session.setAttribute(BSIConstants.CREATED_ENTITIY, obj);
*/			// dynaForm.getMap().clear();
		
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In PersonAction:createPerson() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);

		} catch (Exception ex2) {
			throw ex2;
		}

		finally {
			request.setAttribute(BSIConstants.ACTION_TYPE, BSIConstants.CREATE);
			request.getSession().setAttribute(BSIConstants.CREATED_ENTITIY, obj);
		
			
/*			redirect = new ActionRedirect(mapping.findForward("success"));
			redirect.addParameter(BSIConstants.CREATED_ENTITIY, obj);
*/
		}
		return mapping.findForward("success");
		//return redirect;
	}

	public ActionForward getPersonInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// If user pressed 'Cancel' button,
		// return to home page

		ActionForward forward = null;

		CommonLogger.logDebug(log, "In PersonAction:getPersonInfo() ");

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		String personId = request.getParameter(BSIConstants.PERSON_ID);
		User user = (User) request.getSession().getAttribute(BSIConstants.USER);
		// String personId = user.getPerson().getId().toString();

		CommonLogger.logInfo(log,
				"Executing the view action for the person id:" + personId);

		try {
			PersonManager clientHandler = new PersonManager();
			Person personObj = clientHandler.getPerson(personId);

			CommonLogger.logDebug(log, "Populating the person form");
			PersonForm personForm = (PersonForm) form;
			PropertyUtils.copyProperties(personForm, personObj);
			CommonLogger.logDebug(log,
					"After Populating the person form, the name is "
							+ personForm.getName());
			String personType = personForm.getPersonType();
			if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.BUYER)) {
				forward = mapping.findForward("Buyer");
			} else if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
				forward = mapping.findForward("Supplier");
			}
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In BuyerAction:editPersonalInfo() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} catch (Exception ex2) {
			throw ex2;
		}

		finally {
			request.setAttribute(BSIConstants.ACTION_TYPE, BSIConstants.EDIT);
		}
		return forward;
	}

	public ActionForward updatePersonInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CommonLogger.logDebug(log, "In PersonAction:updatePersonInfo() ");

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		// Person personObj = new Person();

		// PropertyUtils.copyProperties(personObj, (PersonForm)form);

		try {
			PersonManager clientHandler = new PersonManager();

			Person oldPersonObj = clientHandler.getPerson(((PersonForm) form)
					.getId().toString());
			CommonLogger.logDebug(log, "the retrieved person id is "
					+ oldPersonObj.getId());
			PropertyUtils.copyProperties(oldPersonObj, (PersonForm) form);
			clientHandler.updatePersonInfo(oldPersonObj);
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"common.update.succesfull"));
			saveMessages(request, msgs);
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger
					.logDebug(
							log,
							"In PersonAction:updatePersonInfo() \n exception occured. Exception message is "
									+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} catch (Exception ex2) {
			throw ex2;
		}

		finally {
			request.setAttribute(BSIConstants.ACTION_TYPE, BSIConstants.EDIT);
		}

		return mapping.findForward("success");
	}

	/*
	 * 
	 * public ActionForward edit( ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * Exception {
	 * 
	 * // If user pressed 'Cancel' button, // return to home page
	 * 
	 * CommonLogger.logDebug(log,"In PersonAction:edit() ");
	 * 
	 * if (isCancelled(request)) { return mapping.findForward("home"); }
	 * 
	 * 
	 * String personId = request.getParameter(BSIConstants.PERSON_ID);
	 * 
	 * CommonLogger.logInfo(log,"Executing the view action for the person id:");
	 * 
	 * try { PersonManager clientHandler = new PersonManager(); Person personObj
	 * = clientHandler.getPerson(personId);
	 * 
	 * //Get the service region for the person //TODO should support multiple
	 * service region, each for a service. Long serviceId = null; Set srvs =
	 * personObj.getServices(); if (srvs != null){ Object[] services =
	 * srvs.toArray(); if (services.length > 0) serviceId =
	 * ((Service)services[0]).getNodeId(); }
	 * 
	 * //Get the PersonServiceRegion for the service Long svsRegnId = null;
	 * 
	 * PersonSrvsRegionManager ssrMgr = new PersonSrvsRegionManager(); if
	 * (personObj.getPersonType().equalsIgnoreCase("S")) { SupplierSrvsRegion
	 * ssr = ssrMgr.getSupplierSrvsRegion(personObj.getId(),serviceId);
	 * svsRegnId = ssr.getServiceRegionId(); } else if
	 * (personObj.getPersonType().equalsIgnoreCase("B")) { BuyerSrvsRegion bsr =
	 * ssrMgr.getBuyerSrvsRegion(personObj.getId(),serviceId); svsRegnId =
	 * bsr.getServiceRegionId(); }
	 * 
	 * ServiceRegion svsRegn = null; LocationManager locMgr = new
	 * LocationManager(); if (svsRegnId != null) svsRegn =
	 * locMgr.getServiceRegion(svsRegnId);
	 * 
	 * //Get the list of all countries. String countryId =
	 * svsRegn.getCountry().getId().toString(); String stateId =
	 * svsRegn.getState().getId().toString(); String locationId =
	 * svsRegn.getLocation().getId().toString();
	 * 
	 * CommonLogger.logInfo(log,"the country id =" + countryId + ",state id = "
	 * + stateId);
	 * 
	 * List locnList = getCountriesList();
	 * request.getSession().setAttribute(BSIConstants.COUNTRY_LIST,locnList);
	 * 
	 * 
	 * //Get the child regions List states = getChildRegions(countryId);
	 * request.setAttribute(BSIConstants.STATE_LIST,states);
	 * 
	 * 
	 * //Get the list of locations for the selected state Set locns =
	 * getLocations(stateId);
	 * request.setAttribute(BSIConstants.REGION_LIST,locns);
	 * 
	 * 
	 * //Set the values in the form
	 * 
	 * DynaValidatorForm dynaForm = (DynaValidatorForm) form;
	 * dynaForm.set("name",personObj.getName());
	 * dynaForm.set("email",personObj.getEmail());
	 * dynaForm.set("phoneNumber",personObj.getPhoneNumber());
	 * dynaForm.set("address",personObj.getAddress()); //Get the string array of
	 * the serviceIds Set svs = personObj.getServices(); Iterator iter =
	 * svs.iterator(); int size = svs.size(); String[] nodeIds = new
	 * String[size]; int x=0; while(iter.hasNext()) {
	 * nodeIds[x]=((Service)iter.next()).getNodeId().toString(); x++; }
	 * 
	 * dynaForm.set("serviceIds",nodeIds);
	 * 
	 * // dynaForm.set("countryId",countryId); dynaForm.set("stateId",stateId);
	 * dynaForm.set("locationId",locationId);
	 * 
	 * // request.setAttribute(BSIConstants.SERVICE_REGION_SELECTED,svsRegn);
	 * request.setAttribute(BSIConstants.SERVICES_LIST,personObj.getServices());
	 * } catch(BSIException ex1) { ActionErrors errors = new ActionErrors();
	 * CommonLogger.logDebug(log,
	 * "In PersonAction:edit() \n exception occured. Exception message is " +
	 * ex1.getMessage()); errors.add(ActionErrors.GLOBAL_MESSAGE,new
	 * ActionMessage(ex1.getErrorCode(),ex1.getMessage())); saveErrors(request,
	 * errors); } catch(Exception ex2){ throw ex2; }
	 * 
	 * finally{
	 * request.setAttribute(BSIConstants.ACTION_TYPE,BSIConstants.EDIT); }
	 * 
	 * 
	 * 
	 * return mapping.findForward("success"); }
	 */

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In PersonAction:list() ");

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		try {
			PersonManager clientHandler = new PersonManager();

			CommonLogger.logDebug(
					log,
					"The person type is "
							+ request.getParameter(BSIConstants.PERSON_TYPE));

			List personsList = clientHandler.listPersons(request
					.getParameter(BSIConstants.PERSON_TYPE));

			request.getSession().setAttribute(BSIConstants.OBJECTS_LIST,
					personsList);
			/*
			 * 
			 * List supplierList = clientHandler.listSuppliers();
			 * 
			 * request.getSession().setAttribute(BSIConstants.SUPPLIER_LIST,
			 * supplierList);
			 * request.getSession().setAttribute(BSIConstants.SORT_LIST_NAME
			 * ,BSIConstants.SUPPLIER_LIST);
			 * 
			 * 
			 * SortParams sortParams = new SortParams();
			 * sortParams.setActionType("goto"); sortParams.setSortColumn("Id");
			 * sortParams.setSortOrder("asc"); sortParams.setRowsPerPage(3);
			 * sortParams.setGoToPage(2);
			 * 
			 * request.getSession().setAttribute(BSIConstants.SORT_PARAMS,sortParams
			 * );
			 */

		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In PersonAction:list() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} catch (Exception ex2) {
			throw ex2;
		}

		// Forward to result page
		return mapping.findForward("success");
	}

	public ActionForward listSubscribedServices(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CommonLogger.logDebug(log, "In PersonAction:listSubscribedServices() ");

		ActionMessages msgs = new ActionMessages();
		Set srvs = null;

		try {

			// String personId = request.getParameter(BSIConstants.PERSON_ID);
			User user = (User) request.getSession().getAttribute(
					BSIConstants.USER);
			//String personId = user.getPerson().getId().toString();
			String personId = null;
			if (request.getParameter(BSIConstants.PERSON_ID) != null){
				personId = request.getParameter(BSIConstants.PERSON_ID);
			} else {
				personId = user.getPerson().getId().toString();
			}
			String personType = request.getParameter(BSIConstants.PERSON_TYPE);

			CommonLogger.logDebug(log, "The person id is " + personId);

			PersonManager clientHandler = new PersonManager();
			Person personObj = clientHandler.getPerson(personId);

			// Get the service region for the person
			// TODO should support multiple service region, each for a service.
			Long serviceId = null;
			if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.BUYER)) {
				srvs = personObj.getRequestedSevices();
			} else if (personType != null
					&& personType.equalsIgnoreCase(BSIConstants.SUPPLIER)) {
				srvs = personObj.getOfferedServices();
			}

			// srvs = personObj.getServices();
			if (log.isDebugEnabled()) {
				if (srvs != null) {
					Object[] services = srvs.toArray();
					if (services.length > 0)
						serviceId = ((Service) services[0]).getNodeId();
					CommonLogger
							.logDebug(log, "The service id is " + serviceId);
				}
			}

		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger
					.logDebug(
							log,
							"In SupplierAction:subscribeService()  \n exception occured. Exception message is "
									+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} finally {
			request.setAttribute(BSIConstants.SUBSCRIBED_SERVICES_LIST, srvs);
		}
		return mapping.findForward("success");
	}

	public ActionForward editSubServiceLocation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// If user pressed 'Cancel' button,
		// return to home page

		CommonLogger.logDebug(log, "In PersonAction:editSubServiceLocation() ");

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		try {

			String personId = request.getParameter(BSIConstants.PERSON_ID);
			String serviceId = request.getParameter(BSIConstants.SERVICE_ID);
			String serviceName = request
					.getParameter(BSIConstants.SERVICE_NAME);
			String personType = request.getParameter(BSIConstants.PERSON_TYPE);

			CommonLogger.logInfo(log,
					"Executing the editSubServiceLocation action for the person id:"
							+ personId + ",serviceId = " + serviceId
							+ ",serviceName = " + serviceName + " personType="
							+ personType);

			PersonSrvsRegionManager ssrMgr = new PersonSrvsRegionManager();

			List regnList = ssrMgr.getSubscribedSrvsRegions(personId,
					serviceId, personType);
			
/*			//Get the service properties.
			ServiceManager svsMgr = new ServiceManager();
			Set propertySet = svsMgr.getServiceProperties(serviceId);
			
			//Get service propety values
*/			

			SubscribeServiceForm subForm = (SubscribeServiceForm) form;
			/*
			 * LocationForm locForm = null; ServiceRegion svsRegn = null; String
			 * countryId = null; String countryName = null; String stateId =
			 * null; String stateName = null; String locationId = null; String
			 * locationName = null; List locnsList = new ArrayList();
			 * 
			 * for (int z=0;z<serviceRegnList.size();z++) { locForm = new
			 * LocationForm();
			 * 
			 * svsRegn = (ServiceRegion)serviceRegnList.get(z); //Get the list
			 * of all countries. countryId =
			 * svsRegn.getCountry().getId().toString(); countryName =
			 * svsRegn.getCountry().getName();
			 * 
			 * stateId = svsRegn.getState().getId().toString(); stateName =
			 * svsRegn.getState().getName(); locationId =
			 * svsRegn.getLocation().getId().toString(); locationName =
			 * svsRegn.getLocation().getName();
			 * 
			 * locForm.setCountryId(countryId);
			 * locForm.setCountryName(countryName); locForm.setStateId(stateId);
			 * locForm.setStateName(stateName);
			 * locForm.setLocationId(locationId);
			 * locForm.setLocationName(locationName); locnsList.add(locForm); }
			 */
			List locnsLit = prepareLocationFormList(regnList);
			subForm.populateForm(locnsLit);

			List countryList = getCountriesList();
			request.setAttribute(BSIConstants.COUNTRY_LIST, countryList);

		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In SupplierAction:edit() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} catch (Exception ex2) {
			throw ex2;
		}

		finally {
			request.setAttribute(BSIConstants.ACTION_TYPE, BSIConstants.EDIT);
		}

		return mapping.findForward("success");
	}

	public ActionForward listPersonsForService(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CommonLogger.logDebug(log, "In PersonAction.listPersonsForService()");

		String serviceId = request.getParameter(BSIConstants.SERVICE_ID);
		String personType = request.getParameter(BSIConstants.PERSON_TYPE);
		Set personSet = null;
		List newPersonList = new ArrayList();

		CommonLogger.logDebug(log, "The request parameters are, serviceId="
				+ serviceId);
		try {

			// Get the service region
			SubscribeServiceForm ssform = (SubscribeServiceForm) form;
			ArrayList locns = ssform.getLocns();

			List<Long> regionIds = new ArrayList<Long>();
			HashMap locnMap = null;
			LocationForm locn = null;

			for (int x = 0; x < locns.size(); x++) {
				locn = (LocationForm) locns.get(x);
				regionIds.addAll(locn.getSubscribedRegionIds());
			}

			if (regionIds.size() == 0)
				throw new Exception("No regions selected");

			// LocationManager locMgr = new LocationManager();
			// List srvRegnIds = locMgr.getServiceRegions(locnList);

			//Get the buyer/supplier properties
			List propertyValueList = null;
			if ((personType.equalsIgnoreCase(BSIConstants.BUYER)) || (personType.equalsIgnoreCase(BSIConstants.SUPPLIER))) {
				propertyValueList = ((BuyerPropertiesForm) ssform)
						.getPropertiesValues();
			} 
//
			
			PersonManager personMgr = new PersonManager();
			personSet = personMgr.getPersonsForService(serviceId, regionIds,
					personType);

			// Convert this supplier set to the list
			Iterator iter = personSet.iterator();

			while (iter.hasNext()) {
				Person person = (Person) iter.next();
				CommonLogger.logDebug(log,
						"The person name is " + person.getName());
				newPersonList.add(person);
			}

			CommonLogger.logDebug(log, "The person set contains : "
					+ newPersonList);
			// request.getSession().setAttribute(BSIConstants.PERSONS_LIST,
			// suppSet);
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger
					.logDebug(
							log,
							"In PersonAction:listPersonsForService()  \n exception occured. Exception message is "
									+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} finally {
			request.getSession().setAttribute(BSIConstants.OBJECTS_LIST,
					newPersonList);
		}
		return mapping.findForward("success");

	}

	
	public ActionForward listPersonsMatchingServiceProperties(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CommonLogger.logDebug(log, "In PersonAction.listPersonsMatchingServiceProperties()");

		String serviceId = request.getParameter(BSIConstants.SERVICE_ID);
		String personType = request.getParameter(BSIConstants.PERSON_TYPE);
		Set personSet = null;
		List newPersonList = new ArrayList();

		CommonLogger.logDebug(log, "The request parameters are, serviceId="
				+ serviceId);
		try {

			// Get the service region
			SubscribeServiceForm ssform = (SubscribeServiceForm) form;
			ArrayList locns = ssform.getLocns();

			List<Long> regionIds = new ArrayList<Long>();
			HashMap locnMap = null;
			LocationForm locn = null;

			for (int x = 0; x < locns.size(); x++) {
				locn = (LocationForm) locns.get(x);
				regionIds.addAll(locn.getSubscribedRegionIds());
			}

			if (regionIds.size() == 0)
				throw new Exception("No regions selected");

			// LocationManager locMgr = new LocationManager();
			// List srvRegnIds = locMgr.getServiceRegions(locnList);

			//Get the buyer/supplier properties
			List propertyValueList = null;
			if ((personType.equalsIgnoreCase(BSIConstants.BUYER)) || (personType.equalsIgnoreCase(BSIConstants.SUPPLIER))) {
				propertyValueList = ((BuyerPropertiesForm) ssform)
						.getPropertiesValues();
			} 
//
			
			PersonManager personMgr = new PersonManager();
			personSet = personMgr.getPersonsMatchingServiceProperties(serviceId, regionIds,
					personType,propertyValueList);

			// Convert this supplier set to the list
			if (personSet != null){
				Iterator iter = personSet.iterator();
	
				while (iter.hasNext()) {
					Person person = (Person) iter.next();
					CommonLogger.logDebug(log,
							"The person name is " + person.getName());
					newPersonList.add(person);
				}
			}
			CommonLogger.logDebug(log, "The person set contains : "
					+ newPersonList);
			// request.getSession().setAttribute(BSIConstants.PERSONS_LIST,
			// suppSet);
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger
					.logDebug(
							log,
							"In PersonAction:listPersonsForService()  \n exception occured. Exception message is "
									+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} finally {
			request.getSession().setAttribute(BSIConstants.OBJECTS_LIST,
					newPersonList);
		}
		return mapping.findForward("success");

	}

	
	public List getCountriesList() throws Exception {
		CommonLogger.logDebug(log, "In PersonAction:getCountriesList() ");

		RegionManager regMgr = new RegionManager();
		List locnList = regMgr.getRootRegions();

		return locnList;
	}

	public List getChildRegions(String regionId) throws Exception {
		CommonLogger.logDebug(log, "In PersonAction:getChildRegions() ");

		RegionManager regMgr = new RegionManager();
		List childRegns = regMgr.getChildRegions(regionId);
		return childRegns;
	}

	/*
	 * public Set getStates(String countryId) throws Exception{
	 * CommonLogger.logDebug(log,"In PersonAction:getStates() ");
	 * 
	 * LocationManager locnMgr = new LocationManager(); List childRegns =
	 * locnMgr.getStates(countryId); return childRegns; }
	 * 
	 * public Set getLocations(String stateId) throws Exception{
	 * CommonLogger.logDebug(log,"In PersonAction:getLocations() ");
	 * 
	 * LocationManager locnMgr = new LocationManager(); Set locns =
	 * locnMgr.getLocations(stateId); return locns; }
	 */

	public List prepareLocationFormList(List regnList) throws BSIException {
		CommonLogger.logDebug(log,
				"In PersonAction.prepareLocationFormList(): the regn list size is "
						+ regnList.size());
		System.out
				.println("In PersonAction.prepareLocationFormList(): the regn list size is "
						+ regnList.size());
		List locnList = new ArrayList();
		LocationForm locForm = null;
		Region region = null;
		String[] ids;
		String[] names;

		for (int z = 0; z < regnList.size(); z++) {
			ids = new String[6];
			names = new String[6];

			locForm = new LocationForm();
			int arrayPos = 0;

			region = (Region) regnList.get(z);
			ids[arrayPos] = region.getRegionId().toString();
			names[arrayPos] = region.getName();

			// Extract parent region name/ids for the given region
			RegionManager regMgr = new RegionManager();

			while (true) {
				region = regMgr.getParentRegion(region.getRegionId());
				CommonLogger.logDebug(log, "The region id is "
						+ region.getRegionId().toString());
				System.out.println("The region id is "
						+ region.getRegionId().toString());

				if (region == null
						|| region.getRegionId().toString()
								.equalsIgnoreCase(BSIConstants.ROOT_REGION_ID))
					break;
				else {
					arrayPos = arrayPos + 1;
					ids[arrayPos] = region.getRegionId().toString();
					names[arrayPos] = region.getName();
				}
			}

			for (int m = 0; m < 6; m++) {
				System.out.println("The id [] :" + m + "=" + ids[m]);
				System.out.println("The names [] :" + m + "=" + names[m]);

			}
			// Populate the location form
			locForm.setRegionIdAndName(arrayPos + 1, ids, names);
			locnList.add(locForm);
		}
		return locnList;
	}

	public static void main(String[] args) {
		try {
			PersonSrvsRegionManager ssrMgr = new PersonSrvsRegionManager();
			List regnList = ssrMgr.getSubscribedSrvsRegions("1", "2", "B");
			PersonAction action = new PersonAction();
			List locnsList = action.prepareLocationFormList(regnList);
			SubscribeServiceForm form = new SubscribeServiceForm();
			form.populateForm(locnsList);
			List locnForms = form.getLocns();
			System.out.println("Getting the results from the form : ");
			for (int x = 0; x < locnForms.size(); x++) {
				LocationForm form1 = (LocationForm) locnForms.get(x);
				System.out.println(form1.getRegionId1());
				System.out.println(form1.getRegionId2());
				System.out.println(form1.getRegionName1());
				System.out.println(form1.getRegionName2());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
