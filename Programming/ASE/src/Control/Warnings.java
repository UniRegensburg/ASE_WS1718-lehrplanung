package Control;

import javafx.scene.control.Alert;

public class Warnings {

    public Warnings(){

    }

    public void missingTimeTablePerimeter(){
        Alert missingCourseInfo = new Alert(Alert.AlertType.WARNING);
        missingCourseInfo.setTitle("Warnung");
        missingCourseInfo.setContentText("Bitte ein Semester und einen Studiengang auswählen.");

        missingCourseInfo.showAndWait();
    }
}
