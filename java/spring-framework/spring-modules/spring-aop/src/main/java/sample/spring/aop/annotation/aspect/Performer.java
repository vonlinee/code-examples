package sample.spring.aop.annotation.aspect;

public class Performer {

	public Performer() {
		System.out.println("构造 => " + this);
	}
	
	public int perform(){
		System.out.println("PERFORMER INVOKED");
		return 78;
	}
}
