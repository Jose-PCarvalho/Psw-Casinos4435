package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
	
	
	Socket socket;
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	private String name;
	String message;
	boolean LastMessage;
	
	public ClientHandler(Socket socket,Server server) {
		try {
			this.socket=socket;
			this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Server.clientHandlers.add(this);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		
		while(socket.isConnected() && !socket.isClosed()) {
			try {
				message =bufferedReader.readLine();
				System.out.println("Message From Client:"+ message);
				LastMessage=true;
				if(message.equals("Shutdown")) {
					closeEverything(socket,bufferedReader,bufferedWriter);
					break;
				}
				
				
			}catch(IOException e) {
				e.printStackTrace();
				closeEverything(socket,bufferedReader,bufferedWriter);
				break;
			}
			
			
			
			
		}
		
	}
	
	public void closeEverything(Socket socket,BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		
		try {
			if(bufferedReader!=null)
				bufferedReader.close();
			if(bufferedWriter!=null)
				bufferedWriter.close();
			if(socket!=null)
				socket.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
