package Tests.serverTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.junit.Test;

import application.Account;
import application.ClientHandler;

public class setLogInTest {

	@Test
	public void test() throws SQLException, UnknownHostException, IOException {
		ClientHandler c=new ClientHandler();
		c.setLogin("jpc");
		Account user= new Account("jpc");
		assertEquals(user.setLogin(),false);
	}

}
