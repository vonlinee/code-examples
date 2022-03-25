package sample.spring.aop.xml.beforeandafter;

import org.springframework.lang.NonNull;

public class Performer {
	
	public void perform(){
		System.out.println("PERFORMER INVOKED");
		System.out.println(this.getClass().getName());
		if (this.getClass().getName().contains("Spring")) {
			int i = 1 / 0;
		}
	}
}
