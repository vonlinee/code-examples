package mybatis.service.impl;

import mybatis.entity.Orderitems;
import mybatis.mapper.OrderitemsMapper;
import mybatis.service.IOrderitemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@Service
public class OrderitemsServiceImpl extends ServiceImpl<OrderitemsMapper, Orderitems> implements IOrderitemsService {

}
