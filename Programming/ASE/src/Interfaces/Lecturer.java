package Interfaces;

import javafx.beans.property.*;

import java.util.List;

public class Lecturer {

    private SimpleStringProperty LecturerName = new SimpleStringProperty();
    private SimpleStringProperty LecturerSurname = new SimpleStringProperty();
    private SimpleStringProperty LecturerTitle = new SimpleStringProperty();
    private SimpleIntegerProperty LecturerDeputat = new SimpleIntegerProperty();

    public Lecturer(String LecturerName, String LecturerSurname, String LecturerTitle, Integer LecturerDeputat) {

        this.LecturerName = new SimpleStringProperty(LecturerName);
        this.LecturerSurname = new SimpleStringProperty(LecturerSurname);
        this.LecturerTitle = new SimpleStringProperty(LecturerTitle);
        this.LecturerDeputat = new SimpleIntegerProperty(LecturerDeputat);

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

}

