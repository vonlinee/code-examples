package org.example.springboot.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.example.springboot.rest.ListResult;
import org.example.springboot.rest.Result;
import org.example.springboot.serialize.SerializableModel;
import org.example.springboot.service.HelloService;
import org.joda.beans.impl.flexi.FlexiBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Resource
    HelloService service;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        throw new IllegalArgumentException(this.getClass().getName() + " test");
    }
    
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<Map<String, Object>>> test22() {
        ListResult<Map<String, Object>> list = new ListResult<>();
        list.addTestData();
        return list;
    }
    
	@Value("${server.port}")
	private int port;
    
	// 测试Feign序列化
    @PostMapping("/serialzie")
    @ResponseBody
    public SerializableModel serialize(SerializableModel remoteModel) {
    	System.out.println("接收方: " + port);
    	System.out.println(remoteModel);
        return remoteModel;
    }




    // 测试Feign序列化
    @PostMapping("/jodabean")
    @ResponseBody
    public Map<String, Object> serialize(FlexiBean flexiBean) {
        System.out.println(flexiBean);
        return flexiBean.toMap();
    }









}

// org.springframework.web.client.HttpMessageConverterExtractor<T>

