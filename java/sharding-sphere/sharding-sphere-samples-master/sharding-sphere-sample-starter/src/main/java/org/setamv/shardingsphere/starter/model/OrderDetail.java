package org.setamv.shardingsphere.starter.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 按订单ID库分表的订单明细数据Model
 */
@Data
public class OrderDetail implements Serializable {

    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double price;
    private Long creatorId;
}
