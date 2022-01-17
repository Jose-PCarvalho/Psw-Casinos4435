package Tests.gameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.dkeep.Game;

public class kickPlayerTest {

	@Test
	public void test() {
		Game g=new Game(1);
		g.newPlayer(1, "Max", 100);
		assertEquals(g.getNumberOfPlayers(), 1);
		g.kickPlayer(1);
		assertEquals(g.getNumberOfPlayers(), 0);
	}

}
