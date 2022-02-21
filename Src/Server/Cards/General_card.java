package BlackJack.Cards;

public class General_card {
    private String Suit;
    private String Face;
    private int positionInDeck;

    public General_card(String Suit, String Face,int positionInDeck){
        setFace(Face);
        setSuit(Suit);
        this.positionInDeck=positionInDeck;
    }

    private void setSuit(String Suit){
        this.Suit=Suit;
    }

    private void setFace(String Face){
        this.Face=Face;
    }

    public String getSuit(){
        return(this.Suit);
    }

    public String getFace(){
        return(this.Face);
    }
    public int getPosition(){
        return this.positionInDeck;
    }

    public String toString()
    {
        return  getSuit()+getFace();
    }

}
