package org.setamv.shardingsphere.starter.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单ID查询条件
 */
@Data
public class OrderIdConditionDTO implements Serializable {

    private Long mainOrderId;

    private Long detailOrderId;
}
