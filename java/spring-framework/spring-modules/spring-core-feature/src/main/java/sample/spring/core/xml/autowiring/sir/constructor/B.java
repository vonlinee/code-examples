package sample.spring.core.xml.autowiring.sir.constructor;

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
