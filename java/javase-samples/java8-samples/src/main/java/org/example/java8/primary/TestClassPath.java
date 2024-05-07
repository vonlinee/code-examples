package org.example.java8.primary;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class TestClassPath {

	public static void main(String[] args) throws Exception {

		TestClassPath test = new TestClassPath();
		test.getClasspath();
	}
	
	public void getClasspath() throws IOException {
		// 第一种：获取类加载的根路径 D:\git\daotie\daotie\target\classes
		File f = new File(this.getClass().getResource("/").getPath());
		System.out.println(f);

		// 获取当前类的所在工程路径; 如果不加“/” 获取当前类的加载目录 D:\git\daotie\daotie\target\classes\my
		File f2 = new File(this.getClass().getResource("").getPath());
		System.out.println(f2);

		// 第二种：获取项目路径 D:\git\daotie\daotie
		File directory = new File("");// 参数为空
		String courseFile = directory.getCanonicalPath();
		System.out.println(courseFile);

		// 第三种： file:/D:/git/daotie/daotie/target/classes/
		URL xmlpath = this.getClass().getClassLoader().getResource("");
		System.out.println(xmlpath);

		// 第四种： D:\git\daotie\daotie
		Arrays.asList(System.getProperty("user.dir").split(";")).forEach(System.out::println);
		/*
		 * 结果： C:\Documents and Settings\Administrator\workspace\projectName 获取当前工程路径
		 */

		// 第五种： 获取所有的类路径 包括jar包的路径
		String classpath = System.getProperty("java.class.path");
		Arrays.asList(classpath.split(";")).forEach(System.out::println);
	}
}
