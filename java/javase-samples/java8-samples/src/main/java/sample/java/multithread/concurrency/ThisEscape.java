package sample.java.multithread.concurrency;

public class ThisEscape {

	M m;
	
	public ThisEscape() {
		this.m = new M() {
			@Override
			public void method() {
				System.out.println(this); //sample.java.multithread.concurrency.ThisEscape$1@15db9742
			}
		};
	}
	
	public static void main(String[] args) {
		ThisEscape thisEscape = new ThisEscape();
		thisEscape.m.method();
	}
}

interface M {
	void method();
}