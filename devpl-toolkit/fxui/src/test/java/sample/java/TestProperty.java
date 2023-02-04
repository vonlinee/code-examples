package sample.java;

import javafx.beans.property.SimpleStringProperty;

public class TestProperty {

    public static void main(String[] args) {

        final SimpleStringProperty stringProperty = new SimpleStringProperty("123");

        System.out.println(stringProperty.getBean());
        System.out.println(stringProperty.getName());
        System.out.println(stringProperty.getValueSafe());
        System.out.println(stringProperty.get());
    }
}
