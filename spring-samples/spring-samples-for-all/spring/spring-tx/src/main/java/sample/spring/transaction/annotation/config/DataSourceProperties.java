package sample.spring.transaction.annotation.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author : heibaiying
 */

@Configuration(value = "")
@PropertySource(value = "classpath:jdbc.properties")
@Data
public class DataSourceProperties {
    @Value("${mysql.driverClassName}")
    private String driverClassName;
    @Value("${mysql.url}")
    private String url;
    @Value("${mysql.username}")
    private String username;
    @Value("${mysql.password}")
    private String password;
}
