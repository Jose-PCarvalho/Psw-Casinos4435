package BlackJack.Cards;

/**
 * Class for the cards used in game.
 *
 */

public class BlackJackCard extends General_card {

    private int value;


    public BlackJackCard(String Suit, String Face,int positionInDeck){
        super(Suit,Face,positionInDeck);
        setValue();

    }
    
   

    /**
     *  Sets the value for any card. Used exclusively by the contrusctor
     */
    private void setValue(){
        
        if(this.getFace().equals("Ace")==true){
            this.value=11;
            
        }
        else if(this.getFace().equals("2")==true){
            this.value=2;
        }
        else if(this.getFace().equals("3")==true){
            this.value=3;
        }
        else if(this.getFace().equals("4")==true){
            this.value=4;
        }
        else if(this.getFace().equals("5")==true){
            this.value=5;
        }
        else if(this.getFace().equals("6")==true){
            this.value=6;
        }
        else if(this.getFace().equals("7")==true){
            this.value=7;
        }
        else if(this.getFace().equals("8")==true){
            this.value=8;
        }
        else if(this.getFace().equals("9")==true){
            this.value=9;
        }
        else if(this.getFace().equals("10")==true){
            this.value=10;
        }
        else if(this.getFace().equals("J")==true){
            this.value=10;
        }
        else if(this.getFace().equals("Q")==true){
            this.value=10;
        }
        else if(this.getFace().equals("K")==true){
            this.value=10;
        }
    }
    public int getValue(){
        return this.value;

    }

    public boolean isAce(){
        return (this.getFace().equals("Ace")) ? true : false;
    }

    /**
     * switches Ace value on demand.
     * @return true if the switch operation was succeful.
     * 
     */
    public boolean switchAceLow(){
        if(this.isAce() && this.getValue()==11){
            this.value=1;
            return true;
        }
        return false;
    }




}
