package sample.spring.aop.programing;

import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAop {
	

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(MainConfig.class);
		
		MyPointCut pointCut = context.getBean(MyPointCut.class);
		
		Map<String, String> envMap = System.getenv();
		
		String paths = System.getenv().get("PATH");
		
		
		for (String path : paths.split(";")) {
			System.out.println(path);
		}
		
		
		
	}
	
}
