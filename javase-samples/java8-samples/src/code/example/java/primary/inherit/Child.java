package code.example.java.primary.inherit;

public class Child extends Parent {

	public Child() {
		super();
		System.out.println();
		super.set1(10);
		System.out.println(this.object == super.object);
	}
	
}
