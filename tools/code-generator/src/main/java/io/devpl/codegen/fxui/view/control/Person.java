package io.devpl.codegen.fxui.view.control;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 先创建一个静态内部类
 * 作为TableView的行数据
 */
public class Person {
    private final StringProperty name;
    private final IntegerProperty age;
    private final StringProperty sex;
    private final StringProperty email;

    public Person(String name, int age, String sex, String email) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.sex = new SimpleStringProperty(sex);
        this.email = new SimpleStringProperty(email);
    }

    public int getAge() {
        return age.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getName() {
        return name.get();
    }

    public String getSex() {
        return sex.get();
    }
}