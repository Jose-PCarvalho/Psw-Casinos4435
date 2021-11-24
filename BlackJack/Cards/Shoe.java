package BlackJack.Cards;

public class Shoe {

    Deck[] Decks;
    int numberOfDecks;

    public Shoe(int numberOfDecks){
        for(int i=0;i<numberOfDecks;i++){
            Decks[i]=new Deck();
        }

    }
    public void ReceiveCard(BlackJackCard Card){
        for(int i=0;i<numberOfDecks;i++){
            if(this.Decks[i].insertCard(Card)==1){
                break;
            }
        }
    }

    public BlackJackCard dealCard(int position, int deckNumber){

        return Decks[deckNumber].drawCard(position);
    }

    public int AvailableCards(){
        int total=0;
        for (int i=0;i<numberOfDecks;i++){
            total+=this.Decks[i].getNumberOfCards();
        }
        return total;
    }
    
}
