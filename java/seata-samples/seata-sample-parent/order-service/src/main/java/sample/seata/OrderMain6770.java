package sample.seata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.seata.entity.Order;
import sample.seata.service.OrderService;
import tk.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;

@RestController
@SpringBootApplication
@MapperScan("sample.seata.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@RequestMapping("/order")
public class OrderMain6770 {

    @Autowired
    private OrderService orderService;

    //http://localhost:6770/order/create
    //http://localhost:6770/order/create?userId=100&prodId=10000
    @GetMapping("/create")
    public Boolean create(long userId, long productId) {
        Order order = new Order();
        order.setCount(1).setMoney(BigDecimal.valueOf(88)).setProductId(productId).setUserId(userId).setStatus(0);
        return orderService.create(order);
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderMain6770.class, args);
    }
}
