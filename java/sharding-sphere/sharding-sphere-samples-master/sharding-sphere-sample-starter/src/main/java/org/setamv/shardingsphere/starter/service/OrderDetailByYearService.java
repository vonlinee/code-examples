package org.setamv.shardingsphere.starter.service;

import org.setamv.shardingsphere.starter.dao.OrderDetailByYearDAO;
import org.setamv.shardingsphere.starter.dto.OrderDetailByYearDTO;
import org.setamv.shardingsphere.starter.dto.OrderDetailQueryParamsDTO;
import org.setamv.shardingsphere.starter.model.OrderDetailByYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderDetailByYearService {

    @Autowired
    private OrderDetailByYearDAO orderDetailByYearDAO;

    @Transactional(rollbackFor = Exception.class)
    public int add(OrderDetailByYear orderDetail, Date orderDate) {
        return orderDetailByYearDAO.insert(orderDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    public int batchAdd(List<OrderDetailByYear> orderDetails) {
        return orderDetailByYearDAO.batchInsert(orderDetails);
    }

    public List<OrderDetailByYear> list(Long orderId) {
        return orderDetailByYearDAO.list(orderId);
    }

    public List<OrderDetailByYearDTO> listWithMain(OrderDetailQueryParamsDTO params) {
        return orderDetailByYearDAO.listWithMain(params);
    }
}
