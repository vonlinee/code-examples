package io.doraemon.pocket.generator.utils;

import org.openjdk.jol.info.ClassLayout;

public class JolUtils {
	
	public static void printInstance(Object instance) {
		System.out.println(ClassLayout.parseInstance(instance).toPrintable());
	}
	
}
