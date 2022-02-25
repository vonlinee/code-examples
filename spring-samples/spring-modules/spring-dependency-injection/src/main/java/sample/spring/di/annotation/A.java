package sample.spring.di.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class A {
	
	public A(B b) {
		System.out.println(b);
	}
}
