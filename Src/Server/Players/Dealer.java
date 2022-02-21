package BlackJack.Players;
import BlackJack.Cards.*;


public class Dealer extends Base_participant {

    public Hand DealerHand;
    private Shoe dShoe;
    private String Action;
    



    public Dealer(String name,int numberOfDecks){
        super(name);
        this.dShoe=new Shoe(numberOfDecks);
        this.DealerHand=new Hand("Dealer");
        
    }

    /**
     * Renews hand for a new game.
     */
    public void newGame(){
        this.DealerHand=new Hand("Dealer");
       
        
    }


    /**
     * @param Act
     * sets action to be completed later.
     */
    public void setAction(String Act){
        this.Action=Act;
    }

    /**
     * @param Player 
     * Does a specific game action.
     * for himself or the player.
     */
    public void doAction(Gambler Player){

        if(this.Action.equals("Dealer Start")){
            this.DealerHand.insertCard(this.dShoe.dealCard());
            this.DealerHand.insertCard(this.dShoe.dealCard());
            
        }

        if(this.Action.equals("Player Bet")){
            Player.PlayerHand[0].insertCard(this.dShoe.dealCard());
            Player.PlayerHand[0].insertCard(this.dShoe.dealCard());
            
        }
        
        else if(this.Action.equals("Player Hit") ){
            Player.PlayerHand[0].insertCard(this.dShoe.dealCard()); 
            

        }
        else if(this.Action.equals("Player Stand")){
            
        }

        else if(this.Action.equals("Dealer Draw")){
            if(this.DealerHand.isHidden()){
                this.DealerHand.resetHidden();
            }
            this.DealerHand.insertCard(this.dShoe.dealCard());
        
        }
        else if(this.Action.equals("Dealer Reveal")){
            if(this.DealerHand.isHidden()){
                this.DealerHand.resetHidden();
            }
        }

        


        this.Action="Done";

    }

    
    /**
     * @return
     * checks if hand is bust.
     */
    public boolean isBust(){
        if(this.DealerHand.getHandValue()>21){
            return true;
        }
        return false;
    }
    public Shoe getShoe() {
    	return dShoe;
    }




   





}
