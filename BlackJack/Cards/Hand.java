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
    public void insertCard(BlackJackCard Card){
        handCards[numberOfCards]=new BlackJackCard(Card.getSuit(), Card.getFace(), Card.getPosition());
        numberOfCards++;

    }
    
   

    public int getHandValue(){
        this.handValue=0;
        boolean aceFlag=false;
        if(Hidden==true && numberOfCards>0){
            this.handValue=handCards[0].getValue();
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

    public String toString(){
        String str="";
        for(int i=0;i<numberOfCards;i++){
            if(i==1 && this.Hidden==true){

            }
            else{
            str=str+this.handCards[i].toString()+"\n";}

        }
        return str;
    }


}
