package org.setamv.shardingsphere.starter.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.setamv.shardingsphere.starter.dto.OrderDetailDTO;
import org.setamv.shardingsphere.starter.dto.OrderIdConditionDTO;
import org.setamv.shardingsphere.starter.model.OrderDetail;

import java.util.List;

@Mapper
public interface OrderDetailDAO {

    @Insert("insert into order_detail(order_id, product_id, quantity, price, creator_id) values(#{data.orderId}, #{data.productId}, #{data.quantity}, #{data.price}, #{data.creatorId})")
    int insert(@Param("data") OrderDetail data);

    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> list(Long orderId);

    int batchInsert(List<OrderDetail> list);

    List<OrderDetailDTO> listWithMain(OrderIdConditionDTO condition);
}
