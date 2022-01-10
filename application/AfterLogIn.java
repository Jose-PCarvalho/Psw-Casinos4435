package application;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.media.MediaPlayer;

import java.util.concurrent.atomic.AtomicInteger;

import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

public class AfterLogIn implements Initializable {

    public int people = 0;
    //public int balance = 1000;
    public int slideMoney;
    public int flag = 0;
    

    AudioClip mediaPlayer;

    @FXML
    private Button LogOut;
    @FXML
    private Button Menu;
    @FXML
    private Button MenuBack;
    @FXML
    private AnchorPane Slider;

    @FXML
    private Button Table1Join;
    @FXML
    private Label Table1Text;
    @FXML
    private Button Table1Err;
    @FXML
    private Button Table2Join;
    @FXML
    private Label Table2Text;
    @FXML
    private Button Table2Err;
    @FXML
    private Button Table3Join;
    @FXML
    private Label Table3Text;
    @FXML
    private Button Table3Err;
    @FXML
    private Button Table4Join;
    @FXML
    private Label Table4Text;
    @FXML
    private Button Table4Err;
    @FXML
    private Button Table5Join;
    @FXML
    private Label Table5Text;
    @FXML
    private Button Table5Err;

    @FXML
    private AnchorPane AccountTab;
    @FXML
    private Button MyAccBut;
    @FXML
    private Button MyAccClose;
    @FXML
    private Label Prof_Name;
    @FXML
    private Label Prof_UserName;
    @FXML
    private Label Prof_Password;
    @FXML
    private Label Balance;
    @FXML
    private Button WithdrawBut;
    @FXML
    private Button DepositBut;

    @FXML
    private Button Confirm;
    @FXML
    private TextField MoneyAmount;
    @FXML
    private Label MonErr;

    @FXML
    private Pane SlidePane;
    @FXML
    private Label SlideLabel;
    @FXML
    private javafx.scene.control.Slider SlideBut;

    @FXML
    private Button HelpClose;
    @FXML
    private Button HelpBut;
    @FXML
    private AnchorPane HelpTab;
    @FXML
    private ScrollPane HelpPane;
    @FXML
    private AnchorPane HelpText;

    @FXML
    private ToggleButton Music;

    @FXML
    private AnchorPane admin1;
    @FXML
    private AnchorPane admin2;
    @FXML
    private AnchorPane admin3;
    @FXML
    private AnchorPane admin4;
    @FXML
    private AnchorPane admin5;
    @FXML
    private Button adminClose1;
    @FXML
    private Button adminClose2;
    @FXML
    private Button adminClose3;
    @FXML
    private Button adminClose4;
    @FXML
    private Button adminClose5;
    @FXML
    private Button EnterTable1;
    @FXML
    private Button EnterTable2;
    @FXML
    private Button EnterTable3;
    @FXML
    private Button EnterTable4;
    @FXML
    private Button EnterTable5;
    @FXML
    private TextField DealerInput1;
    @FXML
    private TextField DealerInput2;
    @FXML
    private TextField DealerInput3;
    @FXML
    private TextField DealerInput4;
    @FXML
    private TextField DealerInput5;
    @FXML
    private Button ChangeDealer1;
    @FXML
    private Button ChangeDealer2;
    @FXML
    private Button ChangeDealer3;
    @FXML
    private Button ChangeDealer4;
    @FXML
    private Button ChangeDealer5;
    @FXML
    private Label DealerName1;
    @FXML
    private Label DealerName2;
    @FXML
    private Label DealerName3;
    @FXML
    private Label DealerName4;
    @FXML
    private Label DealerName5;
    @FXML
    private Label Table1D;
    @FXML
    private Label Table2D;
    @FXML
    private Label Table3D;
    @FXML
    private Label Table4D;
    @FXML
    private Label Table5D;
    @FXML
    private Label Player11;
    @FXML
    private Label Player12;
    @FXML
    private Label Player13;
    @FXML
    private Label Player14;
    @FXML
    private Label Player15;
    @FXML
    private Label Player16;
    @FXML
    private Label Player17;
    @FXML
    private Label Player21;
    @FXML
    private Label Player22;
    @FXML
    private Label Player23;
    @FXML
    private Label Player24;
    @FXML
    private Label Player25;
    @FXML
    private Label Player26;
    @FXML
    private Label Player27;
    @FXML
    private Label Player31;
    @FXML
    private Label Player32;
    @FXML
    private Label Player33;
    @FXML
    private Label Player34;
    @FXML
    private Label Player35;
    @FXML
    private Label Player36;
    @FXML
    private Label Player37;
    @FXML
    private Label Player41;
    @FXML
    private Label Player42;
    @FXML
    private Label Player43;
    @FXML
    private Label Player44;
    @FXML
    private Label Player45;
    @FXML
    private Label Player46;
    @FXML
    private Label Player47;
    @FXML
    private Label Player51;
    @FXML
    private Label Player52;
    @FXML
    private Label Player53;
    @FXML
    private Label Player54;
    @FXML
    private Label Player55;
    @FXML
    private Label Player56;
    @FXML
    private Label Player57;
    @FXML
    private Circle circle11;
    @FXML
    private Circle circle12;
    @FXML
    private Circle circle13;
    @FXML
    private Circle circle14;
    @FXML
    private Circle circle15;
    @FXML
    private Circle circle16;
    @FXML
    private Circle circle17;
    @FXML
    private Circle circle21;
    @FXML
    private Circle circle22;
    @FXML
    private Circle circle23;
    @FXML
    private Circle circle24;
    @FXML
    private Circle circle25;
    @FXML
    private Circle circle26;
    @FXML
    private Circle circle27;
    @FXML
    private Circle circle31;
    @FXML
    private Circle circle32;
    @FXML
    private Circle circle33;
    @FXML
    private Circle circle34;
    @FXML
    private Circle circle35;
    @FXML
    private Circle circle36;
    @FXML
    private Circle circle37;
    @FXML
    private Circle circle41;
    @FXML
    private Circle circle42;
    @FXML
    private Circle circle43;
    @FXML
    private Circle circle44;
    @FXML
    private Circle circle45;
    @FXML
    private Circle circle46;
    @FXML
    private Circle circle47;
    @FXML
    private Circle circle51;
    @FXML
    private Circle circle52;
    @FXML
    private Circle circle53;
    @FXML
    private Circle circle54;
    @FXML
    private Circle circle55;
    @FXML
    private Circle circle56;
    @FXML
    private Circle circle57;
    @FXML
    private Button kick11;
    @FXML
    private Button kick12;
    @FXML
    private Button kick13;
    @FXML
    private Button kick14;
    @FXML
    private Button kick15;
    @FXML
    private Button kick16;
    @FXML
    private Button kick17;
    @FXML
    private Button kick21;
    @FXML
    private Button kick22;
    @FXML
    private Button kick23;
    @FXML
    private Button kick24;
    @FXML
    private Button kick25;
    @FXML
    private Button kick26;
    @FXML
    private Button kick27;
    @FXML
    private Button kick31;
    @FXML
    private Button kick32;
    @FXML
    private Button kick33;
    @FXML
    private Button kick34;
    @FXML
    private Button kick35;
    @FXML
    private Button kick36;
    @FXML
    private Button kick37;
    @FXML
    private Button kick41;
    @FXML
    private Button kick42;
    @FXML
    private Button kick43;
    @FXML
    private Button kick44;
    @FXML
    private Button kick45;
    @FXML
    private Button kick46;
    @FXML
    private Button kick47;
    @FXML
    private Button kick51;
    @FXML
    private Button kick52;
    @FXML
    private Button kick53;
    @FXML
    private Button kick54;
    @FXML
    private Button kick55;
    @FXML
    private Button kick56;
    @FXML
    private Button kick57;
    
    @FXML
    private Button ChangeImage;
    
    private FileChooser fileChooser;
    private File filePath;
    
    @FXML
    private ImageView ProfileImage;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    
    /*Change profile picture*/
    public void chooseImageButtonPushed(ActionEvent Event) throws IOException{
        Stage stage = (Stage) ChangeImage.getScene().getWindow();
        fileChooser = new FileChooser();
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
    
    static Account user;
    
    public static String name;
    public static String username;
    public static String password;
    public static Date birth;
    public static int balance;
    public static boolean admin;
    private static Connection conn;
    
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    	
    	Prof_Name.setText(" � Profile Name: " + user.name);
    	Prof_UserName.setText(" � Username: " + user.username);
    	Prof_Password.setText(" � Password: " + user.password.replaceAll("(?s).", "*"));
        Balance.setText("� Current Balance: " + user.money);
        Slider.setTranslateX(400);
        AccountTab.setTranslateX(-1400);
        HelpTab.setTranslateX(-1400);
        MoneyAmount.setVisible(false);
        Confirm.setVisible(false);
        SlideLabel.setText(Integer.toString(balance));
        SlidePane.setVisible(false);
        SetTablesInvisible();
        SlideBut.setMax(balance);
        admin1.setTranslateX(-1400);
        admin2.setTranslateX(-1400);
        admin3.setTranslateX(-1400);
        admin4.setTranslateX(-1400);
        admin5.setTranslateX(-1400);
        DealerInput1.setVisible(false);
        DealerInput2.setVisible(false);
        DealerInput3.setVisible(false);
        DealerInput4.setVisible(false);
        DealerInput5.setVisible(false);
        Table1D.setText(DealerName1.getText());
        Table2D.setText(DealerName2.getText());
        Table3D.setText(DealerName3.getText());
        Table4D.setText(DealerName4.getText());
        Table5D.setText(DealerName5.getText());


        MenuOpen();

        MenuClose();

        AccountHandling();

        LogOut.setOnMouseClicked( event -> {
        	
            try {
                changeScene(event,"../Resources/login.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        HelpHandling();

        if(user.admin == true){
            //Table1Admin
            Table1Handling();

            //Table2Admin
            Table2Handling();

            //Table3Admin
            Table3Handling();

            //Table4Admin
            Table4Handling();

            //Table5Admin
            Table5Handling();

        }
        else if(user.admin == false) {
            TableScenarios();
        }

        BackgroundMusic();


    }

    private void Table5Handling() {
        if (Player51.getText().isEmpty()) {
            circle51.setFill(Color.GREY);
        } else {
            circle51.setFill(Color.LIGHTGREEN);
        }
        if (Player52.getText().isEmpty()) {
            circle52.setFill(Color.GREY);
        } else {
            circle52.setFill(Color.LIGHTGREEN);
        }
        if (Player53.getText().isEmpty()) {
            circle53.setFill(Color.GREY);
        } else {
            circle53.setFill(Color.LIGHTGREEN);
        }
        if (Player54.getText().isEmpty()) {
            circle54.setFill(Color.GREY);
        } else {
            circle54.setFill(Color.LIGHTGREEN);
        }
        if (Player55.getText().isEmpty()) {
            circle55.setFill(Color.GREY);
        } else {
            circle55.setFill(Color.LIGHTGREEN);
        }
        if (Player56.getText().isEmpty()) {
            circle56.setFill(Color.GREY);
        } else {
            circle56.setFill(Color.LIGHTGREEN);
        }
        if (Player57.getText().isEmpty()) {
            circle57.setFill(Color.GREY);
        } else {
            circle57.setFill(Color.LIGHTGREEN);
        }
        kick51.setOnMouseClicked(event->{
            Player51.setText("");
            circle51.setFill(Color.GREY);
        });
        kick52.setOnMouseClicked(event->{
            Player52.setText("");
            circle52.setFill(Color.GREY);
        });
        kick53.setOnMouseClicked(event->{
            Player53.setText("");
            circle53.setFill(Color.GREY);
        });
        kick54.setOnMouseClicked(event->{
            Player54.setText("");
            circle54.setFill(Color.GREY);
        });
        kick55.setOnMouseClicked(event->{
            Player55.setText("");
            circle55.setFill(Color.GREY);
        });
        kick56.setOnMouseClicked(event->{
            Player56.setText("");
            circle56.setFill(Color.GREY);
        });
        kick57.setOnMouseClicked(event->{
            Player57.setText("");
            circle57.setFill(Color.GREY);
        });

        Table5Join.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(admin5);
            slide.setToX(500);
            slide.play();
            admin5.setTranslateX(0);
        });
        adminClose5.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(admin5);
            slide.setToX(0);
            slide.play();
            admin5.setTranslateX(-1400);
        });
        EnterTable5.setOnMouseClicked(event -> {
           
            try {
               changeScene(event,"../Resources/Main.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ChangeDealer5.setOnMouseClicked(event -> {
            DealerInput5.setVisible(true);
        });
        DealerInput5.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                DealerName5.setText(DealerInput5.getText());
                Table5D.setText(DealerName5.getText());
                DealerInput5.setVisible(false);
            }
        });
    }

    private void Table4Handling() {
        if(Player41.getText().isEmpty()){
            circle41.setFill(Color.GREY);
        }
        else {
            circle41.setFill(Color.LIGHTGREEN);
        }
        if(Player42.getText().isEmpty()){
            circle42.setFill(Color.GREY);
        }
        else{
            circle42.setFill(Color.LIGHTGREEN);
        }
        if(Player43.getText().isEmpty()){
            circle43.setFill(Color.GREY);
        }
        else{
            circle43.setFill(Color.LIGHTGREEN);
        }
        if(Player44.getText().isEmpty()){
            circle44.setFill(Color.GREY);
        }
        else{
            circle44.setFill(Color.LIGHTGREEN);
        }
        if(Player45.getText().isEmpty()){
            circle45.setFill(Color.GREY);
        }
        else{
            circle45.setFill(Color.LIGHTGREEN);
        }
        if(Player46.getText().isEmpty()){
            circle46.setFill(Color.GREY);
        }
        else{
            circle46.setFill(Color.LIGHTGREEN);
        }
        if(Player47.getText().isEmpty()){
            circle47.setFill(Color.GREY);
        }
        else{
            circle47.setFill(Color.LIGHTGREEN);
        }
        kick41.setOnMouseClicked(event->{
            Player41.setText("");
            circle41.setFill(Color.GREY);
        });
        kick42.setOnMouseClicked(event->{
            Player42.setText("");
            circle42.setFill(Color.GREY);
        });
        kick43.setOnMouseClicked(event->{
            Player43.setText("");
            circle43.setFill(Color.GREY);
        });
        kick44.setOnMouseClicked(event->{
            Player44.setText("");
            circle44.setFill(Color.GREY);
        });
        kick45.setOnMouseClicked(event->{
            Player45.setText("");
            circle45.setFill(Color.GREY);
        });
        kick46.setOnMouseClicked(event->{
            Player46.setText("");
            circle46.setFill(Color.GREY);
        });
        kick47.setOnMouseClicked(event->{
            Player47.setText("");
            circle47.setFill(Color.GREY);
        });

        Table4Join.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(admin4);
            slide.setToX(500);
            slide.play();
            admin4.setTranslateX(0);
        });
        adminClose4.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(admin4);
            slide.setToX(0);
            slide.play();
            admin4.setTranslateX(-1400);
        });
        EnterTable4.setOnMouseClicked(event -> {
           
            try {
                changeScene(event,"../Resources/Main.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ChangeDealer4.setOnMouseClicked(event -> {
            DealerInput4.setVisible(true);
        });
        DealerInput4.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ENTER){
                DealerName4.setText(DealerInput4.getText());
                Table4D.setText(DealerName4.getText());
                DealerInput4.setVisible(false);
            }
        });
    }

    private void Table3Handling() {
        if(Player31.getText().isEmpty()){
            circle31.setFill(Color.GREY);
        }
        else {
            circle31.setFill(Color.LIGHTGREEN);
        }
        if(Player32.getText().isEmpty()){
            circle32.setFill(Color.GREY);
        }
        else{
            circle32.setFill(Color.LIGHTGREEN);
        }
        if(Player33.getText().isEmpty()){
            circle33.setFill(Color.GREY);
        }
        else{
            circle33.setFill(Color.LIGHTGREEN);
        }
        if(Player34.getText().isEmpty()){
            circle34.setFill(Color.GREY);
        }
        else{
            circle34.setFill(Color.LIGHTGREEN);
        }
        if(Player35.getText().isEmpty()){
            circle35.setFill(Color.GREY);
        }
        else{
            circle35.setFill(Color.LIGHTGREEN);
        }
        if(Player36.getText().isEmpty()){
            circle36.setFill(Color.GREY);
        }
        else{
            circle36.setFill(Color.LIGHTGREEN);
        }
        if(Player37.getText().isEmpty()){
            circle37.setFill(Color.GREY);
        }
        else{
            circle37.setFill(Color.LIGHTGREEN);
        }
        kick31.setOnMouseClicked(event->{
            Player31.setText("");
            circle31.setFill(Color.GREY);
        });
        kick32.setOnMouseClicked(event->{
            Player32.setText("");
            circle32.setFill(Color.GREY);
        });
        kick33.setOnMouseClicked(event->{
            Player33.setText("");
            circle33.setFill(Color.GREY);
        });
        kick34.setOnMouseClicked(event->{
            Player34.setText("");
            circle34.setFill(Color.GREY);
        });
        kick35.setOnMouseClicked(event->{
            Player35.setText("");
            circle35.setFill(Color.GREY);
        });
        kick36.setOnMouseClicked(event->{
            Player36.setText("");
            circle36.setFill(Color.GREY);
        });
        kick37.setOnMouseClicked(event->{
            Player37.setText("");
            circle37.setFill(Color.GREY);
        });

        Table3Join.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(admin3);
            slide.setToX(500);
            slide.play();
            admin3.setTranslateX(0);
        });
        adminClose3.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(admin3);
            slide.setToX(0);
            slide.play();
            admin3.setTranslateX(-1400);
        });
        EnterTable3.setOnMouseClicked(event -> {
            try {
                changeScene(event,"../resources/Main.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ChangeDealer3.setOnMouseClicked(event -> {
            DealerInput3.setVisible(true);
        });
        DealerInput3.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ENTER){
                DealerName3.setText(DealerInput3.getText());
                Table3D.setText(DealerName3.getText());
                DealerInput3.setVisible(false);
            }
        });
    }

    private void Table2Handling() {
        if(Player21.getText().isEmpty()){
            circle21.setFill(Color.GREY);
        }
        else {
            circle21.setFill(Color.LIGHTGREEN);
        }
        if(Player22.getText().isEmpty()){
            circle22.setFill(Color.GREY);
        }
        else{
            circle22.setFill(Color.LIGHTGREEN);
        }
        if(Player23.getText().isEmpty()){
            circle23.setFill(Color.GREY);
        }
        else{
            circle23.setFill(Color.LIGHTGREEN);
        }
        if(Player24.getText().isEmpty()){
            circle24.setFill(Color.GREY);
        }
        else{
            circle24.setFill(Color.LIGHTGREEN);
        }
        if(Player25.getText().isEmpty()){
            circle25.setFill(Color.GREY);
        }
        else{
            circle25.setFill(Color.LIGHTGREEN);
        }
        if(Player26.getText().isEmpty()){
            circle26.setFill(Color.GREY);
        }
        else{
            circle26.setFill(Color.LIGHTGREEN);
        }
        if(Player27.getText().isEmpty()){
            circle27.setFill(Color.GREY);
        }
        else{
            circle27.setFill(Color.LIGHTGREEN);
        }

        kick21.setOnMouseClicked(event->{
            Player21.setText("");
            circle21.setFill(Color.GREY);
        });
        kick22.setOnMouseClicked(event->{
            Player22.setText("");
            circle22.setFill(Color.GREY);
        });
        kick23.setOnMouseClicked(event->{
            Player23.setText("");
            circle23.setFill(Color.GREY);
        });
        kick24.setOnMouseClicked(event->{
            Player24.setText("");
            circle24.setFill(Color.GREY);
        });
        kick25.setOnMouseClicked(event->{
            Player25.setText("");
            circle25.setFill(Color.GREY);
        });
        kick26.setOnMouseClicked(event->{
            Player26.setText("");
            circle26.setFill(Color.GREY);
        });
        kick27.setOnMouseClicked(event->{
            Player27.setText("");
            circle27.setFill(Color.GREY);
        });

        Table2Join.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(admin2);
            slide.setToX(500);
            slide.play();
            admin2.setTranslateX(0);
        });
        adminClose2.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(admin2);
            slide.setToX(0);
            slide.play();
            admin2.setTranslateX(-1400);
        });
        EnterTable2.setOnMouseClicked(event -> {
            
            try {
               changeScene(event,"../Resources/Main.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ChangeDealer2.setOnMouseClicked(event -> {
            DealerInput2.setVisible(true);
        });
        DealerInput2.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ENTER){
                DealerName2.setText(DealerInput2.getText());
                Table2D.setText(DealerName2.getText());
                DealerInput2.setVisible(false);
            }
        });
    }

    private void Table1Handling() {
        if(Player11.getText().isEmpty()){
            circle11.setFill(Color.GREY);
        }
        else {
            circle11.setFill(Color.LIGHTGREEN);
        }
        if(Player12.getText().isEmpty()){
            circle12.setFill(Color.GREY);
        }
        else{
            circle12.setFill(Color.LIGHTGREEN);
        }
        if(Player13.getText().isEmpty()){
            circle13.setFill(Color.GREY);
        }
        else{
            circle13.setFill(Color.LIGHTGREEN);
        }
        if(Player14.getText().isEmpty()){
            circle14.setFill(Color.GREY);
        }
        else{
            circle14.setFill(Color.LIGHTGREEN);
        }
        if(Player15.getText().isEmpty()){
            circle15.setFill(Color.GREY);
        }
        else{
            circle15.setFill(Color.LIGHTGREEN);
        }
        if(Player16.getText().isEmpty()){
            circle16.setFill(Color.GREY);
        }
        else{
            circle16.setFill(Color.LIGHTGREEN);
        }
        if(Player17.getText().isEmpty()){
            circle17.setFill(Color.GREY);
        }
        else{
            circle17.setFill(Color.LIGHTGREEN);
        }

        kick11.setOnMouseClicked(event->{
            Player11.setText("");
            circle11.setFill(Color.GREY);
        });
        kick12.setOnMouseClicked(event->{
            Player12.setText("");
            circle12.setFill(Color.GREY);
        });
        kick13.setOnMouseClicked(event->{
            Player13.setText("");
            circle13.setFill(Color.GREY);
        });
        kick14.setOnMouseClicked(event->{
            Player14.setText("");
            circle14.setFill(Color.GREY);
        });
        kick15.setOnMouseClicked(event->{
            Player15.setText("");
            circle15.setFill(Color.GREY);
        });
        kick16.setOnMouseClicked(event->{
            Player16.setText("");
            circle16.setFill(Color.GREY);
        });
        kick17.setOnMouseClicked(event->{
            Player17.setText("");
            circle17.setFill(Color.GREY);
        });

        Table1Join.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(admin1);
            slide.setToX(500);
            slide.play();
            admin1.setTranslateX(0);
        });
        adminClose1.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(admin1);
            slide.setToX(0);
            slide.play();
            admin1.setTranslateX(-1400);
        });
        EnterTable1.setOnMouseClicked(event -> {
           
            try {
            	//Controller.setAccount(user, conn);
                changeScene(event,"../Resources/Main.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ChangeDealer1.setOnMouseClicked(event -> {
            DealerInput1.setVisible(true);
        });
        DealerInput1.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ENTER){
                DealerName1.setText(DealerInput1.getText());
                Table1D.setText(DealerName1.getText());
                DealerInput1.setVisible(false);
            }
        });
    }

    private void BackgroundMusic() {
        Music.setOnMouseClicked(event ->{
        	String path = getClass().getResource("../Resources/music.mp3").getPath();
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
        });
    }

    private void HelpHandling() {
        HelpBut.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(HelpTab);
            slide.setToX(500);
            slide.play();
            AccountTab.setTranslateX(-1400);
            HelpTab.setTranslateX(0);
        });

        HelpClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(HelpTab);
            slide.setToX(0);
            slide.play();
            AccountTab.setTranslateX(-1400);
            HelpTab.setTranslateX(1000);
        });
    }

    private void AccountHandling() {
        MyAccBut.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(AccountTab);
            slide.setToX(500);
            slide.play();
            HelpTab.setTranslateX(-1400);
            AccountTab.setTranslateX(0);
        });

        MyAccClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0));
            slide.setNode(AccountTab);
            slide.setToX(0);
            slide.play();
            HelpTab.setTranslateX(-1400);
            AccountTab.setTranslateX(1000);
        });

        WithdrawBut.setOnMouseClicked(event -> {
            SlidePane.setVisible(true);
            Confirm.setVisible(true);
            MoneyAmount.setVisible(false);
            SlideBut.setMax(user.money);
            flag = 1;
        });

        DepositBut.setOnMouseClicked(event -> {
            MoneyAmount.setVisible(true);
            Confirm.setVisible(true);
            SlideBut.setMax(user.money);
            SlidePane.setVisible(false);
            flag = 2;
        });

        Confirm.setOnMouseClicked(event -> {
            boolean flag_s = false;
            int money = getInt(MoneyAmount.getText());
            int s_money = getInt(SlideLabel.getText());

            if((MoneyAmount.getText().toString().isEmpty() || getInt(MoneyAmount.getText()) < 0) && flag == 2){
                MonErr.setText("Please Input proper data.");
            }
            else {
                if (flag == 1) {
                    user.money -= s_money;
                    flag_s = true;
                    SlideBut.setMax(user.money);
                    //System.out.println("tou1");
                }
                else if(flag == 2){
                    user.money += money;
                    flag_s = true;
                    SlideBut.setMax(user.money);
                    //System.out.println("tou8");
                }
            }
            if(flag_s == true){
                MonErr.setText("");
                Confirm.setVisible(false);
                MoneyAmount.setVisible(false);
                SlidePane.setVisible(false);
                Balance.setText("Current Balance: " + user.money);
                SlideBut.setMax(balance);
                flag = 0;
                try {
					UpdateMoney(user.username,user.money);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        SlideBut.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                slideMoney = (int) SlideBut.getValue();
                SlideLabel.setText(Integer.toString(slideMoney));
            }
        });
    }

    private int getInt(String test){
        try{
            return Integer.parseInt(test.trim());
        }catch(Exception e){
            return -1;
        }
    }

    private void TableScenarios() {
        Table1Join.setOnMouseClicked(event -> {
            people++;
            if(people >= 5 ) {
                Table1Err.setVisible(true);
                Table1Text.setVisible(true);
            }
            else {

                try {
                    changeScene(event,"../Resources/Main.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Table1Err.setOnMouseClicked(event -> {
            Table1Err.setVisible(false);
            Table1Text.setVisible(false);
        });
        Table2Join.setOnMouseClicked(event -> {
            people++;
            if(people >= 5 ) {
                Table2Err.setVisible(true);
                Table2Text.setVisible(true);
            }
            else {
                try {
                    changeScene(event,"../Resources/Main.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Table2Err.setOnMouseClicked(event -> {
            Table2Err.setVisible(false);
            Table2Text.setVisible(false);
        });
        Table3Join.setOnMouseClicked(event -> {
            people++;
            if(people >= 5 ) {
                Table3Err.setVisible(true);
                Table3Text.setVisible(true);
            }
            else {
                //change scene
                try {
                    changeScene(event,"../resources/Main.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Table3Err.setOnMouseClicked(event -> {
            Table3Err.setVisible(false);
            Table3Text.setVisible(false);
        });
        Table4Join.setOnMouseClicked(event -> {
            people++;
            if(people >= 5 ) {
                Table4Err.setVisible(true);
                Table4Text.setVisible(true);
            }
            else {
                //change scene

                try {
                    changeScene(event,"../Resources/Main.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Table4Err.setOnMouseClicked(event -> {
            Table4Err.setVisible(false);
            Table4Text.setVisible(false);
        });
        Table5Join.setOnMouseClicked(event -> {
            people++;
            if(people >= 5 ) {
                Table5Err.setVisible(true);
                Table5Text.setVisible(true);
            }
            else {
                //change scene
                
                try {
                    changeScene(event,"../Resources/Main.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Table5Err.setOnMouseClicked(event -> {
            Table5Err.setVisible(false);
            Table5Text.setVisible(false);
        });
    }

    private void MenuClose() {
        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(Slider);
            slide.setToX(0);
            slide.play();

            Slider.setTranslateX(400);
            slide.setOnFinished((ActionEvent e) ->{
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });
    }

    private void MenuOpen() {
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(Slider);
            slide.setToX(400);
            slide.play();

            Slider.setTranslateX(0);
            slide.setOnFinished((ActionEvent e) ->{
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });
    }

    private void SetTablesInvisible() {
        Table1Err.setVisible(false);
        Table1Text.setVisible(false);
        Table2Err.setVisible(false);
        Table2Text.setVisible(false);
        Table3Err.setVisible(false);
        Table3Text.setVisible(false);
        Table4Err.setVisible(false);
        Table4Text.setVisible(false);
        Table5Err.setVisible(false);
        Table5Text.setVisible(false);
    }
    
    
    public static Connection getConnection() throws SQLException {
    	String url = "jdbc:postgresql://db.fe.up.pt/meec1a0405";
		Properties props = new Properties();
		props.setProperty("user","meec1a0405");
		props.setProperty("password","IICQHlXb");
		Connection conn=null;
		try {
			 conn = DriverManager.getConnection(url, props);
			 return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    
    public void UpdateMoney (String username, int actual_money) throws SQLException {    	
    	String update_account_money = "UPDATE blackjack.users SET money='"+actual_money+"' WHERE username='"+username+"'";
    	Statement statement = conn.createStatement();
		statement.executeUpdate(update_account_money);
		
    }
    
    public void getUser(String username) throws SQLException {
    	
    	String sql_username_exist = "SELECT money, password FROM blackjack.users WHERE username='"+user.username+"'";
    	Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql_username_exist);
		while (resultSet.next()) {
			user.password = resultSet.getString("password");
		    user.money = resultSet.getInt("money");
		}


    	
    	
    	
    }
}


