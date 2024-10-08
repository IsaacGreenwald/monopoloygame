import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

/**
 * Represents a property spot on the Monopoly board.
 */
public class Property implements Spot {
	private String name;
	private int price;
	private Player owner;
	private int rent;
	private boolean mortgaged = false;
	private int mortgageValue;
	private Stage primaryStage;


	public Property(String name, int price, int startingRent, int mortgageValue, Stage primaryStage) {
		this.rent = startingRent;
		this.name = name;
		this.setPrice(price);
		this.mortgageValue = mortgageValue;
		this.primaryStage = primaryStage;
		this.owner = null;

	}

	@Override
	public String getName() {
		return name;
	}
	/**
	 * Determines the action when a player lands on this property spot.
	 * If the property is unowned, it prompts the player to buy.
	 * If the property is owned by someone else, the player has to pay rent.
	 *
	 * @param player The player landing on the spot.
	 */
	@Override
	public void onABoardSpot(Player player) {
		if (this.owner == null) {
			buyProperty(player);
		} else {
			payUp(player);
		}
	}

	/**
	 * Processes the rent payment from a player to the owner of this property.
	 *
	 * @param player - The player who needs to pay the rent.
	 */
	public void payUp(Player player) {
		DialogUtils.showAlert(this.owner.getName() + " owns this property. " + player.getName() + " pays $" + this.rent + " to " + this.owner.getName());
		int payerMoney = player.getMoney() - this.rent;
		int payeeMoney = this.owner.getMoney() + this.rent;
		player.setMoney(payerMoney);
		this.owner.setMoney(payeeMoney);
	}
	/**
	 * Prompts the player to purchase the property.
	 *
	 * @param player - The player landing on the unowned property.
	 * @return True if the player decides to purchase the property, otherwise false.
	 */
	public boolean buyProperty(Player player) {
	    boolean playerDecision;
	    
	    if (player instanceof ComputerPlayer) {
	        playerDecision = computerPlayerBuyProperty((ComputerPlayer) player);
	    } else {
	        playerDecision = humanPlayerBuyProperty(player);
	    }

	    DialogUtils.showAlert(player.getName() + ", your turn is now over.");
	    
	    return playerDecision;
	}

	/**
	 * Handles the purchase of a property by a human player.
	 *
	 * @param player The human player attempting to make the purchase.
	 * @return true if the player proceeds with the purchase
	 */
	private boolean humanPlayerBuyProperty(Player player) {
	    ButtonType result = DialogUtils.promptUser("Does " + player.getName() + " want to purchase " + this.name + " for $" + this.price + "?",
	            ButtonType.YES, ButtonType.NO);
	    boolean wantsToBuy = (result == ButtonType.YES);
	    return proceedWithPurchase(player, wantsToBuy);
	}


	/**
	 * Handles the purchase of a property by a computer player based on their strategy.
	 *
	 * @param player The computer player attempting to make the purchase.
	 * @return true if the player proceeds with the purchase as per their strategy
	 */
	private boolean computerPlayerBuyProperty(ComputerPlayer player) {
	    Strategy strategy = player.getStrategy();
	    boolean wantsToBuy = strategy.chooseBuyProperty(this, player); 
	    return proceedWithPurchase(player, wantsToBuy);
	}


	/**
	 * Proceeds with the purchase of a property by a player if conditions are met.
	 *
	 * @param player The player attempting to make the purchase.
	 * @param wantsToBuy true if the player wants to buy the property
	 * @return true if the purchase is successful
	 */
private boolean proceedWithPurchase(Player player, boolean wantsToBuy) {
    if (this.price > player.getMoney()) {
        DialogUtils.showAlert(player.getName() + " do not have enough money to purchase this property.");
        return false;
    }

    if (wantsToBuy) {
        player.addProperty(this);
        int newBalance = player.getMoney() - this.price;
        player.setMoney(newBalance);
        this.owner = player;
        DialogUtils.showAlert(player.getName() + " have bought " + this.name + ". You now have: $" + newBalance);
        return true;
    }

    DialogUtils.showAlert(player.getName() + ", your turn is now over.");
    return false;
}

	/**
	 * Mortgages the property and adds the mortgage value to the owner's balance.
	 *
	 * @return True if the mortgage is successful otherwise false.
	 */
	public boolean mortgage() {
		if (mortgaged) {
			DialogUtils.showAlert("This property is already mortgaged.");
			return false;
		}

		owner.setMoney(owner.getMoney() + mortgageValue);
		mortgaged = true;
		DialogUtils.showAlert(owner.getName() + " mortgaged " + getName() + " for $" + mortgageValue);
		return true;
	}
	/**
	 * Unmortgages the property and deducts the mortgage value from the owner's balance.
	 *
	 * @return True if the property is actually unmortgaged otherwise false.
	 */
	public boolean unmortgage() {
		if (!mortgaged) {
			DialogUtils.showAlert("This property is not mortgaged.");
			return false;
		}

		if (owner.getMoney() < mortgageValue) {
			DialogUtils.showAlert(owner.getName() + " does not have enough money to unmortgage " + getName() + ".");
			return false;
		}

		owner.setMoney(owner.getMoney() - mortgageValue);
		mortgaged = false;
		DialogUtils.showAlert(owner.getName() + " unmortgaged " + getName() + " for $" + mortgageValue);
		return true;
	}

	/**
	 * Check if the property is mortgaged.
	 *
	 * @return True if the property is mortgaged
	 */
	public boolean isMortgaged() {
		return mortgaged;
	}



	@Override
	public boolean equals(Object o) {
		if(o==this) {
			return true;
		}
		if(!(o instanceof Property)) {
			return false;
		}
		Property property = (Property)o;
		return false; //finish later for tests
	}



	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMortgageValue() {
		return mortgageValue;
	}
	
	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player player) {
		this.owner = player;
	}
	
	public int getRent() {
		return rent; 
		
	}

}
