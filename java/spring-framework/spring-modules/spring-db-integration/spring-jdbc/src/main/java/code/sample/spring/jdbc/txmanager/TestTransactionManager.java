package code.sample.spring.jdbc.txmanager;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class TestTransactionManager {
    public static void main(String[] args) {
        PlatformTransactionManager txmanager = new DataSourceTransactionManager();

    }
}
