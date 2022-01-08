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
    private int[] WinDrawLose={-1,-1,-1,-1,-1,-1,-1,-1};
    private boolean[] betEntered={false,false,false,false,false,false,false,false};
    private int numberOfBets=0;
    private boolean[] playerEndedTurn= {false,false,false,false,false,false,false,false};
    private int numberOfWins=0;
    private boolean[] kick= {false,false,false,false,false,false,false,false};
    int table;
    



public Game(int table){
    D=new Dealer("Dolores Aveiro", 4);
    this.table=table;
    start=true;
}


public void newPlayer(int pid, String name, int Balance) {
	Players[pid]=new Gambler(name,Balance);
	numberOfPlayers = getNumberOfPlayers() + 1;
	OccupiedSlots[pid]=true;
	playerEndedTurn[pid]=false;
	kick[pid]=false;
	if(gameState.equals("Waiting for Players")) {
		newGame();
	}
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

public void freeSpot(int pid) {
	numberOfPlayers = getNumberOfPlayers() - 1;
	System.out.print("Slot nr"+pid+" freed\n");
	OccupiedSlots[pid]=false;
	if(betEntered[pid]) {
		numberOfBets--;
		betEntered[pid]=false;
		playerEndedTurn[pid]=false;
		WinDrawLose[pid]=-1;
		kick[pid]=false;
	}
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
			
			payBet(i,WinDrawLose[i]);
			Players[i].newGame();
			betEntered[i]=false;
			playerEndedTurn[i]=false;
			WinDrawLose[i]=-1;
			if(Players[i].getWallet()==0) {
				kick[i]=true;
				
			}
		}
	}
    
    GameIsPlaying=true;
    PlayerTurn=true;
    numberOfBets=0;
    setGameState("Betting");
    numberOfWins=0;
}

public void endGame() {
	
	D.DealerHand.resetHidden();
	while(true) {
		
		numberOfWins=0;
		for(int i=0;i<8;i++) {
			if(betEntered[i]) {
				if(whoWon(i)==1) {
					numberOfWins++;
				}
			}
		}
		System.out.println("Estás aqui? "+numberOfWins + " "+  D.DealerHand.getHandValue() + " " +  D.isBust());
		if(numberOfWins>0 && D.DealerHand.getHandValue()<17 && !D.isBust()) {
			D.setAction("Dealer Draw");
	        D.doAction(Players[4]);
		}
		if(numberOfWins==0 || D.DealerHand.getHandValue()>=17 || D.isBust() ){
			setGameState("Finished");
			
			for(int i=0;i<8;i++) {
				if(betEntered[i]) {
					if(whoWon(i)==1) {
						numberOfWins++;
					}
				}
			}
			break;
		}
		
	}
	
	
	
	
	
	
	
}

public void PlayerPlay(String Message,int pid) {
	if(playerEndedTurn[pid]==false) {
	
		if(Message.contains("Hit")==true) {
			D.setAction("Player Hit");
            D.doAction(Players[pid]);
            if(Players[pid].isBust()) {
            	playerEndedTurn[pid]=true;
            }
            else if(Players[pid].PlayerHand[0].getHandValue()==21) {
            	playerEndedTurn[pid]=true;
            }
            
		}
		
		else if(Message.contains("Stand")==true) {
			playerEndedTurn[pid]=true;
			D.setAction("Player Stand");
            D.doAction(Players[pid]);  
            }
		else if(Message.contains("Double")==true) {
			D.setAction("Player Hit");
			playerEndedTurn[pid]=true;
            D.doAction(Players[pid]);
            Players[pid].setBet(Players[pid].getBet()); 
            }
		}
  checkPlayerTurns();
}

public boolean getGameIsPlaying() {
	return GameIsPlaying;
}

public void payBet(int pid,int result) {
	
	
	if(result==1 ) {
		Players[pid].deposit(Players[pid].getBet()*2);
		 }
	else if(result==2) {
		Players[pid].deposit(Players[pid].getBet()*2);	
	}
	
	
	
	
}
public int whoWon(int i) {
	
		if(OccupiedSlots[i]) {
			if(Players[i].isBust()) {
				WinDrawLose[i]=0;
				return 0;
			}
			else if(D.isBust()) {
				WinDrawLose[i]=1;
				return 1;
				}
			else if(D.DealerHand.getHandValue()>Players[i].PlayerHand[0].getHandValue()) {
				WinDrawLose[i]= 0;
				return 0;
			}
			else if(D.DealerHand.getHandValue()<Players[i].PlayerHand[0].getHandValue()) {
				WinDrawLose[i]= 1;
				return 1;
				
			}
			else if(D.DealerHand.getHandValue()==Players[i].PlayerHand[0].getHandValue()){
				WinDrawLose[i]= 2;
				return 2;
				}
		}
		return -1;
		
}

public String getGameState() {
	return gameState;
}

public void setGameState(String gameState) {
	this.gameState = gameState;
}


public String getInfo(){
	String info="GameState:"+ getGameState()+":"+table+"%";
	info+= "Dealer" + ":Hand Size:" + D.DealerHand.getHandsize() + ":Hand Value:" + D.DealerHand.getHandValue() +":Hand Cards:" +D.DealerHand.toString()+" %";
	for(int i=1;i<8;i++) {
		if(OccupiedSlots[i] && kick[i]==false) {
			
			info+= "Player"+Integer.toString(i) + ":Wallet:"+Integer.toString(Players[i].getWallet())+":Bet:"+Integer.toString(Players[i].getBet()) +":Hand Size:" + Players[i].PlayerHand[0].getHandsize() + ":Hand Value:" +Players[i].PlayerHand[0].getHandValue() +":Hand Cards:"+ Players[i].PlayerHand[0].toString()+" "+":Result:" +Integer.toString(WinDrawLose[i]) +"%";

		}
		else{
			info+="Player"+Integer.toString(i)+":" +"Not Playing"+"%";
		}
	}
	
	return info;
}
public String lobbyInfo(){
	String info="";
	info+= D.getName()+"%";
	for(int i=1;i<8;i++) {
		if(OccupiedSlots[i] && kick[i]==false) {
			
			info+= Players[i].getName()+"%";

		}
		else{
			info+=" %";
		}
		
	}
	info+="&";
	
	return info;
}

public void setBet(int pid, int value) {
	Players[pid].setBet(value);
	
	
}

public void confirmBet(int pid) {
	if(!betEntered[pid]) {
		betEntered[pid]=true;
		numberOfBets++;
	}
	if(numberOfBets==numberOfPlayers) {
		setGameState("Playing");
		startPlaying();
	}
}

public void startPlaying() {
	D.setAction("Dealer Start");
    D.doAction(P);
	for(int i=1;i<8;i++) {
		if(betEntered[i]) {
	        D.setAction("Player Bet");
	        D.doAction(Players[i]);
			
		}
	}
}


public int getNumberOfPlayers() {
	return numberOfPlayers;
}

public void checkPlayerTurns() {
	int n=0;
	for(int i=0;i<8;i++) {
	if(playerEndedTurn[i]) {
		n++;
	}
	if(n==getNumberOfPlayers()) {
		setGameState("Ending");
	}
		
	}
}

public void kickPlayer(int i) {
	kick[i]=true;
}

public void changeDealerName(String Name) {
	D.setName(Name);
}

}



