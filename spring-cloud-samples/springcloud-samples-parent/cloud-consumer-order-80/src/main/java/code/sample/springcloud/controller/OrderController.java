package code.sample.springcloud.controller;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import code.sample.springcloud.entities.CommonResult;
import code.sample.springcloud.entities.Payment;
import code.sample.springcloud.loadbalance.LoadBalance;

/**
 * HTTP远程调用服务
 */
@RestController
@RequestMapping(value = "/consumer", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private LoadBalance loadBalance;

    @Resource
    private DiscoveryClient discoveryClient;

    public static final String PAYMENT_8001_URL = "http://localhost:8001";
    // payment模块的spring.application.name
    public static final String PAYMENT_SERVICE_8001_URL = "http://CLOUD-PAYMENT-SERVICE";

    //http://localhost:8001/payment/create

    @Resource
    private RestTemplate restTemplate;

    @SuppressWarnings("unchecked")
    @GetMapping("/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        LOG.debug("/payment/create");
        return restTemplate.postForObject(PAYMENT_SERVICE_8001_URL + "/payment/create", payment, CommonResult.class);
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_8001_URL + "/payment/get/" + id, CommonResult.class);
    }

    // 使用RestTemplate

    @SuppressWarnings("unchecked")
    @GetMapping("/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity =
                restTemplate.getForEntity(PAYMENT_8001_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            LOG.info(entity.getStatusCode() + "\t" + entity.getHeaders());
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "操作失败");
        }
    }

    //使用负载均衡

    /**
     * 负载均衡调用
     * @return
     */
    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalance.instance(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin() {
        return restTemplate.getForObject("http://localhost:8001" + "/payment/zipkin/", String.class);
    }
}
