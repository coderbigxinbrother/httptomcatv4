package yc.javax.servlet;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * servlet的容 器
 */
public interface ServletContext {
	
	public Map<String,Servlet> getServlets();
	
	public Iterator<String> getServletNames();
	
	public Servlet getServlet(String name);
	
	public void setServlet( String name, Servlet servlet);


}
