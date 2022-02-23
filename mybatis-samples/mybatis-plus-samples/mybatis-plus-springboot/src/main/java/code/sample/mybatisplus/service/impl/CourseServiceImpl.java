package code.sample.mybatisplus.service.impl;

import code.sample.mybatisplus.entity.Course;
import code.sample.mybatisplus.mapper.CourseMapper;
import code.sample.mybatisplus.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 * @author ly-busicen
 * @since 2022-02-22
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
