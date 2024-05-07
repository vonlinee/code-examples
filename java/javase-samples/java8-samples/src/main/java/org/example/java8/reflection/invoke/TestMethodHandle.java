package org.example.java8.reflection.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class TestMethodHandle {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		MethodHandle mh;
		
		Method privateLookupIn = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
		
		System.out.println(privateLookupIn);
	}
}
