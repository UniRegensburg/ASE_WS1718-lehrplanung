package Control;

import Export.ExportCreator;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;


import java.time.LocalDate;
import java.time.LocalTime;

public class Controller  {

    @FXML
    private Button createLecturerButton, createCourseButton, saveLecturerButton, saveCourseButton, deleteLecturerButton,
            deleteCourseButton, updateLecturerButton, editCourseButton, cancelLecturerButton, cancelCourseButton,
            nextCourseButton, backCourseButton, addCourseToScheduleButton, newSemesterButton, newChairButton,
            newProgramButton, resetCourseFilterButton, saveModuleButton, showTimeTableButton, clearTimeTableButton,
            dbImportButton, dbExportButton, exportTimetableButton, deleteSemesterButton, deleteChairButton,
            deleteProgramButton, deleteModuleButton;
    @FXML
    private TextField lecturerNameText, lecturerSurnameText, lecturerTitleText, lecturerDeputatText, courseNumberText,
            courseTitleText, SWScourseText, hypertextCourseText, maxParticipantsCourseText, creditsCourseText,
            expectedParticipantsCourseText,  participantsText, requirementsText, descriptionText, deputatText,
            languageCourseText1, newSemesterText, newChairText, newProgramText, startTimeMonday, endTimeMonday,
            startTimeTuesday, endTimeTuesday, startTimeWednesday, endTimeWednesday, startTimeThursday, endTimeThursday,
            startTimeFriday, endTimeFriday, writeModuleText;
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
    private ListView<String> lecturerCoursesList, semesterList, chairList, programsList, moduleList;
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
            filterTimeTableByProgramCombo, lecturerRoleCombo, moduleProgramCombo, moduleCombo;
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
    private Warnings warning = new Warnings();
    private HashMap<String, String[]> timeTableGlobal;

    @FXML
    private void initialize() {

        fillLecturers();
        fillTimeTable();

        createLecturer();
        createCourse();

        saveLecturer();
        saveCourse();

        deleteLecturer();
        editLecturerButton();

        deleteCourse();
        editCourseButton();

        fillCourse();
        fillProgramCombos();
        fillLecturersCombo();
        fillChairsCombo();
        fillSemesterCombo();
        fillModuleCombo();

        cancelLecturer();
        cancelCourse();

        nextCourseWindow();
        goBackCourse();

        createProgram();
        deleteProgam();

        createChair();
        deleteChair();

        createSemester();
        deleteSemester();

        createModule();
        deleteModule();

        fillCourseFilterCombo();

        lecturerTableDoubleClick();
        courseTableDoubleClick();
        resetCourseFilter();

        fillSemesterList();
        fillChairList();
        fillProgramsList();
        fillModuleList();

        addListenerToProgramCombo();
        addCourseToTimetable();
        createTimeTableHashMap();

        emptyTimeTable();
        resetTimeTableView();

        importDatabase();
        exportDatabase();
        exportTimeTable();

    }

    private void resetCourseFilter() {
        resetCourseFilterButton.setOnAction((event) -> {
            filterMenu.getItems().clear();
            dc.resetFilterSettings();
            fillCourseFilterCombo();
        });
    }

    private void resetTimeTableView(){
        clearTimeTableButton.setOnAction((event) -> {
            createTimeTableHashMap();
            emptyTimeTable();
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

    private void fillTimeTable(){

        showTimeTableButton.setOnAction((event) -> {

            ObservableList<TimeTable> data = FXCollections.observableArrayList();

            timeSlot.setCellValueFactory(new PropertyValueFactory<>("scheduleTime"));
            colMonday.setCellValueFactory(new PropertyValueFactory<>("scheduleMonday"));
            colTuesday.setCellValueFactory(new PropertyValueFactory<>("scheduleTuesday"));
            colWednesday.setCellValueFactory(new PropertyValueFactory<>("scheduleWednesday"));
            colThursday.setCellValueFactory(new PropertyValueFactory<>("scheduleThursday"));
            colFriday.setCellValueFactory(new PropertyValueFactory<>("scheduleFriday"));

            if(semesterTimeTableCombo.getSelectionModel().isEmpty() || filterTimeTableByProgramCombo.getSelectionModel().isEmpty()){
                warning.missingTimeTablePerimeter();
            }
            else{
                Integer semesterID = dc.getSemesterID(semesterTimeTableCombo.getValue().toString());
                Integer programID = dc.getProgramID(filterTimeTableByProgramCombo.getValue().toString());

                String[] times = dc.getTimeTableSettings(semesterID, programID).split(";");
                for(int i = 0; i<times.length; i++){
                    String[] days = times[i].split(",");
                    data.add(new TimeTable(days[0], days[1], days[2], days[3], days[4], days[5]));
                }

                schedulePreview.setItems(null);
                schedulePreview.setItems(data);
            }
        });
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
            String module = moduleCombo.getValue().toString();

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
                        ctSt, rota, participants, requirements, certificate, deputat, description, turnus, chair, module);

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
                        participants, requirements, certificate, deputat, description, turnus, chair, lecturer, module);
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

        lecturerCoursesList.setItems(dc.getLecturerCourses(selected.getLecturerID()));

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
        moduleCombo.setValue(selected.getCourseModule());

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

    private void fillProgramCombos(){

        courseProgramCombo.getItems().clear();
        programCombo.getItems().clear();
        moduleProgramCombo.getItems().clear();
        filterTimeTableByProgramCombo.getItems().clear();

        List<String> Programs = dc.GetPrograms();

        for(int i = 0; i < dc.GetPrograms().size(); i++){
            courseProgramCombo.getItems().addAll(""+Programs.get(i));
            programCombo.getItems().addAll(""+Programs.get(i));
            moduleProgramCombo.getItems().addAll(""+Programs.get(i));
            filterTimeTableByProgramCombo.getItems().addAll(""+Programs.get(i));
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

    private void fillModuleCombo(){
        moduleCombo.getItems().clear();
        for(int i = 0; i < dc.getModule().size(); i++){
            moduleCombo.getItems().addAll(""+dc.getModule().get(i)+"");
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

    private void fillSemesterList(){

        semesterList.setItems(dc.getSemesterList());
    }

    private void fillChairList(){

        chairList.setItems(dc.getChairList());
    }

    private void fillProgramsList(){

        programsList.setItems(dc.getProgramsList());
    }

    private void fillModuleList(){

        moduleList.setItems(dc.getModuleList());
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
            fillSemesterList();

        });

    }

    private void deleteSemester(){

        deleteSemesterButton.setOnAction((event) -> {
            dc.deleteSemester(semesterList.getSelectionModel().getSelectedItem().toString());
            fillSemesterCombo();
            fillSemesterList();
        });

    }

    private void createChair() {

        newChairButton.setOnAction((event) -> {

            String chairName = newChairText.getText();
            dc.writeChair(chairName);
            newChairText.clear();
            fillChairsCombo();
            fillChairList();

        });

    }

    private void deleteChair(){

        deleteChairButton.setOnAction((event) -> {

            dc.deleteChair(chairList.getSelectionModel().getSelectedItem().toString());
            fillChairsCombo();
            fillChairList();
        });
    }

    private void createProgram() {

        newProgramButton.setOnAction((event) -> {

            String programName = newProgramText.getText();
            dc.writeProgram(programName);
            newProgramText.clear();
            fillProgramCombos();
            fillProgramsList();

        });

    }

    private void deleteProgam(){

        deleteProgramButton.setOnAction((event) -> {

            dc.deleteProgram(programsList.getSelectionModel().getSelectedItem().toString());
            fillProgramCombos();
            fillProgramsList();

        });
    }

    private void createModule(){

        saveModuleButton.setOnAction((event) -> {

            String moduleName = writeModuleText.getText();
            dc.writeModule(moduleName, dc.getProgramID(moduleProgramCombo.getValue().toString()));
            writeModuleText.clear();
            fillModuleList();
        });
    }

    private void deleteModule(){

        deleteModuleButton.setOnAction((event) -> {

            dc.deleteModule(moduleList.getSelectionModel().getSelectedItem().toString());
            fillModuleList();
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

    private void addListenerToProgramCombo() {
        courseProgramCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(courseProgramCombo.getValue() == null){

                } else {
                    dc.getSelectedCourses(courseProgramCombo.getValue().toString());
                    fillCourse();
                }
            }
        });
    }

    private void addCourseToTimetable(){

        addCourseToScheduleButton.setOnAction((event) -> {

            Integer semesterID = dc.getSemesterID(semesterCombo.getValue().toString());
            Integer programID = dc.getProgramID(courseProgramCombo.getValue().toString());

            if(dc.getTimeTableCount(semesterID, programID) == 0){
                dc.writeTimeTable(semesterID,programID,createTimeTable());
            }
            else{
                dc.extendTimeTable(semesterID, programID, extendTimeTable(semesterID, programID));
            }
        });
    }

    private void createTimeTableHashMap(){

        HashMap<String, String[]> timeTable = new HashMap<>();
        String[] week8 = new String[]{"08:00",",-",",-",",-",",-",",-"};
        String[] week830 = new String[]{";08:30",",-",",-",",-",",-",",-"};
        String[] week9 = new String[]{";09:00",",-",",-",",-",",-",",-"};
        String[] week930 = new String[]{";09:30",",-",",-",",-",",-",",-"};
        String[] week10 = new String[]{";10:00",",-",",-",",-",",-",",-"};
        String[] week1030 = new String[]{";10:30",",-",",-",",-",",-",",-"};
        String[] week11 = new String[]{";11:00",",-",",-",",-",",-",",-"};
        String[] week1130 = new String[]{";11:30",",-",",-",",-",",-",",-"};
        String[] week12 = new String[]{";12:00",",-",",-",",-",",-",",-"};
        String[] week1230 = new String[]{";12:30",",-",",-",",-",",-",",-"};
        String[] week13 = new String[]{";13:00",",-",",-",",-",",-",",-"};
        String[] week1330 = new String[]{";13:30",",-",",-",",-",",-",",-"};
        String[] week14 = new String[]{";14:00",",-",",-",",-",",-",",-"};
        String[] week1430 = new String[]{";14:30",",-",",-",",-",",-",",-"};
        String[] week15 = new String[]{";15:00",",-",",-",",-",",-",",-"};
        String[] week1530 = new String[]{";15:30",",-",",-",",-",",-",",-"};
        String[] week16 = new String[]{";16:00",",-",",-",",-",",-",",-"};
        String[] week1630 = new String[]{";16:30",",-",",-",",-",",-",",-"};
        String[] week17 = new String[]{";17:00",",-",",-",",-",",-",",-"};
        String[] week1730 = new String[]{";17:30",",-",",-",",-",",-",",-"};
        String[] week18 = new String[]{";18:00",",-",",-",",-",",-",",-"};
        String[] week1830 = new String[]{";18:30",",-",",-",",-",",-",",-"};
        String[] week19 = new String[]{";19:00",",-",",-",",-",",-",",-"};
        String[] week1930 = new String[]{";19:30",",-",",-",",-",",-",",-"};
        String[] week20 = new String[]{";20:00",",-",",-",",-",",-",",-"};

        timeTable.put("08:00", week8);
        timeTable.put("08:30", week830);
        timeTable.put("09:00", week9);
        timeTable.put("09:30", week930);
        timeTable.put("10:00", week10);
        timeTable.put("10:30", week1030);
        timeTable.put("11:00", week11);
        timeTable.put("11:30", week1130);
        timeTable.put("12:00", week12);
        timeTable.put("12:30", week1230);
        timeTable.put("13:00", week13);
        timeTable.put("13:30", week1330);
        timeTable.put("14:00", week14);
        timeTable.put("14:30", week1430);
        timeTable.put("15:00", week15);
        timeTable.put("15:30", week1530);
        timeTable.put("16:00", week16);
        timeTable.put("16:30", week1630);
        timeTable.put("17:00", week17);
        timeTable.put("17:30", week1730);
        timeTable.put("18:00", week18);
        timeTable.put("18:30", week1830);
        timeTable.put("19:00", week19);
        timeTable.put("19:30", week1930);
        timeTable.put("20:00", week20);


        timeTableGlobal = timeTable;
    }

    private String extendTimeTable(Integer semesterID, Integer programID){

        String existingTimeTable = dc.getTimeTableSettings(semesterID, programID);
        String[] splitSettings = existingTimeTable.split(";");

        Course selected = CourseTable.getSelectionModel().getSelectedItem();
        List<String> daysTimes = dc.getDayTimes(selected.getCourseID());

        LocalTime timeTemp;
        LocalTime start;
        LocalTime end = LocalTime.parse("20:00");
        String resultString = "";

        for(int i = 0; i<splitSettings.length; i++){
            String[] hashKeys = splitSettings[i].split(",");
            String key = hashKeys[0];
            hashKeys[0] = hashKeys[0]+",";
            hashKeys[1] = hashKeys[1]+",";
            hashKeys[2] = hashKeys[2]+",";
            hashKeys[3] = hashKeys[3]+",";
            hashKeys[4] = hashKeys[4]+",";
            hashKeys[5] = hashKeys[5]+";";
            timeTableGlobal.put(key, hashKeys);
        }

        for(int i = 0; i < daysTimes.size(); i++){
            String[] daysTimesSplit = daysTimes.get(i).split(";");
            timeTemp = LocalTime.parse(daysTimesSplit[1]);
            while(!timeTemp.isAfter(LocalTime.parse(daysTimesSplit[2]))){
                String[] temp = timeTableGlobal.get(timeTemp.toString());
                switch(daysTimesSplit[0]){
                    case "1":
                        temp[1] = temp[1]+(selected.getCourseTitle());
                        timeTableGlobal.put(timeTemp.toString(),temp);
                        break;
                    case "2":
                        temp[2] = temp[2]+selected.getCourseTitle();
                        timeTableGlobal.put(timeTemp.toString(),temp);
                        break;
                    case "3":
                        temp[3] = temp[3]+selected.getCourseTitle();
                        timeTableGlobal.put(timeTemp.toString(),temp);
                        break;
                    case "4":
                        temp[4] = temp[4]+selected.getCourseTitle();
                        timeTableGlobal.put(timeTemp.toString(),temp);
                        break;
                    case "5":
                        temp[5] = "";
                        temp[5] = temp[5]+selected.getCourseTitle()+";";
                        timeTableGlobal.put(timeTemp.toString(),temp);
                        break;
                    default: System.out.println("Oh no");
                }
                timeTemp = timeTemp.plusMinutes(30);
            }

        }
        for(start = LocalTime.parse("08:00"); !start.isAfter(end); start = start.plusMinutes(30)){
            String[] result = timeTableGlobal.get(start.toString());
            resultString += result[0]+result[1]+result[2]+result[3]+result[4]+result[5];
        }
        return resultString;
    }

    private String createTimeTable(){

        LocalTime timeTemp;
        LocalTime start;
        LocalTime end = LocalTime.parse("20:00");
        String resultString = "";

        Course selected = CourseTable.getSelectionModel().getSelectedItem();
        List<String> daysTimes = dc.getDayTimes(selected.getCourseID());

        for(int i = 0; i < daysTimes.size(); i++){
            String[] daysTimesSplit = daysTimes.get(i).split(";");
            timeTemp = LocalTime.parse(daysTimesSplit[1]);
            while(!timeTemp.isAfter(LocalTime.parse(daysTimesSplit[2]))){
                String[] temp = timeTableGlobal.get(timeTemp.toString());
                switch(daysTimesSplit[0]){
                    case "1":
                        temp[1] = temp[1]+(selected.getCourseTitle());
                        timeTableGlobal.put(timeTemp.toString(),temp);
                        break;
                    case "2":
                        temp[2] = temp[2]+selected.getCourseTitle();
                        timeTableGlobal.put(timeTemp.toString(),temp);
                        break;
                    case "3":
                        temp[3] = temp[3]+selected.getCourseTitle();
                        timeTableGlobal.put(timeTemp.toString(),temp);
                        break;
                    case "4":
                        temp[4] = temp[4]+selected.getCourseTitle();
                        timeTableGlobal.put(timeTemp.toString(),temp);
                        break;
                    case "5":
                        temp[5] = temp[5]+selected.getCourseTitle();
                        timeTableGlobal.put(timeTemp.toString(),temp);
                        break;
                    default: System.out.println("Oh no");
                }
                timeTemp = timeTemp.plusMinutes(30);
            }

        }
        for(start = LocalTime.parse("08:00"); !start.isAfter(end); start = start.plusMinutes(30)){
            String[] result = timeTableGlobal.get(start.toString());
            resultString += result[0]+result[1]+result[2]+result[3]+result[4]+result[5];
        }
        return resultString;
    }

    private void emptyTimeTable(){
        LocalTime start;
        LocalTime end = LocalTime.parse("20:00");

        ObservableList<TimeTable> data = FXCollections.observableArrayList();

        timeSlot.setCellValueFactory(new PropertyValueFactory<>("scheduleTime"));
        colMonday.setCellValueFactory(new PropertyValueFactory<>("scheduleMonday"));
        colTuesday.setCellValueFactory(new PropertyValueFactory<>("scheduleTuesday"));
        colWednesday.setCellValueFactory(new PropertyValueFactory<>("scheduleWednesday"));
        colThursday.setCellValueFactory(new PropertyValueFactory<>("scheduleThursday"));
        colFriday.setCellValueFactory(new PropertyValueFactory<>("scheduleFriday"));


        for(start = LocalTime.parse("08:00"); !start.isAfter(end); start = start.plusMinutes(30)){
            String[] result = timeTableGlobal.get(start.toString());
            result[0] = result[0].replace(";","");
            result[1] = result[1].replace(",","");
            result[2] = result[2].replace(",","");
            result[3] = result[3].replace(",","");
            result[4] = result[4].replace(",","");
            result[5] = result[5].replace(",","");
            data.add(new TimeTable(result[0], result[1], result[2], result[3], result[4], result[5]));
        }

        schedulePreview.setItems(null);
        schedulePreview.setItems(data);
    }

    private void importDatabase() {

        // HIER PFAD WO DIE DB FINAL IST EINFÜGEN
        String destination = "C:\\Users\\weber_000\\Desktop\\TestZiel";

        dbImportButton.setOnAction((event) -> {

            FileChooser fc = new FileChooser();

            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Datenbank Objekt (*.db)", "*.db"));
            File selectedFile = fc.showOpenDialog(null);

            if(selectedFile != null){

                moveDatabase(selectedFile.getAbsolutePath(), destination + "\\LehrplanungDB.db" );

            }


        });

    }

    private void exportDatabase() {

        //HIER PFAD WO DIE DB FINAL IST EINFÜGEN
        String origin = "C:\\Users\\weber_000\\Desktop\\TestAnfang";

        dbExportButton.setOnAction((event) -> {

            DirectoryChooser dch = new DirectoryChooser();

            dch.setTitle("Zielordnder auswählen");

            File selectedDirectory = dch.showDialog(null);

            if(selectedDirectory != null) {

                moveDatabase(origin+"\\LehrplanungDB.db", selectedDirectory.getAbsolutePath()+"\\LehrplanungDB.db");

            }

        });

    }

    private void moveDatabase(String origin, String destination){

        FileReader orig = null;
        FileWriter dest = null;

        try {

            orig = new FileReader(origin);
            dest = new FileWriter(destination);
            int c = orig.read();

            while(c!=-1) {

                dest.write(c);
                c = orig.read();

            }

        } catch(IOException e) {

            e.printStackTrace();

        } finally {

            close(orig);
            close(dest);
        }

    }

    private void close(Closeable stream) {

        try {
            if (stream != null) {
                stream.close();
            }
        } catch(IOException e) {

        }

    }

    // HOLT BEI BUTTON PRESS DEN LANGEN STRING UND SCHREIBT DEN IN EINE CSV MIT DER EXPORTCREATOR KLASSE
    // CSV LIEGT <---- UNTER INTERFACES STANDARDMÄSSIG, KANN DA BLEIBEN; WIRD ÜBERSCHRIEBEN SOWEIT ICH WEISS
    // ALS NÄCHSTES SOLLTE DIE CSV NOCH IN PDF UMGEWANDELT WERDEN

    private void exportTimeTable() {

        exportTimetableButton.setOnAction((event) -> {

            Integer semesterID = dc.getSemesterID(semesterTimeTableCombo.getValue().toString());
            Integer programID = dc.getProgramID(filterTimeTableByProgramCombo.getValue().toString());

            String table = dc.getTimeTableSettings(semesterID, programID);

            ExportCreator creator = new ExportCreator();
            creator.writeCSV("Lehrplanung.csv", table);

        });

    }



}
