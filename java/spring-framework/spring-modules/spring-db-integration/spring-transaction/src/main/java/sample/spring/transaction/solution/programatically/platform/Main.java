package sample.spring.transaction.solution.programatically.platform;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * using-transaction-manager-directly
 * transaction-management-with-jdbc
 * @author someone
 */
public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);
		
		DataSource dataSource = context.getBean(DataSource.class);
		
		System.out.println(dataSource);
		
		
//		AccountDaoImpl daoImpl = context.getBean(AccountDaoImpl.class);
		
//		daoImpl.transferMoney1("1", "2", 200);
	}
}