import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDatabaseOperations {

    private Connection conn;

    public GameDatabaseOperations(Connection connection) {
        this.conn = connection;
    }

    // Method to insert a new player into the database
    public void insertPlayer(String playerName, int money, int position) throws SQLException {
        String sqlInsert = "INSERT INTO players (name, money, position) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, money);
            pstmt.setInt(3, position);
            
            int countInserted = pstmt.executeUpdate();
            System.out.println(countInserted + " records inserted for player: " + playerName);
        }
    }

    // Method to update player's money
    public void updatePlayerMoney(String playerName, int newMoney) throws SQLException {
        String sqlUpdate = "UPDATE players SET money = ? WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setInt(1, newMoney);
            pstmt.setString(2, playerName);

            int countUpdated = pstmt.executeUpdate();
            System.out.println(countUpdated + " records updated for player: " + playerName);
        }
    }

    // Method to update player's position
    public void updatePlayerPosition(String playerName, int newPosition) throws SQLException {
        String sqlUpdate = "UPDATE players SET position = ? WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setInt(1, newPosition);
            pstmt.setString(2, playerName);

            int countUpdated = pstmt.executeUpdate();
            System.out.println(countUpdated + " records updated for player: " + playerName);
        }
    }

    // Add other necessary methods to save game state

//I can do this
}
