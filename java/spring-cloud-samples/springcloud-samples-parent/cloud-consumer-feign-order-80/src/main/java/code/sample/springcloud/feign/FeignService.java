package code.sample.springcloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import code.sample.springcloud.entities.CommonResult;
import code.sample.springcloud.entities.Payment;

@Repository
@FeignClient(name = "FEIGN-PAYMENT-SERVICE-8001", url = "localhost:8001", fallback = FeignFallBack.class)
// @FeignClient(name = "FEIGN-PAYMENT-SERVICE-8001", url = "CLOUD-PAYMENT-SERVICE", fallback = FeignFallBack.class)
public interface FeignService {

    @RequestMapping(value = "/payment/create", method = RequestMethod.GET)
    CommonResult<Payment> create(Payment payment);

    @GetMapping("/payment/feign/timeout")
    String paymentFeignTimeOut();
}
