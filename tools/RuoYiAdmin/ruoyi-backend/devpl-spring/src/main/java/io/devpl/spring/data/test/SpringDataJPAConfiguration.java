package io.devpl.spring.data.test;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(JpaRepositoriesAutoConfiguration.class)
public class SpringDataJPAConfiguration {

}
