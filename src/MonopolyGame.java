import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Represents the core Monopoly game logic and handling player setup
 */
public class MonopolyGame {
    private GameDatabaseOperations dbOps;
    public MonopolyGame(GameDatabaseOperations dbOps) {
        this.dbOps = dbOps;
    }

    /**
     * Runs the Monopoly game, including player setup and game loop
     * @throws SQLException 
     */
	
	public void EntryMenu(Board board) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		Player player = null; // Initialize player
	    
	    System.out.println("Welcome to Monopoly!");
	    System.out.println("1. New Game");
	    System.out.println("2. Load Game");

	    int choice = scanner.nextInt();

	    if (choice == 1) {
	        // Start a new game
	        run(scanner);
	    } else if (choice == 2) {
	        // Load a saved game
	        loadSavedGame(scanner);
	    } else {
	        System.out.println("Invalid choice. Please choose again.");
	    }
	}
	private Player loadSavedGame(Scanner scanner) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MonopolyGame", "root", "root");

	    // Load the game state from the database
	    GameDatabaseOperations dbOps = new GameDatabaseOperations(conn);
	    Player loadedPlayer = dbOps.loadFirstPlayer();

	    if (loadedPlayer != null) {
	        Board board = new Board(null);
	        System.out.println("Game loaded successfully for " + loadedPlayer.getName() + loadedPlayer.getMoney());
	        continueGameLoop(loadedPlayer, board);
	        return loadedPlayer;
	    } else {
	        System.out.println("No player found in the database. Starting a new game.");
	        run(scanner);
	        return null;
	    }
	    
	}

	/**
	 * Resumes the game loop after loading a saved game.
	 * @param player The player to resume the game with.
	 * @param board The game board to resume.
	 */
	public void continueGameLoop(Player player, Board board) {
	    Scanner scanner = new Scanner(System.in);

	    boolean gameOver = false;

	    while (!gameOver) {
	        System.out.println("\n" + player.getName() + ", it's your turn.");
	        System.out.println("Choose an option:");
	        System.out.println("1. Roll Dice");
	        System.out.println("2. Mortgage/Unmortgage Property"); 
	        System.out.println("3. Save Game");
	        System.out.println("4. Exit");

	        int choice = scanner.nextInt();

	        switch (choice) {
	            case 1:
	            	int[] diceRolls = player.rollDice(); // Assuming rollDice returns an array of two integers, representing the two dice rolls
	            	int totalRoll = diceRolls[0] + diceRolls[1];
	            	int moveValue = player.move(board, diceRolls[0], diceRolls[1]);
	            	System.out.println(player.getName() + " rolled a total of " + totalRoll);

	                break;
	            case 2:
	                mortgageOrUnmortgageProperty(player);
	                break;
	            case 3:
	                try {
	                    dbOps.saveGame(player.getName(), player.getMoney(), player.getPosition(), board);
	                    System.out.println("Game saved successfully!");
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	                break;

	            case 4:
	                System.out.println("Goodbye!");
	                gameOver = true;
	                break;
	        }
	    }
	}


    public void run(Scanner scanner) {
        Scanner scanner1 = new Scanner(System.in);

        System.out.println("Welcome to Monopoly!");

        // Get player's name and chosen piece
        String playerName = getPlayerName(scanner1);
        MonopolyPiece chosenPiece = getChosenPiece(scanner1);

        Player player = new Player(playerName + " (" + chosenPiece.getPieceName() + ")");
        player.setMoney(1500);
        Board board = new Board(null);
        // Insert the new player into the database
        try {
            dbOps.insertPlayer(player.getName(), player.getMoney(), player.getPosition());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean gameOver = false;

        while (!gameOver) {
            System.out.println("\n" + player.getName() + ", it's your turn.");
            System.out.println("Choose an option:");
            System.out.println("1. Roll Dice");
            System.out.println("2. Mortgage/Unmortgage Property"); 
            System.out.println("3. Save Game"); 
            System.out.println("4. Exit");

            int choice = scanner1.nextInt(); 

            switch (choice) {
                case 1:
                	int[] diceRolls = player.rollDice(); // Assuming rollDice returns an array of two integers, representing the two dice rolls
                	int totalRoll = diceRolls[0] + diceRolls[1];
                	int moveValue = player.move(board, diceRolls[0], diceRolls[1]);
                	System.out.println(player.getName() + " rolled a total of " + totalRoll);

                    break;
                case 2:
                    mortgageOrUnmortgageProperty(player);
                    break;
                case 3:                	
                    try {
                        dbOps.updatePlayerMoney(player.getName(), player.getMoney());
                        System.out.println("Player's money updated successfully!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }                   
                    try {
                        dbOps.saveGame(player.getName(), player.getMoney(), player.getPosition(), board);
                        System.out.println("Game saved successfully!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    gameOver = true;
                    break;
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
        System.out.print("Please enter your name: ");
        return scanner.nextLine();
    }

    private MonopolyPiece getChosenPiece(Scanner scanner) {
        System.out.println("Choose your Monopoly piece:");
        for (int i = 0; i < MonopolyPiece.values().length; i++) {
            System.out.println((i + 1) + ". " + MonopolyPiece.values()[i].getPieceName());
        }
        int pieceChoice = scanner.nextInt();
        return MonopolyPiece.getPiece(pieceChoice);
    }
}
