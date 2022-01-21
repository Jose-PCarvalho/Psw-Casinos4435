package BlackJack.Cards;



public class Deck {
    BlackJackCard[] Cards=new BlackJackCard[52];
    boolean[] Available=new boolean[52];
    private int numberOfCards;

    public Deck(){
        final String[] Suits = {"hearts", "diamonds", "clubs", "spade"};
        final String[] Faces = {"Ace", "2", "3", "4","5","6","7","8","9","10","J","Q","K"};
        int f=0;
        int s=0;
        for(int i=0;i<52;i++){
            this.Cards[i]=new BlackJackCard(Suits[s], Faces[f], i);
            f++;
            s++;
            if(s>3){
                s=0;
            }
            if(f>12){
                f=0;
            }
            Available[i]=true;
        }
        this.numberOfCards=52;
    }
   public BlackJackCard drawCard(int position){
       if(isAvailable(position)==true){  
        this.numberOfCards--;  
        Available[position]=false;
        return Cards[position];
       }
       else{
           return null;   
       } 
   }

   public boolean insertCard(BlackJackCard Card){
       if(isAvailable(Card.getPosition())==false){
           Cards[Card.getPosition()]=Card;
           this.numberOfCards++;
           Available[Card.getPosition()]=true;
           return true;
       }
       return false;
   }

   public boolean isAvailable(int position){
       return Available[position];
   }
   public int getNumberOfCards(){
       return this.numberOfCards;
   }
   

}
