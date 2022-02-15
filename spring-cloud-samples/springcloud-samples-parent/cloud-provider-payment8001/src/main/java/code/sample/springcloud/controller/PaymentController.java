package code.sample.springcloud.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import code.sample.springcloud.entities.CommonResult;
import code.sample.springcloud.entities.Payment;
import code.sample.springcloud.service.PaymentService;

@RestController
public class PaymentController {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

	@Resource
	private PaymentService paymentService;

	@Value("${server.port}")
	private String serverPort;

	@Resource
	private DiscoveryClient discoveryClient;

	@PostMapping(value = "/payment/create")
	public CommonResult<?> create(@RequestBody Payment payment) {
		int result = paymentService.create(payment);
		LOG.info("插入结果:" + result);
		if (result > 0) {
			return new CommonResult<>(200, "插入成功,serverPort: " + serverPort, result);
		}
		return new CommonResult<>(444, "插入失败", null);
	}

	@GetMapping(value = "/payment/get/{id}")
	public CommonResult<?> getPaymentById(@PathVariable("id") Long id) {
		Payment paymentById = paymentService.getPaymentById(id);
		LOG.info("查询结果:" + paymentById);
		if (paymentById != null) {
			return new CommonResult<>(200, "查询成功,serverPort: " + serverPort, paymentById);
		} else {
			return new CommonResult<>(444, "没有对应记录，查询id:" + id, null);
		}
	}

	@GetMapping(value = "/payment/discovery")
	public Object discovery() {
		List<String> services = discoveryClient.getServices();
		for (String element : services) {
			LOG.info("****element: " + element);
		}
		List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
		for (ServiceInstance instance : instances) {
			LOG.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t"
					+ instance.getUri());
		}
		return this.discoveryClient;
	}

	@GetMapping(value = "/payment/lb")
	public String getPaymentLB() {
		return serverPort;
	}

	@GetMapping("/payment/feign/timeout")
	public String paymentFeignTimeOut() {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return serverPort;
	}

	@GetMapping("/payment/zipkin")
	public String paymentZipkin() {
		return "hi,i'am paymentzipkin server fall back, welcome to";
	}
}
