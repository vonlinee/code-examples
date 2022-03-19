package sample.spring.integration.mybatis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 集成的基本思想就是将MyBatis需要的一些组件放到Spring容器中管理，具体的实现可以看
 * mybatis-spring这个依赖中做了什么
 * 
 * 将 MyBatis与 Spring 进行整合，主要解决的问题就是将 SqlSessionFactory 对象交由 Spring来管理。
 * 所以，该整合，只需要将 SqlSessionFactory 的对象生成器 SqlSessionFactoryBean 注册在 Spring 容器中，
 * 再将其注入给 Dao 的实现类即可完成整合，实现 Spring 与 MyBatis 的整合常用的方式：扫描的 Mapper 动态代理
 * 
 * 1.注解方式
 * 2.XML配置方式: 参考spring-config.xml和spring-mybatis.xml
 */
@Configuration
@ComponentScan("sample.spring.integration.mybatis")
public class MainConfiguration {

	
	
	
}
