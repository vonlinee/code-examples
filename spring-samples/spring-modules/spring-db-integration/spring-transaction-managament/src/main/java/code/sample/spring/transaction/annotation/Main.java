package code.sample.spring.transaction.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import code.sample.spring.transaction.annotation.config.MainConfiguration;
import code.sample.spring.transaction.annotation.config.SpringContext;
import code.sample.spring.transaction.business.service.AccountServiceImpl;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(MainConfiguration.class);
//		AccountServiceImpl serviceImpl = context.getBean(AccountServiceImpl.class);
//		serviceImpl.transferMoney("zs", "ls", 200.0);
//		serviceImpl.transfer("zs", "ls", 200.0);
		
//		context.getBean(AccountServiceImpl.class).transferMoney("zs", "ls", 200.0);
		
		AccountServiceImpl impl = SpringContext.getBean(AccountServiceImpl.class);
		impl.transferMoney("zs", "ls", 200.0);
		
	}
}
