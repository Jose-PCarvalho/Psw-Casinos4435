package Tests.serverTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.junit.Test;

import application.Account;

public class updateUsernameTest {

	@Test
	public void test() throws UnknownHostException, SQLException, IOException {
		Account user= new Account("jpc");
		user.UpdateUName("123");
		assertEquals(user.username, "123");
		user.UpdateUName("jpc");
		assertEquals(user.username, "jpc");
	}

}
