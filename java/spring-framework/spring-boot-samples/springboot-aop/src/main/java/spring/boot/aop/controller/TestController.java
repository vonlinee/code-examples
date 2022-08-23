package spring.boot.aop.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.aop.service.UserCheckService;
import spring.boot.aop.service.UserService;

import java.lang.reflect.Field;

@RestController
@RequestMapping("/user")
public class TestController {

    @Resource
    UserService userService; // 使用了代理，因此注入的是代理类

    @Resource
    UserCheckService userCheckService;

    // 如果没有为userServcice的方法添加切点，那么不会为UserService生成代理

    //	http://localhost:8080/test/1
    @GetMapping("/login")
    public void login() {
        userService.login("zs", "123");


        Field[] declaredFields = userService.getClass().getDeclaredFields();


    }

    @GetMapping("/point")
    public void pointcut(boolean flag) {
        userCheckService.checkUserInfo("zs", "123", flag);
    }
}
