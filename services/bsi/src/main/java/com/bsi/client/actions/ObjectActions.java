package com.bsi.client.actions;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.MappingDispatchAction;

import com.bsi.client.managers.ObjectManager;
import com.bsi.client.util.BSIConstants;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;
import com.nms.util.ui.ObjectHandler;

/**
 * Retrieve and process data from the submitted form
 * 
 * @version $Rev: 421486 $ $Date: 2006-07-12 20:37:08 -0700 (Wed, 12 Jul 2006) $
 */
public class ObjectActions extends MappingDispatchAction {

	private static Log log = LogFactory.getLog(ObjectActions.class);

	// ------------------------------------------------------------ Constructors

	/**
	 * Constructor for ProcessFormAction.
	 */
	public ObjectActions() {
		super();
	}

	public ActionForward displayCreateObject(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute(BSIConstants.ACTION_TYPE, BSIConstants.CREATE);
		return mapping.findForward("success");
	}

	public ActionForward createObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In CreateObject:execute()");

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}
		Object obj = null;

		try {

			HashMap map = createMapOfRequestParamValues(request);

			String entityName = request.getParameter(BSIConstants.ENTITY_NAME);

			ObjectHandler objHandler = new ObjectHandler();
			Object convertedObject = objHandler.createObject(entityName, map);

			CommonLogger.logDebug(log, "The populated entity is  "
					+ convertedObject.toString());
			ObjectManager objMgr = new ObjectManager();
			obj = objMgr.createObject(convertedObject);
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"creation.action.succesfull"));
			saveMessages(request, msgs);
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In CreateObject \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} catch (Exception ex2) {
			throw ex2;
		} finally {
			request.setAttribute(BSIConstants.CREATED_ENTITIY, obj);
			request.setAttribute(BSIConstants.ACTION_TYPE, BSIConstants.CREATE);
		}

		return mapping.findForward("success");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In ObjectAction:list() ");

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		try {
			ObjectManager objMgr = new ObjectManager();

			//HashMap map = createMapOfRequestParamValues(request);

			String entityName = request.getParameter(BSIConstants.ENTITY_NAME);

			List objectsList = objMgr.getObjects(entityName, null);

			request.getSession().setAttribute(BSIConstants.TOKENS_LIST,
					objectsList);

		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In ObjectAction:list() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
		} catch (Exception ex2) {
			throw ex2;
		} finally {
			request.setAttribute(BSIConstants.ACTION_TYPE, BSIConstants.CREATE);
		}

		// Forward to result page
		return mapping.findForward("success");
	}

	public ActionForward confirmObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In CreateObject:execute()");

		// If user pressed 'Cancel' button,
		// return to home page

		if (isCancelled(request)) {
			return mapping.findForward("home");
		}

		try {

			HashMap map = createMapOfRequestParamValues(request);

			String entityName = request.getParameter(BSIConstants.ENTITY_NAME);

			Class classToLoad = Class.forName(entityName);
			Object convertedObject = classToLoad.newInstance();

			BeanUtils.populate(convertedObject, map);

			// Prepares the map of the key,values which are not null.

			/*
			 * convertedObject.getClass().getFields(); Method[] methods =
			 * classToLoad.getDeclaredMethods();
			 * 
			 * for (Method method : methods) { String methodName =
			 * method.getName();
			 * 
			 * if (methodName.startsWith("get")){ Object result =
			 * method.invoke(convertedObject, null); } }
			 */
			Iterator keys = map.keySet().iterator();
			HashMap<String, Object> criteria = new HashMap<String, Object>();
			while (keys.hasNext()) {
				try {
					String keyName = (String) keys.next();
					Object propertyValue = PropertyUtils.getProperty(
							convertedObject, keyName);
					criteria.put(keyName, propertyValue);

				} catch (Exception ex) {

				}
			}

			ObjectManager objMgr = new ObjectManager();
			List<Object> objects = objMgr.getObjects(entityName, criteria);
			if (objects.size() != 1) {
				throw new BSIException("1009", "1009");
			}
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"creation.action.succesfull"));
			saveMessages(request, msgs);
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In CreateObject \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} catch (Exception ex2) {
			throw ex2;
		} finally {
			request.setAttribute(BSIConstants.ACTION_TYPE, BSIConstants.CREATE);
		}

		return mapping.findForward("success");
	}

	public ActionForward updateObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonLogger.logDebug(log, "In CreateObject:execute()");

		try {

			// Get the id of the object to be updated from the request.
			String entityName = request.getParameter(BSIConstants.ENTITY_NAME);
			HashMap map = createMapOfRequestParamValues(request);

			Class classToLoad = Class.forName(entityName);
			Object convertedObject = classToLoad.newInstance();

			BeanUtils.populate(convertedObject, map);

			// Get object represented by the id
			String entityID = request
					.getParameter(BSIConstants.ENTITIY_ID_NAME);
			Object objID = null;

			List<Method> methods = new ArrayList<Method>();
			/*
			 * Method[] methods = new Method[1]; methods[0] =
			 * Service.class.getMethod("setProperties", clss);
			 */
			// Method arguments.
			/*
			 * Object[] args = new Object[1]; args[0] = propsSet;
			 */
			List<Object> argsList = new LinkedList<Object>();

			Iterator keys = map.keySet().iterator();
			HashMap<String, Object> criteria = new HashMap<String, Object>();
			while (keys.hasNext()) {
				try {
					String keyName = (String) keys.next();
					if (!keyName.equals(entityID)) {
						Object propertyValue = PropertyUtils.getProperty(
								convertedObject, keyName);
						PropertyDescriptor pd = new PropertyDescriptor(keyName,
								classToLoad);
						Method method = PropertyUtils.getWriteMethod(pd);
						methods.add(method);
						argsList.add(propertyValue);
						// criteria.put(keyName, propertyValue);
					} else {
						objID = PropertyUtils.getProperty(convertedObject,
								keyName);
					}
				} catch (Exception ex) {
				}
			}

			ObjectManager objMgr = new ObjectManager();
			Object object = objMgr.updatetObject(objID, classToLoad,
					methods.toArray(new Method[methods.size()]), argsList);

			if (object == null) {
				throw new BSIException("1009", "1009");
			}
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"creation.action.succesfull"));
			saveMessages(request, msgs);

		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In ObjectAction.updateObject() \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");

	}

	public ActionForward SearchObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CommonLogger.logDebug(log, "In CreateObject:SearchObject()");

		try {

			HashMap map = createMapOfRequestParamValues(request);

			String entityName = request.getParameter(BSIConstants.ENTITY_NAME);

			Class classToLoad = Class.forName(entityName);
			Object convertedObject = classToLoad.newInstance();

			BeanUtils.populate(convertedObject, map);

			// Prepares the map of the key,values which are not null.

			
			HashMap<String, Object> criteria = ObjectHandler.getSearchPropertiesMap(convertedObject);

			ObjectManager objMgr = new ObjectManager();
			List<Object> objects = objMgr.getObjectsLike(entityName, criteria);
			request.getSession().setAttribute(BSIConstants.OBJECTS_LIST,
					objects);
		} catch (BSIException ex1) {
			ActionErrors errors = new ActionErrors();
			CommonLogger.logDebug(log,
					"In CreateObject \n exception occured. Exception message is "
							+ ex1.getMessage());
			errors.add(ActionErrors.GLOBAL_MESSAGE,
					new ActionMessage(ex1.getErrorCode(), ex1.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} catch (Exception ex2) {
			throw ex2;
		} finally {
			request.setAttribute(BSIConstants.ACTION_TYPE, BSIConstants.SEARCH);
		}
		return mapping.findForward("success");
	}


	private HashMap createMapOfRequestParamValues(HttpServletRequest request) {

		HashMap map = new HashMap();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			map.put(name, request.getParameterValues(name));
		}
		return map;
	}
}
