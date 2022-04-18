package io.spring.boot.common;

public class Wrapper {
	
	private Throwable throwable;
	
	public Wrapper() {
		this.throwable = new RuntimeException();
	}
	
	public static void main(String[] args) throws Throwable  {
		Wrapper wrapper = new Wrapper();
		try {
			wrapper.throwException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void throwException() throws Throwable {
		throw throwable;
	}
}
