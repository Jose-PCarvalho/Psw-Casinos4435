package BlackJack.Cards;



public class Deck {
    BlackJackCard[] Cards;
    boolean Available[];

    public Deck(){
        final String[] Suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        final String[] Faces = {"Ace", "2", "3", "4","5","6","7","8","9","10","Jack","Queen","King"};
        int f=0;
        int s=0;
        for(int i=0;i<52;i++){
            Cards[i]=new BlackJackCard(Suits[s], Faces[f], i);
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
    }
   public BlackJackCard drawCard(int position){
       if(isAvailable(position)==true){    
        return Cards[position];
       }
       else{
           return null;   //should not use NULL, change later
       } 
   }

   public void insertCard(BlackJackCard Card){
       if(isAvailable(Card.getPosition())==false){
           Cards[Card.getPosition()]=Card;
       }
   }

   private boolean isAvailable(int position){
       return Available[position];
   }

}
