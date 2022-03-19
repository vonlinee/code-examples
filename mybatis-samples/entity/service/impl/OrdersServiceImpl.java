package mybatis.service.impl;

import mybatis.entity.Orders;
import mybatis.mapper.OrdersMapper;
import mybatis.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
