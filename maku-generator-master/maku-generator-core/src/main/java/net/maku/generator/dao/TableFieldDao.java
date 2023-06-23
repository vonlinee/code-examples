package net.maku.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.maku.generator.entity.TableFieldInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 表字段
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Mapper
public interface TableFieldDao extends BaseMapper<TableFieldInfo> {

    List<TableFieldInfo> getByTableId(Long tableId);

    void deleteBatchTableIds(Long[] tableIds);
}
