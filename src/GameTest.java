import java.util.Scanner;

/**
 * This class serves as a simulation test driver for the Monopoly game.
 */
public class GameTest {
    
    /**
     * The main entry point to simulate and visualize a Monopoly game scenario.
     * @param args Command line arguments (currently not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Monopoly!");
        System.out.print("Please enter your name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);
        Board board = new Board(); // Initializes the Monopoly board
        
        boolean gameOver = false;

        System.out.println(player.getName() + " starts the game!");
        
        while (!gameOver) {
            System.out.println("\n" + player.getName() + ", it's your turn.");
            System.out.println("Choose an option:");
            System.out.println("1. Roll Dice");
            System.out.println("2. Exit");

            int choice = scanner.nextInt();
           
        switch (choice) {
        case 1:
            int roll = player.move(board); // Simulates player's move on the board
            System.out.println(player.getName() + " rolled a total of " + roll); // Displays the result of the dice roll
            break;
        case 2:
            System.out.println("Goodbye!");
            gameOver = true;
            break;
        default:
            System.out.println("Invalid choice. Please choose again.");
        }
    }
    scanner.close();
   }
}
