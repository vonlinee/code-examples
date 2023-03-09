package org.example.controller;

import org.example.bean.Employee;
import org.example.bean.Result;
import org.example.utils.DateTimeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 提供HTTP服务，供测试调用，自己调自己
 */
@RestController
@RequestMapping(value = "/api")
public class ServerController {

    @GetMapping(value = "/employee/list")
    public Result<List<Employee>> listEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("张三", 24, 2000, DateTimeUtils.ofLocalDate("1994-04-23")));
        employeeList.add(new Employee("李四", 24, 5000, DateTimeUtils.ofLocalDate("1991-07-11")));
        return Result.<List<Employee>>builder().code(200).message("操作成功").data(employeeList).build();
    }

    @GetMapping(value = "/employee/one")
    public Result<Employee> oneEmployees() {
        Employee employee = new Employee("张三", 24, 2000, DateTimeUtils.ofLocalDate("1994-04-23"));
        return Result.<Employee>builder().code(200).message("操作成功").data(employee).build();
    }
}
