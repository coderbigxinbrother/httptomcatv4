package com.yc.tomcat;

import java.io.File;

public class TomcatConstants {

	//请求的GET方法名
	public static final String REQUEST_METHOD_GET="GET";
	//请求的post方法名
	public static final String REQUEST_METHOD_POST="POST";
	//请求的HEAD方法名
	public static final String REQUEST_METHOD_HEAD="HEAD";
	
	
	//Servlet文件的基路径basepath
	public static final String BASE_PATH=System.getProperty("user.dir")+File.separator+"webapps"+File.separator;
}
