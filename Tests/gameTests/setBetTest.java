package Tests.gameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.dkeep.Game;

public class setBetTest {

	@Test
	public void test() {
		Game g=new Game(1);
		g.newPlayer(1, "Max", 100);
		g.setBet(1, 1);
		g.confirmBet(1);
		assertEquals(1, g.Players[1].getBet());
		assertEquals(true,g.getBetEntered()[1]);
	}

}
