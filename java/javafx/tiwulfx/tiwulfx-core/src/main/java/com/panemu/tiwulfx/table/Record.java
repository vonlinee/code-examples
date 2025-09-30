package com.panemu.tiwulfx.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Record {

    public StringProperty label = new SimpleStringProperty();
    public StringProperty value = new SimpleStringProperty();

    public Record(String label, String value) {
        this.label.set(label);
        this.value.set(value);
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}