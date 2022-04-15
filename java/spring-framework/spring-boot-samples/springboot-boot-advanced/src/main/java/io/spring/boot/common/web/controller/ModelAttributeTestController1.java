package io.spring.boot.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用 @ModelAttribute 注解无返回值的方法
 *
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年4月16日
 */
@Controller
@RequestMapping("/modelattributeTest")
public class ModelAttributeTestController1 {

    @ModelAttribute
    public void myModel(@RequestParam(required = false) String abc, Model model) {
        model.addAttribute("attributeName", abc);
    }

    @RequestMapping(value = "/test1")
    public String test1(Model model) {
        return "modelattributetest/test1";
    }

    @RequestMapping(value = "/test2")
    public String test1(@RequestParam(required = false) String abc, Model model) {
        model.addAttribute("attributeName", abc);
        return "modelattributetest/test1";
    }
}
