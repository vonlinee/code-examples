package org.example.java8.primary.oop.inner;

/**
 * 匿名内部类无法用反编译工具查看：比如jd-gui工具，不知原因
 * 
 * 使用javap查看
 * javap -verbose ./AnonymousInnerClass$1.class > AnonymousInnerClass$1.txt
 * javap -verbose ./AnonymousInnerClass$2.class > AnonymousInnerClass$2.txt
 */
public class AnonymousInnerClass {
	
	// 基于接口的匿名内部类，作为成员变量，AnonymousInnerClass$1.class
	I2 i = new I2() {
		
		int i;
		
		@Override
		public void method() {
			
		}
	};
	
	public static void main(String[] args) {
		
		int num = 10;
		
		// 作为局部变量，会被编译成AnonymousInnerClass$2.class
		I2 i = new I2() {
			@Override
			public void method() {
				
				// 匿名内部类中是能访问到外面的匿名内部类的引用的
				
				// i = 10; // Eclipse会提示：Type mismatch: cannot convert from int to I2
				
				// System.out.println(i); // The local variable i may not have been initialized
				
				int i = num; // 隐藏了外部的i，而不是main方法中的i
				System.out.println(i);
			}
		};
	}
}

interface I2 {
	void method();
}