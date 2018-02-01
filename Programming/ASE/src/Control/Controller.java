
package Control;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import Interfaces.Lecturer;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Database.DatabaseInterface;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller  {


    @FXML
    private TableView<Lecturer> LecturerTable;

    @FXML
    private TableColumn<Lecturer, String> Name;

    @FXML
    private TableColumn<Lecturer, String> Surname;

    @FXML
    private TableColumn<Lecturer, String> Title;

    @FXML
    private TableColumn<Lecturer, Integer> Deputat;

    @FXML
    private ObservableList<Lecturer> data;
    private DatabaseInterface dc;


    @FXML
    private void initialize() {


        dc = new DatabaseInterface();

            data = dc.GetDozenten();

            Name.setCellValueFactory(new PropertyValueFactory<>("LecturerName"));
            Surname.setCellValueFactory(new PropertyValueFactory<>("LecturerSurname"));
            Title.setCellValueFactory(new PropertyValueFactory<>("LecturerTitle"));
            Deputat.setCellValueFactory(new PropertyValueFactory<>("LecturerDeputat"));

            LecturerTable.setItems(null);
            LecturerTable.setItems(data);

    }

}
