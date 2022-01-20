package Tests.gameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.dkeep.Game;

public class payBetInsuranceTest {

	@Test
	public void test() {
		Game g=new Game(1);
		g.Players[1].deposit(2000);
		g.Players[1].setBet(1000);
		g.setInsurance(1);
		assertEquals(g.Players[1].getBet(),2000);
		g.payBet(1, 1);
		assertEquals(g.Players[1].getWallet(),2000);
		
	}

}
