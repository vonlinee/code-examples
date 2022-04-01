package multidatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MultiDataSourcesApplication {

    private static final Logger logger = LoggerFactory.getLogger(MultiDataSourcesApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MultiDataSourcesApplication.class, args);

        Object ds_business = context.getBean("ds_business");
        Object ds_prc = context.getBean("ds_prc");
        Object ds_orc = context.getBean("ds_orc");
        
        System.out.println(ds_business);
        System.out.println(ds_prc);
        System.out.println(ds_orc);
    }
}
