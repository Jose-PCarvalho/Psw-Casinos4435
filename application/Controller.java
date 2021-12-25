package application;

import java.util.ArrayList;

import BlackJack.dkeep.Game;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class Controller {

 //ImageView is a Node used for painting images loaded with Images

 // Image = picture 
 // ImageView = picture frame
 
 @FXML 
 GridPane ChipGrid;
 
 @FXML 
 Label WalletGUI;
 @FXML 
 Label BetGUI;
 @FXML
 ImageView ConfirmButton;
 @FXML
 GridPane PlayGrid;
 @FXML AnchorPane Pane;
 
 final int[] BetValues= {1,2,5,10,20,25,50,100,250,500,1000,2000,5000};
 int currentBet=0;
 int walletValue=10000;
 boolean confirmFlag=true;
 Image confirmImage = new Image(getClass().getResourceAsStream("../Resources/GeneralAssets/Confirm.png"));
 Image Join = new Image(getClass().getResourceAsStream("../Resources/GeneralAssets/Join.png"));
 ImageView JoinButton[] = new ImageView[8];
 Circle PlayerCircle[] = new Circle[8];
 Label PlayerPoints[] = new Label[8];
 ImageView FinalScreen;
 Game g;
 String GameState="";
 boolean playGridOn=false;
 int pid=4;
 //ArrayList<ImageView> HandPlayer1=new ArrayList<ImageView>();
 //ArrayList < ArrayList<ImageView>> PlayersHand = new ArrayList <ArrayList<ImageView> >(7);
 
 ImageView[][] PlayersHands= new ImageView[8][10];
 ImageView[] DealerHand= new ImageView[10];

private boolean SendMessage=false;

private String message="";

 
 
@FXML 
public void BetEntered(MouseEvent e) {
	if(GameState.equals("Betting")) {
	 Node clickedNode = e.getPickResult().getIntersectedNode();
	    if (clickedNode != ChipGrid) {
	        if(currentBet==0) {
	        	ConfirmButton.setImage(confirmImage);
	        	confirmFlag=false;
	        }
	        Integer colIndex = GridPane.getColumnIndex(clickedNode);
	       
	        currentBet=BetValues[colIndex];
	        setMessage("Player"+Integer.toString(pid)+ ":Bet Entered" + currentBet);
	        messageResquest();
	        
	        

	        
	        
	        
	    }
	}
 }

public void BetConfirmed() {
	if(GameState.equals("Betting") && currentBet>0) {
		ConfirmButton.setImage(null);
		setMessage("Player"+Integer.toString(pid)+ ":Bet Confirmed");
        messageResquest();
		currentBet=0;
	}
	
	
	}
 
public void setBetValue(int walletValue,int currentBet) {
	WalletGUI.setText(Integer.toString(walletValue));
	BetGUI.setText(Integer.toString(currentBet));
}

public void setBetValue() {
	WalletGUI.setText(Integer.toString(g.P.getWallet()));
	BetGUI.setText(Integer.toString(g.P.getBet()));
}
 

 
 public void populateGrid() {
	 String[] ChipName= {"../Resources/Coins/1.png","../Resources/Coins/2.png","../Resources/Coins/5.png","../Resources/Coins/10.png","../Resources/Coins/20.png","../Resources/Coins/25.png","../Resources/Coins/50.png","../Resources/Coins/100.png","../Resources/Coins/250.png","../Resources/Coins/500.png","../Resources/Coins/1000.png","../Resources/Coins/2000.png","../Resources/Coins/5000.png"};
	 
	 for(int i=0;i<13;i++) {
		 Image ChipImage= new Image(getClass().getResourceAsStream(ChipName[i]));
		 ImageView Chip= new ImageView(ChipImage);
		 Chip.setFitHeight(76);
		 Chip.setFitWidth(68);
		 ChipGrid.add(Chip, i,0);
			 
	 }
	 ChipGrid.setHgap(0);
	 
 }
 
 public void initialize() {
	 populateGrid();
	 message="Player Joined";
	 messageResquest();
	 
	 
	 //g=new Game();
	 //setBetValue(g.P.getWallet(),g.P.getBet());
	
	 
	 
 
 }
 
 public void setJoinButton(String message) {
	 
	 int i=-1;
	 for (String val: message.split(" ")) {
         System.out.println(val +"\n");
	         if(i>0) {
				 JoinButton[i]=new ImageView(Join);
				 JoinButton[i].setLayoutX(100+150*i);
				 JoinButton[i].setLayoutY(653-150);
				 JoinButton[i].setPreserveRatio(true);
				 JoinButton[i].setSmooth(true);
				 JoinButton[i].setFitWidth(75);
				 //JoinButton[4].setFitWidth(7);
				 JoinButton[i].toFront();
			     Pane.getChildren().add(JoinButton[i]);
			     
			     JoinButton[i].addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
			    	 for(int n=1;n<8;n++) {
			    		 pid=4;
				         Pane.getChildren().remove(JoinButton[n]);
			    	 }
			         setMessage("New Player on position:" + Integer.toString(pid));
			         messageResquest();
			         event.consume();
		     });}
	         i++;
 }
 }
 
private void setPlayGrid() {
	 String[] PlayName= { "../Resources/GeneralAssets/Double.png","../Resources/GeneralAssets/Hit.png","../Resources/GeneralAssets/Stand.png" };
 
	for(int i=0;i<3;i++) {
		 PlayGrid.setHgap(20);
		 Image PlayImage= new Image(getClass().getResourceAsStream(PlayName[i]));
		 ImageView Play= new ImageView(PlayImage);
		 Play.setFitHeight(175);
		 Play.setFitWidth(105);
		 PlayGrid.add(Play, i,0);
		 playGridOn=true;
	 }
	
 }
private void removePlayGrid() {
	PlayGrid.getChildren().remove(0);
	PlayGrid.getChildren().remove(0);
	PlayGrid.getChildren().remove(0);
	playGridOn=false;
}
 

public void Play(MouseEvent e) {
	if(GameState.equals("Playing")) {
	 Node clickedNode = e.getPickResult().getIntersectedNode();
	    if (clickedNode != PlayGrid) {
	    	Integer colIndex = GridPane.getColumnIndex(clickedNode);
	    	if(colIndex==0) {
	    		
	    		 setMessage("Playing: Player" + Integer.toString(pid) +" Double");
		         messageResquest();
	    		
	    	}
	    	else if(colIndex==1) {
	    		setMessage("Playing: Player" + Integer.toString(pid) +" Hit");
		        messageResquest();
	    	}
	    	else if(colIndex==2) {
	    		setMessage("Playing: Player" + Integer.toString(pid) +" Stand");
		        messageResquest();
	    	}
	    	
	        
	    }
	        
	    }

}

public void gameOver(String Result) {
	removePlayGrid();
	//int who=g.whoWon();
	 String[] Screens= { "../Resources/GeneralAssets/LosingScreen.png","../Resources/GeneralAssets/WinningScreen.png","../Resources/GeneralAssets/DrawScreen.png" };
	 FinalScreen= new ImageView(new Image(getClass().getResourceAsStream(Screens[Integer.parseInt(Result)])));
	 FinalScreen.setLayoutX(500);
	 FinalScreen.setLayoutY(200+120);
	 FinalScreen.setPreserveRatio(true);
	 Pane.getChildren().add(FinalScreen);
	 FinalScreen.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
	         newGame();
	         event.consume();
	     });

}

public void newGame() {
	
	Pane.getChildren().remove(FinalScreen);
	setMessage("New Game");
	for (int i=0;i<8;i++) {
		removeCards(i,10);
	}
    messageResquest();
	
	
	
}

public void removeCards(int id, int n) {

	
	System.out.println(n);
	for (int i=0;i<n;i++) {
		
	     Pane.getChildren().remove(PlayersHands[id][i]);
	}
	
	
	Pane.getChildren().remove(PlayerPoints[id]);
	Pane.getChildren().remove(PlayerCircle[id]);
}


	
	public boolean isReady(){
		return SendMessage;
		
	}
	
	public String getMessage() {	
		return message;
	}
	
	public void setMessage(String str) {	
		 message=str;
	}
	public void messageResquest() {
		SendMessage=true;
	}
	public void messageSent() {
		SendMessage=false;
	}
	public void absorbInfo(String msg) {
		String lines[] = msg.split("%");
		for(int i=0;i<lines.length;i++) {
			String Values[]=lines[i].split(":");
			for(String a:Values) {
				System.out.println(a);
			}
			if(i==0) {
				GameState=Values[1];
				if(GameState.contains("Playing") && playGridOn==false) {
					setPlayGrid();	
				}
			}
			if(i==1) {
				String DealerHandSize=Values[2];
				String DealerHandValue=Values[4];
				String DealerHandCards=Values[6];
				drawDealer(DealerHandSize,DealerHandValue,DealerHandCards);
					
				
			}
			if(i>1) {
				if(Values[1].equals("Not Playing")) {
					
					
				}
				else{
					int PlayerId= i-1;
					String PlayerBalance=Values[2];
					String PlayerBet=Values[4];
					String PlayerHandSize=Values[6];
					String PlayerHandValue=Values[8];
					String PlayerHandCards=Values[10];
					String PlayerResult=Values[12];				
					drawPlayer(PlayerId,PlayerBalance,PlayerBet,PlayerHandSize,PlayerHandValue,PlayerHandCards);		
					if(GameState.equals("Finished")) { 
						if(pid==PlayerId)
							gameOver(PlayerResult);
					}
				}
			}
			
			
			
			
		}
		
	}
	
	public void drawDealer(String Size,String Value,String Cards) {
		removeCards(0,Integer.parseInt(Size));
		String lines[] = Cards.split(" ");
		int n=Integer.parseInt(Size);
		if(Integer.parseInt(Value)>0) {
		for (int i=0;i<n;i++) {
			 System.out.println("../Resources/Cards/"+lines[i]+".png");
			 PlayersHands[0][i]=new ImageView(new Image(getClass().getResourceAsStream("../Resources/Cards/"+lines[i]+".png"))); 
			 PlayersHands[0][i].setLayoutX(650+65*i);
			 PlayersHands[0][i].setLayoutY(40);
			 PlayersHands[0][i].setPreserveRatio(true);
			 PlayersHands[0][i].setSmooth(true);
			 PlayersHands[0][i].setFitWidth(75);
			 PlayersHands[0][i].toFront();
			 PlayersHands[0][i].setRotate(0-3*i);
		     Pane.getChildren().add(PlayersHands[0][i]);

		}
		
		PlayerCircle[0]=new Circle(700,199,22);
		PlayerCircle[0].setFill(javafx.scene.paint.Color.AQUA);
		Pane.getChildren().add(PlayerCircle[0]);
		PlayerPoints[0]= new Label(Value);
		PlayerPoints[0].setText(Value);
		PlayerPoints[0].setLayoutX(700-6);
		PlayerPoints[0].setLayoutY(199-10);
		PlayerPoints[0].setFont( new Font("Arial",17));
		PlayerPoints[0].toFront();
		Pane.getChildren().add(PlayerPoints[0]);}
		
		
	}
	
	public void drawPlayer(int PlayerId,String Balance,String Bet,String Size,String Value,String Cards) {
		if(PlayerId==pid) {
			setBetValue(Integer.parseInt(Balance),Integer.parseInt(Bet));
			
		}
		removeCards(PlayerId,Integer.parseInt(Size));
		String linesD[] = Cards.split(" ");
		int n=Integer.parseInt(Size);
		
		if(Integer.parseInt(Value)>0) {
		for (int i=0;i<n;i++) {
			 System.out.println("../Resources/Cards/"+linesD[i]+".png");
			 PlayersHands[pid][i]=new ImageView(new Image(getClass().getResourceAsStream("../Resources/Cards/"+linesD[i]+".png"))); 
			 PlayersHands[pid][i].setLayoutX(659+25*i);
			 PlayersHands[pid][i].setLayoutY(653-1*i);
			 PlayersHands[pid][i].setPreserveRatio(true);
			 PlayersHands[pid][i].setSmooth(true);
			 PlayersHands[pid][i].setFitWidth(75);
			 PlayersHands[pid][i].toFront();
		     Pane.getChildren().add(PlayersHands[pid][i]);
		}
		
		PlayerCircle[pid]=new Circle(700,623,22);
		PlayerCircle[pid].setFill(javafx.scene.paint.Color.AQUA);
		Pane.getChildren().add(PlayerCircle[pid]);
		PlayerPoints[pid]= new Label(Value);
		PlayerPoints[pid].setText(Value);
		PlayerPoints[pid].setLayoutX(700-10);
		PlayerPoints[pid].setLayoutY(623-10);
		PlayerPoints[pid].setFont( new Font("Arial",17));
		PlayerPoints[pid].toFront();
		Pane.getChildren().add(PlayerPoints[pid]);}
		
	}
}



