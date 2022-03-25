package code.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import code.sample.springcloud.entities.CommonResult;
import code.sample.springcloud.entities.Payment;
import code.sample.springcloud.feign.FeignService;

@RestController
@RequestMapping(value = "/feign", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private FeignService feignService;

    private static final String PAYMENT_URL = "localhost:8881/feign/consumer/payment/create";

    @Autowired
    public OrderController(FeignService feignService) {
        this.feignService = feignService;
    }

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return feignService.create(payment);
    }

    @GetMapping("/consumer/feign/timeout")
    public String paymentFeignTimeOut() {
        return feignService.paymentFeignTimeOut();
    }
}
