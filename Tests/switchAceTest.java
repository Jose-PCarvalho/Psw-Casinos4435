package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.BlackJackCard;

public class switchAceTest {

	@Test
	public void test() {
		BlackJackCard card= new BlackJackCard("hearts","Ace",10) ;
		card.switchAceLow();
		assertEquals(card.getValue(),1);
			
		
	}

}
