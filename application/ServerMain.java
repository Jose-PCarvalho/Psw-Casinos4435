package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

import BlackJack.dkeep.Game;

public class ServerMain {
	
	
public static void main(String[] args) {
		Server server;
		try {
			server = new Server(new ServerSocket(1234));
		} catch (IOException e) {
			System.out.println("Can't Create Server");
			e.printStackTrace();
			return;
		}
		System.out.println("Server Created");
		server.receiveMessageFromClient();
		Scanner sc = new Scanner(System.in);
		Game g=new Game();
		int ID;
		int bet;
		while(true) {
			
			if(server.LastMessage.equals("Player Joined") && server.newMessage) {
				System.out.println("Server: Player Joined");
				server.sendMessageToClient(g.AvailableSpots());
				server.newMessage=false;
			}
			if(server.LastMessage.contains("New Player on position:") && server.newMessage) {
				System.out.println("Server: New Player");
				String numberOnly= server.LastMessage.replaceAll("[^0-9]", "");
				System.out.println("Server: New Player" + numberOnly);
				g.newPlayer(Integer.parseInt(numberOnly));
				g.setGameState("Betting"); //fix this later
				server.sendMessageToClient(g.getInfo());
				server.newMessage=false;
			}
			
			if(server.LastMessage.contains("Bet Entered") && server.newMessage && g.getGameState().equals("Betting")) {
				System.out.println("Server: New Bet");
				String parse[]=server.LastMessage.split(":");
				ID=Integer.parseInt(parse[0].replaceAll("[^0-9]", ""));
				bet=Integer.parseInt(parse[1].replaceAll("[^0-9]", ""));
				g.setBet(ID, bet);
				server.sendMessageToClient(g.getInfo());
				server.newMessage=false;
			}
			
			if(server.LastMessage.contains("Bet Confirmed") && server.newMessage && g.getGameState().equals("Betting")) {
				System.out.println("Server: New Bet");
				String parse[]=server.LastMessage.split(":");
				ID=Integer.parseInt(parse[0].replaceAll("[^0-9]", ""));
				g.setGameState("Playing");
				g.startPlaying();
				server.sendMessageToClient(g.getInfo());
				server.newMessage=false;
			}
			
			if(server.LastMessage.contains("Hit") && server.newMessage && g.getGameState().equals("Playing")) {
				System.out.println("Server: Hit");
				ID=Integer.parseInt(server.LastMessage.replaceAll("[^0-9]", ""));
				g.PlayerPlay(server.LastMessage, ID);
				if(g.getGameState().equals("Ending")) {
					while(g.gamelogic("Dealer Draw",ID)) { //Adapt for multiplayer later	
					}
				}
				server.sendMessageToClient(g.getInfo());
				server.newMessage=false;
			}
			if(server.LastMessage.contains("Stand") && server.newMessage && g.getGameState().equals("Playing")) {
				System.out.println("Server: Stand");
				ID=Integer.parseInt(server.LastMessage.replaceAll("[^0-9]", ""));
				g.PlayerPlay(server.LastMessage, ID);
				if(g.getGameState().equals("Ending")) {
					while(g.gamelogic("Dealer Draw",ID)) { //Adapt for multiplayer later	
					}
				}
				server.sendMessageToClient(g.getInfo());
				server.newMessage=false;
			}
			if(server.LastMessage.contains("Double") && server.newMessage && g.getGameState().equals("Playing")) {
				System.out.println("Server: Douvle");
				ID=Integer.parseInt(server.LastMessage.replaceAll("[^0-9]", ""));
				g.PlayerPlay(server.LastMessage, ID);
				if(g.getGameState().equals("Ending")) {
					while(g.gamelogic("Dealer Draw",ID)) { //Adapt for multiplayer later	
					}
				}
				server.sendMessageToClient(g.getInfo());
				server.newMessage=false;
			}
			
			if(server.LastMessage.contains("New Game") && server.newMessage && g.getGameState().equals("Finished")) {
				g.newGame();
				server.sendMessageToClient(g.getInfo());
				server.newMessage=false;
			}
			
		
	       
	        
			if(server.LastMessage.equals("break"))
				break;
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
		sc.close();
		server.close();
	}

}
