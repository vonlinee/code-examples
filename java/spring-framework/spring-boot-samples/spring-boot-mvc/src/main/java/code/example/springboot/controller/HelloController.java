package code.example.springboot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import code.example.springboot.service.HelloService;

@RestController
@RequestMapping("/browser")
public class HelloController {
	
	private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);
	
	@Resource
	HelloService service;
	
	private String nowTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
	}
	
//	chrome浏览器请求同一个页面无法并发请求，必须等上一个页面请求完毕后才能请求
//	开发者工具：网络-停用缓存之后，就可以并发请求
//	http://localhost:8080/browser/test
	
	
	
	@RequestMapping(value = "/test", method=RequestMethod.GET)
	@ResponseBody
	public String test() {
		LOG.info(nowTime());
		service.delay(3); //延时3秒
		LOG.info(nowTime());
		return "";
	}
}
