package Tests.serverTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.junit.Test;

import application.Account;
import application.ClientHandler;

public class setLogoutTest {

	@Test
	public void test() throws UnknownHostException, SQLException, IOException {
		ClientHandler c=new ClientHandler();
		c.setLogout("jpc");
		Account user= new Account("jpc");
		assertEquals(user.setLogin(),true);
	}

}
