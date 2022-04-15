package io.spring.boot.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用 @ModelAttribute 和  @RequestMapping 同时注解方法
 *
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年4月16日
 */
@Controller
@RequestMapping("/modelattributeTest4")
public class ModelAttributeTestController4 {

    @RequestMapping(value = "/test1")
    @ModelAttribute("name")
    public String test1(@RequestParam(required = false) String name) {
        return name;
    }

}
