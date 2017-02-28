package com.yc.tomcat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpTomcat {
	private int port=8888;
	
	public static void main(String[] args) {
		HttpTomcat tomcat=new HttpTomcat();
		tomcat.startServer();
	}
	
	public void startServer(){
		ServerSocket ss=null;
		try {
			ss = new ServerSocket( port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(  true ){
			try {
				Socket s=ss.accept();
				Thread t=new Thread(  new ServerService(s) );
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
