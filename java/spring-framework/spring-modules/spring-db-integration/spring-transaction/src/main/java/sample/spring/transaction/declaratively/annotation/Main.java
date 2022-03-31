package sample.spring.transaction.declaratively.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import sample.spring.transaction.business.service.AccountServiceImpl;

/**
 * 1.需要DataSource 2.需要TransactionManager
 */
public class Main {

    static {
        System.setProperty("jps.track.ap.dependencies", "false");
    }

    private static final ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);

    public static void main(String[] args) {
        test1();

        // test2();
    }

    /**
     * @Transactional 原理
     */
    public static void test1() {
        AccountServiceImpl serviceImpl = context.getBean(AccountServiceImpl.class);
        // serviceImpl.transferMoney("zs", "ls", 200.0);
        serviceImpl.transfer("zs", "ls", 200.0);
    }

    /**
     * @Transactional 加在类上原理
     */
    public static void test3() {
        AccountServiceImpl serviceImpl = context.getBean(AccountServiceImpl.class);
        // serviceImpl.transferMoney("zs", "ls", 200.0);
        serviceImpl.transfer("zs", "ls", 200.0);
    }

    /**
     * @Transactional 失效测试
     */
    public static void test2() {
        AccountServiceImpl serviceImpl = context.getBean(AccountServiceImpl.class);
        // serviceImpl.transferMoney("zs", "ls", 200.0);
        serviceImpl.transferMoney("zs", "ls", 200.0);
    }
}
