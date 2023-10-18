import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    // Method to save the entire game state to the database
    public void saveGame(String playerName, int money, int position, Board board) throws SQLException {
        try {
            conn.setAutoCommit(false); // Disable auto-commit

            // Update player information
            updatePlayerMoney(playerName, money);
            updatePlayerPosition(playerName, position);

            // Save the board state
            // You need to define how you want to save the board state in the database
            // This may involve updating the board_positions table

            conn.commit(); // Commit the changes
        } catch (SQLException e) {
            conn.rollback(); // Rollback the changes in case of an error
            throw e;
        } finally {
            conn.setAutoCommit(true); // Re-enable auto-commit
        }
    }
    public Player loadGame(String playerName) {
        Player player = null;
        String sqlSelect = "SELECT name, money FROM players WHERE name = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            pstmt.setString(1, playerName); // Set the player's name as a parameter

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                // Retrieve the player's data from the result set
                String name = resultSet.getString("name");
                int money = resultSet.getInt("money");
                
                System.out.println("Money retrieved: " + money); // Print the money amount
                // Create a Player object using the retrieved data
                player = new Player(name);
                player.setMoney(money);                //player.setMoney(money);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return player;
    }
    
    public void printAllPlayers() {
        String sqlSelect = "SELECT * FROM players"; // Retrieve all columns

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                // Retrieve the player's data from the result set
                String name = resultSet.getString("name");
                int money = resultSet.getInt("money");
                int position = resultSet.getInt("position");
                boolean inJail = resultSet.getBoolean("in_jail");

                System.out.println("Name: " + name);
                System.out.println("Money: " + money);
                System.out.println("Position: " + position);
                System.out.println("In Jail: " + inJail);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public Player loadFirstPlayer() {
        Player player = null;
        String sqlSelect = "SELECT * FROM players LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                // Get the player's information from the database
                String name = resultSet.getString("name");
                int money = resultSet.getInt("money");
                int position = resultSet.getInt("position");
                
                // Create a Player object with the retrieved data
                player = new Player(name);
                player.setMoney(money);
                // add position and stuff later
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }
}
