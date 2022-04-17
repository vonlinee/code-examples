package io.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用 @ModelAttribute 注解方法参数
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年4月16日
 */
@Controller
@RequestMapping("/modelattributeTest3")
public class ModelAttributeTestController3 {

    @ModelAttribute(value = "attributeName")
    public String myModel(@RequestParam(required = false) String abc) {
        return abc;
    }

    @ModelAttribute
    public void myModel3(Model model) {
        model.addAttribute("name", "SHANHY");
        model.addAttribute("age", "28");
    }

    @RequestMapping(value = "/test1")
    public String test1(@ModelAttribute("attributeName") String str,
                        @ModelAttribute("name") String str2,
                        @ModelAttribute("age") String str3) {
        return "modelattributetest/test1";
    }

}
