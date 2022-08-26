package io.devpl.sdk.support.spring;

import io.devpl.sdk.support.spring.condition.DevplCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Slf4j
@Configuration
@EnableConfigurationProperties(DevplProperties.class)
@PropertySource(value = {"classpath:devpl.properties"}, factory = DevplPropertyFactory.class)
public class DevplBootstrapConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this);
    }
}
