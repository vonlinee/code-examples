package org.setamv.shardingsphere.starter.service;

import org.setamv.shardingsphere.starter.dao.OrderDetailDAO;
import org.setamv.shardingsphere.starter.dto.OrderDetailDTO;
import org.setamv.shardingsphere.starter.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailDAO orderDetailByIdDAO;

    @Transactional(rollbackFor = Exception.class)
    public int add(OrderDetail orderDetail, Date orderDate) {
        return orderDetailByIdDAO.insert(orderDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    public int batchAdd(List<OrderDetail> orderDetails, Date orderDate) {
        return orderDetailByIdDAO.batchInsert(orderDetails);
    }

    public List<OrderDetail> list(Long orderId) {
        return orderDetailByIdDAO.list(orderId);
    }

    public List<OrderDetailDTO> listWithMain(Long orderId) {
        return orderDetailByIdDAO.listWithMain(orderId);
    }
}
