package code.sample.spring.transaction.annotation.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScans({
	@ComponentScan("")
})
public class MainConfiguration {

	@Bean
	DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/mybatis_learn?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
		ds.setUsername("root");
		ds.setPassword("123456");
		return ds;
	}
	
	@Bean
	@Autowired
	JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		jdbcTemplate.setLazyInit(true);
		return jdbcTemplate;
	}
}
