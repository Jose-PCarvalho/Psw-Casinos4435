package Tests.gameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.BlackJackCard;
import BlackJack.dkeep.Game;

public class whoWonTest {

	@Test
	public void test() {
		Game g=new Game(1);
		BlackJackCard card1=new BlackJackCard("hearts","Ace",12);
		BlackJackCard card2=new BlackJackCard("hearts","K",11);
		g.Players[1].PlayerHand[0].insertCard(card2);
		g.Players[1].PlayerHand[0].insertCard(card1);
		g.setBet(1, 1);
		g.confirmBet(1);
		assertEquals(1,g.whoWon(1));
		g=new Game(1);
		g.confirmBet(1);
		assertEquals(2,g.whoWon(1));
		g.D.DealerHand.insertCard(card2);
		g.D.DealerHand.insertCard(card1);
		g.Players[1].PlayerHand[0].insertCard(card2);
		assertEquals(0,g.whoWon(1));
	}

}
