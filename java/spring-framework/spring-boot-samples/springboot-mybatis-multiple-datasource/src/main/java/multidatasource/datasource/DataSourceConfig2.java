package multidatasource.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "multidatasource.mapper.ordercenter", sqlSessionFactoryRef = "SqlSessionFactory_orc")
public class DataSourceConfig2 {
	
	@Bean(name = "ds_orc")
	@ConfigurationProperties(prefix = "spring.datasource.ordercenter")
	public DataSource getDateSource2() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "SqlSessionFactory_orc")
	public SqlSessionFactory test2SqlSessionFactory(@Qualifier("ds_orc") DataSource datasource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(datasource);
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapping/**/*.xml"));
		return bean.getObject();
	}

	@Bean("SqlSessionTemplate_orc")
	public SqlSessionTemplate test2sqlsessiontemplate(
			@Qualifier("SqlSessionFactory_orc") SqlSessionFactory sessionfactory) {
		return new SqlSessionTemplate(sessionfactory);
	}
}
