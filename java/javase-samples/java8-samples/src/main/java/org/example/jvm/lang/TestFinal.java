package org.example.jvm.lang;

public class TestFinal {

	public static void main(String[] args) {
		
	}
	
	public static void method1() {
		String name = "Whoops bug";
	}

	public static void method2() {
		final String name = "Whoops bug";
	}
}
