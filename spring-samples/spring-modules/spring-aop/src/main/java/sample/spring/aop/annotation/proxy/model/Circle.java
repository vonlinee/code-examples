package sample.spring.aop.annotation.proxy.model;

import lombok.Getter;
import lombok.Setter;
import sample.spring.aop.annotation.proxy.service.Shape;

@Getter @Setter
public class Circle implements Shape {

	public void draw(){
		System.out.println("Drawing Circle");
	}
}
