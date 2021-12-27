package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import BlackJack.dkeep.Game;

public class ServerLogic {
	
	
	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket= new ServerSocket(1234);
		Server server= new Server(serverSocket);
		
		new Thread(() -> {
			Game g=new Game();
			int ID;
			int bet;
    	    while(!server.serverSocket.isClosed()) {
    	    	ArrayList<String> remove= new ArrayList<String>();
    	    	server.receiveMessageClientHandlers();
    	    	if(g.getNumberOfPlayers()==0) {
    	    		g.newGame();
    	    	}
    	    	
    	    	for (int i=0;i<server.messageList.size();i++) {
    	    		
    	    		if(server.messageList.get(i).contains("Shutdown")) {
    	    			ID=Integer.parseInt(server.messageList.get(i).replaceAll("[^0-9]", ""));
    	    			g.freeSpot(ID);
    	    		}
    	    		if(server.messageList.get(i).equals("Player Joined")) {
    					System.out.println("Server: Player Joined");
    					server.broadcastMessage(g.AvailableSpots());
    					
    				}
    				if(server.messageList.get(i).contains("New Player on position:")) {
    					System.out.println("Server: New Player");
    					String numberOnly= server.messageList.get(i).replaceAll("[^0-9]", "");
    					System.out.println("Server: New Player" + numberOnly);
    					g.newPlayer(Integer.parseInt(numberOnly));
    					server.broadcastMessage(g.getInfo());
    					
    				}
    				
    				if(server.messageList.get(i).contains("Bet Entered") && g.getGameState().equals("Betting")) {
    					System.out.println("Server: New Bet");
    					String parse[]=server.messageList.get(i).split(":");
    					ID=Integer.parseInt(parse[0].replaceAll("[^0-9]", ""));
    					bet=Integer.parseInt(parse[1].replaceAll("[^0-9]", ""));
    					g.setBet(ID, bet);
    					server.broadcastMessage(g.getInfo());
    					
    				}
    				
    				if(server.messageList.get(i).contains("Bet Confirmed")  && g.getGameState().equals("Betting")) {
    					System.out.println("Server: New Bet");
    					String parse[]=server.messageList.get(i).split(":");
    					ID=Integer.parseInt(parse[0].replaceAll("[^0-9]", ""));
    					g.confirmBet(ID);
    					server.broadcastMessage(g.getInfo());
    					
    				}
    				
    				if(server.messageList.get(i).contains("Hit") && g.getGameState().equals("Playing")) {
    					System.out.println("Server: Hit");
    					ID=Integer.parseInt(server.messageList.get(i).replaceAll("[^0-9]", ""));
    					g.PlayerPlay(server.messageList.get(i), ID);
    					if(g.getGameState().equals("Ending")) {
    						g.endGame();
    					}
    					server.broadcastMessage(g.getInfo());
    					
    				}
    				if(server.messageList.get(i).contains("Stand")  && g.getGameState().equals("Playing")) {
    					System.out.println("Server: Stand");
    					ID=Integer.parseInt(server.messageList.get(i).replaceAll("[^0-9]", ""));
    					g.PlayerPlay(server.messageList.get(i), ID);
    					if(g.getGameState().equals("Ending")) {
    						g.endGame();
    					}
    					server.broadcastMessage(g.getInfo());
    					
    				}
    				if(server.messageList.get(i).contains("Double") && g.getGameState().equals("Playing")) {
    					System.out.println("Server: Double");
    					ID=Integer.parseInt(server.messageList.get(i).replaceAll("[^0-9]", ""));
    					g.PlayerPlay(server.messageList.get(i), ID);
    					if(g.getGameState().equals("Ending")) {
    						g.endGame();
    					}
    					server.broadcastMessage(g.getInfo());
    					
    				}
    				
    				if(server.messageList.get(i).contains("New Game")  && g.getGameState().equals("Finished")) {
    					g.newGame();
    					server.broadcastMessage(g.getInfo());
    					
    				}
    				
    			
    		       
    		        
    				if(server.messageList.get(i).equals("break"))
    					break;   	    			
    	    		remove.add(server.messageList.get(i));
    	    	}
    	    	server.messageList.removeAll(remove);
    	    }

    	  }).start();
		server.startServer();
		
	}

}
