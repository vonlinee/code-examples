package org.setamv.shardingsphere.starter.service;

import org.setamv.shardingsphere.starter.dao.OrderMainByYearDAO;
import org.setamv.shardingsphere.starter.model.OrderDetailByYear;
import org.setamv.shardingsphere.starter.model.OrderMainByYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderMainByYearService {

    @Autowired
    private OrderMainByYearDAO orderMainByYearDAO;

    @Autowired
    private OrderDetailByYearService orderDetailService;

    @Transactional(rollbackFor = Exception.class)
    public Long add(OrderMainByYear order) {
        int cnt = orderMainByYearDAO.insert(order);
        if (cnt <= 0) {
            throw new RuntimeException("保存订单失败！未能成功插入订单主数据。");
        }
        if (order.getOrderDetails() != null && order.getOrderDetails().size() > 0) {
            for (OrderDetailByYear orderDetail : order.getOrderDetails()) {
                orderDetail.setOrderId(order.getOrderId());
                orderDetail.setOrderDate(order.getOrderDate());
            }
            orderDetailService.batchAdd(order.getOrderDetails());
        }
        return order.getOrderId();
    }

    public OrderMainByYear get(Long orderId, boolean withDetail) {
        OrderMainByYear orderMain = orderMainByYearDAO.get(orderId);
        if (orderMain != null && withDetail) {
            orderMain.setOrderDetails(orderDetailService.list(orderMain.getOrderId()));
        }
        return orderMain;
    }
}
