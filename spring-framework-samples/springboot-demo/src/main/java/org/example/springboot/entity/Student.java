package org.example.springboot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student {

	private int id;
	private String name;
	private float score;
}
