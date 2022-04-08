package org.setamv.shardingsphere.starter.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.setamv.shardingsphere.starter.dto.OrderDetailDTO;
import org.setamv.shardingsphere.starter.dto.OrderIdConditionDTO;
import org.setamv.shardingsphere.starter.dto.OrderPaymentDTO;
import org.setamv.shardingsphere.starter.model.OrderDetail;
import org.setamv.shardingsphere.starter.model.OrderPayment;

import java.util.List;

@Mapper
public interface OrderPaymentDAO {

    @Insert("insert into order_payment(order_id, pay_amount, pay_time, creator_id) values(#{data.orderId}, #{data.payAmount}, #{data.payTime}, #{data.creatorId})")
    int insert(@Param("data") OrderPayment data);

    @Select("select * from order_payment where order_id = #{orderId}")
    OrderPayment getByOrderId(Long orderId);

    int batchInsert(List<OrderPayment> list);

    OrderPaymentDTO getWithMain(OrderIdConditionDTO condition);
}
