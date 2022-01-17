package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.dkeep.Game;

public class availableSpotsTest {

	@Test
	public void test() {
		Game g=new Game(1);
		g.newPlayer(1, "Max", 100);
		assertEquals("Available Spots: 2 3 4 5 6 7 ",g.AvailableSpots());
	}

}
