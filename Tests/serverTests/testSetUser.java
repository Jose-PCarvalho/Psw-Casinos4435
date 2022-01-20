package Tests.serverTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.junit.Test;

import application.Account;

public class testSetUser {

	@Test
	public void test() throws UnknownHostException, SQLException, IOException {
		Account user= new Account("jpc");
		assertEquals(user.username,"jpc");
	}

}
