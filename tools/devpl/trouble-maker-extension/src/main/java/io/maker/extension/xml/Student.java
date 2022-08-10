package io.maker.extension.xml;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {

    private int age;
    private String name;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Student(int age, String name, Date date) {
        super();
        this.age = age;
        this.name = name;
        this.date = date;
    }

	public Student() {
	}
}
