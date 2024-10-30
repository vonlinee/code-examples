package org.example.order.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(contextId = "paymentClient2", name = "payment-service", qualifiers = {"p2"})
public interface FeignPaymentService2 {

    @PostMapping("/api/payment/create")
    Map<String, Object> createPayment(Map<String, Object> map);
}
