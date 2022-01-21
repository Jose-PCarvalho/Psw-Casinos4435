package application;



import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URISyntaxException;
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

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
import javafx.scene.Cursor;


public class AfterLoginController {

	Button TableButton[]= new Button[6];
	Button XButton[]= new Button[6];
	int[] ButtonX= {0,118,630,1138,387,888};
	int[] ButtonY= {0,246,242,242,572,572};
	int[] MenuX= {0,118,626,1123,387,888};
	int[] MenuY= {0,184,184,184,541,541};
	Label[][]  PlayerNames= new Label[6][8];
	Button[][] kickButton= new Button[6][8];
	Circle[][] PlayerStatus= new Circle[6][8];
	GridPane[] TableMenuGrid= new GridPane[6];
	Button[] EnterButton= new Button[6];
	int[] colSize= {278,28,60};
	TextField[] changeDealer= new TextField[6];
	boolean MenuFlag=false;
	boolean accMenuFlag=false;
	boolean operationFlag=false;
	int operation;
	int ParamChange;
	@FXML Label ParamLabel1;
	@FXML AnchorPane Pane;
	@FXML Button MenuButton;
	@FXML JFXButton HelpBut;
	@FXML JFXToggleButton Music;
	@FXML JFXSlider slideBut;
	@FXML AnchorPane Menu;
	@FXML Label  NameLabel;
	@FXML Label usernameLabel;
	@FXML Label passwordLabel;
	@FXML Label balanceLabel;
	@FXML AnchorPane accMenu;
	@FXML Button confirmButton;
	@FXML Button pictureButton;
	@FXML Button withdrawButton;
	@FXML Button depositButton;
	@FXML Button closeAcc;
	@FXML Button MyAccBut;
	@FXML TextField depositTF;
	@FXML ImageView ProfileImage;
	@FXML AnchorPane HelpMenu;
	@FXML Button closeHelp;
	@FXML TextFlow HelpText;
	@FXML AnchorPane changeParam;
	@FXML AnchorPane delete;
	@FXML Label ParamLabel;
	@FXML PasswordField Pass;
	@FXML PasswordField PassConfirm;
	@FXML TextField Param;
	@FXML AnchorPane NewComer;
	@FXML PasswordField deletePass;
	@FXML Label PassMatch;
	@FXML Button yesbut;
	@FXML Button nobut;
	@FXML Button AccSucc;
	@FXML Label DeleteQuery;
	@FXML AnchorPane serversAreDown;
	@FXML AnchorPane fullTable;
	
	//private Media media;
	private AudioClip mediaPlayer;
	/*private File directory;
	private File[] files;*/
	public static int songNumber;
	//private ArrayList<File> songs;
	@FXML AnchorPane loggedInAlready;
	
	AnchorPane[] TableMenu=new AnchorPane[6];
	 private Stage stage;
	 private Scene scene;
	 private Parent root;
	 private File filePath;
	 private boolean SendMessage;
	 private String message;
	 static Account user;
	 Label[] DealerLabel=  new Label[6];
	 Label[] NrPlayer= new Label[6];
	 int[] labelPosX= {0,150,659,1164+4,430-4,925-4};
	 int[] labelPosY= {0,257,257,257,590,590};
	private Socket socket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;

	
	public void initialize() throws URISyntaxException {
		
		/*songs = new ArrayList<File>();
		directory = new File("music");
		files = directory.listFiles();
		if(files != null) {
			for(File file : files) {
				songs.add(file);
				System.out.println(file);
			}
		}*/
		
		if(songNumber == 0) {
			Media media = new Media(getClass().getResource("/music/01. key plus words.mp3").toURI().toString());
			mediaPlayer = new AudioClip(media.getSource());
		}else if(songNumber == 1) {
			Media media = new Media(getClass().getResource("/music/01 Tank!.mp3").toURI().toString());
			mediaPlayer = new AudioClip(media.getSource());
		}else if(songNumber == 2) {
			Media media = new Media(getClass().getResource("/music/music.mp3").toURI().toString());
			mediaPlayer = new AudioClip(media.getSource());
		}else {
			Media media = new Media(getClass().getResource("/music/yt1s.com - Bleach TYBW Number One PV Remix.mp3.mp3").toURI().toString());
			mediaPlayer = new AudioClip(media.getSource());
		}
		
		NewComer.setVisible(user.newComer);
		NameLabel.setText("Profile Name : " + user.name);
    	usernameLabel.setText("Username : " + user.username);
    	passwordLabel.setText("Password : " + user.password.replaceAll("(?s).", "*"));
        balanceLabel.setText("Current Balance : " + user.money);
        setTableButtons();
		setTableMenu();
		setXButtons();
		setMenuGrids();
		setPlayers();
		setLabels();
		Menu.setVisible(false);
		accMenu.setVisible(false);
		confirmButton.setVisible(false);
		depositTF.setVisible(false);
		slideBut.setVisible(false);
		delete.setVisible(false);
		changeParam.setVisible(false);
		Pass.setVisible(false);
		PassConfirm.setVisible(false);
		Param.setVisible(false);
		NewComer.toFront();
		PassMatch.setVisible(false);
		AccSucc.setVisible(false);
		DeleteQuery.setText("Are you sure?");
		HelpMenu.setVisible(false);
		Text text1=new Text("What is Casinos4435?\r\n");
		text1.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");
		Text text2 = new Text("Casinos4435 is an online gambling platform that strives to give the best online casino experience. So we present you the BlackJack Game.\n");
		text2.setStyle("-fx-font-size: 18px;");
		Text text3 = new Text("\nHow to play BlackJack?\n");
		text3.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
		Text text4 = new Text("The goal is to beat the dealer's hand without going over 21.\r\n"
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
		Text text7 = new Text("How to deposit money?\r\n"
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
		
        
		
    	boolean alreadyLogged=true;
		try {
			alreadyLogged=user.setLogin();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
			if(!alreadyLogged) {
				loggedInAlready.setVisible(true);
				
			}
		
			else {
				try {
		    		socket = new Socket("localhost", 1234);
		    		bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		    		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    		
		    	} catch (UnknownHostException e1) {
		    		serversAreDown.setVisible(true);
		    		e1.printStackTrace();
		    	} catch (IOException e1) {
		    		serversAreDown.setVisible(true);
		    		e1.printStackTrace();
		    	}
    	
    	setMessage("LobbyInfoRequest%"+user.username);
    	messageRequest();
    	
    	
    	new Thread(() -> {
    	    try{
    	        while(socket.isConnected() && !socket.isClosed()){
    	            String messageFromServer= bufferedReader.readLine();

    	            System.out.println("I received this message "+messageFromServer);

    	            Platform.runLater( () -> {
    	            	if(messageFromServer.contains("Lobby Info")) {
    	            		updateLobby(messageFromServer);
    	            		
    	            	}
    	            	

    	            });


    	        }
    	    }
    	    catch(IOException e){
    	    	e.printStackTrace();
    	    	serversAreDown.setVisible(true);
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
    	    				serversAreDown.setVisible(true);
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
			}
    	
    	
    	Platform.runLater( () -> {
    		
    		Stage stage=(Stage) Pane.getScene().getWindow();
    		stage.setOnCloseRequest(event -> {
    		    logOut();
    		    
    		});
    		
    		
    	});

    	
    	
	}
	
	
	private void setTableButtons() {
		for (int i=1;i<6;i++) {
			TableButton[i]=new Button();
			TableButton[i].setLayoutX(ButtonX[i]);
			TableButton[i].setLayoutY(ButtonY[i]);
			TableButton[i].setPrefWidth(377);
			TableButton[i].setPrefHeight(210);
			Pane.getChildren().add(TableButton[i]);
			TableButton[i].setOpacity(0.0);
			TableButton[i].setCursor(Cursor.HAND);
			
			
			final Button myButton =TableButton[i];
			final int index=i;
			myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{  
				TableMenu[index].setVisible(true);
                myButton.setVisible(false);
                DealerLabel[index].setVisible(false);
				
				
			});
	       /* myButton.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent event) {
	                TableMenu[index].setVisible(true);
	                myButton.setVisible(false);
	            }
	        });*/
			
		}


		
	}
	
	private void setTableMenu() {
		for(int i=1;i<6;i++) {
		TableMenu[i]= new AnchorPane();
		TableMenu[i].setLayoutX(MenuX[i]);
		TableMenu[i].setLayoutY(MenuY[i]);
		TableMenu[i].setPrefWidth(424);
		TableMenu[i].setPrefHeight(340);
		TableMenu[i].setOpacity(1);
		TableMenu[i].setStyle("-fx-background-color : white;"+"-fx-background-radius : 15;");
		TableMenu[i].applyCss();
		Pane.getChildren().add(TableMenu[i]);
		TableMenu[i].setVisible(false);
		
		}
		
		
		
		
		
		
	}
	
	private void setXButtons() {
		for (int i=1;i<6;i++) {
		XButton[i]=new Button("X");
		XButton[i].setFont(Font.font("System", 15));
		XButton[i].setTextFill(Color.BLACK);
		XButton[i].setLayoutX(388);
		XButton[i].setLayoutY(14);
		XButton[i].setPrefWidth(16);
		XButton[i].setPrefHeight(24);
		XButton[i].setStyle("-fx-background-color : grey;"+"-fx-background-radius : 20;");
		XButton[i].setCursor(Cursor.HAND);
		XButton[i].applyCss();
		TableMenu[i].getChildren().add(XButton[i]);
		
		
		final Button myButton =XButton[i];
		final int index=i;
		
		myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{ 
			TableMenu[index].setVisible(false);
            TableButton[index].setVisible(true);
            DealerLabel[index].setVisible(true);
		});

	}
		

	}
	
	private void setLabels() {
		for(int i=1;i<6;i++) {
			DealerLabel[i]=new Label("Teste");
			DealerLabel[i].setLayoutX(labelPosX[i]);
			DealerLabel[i].setLayoutY(labelPosY[i]);
			DealerLabel[i].setFont(new Font("System", 20));
			DealerLabel[i].setPrefWidth(318);
			DealerLabel[i].setPrefHeight(30);
			DealerLabel[i].setAlignment(Pos.CENTER);
			DealerLabel[i].setTextFill(Color.WHITE);
			Pane.getChildren().add(DealerLabel[i]);
			NrPlayer[i]=new Label("0");
			NrPlayer[i].setLayoutX(labelPosX[i]);
			NrPlayer[i].setLayoutY(labelPosY[i]+138);
			NrPlayer[i].setFont(new Font("System", 20));
			NrPlayer[i].setPrefWidth(318);
			NrPlayer[i].setPrefHeight(30);
			NrPlayer[i].setAlignment(Pos.CENTER);
			NrPlayer[i].setTextFill(Color.WHITE);
			Pane.getChildren().add(NrPlayer[i]);
			
		}
		
	}
	private void setPlayers() {
		for(int i=1;i<6;i++) {
			EnterButton[i]=new Button("Enter");
			EnterButton[i].setPrefWidth(68);
			EnterButton[i].setPrefHeight(14);
			EnterButton[i].setStyle("-fx-background-color : green;"+"-fx-background-radius : 20;" +"-fx-text-fill: white;" +" -fx-font-size: 11pt ;");
			EnterButton[i].setLayoutX(180);
			EnterButton[i].setLayoutY(300);
			EnterButton[i].setCursor(Cursor.HAND);
			TableMenu[i].getChildren().add(EnterButton[i]);
			final Button myButton1=EnterButton[i];
			final int t=i;

			
			
	        myButton1.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent event) {
	        		if(Integer.parseInt(NrPlayer[t].getText())<7) {
		        		closeSocket();
						Controller.setAccount(user, t);
						mediaPlayer.stop();
						try {
							changeScene(event,"/Resources/Main.fxml");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
	        		else {
	        			fullTable.setVisible(true);
	        		}
					
	        	}
	        	
	        });
			
			for(int j=0;j<8;j++) {
				PlayerNames[i][j]=new Label();
				PlayerNames[i][j].setText("Player"+j+": "+"Julia Pinheiro");
				PlayerNames[i][j].setFont(Font.font("Verdana", 20));
				PlayerNames[i][j].setVisible(true);
				TableMenuGrid[i].add(PlayerNames[i][j], 0, j);
				if(j>0) {
					PlayerStatus[i][j]=new Circle();
					PlayerStatus[i][j].setRadius(11);
					PlayerStatus[i][j].setFill(javafx.scene.paint.Color.LIGHTGREEN);
					TableMenuGrid[i].add(PlayerStatus[i][j], 1, j);
				}
				if(user.admin) {
					if(j==0)
						kickButton[i][j]=new Button("Change");
					else {
						kickButton[i][j]=new Button("Kick");
					}
				
					kickButton[i][j].setPrefWidth(58);
					kickButton[i][j].setPrefHeight(14);
					kickButton[i][j].setStyle("-fx-background-color : grey;"+"-fx-background-radius : 20;");
					kickButton[i][j].setCursor(Cursor.HAND);
					TableMenuGrid[i].add(kickButton[i][j], 2, j);
					final Button myButton=kickButton[i][j];
					final int player=j;
					final int table=i;
					
					
			        myButton.setOnAction(new EventHandler<ActionEvent>() {
			            public void handle(ActionEvent event) {
			            	
			            	if(player>0) {
			            		setMessage("KickRequest%"+table+"%"+player);
			                	messageRequest();
			           
			          
			            	}
			            	else {
			            		if(changeDealer[table]!=null && !changeDealer[table].getText().equals("")) {

	            		        	TableMenuGrid[table].getChildren().remove(changeDealer[table]);
	            		        	TableMenuGrid[table].add(PlayerNames[table][player], 0, 0);
	            		        	setMessage("DealerRequest%"+ changeDealer[table].getText()+"%"+table+"%");
				                	messageRequest();
	            		        	changeDealer[table].clear();
	            		        	return;
			            		}
			            		changeDealer[table]=new TextField();
			            		changeDealer[table].setText(PlayerNames[table][player].getText().split(":")[1]);
			            		TableMenuGrid[table].getChildren().remove(PlayerNames[table][player]);
			            		TableMenuGrid[table].add(changeDealer[table], 0, 0);
			            		changeDealer[table].setOnKeyPressed(new EventHandler<KeyEvent>() {
			            		    @Override
			            		    public void handle(KeyEvent ke) {
			            		        if (ke.getCode().equals(KeyCode.ENTER)) {
			            		        	//PlayerNames[table][player].setText("Dealer: "+ changeDealer[table].getText());
			            		        	setMessage("DealerRequest%"+ changeDealer[table].getText()+"%"+table+"%");
						                	messageRequest();
			            		        	TableMenuGrid[table].getChildren().remove(changeDealer[table]);
			            		        	TableMenuGrid[table].add(PlayerNames[table][player], 0, 0);
			            		        	changeDealer[table].clear();
			            		            
			            		        }
			            		    }
			            		});
			            		
			            	}
			            		
			            	
			            }
			            
			        });
			        
			        
			        
			        
					kickButton[i][j].setPrefWidth(58);
					kickButton[i][j].setPrefHeight(14);
					kickButton[i][j].setStyle("-fx-background-color : grey;"+"-fx-background-radius : 20;");
					kickButton[i][j].setCursor(Cursor.HAND);
			}
				

				
				
			}
		}
	}
	
	
	private void setMenuGrids() {
		for(int i=1;i<6;i++) {
			TableMenuGrid[i]= new GridPane();
			TableMenuGrid[i].setLayoutX(15);
			TableMenuGrid[i].setLayoutY(45);
			TableMenuGrid[i].setHgap(10);
			final int numCols = 3 ;
	        final int numRows = 8 ;
	        for(int j=0;j<numCols;j++) {
	        	ColumnConstraints colConst = new ColumnConstraints(colSize[j]);
	            TableMenuGrid[i].getColumnConstraints().add(colConst);
	        }
	        for(int j=0;j<numRows;j++) {
	        	RowConstraints rowConst = new RowConstraints(33);
	            TableMenuGrid[i].getRowConstraints().add(rowConst);
	        }
	        TableMenu[i].getChildren().add(TableMenuGrid[i]);
			
			
			
		}
	}
	@FXML
	private void MenuPressed() {
		if(MenuFlag==true) {
			Menu.setVisible(false);
			MenuFlag=false;
		}
		else if(MenuFlag==false) {
			Menu.setVisible(true);
			Menu.toFront();
			MenuFlag=true;
		}
		
	}
	@FXML
	private void openAccMenu() {
		accMenuFlag=true;
		accMenu.setVisible(accMenuFlag);
		accMenu.toFront();
		disableTableButtons();
		
	}
	private void disableTableButtons() {
		for (int i=1;i<6;i++) {
			TableButton[i].setVisible(false);
		}
	}
	private void enableTableButtons() {
		for (int i=1;i<6;i++) {
			TableButton[i].setVisible(true);
		}
	}
	@FXML
	private void closeAccMenu() {
		accMenuFlag=false;
		accMenu.setVisible(accMenuFlag);
		enableTableButtons();
	
		
	}
	
	@FXML
	private void openHelp() {
		HelpMenu.setVisible(true);
		HelpMenu.toFront();
		disableTableButtons();
	}
	@FXML
	private void closeHelp() {
		HelpMenu.setVisible(false);
		enableTableButtons();
	}
	
	@FXML
	private void depositMoney() {
		if(operationFlag==false || operation==2) {
		depositTF.setVisible(true);
		confirmButton.setVisible(true);
		operation=1;
		operationFlag=true;
		slideBut.setVisible(false);}
		else{
			depositTF.setVisible(false);
			confirmButton.setVisible(false);
			operationFlag=false;
		}
	}
	@FXML
	private void withdraw() {
		if(operationFlag==false || operation==1) {
		slideBut.setVisible(true);
		confirmButton.setVisible(true);
		slideBut.setMax(user.money);
		operation=2;
		operationFlag=true;
		depositTF.setVisible(false);}
		else{
			slideBut.setVisible(false);
			confirmButton.setVisible(false);
			operationFlag=false;
		}
	}
	
	@FXML
	private void closeNewComer() {
		NewComer.setVisible(false);
		try {
			user.notNewPlayer();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void changeParametersPName() {
		changeParam.setVisible(true);
		changeParam.toFront();
		ParamLabel.setText("Write New Profile Name: ");
		Pass.setVisible(false);
		PassConfirm.setVisible(false);
		Param.setVisible(true);
		ParamChange = 1;
		
	}
	
	@FXML
	private void changeParametersUName() {
		changeParam.setVisible(true);
		changeParam.toFront();
		ParamLabel.setText("Write New Username: ");
		Pass.setVisible(false);
		PassConfirm.setVisible(false);
		Param.setVisible(true);
		ParamChange = 2;
	}
	
	@FXML
	private void changeParametersPass() {
		changeParam.setVisible(true);
		changeParam.toFront();
		ParamLabel.setText("");
		Param.setVisible(false);
		Pass.setVisible(true);
		PassConfirm.setVisible(true);
		ParamLabel1.setText("Write New Password: ");
		ParamLabel1.setVisible(true);
		ParamChange = 3;
	}
	
	@FXML
	private void deleteAcc() {
		delete.setVisible(true);
		delete.toFront();
	}
	
	@FXML
	private void deleteConfirm() throws SQLException {
		if(deletePass.getText().equals(user.password)){
			PassMatch.setVisible(false);
			deletePass.setVisible(false);
			AccSucc.setVisible(true);
			yesbut.setVisible(false);
			nobut.setVisible(false);
			user.DeleteAccount();
			DeleteQuery.setText("Account successfully deleted.");
			
		}
		else {
			PassMatch.setVisible(true);
			AccSucc.setVisible(false);
			deletePass.setVisible(true);
			yesbut.setVisible(true);
			nobut.setVisible(true);
		}
	}
	
	@FXML
	private void closeParamPopUp() {
		changeParam.setVisible(false);
		Param.setText("");
		ParamLabel1.setVisible(false);
		Pass.clear();
		PassConfirm.clear();
	}
	
	@FXML
	private void closeDelete() {
		delete.setVisible(false);
	}
	
	@FXML
	private void changeParameters() throws SQLException {
		
		
		if(ParamChange == 1) {
			String change = Param.getText();
			user.UpdatePName(change);
			closeParamPopUp();
			NameLabel.setText("Profile Name : " + user.name);
		}
		else if(ParamChange == 2) {
			String change = Param.getText();
			if(!user.UserNameExists(change)) {
				user.UpdateUName(change);
				closeParamPopUp();
				usernameLabel.setText("Username : " + user.username);
				}
			else {
				ParamLabel.setText("That Username is already taken.\n"+" Please enter a new one: ");
			}
			
		}
		else if(ParamChange == 3) {
			String pass = Pass.getText();
			if(PassConfirm.getText().equals(pass) && !pass.equals("")) {
				user.UpdatePass(pass);
				closeParamPopUp();
				passwordLabel.setText("Password : " + user.password.replaceAll("(?s).", "*"));
			}
			else{
				ParamLabel1.setText("Passwords must match.");
			}
		}
	}
	
	@FXML
	private void confirm() {
		if(operation==1) {
			user.money=user.money+Integer.parseInt(depositTF.getText());
			try {
				user.UpdateMoney();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			balanceLabel.setText("Current Balance: " +user.money);
			depositTF.clear();
			depositTF.setVisible(false);
			confirmButton.setVisible(false);
			operationFlag=false;
		}
		
		
		if(operation==2) {
			user.money-=slideBut.getValue();
			try {
				user.UpdateMoney();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			balanceLabel.setText("Current Balance: " + user.money);
			
			slideBut.setVisible(false);
			confirmButton.setVisible(false);
			operationFlag=false;
		}
	}
	
	
	
	public void changeScene(ActionEvent event, String path) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		root= loader.load();
		stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
		scene= new Scene(root);
		stage.setScene(scene);
    }
    public void changeScene(MouseEvent event, String path) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		root= loader.load();
		stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
		scene= new Scene(root);
		stage.setScene(scene);
    }
    
    
    public void userLogOut(MouseEvent event) throws IOException{
        closeDelete();
        changeScene(event,"/Resources/login.fxml");
        mediaPlayer.stop();
    }
    
    public static void setAccount(String name, String username, String password, Date birth, int balance, boolean admin,boolean newComer, Connection connect) throws UnknownHostException, IOException{
    	user= new Account(name, username, password, birth, balance,admin, newComer,connect);
    	
    }
    
    
    @FXML
    public void chooseImage() throws IOException{
        Stage stage = (Stage) pictureButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");
        
        
        this.filePath = fileChooser.showOpenDialog(stage);
        
        try {
        	BufferedImage bufferedImage = ImageIO.read(filePath);
        	Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        	ProfileImage.setImage(image);
        	ProfileImage.setFitWidth(200.0);
        	ProfileImage.setFitHeight(150.0);
        	
        	ProfileImage.setSmooth(true);
        	ProfileImage.setCache(true);
        	
        	
        }catch (IOException e) {
        	System.err.println(e.getMessage());
        }
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
    private void NextMusic() throws URISyntaxException {
    	if(songNumber < 4 - 1) {
    		songNumber++;
    		mediaPlayer.stop();
    		
    		//mediaPlayer = new AudioClip(songs.get(songNumber).toURI().toString());
    	}
    	else {
    		songNumber = 0;
    		mediaPlayer.stop();
    		
    		//mediaPlayer = new AudioClip(songs.get(songNumber).toURI().toString());
    	}
    	
    	if(songNumber == 0) {
			Media media = new Media(getClass().getResource("/music/01. key plus words.mp3").toURI().toString());
			mediaPlayer = new AudioClip(media.getSource());
		}else if(songNumber == 1) {
			Media media = new Media(getClass().getResource("/music/01 Tank!.mp3").toURI().toString());
			mediaPlayer = new AudioClip(media.getSource());
		}else if(songNumber == 2) {
			Media media = new Media(getClass().getResource("/music/music.mp3").toURI().toString());
			mediaPlayer = new AudioClip(media.getSource());
		}else {
			Media media = new Media(getClass().getResource("/music/yt1s.com - Bleach TYBW Number One PV Remix.mp3.mp3").toURI().toString());
			mediaPlayer = new AudioClip(media.getSource());
		}
    	BackgroundMusic();
    }
    
    
    private void updateLobby(String messageFromServer) {
    	
    	String lines[] = messageFromServer.split("&");
    	for(int i=1;i<lines.length;i++) {
    		int n=-1;
    		String player[]=lines[i].split("%");
    		for(int j=0;j<player.length;j++) {
    			changePlayer(i,j,player[j]);
    			if(j==0) {
    				changeDealerLabel(i,player[j]);
    			}
    			if(!player[j].equals(" ")) {
    				n++;
    			}
    		}
    		changeNrLabel(i, n);
    	}
    	
    	
    }
    
    private void changeDealerLabel(int i, String Name) {
    	DealerLabel[i].setText(Name);
    }
    private void changeNrLabel(int i,int n) {
    	NrPlayer[i].setText(Integer.toString(n));
    }
    public boolean isReady(){
		return SendMessage;
		
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
	
	public String getMessage() {	
		return message;
	}
	
	
	public void closeSocket() {
		 System.out.println("Stage is closing AfterLogin");
	     try {
	     	try {
	     	if(bufferedWriter!=null) {
	     		bufferedWriter.write("Shutdown " +0);
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
	
	public void logOut() {
		 System.out.println("Stage is closing AfterLogin");
	     try {
	     	try {
	     	if(bufferedWriter!=null) {
	     		bufferedWriter.write("Logout" + user.username);
				bufferedWriter.newLine();
				bufferedWriter.flush();
	     		bufferedWriter.write("Shutdown"+"%"+"Logout"+"%"+user.username);
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
	
	public void signOut(MouseEvent e) throws IOException {
		logOut();
		changeScene(e,"/Resources/login.fxml");
	}
	
	public void changePlayer(int table, int player, String Name) {
		if(player>0) {
			PlayerNames[table][player].setText("Player"+player+": " +Name);
			if(!Name.equals(" ")) {
				PlayerStatus[table][player].setFill(javafx.scene.paint.Color.LIGHTGREEN);
			}
			else {
				PlayerStatus[table][player].setFill(javafx.scene.paint.Color.GRAY);
			}
				
		}
		else {
			PlayerNames[table][player].setText("Dealer : "+ Name);
		}
	}
	@FXML
	public void closeLogged(MouseEvent event) {
		 try {
			changeScene(event,"/Resources/login.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	@FXML
	public void serversClosed(MouseEvent event) {
		 try {
			 closeSocket();
			changeScene(event,"/Resources/login.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	@FXML
	public void tableFullConfirm() {
		fullTable.setVisible(false);
	}
	
	
	
	
}
