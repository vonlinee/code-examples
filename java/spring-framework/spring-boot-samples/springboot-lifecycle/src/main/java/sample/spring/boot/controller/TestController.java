package sample.spring.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.spring.boot.service.ITestService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    ITestService testService;

    @RequestMapping("/1")
    public String test() {
        return testService.test();
    }
}
