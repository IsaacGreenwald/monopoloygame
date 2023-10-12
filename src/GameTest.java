/**
 * This class serves as a simulation test driver for the Monopoly game.
 */
public class GameTest {
    
    /**
     * The main entry point to simulate and visualize a Monopoly game scenario.
     * @param args Command line arguments (currently not used).
     */
    public static void main(String[] args) {
        Board board = new Board(); // Initializes the Monopoly board
        Player player = new Player("Garen"); // Creates a player named Garen

        System.out.println(player.getName() + " starts the game!");

        // Simulates a set number of turns for the player to move around the board
        int turns = 10;
        for(int i = 0; i < turns; i++) {
            System.out.println("\nTurn " + (i+1)); // Displays the current turn number
            int roll = player.move(board); // Simulates player's move on the board
            System.out.println(player.getName() + " rolled a total of " + roll); // Displays the result of the dice roll
        }
    }
}
