package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import Interfaces.Lecturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


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

    public ObservableList<Lecturer> GetDozenten() {
        try {

            Connection conn = connect();
            ObservableList<Lecturer> data = FXCollections.observableArrayList();

            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM Dozenten");
            while (rs.next()) {

                data.add(new Lecturer(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
            return data;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}



