import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GameDatabaseTest {
    
    public static final String PORT_NUMBER = "8889";
    private static Connection connection;
    private static GameDatabaseOperations dbOps;

    public static void setUpConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:" + PORT_NUMBER + "/MonopolyGame?user=root&password=root&serverTimezone=UTC");
            dbOps = new GameDatabaseOperations(connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void testInsertPlayer() {
        try {
            dbOps.insertPlayer("Alice", 1500, 0);
            dbOps.insertPlayer("Bob", 1500, 0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void testUpdatePlayerMoney() {
        try {
            dbOps.updatePlayerMoney("Alice", 1400);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void testUpdatePlayerPosition() {
        try {
            dbOps.updatePlayerPosition("Bob", 5);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        setUpConnection();

        // Create database and tables
        CreateDatabase.main(null);
        CreateTable.main(null);

        // Test operations
        testInsertPlayer();
        testUpdatePlayerMoney();
        testUpdatePlayerPosition();

        closeConnection();
    }
}
