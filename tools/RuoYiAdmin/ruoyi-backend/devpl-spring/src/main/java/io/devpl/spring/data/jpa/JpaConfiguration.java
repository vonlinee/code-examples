package io.devpl.spring.data.jpa;

import com.alibaba.druid.pool.DruidDataSource;
import io.devpl.spring.data.jdbc.DataSourceConfiguration;
import io.devpl.spring.data.jdbc.DbType;
import org.hibernate.dialect.MySQL5Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * EntityManager是JPA中用于增删改查的接口，它的作用相当于一座桥梁，连接内存中的java对象和数据库的数据存储。
 * 使用EntityManager中的相关接口对数据库实体进行操作的时候， EntityManager会跟踪实体对象的状态，
 * 并决定在特定时刻将对实体的操作映射到数据库操作上面。
 * Hibernate 5版本
 */
@Configuration
@AutoConfigureAfter(DataSourceConfiguration.class)
public class JpaConfiguration {

    private final Logger log = LoggerFactory.getLogger(JpaConfiguration.class);

    private final String[] packagesToScan = {"io.devpl.spring.entity"};

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/devpl?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setDbType(DbType.MySQL5.name());
        return dataSource;
    }

    @Bean
    public JpaProperties jpaProperties() {
        JpaProperties props = new JpaProperties();
        // 这个参数是在建表的时候，将默认的存储引擎切换为 InnoDB 用的
        props.setDatabasePlatform(MySQL5Dialect.class.getName());
        // 配置在日志中打印出执行的 SQL 语句信息。
        props.setShowSql(true);
        props.setGenerateDdl(true);
        props.setOpenInView(true);
        return props;
    }

    // @Bean
    public HibernateProperties hibernateProperties() {
        HibernateProperties hibernateProperties = new HibernateProperties();
        // #配置指明在程序启动的时候要删除并且创建实体类对应的表
        hibernateProperties.setDdlAuto("update");
        // 命名策略
        // 显示命名策略 spring.jpa.hibernate.naming.implicit-strategy
        hibernateProperties.getNaming().setImplicitStrategy(HibernateNamingStrategy.class.getName());
        // 物理命名策略 spring.jpa.hibernate.naming.physical-strategy
        hibernateProperties.getNaming().setPhysicalStrategy(HibernateNamingStrategy.class.getName());
        return hibernateProperties;
    }
}
