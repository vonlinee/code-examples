package mybatis.service.impl;

import mybatis.entity.Vendors;
import mybatis.mapper.VendorsMapper;
import mybatis.service.IVendorsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 生产商表 服务实现类
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@Service
public class VendorsServiceImpl extends ServiceImpl<VendorsMapper, Vendors> implements IVendorsService {

}
