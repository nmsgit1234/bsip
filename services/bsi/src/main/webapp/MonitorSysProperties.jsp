<html>
<%@ page contentType="text/html; charset=utf-8"
         import="java.io.InputStream,
                 java.io.IOException,
                 javax.xml.parsers.SAXParser,
                 java.lang.reflect.*,
                 javax.xml.parsers.SAXParserFactory"
   session="false" %>
<body bgcolor='#ffffff'>

<H>System properties</H>

<UL>
<%
    /**
     * Dump the system properties
     */
    java.util.Enumeration e=null;
    try {
        e= System.getProperties().propertyNames();
    } catch (SecurityException se) {
    }
    if(e!=null) {
        out.write("<pre>");
        for (;e.hasMoreElements();) {
            String key = (String) e.nextElement();
            out.write(key + "=" + System.getProperty(key)+"\n");
        }
        out.write("</pre><p>");
    } else {
        //out.write(getMessage("sysPropError"));
    }
%>
</UL>
    <hr>
    <%= getServletConfig().getServletContext().getServerInfo() %>
</body>
</html>
