package sample.java8.primary.oop;

public class TestFinal {

	final Object object = new Object();

	public static void main(String[] args) {
		Object obj1 = new TestFinal().object;
		Object obj2 = new TestFinal().object;
		System.out.println(obj1 == obj2);
		System.out.println(obj1 + "  " + obj1.hashCode() + "  " + Integer.toHexString(obj1.hashCode()));
		System.out.println(obj2);
	}
}
