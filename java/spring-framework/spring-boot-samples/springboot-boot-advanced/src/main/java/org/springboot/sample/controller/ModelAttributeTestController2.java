package org.springboot.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用 @ModelAttribute 注解带有返回值的方法
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年4月16日
 */
@Controller
@RequestMapping("/modelattributeTest2")
public class ModelAttributeTestController2 {

    @ModelAttribute(value = "attributeName")
    public int myModel(@RequestParam(required = false) int abc) {
        return abc;
    }

    @RequestMapping(value = "/test1")
    public String test1() {
        // 在页面中可以直接使用 ${attributeName} 读取值
        return "modelattributetest/test1";
    }

}
