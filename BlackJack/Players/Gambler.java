package BlackJack.Players;
import BlackJack.Cards.*;
public class Gambler extends Base_participant {
    
    public Hand[] PlayerHand=new Hand[2];
    private int wallet;
    private boolean Double;
    private String Action;
    private boolean ActionDone;
    private int bet;
    private boolean insurance=false;

    public Gambler(String name,int balance){
        super(name);
        newHands();    
        wallet=balance;
    }

    public void newHands(){
        for(int i=0;i<2;i++){
        PlayerHand[i]=new Hand();
         }
    }

    public void newGame(){
        PlayerHand[0]=new Hand();
        PlayerHand[1]=new Hand();
        resetBet();
    }

    public void setAction(String Act){
        this.resetAction();
        this.Action=Act;
    }
    public void finalizeAction(){
        this.ActionDone=true;
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

    public int getWallet(){
        return wallet;
    }

    public void deposit(int quantity){
        this.wallet+=quantity;
    }

    public void withdraw(int quantity){
        this.wallet-=quantity;
    }

    public void setBet(int quantity){
    	if(quantity>this.wallet) {
    		this.bet+=this.wallet;
    		this.wallet=0;
    	}
    	else {
        this.bet+=quantity;
        this.wallet-=quantity;}
    }
    public void resetBet(){
        this.bet=0;
    }
    public int getBet(){
        return this.bet;
    }

    public void winnings(int quantity){
        this.wallet+=quantity;

    }

    public void actionDouble(){
        Double=true;   // complete afterwards
    }

    public boolean isDouble(){
        return Double;
    }

    public boolean isBust(){
        if(this.PlayerHand[0].getHandValue()>21){
            return true;
        }
        return false;
    }

	public boolean getInsurance() {
		return insurance;
	}

	public void setInsurance(boolean insurance) {
		this.insurance = insurance;
	}






}
