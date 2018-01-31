package Interfaces;

import javafx.beans.value.ObservableValue;

public interface ObservablePerson extends Person {

        ObservableValue<String> nameProperty();
        ObservableValue<String> surnameProperty();
        ObservableValue<String> titleProperty();
        ObservableValue<Integer> deputatProperty();
        ObservableValue<String[]>coursesProperty();


}
