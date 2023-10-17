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

	
	@Before
	public void setup() {
		System.out.println("1");
		computerPlayer = new ComputerPlayer("1", new DefaultStrategy());
		
		
	}
	
	@Test
	public void testDefaultStrategyBuyProperty() {
		computerPlayer.setMoney(300);
		computerPlayer.chooseBuyProperty(medAve);
		assertEquals(computerPlayer.getCards().size(), 1);
	}
	@Test
	public void testDefaultStrategyNotBuyProperty() {
		computerPlayer.setMoney(150);
		computerPlayer.chooseBuyProperty(medAve);
		assertEquals(computerPlayer.getCards().size(), 0);
	}
	
	@Test 
	public void testDefaultStrategyMortgage() {
		computerPlayer.setMoney(90);
		computerPlayer.addProperty(OrientalAve);
		computerPlayer.addProperty(balticAve);
		computerPlayer.addProperty(medAve);
		computerPlayer.mortgage();
		assertFalse(computerPlayer.getCards().contains(medAve));
	}
	
	
}
