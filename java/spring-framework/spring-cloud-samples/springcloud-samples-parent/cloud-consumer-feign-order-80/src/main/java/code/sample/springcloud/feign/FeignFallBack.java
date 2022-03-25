package code.sample.springcloud.feign;

import code.sample.springcloud.entities.CommonResult;
import code.sample.springcloud.entities.Payment;

public class FeignFallBack implements FeignService {

	@Override
	public CommonResult<Payment> create(Payment payment) {
		return new CommonResult<>(404, "Feign TimeOut", null);
	}

	@Override
	public String paymentFeignTimeOut() {
		return "Feign TimeOut"; //主动超时
	}
}
