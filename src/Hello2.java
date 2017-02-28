import java.io.PrintWriter;

import yc.javax.servlet.ServletRequest;
import yc.javax.servlet.ServletResponse;
import yc.javax.servlet.http.HttpServlet;
import yc.javax.servlet.http.HttpServletRequest;
import yc.javax.servlet.http.HttpServletResponse;



//使用了适配器模式
public class Hello2 extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(  request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String uname=request.getParameter("uname");
		int age=Integer.parseInt( request.getParameter("age") );
		
		String body="<html><head><title>hello world</title></head><body>uname:"+uname+"</hr>age:"+ age+"</body></html>";
		
		String protocal="HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=utf-8;\r\nContent-Length: "+body.getBytes().length+"\r\n\r\n"+body;
		
		PrintWriter out=response.getWriter();
		out.println(   protocal );
		out.flush();
		
		
	}
}
