package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class DatabaseInterface {

    public DatabaseInterface() {

    }

    public Connection connect() {
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

    public ArrayList<String[]> selectAllDozenten(){
        String sql = "SELECT Vorname, Nachname, Titel FROM Dozenten";
        ArrayList<String[]> result = new ArrayList<String[]>();

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            int columnCount = rs.getMetaData().getColumnCount();

            while(rs.next()) {

                String[] row = new String[columnCount];

                for(int i = 0; i < columnCount; i++) {

                    row[i] = rs.getString(i+1);

                }

                result.add(row);

            }

            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    public static void main(String[] args) {
        DatabaseInterface DF = new DatabaseInterface();
    }

}



