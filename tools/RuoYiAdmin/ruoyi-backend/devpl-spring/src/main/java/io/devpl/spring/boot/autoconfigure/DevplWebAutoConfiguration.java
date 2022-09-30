package io.devpl.spring.boot.autoconfigure;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ConditionalOnWebApplication
public class DevplWebAutoConfiguration {

}
