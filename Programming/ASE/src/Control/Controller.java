package Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import Interfaces.Lecturer;
import Interfaces.Course;
import Interfaces.TimeTable;
import javafx.scene.control.*;
import Database.DatabaseInterface;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;


import java.time.LocalDate;

public class Controller  {

    @FXML
    private Button createLecturerButton, createCourseButton, saveLecturerButton, saveCourseButton, deleteLecturerButton,
            deleteCourseButton, updateLecturerButton, editCourseButton, cancelLecturerButton, cancelCourseButton,
            nextCourseButton, backCourseButton, addCourseToScheduleButton, newSemesterButton, newChairButton,
            newProgramButton, resetCourseFilterButton;
    @FXML
    private TextField lecturerNameText, lecturerSurnameText, lecturerTitleText, lecturerDeputatText, courseNumberText,
            courseTitleText, SWScourseText, hypertextCourseText, maxParticipantsCourseText, creditsCourseText,
            expectedParticipantsCourseText,  participantsText, requirementsText, descriptionText, deputatText,
            languageCourseText1, newSemesterText, newChairText, newProgramText, startTimeMonday, endTimeMonday,
            startTimeTuesday, endTimeTuesday, startTimeWednesday, endTimeWednesday, startTimeThursday, endTimeThursday,
            startTimeFriday, endTimeFriday;
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
            CourseHyperlink, CourseLanguage, CourseStartDate, CourseEndDate, CourseCtSt, CourseRequirements,
            CourseCertificate, CourseDescription, Rolle, CourseTurnus;
    @FXML
    private TableColumn<Course, Integer> CourseID, CourseSWS;
    @FXML
    private TableColumn<Course, Integer> CourseOnlineReg, CourseExtraCourse, CourseFinancing;


    @FXML
    private TableView<TimeTable> schedulePreview;
    @FXML
    private TableColumn<TimeTable, String> timeSlot, colMonday, colTuesday, colWednesday, colThursday, colFriday;
    @FXML
    private ObservableList<Lecturer> data;
    @FXML
    private ObservableList<Course> dataCourse;
    @FXML
    private ComboBox courseProgramCombo, programCombo, ctCombo, rotaCombo, lecturerCombo, certCombo,
            kindCourseCombo, courseFrequencyComboBox, chairCombo, semesterCombo, semesterTimeTableCombo,
            filterTimeTableByProgramCombo, lecturerRoleCombo;
    @FXML
    private CheckBox onlineRegBox, extraCourseBox, financingCourseBox, checkMonday, checkTuesday, checkWednesday,
            checkThursday, checkFriday;
    @FXML
    private DatePicker finalsDate, startDate, endDate;

    @FXML
    private MenuButton filterMenu;


    private Boolean editLecturer = false;
    private Boolean editCourse = false;
    private Integer IDTemp = null;
    private Integer IDTempCourse = null;
    private String TempLecturer = "";
    private DatabaseInterface dc = new DatabaseInterface();

    @FXML
    private void initialize() {

        fillLecturers();

        createLecturer();
        createCourse();

        saveLecturer();
        saveCourse();

        deleteLecturer();
        editLecturerButton();

        deleteCourse();
        editCourseButton();

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

        //addCourseToScedule();

        createProgram();
        createChair();
        createSemester();
        fillCourseFilterCombo();
        fillTimeTableProgramFilterCombo();


        lecturerTableDoubleClick();
        courseTableDoubleClick();
        resetCourseFilter();



    }

    private void resetCourseFilter() {
        resetCourseFilterButton.setOnAction((event) -> {
            filterMenu.getItems().clear();
            dc.resetFilterSettings();
            fillCourseFilterCombo();
        });
    }

    private void fillCourseFilterCombo() {
        List <String> settings = dc.getFilterSettings();
        for(int i=0; i<settings.size(); i++){
            String[] Names = settings.get(i).toString().split(";");
            CheckMenuItem checkItem = new CheckMenuItem(Names[0]);
            checkItem.setSelected(Boolean.valueOf(Names[1]));
            CourseTable.getColumns().get(i).setVisible(Boolean.valueOf(Names[1]));
            filterMenu.getItems().add(checkItem);

            checkItem.setOnAction(event -> {
                if(checkItem.isSelected() || !checkItem.isSelected()){
                    dc.updateFilterSettings(Names[0], checkItem.isSelected());
                    filterMenu.getItems().clear();
                    fillCourseFilterCombo();
                }
            });
        }
    }

    private void fillLecturers() {

        data = dc.GetDozenten();

        ID.setCellValueFactory((new PropertyValueFactory<>("LecturerID")));
        Name.setCellValueFactory(new PropertyValueFactory<>("LecturerName"));
        Surname.setCellValueFactory(new PropertyValueFactory<>("LecturerSurname"));
        Title.setCellValueFactory(new PropertyValueFactory<>("LecturerTitle"));
        Deputat.setCellValueFactory(new PropertyValueFactory<>("LecturerDeputat"));
        Rolle.setCellValueFactory(new PropertyValueFactory<>("LecturerRole"));

        LecturerTable.setItems(null);
        LecturerTable.setItems(data);

    }

    /*private void addCourseToScedule() {

        addCourseToScheduleButton.setOnAction((event) -> {

            Course selected = CourseTable.getSelectionModel().getSelectedItem();
            String name = selected.getCourseTitle();
            String start = selected.getCourseStartTime();
            String day = selected.getCourseDay();


            timeSlot.setCellValueFactory(new PropertyValueFactory<>("scheduleTime"));
            colMonday.setCellValueFactory(new PropertyValueFactory<>("scheduleMonday"));
            colTuesday.setCellValueFactory(new PropertyValueFactory<>("scheduleTuesday"));
            colWednesday.setCellValueFactory(new PropertyValueFactory<>("scheduleWednesday"));
            colThursday.setCellValueFactory(new PropertyValueFactory<>("scheduleThursday"));
            colFriday.setCellValueFactory(new PropertyValueFactory<>("scheduleFriday"));

            schedulePreview.setItems(null);
            schedulePreview.setItems(fillTimeTable(start, name, day));

        });

    }*/

    private ObservableList<TimeTable> fillTimeTable(String start, String title, String day){

        ObservableList<TimeTable> data = FXCollections.observableArrayList();
        String[] Time = start.split(":");

        System.out.println(Time[0]+"  "+day);

        for(int i= 8; i < 21; i++){
            if(Integer.parseInt(Time[0])==i || (Integer.parseInt(Time[0])+1)==i){
                switch (day){
                    case "Montag": data.add(new TimeTable(""+i,""+title,"","","",""));
                    break;
                    case "Dienstag": data.add(new TimeTable(""+i,"",""+title,"","",""));
                    break;
                    case "Mittwoch": data.add(new TimeTable(""+i,"","",""+title,"",""));
                    break;
                    case "Donnerstag": data.add(new TimeTable(""+i,"","","",""+title,""));
                    break;
                    case "Freitag": data.add(new TimeTable(""+i,"","","","",""+title));
                    break;
                    default: System.out.println("Warum nur?");
                }
            }
            else{
                data.add(new TimeTable(""+i,"","","","",""));
            }
        }
        return data;
    }

    private void createLecturer() {

        createLecturerButton.setOnAction((event) -> {

            pane_teachers_overview.setVisible(false);
            pane_single_teacher.setVisible(true);

        });

    }

    private void createCourse() {

        createCourseButton.setOnAction((event) -> {

            startDate.setValue(LocalDate.now());
            endDate.setValue(LocalDate.now().plusMonths(5));
            finalsDate.setValue(LocalDate.now().plusMonths(6));
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
            String role = "--";
            if(!lecturerRoleCombo.getSelectionModel().isEmpty()){
                role = lecturerRoleCombo.getValue().toString();
            }

            if(editLecturer == false) {
                dc.writeLecturers(name, surname, title, deputat, role);
            }
            else {
                dc.updateLecturers(IDTemp, name, surname, title, deputat, role);
                IDTemp = null;
                editLecturer = false;
            }

            pane_teachers_overview.setVisible(true);
            pane_single_teacher.setVisible(false);

            lecturerNameText.clear();
            lecturerSurnameText.clear();
            lecturerTitleText.clear();
            lecturerDeputatText.clear();
            lecturerRoleCombo.setValue(null);

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

            // ZWEITE SEITE
            String program = programCombo.getValue().toString();
            String lecturer = lecturerCombo.getValue().toString();
            String ctSt = ctCombo.getValue().toString();
            String rota = rotaCombo.getValue().toString();
            String participants = participantsText.getText();
            String requirements = requirementsText.getText();
            String certificate = certCombo.getValue().toString();
            String deputat = deputatText.getText();
            String chair = chairCombo.getValue().toString();
            String description = descriptionText.getText();

            //Days + Time
            Boolean checkMo = checkMonday.isSelected();
            Boolean checkDi = checkTuesday.isSelected();
            Boolean checkMi = checkWednesday.isSelected();
            Boolean checkDo = checkThursday.isSelected();
            Boolean checkFr = checkFriday.isSelected();

            String startMo = startTimeMonday.getText();
            String endMo = endTimeMonday.getText();
            String startDi = startTimeTuesday.getText();
            String endDi = endTimeTuesday.getText();
            String startMi = startTimeWednesday.getText();
            String endMi = endTimeWednesday.getText();
            String startDo = startTimeThursday.getText();
            String endDo = endTimeThursday.getText();
            String startFr = startTimeFriday.getText();
            String endFr = endTimeFriday.getText();

            if(editCourse == false) {
                dc.writeCourse(number, title, kind, SWS, hyperlink, maxParticipants, expectedParticipants, onlineReg,
                        credits, extraCourse, financing, finals, start, end, language, program,
                        ctSt, rota, participants, requirements, certificate, deputat, description, turnus, chair);

                dc.connectCourseWithLecturer(lecturer, title);
                if(checkMo){
                    dc.connectCourseWithDay(title, "Montag", startMo, endMo);
                }
                if(checkDi) {
                    dc.connectCourseWithDay(title, "Dienstag", startDi, endDi);
                }
                if(checkMi) {
                    dc.connectCourseWithDay(title, "Mittwoch", startMi, endMi);
                }
                if(checkDo) {
                    dc.connectCourseWithDay(title, "Donnerstag", startDo, endDo);
                }
                if(checkFr) {
                    dc.connectCourseWithDay(title, "Freitag", startFr, endFr);
                }
                dc.addDeputat(deputat, lecturer);

            }

            else {

                if(!TempLecturer.equals(lecturer)){
                    dc.subDeputat(deputat, TempLecturer);
                    dc.addDeputat(deputat, lecturer);
                    TempLecturer = "";
                }
                dc.updateCourse(IDTempCourse, number, title, kind, SWS, hyperlink, maxParticipants, expectedParticipants,
                        onlineReg, credits, extraCourse, financing, finals, start, end, language, program, ctSt, rota,
                        participants, requirements, certificate, deputat, description, turnus, chair, lecturer);
                if(checkMo){
                    dc.connectCourseWithDay(title, "Montag", startMo, endMo);
                }
                if(checkDi) {
                    dc.connectCourseWithDay(title, "Dienstag", startDi, endDi);
                }
                if(checkMi) {
                    dc.connectCourseWithDay(title, "Mittwoch", startMi, endMi);
                }
                if(checkDo) {
                    dc.connectCourseWithDay(title, "Donnerstag", startDo, endDo);
                }
                if(checkFr) {
                    dc.connectCourseWithDay(title, "Freitag", startFr, endFr);
                }
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

    private void editLecturerButton() {

        updateLecturerButton.setOnAction((event) -> {
            editLecturer();
        });

    }

    private void editLecturer(){

        editLecturer = true;
        Lecturer selected = LecturerTable.getSelectionModel().getSelectedItem();

        pane_teachers_overview.setVisible(false);
        pane_single_teacher.setVisible(true);

        lecturerNameText.setText(selected.getLecturerName());
        lecturerSurnameText.setText(selected.getLecturerSurname());
        lecturerTitleText.setText(selected.getLecturerTitle());
        lecturerDeputatText.setText(selected.getLecturerDeputat().toString());
        lecturerRoleCombo.setValue(selected.getLecturerRole());

        IDTemp = selected.getLecturerID();
    }

    private void editCourseButton() {

        editCourseButton.setOnAction((event) -> {
            editCourse();
        });

    }

    private void editCourse(){
        editCourse = true;
        Course selected = CourseTable.getSelectionModel().getSelectedItem();
        List<String> dayTimes = dc.getDayTimes(selected.getCourseID());
        for(int i = 0; i < dayTimes.size(); i++){
            String[] dT = dayTimes.get(i).split(";");
            switch(dT[0]){
                case "1": checkMonday.setSelected(true); startTimeMonday.setText(dT[1]); endTimeMonday.setText(dT[2]);
                break;
                case "2": checkTuesday.setSelected(true); startTimeTuesday.setText(dT[1]); endTimeTuesday.setText(dT[2]);
                break;
                case "3": checkWednesday.setSelected(true); startTimeWednesday.setText(dT[1]); endTimeWednesday.setText(dT[2]);
                break;
                case "4": checkThursday.setSelected(true); startTimeThursday.setText(dT[1]); endTimeThursday.setText(dT[2]);
                break;
                case "5": checkFriday.setSelected(true); startTimeFriday.setText(dT[1]); endTimeFriday.setText(dT[2]);
                break;
                default: System.out.println("Error");
            }
        }

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
        programCombo.setValue(selected.getCourseProgram());
        lecturerCombo.setValue(selected.getCourseLecturer());
        ctCombo.setValue(selected.getCourseCtST());
        rotaCombo.setValue(selected.getCourseRota());
        participantsText.setText(selected.getCourseParticipants());
        requirementsText.setText(selected.getCourseRequirements());
        certCombo.setValue(selected.getCourseCertificate());
        deputatText.setText(selected.getCourseDeputat());
        chairCombo.setValue(selected.getCourseChair());
        descriptionText.setText(selected.getCourseDescription());

        IDTempCourse = selected.getCourseID();
        TempLecturer = selected.getCourseLecturer();

        pane_courses_overview_start.setVisible(false);
        pane_courses_overview_create1.setVisible(true);
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
        CourseCtSt.setCellValueFactory(new PropertyValueFactory<>("courseCtST"));
        CourseRequirements.setCellValueFactory(new PropertyValueFactory<>("courseRequirements"));
        CourseCertificate.setCellValueFactory(new PropertyValueFactory<>("courseCertificate"));
        CourseDescription.setCellValueFactory(new PropertyValueFactory<>("courseDescription"));
        CourseOnlineReg.setCellValueFactory(new PropertyValueFactory<>("courseOnlineReg"));
        CourseExtraCourse.setCellValueFactory(new PropertyValueFactory<>("courseExtraCourse"));
        CourseFinancing.setCellValueFactory(new PropertyValueFactory<>("courseFinancing"));
        CourseTurnus.setCellValueFactory(new PropertyValueFactory<>("courseTurnus"));

        CourseTable.setItems(null);
        CourseTable.setItems(dataCourse);

    }

    private void fillCourseProgramCombo(){
        courseProgramCombo.getItems().clear();
        programCombo.getItems().clear();
        for(int i = 0; i < dc.GetPrograms().size(); i++){
            courseProgramCombo.getItems().addAll(""+dc.GetPrograms().get(i)+"");
            programCombo.getItems().addAll(""+dc.GetPrograms().get(i)+"");
        }
        courseProgramCombo.getSelectionModel().selectFirst();
    }

    private void fillTimeTableProgramFilterCombo() {
        filterTimeTableByProgramCombo.getItems().clear();
        for(int i = 0; i < dc.GetPrograms().size(); i++){
            filterTimeTableByProgramCombo.getItems().addAll(""+dc.GetPrograms().get(i)+"");
        }
    }

    private void fillLecturersCombo(){
        lecturerCombo.getItems().clear();
        for(int i = 0; i < dc.GetLecturers().size(); i++){
            lecturerCombo.getItems().addAll(""+dc.GetLecturers().get(i)+"");
        }
    }

    private void fillChairsCombo(){
        chairCombo.getItems().clear();
        for(int i = 0; i < dc.getChairs().size(); i++){
            chairCombo.getItems().addAll(""+dc.getChairs().get(i)+"");
        }
    }

    private void fillSemesterCombo(){
        semesterCombo.getItems().clear();
        semesterTimeTableCombo.getItems().clear();
        for(int i = 0; i < dc.getSemester().size(); i++){
            semesterCombo.getItems().addAll(""+dc.getSemester().get(i)+"");
            semesterTimeTableCombo.getItems().addAll(""+dc.getSemester().get(i)+"");
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

            clearCourse();
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
        checkMonday.setSelected(false);
        checkTuesday.setSelected(false);
        checkWednesday.setSelected(false);
        checkThursday.setSelected(false);
        checkFriday.setSelected(false);
        creditsCourseText.clear();
        courseFrequencyComboBox.setValue(null);
        extraCourseBox.setSelected(false);
        financingCourseBox.setSelected(false);
        finalsDate.setValue(null);
        startDate.setValue(null);
        endDate.setValue(null);
        languageCourseText1.clear();
        programCombo.setValue(null);
        lecturerCombo.setValue(null);
        ctCombo.setValue(null);
        rotaCombo.setValue(null);
        participantsText.clear();
        requirementsText.clear();
        certCombo.setValue(null);
        deputatText.clear();
        chairCombo.setValue(null);
        descriptionText.clear();
        startTimeMonday.clear();
        startTimeTuesday.clear();
        startTimeWednesday.clear();
        startTimeThursday.clear();
        startTimeFriday.clear();
        endTimeMonday.clear();
        endTimeTuesday.clear();
        endTimeWednesday.clear();
        endTimeThursday.clear();
        endTimeFriday.clear();
    }

    private void createSemester() {

        newSemesterButton.setOnAction((event) -> {

            String semesterName = newSemesterText.getText();
            dc.writeSemester(semesterName);
            newSemesterText.clear();
            fillSemesterCombo();

        });

    }

    private void createChair() {

        newChairButton.setOnAction((event) -> {

            String chairName = newChairText.getText();
            dc.writeChair(chairName);
            newChairText.clear();
            fillChairsCombo();

        });

    }

    private void createProgram() {

        newProgramButton.setOnAction((event) -> {

            String programName = newProgramText.getText();
            dc.writeProgram(programName);
            newProgramText.clear();
            fillCourseProgramCombo();

        });

    }

    private void lecturerTableDoubleClick(){
        LecturerTable.setRowFactory(tv -> {
            TableRow<Lecturer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    editLecturer();
                }
            });
            return row ;
        });
    }

    private void courseTableDoubleClick(){
        CourseTable.setRowFactory(tv -> {
            TableRow<Course> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    editCourse();
                }
            });
            return row ;
        });
    }

}
