package org.setamv.shardingsphere.starter.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 按订单ID分库分表的订单支付数据Model
 */
@Data
public class OrderPayment implements Serializable {

    private Long id;
    private Long orderId;
    private Double payAmount;
    private String payTime;
    private Long creatorId;
}
