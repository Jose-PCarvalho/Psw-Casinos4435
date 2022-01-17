package Tests.gameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.dkeep.Game;

public class payBetTest {

	@Test
	public void test() {
		Game g=new Game(1);
		g.Players[1].deposit(1000);
		g.Players[1].setBet(1000);
		g.payBet(1, 1);
		assertEquals(g.Players[1].getWallet(),2000);
		g.payBet(1, 0);
		assertEquals(g.Players[1].getWallet(),2000);
		g.payBet(1, 2);
		assertEquals(g.Players[1].getWallet(),3000);
	}

}


