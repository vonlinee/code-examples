package sample.spring.transaction.propagation;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import sample.spring.transaction.utils.SpringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScans({
        @ComponentScan("sample.spring.transaction.propagation"),
        @ComponentScan("sample.spring.transaction.utils")
})
@PropertySource("classpath:jdbc.properties")
@Import(SpringUtils.class)
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Bean(name = "transactionManager")
    public PlatformTransactionManager createTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // 声明式事务不需要事务模板
    @Bean(name = "transactionTemplate")
    public TransactionTemplate createTransactionTemplate(PlatformTransactionManager txManager) {
        return new TransactionTemplate(txManager);
    }

    @Bean(name = "springDataSource")
    public DataSource createDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    // 使用此数据源
    @Bean
    @Qualifier("dbcpDataSource")
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/db_mysql?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
        ds.setUsername("root");
        ds.setPassword("123456");
        return ds;
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) throws SQLException {
        LOG.info("Init JdbcTemplate with DataSource => " + dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplate.setLazyInit(true);
        return jdbcTemplate;
    }
}
