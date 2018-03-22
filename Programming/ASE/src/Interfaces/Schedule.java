package Interfaces;

import javafx.beans.property.SimpleStringProperty;

public class Schedule {
    private final SimpleStringProperty timeSlot;

    public Schedule(String timeSlot) {
        this.timeSlot = new SimpleStringProperty(timeSlot);
    }

    public String getTimeSlot() {
        return timeSlot.get();
    }
}
