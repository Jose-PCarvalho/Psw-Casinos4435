package BlackJack.Cards;


public class Hand{

    public BlackJackCard[] handCards=new BlackJackCard[52];
    private int handValue;
    private boolean bust;
    private int numberOfCards=0;
    private boolean Hidden;

    public Hand(){
        this.numberOfCards=0;
        this.bust=false;
        this.handValue=0;
        this.Hidden=false;
    }

    public Hand(String Dealer){
        this.numberOfCards=0;
        this.bust=false;
        this.handValue=0;
        this.Hidden=true;
    }


    public boolean isHidden(){
        return this.Hidden;
    }
    
    public void resetHidden(){
        this.Hidden=false;
    }
    /**
     * @param Card
     * Inserts specific card in the hand.
     */
    public void insertCard(BlackJackCard Card){
        handCards[numberOfCards]=new BlackJackCard(Card.getSuit(), Card.getFace(), Card.getPosition());
        numberOfCards++;

    }
    /**
     * @return
     * true if the hand is Blackjack, false if not
     */
    public boolean isBlackJack() {
    	if(numberOfCards==2 && getTrueHandValue()==21) {
    		return true;
    	}
    	return false;
    }
    
   

    /**
     * @return
     * return the hand value. Always takes in consideration if there's one card hidden
     */
    public int getHandValue(){
        this.handValue=0;
        boolean aceFlag=false;
        if(Hidden==true && numberOfCards>0){
            this.handValue=handCards[1].getValue();
        }
        else{
            for(int i=0;i<numberOfCards;i++){
                this.handValue+=handCards[i].getValue();
                if(this.handCards[i].isAce()==true){
                    aceFlag=true;
                }
            
            }

            if(this.handValue>21 && aceFlag==true){
                for(int i=0;i<numberOfCards;i++){
                    if(this.handCards[i].switchAceLow()==true){
                        this.handValue-=10;
                        break;
                    }
                }
            }
        }

        return this.handValue;
    }
    
    /**
     * @return
     * the hand value. never takes in consideration if card is hidden.
     */
    public int getTrueHandValue(){
        this.handValue=0;
        boolean aceFlag=false;
        int i=0;
     
            for(i=0;i<numberOfCards;i++){
                this.handValue+=handCards[i].getValue();
                if(this.handCards[i].isAce()==true){
                    aceFlag=true;
                }
            
          

            if(this.handValue>21 && aceFlag==true){
                for(i=0;i<numberOfCards;i++){
                    if(this.handCards[i].switchAceLow()==true){
                        this.handValue-=10;
                        break;
                    }
                }
            }
        }

        return this.handValue;
    }
    

   

    /**
     * @return
     * true if the handValue>21
     */
    public boolean isBust(){
        this.bust=false;
        if(getHandValue()>21){
            this.bust=true;
            
        }
        return this.bust;
    }
    public int getHandsize(){
        return numberOfCards;
    }

    /**
     * Transforms card into a string ready to be intreperted by the GUI controller.
     */
    public String toString(){
        String str="";
        for(int i=0;i<numberOfCards;i++){
            if(i==0 && this.Hidden==true){
            	str=str+"cardback"+" ";

            }
            else{
            str=str+this.handCards[i].toString()+" ";}

        }
        return str;
    }


}
