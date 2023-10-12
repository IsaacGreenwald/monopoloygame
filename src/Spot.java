
/**
 * Represents a generic spot on the Monopoly board. 
 * Every spot on the board, whether it's a property or an action spot, 
 * will implement this interface.
 */
public interface Spot {

    /**
     * Gets the name of the spot.
     * 
     * @return The name of the spot.
     */
    String getName();

    /**
     * Defines the action that occurs when a player lands on this spot.
     * 
     * @param player The player who landed on the spot.
     */
    public void onABoardSpot(Player player);
    
}