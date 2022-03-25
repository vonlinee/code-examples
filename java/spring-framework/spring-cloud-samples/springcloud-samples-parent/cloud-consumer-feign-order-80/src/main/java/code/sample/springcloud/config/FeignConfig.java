package code.sample.springcloud.config;

import feign.Logger;	//不要导错包
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL; //打印最详细的日志
    }
}
