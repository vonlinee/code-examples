package org.setamv.shardingsphere.starter.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.setamv.shardingsphere.starter.dto.OrderDetailByYearDTO;
import org.setamv.shardingsphere.starter.dto.OrderDetailQueryParamsDTO;
import org.setamv.shardingsphere.starter.model.OrderDetailByYear;

import java.util.List;

@Mapper
public interface OrderDetailByYearDAO {

    @Insert("insert into order_detail_by_year(order_id, product_id, quantity, price, creator_id) values(#{data.orderId}, #{data.productId}, #{data.quantity}, #{data.price}, #{data.creatorId})")
    int insert(@Param("data") OrderDetailByYear data);

    @Select("select * from order_detail_by_year where order_id = #{orderId}")
    List<OrderDetailByYear> list(Long orderId);

    int batchInsert(List<OrderDetailByYear> list);

    List<OrderDetailByYearDTO> listWithMain(OrderDetailQueryParamsDTO params);
}
