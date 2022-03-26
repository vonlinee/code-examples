package multidatasource;

import multidatasource.common.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import({DynamicDataSourceRegister.class, ProxyTransactionManagementConfiguration.class})
public class Main8888 {
    public static void main(String[] args) {
        SpringApplication.run(Main8888.class, args);
    }
}
