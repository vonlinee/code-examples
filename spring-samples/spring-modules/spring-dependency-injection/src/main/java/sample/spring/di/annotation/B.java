package sample.spring.di.annotation;

import org.springframework.stereotype.Component;

@Component
public class B {
	public B() {
		System.out.println("B() -> " + this);
	}
}
