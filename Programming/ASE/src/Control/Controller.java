package Control;

import Interfaces.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import Interfaces.Lecturer;
import Interfaces.Course;
import javafx.scene.control.*;
import Database.DatabaseInterface;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.time.LocalDate;

public class Controller  {

    @FXML
    private Button createLecturerButton, createCourseButton, saveLecturerButton, saveCourseButton, deleteLecturerButton,
            deleteCourseButton, updateLecturerButton, editCourseButton, cancelLecturerButton, cancelCourseButton,
            nextCourseButton, backCourseButton;
    @FXML
    private TextField lecturerNameText, lecturerSurnameText, lecturerTitleText, lecturerDeputatText, courseNumberText,
            courseTitleText, SWScourseText, hypertextCourseText, maxParticipantsCourseText, creditsCourseText,
            expectedParticipantsCourseText, startTimeText, endTimeText, participantsText,
            requirementsText, descriptionText, deputatText, languageCourseText1;
    @FXML
    private BorderPane pane_teachers_overview, pane_single_teacher, pane_courses_overview_start,
            pane_courses_overview_create1, pane_courses_overview_create2;
    @FXML
    private TableView<Lecturer> LecturerTable;
    @FXML
    private TableColumn<Lecturer, String> Name, Surname, Title;
    @FXML
    private TableColumn<Lecturer, Integer> Deputat, ID;
    @FXML
    private TableView<Course> CourseTable;
    @FXML
    private TableColumn<Course, String> CourseTitle, CourseModule, CourseKind, CourseChair, CourseNumber,
            CourseFinalsDate, CourseRhythem, CourseMaxPar, CourseExpPar, CoursePar, CourseDeputat, CourseCredits,
            CourseHyperlink, CourseLanguage, CourseStartDate, CourseEndDate, CourseStartTime, CourseEndTime, CourseCtSt,
            CourseRequirements, CourseCertificate, CourseDescription;
    @FXML
    private TableColumn<Course, Integer> CourseID, CourseSWS;
    @FXML
    private TableColumn<Course, Integer> CourseOnlineReg, CourseExtraCourse, CourseFinancing;


//ab hier Julias hässlicher Code
    @FXML
    private TableView<Schedule> schedulePreview;
    @FXML
    private TableColumn<Schedule, String> timeSlot;
    public ObservableList<Schedule> timeSlotsList= FXCollections.observableArrayList(
            new Schedule ("08:00"),
            new Schedule ("08:15"),
            new Schedule ("08:30"),
            new Schedule ("08:45"),
            new Schedule ("09:00"),
            new Schedule ("09:15"),
            new Schedule ("09:30"),
            new Schedule ("09:45"),
            new Schedule ("10:00"),
            new Schedule ("10:15"),
            new Schedule ("10:30"),
            new Schedule ("10:45"),
            new Schedule ("11:00"),
            new Schedule ("11:15"),
            new Schedule ("11:30"),
            new Schedule ("11:45"),
            new Schedule ("12:00"),
            new Schedule ("12:15"),
            new Schedule ("12:30"),
            new Schedule ("12:45"),
            new Schedule ("13:00"),
            new Schedule ("13:15"),
            new Schedule ("13:30"),
            new Schedule ("13:45"),
            new Schedule ("14:00"),
            new Schedule ("14:15"),
            new Schedule ("14:30"),
            new Schedule ("14:45"),
            new Schedule ("15:00"),
            new Schedule ("15:15"),
            new Schedule ("15:30"),
            new Schedule ("15:45"),
            new Schedule ("16:00"),
            new Schedule ("16:15"),
            new Schedule ("16:30"),
            new Schedule ("16:45"),
            new Schedule ("17:00"),
            new Schedule ("17:15"),
            new Schedule ("17:30"),
            new Schedule ("17:45"),
            new Schedule ("18:00")
    );
//bis hier Julias hässlicher Code

    @FXML
    private ObservableList<Lecturer> data;
    @FXML
    private ObservableList<Course> dataCourse;
    @FXML
    private ComboBox courseProgramCombo, chairCombo, dayCombo, ctCombo, rotaCombo, lecturerCombo, certCombo,
            kindCourseCombo, courseFrequencyComboBox, chair2Combo, semesterCombo;
    @FXML
    private CheckBox onlineRegBox, extraCourseBox, financingCourseBox;
    @FXML
    private DatePicker finalsDate, startDate, endDate;


    private Boolean editLecturer = false;
    private Boolean editCourse = false;
    private Integer IDTemp = null;
    private Integer IDTempCourse = null;
    private String TempLecturer = "";

    private DatabaseInterface dc = new DatabaseInterface();;

    @FXML
    private void initialize() {

        fillLecturers();

        createLecturer();
        createCourse();

        saveLecturer();
        saveCourse();

        deleteLecturer();
        updateLecturer();

        deleteCourse();
        updateCourse();

        fillCourse();
        fillCourseProgramCombo();
        fillLecturersCombo();
        fillChairsCombo();
        fillSemesterCombo();

        cancelLecturer();
        cancelCourse();

        courseProgramAction();

        nextCourseWindow();
        goBackCourse();

        initSchedule();
        //addCourseToScedule();


    }

    private void fillLecturers() {

        data = dc.GetDozenten();

        ID.setCellValueFactory((new PropertyValueFactory<>("LecturerID")));
        Name.setCellValueFactory(new PropertyValueFactory<>("LecturerName"));
        Surname.setCellValueFactory(new PropertyValueFactory<>("LecturerSurname"));
        Title.setCellValueFactory(new PropertyValueFactory<>("LecturerTitle"));
        Deputat.setCellValueFactory(new PropertyValueFactory<>("LecturerDeputat"));

        LecturerTable.setItems(null);
        LecturerTable.setItems(data);

    }

    private void createLecturer() {

        createLecturerButton.setOnAction((event) -> {

            pane_teachers_overview.setVisible(false);
            pane_single_teacher.setVisible(true);

        });

    }

    private void createCourse() {

        createCourseButton.setOnAction((event) -> {

            pane_courses_overview_start.setVisible(false);
            pane_courses_overview_create1.setVisible(true);

        });

    }

    private void nextCourseWindow() {

        nextCourseButton.setOnAction((event) -> {

            pane_courses_overview_create1.setVisible(false);
            pane_courses_overview_create2.setVisible(true);

        });

    }

    private void saveLecturer() {

        saveLecturerButton.setOnAction((event) -> {

            String name = lecturerNameText.getText();
            String surname = lecturerSurnameText.getText();
            String title = lecturerTitleText.getText();
            Integer deputat = Integer.parseInt(lecturerDeputatText.getText());

            if(editLecturer == false) {
                dc.writeLecturers(name, surname, title, deputat);
            }
            else {
                dc.updateLecturers(IDTemp, name, surname, title, deputat);
                IDTemp = null;
                editLecturer = false;
            }

            pane_teachers_overview.setVisible(true);
            pane_single_teacher.setVisible(false);

            lecturerNameText.clear();
            lecturerSurnameText.clear();
            lecturerTitleText.clear();
            lecturerDeputatText.clear();

            fillLecturers();

        });

    }

    private void saveCourse() {

        saveCourseButton.setOnAction((event) -> {

            String number = courseNumberText.getText();
            String title = courseTitleText.getText();
            String kind = kindCourseCombo.getValue().toString();

            String SWS = SWScourseText.getText();
            String hyperlink = hypertextCourseText.getText();
            String maxParticipants = maxParticipantsCourseText.getText();
            String expectedParticipants = expectedParticipantsCourseText.getText();
            Boolean onlineReg = onlineRegBox.isSelected();
            String credits = creditsCourseText.getText();
            String turnus = courseFrequencyComboBox.getValue().toString();
            Boolean extraCourse = extraCourseBox.isSelected();
            Boolean financing = financingCourseBox.isSelected();
            String finals = finalsDate.getValue().toString();
            String start = startDate.getValue().toString();
            String end = endDate.getValue().toString();
            String language = languageCourseText1.getText();

            // MODUL

            // ZWEITE SEITE
            String program = chairCombo.getValue().toString();
            String lecturer = lecturerCombo.getValue().toString();
            String day = dayCombo.getValue().toString();
            String startTime = startTimeText.getText();
            String endTime = endTimeText.getText();
            String ctSt = ctCombo.getValue().toString();
            String rota = rotaCombo.getValue().toString();
            String participants = participantsText.getText();
            String requirements = requirementsText.getText();
            String certificate = certCombo.getValue().toString();
            String deputat = deputatText.getText();
            String chair = chair2Combo.getValue().toString();
            String description = descriptionText.getText();

            if(editCourse == false) {
                dc.writeCourse(number, title, kind, SWS, hyperlink, maxParticipants, expectedParticipants, onlineReg,
                        credits, extraCourse, financing, finals, start, end, language, program, startTime,
                        endTime, ctSt, rota, participants, requirements, certificate, deputat, description,
                        turnus, chair);

                dc.connectCourseWithLecturerDay(lecturer, title, day);
                dc.addDeputat(deputat, lecturer);

            }

            else {

                if(!TempLecturer.equals(lecturer)){
                    dc.subDeputat(deputat, TempLecturer);
                    dc.addDeputat(deputat, lecturer);
                    TempLecturer = "";
                }
                dc.updateCourse(IDTempCourse, number, title, kind, SWS, hyperlink, maxParticipants, expectedParticipants,
                        onlineReg, credits, extraCourse, financing, finals, start, end, language, program, startTime,
                        endTime, ctSt, rota, participants, requirements, certificate, deputat, description, turnus,
                        chair, lecturer, day);
                editCourse = false;

            }

                pane_courses_overview_start.setVisible(true);
                pane_courses_overview_create2.setVisible(false);
                clearCourse();

                fillCourse();
                fillLecturers();

        });

    }

    private void deleteLecturer() {

        deleteLecturerButton.setOnAction((event) -> {

            Lecturer selected = LecturerTable.getSelectionModel().getSelectedItem();
            dc.deleteLecturers(selected.getLecturerID());

            fillLecturers();

        });

    }

    private void deleteCourse() {

        deleteCourseButton.setOnAction((event) -> {

            Course selected = CourseTable.getSelectionModel().getSelectedItem();
            dc.deleteCourse(selected.getCourseID());

            fillCourse();
            //Deputat von Dozenten entfernen, beachten alte Semester!
        });

    }

    private void updateLecturer() {

        updateLecturerButton.setOnAction((event) -> {

            editLecturer = true;
            Lecturer selected = LecturerTable.getSelectionModel().getSelectedItem();

            pane_teachers_overview.setVisible(false);
            pane_single_teacher.setVisible(true);

            lecturerNameText.setText(selected.getLecturerName());
            lecturerSurnameText.setText(selected.getLecturerSurname());
            lecturerTitleText.setText(selected.getLecturerTitle());
            lecturerDeputatText.setText(selected.getLecturerDeputat().toString());

            IDTemp = selected.getLecturerID();

        });

    }

    private void updateCourse() {

        editCourseButton.setOnAction((event) -> {

            editCourse = true;

            Course selected = CourseTable.getSelectionModel().getSelectedItem();

            pane_courses_overview_start.setVisible(false);
            pane_courses_overview_create1.setVisible(true);

            courseNumberText.setText(selected.getCourseNumber());
            courseTitleText.setText(selected.getCourseTitle());
            kindCourseCombo.setValue(selected.getCourseKind());
            SWScourseText.setText(""+selected.getCourseSWS());
            hypertextCourseText.setText(selected.getCourseHyperlink());
            maxParticipantsCourseText.setText(selected.getCourseMaxParticipants());
            expectedParticipantsCourseText.setText(selected.getCourseExpParticipants());
            onlineRegBox.setSelected(selected.isCourseOnlineReg());
            creditsCourseText.setText(selected.getCourseCredits());
            courseFrequencyComboBox.setValue(selected.getCourseTurnus());
            extraCourseBox.setSelected(selected.isCourseExtraCourse());
            financingCourseBox.setSelected(selected.isCourseFinancing());
            finalsDate.setValue(LocalDate.parse(selected.getCourseFinals()));
            startDate.setValue(LocalDate.parse(selected.getCourseStart()));
            endDate.setValue(LocalDate.parse(selected.getCourseEnd()));
            languageCourseText1.setText(selected.getCourseLanguage());
            chairCombo.setValue(selected.getCourseProgram());
            lecturerCombo.setValue(selected.getCourseLecturer());
            dayCombo.setValue(selected.getCourseDay());
            startTimeText.setText(selected.getCourseStartTime());
            endTimeText.setText(selected.getCourseEndTime());
            ctCombo.setValue(selected.getCourseCtST());
            rotaCombo.setValue(selected.getCourseRota());
            participantsText.setText(selected.getCourseParticipants());
            requirementsText.setText(selected.getCourseRequirements());
            certCombo.setValue(selected.getCourseCertificate());
            deputatText.setText(selected.getCourseDeputat());
            chair2Combo.setValue(selected.getCourseChair());
            descriptionText.setText(selected.getCourseDescription());

            IDTempCourse = selected.getCourseID();
            TempLecturer = selected.getCourseLecturer();

        });

    }

    private void fillCourse() {

        dataCourse = dc.GetCourses();

        CourseID.setCellValueFactory((new PropertyValueFactory<>("courseID")));
        CourseTitle.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        CourseSWS.setCellValueFactory(new PropertyValueFactory<>("courseSWS"));
        CourseKind.setCellValueFactory(new PropertyValueFactory<>("courseKind"));
        CourseModule.setCellValueFactory(new PropertyValueFactory<>("courseModule"));
        CourseChair.setCellValueFactory(new PropertyValueFactory<>("courseChair"));
        CourseNumber.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        CourseFinalsDate.setCellValueFactory(new PropertyValueFactory<>("courseFinals"));
        CourseRhythem.setCellValueFactory(new PropertyValueFactory<>("courseRota"));
        CourseMaxPar.setCellValueFactory(new PropertyValueFactory<>("courseMaxParticipants"));
        CourseExpPar.setCellValueFactory(new PropertyValueFactory<>("courseExpParticipants"));
        CoursePar.setCellValueFactory(new PropertyValueFactory<>("courseParticipants"));
        CourseDeputat.setCellValueFactory(new PropertyValueFactory<>("courseDeputat"));
        CourseCredits.setCellValueFactory(new PropertyValueFactory<>("courseCredits"));
        CourseHyperlink.setCellValueFactory(new PropertyValueFactory<>("courseHyperlink"));
        CourseLanguage.setCellValueFactory(new PropertyValueFactory<>("courseLanguage"));
        CourseStartDate.setCellValueFactory(new PropertyValueFactory<>("courseStart"));
        CourseEndDate.setCellValueFactory(new PropertyValueFactory<>("courseEnd"));
        CourseStartTime.setCellValueFactory(new PropertyValueFactory<>("courseStartTime"));
        CourseEndTime.setCellValueFactory(new PropertyValueFactory<>("courseEndTime"));
        CourseCtSt.setCellValueFactory(new PropertyValueFactory<>("courseCtST"));
        CourseRequirements.setCellValueFactory(new PropertyValueFactory<>("courseRequirements"));
        CourseCertificate.setCellValueFactory(new PropertyValueFactory<>("courseCertificate"));
        CourseDescription.setCellValueFactory(new PropertyValueFactory<>("courseDescription"));
        CourseOnlineReg.setCellValueFactory(new PropertyValueFactory<>("courseOnlineReg"));
        CourseExtraCourse.setCellValueFactory(new PropertyValueFactory<>("courseExtraCourse"));
        CourseFinancing.setCellValueFactory(new PropertyValueFactory<>("courseFinancing"));
        CourseChair.setCellValueFactory(new PropertyValueFactory<>("courseProgram"));


        CourseTable.setItems(null);
        CourseTable.setItems(dataCourse);

    }

    private void fillCourseProgramCombo(){

        for(int i = 0; i < dc.GetPrograms().size(); i++){
            courseProgramCombo.getItems().addAll(""+dc.GetPrograms().get(i)+"");
            chairCombo.getItems().addAll(""+dc.GetPrograms().get(i)+"");
        }
        courseProgramCombo.getSelectionModel().selectFirst();
    }

    private void fillLecturersCombo(){
        for(int i = 0; i < dc.GetLecturers().size(); i++){
            lecturerCombo.getItems().addAll(""+dc.GetLecturers().get(i)+"");
        }
    }

    private void fillChairsCombo(){
        for(int i = 0; i < dc.getChairs().size(); i++){
            chair2Combo.getItems().addAll(""+dc.getChairs().get(i)+"");
        }
    }

    private void fillSemesterCombo(){
        for(int i = 0; i < dc.getSemester().size(); i++){
            semesterCombo.getItems().addAll(""+dc.getSemester().get(i)+"");
        }
    }

    public void courseProgramAction() {

        dc.getSelectedCourses(courseProgramCombo.getValue().toString());
        fillCourse();

    }

    private void cancelLecturer(){

        cancelLecturerButton.setOnAction((event) -> {

            editLecturer = false;
            lecturerNameText.clear();
            lecturerSurnameText.clear();
            lecturerTitleText.clear();
            lecturerDeputatText.clear();

            pane_teachers_overview.setVisible(true);
            pane_single_teacher.setVisible(false);

        });

    }

    private void cancelCourse() {

        cancelCourseButton.setOnAction((event) -> {

            pane_courses_overview_start.setVisible(true);
            pane_courses_overview_create1.setVisible(false);

        });

    }

    private void goBackCourse() {

        backCourseButton.setOnAction((event) -> {

            pane_courses_overview_create1.setVisible(true);
            pane_courses_overview_create2.setVisible(false);

        });

    }

    private void clearCourse(){

        courseNumberText.clear();
        courseTitleText.clear();
        kindCourseCombo.setValue(null);
        SWScourseText.clear();
        hypertextCourseText.clear();
        maxParticipantsCourseText.clear();
        expectedParticipantsCourseText.clear();
        onlineRegBox.setSelected(false);
        creditsCourseText.clear();
        courseFrequencyComboBox.setValue(null);
        extraCourseBox.setSelected(false);
        financingCourseBox.setSelected(false);
        finalsDate.setValue(null);
        startDate.setValue(null);
        endDate.setValue(null);
        languageCourseText1.clear();
        chairCombo.setValue(null);
        lecturerCombo.setValue(null);
        dayCombo.setValue(null);
        startTimeText.clear();
        endTimeText.clear();
        ctCombo.setValue(null);
        rotaCombo.setValue(null);
        participantsText.clear();
        requirementsText.clear();
        certCombo.setValue(null);
        deputatText.clear();
        chair2Combo.setValue(null);
        descriptionText.clear();
    }

    /*private void addCourseToScedule() {

        btn_course_preview.setOnAction((event) -> {

          Course selected = CourseTable.getSelectionModel().getSelectedItem();
          int courseID = selected.getCourseID();

          dc.getCourse(courseID);

        });

    }*/

//ab hier Julias hässlicher Code
    private void initSchedule(){
        timeSlot.setCellValueFactory(new PropertyValueFactory<Schedule, String>("timeSlot"));;
        schedulePreview.setItems(timeSlotsList);

    }

//bis hier Julias hässlicher Code



}
