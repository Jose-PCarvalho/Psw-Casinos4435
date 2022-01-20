package application;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;

import BlackJack.dkeep.Game;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
 GridPane PlayGrid;
 @FXML 
 AnchorPane Pane;
@FXML 
Button MenuButton;
@FXML 
JFXButton HelpBut;
@FXML 
JFXToggleButton Music;
@FXML 
AnchorPane Menu;
@FXML 
Label  NameLabel;
@FXML 
Label usernameLabel;
@FXML 
Label passwordLabel;
@FXML 
Label balanceLabel;
@FXML 
AnchorPane accMenu;
@FXML 
Button closeAcc;
@FXML 
Button MyAccBut;
@FXML 
ImageView ProfileImage;
@FXML Button insuranceButton;
@FXML AnchorPane HelpMenu;
@FXML Button closeHelp;
@FXML TextFlow HelpText;
@FXML Button chatBut; 
@FXML AnchorPane Chat;
@FXML TextFlow ChatText;
@FXML AnchorPane kickMSG;
@FXML AnchorPane kickMSG1;
@FXML ScrollPane chatScroll;
int lastBet=0;
private AudioClip mediaPlayer;
private File directory;
private File[] files;
//private int songNumber;
private ArrayList<File> songs;

boolean chatFlag=false;
 final int[] BetValues= {1,2,5,10,20,25,50,100,250,500,1000,2000,5000};
 int currentBet=0;
 int walletValue=10000;
 Image confirmImage = new Image(getClass().getResourceAsStream("../Resources/GeneralAssets/Confirm.png"));
 Image Join = new Image(getClass().getResourceAsStream("../Resources/GeneralAssets/Join.png"));
 Circle PlayerCircle[] = new Circle[8];
 Label PlayerPoints[] = new Label[8];
 ImageView FinalScreen;
 Game g;
 String GameState="";
 private Stage stage;
 private Scene scene;
 private Parent root;
 private boolean insuranceFlag=false;
 boolean playGridOn=false;
 boolean joined=false;
 boolean MenuFlag=false;
 boolean accMenuFlag=false;
 boolean operationFlag=false;
 int operation;
 ImageView JoinButton1= new ImageView(Join);
 ImageView JoinButton2= new ImageView(Join);
 ImageView JoinButton3= new ImageView(Join);
 ImageView JoinButton4= new ImageView(Join);
 ImageView JoinButton5= new ImageView(Join);
 ImageView JoinButton6= new ImageView(Join);
 ImageView JoinButton7= new ImageView(Join);
 Button ConfirmButton = new Button("Confirm Bet");
 Button CancelButton = new Button("Cancel Bet");
 Button AllIn = new Button("All-In");
 boolean buttonFlag=true;
 static Account user;
 boolean gameFinalized=false;
 @FXML TextField ChatInput;
 Color[] Colors = {Color.BLUE,Color.BLUE, Color.RED, Color.GREEN, Color.PURPLE, Color.BLACK,Color.YELLOW, Color.GRAY};
 
 
 ImageView[][] PlayersHands= new ImageView[8][10];
 ImageView[] DealerHand= new ImageView[10];
 int PlayerPositionX[]= {0,225,400,585,782,980,1170,1345};
 int PlayerPositionY[]= {0,590,678,738,750,734,676,588};
 int PlayerRotation[]= {0,34,22,11,0,-11,-22,-34};
 int PlayerCircleX[] = {0,305,460,628,805,988,1161,1322};
 int PlayerCircleY[] = {0,523,609,659,676,659,609,523};
 
 private boolean SendMessage=false;

 private String message="";
 @FXML Label timeL;
 @FXML Button repeatButton;

private boolean exit=false;;



private static Socket socket;

private static BufferedWriter bufferedWriter;

private static BufferedReader bufferedReader;



public static void setAccount(Account acc,int t){
 	user=acc;
 	user.table=t;
 } 
 
@FXML 
public void BetEntered(MouseEvent e) {
	if(GameState.equals("Betting")) {
		
	 Node clickedNode = e.getPickResult().getIntersectedNode();
	    if (clickedNode != ChipGrid) {
	        if(currentBet==0) {
	        	ConfirmButton.setVisible(true);
	        	CancelButton.setVisible(true);
	        }
	        Integer colIndex = GridPane.getColumnIndex(clickedNode);
	       
	        currentBet=BetValues[colIndex];
	        setMessage( "Bet Entered%" + currentBet+"%"+Integer.toString(user.pid)+"%"+user.table);
	        messageRequest();
	        
	        

	        
	        
	        
	    }
	}
 }

public void BetConfirmed() {
	if(GameState.equals("Betting") && currentBet!=0) {
		setMessage("Bet Confirmed%"+user.pid+"%"+user.table);
        messageRequest();
		currentBet=0;
		ConfirmButton.setVisible(false);
		CancelButton.setVisible(false);
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
	 kickMSG.setVisible(false);
	 songs = new ArrayList<File>();
		directory = new File("music");
		files = directory.listFiles();
		if(files != null) {
			for(File file : files) {
				songs.add(file);
				
			}
		}
		
		
		mediaPlayer = new AudioClip(songs.get(AfterLoginController.songNumber).toURI().toString());
		
		
	 populateGrid();
	 message="Player Joined%"+user.table+"%"+user.name;
	 messageRequest();
	 NameLabel.setText("Profile Name : " + user.name);
 	 usernameLabel.setText("Username : " + user.username);
 	 passwordLabel.setText("Password : " + user.password.replaceAll("(?s).", "*"));
     balanceLabel.setText("Current Balance : " + user.money);
     Menu.setVisible(false);
     accMenu.setVisible(false);
     HelpMenu.setVisible(false);
	 ConfirmButton = new Button("Confirm Bet");
	 ConfirmButton.setFont(Font.font("System", 18));
	 ConfirmButton.setTextFill(Color.WHITE);
	 ConfirmButton.setLayoutX(1450);
	 ConfirmButton.setLayoutY(730);
	 ConfirmButton.setPrefWidth(156);
	 ConfirmButton.setPrefHeight(45);
	 ConfirmButton.setVisible(false);
	 ConfirmButton.setCursor(Cursor.HAND);
	 ConfirmButton.setStyle("-fx-background-color : grey;"+"-fx-background-radius : 20;");
	 ConfirmButton.applyCss();
	 ConfirmButton.setOnMouseClicked( event -> { 
	 BetConfirmed();
	 });
	 
	 CancelButton = new Button("Cancel Bet");
	 CancelButton.setFont(Font.font("System", 18));
	 CancelButton.setTextFill(Color.WHITE);
	 CancelButton.setLayoutX(1450);
	 CancelButton.setLayoutY(665);
	 CancelButton.setPrefWidth(156);
	 CancelButton.setPrefHeight(45);
	 CancelButton.setVisible(false);
	 CancelButton.setCursor(Cursor.HAND);
	 CancelButton.setStyle("-fx-background-color : grey;"+"-fx-background-radius : 20;");
	 CancelButton.applyCss();
	 CancelButton.setOnMouseClicked(event ->{
		    setMessage( "Bet Canceled"+"%"+Integer.toString(user.pid)+"%"+user.table);
	        messageRequest();
	        CancelButton.setVisible(false);
	        ConfirmButton.setVisible(false);
	        currentBet=0;
		 
	 });
	 
	 
	 AllIn = new Button("All-In");
	 AllIn.setFont(Font.font("System", 18));
	 AllIn.setTextFill(Color.WHITE);
	 AllIn.setLayoutX(1450);
	 AllIn.setLayoutY(795);
	 AllIn.setPrefWidth(156);
	 AllIn.setPrefHeight(45);
	 AllIn.setVisible(true);
	 AllIn.setCursor(Cursor.HAND);
	 AllIn.setStyle("-fx-background-color : grey;"+"-fx-background-radius : 20;");
	 AllIn.applyCss();
	 AllIn.setOnMouseClicked( event -> { 
		 if(GameState.equals("Betting")) {
			 if(currentBet==0) {
		        	ConfirmButton.setVisible(true);
		        	CancelButton.setVisible(true);
		        }
			 currentBet=-1;
		 	setMessage( "Bet Entered%" + currentBet+"%"+Integer.toString(user.pid)+"%"+user.table);
	        messageRequest();}
	});
	 
	 Pane.getChildren().add(ConfirmButton);
	 Pane.getChildren().add(CancelButton);
	 Pane.getChildren().add(AllIn);
	 
	 Text text1=new Text("\nWhat is Casinos4435?\r\n");
		text1.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");
		Text text2 = new Text("\nCasinos4435 is an online gambling platform that strives to give the best online casino experience. So we present you the BlackJack Game.\n");
		text2.setStyle("-fx-font-size: 18px;");
		Text text3 = new Text("\nHow to play BlackJack?\n");
		text3.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
		Text text4 = new Text("\nThe goal is to beat the dealer's hand without going over 21.\r\n"
				+ "Basic Blackjack Rules:\r\n"
				+ " - Aces are worth 1 or 11, depending on whether the player or dealer is given a score higher than 21.\r\n"
				+ " - Face cards are worth 10.\r\n"
				+ " - And all others cards have their face value.\r\n"
				+ " - Each player starts with two cards, one of the dealer's cards is hidden until the end.\r\n"
				+ " - To 'Hit' is to ask for another card. To 'Stand' is to hold your score and end your turn.\r\n"
				+ " - If you go over 21 you bust, and the dealer wins regardless of the dealer's hand.\r\n"
				+ " - If you are dealt 21 from the start (Ace & 10), you got a blackjack.\r\n"
				+ " - Blackjack means you win 1.5 the amount of your bet.\r\n"
				+ " - Dealer will hit until his/her cards total 17 or higher.\r\n"
				+ " - Doubling is like a hit, only the bet is doubled and you only get one more card.\r\n"
				+ " - You can only double on the first move.\r\n"
				+ " - When you win, you receive double your bet.\r\n"
				+ " - When you lose, you lose your bet.\r\n"
				+ " - When you draw, you receive your bet.\n");
		text4.setStyle("-fx-font-size: 18px;");
		Text text5 = new Text("\nPay attencion to timers:\r\n"
				+ " - The players have 15 seconds to place their bets after a player's first bet.\r\n"
				+ " - The players have 30 seconds to complete their play.\r\n"
				+ " - The players have 10 seconds between the end of play and the start of a new game.\r\n");
		text5.setStyle("-fx-font-size: 18px;");
		Text text6 = new Text("\nFAQ\n");
		text6.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
		Text text7 = new Text("\nHow to deposit money?\r\n"
				+ "Go to: Menu > My Account > Deposit Money (Write the amount to deposit) > Confirm\r\n"
				+ "\r\n"
				+ "How to withdraw money?\r\n"
				+ "Go to: Menu > My Account > Withdraw Money (Choose the amount to withdraw) > Confirm\r\n"
				+ "\r\n"
				+ "How to delete an account?\r\n"
				+ "Go to: Menu > My Account > Delete Account > Confirm\r\n"
				+ "");
		text7.setStyle("-fx-font-size: 18px;");
		HelpText.getChildren().addAll(text1, text2,text3,text4,text5,text6,text7);

		  try {
				socket = new Socket("localhost", 1234);
				bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				  
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			  ;
			  
			  
			  


			  new Thread(() -> {
			    try{
			        while(socket.isConnected() && !socket.isClosed()){
			            String messageFromServer= bufferedReader.readLine();

			            System.out.println(messageFromServer);

			            Platform.runLater( () -> {
			            	 if(messageFromServer.contains("Chat") && this.joined==true) {
			            		this.updateChat(messageFromServer);
			            	}
			            	 else if(messageFromServer.contains("Available Spots") && this.joined==false) {
			            		this.setJoinButton(messageFromServer);
			            	}
			            	else if(messageFromServer.contains("GameState") && this.joined==true) {
			            		this.absorbInfo(messageFromServer);
			            	}
			            	
			            	

			            });


			        }
			    }
			    catch(IOException e){
			    	Platform.runLater( () -> { 
			    		try {
							if(exit==false) {
								LeaveTable();
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    		
			    		
			    	/*Stage stg=(Stage) Pane.getScene().getWindow();
			    	stg.fireEvent(
			                new WindowEvent(
			                        stg,
			                        WindowEvent.WINDOW_CLOSE_REQUEST
			                )
			        );*/
			    	});
			    	
			    	e.printStackTrace();
			    	
			    	
			    }

			  }).start();
			  
			  
			
			  new Thread(() -> {
			    while(socket.isConnected() && !socket.isClosed()){
			    	Platform.runLater( () -> {
			    		boolean ReadyFlag=this.isReady();
			    		String message=this.getMessage();
			    		
			    		
			    		if(ReadyFlag) {
			    			try {
			    			System.out.println("I just sent this message"+  message);
			    			bufferedWriter.write(message);
		     				bufferedWriter.newLine();
		     				bufferedWriter.flush();
		     				this.messageSent();
			    			}catch(IOException e) {
			    				e.printStackTrace();
			    				try {
			    					closeSocket();
									changeScene("/Resources/AfterLogin.fxml");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
			    			}
			    		}

		           });	
					try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				}

			  }).start();
			  
			  
			  
			  Platform.runLater( () -> {
		  		
		  		Stage stage=(Stage) Pane.getScene().getWindow();
		  		stage.setOnCloseRequest(event -> {
		  		    shutdown();
		  		    
		  		});
		  		
		  		
		  	});

	  
	  
 
 }

 @FXML
	private void MenuPressed() {
		if(MenuFlag==true) {
			Menu.setVisible(false);
			MenuFlag=false;
			Menu.toFront();
		}
		else if(MenuFlag==false) {
			Menu.setVisible(true);
			MenuFlag=true;
			Menu.toFront();
		}
		
	}
	@FXML
	private void openAccMenu() {
		accMenuFlag=true;
		accMenu.setVisible(accMenuFlag);
	    accMenu.toFront();
		
	}
	@FXML
	private void closeAccMenu() {
		accMenuFlag=false;
		accMenu.setVisible(accMenuFlag);
		accMenu.toFront();
		
	}
	
	@FXML
	private void openHelp() {
		HelpMenu.setVisible(true);
		HelpMenu.toFront();
		
	}
	@FXML
	private void closeHelp() {
		HelpMenu.setVisible(false);
		HelpMenu.toFront();
	}
	

	
	@FXML
	private void kickOK() throws IOException  {
		LeaveTable();
	}
	
	
	
	
    @FXML
    private void BackgroundMusic() {
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.1);
        if(Music.isSelected()){
            mediaPlayer.play();
        }
        else if(!(Music.isSelected())){
            mediaPlayer.stop();
        }
        
    }
    
    @FXML 
    private void NextMusic() {
    	if(AfterLoginController.songNumber < songs.size() - 1) {
    		AfterLoginController.songNumber++;
    		mediaPlayer.stop();
    		
    		mediaPlayer = new AudioClip(songs.get(AfterLoginController.songNumber).toURI().toString());
    		BackgroundMusic();
    	}
    	else {
    		AfterLoginController.songNumber = 0;
    		mediaPlayer.stop();
    		
    		mediaPlayer = new AudioClip(songs.get(AfterLoginController.songNumber).toURI().toString());
    		BackgroundMusic();
    	}
    	
    }
    
    public void shutdown() {
   	 System.out.println("Stage is closing");
   	 exit=true;
        try {
        	try {
        	if(bufferedWriter!=null) {
        	bufferedWriter.write("Leave Table%"+user.pid+"%"+user.table+"%"+"Logout"+"%"+user.username);
   			bufferedWriter.newLine();
   			bufferedWriter.flush();
   			}}
        	catch(IOException e) {
        		e.printStackTrace();
        	}
        	if(socket!=null)
   				socket.close();
        	if(bufferedReader!=null)
   				bufferedReader.close();
   			if(bufferedWriter!=null)
   				bufferedWriter.close();	
   			
        	
       
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
    }

 
 public static void closeSocket() {
	 System.out.println("Stage is closing");
     try {
     	try {
     	if(bufferedWriter!=null) {
     		bufferedWriter.write("Leave Table%"+user.pid+"%"+user.table);
			bufferedWriter.newLine();
			bufferedWriter.flush();}}
     	catch(IOException e) {
     		e.printStackTrace();
     	}
     	if(socket!=null)
				socket.close();
     	if(bufferedReader!=null)
				bufferedReader.close();
			if(bufferedWriter!=null)
				bufferedWriter.close();	
			
     	
    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
 }

 
 public void setJoinButton(String message) {
	 
	 int i=0;
	 removeButtons();
	   
	       
		
	 for (String val: message.split(" ")) {
         System.out.println(val +"\n");
	    if(i>1)  { 
	    	if(Integer.parseInt(val)==1) {
	    		JoinButton1=new ImageView(Join);
	    		JoinButton1.setLayoutX(PlayerPositionX[Integer.parseInt(val)]-15);
	    		JoinButton1.setLayoutY(PlayerPositionY[Integer.parseInt(val)]);
	    		JoinButton1.setPreserveRatio(true);
	    		JoinButton1.setSmooth(true);
	    		JoinButton1.setFitWidth(75);
	    		JoinButton1.toFront();
	    		JoinButton1.setCursor(Cursor.HAND);
	     		Pane.getChildren().add(JoinButton1);
	     		JoinButton1.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{		
	    			user.pid=Integer.parseInt(val);
	    			joined=true;
	    			setMessage("New Player on position:" + Integer.toString(user.pid)+"%"+user.username+"%"+Integer.toString(user.money)+"%"+user.table);
	    			messageRequest();
	    			removeButtons();
	    			event.consume();
	    		     });}
	    	if(Integer.parseInt(val)==2) {
	    		JoinButton2=new ImageView(Join);
	    		JoinButton2.setLayoutX(PlayerPositionX[Integer.parseInt(val)]-15);
	    		JoinButton2.setLayoutY(PlayerPositionY[Integer.parseInt(val)]);
	    		JoinButton2.setPreserveRatio(true);
	    		JoinButton2.setSmooth(true);
	    		JoinButton2.setFitWidth(75);
	    		JoinButton2.toFront();
	    		JoinButton2.setCursor(Cursor.HAND);
	     		Pane.getChildren().add(JoinButton2);
	     		JoinButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{		
	    			user.pid=Integer.parseInt(val);
	    			joined=true;
	    			setMessage("New Player on position:" + Integer.toString(user.pid)+"%"+user.username+"%"+Integer.toString(user.money)+"%"+user.table);
	    			messageRequest();
	    			removeButtons();
	    			event.consume();
	    		     });
	    	}
	    	if(Integer.parseInt(val)==3) {
	    		JoinButton3=new ImageView(Join);
	    		JoinButton3.setLayoutX(PlayerPositionX[Integer.parseInt(val)]-15);
	    		JoinButton3.setLayoutY(PlayerPositionY[Integer.parseInt(val)]);
	    		JoinButton3.setPreserveRatio(true);
	    		JoinButton3.setSmooth(true);
	    		JoinButton3.setFitWidth(75);
	    		JoinButton3.toFront();
	    		JoinButton3.setCursor(Cursor.HAND);
	     		Pane.getChildren().add(JoinButton3);
	     		JoinButton3.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{		
	    			user.pid=Integer.parseInt(val);
	    			joined=true;
	    			setMessage("New Player on position:" + Integer.toString(user.pid)+"%"+user.username+"%"+Integer.toString(user.money)+"%"+user.table);
	    			messageRequest();
	    			removeButtons();
	    			event.consume();
	    		     });}
	    	if(Integer.parseInt(val)==4) {
	    		JoinButton4=new ImageView(Join);
	    		JoinButton4.setLayoutX(PlayerPositionX[Integer.parseInt(val)]-15);
	    		JoinButton4.setLayoutY(PlayerPositionY[Integer.parseInt(val)]);
	    		JoinButton4.setPreserveRatio(true);
	    		JoinButton4.setSmooth(true);
	    		JoinButton4.setFitWidth(75);
	    		JoinButton4.toFront();
	    		JoinButton4.setCursor(Cursor.HAND);
	     		Pane.getChildren().add(JoinButton4);
	     		JoinButton4.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{		
	    			user.pid=Integer.parseInt(val);
	    			joined=true;
	    			setMessage("New Player on position:" + Integer.toString(user.pid)+"%"+user.username+"%"+Integer.toString(user.money)+"%"+user.table);
	    			messageRequest();
	    			removeButtons();
	    			event.consume();
	    		     });}
	    	if(Integer.parseInt(val)==5) {
	    		JoinButton5=new ImageView(Join);
	    		JoinButton5.setLayoutX(PlayerPositionX[Integer.parseInt(val)]-15);
	    		JoinButton5.setLayoutY(PlayerPositionY[Integer.parseInt(val)]);
	    		JoinButton5.setPreserveRatio(true);
	    		JoinButton5.setSmooth(true);
	    		JoinButton5.setFitWidth(75);
	    		JoinButton5.toFront();
	    		JoinButton5.setCursor(Cursor.HAND);
	     		Pane.getChildren().add(JoinButton5);
	     		JoinButton5.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{		
	    			user.pid=Integer.parseInt(val);
	    			joined=true;
	    			setMessage("New Player on position:" + Integer.toString(user.pid)+"%"+user.username+"%"+Integer.toString(user.money)+"%"+user.table);
	    			messageRequest();
	    			removeButtons();
	    			event.consume();
	    		     });}
		
	    	if(Integer.parseInt(val)==6) {
	    		JoinButton6=new ImageView(Join);
	    		JoinButton6.setLayoutX(PlayerPositionX[Integer.parseInt(val)]-15);
	    		JoinButton6.setLayoutY(PlayerPositionY[Integer.parseInt(val)]);
	    		JoinButton6.setPreserveRatio(true);
	    		JoinButton6.setSmooth(true);
	    		JoinButton6.setFitWidth(75);
	    		JoinButton6.toFront();
	    		JoinButton6.setCursor(Cursor.HAND);
	     		Pane.getChildren().add(JoinButton6);
	     		JoinButton6.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{		
	    			user.pid=Integer.parseInt(val);
	    			joined=true;
	    			setMessage("New Player on position:" + Integer.toString(user.pid)+"%"+user.username+"%"+Integer.toString(user.money)+"%"+user.table);
	    			messageRequest();
	    			removeButtons();
	    			event.consume();
	    		     });}
	    	if(Integer.parseInt(val)==7){
	    		JoinButton7=new ImageView(Join);
	    		JoinButton7.setLayoutX(PlayerPositionX[Integer.parseInt(val)]-15);
	    		JoinButton7.setLayoutY(PlayerPositionY[Integer.parseInt(val)]);
	    		JoinButton7.setPreserveRatio(true);
	    		JoinButton7.setSmooth(true);
	    		JoinButton7.setFitWidth(75);
	    		JoinButton7.toFront();
	    		JoinButton7.setCursor(Cursor.HAND);
	     		Pane.getChildren().add(JoinButton7);
	     		JoinButton7.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{		
	    			user.pid=Integer.parseInt(val);
	    			joined=true;
	    			setMessage("New Player on position:" + Integer.toString(user.pid)+"%"+user.username+"%"+Integer.toString(user.money)+"%"+user.table);
	    			messageRequest();
	    			removeButtons();
	    			event.consume();
	    		     });}
		    	
		
	    	
		
	    		    
	    }
	    i++;	
	 }
	         
 }
 
 	
 	public void removeButtons() {
 		Pane.getChildren().remove(JoinButton1);
 		Pane.getChildren().remove(JoinButton2);
 		Pane.getChildren().remove(JoinButton3);
 		Pane.getChildren().remove(JoinButton4);
 		Pane.getChildren().remove(JoinButton5);
 		Pane.getChildren().remove(JoinButton6);
 		Pane.getChildren().remove(JoinButton7);
 		
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
	insuranceButton.setVisible(false);
	playGridOn=false;
}
 

public void Play(MouseEvent e) {
	if(GameState.equals("Playing")) {
	 Node clickedNode = e.getPickResult().getIntersectedNode();
	    if (clickedNode != PlayGrid) {
	    	Integer colIndex = GridPane.getColumnIndex(clickedNode);
	    	if(colIndex==0) {
	    		
	    		 setMessage("Playing Double%" +user.pid +"%"+ user.table);
		         messageRequest();
	    		
	    	}
	    	else if(colIndex==1) {
	    		setMessage("Playing Hit%" + user.pid +"%"+ user.table);
		        messageRequest();
	    	}
	    	else if(colIndex==2) {
	    		setMessage("Playing Stand%" + user.pid + "%"+user.table);
		        messageRequest();
	    	}
	    	
	        
	    }
	        
	    }

}

public void gameOver(String Result) {
	if(playGridOn) {
	removePlayGrid();}
	if(Integer.parseInt(Result)==-1) {
		Result="0";
	}
	lastBet=Integer.parseInt(BetGUI.getText());
	if(lastBet!=0) {
		repeatButton.setVisible(true);
	}
	if(!gameFinalized) {
	if(FinalScreen!=null) {
		Pane.getChildren().remove(FinalScreen);
		FinalScreen=null;
	}
	 String[] Screens= { "../Resources/GeneralAssets/LosingScreen.png","../Resources/GeneralAssets/WinningScreen.png","../Resources/GeneralAssets/DrawScreen.png" };
	 FinalScreen= new ImageView(new Image(getClass().getResourceAsStream(Screens[Integer.parseInt(Result)])));
	 FinalScreen.setLayoutX(600);
	 FinalScreen.setLayoutY(200+120);
	 FinalScreen.setPreserveRatio(true);
	 gameFinalized=true;
	 Pane.getChildren().add(FinalScreen);
	 FinalScreen.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
	         newGame();
	         Pane.getChildren().remove(FinalScreen);
	         FinalScreen=null;
	         event.consume();
	     });
	 }

}

public void newGame() {
	
	
	for (int i=0;i<8;i++) {
		removeCards(i,10);
	}
	setMessage("New Game%"+user.table);
    messageRequest();
    insuranceFlag=false;
	
	
	
}

public void removeCards(int id, int n) {

	
	//System.out.println(n);
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
	public void messageRequest() {
		SendMessage=true;
	}
	public void messageSent() {
		SendMessage=false;
	}
	public void absorbInfo(String msg) {
		
		
		removeButtons();
			
		
		String lines[] = msg.split("%");
		for(int i=0;i<lines.length;i++) {
			String Values[]=lines[i].split(":");
			/*for(String a:Values) {
				System.out.println(a);
			}*/
			if(i==0) {
				int t=Integer.parseInt(Values[2]);
				if(t!=user.table) 
					break;
				GameState=Values[1];
				if(!GameState.equals("Finished"))
					gameFinalized=false;
				if(GameState.equals("Betting")) {
					timeL.setText(GameState+" -Time Remaing :"+Values[3]);
					}
				else if(GameState.equals("Playing")) {
					timeL.setText(GameState+" -Time Remaing :"+Values[4]);
					}
				else if(GameState.equals("Finished")) {
					timeL.setText(GameState+" -Time Remaing :"+Values[5]);
					}
				if(Values[6].equals("Insurance") && Integer.parseInt(WalletGUI.getText())>Integer.parseInt(BetGUI.getText())) {
					if(!insuranceFlag)
						insuranceButton.setVisible(true);
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
					if(i-1==user.pid) {
						if(Integer.parseInt(WalletGUI.getText())==0)
							kickMSG.setVisible(true);
						else {
							kickMSG1.setVisible(true);
						}
						
					}
					
				}
				else{
					int PlayerId= i-1;
					String PlayerBalance=Values[2];
					String PlayerBet=Values[4];
					String PlayerHandSize=Values[6];
					String PlayerHandValue=Values[8];
					String PlayerHandCards=Values[10];
					String PlayerResult=Values[12];	
					boolean Participating=Boolean.parseBoolean(Values[14]);
					boolean endedTurn=Boolean.parseBoolean(Values[16]);
					if(GameState.contains("Playing") && playGridOn==false && Participating==true && PlayerId==user.pid && !endedTurn) {
						setPlayGrid();	
					}
					else if( playGridOn==true && endedTurn && PlayerId==user.pid ) {
						removePlayGrid();
						
					}
					drawPlayer(PlayerId,PlayerBalance,PlayerBet,PlayerHandSize,PlayerHandValue,PlayerHandCards);		
					if(GameState.equals("Finished")) { 
						if(user.pid==PlayerId)
							gameOver(PlayerResult);
					}
				}
			}
			
			
			
			
		}
		
	}
	
	public void drawDealer(String Size,String Value,String Cards) {
		removeCards(0,10);
		String lines[] = Cards.split(" ");
		int n=Integer.parseInt(Size);
		if(Integer.parseInt(Value)>0) {
		for (int i=0;i<n;i++) {
			// System.out.println("../Resources/Cards/"+lines[i]+".png");
			 PlayersHands[0][i]=new ImageView(new Image(getClass().getResourceAsStream("../Resources/Cards/"+lines[i]+".png"))); 
			 PlayersHands[0][i].setLayoutX(700+65*i);
			 PlayersHands[0][i].setLayoutY(40);
			 PlayersHands[0][i].setPreserveRatio(true);
			 PlayersHands[0][i].setSmooth(true);
			 PlayersHands[0][i].setFitWidth(75);
			 PlayersHands[0][i].toFront();
			 PlayersHands[0][i].setRotate(0);
		     Pane.getChildren().add(PlayersHands[0][i]);

		}
		
		PlayerCircle[0]=new Circle(800,199,22);
		PlayerCircle[0].setFill(javafx.scene.paint.Color.AQUA);
		Pane.getChildren().add(PlayerCircle[0]);
		PlayerPoints[0]= new Label(Value);
		PlayerPoints[0].setText(Value);
		PlayerPoints[0].setLayoutX(800-6);
		PlayerPoints[0].setLayoutY(199-10);
		PlayerPoints[0].setFont( new Font("Arial",17));
		PlayerPoints[0].toFront();
		Pane.getChildren().add(PlayerPoints[0]);}
		
		
	}
	
	public void drawPlayer(int pid,String Balance,String Bet,String Size,String Value,String Cards) {
		if(user.pid==pid) {
			user.money=Integer.parseInt(Balance);
			setBetValue(user.money,Integer.parseInt(Bet));
			try {
				user.UpdateMoney();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		}
		removeCards(pid,10);
		if(!GameState.equals("Betting")) {
		String linesD[] = Cards.split(" ");
		int n=Integer.parseInt(Size);
		
		if(Integer.parseInt(Value)>0) {
			for (int i=0;i<n;i++) {
				// System.out.println("../Resources/Cards/"+linesD[i]+".png");
				if(PlayerRotation[pid] != 0) {
					 int offsetSignal=1;
					 if(pid>4) {
						 offsetSignal=-1;
					 }
					 PlayersHands[pid][i]=new ImageView(new Image(getClass().getResourceAsStream("../Resources/Cards/"+linesD[i]+".png"))); 
					 PlayersHands[pid][i].setLayoutX(PlayerPositionX[pid]+28*i-15);  //30*i
					 PlayersHands[pid][i].setLayoutY(PlayerPositionY[pid]-45+ 12*i*offsetSignal);
					 PlayersHands[pid][i].setPreserveRatio(true);
					 PlayersHands[pid][i].setSmooth(true);
					 PlayersHands[pid][i].setFitWidth(75);
					 PlayersHands[pid][i].setRotate(PlayerRotation[pid]);
					 PlayersHands[pid][i].toFront();
				     Pane.getChildren().add(PlayersHands[pid][i]);
				}
				else {
				 PlayersHands[pid][i]=new ImageView(new Image(getClass().getResourceAsStream("../Resources/Cards/"+linesD[i]+".png"))); 
				 PlayersHands[pid][i].setLayoutX(PlayerPositionX[pid]+40*i-15);  //30*i
				 PlayersHands[pid][i].setLayoutY(PlayerPositionY[pid]-45+ i);
				 PlayersHands[pid][i].setPreserveRatio(true);
				 PlayersHands[pid][i].setSmooth(true);
				 PlayersHands[pid][i].setFitWidth(75);
				 PlayersHands[pid][i].setRotate(PlayerRotation[pid]);
				 PlayersHands[pid][i].toFront();
			     Pane.getChildren().add(PlayersHands[pid][i]);
				}
			}
			
			PlayerCircle[pid]=new Circle(PlayerCircleX[pid],PlayerCircleY[pid],22);
			if(pid==user.pid) {
			PlayerCircle[pid].setFill(javafx.scene.paint.Color.LIGHTGREEN);
			}
			else {
				PlayerCircle[pid].setFill(javafx.scene.paint.Color.AQUA);
			}
			Pane.getChildren().add(PlayerCircle[pid]);
			PlayerPoints[pid]= new Label(Value);
			PlayerPoints[pid].setText(Value);
			PlayerPoints[pid].setLayoutX(PlayerCircleX[pid]-10);
			PlayerPoints[pid].setLayoutY(PlayerCircleY[pid]-10);
			PlayerPoints[pid].setFont( new Font("Arial",17));
			PlayerPoints[pid].toFront();
			Pane.getChildren().add(PlayerPoints[pid]);}
		}
	}
	
	 


	 public void changeScene(String path) throws IOException {
		 FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		 root= loader.load();
		 stage=(Stage) Pane.getScene().getWindow();
		 scene= new Scene(root);
		 stage.setScene(scene);
	 }


	 public void LeaveTable() throws IOException{
		 exit=true;
		 closeSocket();
		 changeScene("/Resources/AfterLogin.fxml");
		 mediaPlayer.stop();
	 }
	 
	
	 
	 public void clickChat() {
		 chatFlag=!chatFlag;
		 Chat.setVisible(chatFlag);
		 Chat.toFront();
		 if(chatFlag==false) {
			 chatBut.setStyle("-fx-background-color : grey;"+"-fx-background-radius : 60;");
		 }
		 else {
			 chatBut.setStyle("-fx-background-color :#ac2e2e;"+"-fx-background-radius : 60;"); 
		 }
		 

	 }
	 public void chatIn(KeyEvent ke) {
		 if (ke.getCode().equals(KeyCode.ENTER)) {
	            if(!ChatInput.getText().equals(" ")) {
	            	setMessage( "Chat%" + user.table+"%"+user.username+": "+ChatInput.getText()+"%"+user.pid);
	            	messageRequest();
	            	ChatInput.clear();
	    	      
	            
	            }
	        }
	 }
	 @FXML public void closeChat() {
		 System.out.println("CLOSE");
		 chatFlag=!chatFlag;
		 Chat.setVisible(chatFlag);
		 chatBut.setStyle("-fx-background-color : grey;"+"-fx-background-radius : 60;");
	 }
	 
	 public void updateChat(String messageFromServer) {
		
		 String lines[] = messageFromServer.split("%");
		 if(Integer.parseInt(lines[1])==user.table) {
			 
		 
		 Text t=new Text(lines[2]+"\n");
		 t.setFill(Colors[Integer.parseInt(lines[3])]);
		 t.setFont(Font.font("System", 18));
		 ChatText.getChildren().addAll(t);
		 chatScroll.setVvalue(1.0);
		 chatBut.setStyle("-fx-background-color :#ac2e2e;"+"-fx-background-radius : 60;");
		 }
		 
	 }
	 @FXML public void repeatLastBet() {
		 if(GameState.equals("Betting")) {
			 if(currentBet==0) {
		        	ConfirmButton.setVisible(true);
		        	CancelButton.setVisible(true);
		        }
			 currentBet=+lastBet;
		 	setMessage( "Bet Entered%" + currentBet+"%"+Integer.toString(user.pid)+"%"+user.table);
	        messageRequest();
		 }
	 }
	 @FXML public void insuranceRequest() {
		 insuranceButton.setVisible(false);
		 setMessage("Playing Insurance%" + user.pid +"%"+ user.table);
	     messageRequest();
	     insuranceFlag=true;
	 }
}

