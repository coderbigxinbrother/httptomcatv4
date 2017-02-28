package yc.javax.servlet.http;

import yc.javax.servlet.GenericServlet;
import yc.javax.servlet.ServletRequest;
import yc.javax.servlet.ServletResponse;

public class HttpServlet extends GenericServlet {

	private final String GET_METHOD = "GET";
	private final String POST_METHOD = "POST";
	private final String HEAD_METHOD = "HEAD";
	private final String DELETE_METHOD = "DELETE";

	@Override
	public void service(ServletRequest request, ServletResponse response) {
		service((HttpServletRequest)request, (HttpServletResponse)response);
	}

	//根据请求method的不同，完成分发.
	public void service(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod();
		if (method == null || "".equals(method)) {
			throw new RuntimeException("request method:" + method
					+ " should not be null");
		}
		if (GET_METHOD.equals(method)) {
			doGet(request, response);
		} else if (POST_METHOD.equals(method)) {
			doPost(request, response);
		} else if (HEAD_METHOD.equals(method)) {
			doHead(request, response);
		} else if (DELETE_METHOD.equals(method)) {
			doDelete(request, response);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {

	}

	protected void doHead(HttpServletRequest request,
			HttpServletResponse response) {

	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) {
	}

}
