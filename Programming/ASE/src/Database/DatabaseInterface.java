package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Interfaces.Lecturer;
import Interfaces.Course;
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
                        rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6)));
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

    public List getDayTimes(Integer ID){
        List <String> dayTimes = new ArrayList<String>();
        try{
            ResultSet rs = conn.createStatement().executeQuery("SELECT DayID, startTime, endTime FROM ZuordnungWochentag WHERE CourseID ='"+ID+"'");
            while (rs.next()){
                dayTimes.add(rs.getString(1)+";"+rs.getString(2)+";"+rs.getString(3));
            }
            return dayTimes;
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

    public List getChairs(){
        List <String> chairs = new ArrayList<>();
        try{
            ResultSet rs = conn.createStatement().executeQuery("SELECT Name FROM Lehrstuhl");
            while (rs.next()){
                chairs.add(rs.getString(1));
            }
            return chairs;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List getSemester(){
        List <String> semester = new ArrayList<>();
        try{
            ResultSet rs = conn.createStatement().executeQuery("SELECT Semester FROM Semester");
            while (rs.next()){
                semester.add(rs.getString(1));
            }
            return semester;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getLecturerID(String lecturer){
        int lecturerID = 0;
        String[] Names = lecturer.split(" ");
        try{
            Statement stLecturerID = conn.createStatement();
            ResultSet rsLecuterID = stLecturerID.executeQuery("SELECT ID FROM Dozenten WHERE Vorname = '"+Names[0]+"' AND Nachname = '"+Names[1]+"'");
            while(rsLecuterID.next()){
                lecturerID = rsLecuterID.getInt(1);
            }
            return lecturerID;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getChairID(String chair){
        int chairID = 0;
        try{
            Statement getChair = conn.createStatement();
            ResultSet rsChair = getChair.executeQuery("SELECT ID FROM Lehrstuhl WHERE Name = '"+ chair +"'");
            while(rsChair.next()){
                chairID = rsChair.getInt(1);
            }
            return chairID;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getProgramID(String program){
        int programID = 0;
        try{
            Statement getProgram = conn.createStatement();
            ResultSet rsProgram = getProgram.executeQuery("SELECT ID FROM Studiengang WHERE Titel = '"+ program +"'");
            while(rsProgram.next()){
                programID = rsProgram.getInt(1);
            }
            return programID;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List getFilterSettings(){
        List <String> settings = new ArrayList<>();
        try{
            ResultSet rs = conn.createStatement().executeQuery("SELECT Name, Checked FROM Filter");
            while (rs.next()){
                settings.add(rs.getString(1)+";"+rs.getString(2));
            }
            return settings;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeSemester(String name){
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO Semester(Semester)VALUES('"+name+"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void writeChair(String name){
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO Lehrstuhl(Name)VALUES('"+name+"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void writeProgram(String name){
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO Studiengang(Titel)VALUES('"+name+"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connectCourseWithLecturer(String lecturer, String title){
        int courseID = 0;

        try {
            Statement getCourseID = conn.createStatement();
            ResultSet rsCourseID = getCourseID.executeQuery("SELECT ID FROM Kurse WHERE Titel = '"+ title +"'");
            while(rsCourseID.next()){
                courseID = rsCourseID.getInt(1);
            }

            Statement writeCourseLecturerConnection = conn.createStatement();
            writeCourseLecturerConnection.executeUpdate("INSERT INTO ZuordnungDozenten(KursID,DozentID)VALUES" +
                    "('"+courseID+"','"+getLecturerID(lecturer)+"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connectCourseWithDay(String title, String day, String start, String end){
        int courseID = 0;
        int dayID = 0;

        try {

            Statement getCourseID = conn.createStatement();
            ResultSet rsCourseID = getCourseID.executeQuery("SELECT ID FROM Kurse WHERE Titel = '"+ title +"'");
            while(rsCourseID.next()){
                courseID = rsCourseID.getInt(1);
            }

            Statement getDayID = conn.createStatement();
            ResultSet rsDayID = getDayID.executeQuery("SELECT ID FROM Wochentage WHERE Tag ='"+day+"'");
            while(rsDayID.next()){
                dayID = rsDayID.getInt(1);
            }

            Statement writeCourseDayConnection = conn.createStatement();
            writeCourseDayConnection.executeUpdate("INSERT INTO ZuordnungWochentag(CourseID,DayID,startTime,endTime)VALUES" +
                    "('"+courseID+"','"+dayID+"','"+start+"','"+end+"')");

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Course> GetCourses(){
        try {

            ObservableList<Course> data = FXCollections.observableArrayList();
            ResultSet rs;
            String chairName = "";
            String dozentName = "";
            String programName = "";

            if(programID == 0){
                rs=conn.createStatement().executeQuery("SELECT ID, Titel, Modul, SWS, Art, Lehrstuhl, Pruefungsdatum, Rhythmus, " +
                        "OnlineAnmeldung, MaxTeilnehmer, Deputat, Wahlbereich, Credits, ErwTeilnehmer, Hyperlink, Sprache," +
                        "Finanzierung, Anfangsdatum, Enddatum, Kursnummer, CtSt, Teilnehmer, Anforderung," +
                        "Zertifikat, Beschreibung, Turnus, Studiengang FROM Kurse");
            }
            else{
                rs=conn.createStatement().executeQuery("SELECT ID, Titel, Modul, SWS, Art, Lehrstuhl, Pruefungsdatum, Rhythmus, " +
                        "OnlineAnmeldung, MaxTeilnehmer, Deputat, Wahlbereich, Credits, ErwTeilnehmer, Hyperlink, Sprache," +
                        "Finanzierung, Anfangsdatum, Enddatum, Kursnummer, CtSt, Teilnehmer, Anforderung," +
                        "Zertifikat, Beschreibung, Turnus, Studiengang FROM Kurse WHERE Studiengang ='"+programID+"'");
            }
            while (rs.next()) {

                ResultSet rsGetChairName = conn.createStatement().executeQuery("SELECT Name FROM Lehrstuhl " +
                        "WHERE ID = '"+rs.getInt(6)+"'");
                while(rsGetChairName.next()){
                    chairName = rsGetChairName.getString(1);
                }

                ResultSet rsGetDozentenName = conn.createStatement().executeQuery("SELECT Dozenten.Vorname, " +
                        "Dozenten.Nachname FROM Dozenten INNER JOIN ZuordnungDozenten ON ZuordnungDozenten.DozentID = " +
                        "Dozenten.ID WHERE ZuordnungDozenten.KursID = '"+rs.getInt(1)+"'");
                while(rsGetDozentenName.next()){
                    dozentName = rsGetDozentenName.getString(1)+" "+ rsGetDozentenName.getString(2);
                }

                ResultSet rsGetProgramName = conn.createStatement().executeQuery("SELECT Titel FROM Studiengang " +
                        "WHERE ID ='"+rs.getString(27)+"'");
                while(rsGetProgramName.next()){
                    programName = rsGetProgramName.getString(1);
                }

                data.add(new Course(rs.getInt(1), rs.getInt(4), rs.getString(3),
                        rs.getString(5), rs.getString(2), chairName, rs.getString(15),
                        rs.getString(10), Boolean.parseBoolean(rs.getString(9)), rs.getString(26),
                        rs.getString(14), rs.getString(13), rs.getString(7),
                        Boolean.parseBoolean(rs.getString(12)), Boolean.parseBoolean(rs.getString(17)),
                        rs.getString(18), rs.getString(19), rs.getString(16), dozentName,
                        rs.getString(21), rs.getString(8), rs.getString(22),
                        rs.getString(24), rs.getString(23), rs.getString(11),
                        rs.getString(25), rs.getString(20),programName));
            }
            return data;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeLecturers(String name, String surname, String title, Integer deputat, String role) {
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO Dozenten(Nachname,Vorname,Titel,Deputat,Rolle)VALUES('"+name+"'," +
                    "'"+surname+"','"+title+"','"+deputat+"','"+role+"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void writeCourse(String number, String title, String kind, String SWS, String hyperlink, String maxP,
                            String expP, Boolean online, String credits, Boolean extra, Boolean finance, String finals,
                            String startDate, String endDate, String language, String program, String ct, String rotation,
                            String participants, String requirements, String cert, String deputat, String description,
                            String turnus, String chair)
    {
        try{
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO Kurse(Kursnummer,Titel,Art,SWS,Hyperlink,MaxTeilnehmer,ErwTeilnehmer," +
                    "OnlineAnmeldung,Credits,Wahlbereich,Finanzierung,Pruefungsdatum,Anfangsdatum,Enddatum,Sprache," +
                    "Studiengang,CtSt,Rhythmus,Teilnehmer,Anforderung,Zertifikat,Deputat,Beschreibung,Turnus,Lehrstuhl)" +
                    "VALUES('"+number+"','"+title+"','"+kind+"','"+SWS+"','"+hyperlink+"','"+maxP+"','"+expP+"','"+online+"'," +
                    "'"+credits+"','"+extra+"','"+finance+"','"+finals+"','"+startDate+"','"+endDate+"','"+language+"','" +
                    getProgramID(program)+"'," + "'"+ct+"','"+rotation+"','"+participants+"','"+requirements+"','"+cert+"'," +
                    "'"+deputat+"','"+description+"','"+turnus+"','"+getChairID(chair)+"')");

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

    public void addDeputat(String deputat, String lecturer){
        int currentDeputat = 0;
        int lecturerID = getLecturerID(lecturer);
        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT Deputat FROM Dozenten WHERE ID = '"+lecturerID+"'");
            while(rs.next()){
                currentDeputat = rs.getInt(1);
            }
            currentDeputat += Integer.parseInt(deputat);
            Statement stNewDeputat = conn.createStatement();
            stNewDeputat.executeUpdate("UPDATE Dozenten SET Deputat ='"+currentDeputat+"' WHERE ID ='"+lecturerID+"'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void subDeputat(String deputat, String lecturer){
        int currentDeputat = 0;
        int lecturerID = getLecturerID(lecturer);
        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT Deputat FROM Dozenten WHERE ID = '"+lecturerID+"'");
            while(rs.next()){
                currentDeputat = rs.getInt(1);
            }
            currentDeputat -= Integer.parseInt(deputat);

            Statement stNewDeputat = conn.createStatement();
            stNewDeputat.executeUpdate("UPDATE Dozenten SET Deputat ='"+currentDeputat+"' WHERE ID ='"+lecturerID+"'");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteCourse(Integer ID){

        try {
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM Kurse WHERE ID = '" + ID + "'");
            st.executeUpdate("DELETE FROM ZuordnungDozenten WHERE KursID = '"+ID+"'");
                    st.executeUpdate("DELETE FROM ZuordnungWochentag WHERE CourseID = '"+ID+"'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void updateLecturers(Integer ID, String name, String surname, String title, Integer deputat, String role){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE Dozenten SET Nachname ='"+name+"', Vorname ='"+surname+"', " +
                    "Titel ='"+title+"', Deputat ='"+deputat+"', Rolle ='"+role+"' WHERE ID = '"+ID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCourse(Integer ID, String number, String title, String kind, String SWS, String hyperlink, String maxP,
                             String expP, Boolean online, String credits, Boolean extra, Boolean finance, String finals,
                             String start, String end, String language, String program, String ct, String rotation,
                             String participants, String requirements, String cert, String deputat, String description,
                             String turnus, String chair, String lecturer){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE Kurse SET Kursnummer ='"+number+"', Titel ='"+title+"', Art ='"+kind+"', " +
                    "SWS ='"+SWS+"', Hyperlink ='"+hyperlink+"', MaxTeilnehmer ='"+maxP+"', ErwTeilnehmer ='"+expP+"', " +
                    "OnlineAnmeldung ='"+online+"', Credits ='"+credits+"', Wahlbereich ='"+extra+"', Finanzierung ='"+finance+"', " +
                    "Pruefungsdatum ='"+finals+"', Anfangsdatum ='"+start+"', Enddatum ='"+end+"', Sprache ='"+language+"', " +
                    "CtSt ='"+ct+"', Rhythmus ='"+rotation+"', Teilnehmer ='"+participants+"', Anforderung ='"+requirements+"', " +
                    "Zertifikat ='"+cert+"', Deputat ='"+deputat+"', " + "Beschreibung ='"+description+"', Turnus ='"+turnus+"', " +
                    "Studiengang ='"+getProgramID(program)+"', " + "Lehrstuhl ='"+getChairID(chair)+"' WHERE ID = '"+ID+"'");

            Statement stLecturer = conn.createStatement();
            stLecturer.executeUpdate("UPDATE ZuordnungDozenten SET DozentID ='"+getLecturerID(lecturer)+"' WHERE KursID = '"+ID+"'");
            deleteDayCourseConnection(ID);

        } catch (SQLException e) {
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

    public void updateFilterSettings(String name, Boolean checked){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE Filter SET Checked='"+checked+"' WHERE Name ='"+name+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetFilterSettings(){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE Filter SET Checked='true'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteDayCourseConnection(Integer ID){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM ZuordnungWochentag WHERE CourseID ='"+ID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



