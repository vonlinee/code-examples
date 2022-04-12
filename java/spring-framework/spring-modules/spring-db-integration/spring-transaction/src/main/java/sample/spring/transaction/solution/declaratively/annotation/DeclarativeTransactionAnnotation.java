package sample.spring.transaction.solution.declaratively.annotation;

import java.math.BigDecimal;
import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 1.需要DataSource 2.需要TransactionManager
 */
public class DeclarativeTransactionAnnotation {

	static {
		// JPS增量注释
		System.setProperty("jps.track.ap.dependencies", "false");
	}

	private static final ApplicationContext context = new AnnotationConfigApplicationContext(
			DataSourceConfiguration.class);

	public static void main(String[] args) {
		printDataSourceInformation();
		test1();
		// test2();
	}

	/**
	 * @Transactional 原理
	 */
	public static void test1() {
		IAccountService serviceImpl = context.getBean(IAccountService.class);
		serviceImpl.transferMoney("zs", "ls", 200.0);
	}

	/**
	 * @Transactional 失效测试
	 */
	public static void test2() {
		IAccountService serviceImpl = context.getBean(IAccountService.class);
		 serviceImpl.transferMoney("zs", "ls", 200.0);
	}

	/**
	 * @Transactional 加在类上原理
	 */
	public static void test3() {
		IAccountService serviceImpl = context.getBean(IAccountService.class);
		// serviceImpl.transferMoney("zs", "ls", 200.0);
		// serviceImpl.transfer("zs", "ls", 200.0);
	}

	/**
	 * @Transactional 加在类上原理
	 */
	public static void test4() {
		IAccountService serviceImpl = context.getBean(IAccountService.class);
		// serviceImpl.transferMoney("zs", "ls", 200.0);
		serviceImpl.transferMoney("zs", "ls", BigDecimal.valueOf(200.0));
	}

	public static void printDataSourceInformation() {
		DataSource dataSource = null;
		try {
			dataSource = SpringContext.getBean(DataSource.class);
		} catch (Exception e) {
			if (e instanceof NoUniqueBeanDefinitionException) {
				String message = e.getMessage();
				String[] split = message.split(":");
				if (split.length >= 2) {
					String trim = split[split.length - 1].trim();
					String[] split2 = trim.split(",");
					for (String beanName : split2) {
						dataSource = SpringContext.getBean(beanName.trim(), DataSource.class);
						System.out.println(dataSource.toString());
						try (Connection connection = dataSource.getConnection()) {
							System.out.println(connection);
						} catch (Exception e2) {
							System.exit(0);
						}
					}
				}
			}
		}
	}
}
