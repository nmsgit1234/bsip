package com.bsi.client.session;


import java.util.Set;
import java.util.StringTokenizer;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.PermissionsAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nms.util.log.CommonLogger;



public class BSIRoleAdapter implements PermissionsAdapter {

	private static Log log = LogFactory.getLog(BSIRoleAdapter.class);

    private Set roles;

    /**
     * Creates a new instance of BSIRoleAdapter
     */
    public BSIRoleAdapter(Set roles) {
			this.roles = roles;
        }

    /**
     * If the menu is allowed, this should return true.
     *
     * @return whether or not the menu is allowed.
     */
    public boolean isAllowed(MenuComponent menu) {

		CommonLogger.logDebug(log,"The menu name is " + menu.getRoles());
		String strRoles = menu.getRoles();
		//if (strRoles == null || strRoles.length() == 0) return true;
		boolean isAllowed = false;

                if (strRoles == null || strRoles.trim().length() == 0) return true;


		StringTokenizer str = new StringTokenizer(strRoles,",");

		while (str.hasMoreTokens())
		{
			String token = str.nextToken();
                        CommonLogger.logDebug(log,"The token  is " + token);
                        isAllowed = roles.contains(token);
                        if (isAllowed) break;
		}
                CommonLogger.logDebug(log,"is Allowed " + isAllowed);
		return isAllowed;
    }
}
