package org.samples.bean;

import org.samples.bean.vo.Address;
import utils.GenericParam;

import java.util.List;
import java.util.Map;

/**
 * 注释信息
 */
public class Student {

    private final boolean sex = false; // 性别

    /**
     * 姓名
     */
    private String name;

    public int a, b[];

    /**
     * 地址
     */
    List<@GenericParam(Address.class) Address> addresses;

    private Map<@GenericParam(String.class) String, @GenericParam(Student.class) Student> map;

    public void run() {
        System.out.println(this.name + " is running!");
    }

    public String getName() {
        return this.name;
    }

}
