package code.sample.seata;

import code.sample.seata.entity.Storage;
import code.sample.seata.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;

@RestController
@SpringBootApplication
@MapperScan("sample.seata.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@RequestMapping("/order")
public class StorageMain7001 {

    @Autowired
    private StorageService orderService;

    //http://localhost:6770/order/create
    @GetMapping("/create")
    public Boolean create(long userId, long productId) {
        Storage order = new Storage();
        order.setCount(1).setMoney(BigDecimal.valueOf(88)).setProductId(productId).setUserId(userId).setStatus(0);
        return orderService.create(order);
    }

    public static void main(String[] args) {
        SpringApplication.run(StorageMain7001.class, args);
    }
}
