package com.yc.tomcat;

import yc.javax.servlet.ServletRequest;
import yc.javax.servlet.ServletResponse;

/**
 * 资源处理器: 处理静态或动态资源,.....
 *
 */
public interface Processor {
	/**
	 * 处理资源的方法: 包括动静态资源
	 * @param request:  请求对象  (  uri, method, parse,parameter)
	 * @param response: 响应对象  ( PrintWriter )
	 */
	public void process(     ServletRequest request, ServletResponse response  );
}
