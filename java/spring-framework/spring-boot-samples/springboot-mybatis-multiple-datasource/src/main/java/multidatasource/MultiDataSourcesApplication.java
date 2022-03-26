package multidatasource;

import multidatasource.datasource.DataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("multidatasource.mapper")
public class MultiDataSourcesApplication {

    private static final Logger logger = LoggerFactory.getLogger(MultiDataSourcesApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MultiDataSourcesApplication.class, args);

    }
}
