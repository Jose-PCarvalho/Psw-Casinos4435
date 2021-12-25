package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	public String LastMessage="";
	public boolean newMessage=false;

	public Server(ServerSocket serverSocket) {
		this.serverSocket=serverSocket;
		try {
			this.socket=serverSocket.accept();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			System.out.println("Error creating Client");
			e.printStackTrace();
			
		}
	}

	public void sendMessageToClient(String messageToClient) {
		try {
			bufferedWriter.write(messageToClient);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			System.out.println("I just sent this meassge " + messageToClient);
		} catch (IOException e) {
			System.out.println("Error sending message to client");
			closeEverything(socket,bufferedReader,bufferedWriter);
			e.printStackTrace();
		}
		
		
		
	}
	public void receiveMessageFromClient() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(socket.isConnected() && !socket.isClosed()) {
					try {
						String messageFromClient= bufferedReader.readLine();
						System.out.println("I received this message"+messageFromClient);
						LastMessage=messageFromClient;
						newMessage=true;
						if(LastMessage.equals("Shutdown")) {
							closeEverything(socket,bufferedReader,bufferedWriter);
						}
					} catch (IOException e) {
						System.out.println("Error receiving message from Client");
						e.printStackTrace();
						closeEverything(socket,bufferedReader,bufferedWriter);
						
					}
					
				}
			}
			
			
			
			
		}).start();
		
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
public void close() {
	closeEverything(socket,bufferedReader,bufferedWriter);
	try {
		this.serverSocket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	

	
}
