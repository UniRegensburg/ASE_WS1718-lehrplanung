package Control;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import Interfaces.Lecturer;
import Interfaces.Course;
import javafx.scene.control.*;
import Database.DatabaseInterface;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import javax.swing.event.TableModelListener;

public class Controller  {

    @FXML
    private Button createLecturerButton, saveLecturerButton, deleteLecturerButton, updateLecturerButton;
    @FXML
    private TextField lecturerNameText, lecturerSurnameText, lecturerTitleText, lecturerDeputatText;
    @FXML
    private BorderPane pane_teachers_overview, pane_single_teacher;
    @FXML
    private TableView<Lecturer> LecturerTable;
    @FXML
    private TableColumn<Lecturer, String> Name, Surname, Title;
    @FXML
    private TableColumn<Lecturer, Integer> Deputat, ID;
    @FXML
    private TableView<Course> CourseTable;
    @FXML
    private TableColumn<Course, String> CourseTitle, CourseModul, CourseKind, CourseChair;
    @FXML
    private TableColumn<Course, Integer> CourseID, CourseSWS;
    @FXML
    private ObservableList<Lecturer> data;
    @FXML
    private ObservableList<Course> dataCourse;
    @FXML
    private ComboBox CourseProgrammCombo;

    private Boolean edit = false;
    private Integer IDTemp = null;

    private DatabaseInterface dc = new DatabaseInterface();;

    @FXML
    private void initialize() {

        fillLecturers();
        createLecturer();
        saveLecturer();
        deleteLecturer();
        updateLecturer();
        fillCourse();
        fillCourseProgrammCombo();

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


    private void saveLecturer() {

        saveLecturerButton.setOnAction((event) -> {

            String name = lecturerNameText.getText();
            String surname = lecturerSurnameText.getText();
            String title = lecturerTitleText.getText();
            Integer deputat = Integer.parseInt(lecturerDeputatText.getText());

            if(edit == false) {
                dc.writeLecturers(name, surname, title, deputat);
            }
            else {
                dc.updateLecturers(IDTemp, name, surname, title, deputat);
                IDTemp = null;
                edit = false;
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


    private void deleteLecturer() {

        deleteLecturerButton.setOnAction((event) -> {

            Lecturer selected = LecturerTable.getSelectionModel().getSelectedItem();
            dc.deleteLecturers(selected.getLecturerID());

            fillLecturers();

        });

    }

    private void updateLecturer() {

        updateLecturerButton.setOnAction((event) -> {

            edit = true;
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
        CourseModul.setCellValueFactory(new PropertyValueFactory<>("courseModul"));
        CourseChair.setCellValueFactory(new PropertyValueFactory<>("courseChair"));

        CourseTable.setItems(null);
        CourseTable.setItems(dataCourse);

    }

    private void fillCourseProgrammCombo(){

        for(int i = 0; i < dc.GetProgramms().size(); i++){
            CourseProgrammCombo.getItems().addAll(""+dc.GetProgramms().get(i)+"");
        }
    }

}