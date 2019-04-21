package com.socket;
import java.io.IOException;
import java.net.ServerSocket;

public class MainLogin {

	public static void main(String[] args){
		ServerSocket serverSocket;		
		try {
			serverSocket = new ServerSocket(5000);
			while(true) {
				new Echoer(serverSocket.accept()).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
