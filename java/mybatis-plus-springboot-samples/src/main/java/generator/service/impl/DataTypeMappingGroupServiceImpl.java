package generator.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import generator.domain.DataTypeMappingGroup;
import generator.service.DataTypeMappingGroupService;
import generator.mapper.DataTypeMappingGroupMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【data_type_mapping_group(数据类型映射关系表)】的数据库操作Service实现
* @createDate 2024-06-18 12:58:35
*/
@Service
public class DataTypeMappingGroupServiceImpl extends ServiceImpl<DataTypeMappingGroupMapper, DataTypeMappingGroup>
implements DataTypeMappingGroupService{

}
