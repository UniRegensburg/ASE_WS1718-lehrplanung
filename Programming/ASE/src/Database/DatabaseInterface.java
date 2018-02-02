package Database;


import java.net.CookieHandler;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Interfaces.Lecturer;
import Interfaces.Course;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DatabaseInterface {

    Connection conn;

    public DatabaseInterface() {

        conn = connect();

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

            ObservableList<Lecturer> data = FXCollections.observableArrayList();

            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM Dozenten");
            while (rs.next()) {

                data.add(new Lecturer(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
            return data;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List GetProgramms(){
        List<String> Programms = new ArrayList<String>();
        try{
            ResultSet rs = conn.createStatement().executeQuery("SELECT Titel FROM Studiengang");
            while (rs.next()){
                Programms.add(rs.getString(1));
            }
            return Programms;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<Course> GetCourses(){
        try {

            ObservableList<Course> data = FXCollections.observableArrayList();

            ResultSet rs=conn.createStatement().executeQuery("SELECT ID, SWS, Modul, Art, Titel, Lehrstuhl " +
                    "FROM Kurse");
            while (rs.next()) {

                data.add(new Course(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6)));
            }
            return data;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeLecturers(String name, String surname, String title, Integer deputat) {
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO Dozenten(Nachname,Vorname,Titel,Deputat)VALUES('"+name+"'," +
                    "'"+surname+"','"+title+"','"+deputat+"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteLecturers(Integer ID){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM Dozenten WHERE ID = '"+ID+"'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLecturers(Integer ID, String name, String surname, String title, Integer deputat){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE Dozenten SET Nachname ='"+name+"', Vorname ='"+surname+"', Titel ='"+title+"', Deputat ='"+deputat+"' WHERE ID = '"+ID+"'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



