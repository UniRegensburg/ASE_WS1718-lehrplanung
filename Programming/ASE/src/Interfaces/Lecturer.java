package Interfaces;

import javafx.beans.property.*;

import java.util.List;

// Class for creating Lecturer objects
// Provides getter / setter methods for Lecturer Objects

public class Lecturer {

    private SimpleIntegerProperty LecturerID = new SimpleIntegerProperty();
    private SimpleStringProperty LecturerName = new SimpleStringProperty();
    private SimpleStringProperty LecturerSurname = new SimpleStringProperty();
    private SimpleStringProperty LecturerTitle = new SimpleStringProperty();
    private SimpleIntegerProperty LecturerDeputat = new SimpleIntegerProperty();
    private SimpleStringProperty LecturerRole = new SimpleStringProperty();


    public Lecturer(Integer LecturerID, String LecturerSurname, String LecturerName, String LecturerTitle,
                    Integer LecturerDeputat, String LecturerRole) {

        this.LecturerName = new SimpleStringProperty(LecturerName);
        this.LecturerSurname = new SimpleStringProperty(LecturerSurname);
        this.LecturerTitle = new SimpleStringProperty(LecturerTitle);
        this.LecturerDeputat = new SimpleIntegerProperty(LecturerDeputat);
        this.LecturerID = new SimpleIntegerProperty(LecturerID);
        this.LecturerRole = new SimpleStringProperty(LecturerRole);

    }

    public String getLecturerName() {
        return LecturerName.get();
    }

    public String getLecturerSurname() {
        return LecturerSurname.get();
    }

    public String getLecturerTitle() {
        return LecturerTitle.get();
    }

    public Integer getLecturerDeputat() {
        return LecturerDeputat.get();
    }

    public Integer getLecturerID() {
        return LecturerID.get();
    }

    public String getLecturerRole() {
        return LecturerRole.get();
    }


    public void setLecturerName(String value){
        LecturerName.set(value);
    }

    public void setLecturerSurname(String value){
        LecturerSurname.set(value);
    }

    public void setLecturerTitle(String value){
        LecturerTitle.set(value);
    }

    public void setLecturerDeputat(Integer value){
        LecturerDeputat.set(value);
    }

    public void setLecturerID(Integer value){
        LecturerID.set(value);
    }

    public void setLecturerRole(String lecturerRole) {
        this.LecturerRole.set(lecturerRole);
    }


    public StringProperty nameProperty() {
        return LecturerName;
    }

    public StringProperty surnameProperty() {
        return LecturerSurname;
    }

    public StringProperty titleProperty() {
        return LecturerTitle;
    }

    public IntegerProperty deputatProperty() {
        return LecturerDeputat;
    }

    public IntegerProperty IDProperty() {
        return LecturerID;
    }

    public SimpleStringProperty lecturerRoleProperty() {
        return LecturerRole;
    }


}