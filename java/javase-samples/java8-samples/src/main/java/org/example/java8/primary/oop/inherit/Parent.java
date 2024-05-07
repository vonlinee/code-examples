package org.example.java8.primary.oop.inherit;

public class Parent {
	private int i;
	protected int j;
	public int k;
	int m;
	
	Object object = new Object();
	
	public Parent() {
		System.out.println("Parent()");
	}
	
	private void set(int i) {
		this.i = i;
	}
	
	public void set1(int i) {
		set(i);
	}
	
	public int get() {
		return i;
	}
}
