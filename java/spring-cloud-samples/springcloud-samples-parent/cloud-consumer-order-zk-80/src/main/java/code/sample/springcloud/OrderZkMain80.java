package code.sample.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 浏览器访问的服务
 * @author someone
 */
@SpringBootApplication
//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MySelfRule.class)
public class OrderZkMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderZkMain80.class, args);
    }
}

//http://localhost/consumer/payment/zk
