package io.maker.base.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.springframework.core.SpringVersion;

import io.maker.base.lang.meta.ClassDefinition;
import io.maker.base.lang.meta.MetadataUtils;
import io.maker.base.lang.meta.TestClass;
import lombok.Data;

public class Test1 {
	
	public static void main(String[] args) {

//		ClassDefinition cd = MetadataUtils.parse(TestClass.class);
//		
//		String documentation = cd.getDocumentation();
//		

		test5();
		
	}
	
	
	public static void test5() {
		AnnotatedType[] annotatedInterfaces = Data.class.getAnnotatedInterfaces();
		
		AnnotatedType superAnnotatedType = Data.class.getAnnotatedSuperclass();
		
		Class<? super Data> superclass = Data.class.getSuperclass();
		
		Type genericSuperclass = Data.class.getGenericSuperclass();
		
		Annotation[] annotations = Data.class.getAnnotations();
		
		Method[] methods = Data.class.getMethods();
		
	}
	
	public static void test2(Object value) {
		Class<? extends Object> clazz = value.getClass();
		System.out.println(clazz);
	}
	
	public static void test1() {
		Class<Integer> c1 = int.class;
		
		System.out.println(c1);
		
		Integer i1 = new Integer(10);
		Integer i2 = Integer.valueOf(10);
		Integer i3 = 10;  // 实际调用的是Integer.valueOf(10)
		System.out.println(i1 == i2); // false
		System.out.println(i2 == i3); // true
		System.out.println(i1 == i3); // false
		
		System.out.println("===============================");
		
		Integer i11 = new Integer(129);
		Integer i22 = Integer.valueOf(129);
		Integer i33 = 129;
		
		System.out.println(i1.getClass());
		System.out.println(i11 == i22); // false
		System.out.println(i22 == i33); // true
		System.out.println(i11 == i33); // false
	}
}
