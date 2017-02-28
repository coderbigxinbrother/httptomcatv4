package yc.javax.servlet.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import yc.javax.servlet.JspWriter;
import yc.javax.servlet.ServletRequest;
import yc.javax.servlet.ServletResponse;

/**
 * 响应的功能:
 *  1. 从request中取出uri
 *  2. 判断是否在本地存在这个uri指代的文件
 *            没有, 404
 *            有
 *  3. 以输入流读取这个文件
 *  4. 以输出流将文件写到客户端,要加入响应的协议. 
 * @author Administrator
 *
 */
public class HttpServletResponse implements ServletResponse{
	
	private String webroot=System.getProperty("user.dir")+File.separator+"webapps";
	private ServletRequest request;
	private OutputStream output;
	private PrintWriter writer;
	private String contentType="html/text;charset=utf-8";
	
	public Cookie[] cookies=new Cookie[50];
	public int i;
	
	public void addCookie(Cookie cookie){
		if(   i==cookies.length){
			return;
		}
		cookies[i++]= cookie;
	}

	
	
	@Override
	public PrintWriter getWriter() {
		this.writer=new JspWriter( output,   this );
		return this.writer;
	}
	
	public HttpServletResponse(  ServletRequest request, OutputStream output   ){
		this.request=request;
		this.output=output;
	}
	
	/*
	 * 从request中取出uri 2. 判断是否在本地存在这个uri指代的文件 没有, 404 有 3. 以输入流读取这个文件 4.
	 * 以输出流将文件写到客户端,要加入响应的协议.
	 */
	public void sendRedirect() {
		String uri = request.getRequestUri();
		
		File f = new File(webroot, uri);
		String responseprotocal = null;
		byte[] fileContent = null;
		if (!f.exists()) {
			fileContent = readFile(new File(webroot, "404.html"));
			responseprotocal = gen404(fileContent.length);
		} else {
			fileContent = readFile(f);
			responseprotocal = gen200(fileContent.length);
		}
		try {
			output.write(responseprotocal.getBytes());   //写协议
			output.write(fileContent);   //写出文件
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private byte[] readFile(File f) {
		FileInputStream fis = null;
		ByteArrayOutputStream boas = new ByteArrayOutputStream(     );   //字节数组输出流  (  将字节数组数据保存到内存  )
		try {
			// 读取这个文件
			fis = new FileInputStream(f);
			byte[] bs = new byte[1024];
			int length = -1;
			while ((length = fis.read(bs, 0, bs.length)) != -1) {
				boas.write(bs, 0, length);    //写到内存
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return boas.toByteArray();    //一次性地从内存中读取所有的字节数组返回
	}
	
	/**
	 * 要考虑文件的类型
	 * @param bodylength
	 * @return
	 */
	private String gen200(long bodylength) {
		String uri = this.request.getRequestUri();
		int index = uri.lastIndexOf(".");
		if (index >= 0) {
			index = index + 1;
		}
		String fileExtension = uri.substring(index);   //文件的后缀名
		String protocal200 = "";
		if ("JPG".equalsIgnoreCase(fileExtension)) {
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: image/JPEG\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		}else if ("PNG".equalsIgnoreCase(fileExtension)) {
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: image/PNG\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		}else if ("json".equalsIgnoreCase(fileExtension)) {
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		}else {
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		}
		return protocal200;
	}

	/**
	 * 产生404响应
	 * 
	 * @return
	 */
	private String gen404(long bodylength) {
		String protocal404 = "HTTP/1.1 404 File Not Found\r\nContent-Type: text/html\r\nContent-Length: "
				+ bodylength + "\r\n\r\n";
		return protocal404;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType=contentType;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	
}
