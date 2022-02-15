package code.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import code.sample.springcloud.entities.CommonResult;
import code.sample.springcloud.entities.Payment;
import code.sample.springcloud.feign.FeignService;

@RestController
public class OrderController {

	private FeignService feignService;
	
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
