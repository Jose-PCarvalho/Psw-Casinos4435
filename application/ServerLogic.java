package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import BlackJack.dkeep.Game;

public class ServerLogic {
	
	static Game[] g=new Game[6];
	
	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket= new ServerSocket(1234);
		Server server= new Server(serverSocket);
		for(int k=0;k<6;k++) {
			g[k]=new Game(k);
		}
		
		
		new Thread(() -> {   //sanity check thread
			while(!server.serverSocket.isClosed()) {
				for(int j=1;j<6;j++) {   	    		
		    		if(g[j].sanityCheck()) {
		    			server.broadcastMessage(updateLobby());
		    			server.broadcastMessage(g[j].getInfo());
		    		}
	    	}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		
		
		
		new Thread(() -> {
			int ID;
			int bet;
    	    while(!server.serverSocket.isClosed()) {
    	    	ArrayList<String> remove= new ArrayList<String>();
    	    	server.receiveMessageClientHandlers();
    	    	
    	    	for (int i=0;i<server.messageList.size();i++) {
    	    		System.out.println("Novas mensagens "+server.messageList.size());
    	    		if(server.messageList.get(i).contains("LobbyInfoRequest")) {
    	    			
    	    			server.broadcastMessage(updateLobby());
    	    		
    	    		}
    	    		else if(server.messageList.get(i).contains("DealerRequest%")) {
    	    			String lines[]=server.messageList.get(i).split("%");
    	    			String Name=lines[1];
    	    			int table=Integer.parseInt(lines[2]);
    	    			g[table].changeDealerName(Name);
    	    			server.broadcastMessage(updateLobby());
    	    		
    	    		}
    	    		else if(server.messageList.get(i).contains("KickRequest")) {
    	    			String lines[]=server.messageList.get(i).split("%");
    	    			int table=Integer.parseInt(lines[1]);
    	    			int player=Integer.parseInt(lines[2]);
    	    			g[table].kickPlayer(player);
    	    			server.broadcastMessage(updateLobby());
    	    			server.broadcastMessage(g[table].getInfo());
    	    		
    	    		}
    	    		
    	    		
    	    		else if(server.messageList.get(i).contains("Leave Table")) {
    	    			String lines[]=server.messageList.get(i).split("%");
    	    			int table=Integer.parseInt(lines[2]);
    	    			int player=Integer.parseInt(lines[1]);
    	    			g[table].freeSpot(player);
    	    			server.broadcastMessage(updateLobby());
    	    		}
    	    		else if(server.messageList.get(i).contains("Player Joined")) {
    	    			String lines[]=server.messageList.get(i).split("%");
    	    			int table=Integer.parseInt(lines[1]);
    					server.broadcastMessage(g[table].AvailableSpots());
    					
    				}
    	    		else if(server.messageList.get(i).contains("New Player on position:")) {
    					String lines[] = server.messageList.get(i).split("%");
    					System.out.println(server.messageList.get(i));
    					System.out.println("Server: New Player");
    					String numberOnly= lines[0].replaceAll("[^0-9]", "");
    					System.out.println("Server: New Player" + numberOnly);
    					String userName=lines[1];
    					String balance=lines[2];
    					int table=Integer.parseInt(lines[3]);
    					g[table].newPlayer(Integer.parseInt(numberOnly),userName,Integer.parseInt(balance));
    					server.broadcastMessage(g[table].getInfo());
    					server.broadcastMessage(updateLobby());
    					
    					
    				}
    				
    	    		else if(server.messageList.get(i).contains("Bet Entered") ) {
    					System.out.println("Server: New Bet");
    					String parse[]=server.messageList.get(i).split("%");
    					
    					ID=Integer.parseInt(parse[2]);
    					bet=Integer.parseInt(parse[1]);
    					int table=Integer.parseInt(parse[3]);
    					if(g[table].getGameState().equals("Betting")){
	    					g[table].setBet(ID, bet);
	    					server.broadcastMessage(g[table].getInfo());
	    					}
    					
    				}
    				
    	    		else if(server.messageList.get(i).contains("Bet Confirmed")  ) {
    					System.out.println("Server: New Bet");
    					String parse[]=server.messageList.get(i).split("%");
    					ID=Integer.parseInt(parse[1]);
    					int table=Integer.parseInt(parse[2]);
    					if(g[table].getGameState().equals("Betting")) {
	    					g[table].confirmBet(ID);
	    					server.broadcastMessage(g[table].getInfo());}
    					
    				}
    				
    	    		else if(server.messageList.get(i).contains("Hit")) {
    					System.out.println("Server: Hit");
    					String parse[]=server.messageList.get(i).split("%");
    					ID=Integer.parseInt(parse[1]);
    					int table=Integer.parseInt(parse[2]);
    					if(g[table].getGameState().equals("Playing")) {
    					g[table].PlayerPlay(server.messageList.get(i), ID);
    					server.broadcastMessage(g[table].getInfo());
    					}
    					
    				}
    	    		else if(server.messageList.get(i).contains("Stand")) {
    					System.out.println("Server: Stand");
    					String parse[]=server.messageList.get(i).split("%");
    					ID=Integer.parseInt(parse[1]);
    					int table=Integer.parseInt(parse[2]);
    					if(g[table].getGameState().equals("Playing")) {
    					g[table].PlayerPlay(server.messageList.get(i), ID);
    					server.broadcastMessage(g[table].getInfo());
    					}
    					
    				}
    	    		else if(server.messageList.get(i).contains("Double")) {
    					System.out.println("Server: Double");
    					String parse[]=server.messageList.get(i).split("%");
    					ID=Integer.parseInt(parse[1]);
    					int table=Integer.parseInt(parse[2]);
    					if(g[table].getGameState().equals("Playing")) {
    					g[table].PlayerPlay(server.messageList.get(i), ID);
    					server.broadcastMessage(g[table].getInfo());
    					}
    					
    				}
    				
    	    		else if(server.messageList.get(i).contains("New Game")) {
    					String parse[]=server.messageList.get(i).split("%");
    					int table=Integer.parseInt(parse[1]);
    					if(g[table].getGameState()=="Finished") {
	    					g[table].newGameRequest();
	    					server.broadcastMessage(g[table].getInfo());
	    					}
    					
    				}
    				
    			
    		       
    		        
    	    		else if(server.messageList.get(i).equals("break"))
    					break;   	    			
    	    		remove.add(server.messageList.get(i));
    	    	}
    	    	server.messageList.removeAll(remove);
    	    }

    	  }).start();
		server.startServer();
		
	}
	
	static String updateLobby() {
		String msg="Lobby Info%&";
		for(int j=1;j<6;j++) {
			msg+=g[j].lobbyInfo();
		}
		return msg;
	}

}
