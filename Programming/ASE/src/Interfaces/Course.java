package Interfaces;

import javafx.beans.property.*;

import java.util.List;

public class Course {

    private SimpleIntegerProperty courseID = new SimpleIntegerProperty();
    private SimpleIntegerProperty courseSWS = new SimpleIntegerProperty();
    private SimpleStringProperty courseModule = new SimpleStringProperty();
    private SimpleStringProperty courseKind = new SimpleStringProperty();
    private SimpleStringProperty courseTurnus = new SimpleStringProperty();
    private SimpleStringProperty courseTitle = new SimpleStringProperty();
    private SimpleStringProperty courseHyperlink = new SimpleStringProperty();
    private SimpleStringProperty courseMaxParticipants = new SimpleStringProperty();
    private SimpleStringProperty courseExpParticipants = new SimpleStringProperty();
    private SimpleBooleanProperty courseOnlineReg = new SimpleBooleanProperty();
    private SimpleStringProperty courseCredits = new SimpleStringProperty();
    private SimpleBooleanProperty courseExtraCourse = new SimpleBooleanProperty();
    private SimpleBooleanProperty courseFinancing = new SimpleBooleanProperty();
    private SimpleStringProperty courseFinals = new SimpleStringProperty();
    private SimpleStringProperty courseStart = new SimpleStringProperty();
    private SimpleStringProperty courseEnd = new SimpleStringProperty();
    private SimpleStringProperty courseLanguage = new SimpleStringProperty();
    private SimpleStringProperty courseChair = new SimpleStringProperty();
    private SimpleStringProperty courseLecturer = new SimpleStringProperty();
    private SimpleStringProperty courseDay = new SimpleStringProperty();
    private SimpleStringProperty courseStartTime = new SimpleStringProperty();
    private SimpleStringProperty courseEndTime = new SimpleStringProperty();
    private SimpleStringProperty courseCtST = new SimpleStringProperty();
    private SimpleStringProperty courseRota = new SimpleStringProperty();
    private SimpleStringProperty courseParticipants = new SimpleStringProperty();
    private SimpleStringProperty courseRequirements = new SimpleStringProperty();
    private SimpleStringProperty courseCertificate = new SimpleStringProperty();
    private SimpleStringProperty courseDeputat = new SimpleStringProperty();
    private SimpleStringProperty courseDescription = new SimpleStringProperty();
    private SimpleStringProperty courseCanceled = new SimpleStringProperty();

    public Course(Integer courseID, Integer courseSWS, String courseModule, String courseKind, String courseTitle,
                  String courseChair /*, String courseHyperlink, String courseMaxParticipants,
                  Boolean courseOnlineReg, String courseTurnus, String courseExpParticipants, String courseCredits, String courseFinals,
                  Boolean courseExtraCourse, Boolean courseFinancing, String courseStart, String courseEnd,
                  String courseLanguage, String courseLecturer, String courseDay, String courseStartTime,
                  String courseEndTime, String courseCtSt, String courseRota, String courseParticipants,
                  String courseCertificate, String courseRequirements, String courseDeputat, String courseDescription,
                  String courseCanceled*/) {

        this.courseID = new SimpleIntegerProperty(courseID);
        this.courseSWS = new SimpleIntegerProperty(courseSWS);
        this.courseModule = new SimpleStringProperty(courseModule);
        this.courseKind = new SimpleStringProperty(courseKind);
        this.courseTitle = new SimpleStringProperty(courseTitle);
        this.courseChair = new SimpleStringProperty(courseChair);
        /*this.courseTurnus = new SimpleStringProperty(courseTurnus);
        this.courseHyperlink = new SimpleStringProperty(courseHyperlink);
        this.courseMaxParticipants = new SimpleStringProperty(courseMaxParticipants);
        this.courseExpParticipants = new SimpleStringProperty(courseExpParticipants);
        this.courseOnlineReg = new SimpleBooleanProperty(courseOnlineReg);
        this.courseCredits = new SimpleStringProperty(courseCredits);
        this.courseExtraCourse = new SimpleBooleanProperty(courseExtraCourse);
        this.courseFinancing = new SimpleBooleanProperty(courseFinancing);
        this.courseFinals = new SimpleStringProperty(courseFinals);
        this.courseStart = new SimpleStringProperty(courseStart);
        this.courseEnd = new SimpleStringProperty(courseEnd);
        this.courseLanguage = new SimpleStringProperty(courseLanguage);
        this.courseLecturer = new SimpleStringProperty(courseLecturer);
        this.courseDay = new SimpleStringProperty(courseDay);
        this.courseStartTime = new SimpleStringProperty(courseStartTime);
        this.courseEndTime = new SimpleStringProperty(courseEndTime);
        this.courseCtST = new SimpleStringProperty(courseCtSt);
        this.courseRota = new SimpleStringProperty(courseRota);
        this.courseParticipants = new SimpleStringProperty(courseParticipants);
        this.courseCertificate = new SimpleStringProperty(courseCertificate);
        this.courseRequirements = new SimpleStringProperty(courseRequirements);
        this.courseDeputat = new SimpleStringProperty(courseDeputat);
        this.courseDescription = new SimpleStringProperty(courseDescription);
        this.courseCanceled = new SimpleStringProperty(courseCanceled);*/

    }

    public int getCourseID() {
        return courseID.get();
    }

    public SimpleIntegerProperty courseIDProperty() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID.set(courseID);
    }

    public int getCourseSWS() {
        return courseSWS.get();
    }

    public SimpleIntegerProperty courseSWSProperty() {
        return courseSWS;
    }

    public void setCourseSWS(int courseSWS) {
        this.courseSWS.set(courseSWS);
    }

    public String getCourseModule() {
        return courseModule.get();
    }

    public SimpleStringProperty courseModuleProperty() {
        return courseModule;
    }

    public void setCourseModule(String courseModule) {
        this.courseModule.set(courseModule);
    }

    public String getCourseKind() {
        return courseKind.get();
    }

    public SimpleStringProperty courseKindProperty() {
        return courseKind;
    }

    public void setCourseKind(String courseKind) {
        this.courseKind.set(courseKind);
    }

    public String getCourseTurnus() {
        return courseTurnus.get();
    }

    public SimpleStringProperty courseTurnusProperty() {
        return courseTurnus;
    }

    public void setCourseTurnus(String courseTurnus) {
        this.courseTurnus.set(courseTurnus);
    }

    public String getCourseTitle() {
        return courseTitle.get();
    }

    public SimpleStringProperty courseTitleProperty() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle.set(courseTitle);
    }

    public String getCourseHyperlink() {
        return courseHyperlink.get();
    }

    public SimpleStringProperty courseHyperlinkProperty() {
        return courseHyperlink;
    }

    public void setCourseHyperlink(String courseHyperlink) {
        this.courseHyperlink.set(courseHyperlink);
    }

    public String getCourseMaxParticipants() {
        return courseMaxParticipants.get();
    }

    public SimpleStringProperty courseMaxParticipantsProperty() {
        return courseMaxParticipants;
    }

    public void setCourseMaxParticipants(String courseMaxParticipants) {
        this.courseMaxParticipants.set(courseMaxParticipants);
    }

    public String getCourseExpParticipants() {
        return courseExpParticipants.get();
    }

    public SimpleStringProperty courseExpParticipantsProperty() {
        return courseExpParticipants;
    }

    public void setCourseExpParticipants(String courseExpParticipants) {
        this.courseExpParticipants.set(courseExpParticipants);
    }

    public boolean isCourseOnlineReg() {
        return courseOnlineReg.get();
    }

    public SimpleBooleanProperty courseOnlineRegProperty() {
        return courseOnlineReg;
    }

    public void setCourseOnlineReg(boolean courseOnlineReg) {
        this.courseOnlineReg.set(courseOnlineReg);
    }

    public String getCourseCredits() {
        return courseCredits.get();
    }

    public SimpleStringProperty courseCreditsProperty() {
        return courseCredits;
    }

    public void setCourseCredits(String courseCredits) {
        this.courseCredits.set(courseCredits);
    }

    public boolean isCourseExtraCourse() {
        return courseExtraCourse.get();
    }

    public SimpleBooleanProperty courseExtraCourseProperty() {
        return courseExtraCourse;
    }

    public void setCourseExtraCourse(boolean courseExtraCourse) {
        this.courseExtraCourse.set(courseExtraCourse);
    }

    public boolean isCourseFinancing() {
        return courseFinancing.get();
    }

    public SimpleBooleanProperty courseFinancingProperty() {
        return courseFinancing;
    }

    public void setCourseFinancing(boolean courseFinancing) {
        this.courseFinancing.set(courseFinancing);
    }

    public String getCourseFinals() {
        return courseFinals.get();
    }

    public SimpleStringProperty courseFinalsProperty() {
        return courseFinals;
    }

    public void setCourseFinals(String courseFinals) {
        this.courseFinals.set(courseFinals);
    }

    public String getCourseStart() {
        return courseStart.get();
    }

    public SimpleStringProperty courseStartProperty() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart.set(courseStart);
    }

    public String getCourseEnd() {
        return courseEnd.get();
    }

    public SimpleStringProperty courseEndProperty() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd.set(courseEnd);
    }

    public String getCourseLanguage() {
        return courseLanguage.get();
    }

    public SimpleStringProperty courseLanguageProperty() {
        return courseLanguage;
    }

    public void setCourseLanguage(String courseLanguage) {
        this.courseLanguage.set(courseLanguage);
    }

    public String getCourseChair() {
        return courseChair.get();
    }

    public SimpleStringProperty courseChairProperty() {
        return courseChair;
    }

    public void setCourseChair(String courseChair) {
        this.courseChair.set(courseChair);
    }

    public String getCourseLecturer() {
        return courseLecturer.get();
    }

    public SimpleStringProperty courseLecturerProperty() {
        return courseLecturer;
    }

    public void setCourseLecturer(String courseLecturer) {
        this.courseLecturer.set(courseLecturer);
    }

    public String getCourseDay() {
        return courseDay.get();
    }

    public SimpleStringProperty courseDayProperty() {
        return courseDay;
    }

    public void setCourseDay(String courseDay) {
        this.courseDay.set(courseDay);
    }

    public String getCourseStartTime() {
        return courseStartTime.get();
    }

    public SimpleStringProperty courseStartTimeProperty() {
        return courseStartTime;
    }

    public void setCourseStartTime(String courseStartTime) {
        this.courseStartTime.set(courseStartTime);
    }

    public String getCourseEndTime() {
        return courseEndTime.get();
    }

    public SimpleStringProperty courseEndTimeProperty() {
        return courseEndTime;
    }

    public void setCourseEndTime(String courseEndTime) {
        this.courseEndTime.set(courseEndTime);
    }

    public String getCourseCtST() {
        return courseCtST.get();
    }

    public SimpleStringProperty courseCtSTProperty() {
        return courseCtST;
    }

    public void setCourseCtST(String courseCtST) {
        this.courseCtST.set(courseCtST);
    }

    public String getCourseRota() {
        return courseRota.get();
    }

    public SimpleStringProperty courseRotaProperty() {
        return courseRota;
    }

    public void setCourseRota(String courseRota) {
        this.courseRota.set(courseRota);
    }

    public String getCourseParticipants() {
        return courseParticipants.get();
    }

    public SimpleStringProperty courseParticipantsProperty() {
        return courseParticipants;
    }

    public void setCourseParticipants(String courseParticipants) {
        this.courseParticipants.set(courseParticipants);
    }

    public String getCourseRequirements() {
        return courseRequirements.get();
    }

    public SimpleStringProperty courseRequirementsProperty() {
        return courseRequirements;
    }

    public void setCourseRequirements(String courseRequirements) {
        this.courseRequirements.set(courseRequirements);
    }

    public String getCourseCertificate() {
        return courseCertificate.get();
    }

    public SimpleStringProperty courseCertificateProperty() {
        return courseCertificate;
    }

    public void setCourseCertificate(String courseCertificate) {
        this.courseCertificate.set(courseCertificate);
    }

    public String getCourseDeputat() {
        return courseDeputat.get();
    }

    public SimpleStringProperty courseDeputatProperty() {
        return courseDeputat;
    }

    public void setCourseDeputat(String courseDeputat) {
        this.courseDeputat.set(courseDeputat);
    }

    public String getCourseDescription() {
        return courseDescription.get();
    }

    public SimpleStringProperty courseDescriptionProperty() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription.set(courseDescription);
    }

    public String getCourseCanceled() {
        return courseCanceled.get();
    }

    public SimpleStringProperty courseCanceledProperty() {
        return courseCanceled;
    }

    public void setCourseCanceled(String courseCanceled) {
        this.courseCanceled.set(courseCanceled);
    }

}

