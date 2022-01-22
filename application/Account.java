package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Account {
	public String name;
    public String username;
    public String password;
    public Date birth;
    public int money;
    public boolean admin;
    public boolean newComer;
    public Connection conn;
    int pid;
    int table;
    public boolean loggedIn=false;

    
    /**
     * @throws IOException 
     * @throws UnknownHostException ***/
   
    
    public Account(String name, String username, String password, Date birth, int money, boolean admin,boolean newComer, Connection conn) throws UnknownHostException, IOException {
    	this.name=name;
    	this.username=username;
    	this.password=password;
    	this.birth=birth;
    	this.money=money;
    	this.admin=admin;
    	this.newComer=newComer;
    	this.conn=conn;

    }
    
    public Account(String uname) throws UnknownHostException, SQLException, IOException {
		ConnectDB();
		setUser(uname);
	}

	/**
	 * @throws SQLException
	 * Updates the money value on database.
	 */
	public void UpdateMoney () throws SQLException {    	
    	String update_account_money = "UPDATE blackjack.users SET money='"+money+"' WHERE username='"+username+"'";
    	Statement statement = conn.createStatement();
		statement.executeUpdate(update_account_money);
		
    }
    /**
     * @param PName
     * @throws SQLException
     * Updates the profile name in the database.
     */
    public void UpdatePName (String PName) throws SQLException {
    	String update_profile_name = "UPDATE blackjack.users SET name= '" + PName + "' WHERE username ='"+ username +"'";
    	Statement statement =conn.createStatement();
    	statement.executeUpdate(update_profile_name);
    	name = PName;
    }
    /**
     * @param UName
     * @throws SQLException
     * Updates the username in the database
     */
    public void UpdateUName(String UName) throws SQLException{
    	String update_username = "UPDATE blackjack.users SET username= '" + UName + "' WHERE username ='" + username + "'";
    	Statement statement = conn.createStatement();
    	statement.executeUpdate(update_username);
    	username = UName;
    }
    
    /**
     * @param Pass
     * @throws SQLException
     * Updates the password in the database.
     */
    public void UpdatePass(String Pass) throws SQLException{
    	String update_pass = "UPDATE blackjack.users SET password= '" + Pass + "' WHERE username ='" + username + "'";
    	Statement statement = conn.createStatement();
    	statement.executeUpdate(update_pass);
    	password = Pass;
    }
    
    /**
     * @throws SQLException
     * Deletes account from the database.
     */
    public void DeleteAccount() throws SQLException{
    	String delete = "DELETE FROM blackjack.users WHERE username= '" + username + "'";
    	Statement statement = conn.createStatement();
    	statement.executeUpdate(delete);
    }
    
    /**
     * @throws SQLException
     * sets the new player flag too false.
     */
    public void notNewPlayer() throws SQLException {
    	String update_pass = "UPDATE blackjack.users SET newplayer= '" + false + "' WHERE username ='" +username + "'";
    	Statement statement =conn.createStatement();
    	statement.executeUpdate(update_pass);
    	newComer=false;
    }
    /**
     * @return
     * @throws SQLException
     * logs in account return true if account wasn't logged in already before the current session.
     */
    public boolean setLogin () throws SQLException {
    	
    	String logged = "SELECT logged FROM blackjack.users WHERE username='"+username+"'";   	
    	Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(logged);
		if(loggedIn==true) {
			return true;
		}
		while (resultSet.next()) {
            if(resultSet.getString("logged").equals("f")) {
            	String update = "UPDATE blackjack.users SET logged='"+true+"' WHERE username='"+username+"'";
            	statement = conn.createStatement();
        		statement.executeUpdate(update);
        		loggedIn=true;
        		return true;
            }
        }
		return false;
    	
		
    }
    
    
    
    
/**
 * @param username
 * @throws SQLException
 * @throws UnknownHostException
 * @throws IOException
 * Constructor method for the database.
 */
public void setUser(String username) throws SQLException, UnknownHostException, IOException {
    	
    	String sql_username_exist = "SELECT * FROM blackjack.users WHERE username='"+username+"'";
    	Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql_username_exist);
		
		while (resultSet.next()) {
			this.username=resultSet.getString("username");
			this.name = resultSet.getString("name");
			this.password = resultSet.getString("password");
			this.birth = resultSet.getDate("birthdate");
			this.money = resultSet.getInt("money");
			this.admin = resultSet.getBoolean("admin");	
		
        }
}


/**
 * Creates a connection with the DB.
 * 
 */
public void ConnectDB() {
	String url = "jdbc:postgresql://db.fe.up.pt/meec1a0405";
	Properties props = new Properties();
	props.setProperty("user","meec1a0405");
	props.setProperty("password","IICQHlXb");
	conn=null;
	try {
		 conn = DriverManager.getConnection(url, props);
		 
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

/**
 * @param username
 * @return
 * @throws SQLException
 * checks if the username exists in the database
 */
public boolean UserNameExists (String username) throws SQLException {
	String sql_username_exist = "SELECT username FROM blackjack.users";
	
	
	Statement statement = conn.createStatement();
	ResultSet resultSet = statement.executeQuery(sql_username_exist);
	
	while (resultSet.next()) { 
        if(username.equals(resultSet.getString("username")) == true) {
        	return true;
        }
    }
	
	
	return false;
	
}

}