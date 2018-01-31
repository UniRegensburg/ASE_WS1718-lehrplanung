package GUI;

import javafx.beans.property.*;

import java.util.List;

public class User {

    private SimpleStringProperty userName = new SimpleStringProperty();
    private SimpleStringProperty userSurname = new SimpleStringProperty();
    private SimpleStringProperty userTitle = new SimpleStringProperty();
    private SimpleIntegerProperty userDeputat = new SimpleIntegerProperty();

    public User(String userName, String userSurname, String userTitle, Integer userDeputat) {

        this.userName = new SimpleStringProperty(userName);
        this.userSurname = new SimpleStringProperty(userSurname);
        this.userTitle = new SimpleStringProperty(userTitle);
        this.userDeputat = new SimpleIntegerProperty(userDeputat);

    }

    public String getUserName() {
        return userName.get();
    }

    public String getUserSurname() {
        return userSurname.get();
    }

    public String getUserTitle() {
        return userTitle.get();
    }

    public Integer getUserDeputat() {
        return userDeputat.get();
    }

    /*public List getUserKurse() {
        return userKurse.get();
    }
    */

    public void setUserName(String value){

        userName.set(value);

    }

    public void setUserSurname(String value){

        userSurname.set(value);

    }

    public void setUserTitle(String value){

        userTitle.set(value);

    }

    public void setUserDeputat(Integer value){

        userDeputat.set(value);

    }

    public StringProperty nameProperty() {

        return userName;

    }

    public StringProperty surnameProperty() {

        return userSurname;

    }

    public StringProperty titleProperty() {

        return userTitle;

    }

    public IntegerProperty deputatProperty() {

        return userDeputat;

    }

}
