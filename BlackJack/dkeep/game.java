package BlackJack.dkeep;
import BlackJack.Players.*;
import BlackJack.Interface.*;
public class Game {
    public Gambler P;
    public Dealer D;
    Boolean start;
    cli inter;



public Game(){
    D=new Dealer("Julia Pinheiro", 45, "Feminino", 4);
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


public void gameFlow(){
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
        D.setAction("Reveal");
        D.doAction(P);
        inter.PrintCasino(this);
        while(D.DealerHand.getHandValue()<=17 && D.DealerHand.getHandValue()<=P.PlayerHand[0].getHandValue()){
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



}



