package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.dkeep.Game;

public class getNumberOfParticipatingPlayersTest {

	@Test
	public void test() {
		Game g=new Game(1);
		g.newPlayer(1, "Max", 100);
		assertEquals(g.getNumberOfParticipatingPlayers(),0);
		g.setBet(1, 1);
		g.confirmBet(1);
		assertEquals(g.getNumberOfParticipatingPlayers(),1);

	}

}
