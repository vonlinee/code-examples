package code.sample.spring.jdbc.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Student {
	private int id;
	private String name;
	
	public Student(){}
	
	public Student(int id, String name){
		this.id = id;
		this.name = name;
	}
}
