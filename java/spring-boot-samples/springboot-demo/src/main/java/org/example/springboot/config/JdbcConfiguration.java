package org.example.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Configuration
public class JdbcConfiguration {

//	@Bean
//	@Autowired
//	@Primary
//	public PlatformTransactionManager transactionManager(DataSource dataSource) {
//		//定义一个某个框架平台的TransactionManager，如JDBC、Hibernate
//		DataSourceTransactionManager dstm = new DataSourceTransactionManager(); 
//		dstm.setDataSource(dataSource); // 设置数据源
//	    return dstm;
//	}
//	
//	@Bean
//	@Primary
//	TransactionDefinition transactionDefinition() {
//		// 定义事务属性
//	    DefaultTransactionDefinition dtd = new DefaultTransactionDefinition(); 
//	    // 设置传播行为属性
//	    dtd.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
//		return dtd;
//	}
}
