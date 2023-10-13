import java.util.Scanner;

/**
 * Represents a property spot on the Monopoly board.
 */
public class Property implements Spot {
    private String name;
    private int price;
    private Player owner;
    private int rent;

    /**
     * Constructs a new Property with the specified name and price.
     * 
     * @param name  The name of the property.
     * @param price The purchase price of the property.
     */
    public Property(String name, int price, int startingRent) {
    	this.rent = startingRent;
        this.name = name;
        this.setPrice(price);
    }

    @Override
    public String getName() {
        return name;
    }

	@Override
	public void onABoardSpot(Player player) {
		System.out.println(player.getName() + " landed on " + getName());
		if(this.owner == null) {
			this.buyProperty(player);

		}
		else {
			this.payUp(player);
		}
	}
	
	/**
	 * if a player lands on the property owned by another player
	 * they pay the rent of that property
	 * @param player who just rolled
	 */
	public void payUp(Player player) {
		System.out.println(this.owner + " owns this property.");
		System.out.println(player.getName() + " pays $" + this.rent + " to " + this.owner); 
		if(player.getMoney() < this.rent) {
			//if rent costs more than player has
		}
		int payerMoney = player.getMoney() - this.rent;
		int payeeMoney = this.owner.getMoney() + this.rent;
		player.setMoney(payerMoney);
		this.owner.setMoney(payeeMoney);
	}
	
	public void buyProperty(Player player) {
    	
		System.out.println("This property costs: $" + this.price);
		System.out.println("You currently have: $" + player.getMoney());
		if(this.price > player.getMoney()) {
			System.out.println("You do not have enough money to purchase this property. ");
		}
		else {
			Scanner in = new Scanner(System.in);
			boolean invalid = true;
			while(invalid) {
				System.out.println("Would you like to buy this property? Enter Y or N");
				String answer = in.nextLine();
				if(answer.equalsIgnoreCase("Y")) {
					System.out.println("You have bought " + this.name + ".");
					player.addProperty(this);
					int newBalance = player.getMoney() - this.price;
					player.setMoney(newBalance);
					this.owner = player;
					System.out.println("You now have: $" + newBalance);
					invalid = false;
				}
				else if(answer.equalsIgnoreCase("N")) {
					System.out.println("Your turn is now over.");
					invalid = false;
				}
				else {
					System.out.println("\nPlease enter Y or N");
				}
			}
			in.close();
		}
	
}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}