
/**
 * Represents a property spot on the Monopoly board.
 */
public class Property implements Spot {
    private String name;
    private int price;

    /**
     * Constructs a new Property with the specified name and price.
     * 
     * @param name  The name of the property.
     * @param price The purchase price of the property.
     */
    public Property(String name, int price) {
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
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}