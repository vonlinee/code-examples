package sample.spring.transaction.propagation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionPropagation {

    public static void main(String[] args) {
        // 编译报错：https://www.cnblogs.com/banml/p/15411929.html
        System.setProperty("jps.track.ap.dependencies", "true");
        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);
        // JDK代理
        // IAccountService accountService = context.getBean(IAccountService.class);
        // JdkDynamicAopProxy
        // accountService.batchTransferMoney1("zs", "ls", 200.0, 10);

        // CGlib代理  没有实现接口
        AccountService cglibService = context.getBean(AccountService.class);
        cglibService.batchTransferMoney1("zs", "ls", 200.0, 10);

    }
}
