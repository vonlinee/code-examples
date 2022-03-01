package sample.spring.core.autowiring.sir.autodetect;

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
