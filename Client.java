
import BlackJack.dkeep.*;
import BlackJack.Cards.*;
import BlackJack.Players.Dealer;
import BlackJack.Players.Gambler;


public class Client {

    public static void main(String[] args) {
        //Deck d=new Deck();
        //System.out.println(d.drawCard(40).getSuit());
        Gambler Player=new Gambler("JJ", 88, "M");
        Dealer D=new Dealer("JJ", 88, "M",4);
        D.setAction("Player Hit");
        D.doAction(Player);
        System.out.println(Player.PlayerHand[0].handCards[0].getFace() + " " + Player.PlayerHand[0].handCards[0].getValue());

        
        

        Game g=new Game();
        while(true){
            g.gameFlow();

        }


    }
}