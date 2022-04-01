package com.lontyu.sharding.shardingsphere.controller;

import com.lontyu.sharding.shardingsphere.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 测试控制器
 * @author: xiaoZongjin
 * @create: 2019-09-23
 */
@RequestMapping("/test")
@RestController
public class TestController {

    // 库总数 ，根据实际情况修改
    private static final int DB_NUM = 2;
    // student 分表总数，3
    private static final int TB_STUDENT_NUM = 3;
    // teacher 分表总数 2
    private static final int TB_TEACHER_NUM = 2;

    @Autowired
    TestService testService;

    @GetMapping(value = "/001/{id}")
    public String test01(@PathVariable("id") int id) {
        testService.saveStu(id);
        String info = returnDbTableInfo("ok-01", id, "student");
        return info;
    }

    @RequestMapping(value = "/002/{id}", method = RequestMethod.GET)
    public String test02(@PathVariable("id") int id) {
        testService.saveTeacher(id);
        String info = returnDbTableInfo("ok-02", id, "teacher");
        return info;
    }

    @RequestMapping(value = "/003/{id}", method = RequestMethod.GET)
    public String test03(@PathVariable("id") int id) {
        testService.saveUser(id);

        return "ok-03，库名：mydb,表名：user";
    }


    /**
     * 组装返回信息
     *
     * @param name
     * @param id
     * @param type
     * @return
     */
    private String returnDbTableInfo(String name, int id, String type) {
        StringBuffer sb = new StringBuffer();
        sb.append(name + ",库名：mydb" + id % DB_NUM);
        String tableName = "";
        if ("student".equals(type)) {
            tableName = "student_" + id % TB_STUDENT_NUM;
        } else {
            tableName = "teacher_" + id % TB_TEACHER_NUM;
        }
        sb.append(",表名：" + tableName);

        return sb.toString();
    }
}