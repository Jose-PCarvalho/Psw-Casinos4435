package BlackJack.Cards;


public class Hand{

    public BlackJackCard[] handCards;
    private int handValue;
    private boolean bust;
    private int numberOfCards=0;

    public Hand(){
        this.numberOfCards=0;
        this.bust=false;
        this.handValue=0;

    }
    
    public void insertCard(BlackJackCard Card){
        handCards[numberOfCards]=new BlackJackCard(Card.getSuit(), Card.getSuit(), Card.getPosition());
        numberOfCards++;

    }
    
    public int getHandValue(){
        this.handValue=0;
        boolean aceFlag=false;
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

        return this.handValue;
    }

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


}
