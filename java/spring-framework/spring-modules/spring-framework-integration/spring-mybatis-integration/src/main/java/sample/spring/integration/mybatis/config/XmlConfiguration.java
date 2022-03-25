package sample.spring.integration.mybatis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = {"classpath:spring-config.xml"})
public class XmlConfiguration {

}
