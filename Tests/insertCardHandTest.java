package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.BlackJackCard;
import BlackJack.Cards.Hand;

public class insertCardHandTest {

	@Test
	public void test() {
		Hand h=new Hand();
		BlackJackCard card1=new BlackJackCard("Ace","Hearts",10);
		BlackJackCard card2=new BlackJackCard("K","Hearts",11);
		h.insertCard(card1);
		h.insertCard(card2);
		assertEquals(h.getHandsize(),2);
	}

}
