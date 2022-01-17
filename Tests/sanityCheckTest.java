package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.dkeep.Game;

public class sanityCheckTest {

	@Test
	public void test() {
		Game g=new Game(1);
		g.newPlayer(1, "Max", 100);
		assertEquals(g.sanityCheck(), true);
	}

}
