package org.setamv.shardingsphere.starter.dto;

import lombok.Data;
import org.setamv.shardingsphere.starter.model.OrderPayment;

import java.util.Date;

/**
 * 按订单ID分库分表的订单支付数据Model
 */
@Data
public class OrderPaymentDTO extends OrderPayment {

    private Date orderDate;
    private Double amount;
}
