package Tests.serverTests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import application.LoginController;

public class loginTest {

	@Test
	public void test() throws SQLException {
		LoginController.ConnectDB();
		assertEquals(true,LoginController.UserNameExists("jpc"));
		assertEquals(true,LoginController.FindPassword("jpc", "jpc"));
	}

}
