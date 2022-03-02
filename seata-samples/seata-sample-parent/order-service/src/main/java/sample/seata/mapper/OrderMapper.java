package sample.seata.mapper;

import org.springframework.stereotype.Repository;
import sample.seata.entity.Order;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderMapper extends Mapper<Order> {

}
 