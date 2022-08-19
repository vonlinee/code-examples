package org.example.springboot.di.bean;

import org.springframework.stereotype.Component;

//@Component
public class Animal {
	private String name;
	private String color;
	private byte age;

	public Animal(String name, String color, byte age) {
		this.name = name;
		this.color = color;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Animal{" + "name='" + name + '\'' + ", color='" + color + '\'' + ", age=" + age + '}';
	}
}