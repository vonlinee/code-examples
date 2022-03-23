package code.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Value("${server.port}")        //获取端口号
    private String serverPort;

    @RequestMapping("/payment/nacos/{id}")
    public String paymentNacos(@PathVariable("id") Long id) {
        return "nacos provider:" + serverPort + "\t" + id;
    }
}
