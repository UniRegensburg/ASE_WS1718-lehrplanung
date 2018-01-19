
package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class Controller {

    @FXML
    private Button myButton;

    public Controller() {

    }


    @FXML
    private void initialize() {
        // Handle Button event.
        myButton.setOnAction((event) -> {
            System.out.println("Button geht");

        });

    }
}
