package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.dkeep.Game;

public class gameInfoTest {

	@Test
	public void test() {
		Game g=new Game(1);
		g.newPlayer(1, "Max", 100);
		assertEquals("GameState:Waiting for Players:1:10:0:0%Dealer:Hand Size:0:Hand Value:0:Hand Cards: %Player1:Wallet:100:Bet:0:Hand Size:0:Hand Value:0:Hand Cards: :Result:-1:Participating:false:TurnEnded:false%Player2:Not Playing%Player3:Not Playing%Player4:Not Playing%Player5:Not Playing%Player6:Not Playing%Player7:Not Playing%",g.getInfo());
	}

}
