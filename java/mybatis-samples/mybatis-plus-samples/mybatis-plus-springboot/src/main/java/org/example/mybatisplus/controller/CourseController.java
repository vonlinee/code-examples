package org.example.mybatisplus.controller;

import org.example.mybatisplus.mapper.CourseMapper;
import org.example.mybatisplus.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    ICourseService courseService;

    @Autowired
    CourseMapper courseMapper;

    @GetMapping("/findall")
    @ResponseBody
    public List<Map<String, Object>> findAll() {

        System.out.println(courseMapper);
        return courseMapper.queryTeacherAll();
    }

}
