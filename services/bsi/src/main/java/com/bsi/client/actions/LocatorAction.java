package com.bsi.client.actions;

import java.util.Collection;
import java.util.Iterator;
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
import org.apache.struts.actions.MappingDispatchAction;

import com.bsi.client.managers.RegionManager;
import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.Region;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;


/**
 * Retrieve and process data from the submitted form
 *
 * @version $Rev: 421486 $ $Date: 2006-07-12 20:37:08 -0700 (Wed, 12 Jul 2006) $
 */
public class LocatorAction extends MappingDispatchAction {

	private static Log log = LogFactory.getLog(LocatorAction.class);

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for ProcessFormAction.
     */
    public LocatorAction() {
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

    public ActionForward getRegionNames(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

          CommonLogger.logDebug(log, "In LocatorAction:getRegionNames()");

          // If user pressed 'Cancel' button,
          // return to home page

          if (isCancelled(request)) {
            return mapping.findForward("home");
          }

          String actionType = request.getParameter(BSIConstants.ACTION_TYPE);
          String regionId = request.getParameter(BSIConstants.REGION_ID);

          CommonLogger.logDebug(log," The action type is " + actionType + ", the regionId is " + regionId);
          RegionManager regMgr = new RegionManager();
          Collection optionsList = null;
          try
          {
          if (actionType == null || actionType.trim().length() == 0)throw new BSIException("1001","5046");
          if (actionType.equalsIgnoreCase(BSIConstants.LIST_COUNTRY)) {
            optionsList = regMgr.getRootRegions();
          }
          else if (actionType.equalsIgnoreCase(BSIConstants.LIST_CHILD_REGIONS)) {
            //if (regionId == null || regionId.trim().length() == 0)throw new BSIException("1001","5047");
            optionsList = regMgr.getChildRegions(regionId);
          }

          //Check to see if these regions have child regions
          Region region = null;
          Boolean hasChildren = new Boolean(false);
          Iterator iter = optionsList.iterator();
          while (iter.hasNext())
          {
            region = (Region)iter.next();
            if (region.hasChildren())
            {
              hasChildren = Boolean.valueOf(true);
              break;
            }
          }
          CommonLogger.logDebug(log,"Has children returned :" + hasChildren.booleanValue());
          request.setAttribute(BSIConstants.HAS_CHILDREN, hasChildren);
          request.setAttribute(BSIConstants.OPTION_LIST, optionsList);

        }
        catch(BSIException ex1)
        {
          ActionErrors errors = new ActionErrors();
          CommonLogger.logDebug(log,
              "In LocatorAction:getRegionNames() \n exception occured. Exception message is " +
                                ex1.getMessage());
          errors.add(ActionErrors.GLOBAL_MESSAGE,
                     new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
          saveErrors(request, errors);
        }
        catch(Exception ex2){
                throw ex2;
        }

        finally{
                request.setAttribute(BSIConstants.ACTION_TYPE,actionType);
        }

          return mapping.findForward("success");
    }

    public ActionForward getCountries(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

		CommonLogger.logDebug(log,"In LocatorAction:getCountries()");

        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }

        RegionManager regMgr = new RegionManager();
	List countryList = regMgr.getRootRegions();

        request.getSession().setAttribute(BSIConstants.COUNTRY_LIST,countryList);

        return mapping.findForward("success");
    }

/*
    public ActionForward getStates(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {


		CommonLogger.logDebug(log,"In LocatorAction:getStates()");

        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }

		String countryId = (String)request.getParameter("countryId");
		LocationManager locnMgr = new LocationManager();
		//Set states = locnMgr.getStates(countryId);
        //request.getSession().setAttribute(BSIConstants.STATE_LIST,states);

        PrintWriter out = response.getWriter();
        out.println(locnMgr.getStatesHTML(countryId));
		out.flush();

        return null;
    }
*/

/*

    public ActionForward getLocations(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

		CommonLogger.logDebug(log,"In LocatorAction:getLocations()");


        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }

		String stateId = (String)request.getParameter("stateId");
		LocationManager locnMgr = new LocationManager();
		//Set locations = locnMgr.getLocations(stateId);
        //request.setAttribute(BSIConstants.REGION_LIST,locations);

        PrintWriter out = response.getWriter();
        out.println(locnMgr.getLocationsHTML(stateId));
		out.flush();

        return null;
    }
*/
}

