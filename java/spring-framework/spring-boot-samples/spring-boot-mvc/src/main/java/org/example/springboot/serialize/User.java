package org.example.springboot.serialize;

import java.io.Serializable;

public class User {
    //年龄
    private int age;
    //名字
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
