package code.example.springcloud.alibaba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EntityScan("com.bi.cloud.pojo")
//@MapperScan("com.bi.cloud.dao")
public class NacosClientMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(NacosClientMain8001.class, args);
    }
}