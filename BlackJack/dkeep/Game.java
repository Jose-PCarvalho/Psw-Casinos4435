package BlackJack.dkeep;
import BlackJack.Players.*;
import BlackJack.Interface.*;
public class Game implements Runnable{
    public Gambler P;
    public Dealer D;
    Boolean start;
    cli inter;
    private Thread t;
    boolean PlayerTurn=true;
    boolean GameIsPlaying=true;



public Game(){
    D=new Dealer("Dolores Aveiro", 66, "Feminino", 4);
    P=new Gambler("Nuno Daniel",21,"Masculino");
    inter=new cli();
    start=true;
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
    P.newGame();
    D.newGame();
    GameIsPlaying=true;
    PlayerTurn=true;
}
public void run(){
    start=true;
    P.newGame();
    D.newGame();
    inter.PrintCasino(this);

    while(startGame(inter.getPlayerBet())==false){

    }
    inter.PrintCasino(this);

    while(true){
    
        gameControllerPlayerSide(inter.getPlayerAction());
        inter.PrintCasino(this);
        if(P.getAction().equals("Stand")){
            D.setAction("Player Stand");
            D.doAction(P);

            break;
        }
        if(P.isBust()==true || P.PlayerHand[0].getHandValue()==21){
            break;
        }
        

    }
    inter.PrintCasino(this);

    if(P.isBust()==true){
        P.resetBet();
        start=true;
        return;
    }

    else{
        
        inter.PrintCasino(this);
        while(D.DealerHand.getHandValue()<17 && D.DealerHand.getHandValue()<=P.PlayerHand[0].getHandValue()){
            D.setAction("Dealer Draw");
            D.doAction(P);
            inter.PrintCasino(this);

        }

        if(D.isBust()|| D.DealerHand.getHandValue()<=P.PlayerHand[0].getHandValue()){
            P.deposit(P.getBet()*2);
            P.resetBet();
            start=true;
            inter.PrintCasino(this);
            inter.printWin();
        }


    }


}



public void start () {
    System.out.println("Starting " );
    start=true;
    P.newGame();
    D.newGame();
    inter.PrintCasino(this);
    if (t == null) {
       t = new Thread (this);
       t.start ();
    }
 }


public boolean gamelogic(String Message) {
	
	if(Message.contains("Bet Entered")==true) {
		int value=Integer.parseInt(Message.replaceAll("[\\D]", ""));
		this.P.setBet(value);	
	}
	
	else if(Message.contains("Bet Confirmed")==true){
		
		D.setAction("Dealer Start"); //Alterar para quando funcionar em multiplayer
        D.doAction(P);
        D.setAction("Player Bet");
        D.doAction(P);
        
        
		
	}
	
	else if(Message.contains("Play")==true && PlayerTurn==true) {
		if(Message.contains("Hit")==true) {
			D.setAction("Player Hit");
            D.doAction(P);
            GameIsPlaying=!P.isBust();
		}
		
		else if(Message.contains("Stand")==true) {
			D.setAction("Player Stand");
            D.doAction(P);
            PlayerTurn=false;   
            }
		else if(Message.contains("Double")==true) {
			D.setAction("Player Hit");
            D.doAction(P);
            P.setBet(P.getBet());
            PlayerTurn=false;   
            }
		}
	else if(Message.contains("Dealer Draw")==true) {
		D.setAction("Dealer Draw");
        D.doAction(P);
		GameIsPlaying=D.DealerHand.getHandValue()<17 && D.DealerHand.getHandValue()<=P.PlayerHand[0].getHandValue();
	}
	
	return GameIsPlaying;
	

	
	
	
	
	
}

public boolean getGameIsPlaying() {
	return GameIsPlaying;
}

public void payBet() {
	
	
	if(D.isBust() || D.DealerHand.getHandValue()<P.PlayerHand[0].getHandValue() ) {
		P.deposit(P.getBet()*2);
		 }
	P.resetBet();
	
	
	
}
public int whoWon() {
	if(P.isBust())
		return 0;
	if(D.isBust())
		return 1;
	if(D.DealerHand.getHandValue()>P.PlayerHand[0].getHandValue()) {
		return 0;
	}
	if(D.DealerHand.getHandValue()<P.PlayerHand[0].getHandValue()) {
		return 1;
	}
	else {
		return 2;
	}
}

}



