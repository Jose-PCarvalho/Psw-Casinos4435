package Tests.serverTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.junit.Test;

import application.Account;

public class updatePassTest {

	@Test
	public void test() throws SQLException, UnknownHostException, IOException {
		Account user= new Account("jpc");
		user.UpdatePass("123");
		assertEquals(user.password, "123");
		user.UpdatePass("jpc");
		assertEquals(user.password, "jpc");
		
	}

}
