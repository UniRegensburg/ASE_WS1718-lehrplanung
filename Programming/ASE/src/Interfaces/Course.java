package Interfaces;

import javafx.beans.property.*;

import java.util.List;

public class Course {

    private SimpleIntegerProperty courseID = new SimpleIntegerProperty();
    private SimpleIntegerProperty courseSWS = new SimpleIntegerProperty();
    private SimpleStringProperty courseModule = new SimpleStringProperty();
    private SimpleStringProperty courseKind = new SimpleStringProperty();
    private SimpleStringProperty courseTitle = new SimpleStringProperty();
    private SimpleStringProperty courseChair = new SimpleStringProperty();

    public Course(Integer courseID, Integer courseSWS, String courseModule, String courseKind, String courseTitle,
                  String courseChair) {

        this.courseID = new SimpleIntegerProperty(courseID);
        this.courseSWS = new SimpleIntegerProperty(courseSWS);
        this.courseModule = new SimpleStringProperty(courseModule);
        this.courseKind = new SimpleStringProperty(courseKind);
        this.courseTitle = new SimpleStringProperty(courseTitle);
        this.courseChair = new SimpleStringProperty(courseChair);

    }

    public Integer getCourseID() {
        return courseID.get();
    }

    public Integer getCourseSWS() {
        return courseSWS.get();
    }

    public String getcourseModule() {
        return courseModule.get();
    }

    public String getCourseKind() {
        return courseKind.get();
    }

    public String getCourseTitle() {
        return courseTitle.get();
    }

    public String getCourseChair() {
        return courseChair.get();
    }



    public void setCourseID(Integer value){
        courseID.set(value);
    }

    public void setCourseSWS(Integer value){
        courseSWS.set(value);
    }

    public void setCourseModule(String value){
        courseModule.set(value);
    }

    public void setCourseKind(String value){
        courseKind.set(value);
    }

    public void setCourseTitle(String value){
        courseTitle.set(value);
    }

    public void setCourseChair(String value){
        courseChair.set(value);
    }



    public IntegerProperty IDProperty() {
        return courseID;
    }

    public IntegerProperty SWSProperty() {
        return courseSWS;
    }

    public StringProperty moduleProperty() {
        return courseModule;
    }

    public StringProperty kindProperty() {
        return courseKind;
    }

    public StringProperty titleProperty() {
        return courseTitle;
    }

    public StringProperty chairProperty() {
        return courseChair;
    }



}

