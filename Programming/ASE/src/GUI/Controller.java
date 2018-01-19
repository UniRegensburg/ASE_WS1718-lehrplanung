
package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class Controller {

    @FXML
    private Button myButton;

    public Controller() {

        System.out.println("Hallo");

    }


    @FXML
    private void initialize() {
        // Handle Button event.
        myButton.setOnAction((event) -> {
            System.out.println("Button geht");

        });
        //private void handleButtonAction(ActionEvent event) {

        //System.out.println("Button Action");
        //  outputTextArea.appendText("Button Action\n");
        //}
    }
}
