package Tests.gameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BlackJack.Cards.Shoe;

public class dealCardShoeTest {

	@Test
	public void test() {
		Shoe s= new Shoe(6);
		assertEquals(true,s.dealCard()!=null);
	}

}
