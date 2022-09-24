package io.devpl.spring.context.properties;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * {@code @ConfigurationProperties} 注解的类只有放入容器才有作用，比如使用@Component注解
 */
@Data
@Component
@ConfigurationProperties(prefix = "devpl")
public class DevplProperties implements InitializingBean {

    /**
     * Devpl是否开启
     */
    private boolean enabled;

    /**
     * 服务ID
     */
    private String serviceId;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
