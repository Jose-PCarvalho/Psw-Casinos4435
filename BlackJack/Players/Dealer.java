package BlackJack.Players;
import BlackJack.Cards.*;
import java.util.Random;

public class Dealer extends Base_participant {

    public Hand DealerHand;
    private Shoe dShoe;



    public Dealer(String name,int age, String gender,int numberOfDecks){
        super(name, age, gender);
        this.dShoe=new Shoe(numberOfDecks);
        this.DealerHand=new Hand();
    }

    public void newGame(){
        DealerHand=new Hand();
    }


    public void Deal(Gambler Player){

        Random rand = new Random();
  
        dsasdsafd
        int rand_int1 = rand.nextInt(1000);
        int rand_int2 = rand.nextInt(1000);

        


    }



}
