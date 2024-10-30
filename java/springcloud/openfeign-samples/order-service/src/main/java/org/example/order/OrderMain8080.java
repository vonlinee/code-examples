package org.example.order;

import feign.Client;
import org.example.order.remote.FeignPaymentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientFactoryBean;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.HashMap;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OrderMain8080 {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OrderMain8080.class);

//        Object bean1 = context.getBean("payment");
//        System.out.println(bean1);
        FeignPaymentService service = context.getBean(FeignPaymentService.class);
        HashMap<String, Object> map = new HashMap<>();

        long s1 = System.currentTimeMillis();
        service.createPayment(map);
        long e1 = System.currentTimeMillis();
        System.out.println((e1 - s1) + " ms");

        long s2 = System.currentTimeMillis();
        service.createPayment(map);
        long e2 = System.currentTimeMillis();
        System.out.println((e2 - s2) + " ms");

        String[] beanNamesForType = context.getBeanNamesForType(Client.class);

        System.out.println(Arrays.toString(beanNamesForType));
    }
}
