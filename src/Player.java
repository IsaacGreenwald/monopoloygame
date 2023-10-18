import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

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
	private int consecutiveDoubles;
    private boolean hasGetOutOfJailFreeCard = false;
	private Window primaryStage;



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
    public int[] rollDice() {
        Random rand = new Random();
        int dice1 = rand.nextInt(6) + 1;
        int dice2 = rand.nextInt(6) + 1;
        return new int[]{ dice1, dice2 };
    }


    /**
     * Moves the player around the board based on the result of a dice roll.
     * This method wraps around the board if the player's movement exceeds its size.
     * It also triggers any actions associated with the spot they land on.
     *
     * @param board The game board on which the player moves.
     * @return The total value of the dice roll which determined the move.
     */
    public int move(Board board, int dice1, int dice2) {
        int roll = dice1 + dice2; 


        if (inJail) {
            if (hasGetOutOfJailFreeCard()) {
            	DialogUtils.promptUser("Do you want to use your 'get out of jail free' card?");
                useGetOutOfJailFreeCard();
                inJail = false; 
            } else if (jailTurns > 0 || dice1 != dice2) {
                jailTurns--;
                DialogUtils.showAlert(getName() + " is still in jail. Skipping turn.");
                return 0; // Player doesn't move on this turn
            } else {
                // Player's jail time is up, let them continue from jail position or they rolled doubles
                inJail = false;
            }
        }

        if (!inJail) {
            int oldPosition = getPosition();
            setPosition((getPosition() + roll) % Board.getSize());

            // Check if the player passes Go and award them money: $200
            if (getPosition() < oldPosition) {                
                setMoney(getMoney() + 200);
                DialogUtils.showAlert(getName() + " passed 'Go' and collected $200!");
            }
            // Check if the player rolled doubles

            if (dice1 == dice2) {
                consecutiveDoubles++;
                if (consecutiveDoubles == 3) {
                    inJail = true;
                    DialogUtils.showAlert(getName() + " rolled doubles three times in a row and is now in jail!");
                    return 0;
                }
                DialogUtils.showAlert(getName() + " rolled doubles! Roll again.");
            } else {
                consecutiveDoubles = 0;
            }
        }
        return roll;
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
	
	public void giveGetOutOfJailFreeCard() {
	    this.hasGetOutOfJailFreeCard = true;
	}
	
	public boolean hasGetOutOfJailFreeCard() {
	    return this.hasGetOutOfJailFreeCard;
	}
	
	public void useGetOutOfJailFreeCard() {
	    if (this.hasGetOutOfJailFreeCard) {
	        this.hasGetOutOfJailFreeCard = false;
	        // Logic to get out of jail
	    }
	    
	    
	}
	public void setPrimaryStage(Window primaryStage) {
	    this.primaryStage = primaryStage;
	}

	public int getConsecutiveDoubles() {
		// TODO Auto-generated method stub
		return consecutiveDoubles;
	}

}
