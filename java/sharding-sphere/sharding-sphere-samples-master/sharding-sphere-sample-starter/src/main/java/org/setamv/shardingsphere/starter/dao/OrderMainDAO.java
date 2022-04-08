package org.setamv.shardingsphere.starter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.setamv.shardingsphere.starter.model.OrderMain;

@Mapper
public interface OrderMainDAO {

    int insert(OrderMain data);

    @Select("select * from order_main where order_id = #{orderId}")
    OrderMain get(Long orderId);
}
