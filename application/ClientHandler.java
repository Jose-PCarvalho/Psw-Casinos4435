package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

public class ClientHandler implements Runnable {
	
	
	Socket socket;
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	private String name;
	String message;
	boolean LastMessage;
	private ReentrantLock mutex = new ReentrantLock();
	public Connection conn;
	String user;
	private String table="0";
	private String position="0";
	public ClientHandler(Socket socket,Server server) throws SQLException {
		try {
			this.socket=socket;
			this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ConnectDB();
			/*try {
	            mutex.lock();
	            Server.clientHandlers.add(this);
	        } finally {
	            mutex.unlock();
	        }*/
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public ClientHandler() throws SQLException {
		ConnectDB();
	}


	@Override
	public void run() {
		
		while(socket.isConnected() && !socket.isClosed()) {
			try {
				message =bufferedReader.readLine();
				System.out.println("Message From Client:"+ message);
				LastMessage=true;
				
				if(message.contains("Shutdown") && !message.contains("Chat%")) {
					if(message.contains("Logout")) {
						String[] str=message.split("%");
						System.out.println("User:"+str[2]+"LoggedOut");
						try {
							setLogout(str[2]);
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
					}
					closeEverything(socket,bufferedReader,bufferedWriter);
					
					break;
				}
				else if(message.contains("Leave Table")&& !message.contains("Chat%")) {
					position="0";
					table="0";
					if(message.contains("Logout")) {
						String[] str=message.split("%");
						System.out.println("User:"+str[4]+"LoggedOut");
						try {
							setLogout(str[4]);
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
					}
					closeEverything(socket,bufferedReader,bufferedWriter);
					break;
				}
				else if(message.contains("LobbyInfoRequest") && !message.contains("Chat%")) {
					String[] str=message.split("%");
					user=str[1];
					position="0";
					table="0";
					
										
				}
				else if(message.contains("New Player")&& !message.contains("Chat%")) {
					String[] str=message.split(":");
					String[] str1=str[1].split("%");
					table=str1[3];
					position=str1[0];
					user=str1[1];
					System.out.println("table "+table +"position "+position);
					
					
				}
				
				
				
			}catch(IOException e) {
				System.out.println("adsfasfasd");
				message ="Leave Table%"+position+"%"+table;
				LastMessage=true;
				e.printStackTrace();
				closeEverything(socket,bufferedReader,bufferedWriter);
				try {
					setLogout(user);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
	
	public  void ConnectDB() throws SQLException {
    	String url = "jdbc:postgresql://db.fe.up.pt/meec1a0405";
		Properties props = new Properties();
		props.setProperty("user","meec1a0405");
		props.setProperty("password","IICQHlXb");
		conn=null;
		try {
			 conn = DriverManager.getConnection(url, props);
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	public void setLogin (String username) throws SQLException {    	
    	String update = "UPDATE blackjack.users SET logged='"+true+"' WHERE username='"+username+"'";
    	Statement statement = conn.createStatement();
		statement.executeUpdate(update);
		
    }
	public void setLogout (String username) throws SQLException {    	
    	String update = "UPDATE blackjack.users SET logged='"+false+"' WHERE username='"+username+"'";
    	Statement statement = conn.createStatement();
		statement.executeUpdate(update);
		System.out.println("Aqui!"+username);
		
    }
        
    

}
