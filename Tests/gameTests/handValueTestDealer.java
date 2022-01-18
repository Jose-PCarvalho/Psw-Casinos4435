package Tests.gameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.BlackJackCard;
import BlackJack.Cards.Hand;

public class handValueTestDealer {

	@Test
	public void test() {
		Hand h=new Hand("Dealer");
		BlackJackCard card1=new BlackJackCard("hearts","Ace",10);
		BlackJackCard card2=new BlackJackCard("hearts","K",11);
		h.insertCard(card1);
		h.insertCard(card2);
		assertEquals(10,h.getHandValue());
	}

}
