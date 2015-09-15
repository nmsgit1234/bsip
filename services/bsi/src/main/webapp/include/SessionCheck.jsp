<%@ page import="com.bsi.client.session.User,com.bsi.client.util.BSIConstants" %>
<%

      response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
      response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
      response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
      response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

	session = request.getSession();
	User user = null;
	Object obj = session.getAttribute("user");

	if (obj == null)
	{

		response.sendRedirect("Index.do");
		return;
	}

	if (obj != null) user = (User)obj;
	
	
	boolean canViewMatchingBuyers = user.hasRole(BSIConstants.VIEW_BUYERS);
	boolean canDeleteBuyers = user.hasRole(BSIConstants.DELETE_BUYER);
	
	pageContext.setAttribute("canViewMatchingBuyers",new Boolean(canViewMatchingBuyers));
	pageContext.setAttribute("canDeleteBuyers",new Boolean(canDeleteBuyers));	
	
%>
