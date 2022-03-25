package multidatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("multidatasource.mapper")
public class MultiDataSourcesApplication {
	public static void main(String[] args) {
		SpringApplication.run(MultiDataSourcesApplication.class, args);
	}
}
