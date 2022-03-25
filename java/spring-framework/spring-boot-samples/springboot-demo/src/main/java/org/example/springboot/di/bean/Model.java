package org.example.springboot.di.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Model {
	@Autowired
	Student student;
}
