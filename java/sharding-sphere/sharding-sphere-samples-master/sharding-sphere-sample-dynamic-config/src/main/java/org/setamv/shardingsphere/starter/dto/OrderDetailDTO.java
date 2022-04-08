package org.setamv.shardingsphere.starter.dto;

import lombok.Data;
import org.setamv.shardingsphere.starter.model.OrderDetail;

import java.util.Date;

@Data
public class OrderDetailDTO extends OrderDetail {

    private Date orderDate;
    private Double amount;
}
