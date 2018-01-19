package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInterface {

    public DatabaseInterface() {
        selectAllDozenten();

    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/Database/LehrplanungDB.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void selectAllDozenten(){
        String sql = "SELECT Vorname, Nachname, Titel FROM Dozenten";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("Vorname") +  "\t" +
                        rs.getString("Nachname") + "\t" +
                        rs.getString("Titel"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        DatabaseInterface DF = new DatabaseInterface();
    }

}



