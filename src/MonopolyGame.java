import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Represents the core Monopoly game logic and handling player setup
 */
public class MonopolyGame {
    /**
     * Runs the Monopoly game, including player setup and game loop
     */
	public void run() {
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("Welcome to Monopoly!");

	    // Get player's name and chosen piece
	    String playerName = getPlayerName(scanner);
	    MonopolyPiece chosenPiece = getChosenPiece(scanner);

	    Player player = new Player(playerName + " (" + chosenPiece.getPieceName() + ")");
	    Board board = new Board();

	    boolean gameOver = false;

	    while (!gameOver) {
	        System.out.println("\n" + player.getName() + ", it's your turn.");
	        System.out.println("Choose an option:");
	        System.out.println("1. Roll Dice");
	        System.out.println("2. Mortgage/Unmortgage Property"); 
	        System.out.println("3. Exit");

	        int choice = -1; // Initialize with invalid choice
	        if(scanner.hasNextInt()) {
	            choice = scanner.nextInt();
	        }
	        scanner.nextLine();  // Consume the leftover newline character.

	        switch (choice) {
	            case 1:
	                int roll = player.move(board);
	                System.out.println(player.getName() + " rolled a total of " + roll);
	                break;
	            case 2:
	                mortgageOrUnmortgageProperty(player);
	                break;
	            case 3:
	                System.out.println("Goodbye!");
	                gameOver = true;
	                break;
	            default:
	                System.out.println("Invalid choice. Please choose again.");
	        }
	    }
	}

 
   
    /**
     * Represents a method to mortgage or unmortgage a property for a player in the Monopoly game.
     * @param player - represents the main player of the game. 
     */
    private void mortgageOrUnmortgageProperty(Player player) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a property to mortgage/unmortgage:");
        ArrayList<Property> properties = player.getProperties();

        // show on terminal a list of the player's properties and their mortgage status
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            String status = property.isMortgaged() ? " (Mortgaged)" : "";
            System.out.println((i + 1) + ". " + property.getName() + status);
        }

        int choice = scanner.nextInt();

        // Check if the user's choice is within the valid range
        if (choice >= 1 && choice <= properties.size()) {
            Property selectedProperty = properties.get(choice - 1);

            // Check if the selected property is mortgaged or not then mortgage/unmortgage 
            if (selectedProperty.isMortgaged()) {
                selectedProperty.unmortgage();
            } else {
                selectedProperty.mortgage();
            }
        } else {
            System.out.println("Invalid choice. Please choose a valid property.");
        }
    }

    private String getPlayerName(Scanner scanner) {
        String name;
        do {
            System.out.print("Please enter your name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
            }
        } while (name.isEmpty());
        return name;
    }

    private MonopolyPiece getChosenPiece(Scanner scanner) {
        int pieceChoice = -1;
        while (pieceChoice < 1 || pieceChoice > MonopolyPiece.values().length) {
            System.out.println("Choose your Monopoly piece:");
            for (int i = 0; i < MonopolyPiece.values().length; i++) {
                System.out.println((i + 1) + ". " + MonopolyPiece.values()[i].getPieceName());
            }
            try {
                pieceChoice = scanner.nextInt();
                if (pieceChoice < 1 || pieceChoice > MonopolyPiece.values().length) {
                    System.out.println("Invalid choice. Please select a valid piece.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next();  // consume the invalid input
            }
        }
        return MonopolyPiece.values()[pieceChoice - 1];  // Assuming MonopolyPiece.getPiece is not available
    }

}
