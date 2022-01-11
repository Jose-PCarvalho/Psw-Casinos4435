package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;

public class Server {
	ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	public String LastMessage="";
	public boolean newMessage=false;
	public static ArrayList<ClientHandler> clientHandlers =new ArrayList<>();
	ArrayList<String> messageList= new ArrayList<String>();
	private ReentrantLock mutex = new ReentrantLock();

	public Server(ServerSocket serverSocket) {
		this.serverSocket=serverSocket;	
	}
	
	
	
	public void startServer() {
		try {
			
			while(!serverSocket.isClosed()) {
				Socket socket= serverSocket.accept();
				System.out.println("Clint has connected");
				ClientHandler clientHandler= new ClientHandler(socket,this);
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
			
		}catch(IOException e) {
			CloseServerSocket();
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

	public void CloseServerSocket() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void broadcastMessage(String messageToSend) {
		try {
            mutex.lock();
            ArrayList<ClientHandler> remove =new ArrayList<>();
    		for(ClientHandler clientHandler: clientHandlers) {
    			try {
    				if(clientHandler.socket.isClosed()) {
    					remove.add(clientHandler);
    				}
    				else {
    					clientHandler.bufferedWriter.write(messageToSend);
    					clientHandler.bufferedWriter.newLine();
    					clientHandler.bufferedWriter.flush();
    					}
    			}catch(IOException e) {
    				e.printStackTrace();
    				closeEverything(clientHandler.socket,clientHandler.bufferedReader,clientHandler.bufferedWriter);
    				
    			}
    			
    		}
    		clientHandlers.removeAll(remove);
    		
        } finally {
            mutex.unlock();
        }
		
	}
	
	public void removeClientHandler(ClientHandler clientHandler) {
		clientHandlers.remove(clientHandler);
	}
	
	public void receiveMessageClientHandlers() {
		try {
            mutex.lock();
            ArrayList<ClientHandler> copy =new ArrayList<>();
    		copy=clientHandlers;
    		for(ClientHandler clientHandler: copy) {
    			if(clientHandler.LastMessage) {
    				messageList.add(clientHandler.message);
    				clientHandler.LastMessage=false;
    			}
    		} 
        } finally {
            mutex.unlock();
        }
		
	}
	

	
}
