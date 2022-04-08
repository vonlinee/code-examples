package org.setamv.shardingsphere.starter.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 按订单ID分库分表的订单主数据Model
 */
@Data
public class OrderMain implements Serializable {

    private Long orderId;
    private Date orderDate;
    private Double amount;
    private Long creatorId;

    private List<OrderDetail> orderDetails;
}
