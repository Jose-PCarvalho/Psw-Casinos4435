package BlackJack.dkeep;
import BlackJack.Players.*;

import java.util.Timer;
import java.util.TimerTask;
public class Game{
    public Gambler P;
    public Dealer D;
    Boolean start;

    
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
    Timer betTimer= new Timer();
    TimerTask betTimerTask;
    boolean betTimeisUp=false;
    Timer playTimer= new Timer();
    TimerTask playTimerTask;
    boolean playTimeisUp=false;
    Timer newGameTimer= new Timer();
    TimerTask newGameTimerTask;
    int newGameCounter=0;
    boolean newGameTimeisUp=false;
    int GameRequest=0;
	private int betCounter=0;
	private int playCounter=0;
	

    



public Game(int table){
    D=new Dealer("Dolores Aveiro", 4);
    this.table=table;
    start=true;
    for(int i=0;i<8;i++) {
    	Players[i]=new Gambler("Default",0);
    }
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
	if(OccupiedSlots[pid]==true) {
		numberOfPlayers = getNumberOfPlayers() - 1;
		System.out.print("Slot nr"+pid+" freed\n");
		OccupiedSlots[pid]=false;
		if(getBetEntered()[pid]) {
			numberOfBets--;
			getBetEntered()[pid]=false;
			playerEndedTurn[pid]=false;
			WinDrawLose[pid]=-1;
			kick[pid]=false;
		}
	}
}



public void newGame() {
	newGameTimer.cancel();
	newGameTimeisUp=false;
	playTimer.cancel();
	betTimer.cancel();
	betCounter=10;
	playTimeisUp=false;
	betTimeisUp=false;
	start=true;
	GameRequest=0;
	D.newGame();
	if(D.getShoe().AvailableCards()<D.getShoe().getnumberOfDecks()*52/2) {
		D.getShoe().renewShoe();
	}
	for(int i=1;i<8;i++) {
		if(OccupiedSlots[i]) {
			
			payBet(i,WinDrawLose[i]);
			Players[i].newGame();
			getBetEntered()[i]=false;
			playerEndedTurn[i]=false;
			WinDrawLose[i]=-1;
			Players[i].setInsurance(false);

		}
	}
    
    GameIsPlaying=true;
    PlayerTurn=true;
    numberOfBets=0;
    setGameState("Waiting for Players");
    numberOfWins=0;
}

public void endGame() {
	playTimer.cancel();
	playTimeisUp=false;
	D.DealerHand.resetHidden();
	while(true) {
		System.out.println("Calculating Outcomes");
		numberOfWins=0;
		int numberOfDraws=0;
		for(int i=0;i<8;i++) {
			if(getBetEntered()[i]) {
				playerEndedTurn[i]=true;
				if(whoWon(i)==1) {
					numberOfWins++;
				}
				else if(whoWon(i)==2) {
					numberOfDraws++;
				}
			}
		}
		if((numberOfWins>0 ||numberOfDraws>0 )&& D.DealerHand.getHandValue()<17 && !D.isBust()) {
			D.setAction("Dealer Draw");
	        D.doAction(Players[4]);
		}
		if((numberOfWins==0 && numberOfDraws==0) || D.DealerHand.getHandValue()>=17 || D.isBust() ){
			setGameState("Finished");
			
			for(int i=0;i<8;i++) {
				if(getBetEntered()[i]) {
					if(whoWon(i)==1) {
						numberOfWins++;
					}
				}
			}
			break;
		}
		
	}
	
	newGameTimer=new Timer();
	newGameCounter=15;
	newGameTimerTask=new TimerTask(){
	

		@Override
		public void run() {
			if(newGameCounter>0) {
				newGameCounter--;
				System.out.println("Time new Game Remaining "+ newGameCounter);
				if(newGameCounter==0) {
					System.out.println("Time is Up ");
					newGameTimeisUp=true;
					newGameTimer.cancel();
				}
			}
			
			
		}
		
	};
	newGameTimer.scheduleAtFixedRate(newGameTimerTask, 0, 1000);
	
	
	
	
	
	
	
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
			if(Players[pid].getWallet()>Players[pid].getBet()) {
				D.setAction("Player Hit");
				playerEndedTurn[pid]=true;
	            D.doAction(Players[pid]);
	            Players[pid].setBet(Players[pid].getBet()); 
	            }
            }
		
		checkPlayerTurns();
		}
  
}

public boolean getGameIsPlaying() {
	return GameIsPlaying;
}

public void payBet(int pid,int result) {
	
	
	if(result==1 ) {
		if(Players[pid].PlayerHand[0].isBlackJack()) {
			Players[pid].deposit((Players[pid].getBet()*3)/2);
		}
		else if(Players[pid].getInsurance()) {
			Players[pid].deposit((Players[pid].getBet()));
		}
		else {
		Players[pid].deposit(Players[pid].getBet()*2);}
		 }
	else if(result==2) {
		if(Players[pid].getInsurance()) {
			Players[pid].deposit((Players[pid].getBet()/2));
		}
		else 
			Players[pid].deposit(Players[pid].getBet());	
	}
	
	
	
	
	
}
public int whoWon(int i) {
	
		if(getBetEntered()[i]) {
			
			if(Players[i].getInsurance() && D.DealerHand.isBlackJack()) {
				WinDrawLose[i]=1;
				return 1;
			}
			else if(Players[i].isBust()) {
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
	String ins="No";
	if(D.DealerHand.getHandValue()>=10 && D.DealerHand.getHandsize()==2 && getGameState().equals("Playing")) {
		ins="Insurance";
	}
	String info="GameState:"+ getGameState()+":"+table+":"+betCounter+":"+playCounter+":"+newGameCounter+":"+ins+"%";
	info+= "Dealer" + ":Hand Size:" + D.DealerHand.getHandsize() + ":Hand Value:" + D.DealerHand.getHandValue() +":Hand Cards:" +D.DealerHand.toString()+" %";
	for(int i=1;i<8;i++) {
		if(OccupiedSlots[i]) {
			
			info+= "Player"+Integer.toString(i) + ":Wallet:"+Integer.toString(Players[i].getWallet())+":Bet:"+Integer.toString(Players[i].getBet()) +":Hand Size:" + Players[i].PlayerHand[0].getHandsize() + ":Hand Value:" +Players[i].PlayerHand[0].getHandValue() +":Hand Cards:"+ Players[i].PlayerHand[0].toString()+" "+":Result:" +Integer.toString(WinDrawLose[i]) +":Participating:"+getBetEntered()[i]+":TurnEnded:"+playerEndedTurn[i]+"%";

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
	if(value==-1) {
		Players[pid].setBet(Players[pid].getWallet());
	}
	else {
	Players[pid].setBet(value);}
	
	
}

public void confirmBet(int pid) {
	if(!getBetEntered()[pid]) {
		getBetEntered()[pid]=true;
		numberOfBets++;
	}
	
	if(getNumberOfParticipatingPlayers()==1) {
		betCounter=10;
		betTimer=new Timer();
		betTimerTask=new TimerTask(){
		

			@Override
			public void run() {
				if(betCounter>0) {
					betCounter--;
					System.out.println("Time Remaining "+ betCounter);
					if(betCounter==0) {
						System.out.println("Time is Up ");
						betTimeisUp=true;
						
					}
				}
				
				
			}
			
		};
		betTimer.scheduleAtFixedRate(betTimerTask, 0, 1000);
	}
	if(numberOfBets==numberOfPlayers) {
		startPlaying();
		
	}
}

public void startPlaying() {
	betTimeisUp=false;
	betTimer.cancel();
	setGameState("Playing");
	D.setAction("Dealer Start");
    D.doAction(P);
	for(int i=1;i<8;i++) {
		if(getBetEntered()[i]) {
	        D.setAction("Player Bet");
	        D.doAction(Players[i]);
	        if(Players[i].PlayerHand[0].getHandValue()==21) {
            	playerEndedTurn[i]=true;
            }
			
		}
	}
	checkPlayerTurns();
	
	playTimer=new Timer();
	playCounter=30;
	playTimerTask=new TimerTask(){
		

		@Override
		public void run() {
			if(playCounter>0) {
				playCounter--;
				System.out.println("Time Remaining "+ playCounter);
				if(playCounter==0) {
					System.out.println("Time is Up ");
					playTimeisUp=true;
					
					
				}
			}
			
			
		}
		
	};
	playTimer.scheduleAtFixedRate(playTimerTask, 0, 1000);
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
	
	}
	if(n==getNumberOfParticipatingPlayers()) {
		setGameState("Ending");
		endGame();
	}
}

public void kickPlayer(int i) {
	freeSpot(i);
}
public int getNumberOfParticipatingPlayers() {
	int n=0;
	for(int i=1;i<8;i++) {
		if(getBetEntered()[i])
			n++;
	}
	return n;
}

public void changeDealerName(String Name) {
	D.setName(Name);
}

public boolean sanityCheck() {
	boolean UpdateNeed=false;
	if(numberOfPlayers==0 && !getGameState().equals("Waiting for Players")) {
		newGame(); 
		UpdateNeed= true;
	}

	if(numberOfPlayers!=0 && getGameState().equals("Waiting for Players")) {
		setGameState("Betting");
		UpdateNeed= true;
		
	}
	for(int i=1;i<8;i++) {
		if(getGameState().equals("Betting") && Players[i].getWallet()==0 && Players[i].getBet()==0 && OccupiedSlots[i]) {
			freeSpot(i);
			UpdateNeed= true;
		}
		
	}
	if(getGameState().equals("Betting") && betTimeisUp) {
		startPlaying();
		UpdateNeed= true;
	}
	else if(getGameState().equals("Playing") && playTimeisUp) {
		endGame();
		UpdateNeed= true;
	}
	else if(getGameState().equals("Finished") && newGameTimeisUp) {
		newGame();
		UpdateNeed= true;
	}
	if(!getGameState().equals("Waiting for Players"))
		UpdateNeed=true;
	return UpdateNeed;
}

public void newGameRequest() {
	GameRequest++;
	
	if(GameRequest==getNumberOfParticipatingPlayers()) {
		newGameTimer.cancel();
		newGame();
	}
}


public void cancelBet(int iD) {
	if(!getBetEntered()[iD]) {
		getBetEntered()[iD]=false;
		Players[iD].deposit(Players[iD].getBet());
		Players[iD].resetBet();
	}
	
}


public boolean[] getBetEntered() {
	return betEntered;
}

public void setInsurance(int pid) {
	Players[pid].setInsurance(true);
	Players[pid].setBet(Players[pid].getBet());
	
	
}




}



