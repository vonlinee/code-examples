package io.devpl.auth;

import io.devpl.auth.config.ConfigProperties;
import io.devpl.auth.config.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StopWatch;

@Slf4j
@SpringBootApplication
@MapperScan("io.devpl.auth.mapper")
@EnableConfigurationProperties({ConfigProperties.class, SmsProperties.class}) // 开启配置注入
@EnableAspectJAutoProxy  // 开启AOP
@EnableAsync  // 开启异步调用（多线程执行），主要用于同步基础平台数据
@EnableScheduling
public class DevplAuthServer {
    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start();
        SpringApplication.run(DevplAuthServer.class, args);
        watch.stop();
        log.info("【项目启动成功，耗时：" + watch.getLastTaskTimeMillis() / 1000.0 + "秒】");
    }
}
