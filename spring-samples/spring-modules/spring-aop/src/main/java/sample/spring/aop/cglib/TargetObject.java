package sample.spring.aop.cglib;

public class TargetObject {

	public String method(String paramName) {
		System.out.println("TargetObject => " + paramName);
		return paramName;
	}

	@Override
	public String toString() {
		return "TargetObject [] " + getClass();
	}
}