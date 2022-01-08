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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;


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
	private static Connection conn;
	@FXML ImageView ProfileImage;
	
	AnchorPane[] TableMenu=new AnchorPane[6];
	 private Stage stage;
	 private Scene scene;
	 private Parent root;
	 private File filePath;
	 private AudioClip mediaPlayer;
	private boolean SendMessage;
	private String message;
	 static Account user;
	 private static Socket socket;
	 private static  BufferedReader bufferedReader;
	 private static BufferedWriter bufferedWriter;
	

	
	public void initialize() {
		NameLabel.setText("Profile Name : " + user.name);
    	usernameLabel.setText("Username : " + user.username);
    	passwordLabel.setText("Password : " + user.password.replaceAll("(?s).", "*"));
        balanceLabel.setText("Current Balance : " + user.money);
        setTableButtons();
		setTableMenu();
		setXButtons();
		setMenuGrids();
		setPlayers();
		Menu.setVisible(false);
		accMenu.setVisible(false);
		confirmButton.setVisible(false);
		depositTF.setVisible(false);
		slideBut.setVisible(false);
        
        
        try {
    		socket = new Socket("localhost", 1234);
    		bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    		  
    	} catch (UnknownHostException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	} catch (IOException e1) {
    		
    		e1.printStackTrace();
    	};
    	
    	
    	setMessage("LobbyInfoRequest");
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
    	    			}
    	    		}

               });	
    			try {
    			Thread.sleep(100);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		}

    	  }).start();
    	
    	
    	Platform.runLater( () -> {
    		
    		Stage stage=(Stage) Pane.getScene().getWindow();
    		stage.setOnCloseRequest(event -> {
    		    closeSocket();
    		    
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
			
			
			final Button myButton =TableButton[i];
			final int index=i;
			myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{  
				TableMenu[index].setVisible(true);
                myButton.setVisible(false);
				
				
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
		TableMenu[i].setOpacity(0.9);
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
		XButton[i].applyCss();
		TableMenu[i].getChildren().add(XButton[i]);
		
		
		final Button myButton =XButton[i];
		final int index=i;
		
		myButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{ 
			TableMenu[index].setVisible(false);
            TableButton[index].setVisible(true);	
		});
        /*myButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                TableMenu[index].setVisible(false);
                TableButton[index].setVisible(true);
            }
        });*/
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
			TableMenu[i].getChildren().add(EnterButton[i]);
			final Button myButton1=EnterButton[i];
			final int t=i;

			
			
	        myButton1.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent event) {
	        		Controller.setAccount(user, conn, t);
	        		closeSocket();
	        		try {
						changeScene(event,"../resources/Main.fxml");
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        	
	        });
			
			for(int j=0;j<8;j++) {
				PlayerNames[i][j]=new Label();
				PlayerNames[i][j].setText("Player"+j+": "+"Julia Pinheiro");
				PlayerNames[i][j].setFont(Font.font("Verdana", 20));
				//PlayerNames[i][j].setStyle("-fx-font-weight: bold;");
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
					TableMenuGrid[i].add(kickButton[i][j], 2, j);
					final Button myButton=kickButton[i][j];
					final int player=j;
					final int table=i;
					
					
			        myButton.setOnAction(new EventHandler<ActionEvent>() {
			            public void handle(ActionEvent event) {
			            	
			            	if(player>0) {
			            		setMessage("KickRequest%"+table+"%"+player);
			                	messageRequest();
			           
			            		//PlayerNames[table][player].setText("Player"+player+":");
			            		//PlayerStatus[table][player].setFill(javafx.scene.paint.Color.GRAY);
			          
			            	}
			            	else {
			            		if(changeDealer[table]!=null && !changeDealer[table].getText().equals("")) {
			            			//PlayerNames[table][player].setText("Dealer: "+ changeDealer[table].getText());
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
			}
				

				
				
			}
		}
	}
	
	
	private void setMenuGrids() {
		for(int i=1;i<6;i++) {
			TableMenuGrid[i]= new GridPane();
			TableMenuGrid[i].setLayoutX(15);
			TableMenuGrid[i].setLayoutY(45);
			//TableMenuGrid[i].setGridLinesVisible(true);
			TableMenuGrid[i].setHgap(10);
			//TableMenuGrid[i].setVgap(3);
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
			MenuFlag=true;
		}
		
	}
	@FXML
	private void openAccMenu() {
		accMenuFlag=true;
		accMenu.setVisible(accMenuFlag);
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
	private void depositMoney() {
		if(operationFlag==false) {
		depositTF.setVisible(true);
		confirmButton.setVisible(true);
		operation=1;
		operationFlag=true;}
	}
	@FXML
	private void withdraw() {
		if(operationFlag==false) {
		slideBut.setVisible(true);
		confirmButton.setVisible(true);
		slideBut.setMax(user.money);
		operation=2;
		operationFlag=true;}
	}
	
	
	@FXML
	private void confirm() {
		if(operation==1) {
			user.money=user.money+Integer.parseInt(depositTF.getText());
			try {
				UpdateMoney(user.username,user.money);
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
				UpdateMoney(user.username,user.money);
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
    
    
    public void userLogOut(ActionEvent event) throws IOException{
        
        changeScene(event,"../Resources/login.fxml");
    }
    
    public static void setAccount(String name, String username, String password, Date birth, int balance, boolean admin, Connection connect){
    	user= new Account(name, username, password, birth, balance,admin);
    	conn=connect;
    }
    
    public void UpdateMoney (String username, int actual_money) throws SQLException {    	
    	String update_account_money = "UPDATE blackjack.users SET money='"+actual_money+"' WHERE username='"+username+"'";
    	Statement statement = conn.createStatement();
		statement.executeUpdate(update_account_money);
		
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
       
            String path = getClass().getResource("../resources/music.mp3").getPath();
            Media media = new Media(new File(path).toURI().toString());
            mediaPlayer = new AudioClip(media.getSource());
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.1);
            if(Music.isSelected()){
                mediaPlayer.play();
            }
            else if(!(Music.isSelected())){
                mediaPlayer.stop();
            }
        
    }
    
    
    private void updateLobby(String messageFromServer) {
    	
    	String lines[] = messageFromServer.split("&");
    	for(int i=1;i<lines.length;i++) {
    		String player[]=lines[i].split("%");
    		for(int j=0;j<player.length;j++) {
    			changePlayer(i,j,player[j]);
    		}
    	}
    	
    	
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
	
	
	
	
}
