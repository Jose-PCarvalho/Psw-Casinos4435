package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.BlackJackCard;

public class isAceTest {

	@Test
	public void test() {
		BlackJackCard card1 =new BlackJackCard("hearts","Ace",10);
		BlackJackCard card2 =new BlackJackCard("hearts","King",10);
		assertEquals(card1.isAce(),true);
		assertEquals(card2.isAce(),false);
	}

}
