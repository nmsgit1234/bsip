package com.bsi.client.actions;

import java.text.MessageFormat;

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
import org.apache.struts.validator.DynaValidatorForm;

import com.bsi.client.actions.forms.PersonForm;
import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.Person;
import com.nms.util.comm.IEmailManager;
import com.nms.util.comm.ISMSManager;
import com.nms.util.log.CommonLogger;

public class OACAction extends MappingDispatchAction {

	private static Log log = LogFactory.getLog(OACAction.class);
	
	public static ISMSManager smsManager = null;
	public static IEmailManager emailManager = null;

	// ------------------------------------------------------------ Constructors

	public static IEmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(IEmailManager emailManager) {
		this.emailManager = emailManager;
	}

	public ISMSManager getSmsManager() {
		return smsManager;
	}

	public void setSmsManager(ISMSManager smsManager) {
		this.smsManager = smsManager;
	}

	/**
	 * Constructor for ProcessFormAction.
	 */
	public OACAction() {
		super();
	}


	public ActionForward generateOAC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In OACAction:generateOAC() ");
		ActionErrors errors = new ActionErrors();

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		try {
			
			PersonForm personForm = (PersonForm)form;
			long oacNumber = (long)(Math.random()*1000000);
			personForm.setOacNumber(new Long(oacNumber).toString());
			personForm.setIsOACAuthenticated("N");
		} catch (Exception ex1) {
			CommonLogger.logError(log,"Exception occured while generating the OAC", ex1);
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage("1001", "5069"));
			saveErrors(request, errors);
		} 
		return mapping.findForward("createObject");
	}

	
	
	
	public ActionForward sendOAC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In OACAction:sendOAC() ");
		ActionErrors errors = new ActionErrors();

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		try {
			Object obj =request.getSession().getAttribute(BSIConstants.CREATED_ENTITIY);
			Object[] args = {((Person)obj).getOacNumber()};
			String message = getResources(request).getMessage("OAC.sms.message");
			MessageFormat  msgFormatter = new MessageFormat (message);
			String formattedMsg = msgFormatter.format(args);
			getMessages(request).clear();
			smsManager.sendSMS(true,((Person)obj).getCountryCode() + ((Person)obj).getPhoneNumber(), formattedMsg);
		} catch (Exception ex1) {
			CommonLogger.logError(log,"Exception occured while generating the OAC", ex1);
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage("1001", "5070"));
			saveErrors(request, errors);
			return mapping.findForward("failure");

		} 

		return mapping.findForward("success");
	}
	

	public ActionForward sendOACByEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In OACAction:sendOAC() ");
		ActionErrors errors = new ActionErrors();

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		try {
			Object obj =request.getSession().getAttribute(BSIConstants.CREATED_ENTITIY);
			//Object[] args = {((Person)obj).getOacNumber()};
			
			String oacEmailSubject = getResources(request).getMessage("OAC.email.subject");
			
			String url = request.getRequestURL().toString();
			int index = url.lastIndexOf("/");
			url = url.substring(0, index+1);

			String completeURL = url + "ConfirmOAC.do?id=" + ((Person)obj).getId() + "&entityName=com.bsi.common.beans.Person&oacNumber=" + ((Person)obj).getOacNumber();
			Object[] args = {completeURL};
			
			MessageFormat  oacEmailBody = new MessageFormat (getResources(request).getMessage("OAC.email.body"));
			String oacEmailBodyMsg = oacEmailBody.format(args);
			//request.getAttribute(ActionMessages.GLOBAL_MESSAGE);
			getMessages(request).clear();
			emailManager.sendEmail(null, ((Person)obj).getEmail(), oacEmailSubject, oacEmailBodyMsg);
			//getMessages(request).clear();
			ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"OAC.verify.email"));
			saveMessages(request, msgs);
			
		} catch (Exception ex1) {
			CommonLogger.logError(log,"Exception occured while sending oac by email", ex1);
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage("1001", "5073"));
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} 

		return mapping.findForward("success");
	}
	
	
	
	
	/**
	 * Prepares the data for what needs to be updated in the database once the confirmation is successful.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareForOACUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In OACAction:prepareForOACUpdate() ");
		ActionErrors errors = new ActionErrors();

		// If user pressed 'Cancel' button,
		// return to home page

		ActionForward forward = mapping.findForward( "success" );   
		StringBuffer path = new StringBuffer( forward.getPath() );   

		try {
			
			boolean isQuery = (path.indexOf( "?" ) >= 0 );
			
			StringBuffer forwardPath = new StringBuffer();
			forwardPath.append(BSIConstants.ENTITY_NAME).append("=" );   
			forwardPath.append("com.bsi.common.beans.Person");
			forwardPath.append( "&").append(BSIConstants.ENTITIY_ID_NAME).append("=" );      
			forwardPath.append("id");
			forwardPath.append( "&").append("isActive").append("=" );      
			forwardPath.append("Y");
			forwardPath.append( "&").append("isOACAuthenticated").append("=" );      
			forwardPath.append("Y");
			forwardPath.append( "&").append("id").append("=" );      
			forwardPath.append(((DynaValidatorForm)form).getString("id"));
			
			if( isQuery )   
			{   
			    path.append( "&").append(forwardPath.toString());
			}   
			else  
			{   
			    path.append( "?").append(forwardPath.toString());
			}   
		} catch (Exception ex1) {
			CommonLogger.logError(log,"Exception occured while preparing the the OAC update", ex1);
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage("1001", "" +
							""));
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} 

		return new ActionForward(path.toString());  
	}
	
	
	
}
