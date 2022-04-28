package io.maker.generator.mybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MyBatisConfiguration {

	private static final String DEFAULT_MAPPER_CLASSPATH_LOCATION = "mybatis/mapping/*.xml";
	
	@Value(value = "${mybatis.mapper.classpathlocation:" + DEFAULT_MAPPER_CLASSPATH_LOCATION + "}")
	public String mapperLocationPattern;
	
	@Bean(name = "sessionFactory")
	public SqlSessionFactory test1SqlSessionFactory(DataSource datasource) throws Exception {
		
		System.out.println(datasource);
		
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(datasource);
		// 设置mybatis的xml所在位置
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocationPattern));
		return bean.getObject();
	}

	@Bean("sqlSessionTemplate")
	@Primary
	public SqlSessionTemplate test1sqlsessiontemplate(
			@Qualifier("sessionFactory") SqlSessionFactory sessionfactory) {
		return new SqlSessionTemplate(sessionfactory);
	}
}
