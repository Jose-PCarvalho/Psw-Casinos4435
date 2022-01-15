package application;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.naming.Context;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Signup {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField NameField;
    @FXML
    private TextField PasswordField;
    @FXML
    private Button CreateAccBut;
    @FXML
    private Label CAError;
    @FXML
    private DatePicker AgeBD;
    @FXML AnchorPane AgeError;
    @FXML Button AgeOk;
    
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void changeScene(ActionEvent event, String path) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		root= loader.load();
		stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
		scene= new Scene(root);
		stage.setScene(scene);
    }

    public void CreateAccount(ActionEvent Event) throws IOException, InterruptedException, SQLException {
        if (usernameField.getText().isEmpty() || AgeBD.getValue() == null || PasswordField.getText().isEmpty() || NameField.getText().isEmpty()) {
            CAError.setText("Please input data.");
            CAError.setVisible(true);
        }
        
        else if(GetYears(AgeBD.getValue()) < 18 && AgeBD.getValue() != null){
        	//CAError.setText("You are too young to play this game.");
        	AgeError.setVisible(true);
        	
        }
        
        else {
        	if(UserNameExists(usernameField.getText()) == true) {
        		CAError.setText("Username already exists.\nChoose other.");
        		CAError.setVisible(true);
        	}
        	else {
        		InsertNewUser(NameField.getText(), usernameField.getText(), PasswordField.getText(), AgeBD.getValue(), 1000, false);
        		CAError.setText("Account successfully created.");
        		CAError.setVisible(true);
        		
        		changeScene(Event,"../Resources/login.fxml");
        	}

        }
    }

    @FXML 
    private void quit() {
    	Stage stage = (Stage) AgeOk.getScene().getWindow();
    	stage.close();
    }
    
    public void GoBack(ActionEvent Event) throws IOException {
        
        changeScene(Event,"../Resources/login.fxml");
    }

    public int GetYears(LocalDate date){
        Period period = Period.between(date, LocalDate.now());
        return period.getYears();
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
    
    public boolean UserNameExists (String usernameField) throws SQLException {
    	String sql_username_exist = "SELECT username FROM blackjack.users";
    	Connection conn = getConnection();
    	
    	Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql_username_exist);
		
		while (resultSet.next()) { 
            if(usernameField.equals(resultSet.getString("username")) == true) {
            	return true;
            }
        }
		
		conn.close();
    	return false;
    	
    }
    
    public void InsertNewUser (String name, String username, String pass, LocalDate birth, int money, boolean admin) throws SQLException {    	
    	Connection conn = getConnection();
    	String sql_insert_user = "INSERT INTO blackjack.users (name, username, password, birthdate, money, admin) VALUES ('"+name+"', '"+username+"', '"+pass+"', DATE('"+birth+"'), '"+money+"', '"+admin+"')";

    	Statement statement = conn.createStatement();
		statement.executeUpdate(sql_insert_user);
		
		
		conn.close();
    }
}


