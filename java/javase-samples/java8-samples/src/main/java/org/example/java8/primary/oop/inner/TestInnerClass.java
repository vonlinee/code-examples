package org.example.java8.primary.oop.inner;

//编译生成的文件如下
//目录: D:\Projects\Github\code-org.example\javase-samples\java8-samples\target\classes\sample\java\primary\oop\inner
//149 I.class
//546 OuterClass1$Inner.class
//413 OuterClass1.class
//805 OuterClass2$1.class   匿名内部类也会生成单独的文件
//805 OuterClass2$2.class	匿名内部类也会生成单独的文件
//640 OuterClass2.class
//967 TestInnerClass.class
public class TestInnerClass {
	public static void main(String[] args) {
		
		OuterClass2 o2 = new OuterClass2();
		
		// 匿名内部类命名：外部类包名.类名$1@{Integer.toHexString(内部类对象的hashCode)}
		o2.i1.method(); //sample.java.primary.oop.inner.OuterClass2$1@15db9742
		o2.i2.method(); //sample.java.primary.oop.inner.OuterClass2$2@15db9742
		
		System.out.println(Integer.toHexString(o2.i1.hashCode()));
	}
}

//后面的类是前面的类的内部类

//1.普通的组合类形式，即在一个类内部定义一个普通的类   
//OuterClass1.class
//OuterClass1$Inner.class
class OuterClass1 {
	
	class Inner {
		
	}
}

//2.在一个类内部定义一个匿名类（一般是接口interface）
//此时会产生以下两个类：
//OuterClass2.class
//OuterClass2$1.class
//其中$1代表的就是Outer类里面的new Test(){}这个匿名类
class OuterClass2 {
	I i1 = new I() {
		@Override
		public void method() {
			System.out.println(this);
			System.out.println("匿名内部类中外部类的this => " + OuterClass2.this);
			
			// i1和i2都是匿名内部类
			System.out.println("匿名内部类中外部类的this.i1 => " + OuterClass2.this.i1);
			System.out.println("匿名内部类中外部类的this.i2 => " + OuterClass2.this.i2);
		}
	};
	
	I i2 = new I() {
		@Override
		public void method() {
			System.out.println(this);
		}
	};
}

interface I {
	void method();
}
