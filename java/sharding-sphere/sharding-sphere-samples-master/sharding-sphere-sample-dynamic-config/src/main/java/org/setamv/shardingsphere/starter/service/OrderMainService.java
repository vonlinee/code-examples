package org.setamv.shardingsphere.starter.service;

import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.setamv.shardingsphere.starter.dao.OrderMainDAO;
import org.setamv.shardingsphere.starter.model.OrderDetail;
import org.setamv.shardingsphere.starter.model.OrderMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderMainService {

    @Autowired
    private OrderMainDAO orderMainByIdDAO;

    @Autowired
    private OrderDetailService orderDetailByIdService;

    private SnowflakeShardingKeyGenerator keyGenerator = new SnowflakeShardingKeyGenerator();

    @Transactional(rollbackFor = Exception.class)
    public Long add(OrderMain order) {
        int cnt = orderMainByIdDAO.insert(order);
        if (cnt <= 0) {
            throw new RuntimeException("保存订单失败！未能成功插入订单主数据。");
        }
        if (order.getOrderDetails() != null && order.getOrderDetails().size() > 0) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                orderDetail.setOrderId(order.getOrderId());
            }
            orderDetailByIdService.batchAdd(order.getOrderDetails(), order.getOrderDate());
        }
        return order.getOrderId();
    }

    public OrderMain get(Long orderId, boolean withDetail) {
        OrderMain orderMain = orderMainByIdDAO.get(orderId);
        if (orderMain != null && withDetail) {
            orderMain.setOrderDetails(orderDetailByIdService.list(orderMain.getOrderId()));
        }
        return orderMain;
    }
}
