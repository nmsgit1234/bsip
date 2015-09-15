package com.bsi.client.actions;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bsi.client.managers.ServiceManager;
import com.nms.util.log.CommonLogger;

/**
 * Retrieve and process data from the submitted form
 *
 * @version $Rev: 421486 $ $Date: 2006-07-12 20:37:08 -0700 (Wed, 12 Jul 2006) $
 */
public class TreeBrowsing extends Action {

	private static Log log = LogFactory.getLog(TreeBrowsing.class);

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for ProcessFormAction.
     */
    public TreeBrowsing() {
        super();
    }

    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {


		CommonLogger.logDebug(log,"In TreeBrowsing:execute()");

        // If user pressed 'Cancel' button,
        // return to home page

        if (isCancelled(request)) {
            return mapping.findForward("home");
        }


		String id = request.getParameter("id");


		Enumeration paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) {
		      String paramName = (String)paramNames.nextElement();
		      CommonLogger.logDebug(log,"The parame name is " + paramName);
			  String[] paramValues = request.getParameterValues(paramName);
			  if (paramValues.length == 1) {
				  String paramValue = paramValues[0];
				 if (paramValue.length() == 0)
					CommonLogger.logDebug(log,"No Value");
				  else
					CommonLogger.logDebug(log,"The parame value is " + paramValue);
		      }
		}

		CommonLogger.logDebug(log,"The id requested is " + id);

		response.setContentType("text/xml");

        PrintWriter out = response.getWriter();

        ServiceManager svsMgr = new ServiceManager();

		String xml="";
		if (id.equalsIgnoreCase("0"))
		{
			xml = svsMgr.getFirstLevelServicesAsXML();

		}
		else
		{
			xml = svsMgr.getChildServicesAsXML(id);
		}

		out.println(xml);
		out.flush();

        return null;
    }

}

