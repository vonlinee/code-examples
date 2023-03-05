package org.example.mybatisplus.service.impl;

import org.example.mybatisplus.entity.Course;
import org.example.mybatisplus.mapper.CourseMapper;
import org.example.mybatisplus.service.ICourseService;
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
