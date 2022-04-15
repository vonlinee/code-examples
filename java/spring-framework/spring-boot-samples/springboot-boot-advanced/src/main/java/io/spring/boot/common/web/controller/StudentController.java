package io.spring.boot.common.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.spring.boot.common.web.dao.IScoreDao;
import io.spring.boot.common.web.entity.Score;
import io.spring.boot.common.web.entity.Student;
import io.spring.boot.common.web.entity.ValidatorTest;
import io.spring.boot.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;

@RestController
@RequestMapping("/stu")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IScoreDao scoreService;

    @RequestMapping("/likeName")
    public List<Student> likeName(@RequestParam String name, HttpServletRequest request) {
        logger.info(">>>>>>>>>>>>>" + request.getParameter("name2"));
        PageHelper.startPage(1, 1);// 只对第一个查询有效
        List<Student> list = new ArrayList<>();
        list.addAll(studentService.likeName(name));
        list.addAll(studentService.likeNameByDefaultDataSource(name));
        return list;
    }

    /**
     * 如果要携带错误信息BindingResult，返回值必须为Model或者ModelAndView。
     * 比如单纯的返回 List<Student> 是不会携带 BindingResult 信息的。
     *
     * @param test
     * @param result
     * @param model
     * @return
     * @author SHANHY
     * @create 2016年4月28日
     */
    @RequestMapping("/list")
    public Model getStus(@Valid ValidatorTest test, BindingResult result, Model model) {
        logger.info("从数据库读取Student集合");
        model.addAttribute("list", studentService.getList());
        return model;
    }

    @RequestMapping("/scoreList")
    public List<Score> getScoreList() {
        logger.info("从数据库读取Score集合");
        // 测试更新数据库
        logger.info("更新的行数：" + scoreService.updateScoreById(88.8f, 2));
//        scoreService.delete(23);

        return scoreService.getList();
    }
}
