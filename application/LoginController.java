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
    private Label ErrorMsg;
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
    static Connection conn;
    
  

    public void userLogIn(ActionEvent event) throws IOException, SQLException, InterruptedException{
    	if(conn==null) {
    		ConnectDB();
    		}
    	else if(conn.isClosed()) {
    		ConnectDB();
    	}
        if(usernameTF.getText().isEmpty() && PasswordTF.getText().isEmpty()){
        	ErrorMsg.setText("Please enter your data.");
        }
        else {
        	String username=usernameTF.getText();
            if(UserNameExists(username) == true) {
            	if(FindPassword(username, PasswordTF.getText()) == true) {
            		setUser();
            		changeScene(event,"../Resources/AfterLogin.fxml");	
            	}
            	else {
            		ErrorMsg.setText("Wrong password.Try again.");
            	}
            }
            else {
            	ErrorMsg.setText("Wrong username.Try again.");
            }
        }
        ErrorMsg.setVisible(true);
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
    	Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql_username_exist);
		String reg_name=null;
		String reg_password=null;
		Date reg_birth=null;
		int reg_money=0;
		boolean reg_admin=false;
		while (resultSet.next()) {
			reg_name = resultSet.getString("name");
			reg_password = resultSet.getString("password");
		    reg_birth = resultSet.getDate("birthdate");
		    reg_money = resultSet.getInt("money");
		    reg_admin = resultSet.getBoolean("admin");	
        }
		
		
		
		AfterLoginController.setAccount(reg_name,usernameTF.getText() , reg_password, reg_birth, reg_money, reg_admin, conn);
		
    	
    }

    public void cancelButAct(ActionEvent Event) throws IOException{
        Stage stage = (Stage) CancelBut.getScene().getWindow();
        stage.close();
    }

    public void changeWindowSU(ActionEvent Event) throws IOException{
 
        changeScene(Event,"../resources/signup.fxml");
    }

    
    
    public static void ConnectDB() throws SQLException {
    	String url = "jdbc:postgresql://db.fe.up.pt/meec1a0405";
		Properties props = new Properties();
		props.setProperty("user","meec1a0405");
		props.setProperty("password","IICQHlXb");
		conn=null;
		try {
			 conn = DriverManager.getConnection(url, props);
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    public static boolean UserNameExists (String usernameField) throws SQLException {
    	String sql_username_exist = "SELECT username FROM blackjack.users";
    	
    	
    	Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql_username_exist);
		
		while (resultSet.next()) {
            if(usernameField.equals(resultSet.getString("username")) == true) {
            	return true;
            }
        }
		
		
    	return false;
    }
    
    public static boolean FindPassword (String usernameField, String passField) throws SQLException {
    	String sql_pass= "SELECT password FROM blackjack.users WHERE username='"+usernameField+"'";
    	
    	
    	Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql_pass);
		
		while (resultSet.next()) {
            if(passField.equals(resultSet.getString("password")) == true) {
            	return true;
            }
        }
		
		
    	return false;
    }
    
   
    
    
}