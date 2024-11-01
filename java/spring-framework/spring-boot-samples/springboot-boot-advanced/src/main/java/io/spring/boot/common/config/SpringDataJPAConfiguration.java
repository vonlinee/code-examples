package io.spring.boot.common.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "myEntityManagerFactory", transactionManagerRef = "myTransactionManager", basePackages = {
        "org.springboot.sample.dao"
} // 设置repository所在位置
)
public class SpringDataJPAConfiguration implements ApplicationContextAware {

    @Value("${myuser:root}")
    private String myuser;
    @Value("${mypass:123456}")
    private String mypass;
    @Value("${spring.datasource.driver-class-name}")
    private String mydriver;
    @Value("${spring.datasource.url}")
    private String myurl;

    @Bean
    public HikariDataSource hds() {
        HikariDataSource hds = new HikariDataSource();
        hds.setUsername(myuser);
        hds.setPassword(mypass);
        hds.setJdbcUrl(myurl);
        hds.setDriverClassName(mydriver);
        hds.setAutoCommit(true);
        return hds;
    }

    @Bean
    public Properties prop() {
        Properties prop = new Properties();
        // prop.put("hibernate.connection.driver_class",mydriver);
        // prop.put("hibernate.connection.url",myurl);
        // prop.put("hibernate.connection.username",myuser);
        // prop.put("hibernate.connection.password",mypass);
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.connection.userUnicode", "true");
        prop.put("hibernate.connection.characterEncoding", "UTF-8");
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.use_sql_comments", "true");
        prop.put("hibernate.hbm2ddl.auto", "update");
        prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        prop.put("hibernate.connection.autoReconnect", "true");
        prop.put("hibernate.connection.autoReconnectForPools", "true");
        prop.put("hibernate.connection.is-connection-validation-required", "true");
        prop.put("validationQuery", "SELECT 1");
        prop.put("testOnBorrow", "true");
        return prop;
    }

    @Primary
    @Bean(name = "myEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean myEntityManagerFactory(HikariDataSource hds, Properties prop) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        // 这个扫描的是Entity(JavaBean)的位置，注意与上方的repository区别开
        //@EntityScan(basePackages= {"org.springboot.sample.entity"})
        bean.setPackagesToScan("org.springboot.sample.entity");
        HibernateJpaVendorAdapter hjva = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(hjva);
        bean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        bean.setDataSource(hds);
        bean.setJpaProperties(prop);
        return bean;
    }

    @Primary
    @Bean(name = "myEntityManager")
    public EntityManager myEntityManager(EntityManagerFactory myEntityManagerFactory) {
        return myEntityManagerFactory.createEntityManager();
    }

    @Primary
    @Bean(name = "myTransactionManager")
    public PlatformTransactionManager myTransactionManager(EntityManagerFactory myEntityManagerFactory) {
        JpaTransactionManager jtm = new JpaTransactionManager();
        jtm.setEntityManagerFactory(myEntityManagerFactory);
        return jtm;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("=================");
    }
}
