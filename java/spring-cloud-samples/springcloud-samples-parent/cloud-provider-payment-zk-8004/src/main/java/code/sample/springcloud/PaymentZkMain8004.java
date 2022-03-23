package code.sample.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient  // 该注解用于向使用consul或者Zookeeper作为注册中心时注册服务
public class PaymentZkMain8004 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentZkMain8004.class, args);
    }
}
/*
解决方法
https://blog.csdn.net/renkai721/article/details/112257894

















 */
// springboot2启动项目报错，应该是数据库连接的问题，导致无法启动

// 第一种，项目不需要连接数据库，启动报错
// 只要在将@SpringBootApplication修改为@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
// 就可以启动的时候不需要连接数据库。

// 第2种，需要连接数据库，启动报错

// #在application.properties/或者application.yml文件中没有添加数据库配置信息.
//         spring:
//         datasource:
//         url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false
//         username: root
//         password: 123456
//         driver-class-name: com.mysql.jdbc.Driver