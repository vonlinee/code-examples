package sample.spring.transaction.solution.declaratively.annotation;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 1.需要DataSource 
 * 2.需要TransactionManager
 */
public class DeclarativeTransactionAnnotation {

    static {
    	// JPS增量注释
        System.setProperty("jps.track.ap.dependencies", "false");
    }

    private static final ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);

    public static void main(String[] args) {
    	printDataSourceInformation();
        test1();
        // test2();
    }

    public static void printDataSourceInformation() {
    	DataSource dataSource = SpringContext.getBean(DataSource.class);
    	System.out.println(dataSource);
    }
    
    /**
     * @Transactional 原理
     */
    public static void test1() {
    	IAccountService serviceImpl = context.getBean(IAccountService.class);
        // serviceImpl.transferMoney("zs", "ls", 200.0);
    }

    /**
     * @Transactional 失效测试
     */
    public static void test2() {
    	IAccountService serviceImpl = context.getBean(IAccountService.class);
        // serviceImpl.transferMoney("zs", "ls", 200.0);
        // serviceImpl.transferMoney("zs", "ls", 200.0);
    }
    
    /**
     * @Transactional 加在类上原理
     */
    public static void test3() {
    	IAccountService serviceImpl = context.getBean(IAccountService.class);
        // serviceImpl.transferMoney("zs", "ls", 200.0);
//        serviceImpl.transfer("zs", "ls", 200.0);
    }

    /**
     * @Transactional 加在类上原理
     */
    public static void test4() {
    	IAccountService serviceImpl = context.getBean(IAccountService.class);
        // serviceImpl.transferMoney("zs", "ls", 200.0);
    	serviceImpl.transferMoney("zs", "ls", BigDecimal.valueOf(200.0));
    }
}
