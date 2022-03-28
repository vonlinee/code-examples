package com.lontyu.sharding.shardingsphere.service;

import com.lontyu.sharding.shardingsphere.mapper.StudentMapper;
import com.lontyu.sharding.shardingsphere.mapper.TeacherMapper;
import com.lontyu.sharding.shardingsphere.mapper.UserMapper;
import com.lontyu.sharding.shardingsphere.model.Student;
import com.lontyu.sharding.shardingsphere.model.Teacher;
import com.lontyu.sharding.shardingsphere.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: xiaoZongjin
 * @create: 2019-09-23
 */
@Service
public class TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 保存 student 结果
     *
     * @param id
     */
    public void saveStu(int id) {
        Student student = new Student();
        student.setId(id);
        student.setName("zhansgan" + id);
        student.setAddress("江西" + id);

        int count = studentMapper.insertSelective(student);

        LOGGER.info("保存STUDENT结果：" + (count > 0 ? true : false));
    }

    /**
     * 保存 user 结果
     *
     * @param id
     */
    public void saveUser(int id) {
        User user = new User();
        user.setId(id);
        user.setName("zhansgan" + id);
        user.setAge(2 + id);

        int count = userMapper.insertSelective(user);

        LOGGER.info("保存USER结果：" + (count > 0 ? true : false));
    }

    /**
     * 保存 teacher结果
     *
     * @param id
     */
    public void saveTeacher(int id) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setName("zhansgan" + id);
        teacher.setAge(id + 5);

        int count = teacherMapper.insertSelective(teacher);

        LOGGER.info("保存Teacher结果：" + (count > 0 ? true : false));
    }

}