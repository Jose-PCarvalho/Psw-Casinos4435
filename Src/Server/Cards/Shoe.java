package BlackJack.Cards;
import java.util.Random;
public class Shoe {

    Deck[] Decks=new Deck[10];
    int numberOfDecks;

    public Shoe(int numberOfDecks){
        this.numberOfDecks=numberOfDecks;
        createDecks(numberOfDecks);

    }
    
    public int getnumberOfDecks() {
    	return numberOfDecks;
    }
    /**
     * @param Card
     * Receives a specific card and sees if it can be inserted in any deck
     */
    public void ReceiveCard(BlackJackCard Card){
        for(int i=0;i<numberOfDecks;i++){
            if(this.Decks[i].insertCard(Card)==true){
                break;
            }
        }
    }

    /**
     * @param position
     * @param deckNumber
     * @return
     * Deals a specific card, by position and number of the deck.
     */
    public BlackJackCard dealCard(int position, int deckNumber){

        return Decks[deckNumber].drawCard(position); 
    }

    /**
     * @return
     * Deals a random Card.
     */
    public BlackJackCard dealCard(){
        Random rand = new Random();
        boolean found=false;
        int position = rand.nextInt(51);
        int deckNumber = rand.nextInt(numberOfDecks);
        BlackJackCard card=Decks[deckNumber].drawCard(position);
        if(card==null){
            while(found==false){
                position = rand.nextInt(51);
                deckNumber = rand.nextInt(numberOfDecks);
                card=Decks[deckNumber].drawCard(position);
                if(card!=null){
                    found=true;
                }
            }
        }
    
        return card;

    }

    


    /**
     * @return
     * gets the number of cards available in the deck.
     */
    public int AvailableCards(){
        int total=0;
        for (int i=0;i<numberOfDecks;i++){
            total+=this.Decks[i].getNumberOfCards();
        }
        return total;
    }

    public void renewShoe(){
        for(int i=0;i<numberOfDecks;i++){
            Decks[i]=new Deck();
        }
    }
    private void createDecks(int numberOfDecks){
        for(int i=0;i<numberOfDecks;i++){
            Decks[i]=new Deck();
        }
        
    }
    
}


