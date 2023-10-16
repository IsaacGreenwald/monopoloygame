import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class demonstrates how to create a database using the program. This will not throw an error 
 * if the database already exists as it checks for the database's existence before attempting to create it.
 */
public class CreateDatabase {

    public static final String PORT_NUMBER = "3306";
    
    public CreateDatabase() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        try (
                // Step 1: Allocate a database "Connection" object
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:" + PORT_NUMBER + "/?serverTimezone=UTC", 
                        "root", "root"); // MySQL
                // Step 2: Allocate a "Statement" object in the Connection
                Statement stmt = conn.createStatement();
                ) {
            // Step 3 - create our database only if it doesn't exist
            String sql = "CREATE DATABASE IF NOT EXISTS MonopolyGame";
            stmt.execute(sql);

            System.out.println("Database MonopolyGame created successfully!");

        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
