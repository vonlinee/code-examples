package io.devpl.spring.boot.factories.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

/**
 * 在SpringApplicationRunListener#contextPrepared之前执行
 * 执行顺序：
 * 1.ApplicationContextInitializer#initialize
 * 2.SpringApplicationRunListener#contextPrepared
 * 3.ApplicationListener<BootstrapContextClosedEvent>#onApplicationEvent
 * https://www.cnblogs.com/duanxz/p/11239291.html
 */
public class DevplContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    private static final Logger log = LoggerFactory.getLogger(DevplContextInitializer.class);

    /**
     * 在spring容器刷新之前执行的一个回调函数
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; // 越小越先执行
    }
}
