package code.sample.springcloud.service;

import org.apache.ibatis.annotations.Param;

import code.sample.springcloud.entities.Payment;

public interface PaymentService {
    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
