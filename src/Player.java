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

	
    /**
     * Moves the player around the board based on the result of a dice roll.
     * This method wraps around the board if the player's movement exceeds its size.
     * It also triggers any actions associated with the spot they land on.
     *
     * @param board The game board on which the player moves.
     * @return The total value of the dice roll which determined the move.
     */
    public int move(Board board) {
        int roll = 0;

        if (inJail) {
            if (hasGetOutOfJailFreeCard()) {
                System.out.println("Do you want to use your 'get out of jail free' card?");
                useGetOutOfJailFreeCard();
                inJail = false; // Assuming they use the card immediately
            } else if (jailTurns > 0) {
                jailTurns--;
                return 0; // Player doesn't move on this turn
            } else {
                // Player's jail time is up, let them continue from jail position
                inJail = false;
            }
        }

        if (!inJail) { // If the player is not in jail, then handle the movement
            roll = rollDice();
            int oldPosition = getPosition();
            setPosition((getPosition() + roll) % Board.getSize());
            Spot currentSpot = board.getSpot(getPosition());

            // Check if the player passes Go and award them money: $200
            if (getPosition() < oldPosition) {                
                setMoney(getMoney() + 200);
                System.out.println(getName() + " passed 'Go' and collected $200!");
            }

            // Check if the player rolled doubles
            int dice1 = rollDice();
            int dice2 = rollDice();
            if (dice1 == dice2) {
                consecutiveDoubles++;
                if (consecutiveDoubles == 3) {
                    // Player rolled doubles 3 times in a row send them to jail
                    inJail = true;
                    return 0; // Player doesn't move further in this turn
                } else {
                    // Player rolled doubles take another turn
                    System.out.println(getName() + " rolled doubles! Roll again.");
                    return roll; // Return the dice roll value for the current turn
                }
            } else {
                // Reset consecutive doubles counter
                consecutiveDoubles = 0;
            }

            currentSpot.onABoardSpot(this);
        }

        return roll; // Return the roll value or 0 when in jail
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
