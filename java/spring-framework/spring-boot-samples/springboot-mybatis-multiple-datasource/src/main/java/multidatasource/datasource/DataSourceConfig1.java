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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

//表示这个类为一个配置类
@Configuration
// 配置mybatis的接口类放的地方
@MapperScan(basePackages = "multidatasource.mapper.productcenter", sqlSessionFactoryRef = "test1SqlSessionFactory")
public class DataSourceConfig1 {
	
	@Bean(name = "ds_prod")
	@Primary  // 表示这个数据源是默认数据源
	// 读取application.properties中的配置参数映射成为一个对象,prefix表示参数的前缀
	@ConfigurationProperties(prefix = "spring.datasource.productcenter") 
	public DataSource getDateSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "prod_SqlSessionFactory")
	// 表示这个数据源是默认数据源
	@Primary
	// @Qualifier表示查找Spring容器中名字为test1DataSource的对象
	public SqlSessionFactory test1SqlSessionFactory(@Qualifier("ds_prod") DataSource datasource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(datasource);
		bean.setMapperLocations(
				// 设置mybatis的xml所在位置
				new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapping/**/*.xml"));
		return bean.getObject();
	}

	@Bean("SqlSessionTemplate_prod")
	// 表示这个数据源是默认数据源
	@Primary
	public SqlSessionTemplate test1sqlsessiontemplate(
			@Qualifier("SqlSessionFactory_prod") SqlSessionFactory sessionfactory) {
		return new SqlSessionTemplate(sessionfactory);
	}
}
