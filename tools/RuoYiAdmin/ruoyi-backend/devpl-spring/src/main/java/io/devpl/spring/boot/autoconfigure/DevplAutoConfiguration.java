package io.devpl.spring.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ImportResource;

/**
 * Devpl-Spring所有自动配置的入口
 */
@ConditionalOnProperty(name = "devpl.enable", havingValue = "true")
@ImportResource(locations = {
        "classpath:devpl-spring.xml"
})
public class DevplAutoConfiguration {

}
