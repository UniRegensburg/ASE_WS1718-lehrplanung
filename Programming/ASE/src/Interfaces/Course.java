package Interfaces;

import javafx.beans.property.*;

import javax.lang.model.util.SimpleElementVisitor6;
import java.net.SocketImpl;

public class Course {

    private SimpleIntegerProperty courseID = new SimpleIntegerProperty();


    private SimpleIntegerProperty courseNumber = new SimpleIntegerProperty();
    private SimpleStringProperty courseTitle = new SimpleStringProperty();
    private SimpleStringProperty courseKind = new SimpleStringProperty();
    private SimpleIntegerProperty courseSWS = new SimpleIntegerProperty();
    /*private SimpleStringProperty courseHyperlink = new SimpleStringProperty();
    private SimpleStringProperty coursseMaxParticipants = new SimpleStringProperty();
    private SimpleStringProperty courseExpParticipants = new SimpleStringProperty();
    private SimpleBooleanProperty onlineReg = new SimpleBooleanProperty();
    private SimpleStringProperty credits = new SimpleStringProperty();
    private SimpleBooleanProperty extraCourse = new SimpleBooleanProperty();
    private SimpleBooleanProperty financing = new SimpleBooleanProperty();
    private SimpleStringProperty finals = new SimpleStringProperty();
    private SimpleStringProperty start = new SimpleStringProperty();
    private SimpleStringProperty end = new SimpleStringProperty();
    private SimpleStringProperty language = new SimpleStringProperty();
    private SimpleStringProperty chair = new SimpleStringProperty();

    private SimpleStringProperty lecturer = new SimpleStringProperty();

    private SimpleStringProperty day = new SimpleStringProperty();
    private SimpleStringProperty startTime = new SimpleStringProperty();
    private SimpleStringProperty endTime = new SimpleStringProperty();
    private SimpleStringProperty ctST = new SimpleStringProperty();
    private SimpleStringProperty rota = new SimpleStringProperty();
    private SimpleStringProperty participants = new SimpleStringProperty();
    private SimpleStringProperty requirements = new SimpleStringProperty();
    private SimpleStringProperty certificate = new SimpleStringProperty();
    private SimpleStringProperty deputat = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleStringProperty canceled = new SimpleStringProperty();*/

    private SimpleStringProperty courseModule = new SimpleStringProperty();

    private SimpleStringProperty courseChair = new SimpleStringProperty();

    public Course(Integer courseNumber, Integer courseSWS, String courseModule, String courseKind, String courseTitle,
                  String courseChair) {

        this.courseID = new SimpleIntegerProperty(courseNumber);
        this.courseSWS = new SimpleIntegerProperty(courseSWS);
        this.courseModule = new SimpleStringProperty(courseModule);
        this.courseKind = new SimpleStringProperty(courseKind);
        this.courseTitle = new SimpleStringProperty(courseTitle);
        this.courseChair = new SimpleStringProperty(courseChair);

    }

    public Integer getCourseID() {
        return courseNumber.get();
    }

    public Integer getCourseSWS() {
        return courseSWS.get();
    }

    public String getCourseModule() {
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
        courseNumber.set(value);
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
        return courseNumber;
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

