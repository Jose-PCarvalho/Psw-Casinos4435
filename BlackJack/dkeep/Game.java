package BlackJack.dkeep;
import BlackJack.Players.*;
import BlackJack.Interface.*;
public class Game{
    public Gambler P;
    public Dealer D;
    Boolean start;
    cli inter;
    
    boolean PlayerTurn=true;
    boolean GameIsPlaying=true;
    private String gameState="Waiting for Players";
    public Gambler[] Players= new Gambler[8];
    private int numberOfPlayers=0;
    private boolean[] OccupiedSlots= {false,false,false,false,false,false,false,false};
    private int[] WinDrawLose={0,0,0,0,0,0,0,0};
    
    



public Game(){
    D=new Dealer("Dolores Aveiro", 66, "Feminino", 4);
    //P=new Gambler("Nuno Daniel",21,"Masculino");
    inter=new cli();
    start=true;
}


public void newPlayer(int pid) {
	Players[pid]=new Gambler("Nuno Daniel",21,"Masculino");
	numberOfPlayers++;
	OccupiedSlots[pid]=true;
}

public String AvailableSpots() {
	String Message="Available Spots: ";
	for(int i=1;i<8;i++) {
		if(OccupiedSlots[i]==false) 
		{
			Message=Message + Integer.toString(i) +" ";
		}
		
	}
	return Message;
	
}

public boolean gameControllerPlayerSide(String Act){
    P.setAction(Act);
    
    
        if(P.getAction().equals("Hit")==true){
            D.setAction("Player Hit");
            D.doAction(P);
            return true;
            
        }
        else if(P.getAction().equals("Stand")==true){
            D.setAction("Player Stand");
            D.doAction(P);
            return true;
            
        }
        
    
    return false;

}

public boolean startGame(int bet){
    
        if(bet>=0){
            D.setAction("Dealer Start");
            D.doAction(P);
            D.setAction("Player Bet");
            D.doAction(P);
            P.setBet(bet);
            
            start=false;
            return true;
            
        
    }
    return false;
}

public void newGame() {
	start=true;
	D.newGame();
	for(int i=1;i<8;i++) {
		if(OccupiedSlots[i]) {
			Players[i].newGame();
		}
	}
    
    GameIsPlaying=true;
    PlayerTurn=true;
    setGameState("Betting");
}

public boolean gamelogic(String Message,int pid) {
	

	
	 D.setAction("Dealer Reveal");
	 D.doAction(Players[pid]);
	if(Message.contains("Dealer Draw")==true) {
		if(D.DealerHand.getHandValue()<=Players[pid].PlayerHand[0].getHandValue() && !Players[pid].isBust()) {
			D.setAction("Dealer Draw");
	        D.doAction(Players[pid]);
		}
		GameIsPlaying=D.DealerHand.getHandValue()<17 && D.DealerHand.getHandValue()<=Players[pid].PlayerHand[0].getHandValue() && !Players[pid].isBust();
		
	}
	if(!GameIsPlaying) {
		whoWon();
		setGameState("Finished");
	
	}
	
	return GameIsPlaying;
	

	
	
	
	
	
}

public void PlayerPlay(String Message,int pid) {
	if(Message.contains("Playing")==true) {
		if(Message.contains("Hit")==true) {
			D.setAction("Player Hit");
            D.doAction(Players[pid]);
            if(Players[pid].isBust()) {
            	setGameState("Ending");
            }
            else if(Players[pid].PlayerHand[0].getHandValue()==21) {
            	setGameState("Ending");
            }
            
		}
		
		else if(Message.contains("Stand")==true) {
			setGameState("Ending");
			D.setAction("Player Stand");
            D.doAction(Players[pid]);  
            }
		else if(Message.contains("Double")==true) {
			D.setAction("Player Hit");
			setGameState("Ending");
            D.doAction(Players[pid]);
            Players[pid].setBet(Players[pid].getBet()); 
            }
		}
}

public boolean getGameIsPlaying() {
	return GameIsPlaying;
}

public void payBet() {
	
	
	if(D.isBust() || D.DealerHand.getHandValue()<P.PlayerHand[0].getHandValue() ) {
		P.deposit(P.getBet()*2);
		 }
	else if(D.DealerHand.getHandValue()==P.PlayerHand[0].getHandValue()) {
		P.deposit(P.getBet());	
	}
	P.resetBet();
	
	
	
}
public void whoWon() {
	for(int i=1;i<8;i++) {
		if(OccupiedSlots[i]) {
			if(Players[i].isBust())
				WinDrawLose[i]=0;
			else if(D.isBust())
				WinDrawLose[i]=1;
			else if(D.DealerHand.getHandValue()>Players[i].PlayerHand[0].getHandValue()) {
				WinDrawLose[i]= 0;
			}
			else if(D.DealerHand.getHandValue()<Players[i].PlayerHand[0].getHandValue()) {
				WinDrawLose[i]= 1;
			}
			else {
				WinDrawLose[i]= 2;}
		}
		}
}

public String getGameState() {
	return gameState;
}

public void setGameState(String gameState) {
	this.gameState = gameState;
}


public String getInfo(){
	String info="GameState:"+ getGameState()+"%";
	info+= "Dealer" + ":Hand Size:" + D.DealerHand.getHandsize() + ":Hand Value:" + D.DealerHand.getHandValue() +":Hand Cards:" +D.DealerHand.toString()+" %";
	for(int i=1;i<8;i++) {
		if(OccupiedSlots[i]) {
			info+= "Player"+Integer.toString(i) + ":Wallet:"+Integer.toString(Players[i].getWallet())+":Bet:"+Integer.toString(Players[i].getBet()) +":Hand Size:" + Players[i].PlayerHand[0].getHandsize() + ":Hand Value:" +Players[i].PlayerHand[0].getHandValue() +":Hand Cards:"+ Players[i].PlayerHand[0].toString()+" "+":Result:" +Integer.toString(WinDrawLose[i]) +"%";

		}
		else{
			info+="Player"+Integer.toString(i)+":" +"Not Playing"+"%";
		}
	}
	
	return info;
}

public void setBet(int pid, int value) {
	Players[pid].setBet(value);
	
}

public void startPlaying() {
	D.setAction("Dealer Start");
    D.doAction(P);
	for(int i=1;i<8;i++) {
		if(OccupiedSlots[i]) {
	        D.setAction("Player Bet");
	        D.doAction(Players[i]);
			
		}
	}
}

}



