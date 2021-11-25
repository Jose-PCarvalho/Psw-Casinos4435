package BlackJack.Players;
import BlackJack.Cards.*;
public class Gambler extends Base_participant {
    
    public Hand[] PlayerHand;
    private int wallet;
    private boolean Double;
    private String Action;
    private boolean ActionDone;

    public Gambler(String name,int age, String gender){
        super(name, age, gender);
        PlayerHand[1]=new Hand();
        PlayerHand[2]=new Hand();    
    }

    public void DoAction(String Act){

        if(Act.equals("Hit")==true || Act.equals("Stand")==true || Act.equals("Double")==true || Act.equals("Split")==true || Act.equals("Leave")==true || Act.equals("Bet") ){
            this.Action=Act;
            ActionDone=true;
        }

    }
    public boolean isActionDone(){
        return ActionDone;
    }

    public String getAction(){
        return Action;
    }

    public void resetAction(){
        ActionDone=false;
    }

    public int checkWallet(){
        return wallet;
    }

    public void deposit(int quantity){
        this.wallet+=quantity;
    }

    public void withdraw(int quantity){
        this.wallet-=quantity;
    }

    public void actionDouble(){
        Double=true;   // complete afterwards
    }

    public boolean isDouble(){
        return Double;
    }






}
