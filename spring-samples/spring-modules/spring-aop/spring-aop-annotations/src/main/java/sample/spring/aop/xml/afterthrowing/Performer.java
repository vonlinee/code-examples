package sample.spring.aop.xml.afterthrowing;

public class Performer {

	public void validateAge(int age){
		if(age<18){
			throw new ArithmeticException();
		}
		else{
			System.out.println("Valid Artist");
		}
	}
}
