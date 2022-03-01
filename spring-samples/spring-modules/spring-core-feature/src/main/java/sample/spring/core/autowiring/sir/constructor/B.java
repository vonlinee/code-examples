package sample.spring.core.autowiring.sir.constructor;

public class B {
	private A aa;

	public B(A aa)
	{
		this.aa=aa;
	}
	public A getAa() {
		return aa;
	}
}
