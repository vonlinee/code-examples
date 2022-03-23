package com.yuhb.customer;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.yuhb.customer.mapper")
@EnableAutoDataSourceProxy  // > seata-1.x.x
public class CustomerApp {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApp.class, args);
    }
}
