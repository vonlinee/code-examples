package io.spring.boot.service;

import java.util.List;

import io.spring.boot.common.entity.Student;

public interface IStudentService {

    List<Student> likeName(String name);

    int testSave();

    List<Student> likeNameByDefaultDataSource(String name);

    /**
     * 不指定数据源使用默认数据源
     * @return
     * @author SHANHY
     * @create 2016年1月24日
     */
    List<Student> getList();

    /**
     * 指定数据源
     * @return
     * @author SHANHY
     * @create 2016年1月24日
     */
    List<Student> getListByDs1();

    /**
     * 指定数据源
     * @return
     * @author SHANHY
     * @create 2016年1月24日
     */
    List<Student> getListByDs2();

}