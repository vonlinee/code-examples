package org.example.java8.primary.oop.inner;

/*
1,当内部类定义在外部类的成员位置上，而且非私有，可以在外部其他类中。
可以直接建立内部类对象。
格式
	外部类名.内部类名  变量名 = 外部类对象.内部类对象;
	Outer.Inner in = new Outer().new Inner();

2,当内部类在成员位置上，就可以被成员修饰符所修饰。
	比如，private：将内部类在外部类中进行封装。
		static:内部类就具备static的特性。
		当内部类被static修饰后，只能直接访问外部类中的static成员。出现了访问局限。

		在外部其他类中，如何直接访问static内部类的非静态成员呢？
		new Outer.Inner().function();

		在外部其他类中，如何直接访问static内部类的静态成员呢？
		Outer.Inner.function();

	注意：当内部类中定义了静态成员，该内部类必须是static的。
		  当外部类中的静态方法访问内部类时，内部类也必须是static的。


*/
public class InnerClassDemo1 {
	public static void main(String[] args) {
		Outer.method();
		Outer.Inner1.function();
		new Outer.Inner1().function();

	}
}

class Outer {
	private static int x = 3;

	//静态内部类
	static class Inner1 { 
        static void function() {
            System.out.println("innner :"+x);
        }
    }

	static class Inner2 {
		void show() {
			System.out.println("inner2 show");
		}
	}

	public static void method() {
		Inner1.function();
		new Inner2().show();
	}

}