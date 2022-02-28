package code.sample.spring.transaction.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportResource;

import code.sample.spring.transaction.annotation.config.ApplicationConfig;
import code.sample.spring.transaction.annotation.config.SpringContext;
import code.sample.spring.transaction.business.service.AccountServiceImpl;

@ImportResource("classpath:bean.xml")
public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		AccountServiceImpl serviceImpl = context.getBean(AccountServiceImpl.class);
//		serviceImpl.transferMoney("zs", "ls", 200.0);
//		serviceImpl.transfer("zs", "ls", 200.0);
		
//		context.getBean(AccountServiceImpl.class).transferMoney("zs", "ls", 200.0);
		
		AccountServiceImpl impl = SpringContext.getBean(AccountServiceImpl.class);
//		impl.transferMoney("zs", "ls", 200.0);
		
	}
}
