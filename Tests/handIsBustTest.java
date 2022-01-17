package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.BlackJackCard;
import BlackJack.Cards.Hand;

public class handIsBustTest {

	@Test
	public void test() {
		Hand h=new Hand();
		BlackJackCard card1=new BlackJackCard("hearts","K",12);
		BlackJackCard card2=new BlackJackCard("hearts","K",11);
		BlackJackCard card3=new BlackJackCard("hearts","K",12);
		h.insertCard(card1);
		h.insertCard(card2);
		h.insertCard(card3);
		assertEquals(true,h.isBust());
		h=new Hand();
		h.insertCard(card1);
		h.insertCard(card2);
		assertEquals(false,h.isBust());
	}

}
