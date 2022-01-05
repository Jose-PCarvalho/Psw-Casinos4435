package application;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;
import javafx.scene.Node;

public final class LoginController{
	
    public static int flag_u = 0;
    
    public static String log_username;

    @FXML
    private Button CancelBut;
    @FXML
    private static Label ErrorMsg;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView LockImg;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField PasswordTF;
    @FXML
    private Button LoginBut;
    @FXML
    private Button SignUpBut;
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void userLogIn(ActionEvent event) throws IOException, SQLException{
    	
    	
        if(usernameTF.getText().isEmpty() && PasswordTF.getText().isEmpty()){
            ErrorMsg.setText("Please enter your data.");
        }
        if(UserNameExists(usernameTF.getText()) == true) {
        	if(FindPassword(usernameTF.getText(), PasswordTF.getText()) == true) {
        		setUser();
        		changeScene(event,"../Resources/afterloginMC.fxml");
        		
        	}
        	else {
        		ErrorMsg.setText("Wrong password.");
        	}
        }
        else {
        	ErrorMsg.setText("Wrong username.");
        }
    }
    public void changeScene(ActionEvent event, String path) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		root= loader.load();
		stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
		scene= new Scene(root);
		stage.setScene(scene);
    }
    public void setUser() throws SQLException {
    	
    	String sql_username_exist = "SELECT * FROM blackjack.users WHERE username='"+usernameTF.getText()+"'";
    	Connection conn = getConnection();
    	Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql_username_exist);
		String reg_name=null;
		String reg_password=null;
		Date reg_birth=null;
		int reg_money=0;
		boolean reg_admin=false;
		while (resultSet.next()) {
			reg_name = resultSet.getString("name");
	    	System.out.println(reg_name);
			reg_password = resultSet.getString("password");
	    	System.out.println(reg_password);
		    reg_birth = resultSet.getDate("birthdate");
	    	System.out.println(reg_birth);
		    reg_money = resultSet.getInt("money");
	    	System.out.println(reg_money);
		    reg_admin = resultSet.getBoolean("admin");	
	    	System.out.println(reg_admin);
        }
		conn.close();
		AfterLogIn.setAccount(reg_name,usernameTF.getText() , reg_password, reg_birth, reg_money, reg_admin);
		
    	
    }

    public void cancelButAct(ActionEvent Event) throws IOException{
        Stage stage = (Stage) CancelBut.getScene().getWindow();
        stage.close();
    }

    public void changeWindowSU(ActionEvent Event) throws IOException{
 
        changeScene(Event,"../resources/signup.fxml");
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
    
    public static boolean UserNameExists (String usernameField) throws SQLException {
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
    
    public static boolean FindPassword (String usernameField, String passField) throws SQLException {
    	String sql_pass= "SELECT password FROM blackjack.users WHERE username='"+usernameField+"'";
    	Connection conn = getConnection();
    	
    	Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql_pass);
		
		while (resultSet.next()) {
            if(passField.equals(resultSet.getString("password")) == true) {
            	return true;
            }
        }
		
		conn.close();
    	return false;
    }
    
    public static boolean isAdmin (String usernameField) throws SQLException {
    	String sql_isAdmin= "SELECT admin FROM blackjack.users WHERE username='"+usernameField+"'";
    	Connection conn = getConnection();
    	
    	Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql_isAdmin);
		
		while (resultSet.next()) {
            if(usernameField.equals(resultSet.getString("admin")) == true) {
            	return true;
            }
        }
		
		conn.close();
    	return false;
    }
    
    
}