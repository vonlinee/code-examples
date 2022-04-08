package org.setamv.shardingsphere.starter.service;

import org.setamv.shardingsphere.starter.dao.OrderPaymentDAO;
import org.setamv.shardingsphere.starter.dto.OrderIdConditionDTO;
import org.setamv.shardingsphere.starter.dto.OrderPaymentDTO;
import org.setamv.shardingsphere.starter.model.OrderPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderPaymentService {

    @Autowired
    private OrderPaymentDAO orderPaymentDAO;

    @Transactional(rollbackFor = Exception.class)
    public int add(OrderPayment orderorderPayment) {
        return orderPaymentDAO.insert(orderorderPayment);
    }

    public OrderPayment getByOrderId(Long orderId) {
        return orderPaymentDAO.getByOrderId(orderId);
    }

    public OrderPaymentDTO getWithMain(OrderIdConditionDTO condition) {
        return orderPaymentDAO.getWithMain(condition);
    }
}
