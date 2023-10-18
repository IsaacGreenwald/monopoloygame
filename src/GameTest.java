import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GameTest {
	
    public static void main(String[] args) throws SQLException {
        // Establish a database connection
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/MonopolyGame", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create an instance of GameDatabaseOperations
        GameDatabaseOperations dbOps = new GameDatabaseOperations(conn);
        // Call the printAllPlayers method to print all player data
        dbOps.printAllPlayers();
        // Create an instance of MonopolyGame and pass dbOps to the constructor
        MonopolyGame game = new MonopolyGame(dbOps);

        // Create a new Board object
        Board board = new Board(null); // Initialize the board
        
        // Start the Monopoly game
        game.entryMenu(board);
    }
}
