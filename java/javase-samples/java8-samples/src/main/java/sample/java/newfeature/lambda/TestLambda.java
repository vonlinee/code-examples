package sample.java.newfeature.lambda;

import java.util.function.Predicate;

public class TestLambda {
	public static void main(String[] args) {
		System.setProperty("jdk.internal.lambda.dumpProxyClasses", "");
		TestLambda main = new TestLambda();
		main.lambda("AAA", str -> str.length() > 5);
		
	}
	
	public void lambda(String value, Predicate<String> predicate) {
		boolean result = predicate.test(value);
		System.out.println(result);
	}
}
