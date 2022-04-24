package sample.netty.chapter4;

public class Test1 {

	public static void main(String[] args) {
		
		// A instanceof B表达式判断A是否为B的实例
		// A应该是对象，而B是类
		
		System.out.println("" instanceof String); // true
		System.out.println(String.class instanceof Class); // true，这里不用加泛型
		
		// 不兼容会直接报错，编译不通过 Incompatible conditional operand types Class<String> and String
		// System.out.println("" instanceof Class); 
		// System.out.println(String.class instanceof String); 
		
		System.out.println("" instanceof Comparable); // true，兼容的类型，可以进行类型转换
		
		// 不兼容
		//System.out.println(10 instanceof Double);
		//System.out.println(10 instanceof Integer);
		
		Integer i = 10;
		System.out.println(i instanceof Integer); // true
		// System.out.println(i instanceof Double);  不兼容
		// System.out.println(i instanceof Long);  不兼容
		
		// JSL-15.20.2规定
		// The type of the RelationalExpression operand of the instanceof operator must be a reference type 
		// or the null type, or a compile-time error occurs.
		// It is a compile-time error if the ReferenceType mentioned after the instanceof operator does not 
		// denote a reference type that is reifiable (§4.7).
		// If a cast of the RelationalExpression to the ReferenceType would be rejected as a compile-time 
		// error (§15.16), then the instanceof relational expression likewise produces a compile-time error. 
		// In such a situation, the result of the instanceof expression could never be true.
		
		// JSL-15.20.2规定
		// At run time, the result of the instanceof operator is true if the value of the RelationalExpression 
		// is not null and the reference could be cast to the ReferenceType without raising a ClassCastException. 
		// Otherwise the result is false.
		System.out.println(null instanceof Object);
		
		
		
		// 编译器并不是万能的，并不能检测出所有问题
		
		
		
		java.sql.Date d1 = new java.sql.Date(10);
		
		java.util.Date d2 = new java.util.Date();
		
		// java.sql.Date d3 = (java.sql.Date) d2; // 向下
		
		// System.out.println(d3.getClass());
		
		
		System.out.println("============================================");
		
		System.out.println(String.class.isInstance("A"));
		
		System.out.println(java.util.Date.class.isInstance(d1)); // true
		
		System.out.println(java.util.Date.class.isInstance(null)); // false
		
		java.sql.Date d3 = null;
		System.out.println(java.util.Date.class.isInstance(d3)); // false
		
	}
	
	
	
	
	
	
	
	
	
}
