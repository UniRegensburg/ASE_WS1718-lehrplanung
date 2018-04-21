package Interfaces;

import javafx.beans.property.*;

// Class for creating TimeTable objects
// Provides getter / setter methods for TimeTable Objects

public class TimeTable {

    private SimpleStringProperty scheduleTime = new SimpleStringProperty();
    private SimpleStringProperty scheduleMonday = new SimpleStringProperty();
    private SimpleStringProperty scheduleTuesday = new SimpleStringProperty();
    private SimpleStringProperty scheduleWednesday = new SimpleStringProperty();
    private SimpleStringProperty scheduleThursday = new SimpleStringProperty();
    private SimpleStringProperty scheduleFriday = new SimpleStringProperty();

    public TimeTable(String Time, String Monday, String Tuesday, String Wednesday, String Thursday, String Friday){
        this.scheduleTime = new SimpleStringProperty(Time);
        this.scheduleMonday = new SimpleStringProperty(Monday);
        this.scheduleTuesday = new SimpleStringProperty(Tuesday);
        this.scheduleWednesday = new SimpleStringProperty(Wednesday);
        this.scheduleThursday = new SimpleStringProperty(Thursday);
        this.scheduleFriday = new SimpleStringProperty(Friday);
    }

    public String getScheduleTime() {
        return scheduleTime.get();
    }

    public SimpleStringProperty scheduleTimeProperty() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime.set(scheduleTime);
    }

    public String getScheduleMonday() {
        return scheduleMonday.get();
    }

    public SimpleStringProperty scheduleMondayProperty() {
        return scheduleMonday;
    }

    public void setScheduleMonday(String scheduleMonday) {
        this.scheduleMonday.set(scheduleMonday);
    }

    public String getScheduleTuesday() {
        return scheduleTuesday.get();
    }

    public SimpleStringProperty scheduleTuesdayProperty() {
        return scheduleTuesday;
    }

    public void setScheduleTuesday(String scheduleTuesday) {
        this.scheduleTuesday.set(scheduleTuesday);
    }

    public String getScheduleWednesday() {
        return scheduleWednesday.get();
    }

    public SimpleStringProperty scheduleWednesdayProperty() {
        return scheduleWednesday;
    }

    public void setScheduleWednesday(String scheduleWednesday) {
        this.scheduleWednesday.set(scheduleWednesday);
    }

    public String getScheduleThursday() {
        return scheduleThursday.get();
    }

    public SimpleStringProperty scheduleThursdayProperty() {
        return scheduleThursday;
    }

    public void setScheduleThursday(String scheduleThursday) {
        this.scheduleThursday.set(scheduleThursday);
    }

    public String getScheduleFriday() {
        return scheduleFriday.get();
    }

    public SimpleStringProperty scheduleFridayProperty() {
        return scheduleFriday;
    }

    public void setScheduleFriday(String scheduleFriday) {
        this.scheduleFriday.set(scheduleFriday);
    }



}
