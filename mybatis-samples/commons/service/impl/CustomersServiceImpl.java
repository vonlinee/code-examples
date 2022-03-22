package mybatis.service.impl;

import mybatis.entity.Customers;
import mybatis.mapper.CustomersMapper;
import mybatis.service.ICustomersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@Service
public class CustomersServiceImpl extends ServiceImpl<CustomersMapper, Customers> implements ICustomersService {

}
