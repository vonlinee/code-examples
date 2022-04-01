package multidatasource.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
// 配置mybatis的接口类放的地方
// 在指定使用sqlSessionFactoryRef的情况下，这里有一个或多个的Spring的容器。经常我们会使用一个或多个的数据库.
@MapperScan(basePackages = "multidatasource.mapper.*", sqlSessionFactoryRef = "SqlSessionFactory_business")
public class DataSourceConfig implements InitializingBean {

    @Bean(name = "ds_business")
    @Primary  // 表示这个数据源是默认数据源
    // 读取application.properties中的配置参数映射成为一个对象,prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.business")
    public DataSource businessDateSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "SqlSessionFactory_business")
    @Primary  // 表示这个数据源是默认数据源
    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory bussinessSqlSessionFactory(@Qualifier("ds_business") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapping/**/*.xml"));
        return bean.getObject();
    }

    @Bean("SqlSessionTemplate_business")
    // 表示这个数据源是默认数据源
    @Primary
    public SqlSessionTemplate bussinessSqlsessiontemplate(
            @Qualifier("SqlSessionFactory_business") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

    // ==========================================
    @Bean(name = "ds_prc")
    // 读取application.properties中的配置参数映射成为一个对象,prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.productcenter")
    public DataSource getDateSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "SqlSessionFactory_prc")  // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("ds_prc") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapping/**/*.xml"));
        return bean.getObject();
    }

    @Bean("SqlSessionTemplate_prc") // 表示这个数据源是默认数据源
    public SqlSessionTemplate test1sqlsessiontemplate(
            @Qualifier("SqlSessionFactory_prc") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

    // ==========================  ORC 数据源配置  ===================================

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

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
