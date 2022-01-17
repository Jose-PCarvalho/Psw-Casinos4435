package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.General_card;

public class cardTest {

	@Test
	public void test() {
		General_card card=new General_card("hearts","2",10);
		String testString=card.toString();
		assertEquals(testString,"hearts2");
	}

}
