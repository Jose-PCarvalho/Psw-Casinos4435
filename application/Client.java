package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.scene.control.Label;

public class Client {
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	public Client(Socket socket) {
		this.socket=socket;
		try {
			this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			System.out.println("Error creating Client");
			e.printStackTrace();
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
		

	}

	

	private void closeEverything(Socket socket,BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		
		try {
			if(socket!=null)
				socket.close();
			if(bufferedReader!=null)
				bufferedReader.close();
			if(bufferedWriter!=null)
				bufferedWriter.close();
			
		
		
		
		
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void sendMessageToServer(String messageToServer) {
		try {
			bufferedWriter.write(messageToServer);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			
			
		}catch(IOException e) {
			System.out.println("Error sending message to Server");
			e.printStackTrace();
			closeEverything(socket,bufferedReader,bufferedWriter);
		}
		
	}
	
	public void receiveMessageFromServer() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(socket.isConnected()) {
					try {
						String messageFromServer= bufferedReader.readLine();
						//Controller.addLabel(messageFromServer);
					}catch(IOException e) {
						System.out.println("Error creating Client");
						e.printStackTrace();
						closeEverything(socket,bufferedReader,bufferedWriter);
						break;
					}
					
					
				}

			}
	
		}).start();
		}
		
		
		
		
		
	




}
