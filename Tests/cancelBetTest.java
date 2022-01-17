package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.dkeep.Game;

public class cancelBetTest {

	@Test
	public void test() {
		Game g=new Game(1);
		g.newPlayer(1, "Max", 100);
		g.setBet(1, 1);
		assertEquals(1, g.Players[1].getBet());
		g.cancelBet(1);
		assertEquals(0, g.Players[1].getBet());
		assertEquals(false,g.getBetEntered()[1]);
	}

}
