package org.example.jvm.loadclass;

import sun.net.spi.nameservice.dns.DNSNameService;

public class TestClassLoader {

	public static void main(String[] args) {
		test3();
	}

	public static void test1() {
		System.out.println(String.class.getClassLoader());
		String bootClassPath = System.getProperty("sun.boot.class.path");
		// 分隔符 Windows用; Linux用:
		for (String path : bootClassPath.split(";")) {
			System.out.println(path);
		}
	}

	public static void test2() {
		ClassLoader classLoader = DNSNameService.class.getClassLoader();
		System.out.println(classLoader); //sun.misc.Launcher$ExtClassLoader@70dea4e
		String extDirs = System.getProperty("java.ext.dirs");
		for (String path : extDirs.split(";")) {
			System.out.println(path);
		}
		//D:\Develop\Environment\JDK\jdk8u301\jre\lib\ext
		//C:\WINDOWS\Sun\Java\lib\ext
	}
	
	public static void test3() {
		ClassLoader appClassLoader = TestClassLoader.class.getClassLoader();
		ClassLoader extClassLoader = DNSNameService.class.getClassLoader();
		ClassLoader appParent = appClassLoader.getParent();  // true
		System.out.println(appParent == extClassLoader);
		System.out.println(appClassLoader); //sun.misc.Launcher$AppClassLoader@73d16e93
		//D:\Develop\Projects\Github\code-org.example\javase-samples\jvm-sample\bin
		String extDirs = System.getProperty("java.class.path");
		for (String path : extDirs.split(";")) {
			System.out.println(path);
		}
	}
}
