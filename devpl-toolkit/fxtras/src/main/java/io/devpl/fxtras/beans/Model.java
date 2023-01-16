package io.devpl.fxtras.beans;

public class Model {

    private int age;
    private String name;
    private Double height;

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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Model{");
        sb.append("age=").append(age);
        sb.append(", name='").append(name).append('\'');
        sb.append(", height=").append(height);
        sb.append('}');
        return sb.toString();
    }
}
