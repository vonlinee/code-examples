package io.devpl.codegen.mbg.fx.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * 字典数据
 */
public class SysDictData {

    private final SimpleStringProperty name;
    private final SimpleStringProperty value;
    private final SimpleStringProperty description;

    public SysDictData(String fName, String lName, String email) {
        this.name = new SimpleStringProperty(fName);
        this.value = new SimpleStringProperty(lName);
        this.description = new SimpleStringProperty(email);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String fName) {
        name.set(fName);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String fName) {
        value.set(fName);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String fName) {
        description.set(fName);
    }
}
