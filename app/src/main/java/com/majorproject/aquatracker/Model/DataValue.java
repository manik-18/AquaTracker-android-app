package com.majorproject.aquatracker.Model;

public class DataValue {
    private String time;
    private double value;

    public DataValue() {
        // Default constructor required for calls to DataSnapshot.getValue(DataValue.class)
    }

    // Getters and setters
    // Make sure to generate getters and setters for all fields

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
