package application;

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
    
    /*****/
   
    
    public Account(String name, String username, String password, Date birth, int money, boolean admin,boolean newComer) {
    	this.name=name;
    	this.username=username;
    	this.password=password;
    	this.birth=birth;
    	this.money=money;
    	this.admin=admin;
    	this.newComer=newComer;
    }
}