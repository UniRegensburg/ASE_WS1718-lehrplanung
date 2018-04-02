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

public class Controller  {

    @FXML
    private Button createLecturerButton, createCourseButton, saveLecturerButton, saveCourseButton, deleteLecturerButton,
            updateLecturerButton, cancelLecturerButton, cancelCourseButton, nextCourseButton, backCourseButton;
    @FXML
    private TextField lecturerNameText, lecturerSurnameText, lecturerTitleText, lecturerDeputatText, courseNumberText,
            courseTitleText, SWScourseText, hypertextCourseText, maxParticipantsCourseText, creditsCourseText,
            expectedParticipantsCourseText, startTimeText, endTimeText, participantsText,
            requirementsText, descriptionText, deputatText;
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
    private TableColumn<Course, String> CourseTitle, CourseModule, CourseKind, CourseChair;
    @FXML
    private TableColumn<Course, Integer> CourseID, CourseSWS;


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
    private ComboBox courseProgramCombo, chairCombo, dayCombo, ctCombo, rotaCombo, lecturerCombo, certCombo, kindCourseCombo;
    @FXML
    private CheckBox onlineRegBox, extraCourseBox, financingCourseBox;
    @FXML
    private DatePicker finalsDate, startDate, endDate, canceledDate;


    private Boolean editLecturer = false;
    private Boolean editCourse = false;
    private Integer IDTemp = null;

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

        fillCourse();
        fillCourseProgramCombo();

        cancelLecturer();
        cancelCourse();

        courseProgramAction();

        nextCourseWindow();
        goBackCourse();

        initSchedule();


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


            //String groups = groupsCourseText.getText();


            Boolean extraCourse = extraCourseBox.isSelected();
            Boolean financing = financingCourseBox.isSelected();
            String finals = finalsDate.getValue().toString();
            String start = startDate.getValue().toString();
            String end = endDate.getValue().toString();
            String language = creditsCourseText.getText();

            // MODUL



            // ZWEITE SEITE
            String chair = chairCombo.getValue().toString();
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
            String description = descriptionText.getText();
            String canceled = canceledDate.getValue().toString();







            if(editCourse == false) {
                //dc.writeLecturers(name, surname, title, deputat);
            }
            else {
                //dc.updateLecturers(IDTemp, name, surname, title, deputat);
                //IDTemp = null;
                editCourse = false;
            }

            pane_courses_overview_start.setVisible(true);
            pane_courses_overview_create1.setVisible(false);

            /*lecturerNameText.clear();
            lecturerSurnameText.clear();
            lecturerTitleText.clear();
            lecturerDeputatText.clear();

            fillCourse();*/

        });

    }

    private void deleteLecturer() {

        deleteLecturerButton.setOnAction((event) -> {

            Lecturer selected = LecturerTable.getSelectionModel().getSelectedItem();
            dc.deleteLecturers(selected.getLecturerID());

            fillLecturers();

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

    private void fillCourse() {

        dataCourse = dc.GetCourses();

        CourseID.setCellValueFactory((new PropertyValueFactory<>("courseID")));
        CourseTitle.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        CourseSWS.setCellValueFactory(new PropertyValueFactory<>("courseSWS"));
        CourseKind.setCellValueFactory(new PropertyValueFactory<>("courseKind"));
        CourseModule.setCellValueFactory(new PropertyValueFactory<>("courseModule"));
        CourseChair.setCellValueFactory(new PropertyValueFactory<>("courseChair"));

        CourseTable.setItems(null);
        CourseTable.setItems(dataCourse);

    }

    private void fillCourseProgramCombo(){

        for(int i = 0; i < dc.GetProgramms().size(); i++){
            courseProgramCombo.getItems().addAll(""+dc.GetProgramms().get(i)+"");
        }
        courseProgramCombo.getSelectionModel().selectFirst();
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

//ab hier Julias hässlicher Code
    private void initSchedule(){
        timeSlot.setCellValueFactory(new PropertyValueFactory<Schedule, String>("timeSlot"));;
        schedulePreview.setItems(timeSlotsList);

    }

//bis hier Julias hässlicher Code



}
