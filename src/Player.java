import java.util.ArrayList;

import javafx.scene.image.ImageView;

/**
 * Represents a player in the Monopoly game, including their name, money, and position.
 */
public class Player {
    private String name;           // Name of the player
    private int money;             // Amount of money the player has
    private int position;          // Position of the player on the board
    private boolean inJail;        // Check if player is in Jail
    private int jailTurns;         // Add a field to track the remaining jail turns
    private ArrayList<Property> cards;
    private MonopolyPiece piece;
    private ImageView tokenImageView;


    /**
     * Constructs a new player with the given name and initializes starting money and position.
     *
     * @param name Name of the player.
     */
    public Player(String name) {
        cards = new ArrayList<Property>();
        this.setName(name);
        this.setMoney(1500); // Starting amount in Monopoly
        this.setPosition(0);
        this.inJail = false; // Initialize the inJail flag
        this.jailTurns = 0; // Initialize jailTurns
    }

    /**
     * Simulates the rolling of two six-sided dice.
     *
     * @return The combined result of the two dice rolls.
     */
    public static int rollDice() {
        int dice1 = (int) (Math.random() * 6) + 1; // Simulates the roll of the first die
        int dice2 = (int) (Math.random() * 6) + 1; // Simulates the roll of the second die
        return dice1 + dice2; // Returns the sum of the two dice rolls
    }


    // Method to check if the player is in jail
    public boolean isInJail() {
        return inJail;
    }

    // Method to set the player's jail status
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public int getJailTurns() {
        return jailTurns;
    }

    public void setJailTurns(int jailTurns) {
        this.jailTurns = jailTurns;
    }

    public void addProperty(Property property) {
        cards.add(property);
    }

    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     *
     * @param name The name to set for the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the current amount of money the player has.
     *
     * @return The current money of the player.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the amount of money for the player.
     *
     * @param money The amount to set for the player's money.
     */
    public void setMoney(int money) {
        this.money = money;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

	public ArrayList<Property> getProperties() {
		return this.cards;
	}
	
	public MonopolyPiece getPiece() {
	    return piece;
	}

	public void setPiece(MonopolyPiece piece) {
	    this.piece = piece;
	}
	public ImageView getTokenImageView() {
	    return tokenImageView;
	}

	public void setTokenImageView(ImageView tokenImageView) {
	    this.tokenImageView = tokenImageView;
	}
}
