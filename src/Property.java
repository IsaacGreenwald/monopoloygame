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
		showAlert(this.owner + " owns this property. " + player.getName() + " pays $" + this.rent + " to " + this.owner);
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
		if (this.price > player.getMoney()) {
			showAlert("You do not have enough money to purchase this property.");
			return false;
		}

		ButtonType result = promptUser("Does " + player.getName() + " want to purchase " + this.name + " for $" + this.price + "?",
				ButtonType.YES, ButtonType.NO);

		if (result == ButtonType.YES) {
			player.addProperty(this);
			int newBalance = player.getMoney() - this.price;
			player.setMoney(newBalance);
			this.owner = player;
			showAlert("You have bought " + this.name + ". You now have: $" + newBalance);
			return true;
		}

		showAlert("Your turn is now over.");
		return false;
	}

	/**
	 * Mortgages the property and adds the mortgage value to the owner's balance.
	 *
	 * @return True if the mortgage is successful otherwise false.
	 */
	public boolean mortgage() {
		if (mortgaged) {
			showAlert("This property is already mortgaged.");
			return false;
		}

		owner.setMoney(owner.getMoney() + mortgageValue);
		mortgaged = true;
		showAlert(owner.getName() + " mortgaged " + getName() + " for $" + mortgageValue);
		return true;
	}
	/**
	 * Unmortgages the property and deducts the mortgage value from the owner's balance.
	 *
	 * @return True if the property is actually unmortgaged otherwise false.
	 */
	public boolean unmortgage() {
		if (!mortgaged) {
			showAlert("This property is not mortgaged.");
			return false;
		}

		if (owner.getMoney() < mortgageValue) {
			showAlert(owner.getName() + " does not have enough money to unmortgage " + getName() + ".");
			return false;
		}

		owner.setMoney(owner.getMoney() - mortgageValue);
		mortgaged = false;
		showAlert(owner.getName() + " unmortgaged " + getName() + " for $" + mortgageValue);
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
	/**
	 * Displays an alert with a given message to the player.
	 *
	 * @param message The message to be displayed in the alert.
	 */
	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.initOwner(primaryStage); 
		alert.setTitle("Monopoly Game Info");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * Prompts the user with a dialog containing a specific message and set of options.
	 *
	 * @param message The message to be displayed in the dialog.
	 * @param options Varargs of ButtonType options for the dialog.
	 * @return The ButtonType choice selected by the user
	 */
	private ButtonType promptUser(String message, ButtonType... options) {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(primaryStage);
		dialog.setTitle("Property Purchase");
		dialog.setContentText(message);
		dialog.getDialogPane().getButtonTypes().addAll(options);
		Optional<ButtonType> result = dialog.showAndWait();
		return result.orElse(ButtonType.NO);
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
	
	public void setOwner(Player player) {
		this.owner = player;
	}

}
