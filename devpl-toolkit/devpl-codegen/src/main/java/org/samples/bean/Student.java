package org.samples.bean;

import org.samples.bean.vo.Address;
import org.samples.bean.vo.Response;

/**
 * 注释信息
 */
public class Student {

    // private final boolean sex = false; // 性别
    //
    // /**
    //  * 姓名
    //  */
    // private String name;
    //
    // public int a, b[];
    public Student(Response<Address> response) {
        this.response = response;
    }

    /**
     * 地址
     */
    // private List<Address> addresses;
    private Response<Address> response;
    // private List<Map<String, Object>> mapList;
    // private Map<@GenericParam(String.class) String, @GenericParam(Student.class) Student> map;
    // public void run() {
    //     System.out.println(this.name + " is running!");
    // }
    //
    // public String getName() {
    //     return this.name;
    // }
}
