package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.BlackJackCard;
import BlackJack.Cards.Deck;

public class insertCardTest {

	@Test
	public void test() {
		Deck d= new Deck();
		BlackJackCard card=d.drawCard(30);
		assertEquals(d.isAvailable(30),false);
		assertEquals(d.getNumberOfCards(),51);
		d.insertCard(card);
		assertEquals(d.isAvailable(30),true);
		assertEquals(d.getNumberOfCards(),52);
	}

}
