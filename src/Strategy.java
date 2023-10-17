import java.util.ArrayList;

/*
 * interface for computer player strategies
 */
public interface Strategy {

	/**
	 * decides when the computer player will buy a property
	 * @param property landed on
	 * @param computer player who just rolled
	 */
	public void chooseBuyProperty(Property property, ComputerPlayer computerPlayer);
	
	/**
	 * decides when the computer player will mortgage or unmortgage properties
	 * @param computer player 
	 */
	public void mortgageProperty(ComputerPlayer computerPlayer);
	
	/**
	 * decides how much money the computer player is willing to bid on a property
	 * @param the property being bid on
	 * @param the computer player
	 */
	public void bid(Property property, ComputerPlayer computerPlayer);
	
}
