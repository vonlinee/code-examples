package org.setamv.shardingsphere.starter.dto;

import lombok.Data;
import org.setamv.shardingsphere.starter.model.OrderDetailByYear;

@Data
public class OrderDetailByYearDTO extends OrderDetailByYear {

    private Double amount;
}
