package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.BlackJackCard;

public class cardValueTest {

	@Test
	public void test() {
		BlackJackCard card= new BlackJackCard("hearts","Ace",10);
		assertEquals(card.getValue(),11);
	}

}
