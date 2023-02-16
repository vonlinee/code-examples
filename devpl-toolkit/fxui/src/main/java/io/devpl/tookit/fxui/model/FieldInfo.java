package io.devpl.tookit.fxui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 字段信息
 */
public class FieldInfo {

    private final StringProperty modifier = new SimpleStringProperty();
    private final StringProperty dataType = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty remarks = new SimpleStringProperty();

    public String getModifier() {
        return modifier.get();
    }

    public StringProperty modifierProperty() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier.set(modifier);
    }

    public String getDataType() {
        return dataType.get();
    }

    public StringProperty dataTypeProperty() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType.set(dataType);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getRemarks() {
        return remarks.get();
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }
}
