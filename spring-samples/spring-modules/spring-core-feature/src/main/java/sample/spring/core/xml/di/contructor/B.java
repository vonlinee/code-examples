package sample.spring.core.xml.di.contructor;

public class B {
	private A aa;

	public A getAa() {
		return aa;
	}

	public void setAa(A aa) {
		this.aa = aa;
	}

	public B(A aa)
	{
		this.aa=aa;
	}
	
}
