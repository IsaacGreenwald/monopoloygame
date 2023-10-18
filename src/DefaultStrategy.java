import java.util.ArrayList;

/**
 * basic strategy for computer player. 
 * will buy a property if it has enough money to buy the property 
 * and have 100 left over
 * bid up to double the starting price of the property
 * if it has less than 100, it will mortgage its least expensive property
 * @author igree
 *
 */
public class DefaultStrategy implements Strategy{

	
	@Override
	public boolean chooseBuyProperty(Property property, ComputerPlayer computerPlayer) {
	    int willingToPay = computerPlayer.getMoney() - 100;

	    if (willingToPay > property.getPrice()) {
	
	        return true;  
	    }

	    return false;  }

	@Override
	public void mortgageProperty(ComputerPlayer computerPlayer) {
		Property toMortgage;
		ArrayList<Property> owned = computerPlayer.getCards();
		if(computerPlayer.getCards().size() == 1) {
			toMortgage = owned.get(0);
			toMortgage.mortgage();
		}
		else if(owned.size() > 1) {
			toMortgage = owned.get(0);
			for(int i = 1; i < owned.size(); i++) {
				if(owned.get(i).getPrice() < toMortgage.getPrice()) { 
																	  
					toMortgage = owned.get(i);
				}
			}
			toMortgage.mortgage();
		}
	}

	
}
