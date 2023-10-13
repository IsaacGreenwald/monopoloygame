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
