
package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Database.DatabaseInterface;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.naming.Name;
import javax.swing.text.html.ImageView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Controller  {

    private DatabaseInterface DB = new DatabaseInterface();

    @FXML
    private TableView<User> KursTable;

    @FXML
    private TableColumn<User, String> Name;

    @FXML
    private TableColumn<User, String> Vorname;

    @FXML
    private TableColumn<User, String> Titel;

    @FXML
    private TableColumn<User, Integer> Deputat;

    //@FXML
    //private Label Label;

    @FXML
    private Button myButton2;

    @FXML
    private ObservableList<User> data;
    private DatabaseInterface dc;


    @FXML
    private void initialize() {

        /*ArrayList<String[]> result = DB.selectAllDozenten();
        for (int i = 0; i < result.size(); i++){

            System.out.println(result.get(i));

        }*/

        dc = new DatabaseInterface();

        myButton2.setOnAction((event) -> {

            System.out.println("Button geht.");
            try {
                Connection conn = dc.connect();
                data = FXCollections.observableArrayList();

                ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM Dozenten");
                while (rs.next()) {

                    data.add(new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            Name.setCellValueFactory(new PropertyValueFactory<>("userName"));
            Vorname.setCellValueFactory(new PropertyValueFactory<>("userSurname"));
            Titel.setCellValueFactory(new PropertyValueFactory<>("userTitle"));
            Deputat.setCellValueFactory(new PropertyValueFactory<>("userDeputat"));

            KursTable.setItems(null);
            KursTable.setItems(data);







        });

        //Label.setText("I'm a Label.");

    }

}
