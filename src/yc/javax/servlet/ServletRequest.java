package yc.javax.servlet;

/**
 * 包装http请求
 *
 */
public interface ServletRequest {
	
	/**
	 * 取请求的参数
	 * @param key:根据键取值
	 * @return:有则返回值，没有，则返回null
	 */
	public String getParameter(String key);
	
	/**
	 * HTTP:    GET /servlet/Hello?name=zy HTTP/1.1
	 * 
	 *          POST /servlet/Hello HTTP/1.1
	 *          
	 *          name=zy
	 * 解析请求:   1. 解析出  uri
	 *            2.  解析出 参数
	 *            3.  解析出请求的方式 get/post
	 */
	public void parse();
	
	/**
	 * 获取解析出来的uri地址
	 * @return
	 */
	public String getRequestUri();
	
	/**
	 * 获取请求方式的方法
	 * @return
	 */
	public String getMethod();
	
	
}
