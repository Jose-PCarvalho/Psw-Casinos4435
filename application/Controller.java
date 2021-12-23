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
 Image myImage = new Image(getClass().getResourceAsStream("../Resources/Cards/heartsAce.png"));
 Image myImage2 = new Image(getClass().getResourceAsStream("../Resources/Cards/clubsAce.png"));
 Image confirmImage = new Image(getClass().getResourceAsStream("../Resources/GeneralAssets/Confirm.png"));
 Circle PlayerCircle[] = new Circle[8];
 Label PlayerPoints[] = new Label[8];
 ImageView FinalScreen;
 Game g;
 int pid=1;
 //ArrayList<ImageView> HandPlayer1=new ArrayList<ImageView>();
 //ArrayList < ArrayList<ImageView>> PlayersHand = new ArrayList <ArrayList<ImageView> >(7);
 
 ImageView[][] PlayersHands= new ImageView[8][10];
 ImageView[] DealerHand= new ImageView[10];
; 
 
 
@FXML 
public void BetEntered(MouseEvent e) {
	if(g.getGameState().equals("Betting")) {
	 Node clickedNode = e.getPickResult().getIntersectedNode();
	    if (clickedNode != ChipGrid) {
	        if(currentBet==0) {
	        	ConfirmButton.setImage(confirmImage);
	        	confirmFlag=false;
	        }
	        Integer colIndex = GridPane.getColumnIndex(clickedNode);
	       
	        currentBet=BetValues[colIndex];
	        g.gamelogic("Bet Entered" + currentBet);
	        BetGUI.setText(Integer.toString(g.P.getBet()));
	        WalletGUI.setText(Integer.toString(g.P.getWallet()));
	        
	        
	        
	    }
	}
 }

public void BetConfirmed() {
	if(g.getGameState().equals("Betting") && currentBet>0) {
		ConfirmButton.setImage(null);
		g.gamelogic("Bet Confirmed");
		currentBet=0;
		setPlayGrid();
		DrawCards();
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
	 g=new Game();
	 setBetValue(g.P.getWallet(),g.P.getBet());
	
	 
	 
 
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
	 }
	
 }
private void removePlayGrid() {
	PlayGrid.getChildren().remove(0);
	PlayGrid.getChildren().remove(0);
	PlayGrid.getChildren().remove(0);
}
 

public void Play(MouseEvent e) {
	if(g.getGameState().equals("Playing")) {
	 Node clickedNode = e.getPickResult().getIntersectedNode();
	    if (clickedNode != PlayGrid) {
	    	Integer colIndex = GridPane.getColumnIndex(clickedNode);
	    	if(colIndex==0) {
	    		g.gamelogic("Play: Double");
	    		while(g.gamelogic("Dealer Draw")) {
	    			DrawCards();
	    		}
	    	}
	    	else if(colIndex==1) {
	    		g.gamelogic("Play: Hit");
	    	}
	    	else if(colIndex==2) {
	    		g.gamelogic("Play: Stand");
	    		while(g.gamelogic("Dealer Draw")) {
	    			DrawCards();
	    		}
	    	}
	    	DrawCards();
	        if(g.getGameIsPlaying()==false) {
	        	g.payBet();
	        	DrawCards();
	        	gameOver();
	        }
	        
	    }
	        
	    }

}

public void gameOver() {
	removePlayGrid();
	int who=g.whoWon();
	 String[] Screens= { "../Resources/GeneralAssets/LosingScreen.png","../Resources/GeneralAssets/WinningScreen.png","../Resources/GeneralAssets/DrawScreen.png" };
	 FinalScreen= new ImageView(new Image(getClass().getResourceAsStream(Screens[who])));
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
	
	removeCards();
	
	g.newGame();
}

public void removeCards() {
int n=g.P.PlayerHand[0].getHandsize();
	
	System.out.println(n);
	for (int i=0;i<n;i++) {
		
	     Pane.getChildren().remove(PlayersHands[pid][i]);
	}
	
	n=g.D.DealerHand.getHandsize();
	
	for (int i=0;i<n;i++) {
		 
	     Pane.getChildren().remove(PlayersHands[0][i]);
	}
	Pane.getChildren().remove(PlayerPoints[pid]);
	Pane.getChildren().remove(PlayerPoints[0]);
	Pane.getChildren().remove(PlayerCircle[pid]);
	Pane.getChildren().remove(PlayerCircle[0]);
}

public void DrawCards() {
	
	String lines[] = g.P.PlayerHand[0].toString().split("\\r?\\n");
	int n=g.P.PlayerHand[0].getHandsize();
	removeCards();
	 
	for (int i=0;i<n;i++) {
		System.out.println("../Resources/Cards/"+lines[i]+".png");
		 PlayersHands[pid][i]=new ImageView(new Image(getClass().getResourceAsStream("../Resources/Cards/"+lines[i]+".png"))); 
		 PlayersHands[pid][i].setLayoutX(650+25*i);
		 PlayersHands[pid][i].setLayoutY(653-1*i);
		 PlayersHands[pid][i].setPreserveRatio(true);
		 PlayersHands[pid][i].setSmooth(true);
		 PlayersHands[pid][i].setFitWidth(75);
		 PlayersHands[pid][i].toFront();
		 PlayersHands[pid][i].setRotate(0-3*i);
	     Pane.getChildren().add(PlayersHands[pid][i]);
	}
	PlayerCircle[pid]=new Circle(700,623,22);
	PlayerCircle[pid].setFill(javafx.scene.paint.Color.AQUA);
	Pane.getChildren().add(PlayerCircle[pid]);
	PlayerPoints[pid]= new Label(Integer.toString(g.P.PlayerHand[0].getHandValue()));
	PlayerPoints[pid].setText(Integer.toString(g.P.PlayerHand[0].getHandValue()));
	PlayerPoints[pid].setLayoutX(700-10);
	PlayerPoints[pid].setLayoutY(623-10);
	PlayerPoints[pid].setFont( new Font("Arial",17));
	PlayerPoints[pid].toFront();
	System.out.println(Integer.toString(g.P.PlayerHand[0].getHandValue()));
	Pane.getChildren().add(PlayerPoints[pid]);
	
	String linesD[] = g.D.DealerHand.toString().split("\\r?\\n");
	n=g.D.DealerHand.getHandsize();
	
	for (int i=0;i<n;i++) {
		 System.out.println("../Resources/Cards/"+linesD[i]+".png");
		 PlayersHands[0][i]=new ImageView(new Image(getClass().getResourceAsStream("../Resources/Cards/"+linesD[i]+".png"))); 
		 PlayersHands[0][i].setLayoutX(659+65*i);
		 PlayersHands[0][i].setLayoutY(40);
		 PlayersHands[0][i].setPreserveRatio(true);
		 PlayersHands[0][i].setSmooth(true);
		 PlayersHands[0][i].setFitWidth(75);
		 PlayersHands[0][i].toFront();
		 //PlayersHands[0][i].setRotate(-180);
	     Pane.getChildren().add(PlayersHands[0][i]);
	}
	
	PlayerCircle[0]=new Circle(700,199,22);
	PlayerCircle[0].setFill(javafx.scene.paint.Color.AQUA);
	Pane.getChildren().add(PlayerCircle[0]);
	PlayerPoints[0]= new Label(Integer.toString(g.D.DealerHand.getHandValue()));
	PlayerPoints[0].setText(Integer.toString(g.D.DealerHand.getHandValue()));
	PlayerPoints[0].setLayoutX(700-6);
	PlayerPoints[0].setLayoutY(199-10);
	PlayerPoints[0].setFont( new Font("Arial",17));
	PlayerPoints[0].toFront();
	Pane.getChildren().add(PlayerPoints[0]);
	setBetValue();
	
}
 
}