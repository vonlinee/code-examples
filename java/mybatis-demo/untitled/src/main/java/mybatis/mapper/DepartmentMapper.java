package mybatis.mapper;

import mybatis.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    List<Department> selectList();
}
