import java.util.Scanner;

/**
 * This class serves as a simulation test driver for the Monopoly game
 * Also prompts user for certain inputs, handles turn menu 
 * Will separate into more files later on
 */
public class GameTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Monopoly!");
        System.out.print("Please enter your name: ");
        String playerName = scanner.nextLine();

        // Display the Monopoly piece options
        System.out.println("Choose your Monopoly piece:");
        for (int i = 0; i < MonopolyPiece.values().length; i++) {
            System.out.println((i + 1) + ". " + MonopolyPiece.values()[i].getPieceName());
        }

        int pieceChoice = scanner.nextInt();
        MonopolyPiece chosenPiece = MonopolyPiece.getPiece(pieceChoice);

        Player player = new Player(playerName + " (" + chosenPiece.getPieceName() + ")");
        Board board = new Board();

        boolean gameOver = false;

        while (!gameOver) {
            System.out.println("\n" + player.getName() + ", it's your turn.");
            System.out.println("Choose an option:");
            System.out.println("1. Roll Dice");
            System.out.println("2. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    int roll = player.move(board);
                    System.out.println(player.getName() + " rolled a total of " + roll);
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
/**
 * Enumeration representing Monopoly pieces
 */
enum MonopolyPiece {
    HAT("Top Hat"),
    CAR("Lambo"),
    DOG("Duck"),
    THIMBLE("Thimble"),
    SHOE("Penguin"),
    SHIP("Ship"),
    WHEELBARROW("Plane"),
    IRON("Iron");

    private final String pieceName;
    
    /**
     * Creates a new Monopoly piece with the given name.
     *
     * @param pieceName The name of the Monopoly piece.
     */
    MonopolyPiece(String pieceName) {
        this.pieceName = pieceName;
    }

    public String getPieceName() {
        return pieceName;
    }
    
    public static MonopolyPiece getPiece(int choice) {
        return values()[choice - 1];
    }
}
