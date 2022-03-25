package code.sample.springcloud.service.impl;

import org.springframework.stereotype.Service;

import code.sample.springcloud.dao.PaymentDao;
import code.sample.springcloud.entities.Payment;
import code.sample.springcloud.service.PaymentService;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Resource
	private PaymentDao paymentDao;

	@Override
	public int create(Payment payment) {
		return paymentDao.create(payment);
	}

	@Override
	public Payment getPaymentById(Long id) {
		return paymentDao.getPaymentById(id);
	}
}
