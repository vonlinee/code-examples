package sample.spring.aop.annotation.joinpoint;

public class Performer {

	public int perform(String performerName, int performerAge){
		System.out.println("PERFORMER INVOKED");
		return 78;
	}
}
