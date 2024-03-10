package com.majorproject.aquatracker.Model;

import java.util.List;

public class FirebaseData {
    private int max;
    private int min;
    private List<DataValue> values;

    public FirebaseData() {
        // Default constructor required for calls to DataSnapshot.getValue(PhData.class)
    }

    // Getters and setters
    // Make sure to generate getters and setters for all fields

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public List<DataValue> getValues() {
        return values;
    }

    public void setValues(List<DataValue> values) {
        this.values = values;
    }
}

