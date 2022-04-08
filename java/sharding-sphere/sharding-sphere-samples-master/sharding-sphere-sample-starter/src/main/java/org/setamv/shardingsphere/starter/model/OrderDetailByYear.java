package org.setamv.shardingsphere.starter.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 按年分库分表的订单明细数据Model
 * <p>注意，订单明细数据中不包含订单日期，需要借助主表的订单日期进行分库分表
 */
@Data
public class OrderDetailByYear implements Serializable {

    private Long id;
    private Long orderId;
    private Date orderDate;
    private Long productId;
    private Integer quantity;
    private Double price;
    private Long creatorId;
}
