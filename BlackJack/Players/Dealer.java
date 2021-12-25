package BlackJack.Players;
import BlackJack.Cards.*;


public class Dealer extends Base_participant {

    public Hand DealerHand;
    private Shoe dShoe;
    private String Action;
    



    public Dealer(String name,int age, String gender,int numberOfDecks){
        super(name, age, gender);
        this.dShoe=new Shoe(numberOfDecks);
        this.DealerHand=new Hand("Dealer");
        
    }

    public void newGame(){
        this.DealerHand=new Hand("Dealer");
       
        
    }


    public void setAction(String Act){
        this.Action=Act;
    }

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
            
            //if splitted, draw another card

        }
        else if(this.Action.equals("Player Stand")){
            this.DealerHand.resetHidden();
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

    
    public boolean isBust(){
        if(this.DealerHand.getHandValue()>21){
            return true;
        }
        return false;
    }




   





}
