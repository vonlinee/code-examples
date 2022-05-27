package sample.spring.boot.h2.config;

import java.io.File;

public class Test {
	
	public static void main(String[] args) {
		
		File file = new File("C:\\Users\\ly-wangliang\\Desktop\\code-samples\\java\\spring-framework\\spring-boot-samples\\springboot-config-detail\\.settings");
		
		boolean delete = file.delete();
		
		System.out.println(delete);
	}
}
