import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ComputerPlayerTest {
	private ComputerPlayer computerPlayer;
	Property medAve = new Property("Mediterranean Avenu", 60, 2, 30, null);
	Property balticAve = new Property("Baltic Avenue", 60, 4, 30, null);
	Property OrientalAve = new Property("Oriental Avenue", 100, 6, 50, null);
	ComputerPlayerFactory computerFactory;
	
	@Before
	public void setup() {
		computerFactory = new ComputerPlayerFactory();
		computerPlayer = computerFactory.createComputerPlayer(null,  null, 1);
		
	}
	
	@Test
	public void testDefaultStrategyBuyProperty() {
		computerPlayer.setMoney(300); 
		computerPlayer.chooseBuyProperty(medAve);//not working properly
		assertEquals(computerPlayer.getCards().size(), 1);
	}
	@Test
	public void testDefaultStrategyNotBuyProperty() {
		computerPlayer.setMoney(150);
		computerPlayer.chooseBuyProperty(medAve);
		assertEquals(computerPlayer.getCards().size(), 0);
	}
	

	
}
