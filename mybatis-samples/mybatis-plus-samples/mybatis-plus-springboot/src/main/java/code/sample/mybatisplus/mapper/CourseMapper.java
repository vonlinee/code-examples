package code.sample.mybatisplus.mapper;

import code.sample.mybatisplus.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 * @author sdf
 * @since 2022-02-22
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    List<Map<String, Object>> queryTeacherAll();
}
