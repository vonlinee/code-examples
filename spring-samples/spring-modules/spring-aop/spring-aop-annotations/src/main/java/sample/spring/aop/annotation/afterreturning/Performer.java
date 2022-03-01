package sample.spring.aop.annotation.afterreturning;

public class Performer {

	public String perform(String performerName, int performerAge){
		System.out.println("PERFORMER INVOKED");
		return "Singer";
	}
}
