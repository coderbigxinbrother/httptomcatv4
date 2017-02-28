package com.yc.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import yc.javax.servlet.ServletRequest;
import yc.javax.servlet.ServletResponse;
import yc.javax.servlet.http.HttpServletRequest;
import yc.javax.servlet.http.HttpServletResponse;

public class ServerService implements Runnable {
	private Socket socket;
	private InputStream iis;
	private OutputStream oos;
	
	public ServerService(  Socket socket ){
		this.socket=socket;
	}

	@Override
	public void run() {
		try {
			this.iis=  this.socket.getInputStream();
			this.oos=this.socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			//
			
			//创建请求对象
			ServletRequest request=new HttpServletRequest(  this.iis);
			request.parse();
			
			//创建响应对象
			ServletResponse response=new HttpServletResponse( request, this.oos);
			
			//定义一个处理器
			Processor processor=null;
			// 判断 uri 是否以 /servlet/开头，是的，则表明请求的是动态的资源
			//工厂模式.
			if(   request.getRequestUri()!=null&&   request.getRequestUri().startsWith("/servlet/")    ){ //http://loclahost:8888/servlet/HelloServlet
				processor=new DynamicProcessor();
			}else{
				//否则为静态资源
				processor=new StaticProcessor();
			}
			//调用处理器的处理方法
			processor.process(request, response);
			//http协议是一个无状态. 基于请求/响应
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
