package io.devpl.codegen.fxui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.openjdk.jol.info.ClassLayout;

import java.lang.reflect.Modifier;

public class BeanInfo {

    private final IntegerProperty modifier = new SimpleIntegerProperty(Modifier.PRIVATE);
    private final StringProperty type = new SimpleStringProperty("int");
    private final StringProperty fieldName = new SimpleStringProperty("");
    private final StringProperty initialValue = new SimpleStringProperty("");
    private final StringProperty comment = new SimpleStringProperty("");

    public int getModifier() {
        return modifier.get();
    }

    public IntegerProperty modifierProperty() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier.set(modifier);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getFieldName() {
        return fieldName.get();
    }

    public StringProperty fieldNameProperty() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName.set(fieldName);
    }

    public String getInitialValue() {
        return initialValue.get();
    }

    public StringProperty initialValueProperty() {
        return initialValue;
    }

    public void setInitialValue(String initialValue) {
        this.initialValue.set(initialValue);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }
}
