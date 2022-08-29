package io.devpl.sdk.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Configuration
@EnableConfigurationProperties(DevplProperties.class)
@PropertySource(value = {"classpath:devpl.properties", "classpath:devpl.yml", "classpath:devpl.xml"}, factory = DevplPropertyFactory.class, encoding = "UTF-8")
public class DevplConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
