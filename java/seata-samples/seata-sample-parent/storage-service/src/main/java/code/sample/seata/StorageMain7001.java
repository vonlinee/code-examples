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
@RequestMapping("/storage")
public class StorageMain7001 {

    @Autowired
    private StorageService storageService;

    //http://localhost:6770/order/create
    @GetMapping("/use")
    public Boolean create(long userId, long productId) {
        Storage storage = new Storage();
        storage.setId(1L);
        storage.setTotal(100);
        storage.setProductId(10L);
        storage.setUsed(1);
        return storageService.updateUseNum(storage.getUsed(), storage.getProductId());
    }

    public static void main(String[] args) {
        SpringApplication.run(StorageMain7001.class, args);
    }
}
