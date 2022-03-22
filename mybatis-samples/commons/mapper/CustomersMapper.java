package mybatis.mapper;

import mybatis.entity.Customers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@Mapper
public interface CustomersMapper extends BaseMapper<Customers> {

}
