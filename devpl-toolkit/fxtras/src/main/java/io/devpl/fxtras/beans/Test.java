package io.devpl.fxtras.beans;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Test {

    public static void main(String[] args) {

        Model model = new Model();

        SimpleStringProperty property = new SimpleStringProperty();

        PropertyBean<Model> bean = PropertyBean.of(model);

        bean.bind("name", String.class, property);

        property.addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

            }
        });
        property.set("zs");

    }
}
