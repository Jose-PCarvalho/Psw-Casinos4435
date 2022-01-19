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
    
    public void UpdateMoney () throws SQLException {    	
    	String update_account_money = "UPDATE blackjack.users SET money='"+money+"' WHERE username='"+username+"'";
    	Statement statement = conn.createStatement();
		statement.executeUpdate(update_account_money);
		
    }
    public void UpdatePName (String PName) throws SQLException {
    	String update_profile_name = "UPDATE blackjack.users SET name= '" + PName + "' WHERE username ='"+ username +"'";
    	Statement statement =conn.createStatement();
    	statement.executeUpdate(update_profile_name);
    	name = PName;
    }
    public void UpdateUName(String UName) throws SQLException{
    	String update_username = "UPDATE blackjack.users SET username= '" + UName + "' WHERE username ='" + username + "'";
    	Statement statement = conn.createStatement();
    	statement.executeUpdate(update_username);
    	username = UName;
    }
    
    public void UpdatePass(String Pass) throws SQLException{
    	String update_pass = "UPDATE blackjack.users SET password= '" + Pass + "' WHERE username ='" + username + "'";
    	Statement statement = conn.createStatement();
    	statement.executeUpdate(update_pass);
    	password = Pass;
    }
    
    public void DeleteAccount() throws SQLException{
    	String delete = "DELETE FROM blackjack.users WHERE username= '" + username + "'";
    	Statement statement = conn.createStatement();
    	statement.executeUpdate(delete);
    }
    
    public void notNewPlayer() throws SQLException {
    	String update_pass = "UPDATE blackjack.users SET newplayer= '" + false + "' WHERE username ='" +username + "'";
    	Statement statement =conn.createStatement();
    	statement.executeUpdate(update_pass);
    	newComer=false;
    }
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

}