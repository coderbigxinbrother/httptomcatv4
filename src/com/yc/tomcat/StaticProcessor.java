package com.yc.tomcat;

import yc.javax.servlet.ServletRequest;
import yc.javax.servlet.ServletResponse;

public class StaticProcessor implements Processor {

	@Override
	public void process(ServletRequest request, ServletResponse response) {
		response.sendRedirect();
	}

}
