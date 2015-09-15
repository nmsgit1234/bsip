package com.bsi.client.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.navigator.menu.PermissionsAdapter;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.MappingDispatchAction;

import com.bsi.client.managers.PersonManager;
import com.bsi.client.session.BSIRoleAdapter;
import com.bsi.client.session.User;
import com.bsi.client.util.BSIConstants;
import com.bsi.common.beans.Person;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

public class LogonAction extends MappingDispatchAction {

	private static Log log = LogFactory.getLog(LogonAction.class);

	/**
	 * Constructor for ProcessFormAction.
	 */
	public LogonAction() {
		super();
	}

	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		try {
			CommonLogger.logDebug(log, "In LogonAction:create() ");
			String username = doGet(form, "email");
			String password = doGet(form, "password");

			HashMap userInputMap = new HashMap();
			userInputMap.put(BSIConstants.USERID, username);
			userInputMap.put(BSIConstants.PASSWORD, password);
			userInputMap.put(BSIConstants.IS_ACTIVE, "Y");

			PersonManager clientHandler = new PersonManager();
			List person = clientHandler.login(userInputMap);
			if (person == null || person.size() == 0)
				throw new BSIException("1007");

			Person personObj = (Person) person.get(0);
			String type = (String) personObj.getPersonType();
			HttpSession session = request.getSession(true);
			User user = new User();
			user.setPerson(personObj);

			// Set user roles;
			user.setRoles(getRoles(personObj.getId().toString()));
			session.setAttribute(BSIConstants.USER, user);
			PermissionsAdapter roleAdapter = new BSIRoleAdapter(user.getRoles());

			session.setAttribute(BSIConstants.ROLE_PERMISSIONS, roleAdapter);

			CommonLogger
					.logDebug(
							log,
							"The user "
									+ username
									+ "succesfully logged into the system. The user is of type "
									+ type);
			forward = mapping.findForward("success");
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In LogonAction:login() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
			forward = mapping.findForward("failure");
		} catch (Exception ex2) {
			throw ex2;
		}
		return forward;
	}

	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(BSIConstants.USER);
			String userId = user.getPerson().getEmail();
			session.removeAttribute(BSIConstants.USER);
			session.invalidate();
			CommonLogger.logDebug(log, "The user with userid" + userId
					+ "  session is invalidated");
		} catch (Exception ex) {
			throw ex;
		}
		return mapping.findForward("success");
	}

	public ActionForward changeLocale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			HttpSession session = request.getSession(true);

			// Default
			String langCountry = "en_US";
			String langCode = "en";
			String countryCode = "US";

			langCountry = request.getParameter(BSIConstants.LANG_COUNTRY);

			if (langCountry != null && langCountry.trim().length() > 0) {
				StringTokenizer str = new StringTokenizer(langCountry, "_");
				if (str.countTokens() == 2) {
					langCode = str.nextToken();
					countryCode = str.nextToken();
				}
			}

			Locale userLocale = new Locale(langCode, countryCode);
			session.setAttribute(Globals.LOCALE_KEY, userLocale);
			if (session.getAttribute(BSIConstants.USER) == null)
				return mapping.findForward("success");

			User user = (User) session.getAttribute(BSIConstants.USER);
			user.setLocaleString(langCountry);
			session.setAttribute(BSIConstants.USER, user);

		} catch (Exception ex) {
			throw ex;
		}
		return mapping.findForward("success");
	}

	protected Set getRoles(String personID) throws Exception {
		PersonManager personMgr = new PersonManager();
		Set roles = personMgr.getRoles(personID);
		return roles;
	}

	protected String doGet(ActionForm form, String property) {
		String initial;
		try {
			initial = (String) PropertyUtils.getSimpleProperty(form, property);
		} catch (Throwable t) {
			initial = null;
		}
		String value = null;
		if ((initial != null) && (initial.length() > 0)) {
			value = initial.trim();
			if (value.length() == 0) {
				value = null;
			}
		}
		return value;
	}

}
