
package GUI;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import Interfaces.Lecturer;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Database.DatabaseInterface;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller  {


    @FXML
    private TableView<Lecturer> CourseTable;

    @FXML
    private TableColumn<Lecturer, String> Name;

    @FXML
    private TableColumn<Lecturer, String> Surname;

    @FXML
    private TableColumn<Lecturer, String> Title;

    @FXML
    private TableColumn<Lecturer, Integer> Deputat;

    //@FXML
    //private Label Label;

    @FXML
    private Button myButton2;

    @FXML
    private ObservableList<Lecturer> data;
    private DatabaseInterface dc;


    @FXML
    private void initialize() {


        dc = new DatabaseInterface();

        myButton2.setOnAction((event) -> {

            data = dc.GetDozenten();

            Name.setCellValueFactory(new PropertyValueFactory<>("LecturerName"));
            Surname.setCellValueFactory(new PropertyValueFactory<>("LecturerSurname"));
            Title.setCellValueFactory(new PropertyValueFactory<>("LecturerTitle"));
            Deputat.setCellValueFactory(new PropertyValueFactory<>("LecturerDeputat"));

            CourseTable.setItems(null);
            CourseTable.setItems(data);







        });

        //Label.setText("I'm a Label.");

    }

}
