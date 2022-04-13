package sample.spring.transaction.propagation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionPropagation {

    public static void main(String[] args) {
        System.setProperty("jps.track.ap.dependencies", "true");
        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);
        IAccountService accountService = context.getBean(IAccountService.class);
        accountService.batchTransferMoney1("zs", "ls", 200.0, 10);
    }
}
