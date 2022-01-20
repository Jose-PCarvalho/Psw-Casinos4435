package Tests.serverTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.junit.Test;

import application.Account;

public class updateMoneyTest {

	@Test
	public void test() throws SQLException, UnknownHostException, IOException {
		Account user= new Account("jpc");
		user.money=1000;
		user.UpdateMoney();
		assertEquals(1000,user.money);
	}

}
