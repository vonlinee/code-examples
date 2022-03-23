package code.sample.springcloud.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //支持Nacos的配置动态刷新
public class OrderSentinelController {

    @GetMapping(value = "/sentinel/test")
    public String paymentInfo() {
        return "";
    }
}
