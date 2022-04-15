package io.spring.boot.common.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 服务启动执行
 */
@Component
@Order(value = 1)
public class ApplicationStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }
}