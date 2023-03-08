package sample.java8.primary.oop.inherit;

public class TestPrivateInherit {
	public static void main(String[] args) {
		Father p = new Child1();
		p.setI(10);
		
		System.out.println(p.getI()); // 10
		
		Child1 child1 = (Child1) p;
		
		int i = child1.getI();
		
		System.out.println(i);
	}
}

class Father {
	private int i;

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
}

class Child1 extends Father {
	
}
