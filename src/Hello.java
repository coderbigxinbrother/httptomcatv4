import java.io.PrintWriter;

import yc.javax.servlet.JspWriter;
import yc.javax.servlet.Servlet;
import yc.javax.servlet.ServletRequest;
import yc.javax.servlet.ServletResponse;
import yc.javax.servlet.http.Cookie;
import yc.javax.servlet.http.HttpServlet;
import yc.javax.servlet.http.HttpServletRequest;
import yc.javax.servlet.http.HttpServletResponse;


public class Hello extends HttpServlet {
	public Hello(){
		System.out.println(  "constructor");
	}
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response) {
		doPost(  request, response);
	}
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		
		response.addCookie(   new Cookie( "name","zy"));
		response.addCookie(  new Cookie("age","20"));
		
		String uname=request.getParameter("uname");
		int age=Integer.parseInt( request.getParameter("age") );
		String body="<html><head><title>hello world</title></head><body>uname:"+uname+"</hr>age:"+ age+"</body></html>";
		//与标准tomcat不同的地方： 可以自动封装好协议的头部.
		//String protocal="HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=utf-8;\r\nContent-Length: "+body.getBytes().length+"\r\n\r\n"+body;
		JspWriter out=(JspWriter) response.getWriter();
		out.println(   body );
	}
	@Override
	public void init() {
		System.out.println(  "init()");
	}
	
	
	


	
}
