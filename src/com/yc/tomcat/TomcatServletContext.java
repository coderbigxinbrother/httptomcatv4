package com.yc.tomcat;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import yc.javax.servlet.Servlet;
import yc.javax.servlet.ServletContext;
//单例: 保证整 个项目只有一个   servlet容器. (  tomcat这种web服务器我们称为  servlet容 器  )
public class TomcatServletContext implements ServletContext {
	//所有白servlet
	private Map<String, Servlet> servlets = new Hashtable<String, Servlet>();
	
	private static TomcatServletContext tomcatServletContext;
	
	private TomcatServletContext() {
	}
	
	public synchronized static TomcatServletContext getInstance() {
		if (tomcatServletContext == null) {
			tomcatServletContext = new TomcatServletContext();
		}
		return tomcatServletContext;
	}
	@Override
	public Map<String, Servlet> getServlets() {
		return servlets;
	}
	@Override
	public Iterator<String> getServletNames() {
		return servlets.keySet().iterator();
	}
	@Override
	public Servlet getServlet(String name) {
		Servlet servlet=null;
		if(   servlets.containsKey(name)){
			servlet=servlets.get(name);
		}
		return servlet;
	}
	public void setServlet( String name, Servlet servlet){
		servlets.put(name, servlet);
	}

}
