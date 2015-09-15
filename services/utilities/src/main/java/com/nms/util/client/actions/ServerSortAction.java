package com.nms.util.client.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.nms.util.beans.SortParams;
import com.nms.util.beans.SortingUtility;
import com.nms.util.client.Constants;
import com.nms.util.log.CommonLogger;


/**
 * Retrieve and process data from the submitted form
 *
 * @version $Rev: 421486 $ $Date: 2006-07-12 20:37:08 -0700 (Wed, 12 Jul 2006) $
 */
public class ServerSortAction extends Action {

	private static Log log = LogFactory.getLog(ServerSortAction.class);

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for ProcessFormAction.
     */
    public ServerSortAction() {
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
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {


		CommonLogger.logDebug(log,"In ServerSortAction:execute()");

        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }


		Object result = request.getSession().getAttribute((String)request.getSession().getAttribute(Constants.SORT_LIST_NAME));
		SortParams sortParams = (SortParams)request.getSession().getAttribute(Constants.SORT_PARAMS);
		List resultPage = null;

		if (result != null && sortParams != null)
		{
				resultPage = SortingUtility.getSortedPage(result,sortParams,"enity_list");
		}

		CommonLogger.logDebug(log,"In ServerSortAction , sorted result is : " + resultPage);

        request.setAttribute("result",resultPage);
        // Forward to result page
        return mapping.findForward("success");
    }

}

