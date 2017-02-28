package com.yc.tomcat;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import yc.javax.servlet.Servlet;
import yc.javax.servlet.ServletRequest;
import yc.javax.servlet.ServletResponse;

public class DynamicProcessor implements Processor {

	// private Map<String, Object > map=new HashMap<String,Object >();

	@Override
	// protocal: GET /servlet/Hello
	/**    从request中取出请求白servlet的名字, 根据名字找到  Servlet的类字节码， 以反射白方式加载这个servlet对象，因为Servlet对象实现 了Servlet 接口( sun规范),
	 * 我就知道 里面有   生命 周期的三个方法   init()  service()  destroy(),就可以顺序调用, 再调用  response的sendRedirect()
	 */
	public void process(ServletRequest request, ServletResponse response) {
		// 1. 取出 uri /servlet/servlet类名
		String uri = request.getRequestUri();
		// 2. 取出 servletname
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		// 3. 类加载 -> 仍然要通过 url地址来加载 servlet字节码
		// URLClassLoader
		URLClassLoader loader = null;
		// 4。 通过字节码反射成一个对象

		TomcatServletContext tsc = TomcatServletContext.getInstance();  //servletContext 对象就是application
		Servlet servlet = tsc.getServlet(servletName);
		if (servlet == null) {
			try {
				URL url = new URL("file", null, TomcatConstants.BASE_PATH); // 这个路径是写死的，标准tomcat是使用web.xml来进行配置
				URL[] urls = new URL[] { url };
				// 创建了一个类加载器，这个加载器从 项目下的 webapps中读取servlet
				loader = new URLClassLoader(urls);
				// 加 servletname
				Class c = loader.loadClass(servletName);
				servlet = (Servlet) c.newInstance(); // => 调用了servlet构造方法.
														// 从这里可看出，我们的tomcat创建的servlet是多实例的，而标准的tomcat是单实例.
				tsc.setServlet(servletName, servlet);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			if (servlet != null && servlet instanceof Servlet) {
				// 5. 控制生命周期 => init() -> service () j2EE就是规范.
				servlet.init();
				servlet.service(request, response);
			}
		} catch (Exception e) {
			String bodyentity = e.getMessage();
			bodyentity = gen500(bodyentity.getBytes().length) + bodyentity;
			PrintWriter out = response.getWriter();
			out.println(bodyentity);
			out.flush();
		}

	}

	/**
	 * 产生404响应
	 * 
	 * @return
	 */
	private String gen500(long bodylength) {
		String protocal500 = "HTTP/1.1 500 Server Internal Error\r\nContent-Type: text/html\r\nContent-Length: "
				+ bodylength + "\r\n\r\n";
		return protocal500;
	}

}
