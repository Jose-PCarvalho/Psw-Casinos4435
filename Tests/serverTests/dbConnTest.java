package Tests.serverTests;



import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

import org.junit.Test;

import application.LoginController;
import application.Server;

public class dbConnTest {

	@Test
	public void test() throws IOException, SQLException {
		LoginController.ConnectDB();
		
	}

}
