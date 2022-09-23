package io.devpl.spring.data.jpa;

import io.devpl.spring.data.jdbc.DataSourceConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hibernate 5版本
 */
@Slf4j
@AutoConfigureAfter(DataSourceConfiguration.class)
@Configuration(proxyBeanMethods = false)
public class JpaConfiguration {

    @Bean
    public JpaProperties jpaProperties() {
        JpaProperties props = new JpaProperties();
        // 这个参数是在建表的时候，将默认的存储引擎切换为 InnoDB 用的
        props.setDatabasePlatform(MySQL8Dialect.class.getName());
        // 配置在日志中打印出执行的 SQL 语句信息。
        props.setShowSql(true);
        props.setGenerateDdl(true);
        props.setOpenInView(true);
        return props;
    }

    @Bean
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
