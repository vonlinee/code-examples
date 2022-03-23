package org.example.springboot.common;

import java.util.UUID;

/**
 * 数据生成
 * @author someone
 */
public abstract class DataGenerator {

	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static String randomUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String randomIdCard() {
		return "";
	}
	
	public static String randomName() {
		return "Hello World!!";
	}
}
