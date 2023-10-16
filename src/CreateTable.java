import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public static final String PORT_NUMBER = "3306";

    public CreateTable() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        try (
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:" + PORT_NUMBER + "/MonopolyGame?user=root&password=root&serverTimezone=UTC"); 
            Statement stmt = conn.createStatement();
        ) {
            // Player table
            String playerSQL = "CREATE TABLE IF NOT EXISTS players (" +
                               "player_id INT AUTO_INCREMENT PRIMARY KEY, " +
                               "name VARCHAR(50), " +
                               "money INT, " +
                               "position INT, " +
                               "in_jail BOOLEAN DEFAULT FALSE" +
                               ")";
            stmt.execute(playerSQL);

            // Board table
            String boardSQL = "CREATE TABLE IF NOT EXISTS board_positions (" +
                              "position_id INT AUTO_INCREMENT PRIMARY KEY, " +
                              "name VARCHAR(50), " +
                              "type VARCHAR(50), " +
                              "player_id INT, " +
                              "FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE SET NULL" +
                              ")";
            stmt.execute(boardSQL);

            // Property table
            String propertySQL = "CREATE TABLE IF NOT EXISTS properties (" +
                                 "property_id INT AUTO_INCREMENT PRIMARY KEY, " +
                                 "name VARCHAR(50), " +
                                 "price INT, " +
                                 "rent INT, " +
                                 "color VARCHAR(20), " +
                                 "owner_id INT, " +
                                 "FOREIGN KEY (owner_id) REFERENCES players(player_id) ON DELETE SET NULL" +
                                 ")";
            stmt.execute(propertySQL);

            // Community Chest cards table
            String communityCardSQL = "CREATE TABLE IF NOT EXISTS community_cards (" +
                                      "card_id INT AUTO_INCREMENT PRIMARY KEY, " +
                                      "description VARCHAR(255), " +
                                      "action VARCHAR(50)" +
                                      ")";
            stmt.execute(communityCardSQL);

            // Chance cards table
            String chanceCardSQL = "CREATE TABLE IF NOT EXISTS chance_cards (" +
                                   "card_id INT AUTO_INCREMENT PRIMARY KEY, " +
                                   "description VARCHAR(255), " +
                                   "action VARCHAR(50)" +
                                   ")";
            stmt.execute(chanceCardSQL);

            // Add other tables as needed for the game state

        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
