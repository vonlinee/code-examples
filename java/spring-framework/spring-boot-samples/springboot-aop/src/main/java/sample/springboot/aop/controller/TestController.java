package sample.springboot.aop.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sample.springboot.aop.service.UserService;

@RestController
@RequestMapping("/test")
public class TestController {

	@Resource
	UserService userService; // 使用了代理，因此注入的是代理类
	
	// 如果没有为userServcice的方法添加切点，那么不会为UserService生成代理
	
//	http://localhost:8080/test/1
	@GetMapping("/1")
	public void test1() {
		System.out.println(userService.getClass().toString());
		userService.login("zs", "123");
	}
}
