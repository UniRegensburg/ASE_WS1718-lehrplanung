
package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Database.DatabaseInterface;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Controller  {

    @FXML
    private Button myButton;
    private TableView KursTable;
    private TableColumn KursTabelle_Vorname;

    public Controller() {

        ObservableList<ArrayList> test = initializeTable();
        KursTable.setItems(test);

    }


    @FXML
    private void initialize() {
        // Handle Button event.
        myButton.setOnAction((event) -> {

            System.out.println("test");

        });



        //private void handleButtonAction(ActionEvent event) {

        //System.out.println("Button Action");
        //  outputTextArea.appendText("Button Action\n");
        //}
    }

    @FXML
    private ObservableList<ArrayList> initializeTable() {

        DatabaseInterface DI = new DatabaseInterface();
        ObservableList<ArrayList> Dozenten = FXCollections.observableArrayList();
        Dozenten.add(new ArrayList(DI.selectAllDozenten()));

        return Dozenten;
    }
}
