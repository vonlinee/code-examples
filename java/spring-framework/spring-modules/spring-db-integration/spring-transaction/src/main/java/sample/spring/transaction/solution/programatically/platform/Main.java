package sample.spring.transaction.solution.programatically.platform;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * using-transaction-manager-directly
 * transaction-management-with-jdbc
 * 
 * 使用TransactionTemplate比使用PlatformTransactionManager更简单
 * 将一些模板方法封装到了TransactionTemplate中
 * 这两种方式都是编程式事务
 */
public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);
		
		DataSource dataSource = context.getBean(DataSource.class);
		
		System.out.println(dataSource);
		
		
//		AccountDaoImpl daoImpl = context.getBean(AccountDaoImpl.class);
		
//		daoImpl.transferMoney1("1", "2", 200);
	}
}