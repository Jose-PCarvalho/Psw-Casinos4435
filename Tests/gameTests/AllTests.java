package Tests.gameTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ availableSpotsTest.class, cancelBetTest.class, cardTest.class, cardValueTest.class,
		changeDealerNameTest.class, dealCardShoeTest.class, drawCardTest.class, gameInfoTest.class,
		getNumberOfParticipatingPlayersTest.class, handIsBustTest.class, handToStringTest.class, handValueTest.class,
		handValueTestDealer.class, insertCardHandTest.class, insertCardTest.class, isAceTest.class,
		isBlackJackTest.class, kickPlayerTest.class, lobbyInfoTest.class, payBetInsuranceTest.class, payBetTest.class,
		sanityCheckTest.class, setBetTest.class, switchAceTest.class, trueHandValueTest.class, whoWonTest.class })
public class AllTests {

}
