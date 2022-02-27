package code.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //支持Nacos的配置动态刷新
public class OrderNacosController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping(value = "/config/info")
    public String paymentInfo() {
        return configInfo;
    }
}
