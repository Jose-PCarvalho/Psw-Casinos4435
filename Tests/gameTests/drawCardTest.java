package Tests.gameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.BlackJackCard;
import BlackJack.Cards.Deck;

public class drawCardTest {

	@Test
	public void test() {
		Deck d= new Deck();
		BlackJackCard card=d.drawCard(0);
		assertEquals(true,card!=null);
		assertEquals(card.getPosition(),0);
		assertEquals(card.getFace(),"Ace");
	}

}
