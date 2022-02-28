package code.sample.spring.transaction.declaratively.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import code.sample.spring.transaction.business.service.AccountServiceImpl;
import code.sample.spring.transaction.declaratively.annotation.config.MainConfiguration;

public class Main {
	
	static ApplicationContext context = 
			new AnnotationConfigApplicationContext(MainConfiguration.class);
	
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		test1();
	}
	
	public static void test1() {
		AccountServiceImpl serviceImpl = context.getBean(AccountServiceImpl.class);
		LOG.info(serviceImpl.getClass().toString()); //AccountServiceImpl$$EnhancerBySpringCGLIB$$13744cf5
//		serviceImpl.transferMoney("zs", "ls", 200.0);
		serviceImpl.transfer("zs", "ls", 200.0);
	}
}

//1.需要DataSource
//2.需要TransactionManager