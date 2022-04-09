package sample.java.primary.oop.inherit;

public class TestThisInParentAndChild {
	
	public static void main(String[] args) {
		B b = new B();
		b.delegate = new A();
	}
}

interface I {
	
}

class P implements I {

	I wrapper;
	
	public P() {
		System.out.println(this);
		this.wrapper = this;
	}
}

class A extends P {
	
}

class B extends P {
	
	public P delegate;
	
	public B() {
		System.out.println("B::B()");
	}
}