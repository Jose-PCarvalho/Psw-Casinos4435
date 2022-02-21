package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
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
	public Connection conn;
	public Server(ServerSocket serverSocket) {
		this.serverSocket=serverSocket;	
	}
	
	
	
	/**
	 * @throws SQLException
	 * starts the server operation.
	 * creates a thread for every client that connects.
	 */
	public void startServer() throws SQLException {
		try {
			ConnectDB();
			String query = "UPDATE blackjack.users SET logged ='false' WHERE logged='true'";   	
			Statement statement = conn.createStatement();
    		statement.executeUpdate(query);
			conn.close();
			
			while(!serverSocket.isClosed()) {
				Socket socket= serverSocket.accept();
				System.out.println("Clint has connected");
				try {
				mutex.lock();
				ClientHandler clientHandler= new ClientHandler(socket,this);
				Thread thread = new Thread(clientHandler);
				Server.clientHandlers.add(clientHandler);
				thread.start();}
				finally {
					mutex.unlock();
				}

			}
			
		}catch(IOException e) {
			CloseServerSocket();
		}
	}

	/**
	 * @param messageToClient
	 * sends a String to a client.
	 */
	public void sendMessageToClient(String messageToClient) {
		try {
			getBufferedWriter().write(messageToClient);
			getBufferedWriter().newLine();
			getBufferedWriter().flush();
			System.out.println("I just sent this meassge " + messageToClient);
		} catch (IOException e) {
			System.out.println("Error sending message to client");
			closeEverything(socket,bufferedReader,getBufferedWriter());
			e.printStackTrace();
		}
		
		
		
	}
	/**
	 * receives message from a client
	 */
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
							closeEverything(socket,bufferedReader,getBufferedWriter());
						}
					} catch (IOException e) {
						System.out.println("Error receiving message from Client");
						e.printStackTrace();
						closeEverything(socket,bufferedReader,getBufferedWriter());
						
					}
					
				}
			}
			
			
			
			
		}).start();
		
	}
	
	/**
	 * @param socket
	 * @param bufferedReader
	 * @param bufferedWriter
	 * closes socket, and buffered reader and writer.
	 */
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
		closeEverything(socket,bufferedReader,getBufferedWriter());
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CloseServerSocket() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
	}
	
	
	
	/**
	 * @param messageToSend
	 * broadcasts a message to every client.
	 */
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
	
	/**
	 * @param clientHandler
	 * removes a client that no longer is communicating
	 */
	public void removeClientHandler(ClientHandler clientHandler) {
		clientHandlers.remove(clientHandler);
	}
	
	/**
	 * 
	 * receives messages from clientHandlers.
	 */
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



	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}
	/**
	 * @throws SQLException
	 * connects to the db.
	 */
	public  void ConnectDB() throws SQLException {
    	String url = "jdbc:postgresql://db.fe.up.pt/meec1a0405";
		Properties props = new Properties();
		props.setProperty("user","meec1a0405");
		props.setProperty("password","IICQHlXb");
		conn=null;
		try {
			 conn = DriverManager.getConnection(url, props);
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}

	
}
