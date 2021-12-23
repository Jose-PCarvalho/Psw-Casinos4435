package BlackJack.Interface;
import java.util.Scanner;

import BlackJack.dkeep.Game;
public class cli {
    Scanner s;


    public cli(){
        s=new Scanner(System.in);
    }

    public String getPlayerAction(){
        while(s.hasNextLine()==false){  
        }
        System.out.print("Enter an Action ");  
        String str= s.next(); 
        return str;
    }

    public void PrintCasino(Game g){
        System.out.println("*********************");
        System.out.print("Dealer Name "+ g.D.getName());
        System.out.println(" Hand Value: "+g.D.DealerHand.getHandValue());
        System.out.println(" Hand : \n"+g.D.DealerHand.toString());

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.print("Gambler Name "+ g.P.getName());
        System.out.print(" Bet"+g.P.getBet());
        System.out.print(" Currency "+g.P.getWallet());
        System.out.println(" Hand Value: "+g.P.PlayerHand[0].getHandValue());
        System.out.println(" Hand : \n"+g.P.PlayerHand[0].toString());
        


    }

    public int getPlayerBet(){
        System.out.print("Enter a bet ");  
        while(s.hasNextLine()==false){  
        }
          
        int num = s.nextInt();
        return num;
    }

    public void printWin(){
        System.out.println("YOU WON");
    }




}




