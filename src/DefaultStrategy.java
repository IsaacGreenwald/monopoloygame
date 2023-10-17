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
	public void buyProperty(Property property, ComputerPlayer computerPlayer) {
		int willingToPay = computerPlayer.getMoney() - 100;
		if(willingToPay > property.getPrice()) {
			int newBalance = computerPlayer.getMoney() - property.getPrice();
			computerPlayer.setMoney(newBalance);
			computerPlayer.addProperty(property);
			property.setOwner(computerPlayer);
		}
		else {
			//make window that says the computer player has chosen not buy the proeprty
		}
	}

	@Override
	public void mortgageProperty(ComputerPlayer computerPlayer) {
		Property toMortgage;
		if(computerPlayer.getCards().size() == 0) {
			
		}
	}

	@Override
	public void bid(Property property, ComputerPlayer computerPlayer) {
		
	}

	
}
