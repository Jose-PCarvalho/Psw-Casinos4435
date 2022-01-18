package Tests.gameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.dkeep.Game;

public class changeDealerNameTest {

	@Test
	public void test() {
		Game g=new Game(1);
		assertEquals(g.D.getName(),"Dolores Aveiro");
		g.changeDealerName("Julia Pinheiro");
		assertEquals(g.D.getName(),"Julia Pinheiro");
	}

}
