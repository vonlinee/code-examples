package multidatasource.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Data;
 
/**
 * 主数据源配置
 * https://blog.csdn.net/jt_s8645/article/details/84569014
 */
@Data
//@Configuration
//@ConfigurationProperties(prefix = "primary.datasource.druid") // 前缀为primary.datasource.druid的配置信息
//@MapperScan(basePackages = PrimaryDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "primarySqlSessionFactory")
public class PrimaryDataSourceConfig {
 
    /**
     * dao层的包路径
     */
    static final String PACKAGE = "com.dabo.mini.game.zhaxinle.dao.primary";
 
    /**
     * mapper文件的相对路径
     */
    private static final String MAPPER_LOCATION = "classpath:mappers/primary/*.xml";
 
    private String filters;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
 
    // 主数据源使用@Primary注解进行标识
    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource() throws SQLException {
        DruidDataSource druid = new DruidDataSource();
        druid.setFilters(filters); // 监控统计拦截的filters
        // 配置基本属性
        druid.setDriverClassName(driverClassName);
        druid.setUsername(username);
        druid.setPassword(password);
        druid.setUrl(url);
        //初始化时建立物理连接的个数
        druid.setInitialSize(initialSize);
        druid.setMaxActive(maxActive); //最大连接池数量
        druid.setMinIdle(minIdle); //最小连接池数量
        druid.setMaxWait(maxWait); //获取连接时最大等待时间，单位毫秒。
        druid.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis); //间隔多久进行一次检测，检测需要关闭的空闲连接
        druid.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis); //一个连接在池中最小生存的时间
        druid.setValidationQuery(validationQuery); //用来检测连接是否有效的sql
        druid.setTestWhileIdle(testWhileIdle); //建议配置为true，不影响性能，并且保证安全性。
        //申请连接时执行validationQuery检测连接是否有效
        druid.setTestOnBorrow(testOnBorrow);
        druid.setTestOnReturn(testOnReturn);
        //是否缓存preparedStatement，也就是PSCache，oracle设为true，mysql设为false。分库分表较多推荐设置为false
        druid.setPoolPreparedStatements(poolPreparedStatements);
        // 打开PSCache时，指定每个连接上PSCache的大小
        druid.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        return druid;
    }
 
    // 创建该数据源的事务管理
    @Primary
    @Bean(name = "primaryTransactionManager")
    public DataSourceTransactionManager primaryTransactionManager() throws SQLException {
        return new DataSourceTransactionManager(primaryDataSource());
    }
 
    // 创建Mybatis的连接会话工厂实例
    @Primary
    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);  // 设置数据源bean
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(PrimaryDataSourceConfig.MAPPER_LOCATION));  // 设置mapper文件路径
 
        return sessionFactory.getObject();
    }
}