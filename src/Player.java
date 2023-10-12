
/**
 * Represents a player in the Monopoly game, including their name, money, and position.
 */
public class Player {
    private String name;       // Name of the player
    private int money;         // Amount of money the player has
    private int position;      // Position of the player on the board

    /**
     * Constructs a new player with the given name and initializes starting money and position.
     * @param name Name of the player.
     */
    public Player(String name) {
        this.setName(name);
        this.setMoney(1500); // starting amount in Monopoly
        this.position = 0;
    }

    /**
     * Simulates the rolling of two six-sided dice.
     * @return The combined result of the two dice rolls.
     */
    public int rollDice() {
        int dice1 = (int) (Math.random() * 6) + 1; // Simulates the roll of the first die
        int dice2 = (int) (Math.random() * 6) + 1; // Simulates the roll of the second die
        return dice1 + dice2; // Returns the sum of the two dice rolls
    }

    /**
     * Moves the player around the board based on the result of a dice roll. 
     * This method wraps around the board if the player's movement exceeds its size.
     * It also triggers any actions associated with the spot they land on.
     * @param board The game board on which the player moves.
     * @return The total value of the dice roll which determined the move.
     */
    public int move(Board board) {
        int roll = rollDice(); // Gets the combined dice roll result
        position = (position + roll) % Board.getSize(); // Updates player's position considering board wrapping
        Spot currentSpot = board.getSpot(position); // Retrieves the spot player landed on
        currentSpot.onABoardSpot(this); // Triggers spot-specific actions
        return roll;  // Returns the dice roll value
    }

    // Getters and setters
    
    /**
     * Returns the name of the player.
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     * @param name The name to set for the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the current amount of money the player has.
     * @return The current money of the player.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the amount of money for the player.
     * @param money The amount to set for the player's money.
     */
    public void setMoney(int money) {
        this.money = money;
    }
}
