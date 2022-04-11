package sample.spring.transaction.problem;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 演示单一数据源事务问题
 * 使用注解配置，其他配置方式也类似
 */
@Configuration
public class TransactionProblem {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/db_mysql?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean(name= {"account-dao-1"})
	public IAccountDao accountDao() {
		return new AccountDaoImpl();
	}
	
	@Autowired
	public IAccountDao accountDao;
	
	public void transferMoney() {
		accountDao.outMoney("zs", BigDecimal.valueOf(200.0));
		int i = 1 / 0; //演示事务问题
		accountDao.inMoney("ls", BigDecimal.valueOf(200.0));
	}
	
	public void transferMoney1() {
		accountDao.outMoney("zs", BigDecimal.valueOf(200.0));
		accountDao.inMoney("ls", BigDecimal.valueOf(200.0));
		int i = 1 / 0; //演示事务问题
	}
	
	public static void main(String[] args) {
		ApplicationContext context =  new AnnotationConfigApplicationContext(TransactionProblem.class);
		TransactionProblem problem = context.getBean(TransactionProblem.class);
		problem.transferMoney();
	}
}
