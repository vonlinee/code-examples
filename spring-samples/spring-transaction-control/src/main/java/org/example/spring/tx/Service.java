package org.example.spring.tx;

import org.example.spring.tx.service.AccountServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:bean.xml")
public class Service {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		AccountServiceImpl serviceImpl = context.getBean(AccountServiceImpl.class);
//		serviceImpl.transferMoney("zs", "ls", 200.0);
//		serviceImpl.transfer("zs", "ls", 200.0);
		
//		context.getBean(AccountServiceImpl.class).transferMoney("zs", "ls", 200.0);
		
		AccountServiceImpl impl = SpringContext.getBean(AccountServiceImpl.class);
		
//		impl.transferMoney("zs", "ls", 200.0);
		
		
		Object bean = context.getBean("obj");
		System.out.println(bean);
		
	}
}
