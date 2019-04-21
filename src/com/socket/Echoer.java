package com.socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Echoer extends Thread {

	private Socket socket;	
	private static String username = "toor";
	private static String password = "root";
	
	public Echoer(Socket socket) {
		this.socket = socket;
	}
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			String username = input.readLine();
			String password = input.readLine();
			while(true) {
				if(username.equals(Echoer.username) && password.equals(Echoer.password)) {
					output.println("Succes!");
				}
				else {
					output.println("Failed!");
					socket.close();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
