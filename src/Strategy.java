import java.util.ArrayList;

/*
 * interface for computer player strategies
 */
public interface Strategy {

	/**
	 * decides when the computer player will buy a property
	 * @param property landed on
	 * @param computer player who just rolled
	 * @return 
	 */
	public boolean chooseBuyProperty(Property property, ComputerPlayer computerPlayer);
	
	/**
	 * decides when the computer player will mortgage or unmortgage properties
	 * @param computer player 
	 */
	public void mortgageProperty(ComputerPlayer computerPlayer);
	
}
