package code.sample.mybatisplus.controller;

import code.sample.mybatisplus.entity.Course;
import code.sample.mybatisplus.service.ICourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 * @author ly-busicen
 * @since 2022-02-22
 */
@Controller
@RequestMapping("/test/course")
public class CourseController {

    @Resource
    ICourseService courseService;

    @GetMapping("/findall")
    @ResponseBody
    public Course findAll() {
        return courseService.query().one();
    }

}
