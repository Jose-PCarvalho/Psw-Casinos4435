package Tests.serverTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.junit.Test;

import application.Account;

public class updatePNameTest {

	@Test
	public void test() throws UnknownHostException, SQLException, IOException {
		Account user= new Account("jpc");
		user.UpdatePName("123");
		assertEquals(user.name, "123");
		user.UpdatePName("jpc");
		assertEquals(user.name, "jpc");
	}

}
