package io.devpl.codegen.ui.fxui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.lang.reflect.Modifier;

public class BeanInfo {

    private final IntegerProperty modifier = new SimpleIntegerProperty(Modifier.PRIVATE);
    private final StringProperty type = new SimpleStringProperty("int");
    private final StringProperty fieldName = new SimpleStringProperty("");
    private final StringProperty initialValue = new SimpleStringProperty("");


}
