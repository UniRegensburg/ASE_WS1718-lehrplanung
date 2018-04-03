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
    Integer programID;

    public DatabaseInterface() {

        conn = connect();
        programID = 0;

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

    public List GetPrograms(){
        List<String> Programs = new ArrayList<String>();
        try{
            ResultSet rs = conn.createStatement().executeQuery("SELECT Titel FROM Studiengang");
            while (rs.next()){
                Programs.add(rs.getString(1));
            }
            return Programs;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List GetLecturers(){
        List <String> Lecturers = new ArrayList<>();
        try{
            ResultSet rs = conn.createStatement().executeQuery("SELECT Vorname, Nachname FROM Dozenten");
            while (rs.next()){
                Lecturers.add(rs.getString(1)+" "+rs.getString(2));
            }
            return Lecturers;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void connectCourseWithLecturer(String lecturer, String title){
        int courseID = 0;
        int lecturerID = 0;
        String[] Names = lecturer.split(" ");
        try {
            Statement getCourseID = conn.createStatement();
            ResultSet rsCourseID = getCourseID.executeQuery("SELECT ID FROM Kurse WHERE Titel = '"+ title +"'");
            while(rsCourseID.next()){
                courseID = rsCourseID.getInt(1);
            }

            Statement getLecturerID = conn.createStatement();
            ResultSet rsLecuterID = getLecturerID.executeQuery("SELECT ID FROM Dozenten WHERE Vorname = '"+Names[0]+"' AND Nachname = '"+Names[1]+"'");
            while(rsLecuterID.next()){
                lecturerID = rsLecuterID.getInt(1);
            }

            Statement writeCourseLecturerConnection = conn.createStatement();
            writeCourseLecturerConnection.executeUpdate("INSERT INTO ZuordnungDozenten(KursID,DozentID)VALUES('"+courseID+"','"+lecturerID+"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Course> GetCourses(){
        try {

            ObservableList<Course> data = FXCollections.observableArrayList();
            ResultSet rs;

            if(programID == 0){
                rs=conn.createStatement().executeQuery("SELECT ID, SWS, Modul, Art, Titel, Lehrstuhl " +
                        "FROM Kurse");
            }
            else{
                rs=conn.createStatement().executeQuery("SELECT ID, SWS, Modul, Art, Titel, Lehrstuhl " +
                        "FROM Kurse WHERE Studiengang ='"+programID+"'");
            }
            while (rs.next()) {

                data.add(new Course(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6)));
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

    public void writeCourse(String number, String title, String kind, String SWS, String hyperlink, String maxP, String expP, Boolean online, String credits, Boolean extra, Boolean finance, String finals, String start, String end, String language, String chair, String day, String startTime, String endTime, String ct, String rotation, String participants, String requirements, String cert, String deputat, String description, String cancelled)
    {
        try{
            Statement getChair = conn.createStatement();
            int chairID = 0;

            ResultSet rs = getChair.executeQuery("SELECT ID FROM Studiengang WHERE Titel = '"+ chair +"'");
            while(rs.next()){
                chairID = rs.getInt(1);
            }

            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO Kurse(Kursnummer,Titel,Art,SWS,Hyperlink,MaxTeilnehmer,ErwTeilnehmer," +
                    "OnlineAnmeldung,Credits,Wahlbereich,Finanzierung,Pruefungsdatum,Anfangsdatum,Enddatum,Sprache," +
                    "Studiengang,Kursbeginn,Kursende,CtSt,Turnus,Teilnehmer,Anforderung,Zertifikat,Deputat,Beschreibung)" +
                    "VALUES('"+number+"','"+title+"','"+kind+"','"+SWS+"','"+hyperlink+"','"+maxP+"','"+expP+"','"+online+"'," +
                    "'"+credits+"','"+extra+"','"+finance+"','"+finals+"','"+start+"','"+end+"','"+language+"','"+chairID+"'," +
                    "'"+startTime+"','"+endTime+"','"+ct+"','"+rotation+"','"+participants+"','"+requirements+"','"+cert+"'," +
                    "'"+deputat+"','"+description+"')");

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

    public void deleteCourse(Integer ID){

        try {
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM Kurse WHERE ID = '" + ID + "'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateLecturers(Integer ID, String name, String surname, String title, Integer deputat){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE Dozenten SET Nachname ='"+name+"', Vorname ='"+surname+"', " +
                    "Titel ='"+title+"', Deputat ='"+deputat+"' WHERE ID = '"+ID+"'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getSelectedCourses(String program){

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT ID FROM Studiengang WHERE Titel = '"+ program +"'");
            while(rs.next()){
                programID = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}



